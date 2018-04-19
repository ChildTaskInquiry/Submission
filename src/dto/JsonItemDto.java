package dto;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class JsonItemDto {

	private Map<String, Map<Integer, String>> itemMap = new TreeMap<String, Map<Integer, String>>();
	private Map<String, Map<Integer, List<String>>> arrayItemMap = new TreeMap<String, Map<Integer, List<String>>>();
	private Map<String, Map<Integer, List<String>>> arrayBothItemMap = new TreeMap<String, Map<Integer, List<String>>>();
	private Map<String, Map<Integer, String>> parentItemMap = new TreeMap<String, Map<Integer, String>>();

	public JsonItemDto(Map<String, Map<Integer, String>> itemMap, Map<String, Map<Integer, List<String>>> arrayItemMap,
			Map<String, Map<Integer, List<String>>> arrayBothItemMap, Map<String, Map<Integer, String>> parentItemMap) {
		this.itemMap = itemMap;
		this.arrayItemMap = arrayItemMap;
		this.arrayBothItemMap = arrayBothItemMap;
		this.parentItemMap = parentItemMap;
	}

	public JsonItemDto() {
		super();
	}

	public Map<String, Map<Integer, String>> getItemMap() {
		return itemMap;
	}

	public Map<String, Map<Integer, List<String>>> getArrayItemMap() {
		return arrayItemMap;
	}

	public Map<String, Map<Integer, List<String>>> getArrayBothItemMap() {
		return arrayBothItemMap;
	}

	public Map<String, Map<Integer, String>> getParentItemMap() {
		return parentItemMap;
	}
}
