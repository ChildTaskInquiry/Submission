package action;

import static constant.JsonConstants.*;
import static constant.RedmineConstants.*;

import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

public class FilterJson {

	public List<JSONObject> filterJson(List<JSONObject> itemJsonList) {
		for (Iterator<JSONObject> row = itemJsonList.iterator(); row.hasNext();) {
			JSONObject itemJson = row.next();
			// 出力条件に満たしていれば
			if (!isConditional(itemJson)) {
				row.remove();
			}
		}
		return itemJsonList;
	}

	private static boolean isConditional(JSONObject itemJson) {
		if (itemJson.getString(ITEM_TASK_CLASSIFICATION).equals(CLASSIFICATION_EVALUATING)
				|| itemJson.getString(ITEM_TASK_CLASSIFICATION).equals(CLASSIFICATION_ADDITIONAL)) {
			if (itemJson.containsKey(ITEM_PARENT_ID)) {
				return true;
			}
		}
		return false;

	}
}
