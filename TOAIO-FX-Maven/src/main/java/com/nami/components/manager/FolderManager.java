package com.nami.components.manager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FolderManager {

	private final Map<String, String> map = new HashMap<>();

	public FolderManager(String rootPath) {
		map.put("root", rootPath);
	}

	public void addFolder(String name, String prefix, String suffix) {
		map.put(name, map.get(prefix).concat("\\").concat(suffix));
	}

	public void createFolders() {
		for (Map.Entry<String, String> e : map.entrySet()) {
			File file = new File(e.getValue());
			if (!file.exists())
				file.mkdirs();
		}
	}

	public String getFolder(String name) {
		return map.get(name);
	}

}
