package constant;

import static constant.JsonConstants.*;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class ExcelConstants {

  public static final String EXCEL_IN_PATH =
      "C:\\Users\\works\\.jenkins\\jobs\\Update_Process_Result\\workspace\\";
  public static final String EXCEL_IN_FILENAME = "Process_Result_Sheet";
  public static final String EXCEL_TEMPLATE_FILENAME = "LJS_template";
  public static final String EXCEL_OUT_PATH =
      "C:\\Users\\works\\.jenkins\\jobs\\Update_LaunchJudgementSheet\\workspace\\";
  public static final String EXCEL_OUT_FILENAME = "Launch_Judgement_Sheet";
  public static final String EXCEL_EXTENTION = ".xls";

  public static final Map<String, String> HEADER_TRANSLATE_MAP;
  static {
    TreeMap<String, String> map = new TreeMap<String, String>();
    map.put("トラッカー", "tracker");
    map.put("タイトル", "title");
    map.put("ステータス", "status");
    map.put("テスト設計", "testdesignstatus");
    map.put("開発者", "developer");
    map.put("評価者", ITEM_EVALUATOR);
    map.put("evaluator", ITEM_EVALUATOR);
    map.put("優先度", "priority");
    map.put("開発確認完了", ITEM_CONFIRMED);
    map.put("開発確認完了日", ITEM_CONFIRMED);
    map.put(ITEM_TEST_DESIGNED_JA, ITEM_TEST_DESIGNED);
    map.put("テスト設計完了日", ITEM_TEST_DESIGNED);
    map.put("testdesigned", ITEM_TEST_DESIGNED);
    map.put("テスト完了", ITEM_TESTED);
    map.put("テスト完了日", ITEM_TESTED);
    map.put("設計工数", ITEM_ESTIMATE_DESIGN);
    map.put("実行工数", ITEM_ESTIMATE_TEST);
    map.put("戻り工数", ITEM_ESTIMATE_ADDITIONAL);
    HEADER_TRANSLATE_MAP = Collections.unmodifiableMap(map);
  }
}
