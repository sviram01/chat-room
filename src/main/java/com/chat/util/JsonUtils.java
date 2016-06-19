package com.chat.util;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;

public class JsonUtils {

	@SuppressWarnings("unchecked")
	public static Map<String, String> jsonMap(String content) {
		Gson gson = new Gson();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map = (Map<String, String>) gson.fromJson(content, map.getClass());
		return map;
	}
}

