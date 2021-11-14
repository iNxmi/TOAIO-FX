package com.nami.components;

import com.nami.Main;

public abstract class SceneController {

	private Main main;

	public void init() {
		onInit();
	}

	public abstract void onInit();

	public void setMain(Main main) {
		this.main = main;
	}

	public Main getMain() {
		return main;
	}

}
