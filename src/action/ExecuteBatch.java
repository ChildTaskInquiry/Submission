package action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExecuteBatch {

	public void execute(String batch_path) {
		Runtime runtime = Runtime.getRuntime();
		Process p;
		try {
			p = runtime.exec(batch_path);
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			for (;;) {
				String line;
				line = br.readLine();
				if (line == null)
					break;
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getConsole(String batch_path) {
		Runtime runtime = Runtime.getRuntime();
		Process p;
		List<String> consoles = new ArrayList<String>();
		try {
			p = runtime.exec(batch_path);
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			for (;;) {
				String line;
				line = br.readLine();
				if (line == null)
					break;
				consoles.add(line);
			}
			return consoles;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public JSONObject getJsonObjectConsole(String batch_path) {
		List<String> consoles = getConsole(batch_path);
		JSONObject json = new JSONObject();
		for (String console : consoles) {
			if (console.length() > 0 && console.substring(0, 1).equals("{")) {
				json = JSONObject.fromObject(console);
			}
		}
		// System.out.println(json);
		return json;
	}

	public JSONArray getJsonArrayConsole(String batch_path) {
		List<String> consoles = getConsole(batch_path);
		JSONArray json = new JSONArray();
		for (String console : consoles) {
			if (console.length() > 0 && console.substring(0, 1).equals("[")) {
				json = JSONArray.fromObject(console);
			}
		}
		System.out.println(json);
		return json;
	}

}
