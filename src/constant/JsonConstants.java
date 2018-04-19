package constant;

public class JsonConstants {

	public static final String DEFAULT_JSON_ENCODE = "UTF-8";

  /* Patchスケジュール取得API */
  public static final String PATCH_SCHEDULE_URL = "event";

  /* Index名 */
  public static final String INDEX_TYPE = "event";

  /* 各項目名 */
  public static final String ITEM_ID = "id";
  public static final String ITEM_ISSUE = "issue";
  public static final String ITEM_VALUE = "value";
  public static final String ITEM_PARENT_ID = "parentId";
  public static final String ITEM_SUBJECT = "subject";
  public static final String ITEM_TRACKER = "tracker";
  public static final String ITEM_PROJECT = "project";
  public static final String ITEM_STATUS = "status";
  public static final String ITEM_LICENSE = "license";
  public static final String ITEM_SUBSYSTEM = "author";
  public static final String ITEM_AUTHOR = "subsystem";
  public static final String ITEM_ASSIGNED_TO = "assigned_to";
  public static final String ITEM_PRIORITY = "priority";
  public static final String ITEM_TASK_CLASSIFICATION = "taskClassification";
  public static final String ITEM_START_DATE = "start_date";
  public static final String ITEM_DUE_DATE = "due_date";
  public static final String ITEM_CREATED_ON = "created_on";
  public static final String ITEM_UPDATED_ON = "updated_on";
  public static final String ITEM_EVA_TASK = "evaTasks";
  public static final String ITEM_PARENT_STATUS = "parentStatus";
  public static final String ITEM_PARENT_TEST_DESIGN_STATUS = "parentTestDesignStatus";
  public static final String ITEM_CUSTOM_FIELDS = "custom_fields";
  public static final String ITEM_ESTIMATED_HOURS = "estimated_hours";

  public static final String ITEM_EVALUATOR = "evaluater";
  public static final String ITEM_TEST_DESIGNED = "testdesinged";
  public static final String ITEM_TEST_DESIGNED_JA = "テスト設計完了";
  public static final String ITEM_TESTED = "testcomplete";
  public static final String ITEM_CONFIRMED = "developcomplete";


  public static final String ITEM_ESTIMATE_DESIGN = "designman-hour";
  public static final String ITEM_ESTIMATE_TEST = "testman-hour";
  public static final String ITEM_ESTIMATE_ADDITIONAL = "additionalman-hour";

  public static final int SEVERITY_ITEM_NO = 176;
  public static final String SEVERITY_NORMAL = "1395";

  public static final int CATEGORY_ITEM_NO = 162;
  public static final String CATEGORY_TASK = "1319";

  public static final int TASK_CLASS_ITEM_NO = 252;
  public static final String TASK_CLASS_EVA = "1980";
  public static final String TASK_CLASS_ADD_EVA = "2398";

  public static final int STATUS_NEW_ID = 1;
  public static final int TRACKER_TASK_ID = 5;
  public static final int PRIORITY_NORMAL_ID = 2;

  public static final String STATUS_ID = "status_id";
  public static final String TRACKER_ID = "tracker_id";
  public static final String PRIORITY_ID = "priority_id";




  // CAT登録有無
  public static final String REGISTERED = "Registered";
  public static final String UNREGISTERED = "Unregistered";
}
