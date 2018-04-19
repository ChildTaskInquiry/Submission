package action;

import static constant.ExcelConstants.*;
import static constant.JsonConstants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.FlexibleDateFormatter;

import net.sf.json.JSONObject;

public class GeneratePlanningTicketJson {
	private Map<Integer, String> headerMap = new TreeMap<Integer, String>();
	private List<JSONObject> itemJsonList = new ArrayList<JSONObject>();
	private JSONObject itemJson;
	private int idCellNum;
	private FlexibleDateFormatter df = new FlexibleDateFormatter();

	public List<JSONObject> generatePlanningTicketJson(Workbook planningExcel) {
		// 全シート分読み込み
		for (int x = 0; x < planningExcel.getNumberOfSheets(); x++) {
			Sheet sheet = planningExcel.getSheetAt(x);
			// 各行を読み込み
			for (int i = 0; i < 500; i++) {
				if (sheet.getRow(i) != null) {
					Row row = sheet.getRow(i);
					if (headerMap.size() == 0) {
						// ヘッダー行を取得出来るまでは、ヘッダー行を探す
						judgeHeaderMap(row);
					} else {
						// ヘッダー行以降のものは、リストとして追加する
						generateItemJson(row);
					}
				}
			}
			headerMap.clear();
		}
		System.out.println("Count of create target tickets : " + itemJsonList.size());
		return itemJsonList;
	}

	private void generateItemJson(Row row) {
		itemJson = new JSONObject();
		if (row.getCell(idCellNum) != null && isNumberCell(row.getCell(idCellNum))
				&& !row.getCell(idCellNum).toString().equals("")) {
			for (Entry<Integer, String> header : headerMap.entrySet()) {
				itemJson.accumulate(header.getValue(), getCellValue(row, header.getKey()));
			}
			System.out.println(itemJson);
			itemJsonList.add(itemJson);
		}
	}

	private boolean isNumberCell(Cell cell) {
		try {
			cell.getNumericCellValue();
			return true;
		} catch (IllegalStateException e) {
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	private Object getCellValue(Row row, Integer key) {
		if (row.getCell(key) == null) {
			return null;
		}
		Cell cell = row.getCell(key);
		try {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					return df.format(cell.getDateCellValue(), FlexibleDateFormatter.HYPHEN);
				}
				return cell.getNumericCellValue();
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
			default:
				return cell.getStringCellValue();
			}
		} catch (IllegalStateException e) {
			return cell.getNumericCellValue();
		}
	}

	private void judgeHeaderMap(Row row) {
		boolean idFlag = false;
		boolean designFlag = false;
		for (int j = 0; j < 100; j++) {
			if (row.getCell(j) != null) {
				Cell cell = row.getCell(j);
				String value = cell.toString().replaceAll("\\*", "").replaceAll("\\n", "").replaceAll("　", "")
						.replaceAll(" ", "").toLowerCase();
				if (value.equals(ITEM_ID)) {
					idFlag = true;
					idCellNum = j;
				}
				if ((HEADER_TRANSLATE_MAP.containsKey(value)
						&& HEADER_TRANSLATE_MAP.get(value).equals(ITEM_TEST_DESIGNED))
						|| value.equals(ITEM_TEST_DESIGNED)) {
					designFlag = true;
				}
				if (idFlag && designFlag) {
					generateHeaderMap(row);
					break;
				}
			}
		}
	}

	private void generateHeaderMap(Row row) {
		for (int j = 0; j < 100; j++) {
			if (row.getCell(j) != null && !row.getCell(j).toString().equals("")) {
				boolean existFlag = false;
				String value = row.getCell(j).toString().replaceAll("\\*", "").replaceAll("\\n", "").replaceAll("　", "")
						.replaceAll(" ", "").toLowerCase();
				if (HEADER_TRANSLATE_MAP.containsKey(value)) {
					value = HEADER_TRANSLATE_MAP.get(value);
				}
				for (Entry<Integer, String> header : headerMap.entrySet()) {
					if (header.getValue().equals(value)) {
						existFlag = true;
						break;
					}
				}
				if (!existFlag) {
					headerMap.put(j, value);
				}
			}
		}
	}

}
