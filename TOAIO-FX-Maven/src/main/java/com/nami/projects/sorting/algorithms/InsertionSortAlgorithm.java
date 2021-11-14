package com.nami.projects.sorting.algorithms;

import com.nami.projects.sorting.components.SortingAlgorithmExecutor;

public class InsertionSortAlgorithm extends SortingAlgorithmExecutor {

	@Override
	public String onGetName() {
		return "InsertionSort";
	}

	@Override
	public int onGetWorstCase(int size) {
		return size * size;
	}

	@Override
	public int[] onGetPositions() {
		return new int[1];
	}

	@Override
	public int[] onGetStats() {
		return new int[2];
	}

	@Override
	public void onCalculate() {
		for (int i = 1; i < nodes.length; i++) {
			for (positions[0] = i; positions[0] > 0; positions[0]--) {
				capture();
				if (nodes[positions[0]] < nodes[positions[0] - 1]) {
					swap(positions[0], positions[0] - 1);
				}
			}
			capture();
		}
	}

}
