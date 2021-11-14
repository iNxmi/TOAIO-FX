package com.nami.projects.mandelbrot.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.apache.commons.numbers.complex.Complex;

public class MandelbrotCalculator {

	public MandelbrotCalculator() {

	}

	public BufferedImage calculate(int imgWidth, int imgHeight, double mbX, double mbY, double size, int iterations) {
		double dx = size / (imgWidth - 1);
		double dy = size / (imgHeight - 1);

		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < imgWidth; x++)
			for (int y = 0; y < imgHeight; y++)
				img.setRGB(x, y, calculatePoint(x, y, mbX, mbY, dx, dy, iterations));

		Graphics g = img.getGraphics();
		g.setColor(Color.white);
		g.drawRect(0, 0, img.getWidth() - 1, img.getHeight() - 1);

		return img;
	}

	private int calculatePoint(int x, int y, double mbX, double mbY, double dx, double dy, int iterations) {
		Complex n = Complex.ofCartesian(mbX + x * dx, mbY + y * dy);
		Complex z = n;
		int i;

		for (i = 0; i < iterations; i++) {
			z = z.multiply(z).add(n);

			if (z.abs() > 2.0)
				break;
		}

		Color color = new Color(i * 3, i * 3, i * 3, i * 3);
		return color.getRGB();
	}

}
