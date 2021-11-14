package com.nami.projects.sorting.scenes;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;

import com.nami.components.SceneController;
import com.nami.projects.sorting.algorithms.BubbleSortAlgorithm;
import com.nami.projects.sorting.algorithms.CocktailSortAlgorithm;
import com.nami.projects.sorting.algorithms.InsertionSortAlgorithm;
import com.nami.projects.sorting.algorithms.SelectionSortAlgorithm;
import com.nami.projects.sorting.components.SortingAlgorithmCapture;
import com.nami.projects.sorting.components.SortingAlgorithmExecutor;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class SortingNewScene extends SceneController implements Runnable {

	// Header
	@FXML
	private Label toDoLabel;

	// Settings
	@FXML
	private TextField fileNameTextField;
	@FXML
	private Spinner<Integer> numberOfNodesSpinner;
	@FXML
	private ComboBox<String> algorithmChoiceBox;

	// Directory
	@FXML
	private TextField directoryPathTextField;

	// Progress
	@FXML
	private Label progressLabel;

	// Buttons
	@FXML
	private Button finishButton;

	// Other
	private Map<String, SortingAlgorithmExecutor> algorithmParser;
	private String mainDirectoryPath;

	@Override
	public void onInit() {
		mainDirectoryPath = getMain().getFolderManager().getFolder("saves_sorting");

		directoryPathTextField.setText(mainDirectoryPath);

		algorithmChoiceBox.setOnAction(e -> updateFinishButton());
		fileNameTextField.textProperty().addListener((observable, oldValue, newValue) -> updateFinishButton());

		numberOfNodesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 200, 100));

		initAlgorithmChoiceBox();
	}

	// Initiation
	private void initAlgorithmChoiceBox() {
		algorithmParser = new HashMap<>();

		List<SortingAlgorithmExecutor> algorithms = new ArrayList<>();

		algorithms.add(new BubbleSortAlgorithm());
		algorithms.add(new CocktailSortAlgorithm());
		algorithms.add(new InsertionSortAlgorithm());
		algorithms.add(new SelectionSortAlgorithm());

		for (SortingAlgorithmExecutor a : algorithms) {
			algorithmParser.put(a.getName(), a);
			algorithmChoiceBox.getItems().add(a.getName());
		}
	}

	// Directory
	public void onBrowseAction(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(directoryPathTextField.getText()));
		fileChooser.setDialogTitle("Select destination directory");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			directoryPathTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
	}

	public void onOpenAction(ActionEvent e) {
		try {
			Desktop.getDesktop().open(new File(directoryPathTextField.getText()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void onResetAction(ActionEvent e) {
		directoryPathTextField.setText(mainDirectoryPath);
	}

	// Buttons
	public void onFinishButtonAction(ActionEvent e) {
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		SortingAlgorithmExecutor algorithm = algorithmParser.get(algorithmChoiceBox.getValue());
		algorithm.calculate(numberOfNodesSpinner.getValue());

		while (!algorithm.isFinished())
			Platform.runLater(() -> progressLabel.setText(algorithm.getImages().size() + ""));

		Platform.runLater(() -> progressLabel.setText("Saving..."));

		try {
			File folder = new File(directoryPathTextField.getText());
			if (!folder.exists())
				folder.mkdirs();

			SortingAlgorithmCapture capture = algorithm.getFinishedCapture();
			capture.saveAlgorithmAsZip(folder.getAbsolutePath(), fileNameTextField.getText());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Platform.runLater(() -> progressLabel.setText("Done!"));
	}

	public void onCancelButtonAction(ActionEvent e) {

	}

	// Methods

	private void updateFinishButton() {
		if (algorithmChoiceBox.getValue() != null) {
			boolean textField = !fileNameTextField.getText().trim().isEmpty();
			boolean choiceBox = !algorithmChoiceBox.getValue().trim().isEmpty();

			boolean value = textField && choiceBox;
			finishButton.setDisable(!value);
		}
	}

}
