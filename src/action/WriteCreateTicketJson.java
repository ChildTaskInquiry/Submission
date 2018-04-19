package action;

import static constant.Constants.*;
import static constant.JenkinsConstants.*;
import static constant.JsonConstants.*;
import static constant.RedmineConstants.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.DayWorkCalendar;
import com.FlexibleDateFormatter;

import dto.RedmineDto;
import net.sf.json.JSONObject;
import writeText.WriteText;

public class WriteCreateTicketJson {

	private WriteText wt = new WriteText();
	private FlexibleDateFormatter df = new FlexibleDateFormatter();
	private JSONObject json = new JSONObject();
	private JSONObject base = new JSONObject();
	private List<JSONObject> customFieldList = new ArrayList<JSONObject>();
	private DayWorkCalendar dwc = new DayWorkCalendar();
	private String addWord;
	private Calendar sDate = Calendar.getInstance();
	private Calendar eDate = Calendar.getInstance();

	public String writeCreateDesignTicketJson(JSONObject planningJson, Map<String, Integer> userMap,
			Map<Integer, Integer> projectIdMap, Map<Integer, RedmineDto> funbugMap) {

		// どのチケットにも入力する情報(チケット情報に関係ない定数)
		accumulateBase();

		// 項目に問題があるか確認
		if (isError(planningJson, userMap, projectIdMap)) {
			return "";
		}

		// どのチケットにも入力する情報(親チケット単位で決まる情報)
		accumulateCommon(planningJson, userMap, projectIdMap);

		// Design用の項目を入力する情報
		accumulateDesign(planningJson, funbugMap);

		// 最後の処理
		return lastAccumulate(planningJson);
	}

	public String writeCreateTestingTicketJson(JSONObject planningJson, Map<String, Integer> userMap,
			Map<Integer, Integer> projectIdMap, Map<Integer, RedmineDto> funbugMap) {

		// どのチケットにも入力する情報(チケット情報に関係ない定数)
		accumulateBase();

		// 項目に問題があるか確認
		if (isError(planningJson, userMap, projectIdMap)) {
			return "";
		}

		// どのチケットにも入力する情報(親チケット単位で決まる情報)
		accumulateCommon(planningJson, userMap, projectIdMap);

		// Design用の項目を入力する情報
		accumulateTesting(planningJson, funbugMap);

		// 最後の処理
		return lastAccumulate(planningJson);
	}

	public String writeCreateAdditionalTicketJson(JSONObject planningJson, Map<String, Integer> userMap,
			Map<Integer, Integer> projectIdMap, Map<Integer, RedmineDto> funbugMap) {

		// どのチケットにも入力する情報(チケット情報に関係ない定数)
		accumulateBase();

		// 項目に問題があるか確認
		if (isError(planningJson, userMap, projectIdMap)) {
			return "";
		}

		// どのチケットにも入力する情報(親チケット単位で決まる情報)
		accumulateCommon(planningJson, userMap, projectIdMap);

		// Design用の項目を入力する情報
		accumulateAdditional(planningJson, funbugMap);

		// 最後の処理
		return lastAccumulate(planningJson);
	}

	private boolean isError(JSONObject planningJson, Map<String, Integer> userMap, Map<Integer, Integer> projectIdMap) {
		String errMsg = "";
		boolean errFlag = false;
		if (!userMap.containsKey(planningJson.getString(ITEM_EVALUATOR))) {
			errMsg = errMsg + "Evaluater欄がRedmineのloginIdに存在しません / ";
			errFlag = true;
		}
		if (df.parse(planningJson.getString(ITEM_TEST_DESIGNED)) == null) {
			errMsg = errMsg + "テスト設計完了予定日がフォーマット通りではありません / ";
			errFlag = true;
		}
		if (df.parse(planningJson.getString(ITEM_CONFIRMED)) == null) {
			errMsg = errMsg + "開発完了予定日がフォーマット通りではありません / ";
			errFlag = true;
		}
		if (df.parse(planningJson.getString(ITEM_TESTED)) == null) {
			errMsg = errMsg + "テスト完了予定日がフォーマット通りではありません / ";
			errFlag = true;
		}
		if (!planningJson.containsKey(ITEM_ESTIMATE_DESIGN)
				|| planningJson.getString(ITEM_ESTIMATE_DESIGN).equals("null")) {
			errMsg = errMsg + "設計工数が記載されていません(「設計工数」のヘッダー行を追加して、予定設計工数を入力してください) / ";
			errFlag = true;
		}
		if (!planningJson.containsKey(ITEM_ESTIMATE_TEST)
				|| planningJson.getString(ITEM_ESTIMATE_TEST).equals("null")) {
			errMsg = errMsg + "実行工数が記載されていません(「実行工数」のヘッダー行を追加して、予定実行工数を入力してください) / ";
			errFlag = true;
		}
		if (errFlag) {
			errMsg = errMsg.substring(0, errMsg.length() - 3);
			System.out.println("[WARN] " + planningJson.getInt(ITEM_ID) + ":" + errMsg);
			return true;
		} else {
			return false;
		}
	}

