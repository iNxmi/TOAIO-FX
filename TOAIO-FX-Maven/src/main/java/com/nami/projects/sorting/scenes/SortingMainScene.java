package com.nami.projects.sorting.scenes;

import java.io.File;
import java.io.IOException;

import com.nami.components.SceneController;
import com.nami.components.Scenes;
import com.nami.components.manager.StageManager;
import com.nami.projects.sorting.components.SortingAlgorithmCapture;
import com.nami.projects.sorting.components.SortingAlgorithmCapturePlayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SortingMainScene extends SceneController implements Runnable {

	@FXML
	private StackedBarChart<String, Number> chart;

	@FXML
	private ListView<String> captureStatList;
	@FXML
	private ListView<String> frameStatList;

	@FXML
	private Label speedLabel;
	@FXML
	private Slider speedSlider;

	@FXML
	private Label positionLabel;
	@FXML
	private Slider positionSlider;

	@FXML
	private Button playButton;
	@FXML
	private Button dirButton;

	private StageManager stageManager;
	private SortingAlgorithmCapturePlayer player;

	@Override
	public void onInit() {
		stageManager = new StageManager(getMain());
		player = new SortingAlgorithmCapturePlayer(chart, 17);

		player.setRunnable(this);
		player.setSpeed(speedSlider.getValue());
	}

	// Player Controls

	public void speedDragOver(MouseEvent e) {
		player.setSpeed(speedSlider.getValue());
		speedLabel.setText("Speed (" + round(speedSlider.getValue()) + ")");
	}

	public void pos1Button(ActionEvent e) {
		if (player.getCapture() != null)
			player.setIndex(0);
	}

	public void previousButton(ActionEvent e) {
		if (player.getCapture() != null && player.getIndex() >= 1)
			player.setIndex(player.getIndex() - 1);
	}

	public void playButton(ActionEvent e) {
		if (player.isPlaying()) {
			player.stop();
		} else {
			player.play();
		}
		playButton.setText(player.isPlaying() ? "Stop" : "Play");
	}

	public void dirButton(ActionEvent e) {
		player.setDirection(player.getDirection() * -1);
		dirButton.setText((player.getDirection() > 0 ? ">" : "<"));
	}

	public void nextButton(ActionEvent e) {
		if (player.getCapture() != null && player.getIndex() < player.getCapture().getTotalFrames() - 1)
			player.setIndex(player.getIndex() + 1);
	}

	public void endeButton(ActionEvent e) {
		if (player.getCapture() != null)
			player.setIndex(player.getCapture().getTotalFrames() - 1);
	}

	public void positionDragOver(MouseEvent e) {
		if (player.getCapture() != null) {
			player.setIndex(positionSlider.getValue());
			positionLabel.setText("Position (" + round(positionSlider.getValue()) + ")");
		}
	}

	// File Menu Item

	public void fileNew(ActionEvent e) {
		try {
			stageManager.setStage(Scenes.SORTING_NEW);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void fileOpen(ActionEvent e) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select Sorting Algorithm Capture File");
		fc.getExtensionFilters().add(new ExtensionFilter("Sorting Algorithm Capture (.asrt)", "*.asrt"));
		fc.setInitialDirectory(new File(getMain().getFolderManager().getFolder("saves_sorting")));
		File file = fc.showOpenDialog(getMain().getStageManager().getStage());

		if (file != null)
			try {
				SortingAlgorithmCapture capture = new SortingAlgorithmCapture(file);

				player.setCapture(capture);
				player.getChart().setTitle(file.getName());

				positionSlider.setMax(capture.getTotalFrames() - 1.0d);

				captureStatList.getItems().clear();
				captureStatList.getItems().add("Total Time (ms) : " + capture.getPastTime() / 1000000d);
				captureStatList.getItems().add("Total Frames: " + capture.getTotalFrames());
				captureStatList.getItems().add("Total Compared: " + capture.getCompared());
				captureStatList.getItems().add("Total Swapped: " + capture.getSwapped());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	}

	public void fileClose(ActionEvent e) {
		player.stop();
		playButton.setText("Play");

		player.clear();
		captureStatList.getItems().clear();
		frameStatList.getItems().clear();
	}

	// Methods
	private double round(double value) {
		return Math.round(value * 100d) / 100d;
	}

	@Override
	public void run() {
		var capture = player.getCapture();
		var index = player.getIndex();

		positionSlider.setValue(index);
		positionLabel.setText("Position (" + round(positionSlider.getValue()) + ")");

		frameStatList.getItems().clear();
		frameStatList.getItems().add("Time : " + capture.getImages().get((int) index).getPastTime() / 1000000d + "ms");
		frameStatList.getItems().add("Frame: " + (int) index);
		frameStatList.getItems().add("Compared: " + capture.getImages().get((int) index).getStats()[0]);
		frameStatList.getItems().add("Swapped: " + capture.getImages().get((int) index).getStats()[1]);
	}

}
