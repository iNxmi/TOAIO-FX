package com.nami.components.manager;

import java.io.IOException;

import com.nami.Launch;
import com.nami.components.SceneController;
import com.nami.components.Scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager {

	private Launch main;
	private Stage stage = new Stage();

	public StageManager(Launch main) {
		this.main = main;
	}

	private String getStyleSheetPath(Scenes scene) {
		String classPath = scene.path().concat(".").concat(scene.fileName());
		return ("/").concat(classPath.replace(".", "/").concat(".css"));
	}

	private String getParentPath(Scenes scene) {
		String classPath = scene.path().concat(".").concat(scene.fileName());
		return ("/").concat(classPath.replace(".", "/").concat(".fxml"));
	}

	private Parent loadParent(Scenes scene) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(getParentPath(scene)));
		Parent root = loader.load();
		
		SceneController controller = (SceneController) loader.getController();
		controller.setMain(main);
		controller.init();
		
		return root;
	}

	public void setStage(Scenes scene) throws IOException {
		stage.close();

		stage = new Stage();
		Parent parent = loadParent(scene);
		Scene sc = new Scene(parent);
		sc.getStylesheets().add(getStyleSheetPath(scene));

		stage.setScene(sc);
		stage.show();
	}

	public Stage getStage() {
		return stage;
	}

}
