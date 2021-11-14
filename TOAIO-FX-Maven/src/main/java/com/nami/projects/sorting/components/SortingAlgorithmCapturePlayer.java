package com.nami.projects.sorting.components;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.util.Duration;

public class SortingAlgorithmCapturePlayer {

	private static final double DEFAULT_SPEED = 3.0d;
	private static final double DEFAULT_INDEX = 0.0d;
	private static final double DEFAULT_DIRECTION = 1.0d;

	private StackedBarChart<String, Number> chart;
	private Timeline timeline;

	private SortingAlgorithmCapture capture;

	private Runnable runnable;

	private double speed;
	private double index;
	private double direction;

	private boolean playing;

	public SortingAlgorithmCapturePlayer(StackedBarChart<String, Number> chart, double timelineMs) {
		this.chart = chart;

		this.timeline = new Timeline(new KeyFrame(Duration.millis(timelineMs), e -> update()));
		this.timeline.setCycleCount(-1);

		setDefaultVariables();
	}

	public void update() {
		if (capture != null && playing) {
			updateIndex();

			if (runnable != null)
				runnable.run();

			updateChart();
		}
	}

	private void updateIndex() {
		double nextIndex = index + speed * direction;

		if (nextIndex >= 0 && nextIndex <= capture.getTotalFrames()) {
			index += speed * direction;
		} else if (nextIndex < 0) {
			index = 0;
		} else if (nextIndex > capture.getTotalFrames()) {
			index = capture.getTotalFrames() - 1.0d;
		}
	}

	private void updateChart() {
		Series<String, Number> othersSeries = new XYChart.Series<>();
		Series<String, Number> highlightedSeries = new XYChart.Series<>();

		chart.getData().clear();

		for (int i = 0; i < capture.getNumberOfNodes(); i++) {
			XYChart.Data<String, Number> others = new XYChart.Data<>();
			XYChart.Data<String, Number> highlighted = new XYChart.Data<>();

			List<Integer> positions = Arrays.stream(capture.getImages().get((int) index).getPositions()).boxed()
					.collect(Collectors.toList());

			if (positions.contains(i)) {
				highlighted.setXValue(i + "");
				highlighted.setYValue(capture.getImages().get((int) index).getNodes()[i]);
				highlightedSeries.getData().add(highlighted);
			} else {
				others.setXValue(i + "");
				others.setYValue(capture.getImages().get((int) index).getNodes()[i]);
				othersSeries.getData().add(others);
			}

		}

		chart.getData().add(highlightedSeries);
		chart.getData().add(othersSeries);
	}

	public void clear() {
		this.capture = null;
		setDefaultVariables();
		chart.getData().clear();
	}

	private void setDefaultVariables() {
		this.speed = DEFAULT_SPEED;
		this.index = DEFAULT_INDEX;
		this.direction = DEFAULT_DIRECTION;
	}

	public void play() {
		this.playing = true;
		this.timeline.play();
	}

	public void stop() {
		this.playing = false;
		this.timeline.stop();
	}

	public void setCapture(SortingAlgorithmCapture capture) {
		this.capture = capture;
		setDefaultVariables();
		updateChart();
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setIndex(double index) {
		this.index = index;
		updateChart();
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}

	public SortingAlgorithmCapture getCapture() {
		return capture;
	}

	public double getSpeed() {
		return speed;
	}

	public double getIndex() {
		return index;
	}

	public double getDirection() {
		return direction;
	}

	public boolean isPlaying() {
		return playing;
	}

	public Runnable getRunnable() {
		return runnable;
	}

	public StackedBarChart<String, Number> getChart() {
		return chart;
	}

}