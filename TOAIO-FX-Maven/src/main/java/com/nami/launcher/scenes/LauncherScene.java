package com.nami.launcher.scenes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.nami.components.SceneController;
import com.nami.components.Scenes;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class LauncherScene extends SceneController {

	@FXML
	private ComboBox<String> comboBox;

	private Map<String, Scenes> map = new HashMap<>();

	@Override
	public void onInit() {
		getMain().getStageManager().getStage().setTitle("Launcher");
		getMain().getStageManager().getStage().setResizable(false);

		map.put("Sorting", Scenes.SORTING);
		map.put("Mandelbrot", Scenes.MANDELBROT);

		for (var entry : map.entrySet())
			comboBox.getItems().add(entry.getKey());

		comboBox.setValue("Sorting");
	}

	public void onButton() {
		if (comboBox.getValue() != null)
			try {
				getMain().getStageManager().setStage(map.get(comboBox.getValue()));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
