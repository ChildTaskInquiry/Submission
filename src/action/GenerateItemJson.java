package action;

import static constant.JsonConstants.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dto.JsonItemDto;
import dto.RedmineDto;
import net.sf.json.JSONObject;

public class GenerateItemJson {
	private static SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");

	private static String replaceTimestamp(String format) {
		format = format.replaceAll(" ", "T");
		format = format + "Z";
		return format;
	}

	public List<JSONObject> accumulateBase(Map<Integer, RedmineDto> issueMap, JsonItemDto jsonItemDto) {

		List<JSONObject> itemJsonList = new ArrayList<JSONObject>();

		for (Map.Entry<Integer, RedmineDto> issue : issueMap.entrySet()) {

			JSONObject itemJson = new JSONObject();
			Integer key = issue.getKey();
			RedmineDto value = issue.getValue();

			// デフォルトフィールドの情報追加
			itemJson.accumulate(ITEM_ID, key);
			itemJson.accumulate(ITEM_PARENT_ID, value.getParent_id());
			itemJson.accumulate(ITEM_SUBJECT, value.getSubject());
			itemJson.accumulate(ITEM_TRACKER, value.getTracker());
			itemJson.accumulate(ITEM_STATUS, value.getStatus());
			itemJson.accumulate(ITEM_PROJECT, value.getProject());
			itemJson.accumulate(ITEM_LICENSE, value.getLicense());
			itemJson.accumulate(ITEM_SUBSYSTEM, value.getSubsystem());
			itemJson.accumulate(ITEM_AUTHOR, value.getAuthor());
			itemJson.accumulate(ITEM_ASSIGNED_TO, value.getAssignedTo());
			itemJson.accumulate(ITEM_PRIORITY, value.getPriority());
			itemJson.accumulate(ITEM_START_DATE, value.getStart_date());
			itemJson.accumulate(ITEM_DUE_DATE, value.getDue_date());
			itemJson.accumulate(ITEM_CREATED_ON, replaceTimestamp(sdf.format(value.getCreated_on())));
			itemJson.accumulate(ITEM_UPDATED_ON, replaceTimestamp(sdf.format(value.getUpdated_on())));

			/* カスタムフィールドの追加項目(単一項目) */
			for (Map.Entry<String, Map<Integer, String>> item : jsonItemDto.getItemMap().entrySet()) {
				if (item.getValue().containsKey(key)) {
					itemJson.accumulate(item.getKey(), item.getValue().get(key));
				} else {
					itemJson.accumulate(item.getKey(), null);
				}
			}

			/* カスタムフィールドの追加項目(配列項目) */
			for (Map.Entry<String, Map<Integer, List<String>>> item : jsonItemDto.getArrayItemMap().entrySet()) {
				if (item.getValue().containsKey(key)) {
					itemJson.accumulate(item.getKey(), item.getValue().get(key));
				} else {
					itemJson.accumulate(item.getKey(), null);
				}
			}

			/* カスタムフィールドの追加項目(配列項目)で、当チケットか親チケットにあれば結合するメソッド */
			for (Map.Entry<String, Map<Integer, List<String>>> item : jsonItemDto.getArrayBothItemMap().entrySet()) {
				if (item.getValue().containsKey(key)) {
					itemJson.accumulate(item.getKey(), item.getValue().get(key));
				} else if (item.getValue().containsKey(value.getParent_id())) {
					itemJson.accumulate(item.getKey(), item.getValue().get(value.getParent_id()));
				} else {
					itemJson.accumulate(item.getKey(), null);
				}
			}

			/* カスタムフィールドの追加項目(親チケット情報の単一項目) */
			for (Map.Entry<String, Map<Integer, String>> item : jsonItemDto.getParentItemMap().entrySet()) {
				if (item.getValue().containsKey(value.getParent_id())) {
					itemJson.accumulate(item.getKey(), item.getValue().get(value.getParent_id()));
				} else {
					itemJson.accumulate(item.getKey(), null);
				}
			}

			itemJsonList.add(itemJson);
		}
		return itemJsonList;
	}
}
