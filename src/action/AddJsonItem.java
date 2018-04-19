package action;

import static constant.JsonConstants.*;
import static constant.SqlConstants.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.PerformanceMeasurement;

import dao.RedmineDao;
import dto.JsonItemDto;

public class AddJsonItem {
	private static RedmineDao rdao = new RedmineDao();
	private static PerformanceMeasurement pm = new PerformanceMeasurement();
	private Map<String, Map<Integer, String>> itemMap = new TreeMap<String, Map<Integer, String>>();
	private Map<String, Map<Integer, List<String>>> arrayItemMap = new TreeMap<String, Map<Integer, List<String>>>();
	private Map<String, Map<Integer, List<String>>> arrayBothItemMap = new TreeMap<String, Map<Integer, List<String>>>();
	private Map<String, Map<Integer, String>> parentItemMap = new TreeMap<String, Map<Integer, String>>();

	public JsonItemDto addJsonItem() throws SQLException {
		try {
			itemMap.put(ITEM_TASK_CLASSIFICATION, getItem(TASK_CLASSIFICATION));
			parentItemMap.put(ITEM_PARENT_STATUS, getItem(STATUS));
			parentItemMap.put(ITEM_PARENT_TEST_DESIGN_STATUS, getItem(TEST_DESIGN_STATUS));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		}
		return new JsonItemDto(itemMap, arrayItemMap, arrayBothItemMap, parentItemMap);
	}

	private static Map<Integer, String> getItem(String selectSql) throws SQLException {
		Map<Integer, String> valueMap = new TreeMap<Integer, String>();
		try {
			valueMap = rdao.getCustomField(selectSql);
			pm.getPerformance("[DEBUG] load completed of " + selectSql + " ： ");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		}
		return valueMap;
	}

}
