package com.nami.components;

import com.nami.Launch;

public abstract class SceneController {

	private Launch main;

	public void init() {
		onInit();
	}

	public abstract void onInit();

	public void setMain(Launch main) {
		this.main = main;
	}

	public Launch getMain() {
		return main;
	}

}
