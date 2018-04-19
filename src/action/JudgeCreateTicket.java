package action;

import static constant.JsonConstants.*;
import static constant.RedmineConstants.*;

import java.util.List;
import java.util.Map;

import dto.RedmineDto;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JudgeCreateTicket {

	private Map<Integer, RedmineDto> funbugMap;

	public String judgeCreateTicekt(List<JSONObject> planningJsonList, List<JSONObject> taskJsonList,
			Map<String, Integer> userMap, Map<Integer, Integer> projectIdMap, Map<Integer, RedmineDto> funbugMap) {

		this.funbugMap = funbugMap;

		planningJsonList = addMatchingTaskJsonList(planningJsonList, taskJsonList);

		return extractBat(planningJsonList, userMap, projectIdMap);
	}

	private String extractBat(List<JSONObject> planningJsonList, Map<String, Integer> userMap,
			Map<Integer, Integer> projectIdMap) {
		String bat = "";
		WriteCreateTicketJson wcj = new WriteCreateTicketJson();
		for (JSONObject planningJson : planningJsonList) {

			// テスト設計用のチケットを起票するかどうかの条件
			if (isCreateTestDesing(planningJson)) {
				bat = bat + wcj.writeCreateDesignTicketJson(planningJson, userMap, projectIdMap, funbugMap);
			}

			// テスト実行用のチケットを起票するかどうかの条件
			if (isCreateTesting(planningJson)) {
				bat = bat + wcj.writeCreateTestingTicketJson(planningJson, userMap, projectIdMap, funbugMap);
			}

			// 戻りチケットを起票するかどうかの条件
			if (isCreateAdditional(planningJson)) {
				bat = bat + wcj.writeCreateAdditionalTicketJson(planningJson, userMap, projectIdMap, funbugMap);
			}
		}
		return bat;
	}

	private Boolean isCreateBaseJudge(JSONObject planningJson) {

		// FunctionでもBugでもなければ、記票しないし、ステータスがPutOff/Destructedでも起票しない
		if (funbugMap.containsKey(planningJson.getInt(ITEM_ID))) {
			String status = funbugMap.get(planningJson.getInt(ITEM_ID)).getStatus();
			if (STATUS_PRIORITY.get(status) >= 120) {
				return false;
			}
		} else {
			return false;
		}

		// 評価タスクが一件もなければ起票
		if (planningJson.getJSONArray(ITEM_EVA_TASK).size() == 0) {
			return true;
		}

		// どちらでもなければ詳細判定を行う必要がある
		return null;
	}

	private boolean isCreateAdditional(JSONObject planningJson) {

		// 評価タスクが一件もない、かつＴｒａckerがFunction or Bugであれば起票
		Boolean isCreate = isCreateBaseJudge(planningJson);
		if (isCreate != null) {
			return isCreate;
		}

		// 評価タスク内の詳細判定
		boolean openFlag = false;
		boolean existFlag = false;
		for (int i = 0; i < planningJson.getJSONArray(ITEM_EVA_TASK).size(); i++) {
			JSONObject evaTaskJson = planningJson.getJSONArray(ITEM_EVA_TASK).getJSONObject(i);

			// 評価分類がAdditionalで、かつSubjectが戻りのキーなら
			if (evaTaskJson.getString(ITEM_TASK_CLASSIFICATION).equals(CLASSIFICATION_ADDITIONAL)
					&& evaTaskJson.getString(ITEM_SUBJECT).indexOf(EVA_TASK_ADDITIONAL_KEYWORD) >= 0) {
				existFlag = true;

				if (STATUS_PRIORITY.get(evaTaskJson.getString(ITEM_STATUS)) <= 90) {
					openFlag = true;
				}
			}
		}

		// 戻りチケットが存在するが、オープンの戻りチケットが存在する場合のみ、記票しない
		if (existFlag && openFlag) {
			return false;
		} else {
			return true;
		}
	}

	private boolean isCreateTesting(JSONObject planningJson) {

		// 評価タスクが一件もない、かつＴｒａckerがFunction or Bugであれば起票
		Boolean isCreate = isCreateBaseJudge(planningJson);
		if (isCreate != null) {
			return isCreate;
		}

		// 評価タスク内の詳細判定
		boolean testedFlag = false;
		boolean openFlag = false;
		boolean existFlag = false;
		for (int i = 0; i < planningJson.getJSONArray(ITEM_EVA_TASK).size(); i++) {
			JSONObject evaTaskJson = planningJson.getJSONArray(ITEM_EVA_TASK).getJSONObject(i);

			// 評価分類がEvaluatingで、かつSubjectがテスト実行のキーなら
			if (evaTaskJson.getString(ITEM_TASK_CLASSIFICATION).equals(CLASSIFICATION_EVALUATING)
					&& evaTaskJson.getString(ITEM_SUBJECT).indexOf(EVA_TASK_TEST_KEYWORD) >= 0) {
				existFlag = true;

				if (STATUS_PRIORITY.get(evaTaskJson.getString(ITEM_PARENT_STATUS)) >= 90) {
					testedFlag = true;
				}
				if (STATUS_PRIORITY.get(evaTaskJson.getString(ITEM_STATUS)) <= 90) {
					openFlag = true;
				}
			}
		}

		// テストチケットが存在するが、親チケットのテータスがテスト完了しているもしくはオープンのテスト実行チケットが存在する場合のみ、記票しない
		if (existFlag && (testedFlag || openFlag)) {
			return false;
		} else {
			return true;
		}
	}

	private boolean isCreateTestDesing(JSONObject planningJson) {

		// 評価タスクが一件もない、かつＴｒａckerがFunction or Bugであれば起票
		Boolean isCreate = isCreateBaseJudge(planningJson);
		if (isCreate != null) {
			return isCreate;
		}

		// 評価タスク内の詳細判定
		boolean designedFlag = false;
		boolean openFlag = false;
		boolean existFlag = false;
		for (int i = 0; i < planningJson.getJSONArray(ITEM_EVA_TASK).size(); i++) {
			JSONObject evaTaskJson = planningJson.getJSONArray(ITEM_EVA_TASK).getJSONObject(i);
			// 評価分類がEvaluatingで、かつSubjectが設計のキーなら
			if (evaTaskJson.getString(ITEM_TASK_CLASSIFICATION).equals(CLASSIFICATION_EVALUATING)
					&& evaTaskJson.getString(ITEM_SUBJECT).indexOf(EVA_TASK_DESGIN_KEYWORD) >= 0) {
				existFlag = true;

				if (evaTaskJson.getString(ITEM_PARENT_TEST_DESIGN_STATUS).equals(TEST_DESIGNED)
						|| evaTaskJson.getString(ITEM_PARENT_TEST_DESIGN_STATUS).equals(TEST_APPROVED)) {
					designedFlag = true;
				}
				if (STATUS_PRIORITY.get(evaTaskJson.getString(ITEM_STATUS)) <= 90) {
					openFlag = true;
				}
			}
		}
		// 設計チケットが存在するが、親チケットのテスト設計ステータスが設計完了しているもしくはオープンの設計チケットが存在する場合のみ、記票しない
		if (existFlag && (designedFlag || openFlag)) {
			return false;
		} else {
			return true;
		}
	}

	// planningsheetのID別に、対応する評価チケットを記録
	private List<JSONObject> addMatchingTaskJsonList(List<JSONObject> planningJsonList, List<JSONObject> taskJsonList) {
		for (JSONObject planningJson : planningJsonList) {
			JSONArray matchingTaskJsonList = new JSONArray();
			for (JSONObject taskJson : taskJsonList) {
				if (taskJson.getInt(ITEM_PARENT_ID) == planningJson.getInt(ITEM_ID)) {
					matchingTaskJsonList.add(taskJson);
				}
			}
			planningJson.accumulate(ITEM_EVA_TASK, matchingTaskJsonList);
		}
		return planningJsonList;
	}

}
