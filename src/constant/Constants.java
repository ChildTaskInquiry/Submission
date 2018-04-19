package constant;

import java.io.File;

public class Constants {

	// 改行コード
	public static final String LINE_FEED = "LINE_FEED";
	// 空白コード
	public static final String BLANK = "BLANK";
	// 拡張子
	public static final String EXTENSION = ".csv";
	// 読み込みの文字コード
	public static final String DEFAULT_BASH_ENCODE = "UTF-8";
	public static final String DEFAULT_SQL_ENCODE = "UTF-8";
	public static final String DEFAULT_CSV_CODE = "Windows-932";
	// 書き込みの文字コード
	public static final String WRITE_CODE = "Windows-932";

	//デバック時変更箇所
	public static final String JSON_PATH = "E:\\P_PersonalGit\\Submission\\json\\";
	public static final String EXCEL_FILE_PATH = "E:\\P_PersonalGit\\Submission\\file\\";
	public static final String SOURCE_CMD = "source C:/bash/functions.sh\n\n";
	public static final String BASH_PATH = "E:\\P_PersonalGit\\Submission\\bash\\";
	public static final String BAT_FILE_PATH = "E:\\P_PersonalGit\\Submission\\bash\\manhour-creater.bat";
	public static final String SQL_PATH = "E:\\P_PersonalGit\\Submission\\sql\\";

	public static final String BASH_NAME = "manhour-creater.sh";

	// ファイル取得先のパス
	// "C:\\Users\\Kira\\Documents\\作業用フォルダ\\toolList\\マニュアルリスト作成ツール\\" 家テスト用
	// "C:\\Users\\works\\Documents\\ツールフォルダ\\マニュアルリスト作成ツール\\" 会社テスト用
	// getCurrentPath() 実行用"C:\\Users\\works\\Downloads\\"
	public static final String CURRENT_PATH = getCurrentPath();

	// カレントパスを読み取るメソッド
	private static String getCurrentPath() {
		String cp = System.getProperty("java.class.path");
		String fs = System.getProperty("file.separator");

		String acp = (new File(cp)).getAbsolutePath();
		int p, q;
		for (p = 0; (q = acp.indexOf(fs, p)) >= 0; p = q + 1)
			;

		return acp.substring(0, p);
	}
}
