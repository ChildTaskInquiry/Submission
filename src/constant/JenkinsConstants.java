package constant;

import java.util.Map;

public class JenkinsConstants {

  public static boolean SUBJECT_MERGE_FLAG;
  public static String EVA_DEAD_DATE;

  // SQLの引数
  public static Map<String, String> JENKINS_SQL_ARGUMENT;

  // 作成する子タスクに親タスクの題名を付与するかのフラグ
  public static void jenkinsSetting(String subjectMergeFlag, String evaDeadDate) {
    SUBJECT_MERGE_FLAG = Boolean.valueOf(subjectMergeFlag);
    EVA_DEAD_DATE = evaDeadDate;
  }

  public static final String LAUNCH = "launch";
  public static final String PATCH = "patch";
  public static final String UPDATE = "update";
}
