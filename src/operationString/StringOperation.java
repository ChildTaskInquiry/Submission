package operationString;

import static constant.Constants.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringOperation {

	// 空白文字の削除関数
	public String changeNoBlank(String string) {
		if (string != null) {
			string = string.replaceAll(BLANK, "");
			string = string.replaceAll(" ", "");
			string = string.replaceAll("　", "");
			return string;
		} else {
			return "";
		}
	}

	// 第1引数の中の、第2引数から第3引数の間の文字列を取得
	public String getZone(String string, String startElement, String endElement) {
		if (string.indexOf(startElement) >= 0 && string.indexOf(endElement) > string.indexOf(startElement)) {
			String result = string.substring(string.indexOf(startElement) + startElement.length());
			result = result.substring(0, result.indexOf(endElement));
			return result;
		} else {
			return "";
		}
	}

	// 改行コードが何もないかどうかの真偽
	public boolean isNoFeed(String element) {
		if (element != null) {
			element.replaceAll(LINE_FEED, "");
			if (element.equals("")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// wordの中から、removeWordsを取り除いて返す
	public String removeWords(String word, List<String> removeWords) {
		if (word != null) {
			if (removeWords != null) {
				for (String removeWord : removeWords) {
					word = word.replaceAll(removeWord, "");
				}
			}
			return word;
		} else {
			return null;
		}
	}

	// 第1引数の中に第2引数の要素が何個入っているかを返すメソッド
	public int countOfCharacters(String setString, String elementString) {
		Pattern p = Pattern.compile(elementString);
		Matcher m = p.matcher(setString);
		int count = 0;
		int s = 0;
		while (m.find(s)) {
			count++;
			s = m.end();
		}
		return count;
	}

	// nameにincludeを含むかどうかの判断。
	public boolean isInclude(String name, String include) {
		if (name != null && include != null) {
			if (name.length() >= include.length()) {
				if (name.indexOf(include) >= 0) {
					return true;
				}
			}
		}
		return false;
	}

	// nameにincludeを含むかどうかの判断。厳密に判断。（空白での検索はfalse）
	public boolean isStrictInclude(String name, String include) {
		if (name != null && include != null) {
			if (name.length() >= include.length() && include.length() > 0) {
				if (name.indexOf(include) >= 0) {
					return true;
				}
			}
		}
		return false;
	}

	//
	public String replaceWords(String name, Map<String, String> replaceWords) {
		if (name != null) {
			if (replaceWords != null) {
				for (Map.Entry<String, String> entry : replaceWords.entrySet()) {
					name = name.replaceAll(entry.getKey(), entry.getValue());
				}
			}
			return name;
		} else {
			return null;
		}
	}

	// 文字列stringの最初の文字がindexだったらtrue、それ以外だったらfalse
	public boolean isIndexFirst(String string, String index) {
		int strLng = string.length();
		int indLng = index.length();
		if (strLng >= indLng) {
			if (string.substring(0, indLng).equals(index)) {
				return true;
			}
		}
		return false;
	}

	// ヌルぽ対策用
	public String escapeNull(String string) {
		if (string == null) {
			return "";
		} else {
			return string;
		}
	}

	// ヌルにしちゃうやつ
	public String takeNull(String string) {
		if (string != null) {
			if (string.equals("")) {
				return null;
			}
			return string;
		} else {
			return string;
		}
	}

	public String validName(String vpName) {
		if (vpName.indexOf("*") >= 0) {
			vpName = vpName.replaceAll("\\*", "");
		}
		if (vpName.indexOf(" ") >= 0) {
			vpName = vpName.replaceAll(" ", "_");
		}
		return vpName;
	}
}
