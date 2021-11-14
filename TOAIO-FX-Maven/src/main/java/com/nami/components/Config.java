package com.nami.components;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Config {

	public static final String SEPARATOR = "=";

	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;

	private Map<String, Object> values = new HashMap<>();

	public Config(OutputStream outputStream) {
		this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
	}

	public Config(InputStream inputStream) {
		this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	}
	
	public Config(InputStream inputStream, OutputStream outputStream) {
		this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
	}

	public void load() throws IOException {
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			if (!line.trim().isEmpty()) {
				String[] args = line.split(SEPARATOR);
				values.put(args[0].trim(), args[1].trim());
			}
		}
	}

	public void flush() throws IOException {
		for (Map.Entry<String, Object> entry : values.entrySet()) {
			bufferedWriter.write(entry.getKey() + SEPARATOR + entry.getValue() + "\n");
			bufferedWriter.flush();
		}
	}

	public void close() throws IOException {
		if (bufferedReader != null)
			bufferedReader.close();
		if (bufferedWriter != null)
			bufferedWriter.close();
	}

	public void add(String name, Object value) {
		values.put(name, value);
	}

	public <T> void addArray(String name, T[] value) {
		add(name, Arrays.toString(value));
	}

	private Object get(String name) {
		return values.get(name);
	}

	public String getString(String name) {
		return get(name).toString();
	}

	public byte getByte(String name) {
		return Byte.parseByte(getString(name));
	}

	public short getShort(String name) {
		return Short.parseShort(getString(name));
	}

	public int getInt(String name) {
		return Integer.parseInt(getString(name));
	}

	public long getLong(String name) {
		return Long.parseLong(getString(name));
	}

	public float getFloat(String name) {
		return Float.parseFloat(getString(name));
	}

	public double getDouble(String name) {
		return Double.parseDouble(getString(name));
	}

	public boolean getBoolean(String name) {
		return Boolean.parseBoolean(getString(name));
	}

	public String[] getStringArray(String name) {
		return prepareStringArray(getString(name));
	}

	public byte[] getByteArray(String name) {
		String[] stringArray = getStringArray(name);
		byte[] array = new byte[stringArray.length];

		for (int i = 0; i < array.length; i++)
			array[i] = Byte.parseByte(stringArray[i]);

		return array;
	}

	public short[] getShortArray(String name) {
		String[] stringArray = getStringArray(name);
		short[] array = new short[stringArray.length];

		for (int i = 0; i < array.length; i++)
			array[i] = Short.parseShort(stringArray[i]);

		return array;
	}

	public int[] getIntArray(String name) {
		String[] stringArray = getStringArray(name);
		int[] array = new int[stringArray.length];

		for (int i = 0; i < array.length; i++)
			array[i] = Integer.parseInt(stringArray[i]);

		return array;
	}

	public long[] getLongArray(String name) {
		String[] stringArray = getStringArray(name);
		long[] array = new long[stringArray.length];

		for (int i = 0; i < array.length; i++)
			array[i] = Long.parseLong(stringArray[i]);

		return array;
	}

	public float[] getFloatArray(String name) {
		String[] stringArray = getStringArray(name);
		float[] array = new float[stringArray.length];

		for (int i = 0; i < array.length; i++)
			array[i] = Float.parseFloat(stringArray[i]);

		return array;
	}

	public double[] getDoubleArray(String name) {
		String[] stringArray = getStringArray(name);
		double[] array = new double[stringArray.length];

		for (int i = 0; i < array.length; i++)
			array[i] = Double.parseDouble(stringArray[i]);

		return array;
	}

	public boolean[] getbooleanArray(String name) {
		String[] stringArray = getStringArray(name);
		boolean[] array = new boolean[stringArray.length];

		for (int i = 0; i < array.length; i++)
			array[i] = Boolean.parseBoolean(stringArray[i]);

		return array;
	}

	private String[] prepareStringArray(String string) {
		return string.replace("[", "").replace("]", "").replace(" ", "").split(",");
	}

}
