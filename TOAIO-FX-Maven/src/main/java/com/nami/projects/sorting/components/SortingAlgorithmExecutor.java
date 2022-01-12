package com.nami.projects.sorting.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class SortingAlgorithmExecutor implements Runnable {

	private final Random random = new Random();

	protected int[] nodes;
	protected int[] positions;
	protected int[] stats;

	protected List<SortingAlgorithmFrame> images = new ArrayList<>();

	private long startTime;

	private SortingAlgorithmFrame currentImage;

	protected boolean calculationFinished;

	private SortingAlgorithmCapture capture;

	public void calculate(int numberOfNodes) {
		nodes = new int[numberOfNodes];
		positions = onGetPositions();
		stats = onGetStats();

		for (var i = 0; i < numberOfNodes; i++)
			nodes[i] = i + 1;

		for (var i = 0; i < numberOfNodes; i++)
			swap(i, random.nextInt(numberOfNodes));

		Thread thread = new Thread(this, "calculation_thread");
		thread.start();
	}

	@Override
	public void run() {
		startTime = System.nanoTime();

		onCalculate();

		capture = new SortingAlgorithmCapture(onGetName(), images, nodes.length, System.nanoTime() - startTime, stats);

		calculationFinished = true;
	}

	public abstract void onCalculate();

	public void swap(int indexA, int indexB) {
		var temp = nodes[indexA];
		nodes[indexA] = nodes[indexB];
		nodes[indexB] = temp;
	}

	public void capture() {
		currentImage = new SortingAlgorithmFrame(System.nanoTime() - startTime, nodes, positions, stats);
		images.add(currentImage);
	}

	public abstract String onGetName();

	public abstract int onGetWorstCase(int size);

	public abstract int[] onGetPositions();

	public abstract int[] onGetStats();

	public String getName() {
		return onGetName();
	}

	public int getWorstCase(int size) {
		return onGetWorstCase(size);
	}

	public SortingAlgorithmFrame getCurrentImage() {
		return currentImage;
	}

	public boolean isFinished() {
		return calculationFinished;
	}

	public SortingAlgorithmCapture getFinishedCapture() {
		return capture;
	}

	public List<SortingAlgorithmFrame> getImages() {
		return images;
	}

}
