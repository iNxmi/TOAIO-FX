package com.nami.components;

public enum Scenes {

	LAUNCHER("com.nami.launcher.scenes", "LauncherScene"),

	SORTING("com.nami.projects.sorting.scenes", "SortingMainScene"),
	SORTING_NEW("com.nami.projects.sorting.scenes", "SortingNewScene"),

	MANDELBROT("com.nami.projects.mandelbrot.scenes", "MandelbrotMainScene");

	private final String path;
	private final String fileName;

	private Scenes(String path, String fileName) {
		this.path = path;
		this.fileName = fileName;
	}

	public String path() {
		return path;
	}

	public String fileName() {
		return fileName;
	}

}
