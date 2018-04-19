package constant;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class RedmineConstants {

  // redmineデータベースとの接続情報
  public static String URL;// DBの接続先(ローカルでポートを開けて接続中)
  public static String USER = "XXXXX";// user名
  public static String PASS = "XXXXX";// PASSWORD

  public static String HOST;

  public static final boolean REAL = true;
  public static final boolean DUMMY = false;

  public static void environmentSetting(boolean flag) {
    if (flag) {
      // 本番環境用の設定
      URL = "XXXXX";
      HOST = "XXXXX";
      System.out.println("本番環境で実行します [host:" + HOST + "]");
    } else {
      // 検証環境用の設定
    	URL = "XXXXX";
        HOST = "XXXXX";
      System.out.println("検証環境で実行します [host:" + HOST + "]");
    }
  }


  // RedmineのIssueを見る時のURL
  public static final String ISSUE_URL = "http://hue-redmine/redmine/issues/";


  public static final String API_KEY_QA_ADMIN = "4e5293fa100b0ea21dfe90dba6db737f0f4f0cbd";


  static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");

  // クエリ取得の際の各列番号(Master)
  public static final int ISSUE_ID = 1;
  public static final int PARENT_ID = 2;
  public static final int CATEGORY = 3;
  public static final int LICENSE = 4;
  public static final int SUBSYSTEM = 5;
  public static final int SUBSYSTEM_IDENTIFIER = 6;

  // Tracker名称
  public static final String TASK = "Task(タスク)";
  public static final String BUG = "Bug";
  public static final String FUNCTION = "Function(機能開発)";
  public static final String SUPPORT = "Support(サポート)";

  // URL
  public static final String ISSUES = "http://hue-redmine/redmine/issues/";

  // ステータス名称
  public static final String NEW = "New(新規)";
  public static final String REMAND = "Remand(開発戻し)";
  public static final String DEVELOPING = "Developing(開発中)";
  public static final String DEVELOPED = "Developed(開発完了)";
  public static final String CONFIRMED = "Develop confirmed(開発確認済)";
  public static final String EVALUATING = "Evaluating(評価中)";
  public static final String TESTED = "Tested(テスト完了)";
  public static final String EVALUATED = "Evaluated(評価完了)";
  public static final String CLOSED = "Closed(完了)";
  public static final String PUTOFF = "PutOff(先送り)";
  public static final String PUTOFF_CLOSED = "PutOff closed(先送り完了)";
  public static final String DESTRUCTED = "Destructed(破棄)";
  public static final String RESOLVED = "Resolved(対応完了)";
  public static final String IN_PROGRESS = "In Progress(着手)";
  public static final String REVERTED = "Reverted(巻き戻し)";

  // 定型文
  public static final String APPROVED = "Approved(承認済)";
  public static final String NA = "N/A";


  // Task classification
  public static final String CLASSIFICATION_EVALUATING = "評価 / Evaluating";
  public static final String CLASSIFICATION_ADDITIONAL = "評価追加作業/Additional work(Eva)";

  // 評価タスク名
  public static final String EVA_TASK_DESGIN_KEYWORD = "設計";
  public static final String EVA_TASK_TEST_KEYWORD = "実行";
  public static final String EVA_TASK_ADDITIONAL_KEYWORD = "戻り対応";

  // 評価タスク名
  public static final String EVA_TASK_DESGIN_SUBJECT = "評価タスク[設計]/Evaluation task[Design]";
  public static final String EVA_TASK_TEST_SUBJECT = "評価タスク[実行]/Evaluation task[Testing]";
  public static final String EVA_TASK_ADDITIONAL_SUBJECT =
      "評価タスク[戻り対応]/Evaluation task[Additional]";


  // Test Design Status
  public static final String TEST_DESIGNING = "Test designing(テスト設計中)";
  public static final String TEST_DESIGNED = "Test designed(テスト設計完了)";
  public static final String TEST_REDESIGNING = "Test Redesigning(テスト設計修正中)";
  public static final String TEST_APPROVED = "Test Approved(承認済)";

  // Importance名称
  public static final String CRITICAL = "Critical";
  public static final String MAJOR = "Major";
  public static final String NORMAL = "Normal";
  public static final String MINOR = "Minor";

  public static final Map<String, Integer> STATUS_PRIORITY;
  static {
    TreeMap<String, Integer> map = new TreeMap<String, Integer>();
    map.put(NEW, 30);
    map.put(REMAND, 40);
    map.put(IN_PROGRESS, 45);
    map.put(DEVELOPING, 50);
    map.put(DEVELOPED, 60);
    map.put(RESOLVED, 65);
    map.put(CONFIRMED, 70);
    map.put(EVALUATING, 80);
    map.put(TESTED, 90);
    map.put(EVALUATED, 100);
    map.put(CLOSED, 110);
    map.put(PUTOFF, 120);
    map.put(PUTOFF_CLOSED, 130);
    map.put(DESTRUCTED, 140);
    STATUS_PRIORITY = Collections.unmodifiableMap(map);
  }
}
