package com.nami.projects.sorting.components;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import com.nami.components.Config;

public class SortingAlgorithmCapture {

	private final String algorithmName;

	private List<SortingAlgorithmFrame> images = new ArrayList<>();

	private final int totalFrames;

	private final int numberOfNodes;

	private final int compared;
	private final int swapped;

	private final long pastTime;

	public SortingAlgorithmCapture(String algorithmName, List<SortingAlgorithmFrame> images, int numberOfNodes,
			long pastTime, int[] stats) {
		this.algorithmName = algorithmName;

		this.images = images;
		this.totalFrames = images.size();

		this.numberOfNodes = numberOfNodes;

		this.pastTime = pastTime;

		this.compared = stats[0];
		this.swapped = stats[1];
	}

	public SortingAlgorithmCapture(File file) throws IOException {
		try (var zipFile = new ZipFile(file)) {
			Config config = new Config(zipFile.getInputStream(zipFile.getEntry("data.dat")));

			config.load();
			algorithmName = config.getString("algorithm");
			numberOfNodes = config.getInt("numberOfNodes");
			totalFrames = config.getInt("totalFrames");
			compared = config.getInt("compared");
			swapped = config.getInt("swapped");
			pastTime = config.getLong("pastTime");

			for (int i = 0; i < totalFrames; i++) {
				config = new Config(zipFile.getInputStream(zipFile.getEntry("frame-" + i + ".dat")));
				config.load();

				images.add(new SortingAlgorithmFrame(config.getLong("pastTime"), config.getIntArray("nodes"),
						config.getIntArray("positions"), config.getIntArray("stats")));
			}
			config.close();
		}
	}

	public void saveAlgorithmAsZip(String path, String fileName) throws IOException {
		var file = new File(path.concat("/").concat(fileName).concat(".asrt"));
		var fileOutputStream = new FileOutputStream(file);
		try (var zipOutputStream = new ZipOutputStream(fileOutputStream)) {
			zipOutputStream.putNextEntry(new ZipEntry("data.dat"));

			Config config = new Config(zipOutputStream);

			config.add("algorithm", algorithmName);
			config.add("numberOfNodes", numberOfNodes);
			config.add("totalFrames", totalFrames);
			config.add("compared", compared);
			config.add("swapped", swapped);
			config.add("pastTime", pastTime);
			config.flush();

			for (var i = 0; i < images.size(); i++) {
				zipOutputStream.putNextEntry(new ZipEntry("frame-" + i + ".dat"));
				SortingAlgorithmFrame img = images.get(i);
				config = new Config(zipOutputStream);
				config.add("pastTime", img.getPastTime());

				Integer[] list = new Integer[img.getNodes().length];
				for (int j = 0; j < img.getNodes().length; j++)
					list[j] = Integer.valueOf(img.getNodes()[j]);
				config.addArray("nodes", list);

				Integer[] positions = new Integer[img.getPositions().length];
				for (int j = 0; j < img.getPositions().length; j++)
					positions[j] = Integer.valueOf(img.getPositions()[j]);
				config.addArray("positions", positions);

				Integer[] stats = new Integer[img.getStats().length];
				for (int j = 0; j < img.getStats().length; j++)
					stats[j] = Integer.valueOf(img.getStats()[j]);
				config.addArray("stats", stats);

				config.flush();
			}
			config.close();

			zipOutputStream.flush();
		}
	}

	public String getAlgorithmName() {
		return algorithmName;
	}

	public List<SortingAlgorithmFrame> getImages() {
		return images;
	}

	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	public int getCompared() {
		return compared;
	}

	public int getSwapped() {
		return swapped;
	}

	public long getPastTime() {
		return pastTime;
	}

	public int getTotalFrames() {
		return totalFrames;
	}

}
