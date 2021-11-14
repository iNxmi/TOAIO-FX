package com.nami.projects.sorting.components;

public class SortingAlgorithmImage {

	private final int[] nodes;
	private final int[] positions;
	private final int[] stats;

	private final long pastTime;

	public SortingAlgorithmImage(long pastTime, int[] nodes, int[] positions, int[] stats) {
		this.pastTime = pastTime;
		this.nodes = nodes.clone();
		this.positions = positions.clone();
		this.stats = stats.clone();
	}

	public int[] getNodes() {
		return nodes;
	}

	public int[] getPositions() {
		return positions;
	}

	public int[] getStats() {
		return stats;
	}

	public long getPastTime() {
		return pastTime;
	}

}