	private void accumulateDesign(JSONObject planningJson, Map<Integer, RedmineDto> funbugMap) {

		addWord = "_design";

		String subject = EVA_TASK_DESGIN_SUBJECT;
		if (SUBJECT_MERGE_FLAG) {
			subject = mergeSubject(subject, funbugMap.get(planningJson.getInt(ITEM_ID)).getSubject());
		}
		base.accumulate(ITEM_SUBJECT, subject);
		base.accumulate(ITEM_ESTIMATED_HOURS, planningJson.getDouble(ITEM_ESTIMATE_DESIGN));

		// Task ClassifictionをEvaluatingで入力
		JSONObject taskClassification = new JSONObject();
		taskClassification.accumulate(ITEM_ID, TASK_CLASS_ITEM_NO);
		taskClassification.accumulate(ITEM_VALUE, TASK_CLASS_EVA);
		customFieldList.add(taskClassification);

		Calendar designedDate = Calendar.getInstance();
		designedDate.setTime(df.parse(planningJson.getString(ITEM_TEST_DESIGNED)));
		Calendar today = Calendar.getInstance();
		eDate.setTime(designedDate.getTime());
		sDate.setTime(dwc.calculateDayWork(designedDate, -2).getTime());
		// 設計完了予定の2営業日前がジョブ実行日より前の日になってしまうのであれば、ジョブ実行日を開始日にする
		if (today.compareTo(sDate) > 0) {
			sDate.setTime(today.getTime());
		}
	}

	private String mergeSubject(String subject, String parentSubject) {
		subject = subject + "_" + parentSubject;
		if (subject.length() > 255) {
			subject = subject.substring(0, 254);
		}
		return subject;
	}

	private void accumulateTesting(JSONObject planningJson, Map<Integer, RedmineDto> funbugMap) {

		addWord = "_testing";

		String subject = EVA_TASK_TEST_SUBJECT;
		if (SUBJECT_MERGE_FLAG) {
			subject = mergeSubject(subject, funbugMap.get(planningJson.getInt(ITEM_ID)).getSubject());
		}
		base.accumulate(ITEM_SUBJECT, subject);
		base.accumulate(ITEM_ESTIMATED_HOURS, planningJson.getDouble(ITEM_ESTIMATE_TEST));

		// Task ClassifictionをEvaluatingで入力
		JSONObject taskClassification = new JSONObject();
		taskClassification.accumulate(ITEM_ID, TASK_CLASS_ITEM_NO);
		taskClassification.accumulate(ITEM_VALUE, TASK_CLASS_EVA);
		customFieldList.add(taskClassification);

		Calendar testedDate = Calendar.getInstance();
		testedDate.setTime(df.parse(planningJson.getString(ITEM_TESTED)));
		Calendar designedDate = Calendar.getInstance();
		designedDate.setTime(df.parse(planningJson.getString(ITEM_TEST_DESIGNED)));
		Calendar confirmedDate = Calendar.getInstance();
		confirmedDate.setTime(df.parse(planningJson.getString(ITEM_CONFIRMED)));
		eDate.setTime(testedDate.getTime());

		// 開発完了予定とテスト設計完了予定の遅い方の翌営業日が開始日に設定される
		if (designedDate.compareTo(confirmedDate) > 0) {
			sDate.setTime(dwc.calculateDayWork(designedDate, 1).getTime());
		} else {
			sDate.setTime(dwc.calculateDayWork(confirmedDate, 1).getTime());
		}
	}

