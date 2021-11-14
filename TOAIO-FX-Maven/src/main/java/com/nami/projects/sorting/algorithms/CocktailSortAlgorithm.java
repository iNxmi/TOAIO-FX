package com.nami.projects.sorting.algorithms;

import com.nami.projects.sorting.components.SortingAlgorithmExecutor;

public class CocktailSortAlgorithm extends SortingAlgorithmExecutor {

	@Override
	public String onGetName() {
		return "CocktailSort";
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
		boolean swapped;
		do {
			swapped = false;
			for (positions[0] = 0; positions[0] <= nodes.length - 2; positions[0]++) {

				capture();

				if (nodes[positions[0]] > nodes[positions[0] + 1]) {
					swap(positions[0], positions[0] + 1);
					swapped = true;
				}
			}

			capture();

			if (!swapped) {
				break;
			}

			swapped = false;
			for (positions[0] = nodes.length - 2; positions[0] >= 1; positions[0]--) {
				if (nodes[positions[0]] > nodes[positions[0] + 1]) {
					swap(positions[0], positions[0] + 1);
					swapped = true;
				}

				capture();

			}
		} while (swapped);
	}

}
