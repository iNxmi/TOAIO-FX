package com.nami.projects.sorting.algorithms;

import com.nami.projects.sorting.components.SortingAlgorithmExecutor;

public class SelectionSortAlgorithm extends SortingAlgorithmExecutor {

	@Override
	public String onGetName() {
		return "SelectionSort";
	}

	@Override
	public int onGetWorstCase(int size) {
		return size * size;
	}

	@Override
	public int[] onGetPositions() {
		return new int[2];
	}

	@Override
	public int[] onGetStats() {
		return new int[2];
	}

	@Override
	public void onCalculate() {
		for (int i = 0; i < nodes.length - 1; i++) {
			System.out.println(i);
			for (positions[0] = i; positions[0] < nodes.length - 1; positions[0]++) {
				capture();
				if (nodes[positions[1]] > nodes[positions[0]])
					positions[1] = positions[0];
			}
			capture();
			swap(positions[1], i);
		}
	}

}