	private void accumulateAdditional(JSONObject planningJson, Map<Integer, RedmineDto> funbugMap) {

		addWord = "_additional";

		String subject = EVA_TASK_ADDITIONAL_SUBJECT;
		if (SUBJECT_MERGE_FLAG) {
			subject = mergeSubject(subject, funbugMap.get(planningJson.getInt(ITEM_ID)).getSubject());
		}
		base.accumulate(ITEM_SUBJECT, subject);
		if (!planningJson.containsKey(ITEM_ESTIMATE_ADDITIONAL)
				|| planningJson.getString(ITEM_ESTIMATE_ADDITIONAL).equals("")
				|| planningJson.getString(ITEM_ESTIMATE_ADDITIONAL).equals("null")) {
			base.accumulate(ITEM_ESTIMATED_HOURS, 0);
		} else {
			base.accumulate(ITEM_ESTIMATED_HOURS, planningJson.getDouble(ITEM_ESTIMATE_ADDITIONAL));
		}

		// Task ClassifictionをEvaluatingで入力
		JSONObject taskClassification = new JSONObject();
		taskClassification.accumulate(ITEM_ID, TASK_CLASS_ITEM_NO);
		taskClassification.accumulate(ITEM_VALUE, TASK_CLASS_ADD_EVA);
		customFieldList.add(taskClassification);

		Calendar testedDate = Calendar.getInstance();
		testedDate.setTime(df.parse(planningJson.getString(ITEM_TESTED)));
		Calendar evaDeadDate = Calendar.getInstance();
		evaDeadDate.setTime(df.parse(EVA_DEAD_DATE));
		sDate.setTime(dwc.calculateDayWork(testedDate, 1).getTime());
		eDate.setTime(evaDeadDate.getTime());
	}

	private void accumulateCommon(JSONObject planningJson, Map<String, Integer> userMap,
			Map<Integer, Integer> projectIdMap) {
		base.accumulate("parent_issue_id", planningJson.getInt(ITEM_ID));
		base.accumulate("project_id", projectIdMap.get(planningJson.getInt(ITEM_ID)));
		base.accumulate("assigned_to_id", userMap.get(planningJson.getString(ITEM_EVALUATOR)));
	}

	private void accumulateBase() {
		json.clear();
		base.clear();
		customFieldList.clear();
		addWord = "";
		sDate.clear();
		eDate.clear();

		// デフォの値を入力
		base.accumulate(STATUS_ID, STATUS_NEW_ID); // Status-New
		base.accumulate(TRACKER_ID, TRACKER_TASK_ID); // Tracekr-Task
		base.accumulate(PRIORITY_ID, PRIORITY_NORMAL_ID); // Priority-Normal

		// SeverityをNormalで入力
		JSONObject severity = new JSONObject();
		severity.accumulate(ITEM_ID, SEVERITY_ITEM_NO);
		severity.accumulate(ITEM_VALUE, SEVERITY_NORMAL);
		customFieldList.add(severity);

		// Issue categoryをTaskで入力
		JSONObject issueCategory = new JSONObject();
		issueCategory.accumulate(ITEM_ID, CATEGORY_ITEM_NO);
		issueCategory.accumulate(ITEM_VALUE, CATEGORY_TASK);
		customFieldList.add(issueCategory);
	}

	private String lastAccumulate(JSONObject planningJson) {
		// 期日より開始日の方が遅い場合、開始日を期日と一緒にする
		if (sDate.compareTo(eDate) > 0) {
			sDate.setTime(eDate.getTime());
		}
		base.accumulate(ITEM_START_DATE, df.format(sDate.getTime(), FlexibleDateFormatter.HYPHEN));
		base.accumulate(ITEM_DUE_DATE, df.format(eDate.getTime(), FlexibleDateFormatter.HYPHEN));
		base.accumulate(ITEM_CUSTOM_FIELDS, customFieldList);
		json.accumulate(ITEM_ISSUE, base);
		String fileName = planningJson.getInt(ITEM_ID) + addWord;
		wt.writeText(JSON_PATH, fileName + ".json", json.toString(), DEFAULT_JSON_ENCODE);
		return generateBat(JSON_PATH, fileName, API_KEY_QA_ADMIN) + "\n";
	}

	// Batファイルの形に整形する
	private String generateBat(String path, String fileName, String apiKey) {
		String bat;
		bat = "ISSUE_ID_TYPE=" + fileName + "\n" + "HTTP_RESPONSE=`" + "curl -s -H \"Content-Type: application/json\""
				+ " -X POST " + " -d @" + toBashPath(path + fileName) + ".json" + " -H \"X-Redmine-API-Key: " + apiKey
				+ "\" " + "http://" + HOST + "/redmine/issues.json`\n"
				+ "createTicket \"${ISSUE_ID_TYPE}\" \"${HTTP_RESPONSE}\"";
		return bat;
	}

	private String toBashPath(String path) {
		path = path.replaceAll("\\\\", "/");
		return path;
	}

}
