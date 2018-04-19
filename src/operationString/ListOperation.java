package operationString;

import java.util.ArrayList;
import java.util.List;

public class ListOperation {

	private StringOperation strOpr = new StringOperation();

	// baseWordsの中で、重複している数を返す。removeWordsは考慮せずに。
	public int overlapSize(List<String> mainList, List<String> removeWords) {
		if (mainList != null) {
			List<String> baseWords = new ArrayList<String>(mainList);
			if (removeWords != null) {
				baseWords = removeWords(baseWords, removeWords);
			}
			return overlapSize(baseWords);
		} else {
			return -1;
		}
	}

	// baseWordsから、removeWordsを取り除いて返す。
	private List<String> removeWords(List<String> mainList, List<String> removeWords) {
		if (mainList != null) {
			List<String> baseWords = new ArrayList<String>(mainList);
			if (removeWords != null) {
				for (int i = 0; i < baseWords.size(); i++) {
					baseWords.set(i, strOpr.removeWords(baseWords.get(i), removeWords));
				}
			}
			return baseWords;
		} else {
			return null;
		}
	}

	// wordsの中で、重複している数を返す。
	private int overlapSize(List<String> mainList) {
		if (mainList != null) {
			List<String> baseWords = new ArrayList<String>(mainList);
			List<String> overlapWords = new ArrayList<String>();
			int cnt = 0;
			boolean flg = false;
			for (String baseWord : baseWords) {
				for (String overlapWord : overlapWords) {
					if (baseWord.equals(overlapWord)) {
						cnt++;
						flg = true;
						continue;
					}
				}
				if (flg) {
					flg = false;
				} else {
					overlapWords.add(baseWord);
				}
			}
			return cnt;
		} else {
			return -1;
		}
	}

	// baseWordsから、重複を除いたリストを返す。removeWordsは考慮せずに。
	public List<String> pureList(List<String> mainList, List<String> removeWords) {
		if (mainList != null) {
			List<String> baseWords = new ArrayList<String>(mainList);
			if (removeWords != null) {
				baseWords = removeWords(baseWords, removeWords);
			}
			return pureList(baseWords);
		} else {
			return null;
		}
	}

	// baseWordsから、重複を除いたリストを返す。
	private List<String> pureList(List<String> mainList) {
		if (mainList != null) {
			List<String> baseWords = new ArrayList<String>(mainList);
			List<String> overlapWords = new ArrayList<String>();
			boolean flg = false;
			String baseWord;
			int i = 0;
			int size = baseWords.size();
			while (i < size) {
				baseWord = baseWords.get(i);
				for (String overlapWord : overlapWords) {
					if (baseWord.equals(overlapWord)) {
						flg = true;
						baseWords.remove(i);
						size--;
						i--;
						continue;
					}
				}
				if (flg) {
					flg = false;
				} else {
					overlapWords.add(baseWord);
				}
				i++;
			}
			return baseWords;
		} else {
			return null;
		}
	}

}
