package com.nami;

import com.nami.components.Scenes;
import com.nami.components.manager.FolderManager;
import com.nami.components.manager.StageManager;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private FolderManager folderManager;
	private StageManager stageManager;

	@Override
	public void start(Stage defaultStage) throws Exception {
		initFolders();

		stageManager = new StageManager(this);
		stageManager.setStage(Scenes.LAUNCHER);
	}

	private void initFolders() {
		folderManager = new FolderManager(System.getenv("APPDATA").concat("\\.toaio"));

		// Main
		folderManager.addFolder("root_logs", "root", "logs");
		folderManager.addFolder("root_res", "root", "res");
		folderManager.addFolder("root_saves", "root", "saves");

		// Logs
		folderManager.addFolder("logs_flappybird", "root_logs", "flappybird");

		// Resources
		folderManager.addFolder("res_flappybird", "root_res", "flappybird");

		// Saves
		folderManager.addFolder("saves_flappybird", "root_saves", "flappybird");
		folderManager.addFolder("saves_mandelbrot", "root_saves", "mandelbrot");
		folderManager.addFolder("saves_path", "root_saves", "path");
		folderManager.addFolder("saves_sorting", "root_saves", "sorting");

		folderManager.createFolders();
	}

	public FolderManager getFolderManager() {
		return folderManager;
	}

	public StageManager getStageManager() {
		return stageManager;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
