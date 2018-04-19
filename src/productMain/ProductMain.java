package productMain;

import static constant.Constants.*;
import static constant.SqlConstants.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import action.AddJsonItem;
import action.ExecuteBatch;
import action.FilterJson;
import action.GenerateItemJson;
import action.GeneratePlanningTicketJson;
import action.JudgeCreateTicket;
import constant.JenkinsConstants;
import constant.RedmineConstants;
import dao.ExcelDao;
import dao.RedmineDao;
import dto.JsonItemDto;
import dto.RedmineDto;
import net.sf.json.JSONObject;
import writeText.WriteText;

public class ProductMain {
	private static RedmineDao rdao = new RedmineDao();

	// arg0→subjectMergeFlag、arg1→対象環境、arg2→Excelファイル
	// arg3→EVA_DEAD(2018-04-24)
	public static void main(String[] args) throws IOException {

		// 起動時引数の取得
		JenkinsConstants.jenkinsSetting(args[0], args[3]);

		// 本番環境か検証環境かを選択。false→検証環境 / true→本番環境
		RedmineConstants.environmentSetting(Boolean.valueOf(args[1]));

		// Excelファイルの絶対パス
		String targetExcelFile = args[2];

		try {
			List<JSONObject> planningJsonList = createPlanningJsonList(targetExcelFile);

			List<JSONObject> taskJsonList = createTaskJsonList();

			// 実行するバッチファイルの作成
			createExecuteBat(planningJsonList, jsonFiltering(taskJsonList));

			// batファイルの実行
			ExecuteBatch eb = new ExecuteBatch();
			eb.execute(BAT_FILE_PATH);
			System.out.println("[INFO] Create batch is completed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ExcelSheetから作成する対象の親チケットを抜き出す
	 *
	 * @param targetExcelFile
	 * @return
	 * @throws IOException
	 */
	private static List<JSONObject> createPlanningJsonList(String targetExcelFile) throws IOException {
		ExcelDao excelDao = new ExcelDao();
		GeneratePlanningTicketJson gpj = new GeneratePlanningTicketJson();
		List<JSONObject> planningJsonList = gpj.generatePlanningTicketJson(excelDao.inputExcel(targetExcelFile));
		return planningJsonList;
	}

	/**
	 * createTaskJsonListを作成するメソッド
	 *
	 * @return
	 * @throws SQLException
	 */
	private static List<JSONObject> createTaskJsonList() throws SQLException {
		JsonItemDto jsonItemDto;
		try {
			jsonItemDto = getRedmineItem();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		}
		// baseのIssue情報に、不随したい情報を追加
		Map<Integer, RedmineDto> taskMap = rdao.getRedmineList(ISSUE_TASK);
		GenerateItemJson gij = new GenerateItemJson();
		List<JSONObject> taskJsonList = gij.accumulateBase(taskMap, jsonItemDto);

		return taskJsonList;
	}

	/**
	 *
	 * @return
	 * @throws SQLException
	 */
	private static JsonItemDto getRedmineItem() throws SQLException {
		// Redmineから欲しい項目の情報を取得し、baseに追加するかたちに整える
		JsonItemDto jsonItemDto = new JsonItemDto();
		AddJsonItem aji = new AddJsonItem();
		try {
			jsonItemDto = aji.addJsonItem();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		}
		return jsonItemDto;
	}

	private static void createExecuteBat(List<JSONObject> planningJsonList, List<JSONObject> taskJsonList)
			throws SQLException {

		Map<String, Integer> userMap = new TreeMap<String, Integer>();
		Map<Integer, RedmineDto> funbugMap = new TreeMap<Integer, RedmineDto>();
		Map<Integer, Integer> projectIdMap = new TreeMap<Integer, Integer>();

		try {
			userMap = rdao.getStringMap(USERS);
			funbugMap = rdao.getRedmineList(ISSUE_FUNBUG);
			projectIdMap = rdao.getIdMap(PROJECT_ID);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		}

		// それぞれに起票するか否かの条件を付与
		JudgeCreateTicket jct = new JudgeCreateTicket();

		String bat = jct.judgeCreateTicekt(planningJsonList, taskJsonList, userMap, projectIdMap, funbugMap);
		bat = SOURCE_CMD + bat;
		WriteText wt = new WriteText();
		wt.writeText(BASH_PATH, BASH_NAME, bat, DEFAULT_BASH_ENCODE);
	}

	/**
	 * Jsonから、条件に合致するレコードだけ抽出
	 *
	 * @param taskJsonList
	 * @return
	 */
	private static List<JSONObject> jsonFiltering(List<JSONObject> taskJsonList) {
		FilterJson fj = new FilterJson();
		taskJsonList = fj.filterJson(taskJsonList);
		return taskJsonList;
	}
}
