package dao;

import static constant.Constants.*;
import static constant.JenkinsConstants.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class SqlDao {
	// 指定のファイルを取得する場合
	public String getSQL(String path, String fileName, String encode) {

		// 指定のファイルをBufferedReaderで読み込み
		File readFile = new File(path + fileName);
		BufferedReader br;
		if (encode == null) {
			encode = DEFAULT_SQL_ENCODE;
		}
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(readFile), encode));
			String sql = changeSQLFormat(br);
			System.out.println(sql);
			return sql;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String changeSQLFormat(BufferedReader br) {
		String line;
		String string = "";
		try {
			while ((line = br.readLine()) != null) {
				if (line.indexOf("--") >= 0) {// SQLエスケープ文字があったら
					line = line.substring(0, line.indexOf("--"));// エスケープ文字が始まるまで取得。(エスケープ文字以降の文字は取得しない)
				}
				line = line.replace("\\t", " ");// タブ文字の削除
				// :があるときの引数の変換の暫定運用。 TODO 本格的に引数の設定をしたい場合、ロジックに甘さがあり
				if (line.indexOf(":") >= 0) {
					String word = line.substring(line.indexOf(":") + 1, line.length());
					if (JENKINS_SQL_ARGUMENT != null && JENKINS_SQL_ARGUMENT.containsKey(word)) {
						line = line.replace(":" + word, JENKINS_SQL_ARGUMENT.get(word));
					}
				}
				string = string + line + " ";
			}
			return string;
		} catch (IOException e) {
			System.out.println("IOException:" + e);
		}
		return null;
	}
}
