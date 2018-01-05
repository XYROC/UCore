package ucore.lib;

import java.util.HashMap;

public class Data {

	private HashMap<String, HashMap<String, Object>> data;

	public Data() {
		data = new HashMap<>();
	}

	public void appendList(String key, HashMap<String, Object> list) {
		data.put(key, list);
	}

	public HashMap<String, Object> getList(String list){
		return data.get(list);
	}

	public void removeList(String key) {
		data.remove(key);
	}

	public Object get(String list, String key) {
		return data.get(list).get(key);
	}

}
