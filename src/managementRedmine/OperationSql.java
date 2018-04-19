package managementRedmine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import dto.RedmineDto;

public class OperationSql {

	// 複数行にまたがって取得している情報を配列化するジョブ
	public Map<Integer, List<String>> groupBy(List<RedmineDto> rDtos, String split) {
		Map<Integer, List<String>> items = new TreeMap<Integer, List<String>>();
		List<String> values = new ArrayList<String>();
		Integer preKey = null;
		for (RedmineDto rDto : rDtos) {
			if (preKey == null) {
				preKey = rDto.getKey();
			}
			if (preKey.equals(rDto.getKey())) {
				values.add(rDto.getValue());
			} else {
				items.put(preKey, values);
				values = new ArrayList<String>();
				preKey = rDto.getKey();
				values.add(rDto.getValue());
			}
		}
		items.put(preKey, values);
		return items;
	}

}
