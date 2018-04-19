package com;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class FlexibleDateFormatter {

	private static SimpleDateFormat sdfHyphen = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdfSlash = new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat sdfHyphenShort = new SimpleDateFormat("yyyy-M-d");
	private static SimpleDateFormat sdfSlashShort = new SimpleDateFormat("yyyy/M/d");
	private static SimpleDateFormat sdfNoYear = new SimpleDateFormat("MM/dd");
	private static SimpleDateFormat sdfNoYearShort = new SimpleDateFormat("M/d");
	private static SimpleDateFormat sdfSingapore = new SimpleDateFormat("dd/MM/yyyy");

	public static final int HYPHEN = 0;
	public static final int SLASH = 1;
	public static final int HYPHEN_SHORT = 2;
	public static final int SLASH_SHORT = 3;
	public static final int SINGAPORE = 4;
	public static final int NO_YEAR = 5;
	public static final int NO_YEAR_SHORT = 6;

	private static final Map<Integer, SimpleDateFormat> SDF_MAP;
	static {
		TreeMap<Integer, SimpleDateFormat> map = new TreeMap<Integer, SimpleDateFormat>();
		map.put(HYPHEN, sdfHyphen);
		map.put(SLASH, sdfSlash);
		map.put(HYPHEN_SHORT, sdfHyphenShort);
		map.put(SLASH_SHORT, sdfSlashShort);
		map.put(SINGAPORE, sdfSingapore);
		map.put(NO_YEAR, sdfNoYear);
		map.put(NO_YEAR_SHORT, sdfNoYearShort);
		SDF_MAP = Collections.unmodifiableMap(map);
	}

	public Date parse(String str) {
		for (int i = 0; i < SDF_MAP.size(); i++) {
			try {
				Calendar date = Calendar.getInstance();
				int thisYear = date.get(Calendar.YEAR);
				date.setTime(SDF_MAP.get(i).parse(str));
				if (date.get(Calendar.YEAR) == 1970) {
					date.set(thisYear, date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
				} else if (date.get(Calendar.YEAR) < 2018) {
					continue;
				}
				return date.getTime();
			} catch (ParseException e) {
				continue;
			}
		}
		return null;
	}

	public Object format(Date date, int key) {
		return SDF_MAP.get(key).format(date);
	}
}
