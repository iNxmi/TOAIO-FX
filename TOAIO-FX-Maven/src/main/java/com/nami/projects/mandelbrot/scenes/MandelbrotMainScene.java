package com.nami.projects.mandelbrot.scenes;

import java.awt.image.BufferedImage;

import com.nami.components.SceneController;
import com.nami.projects.mandelbrot.components.MandelbrotCalculator;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.util.Duration;

public class MandelbrotMainScene extends SceneController {

//	One useful interpretation of this question is: What (if any) is the common definition of "zoom" when viewing the Mandelbrot set?
//
//	So far, I have three examples with three different ways of specifying zoom:
//
//	https://mandelbrot.ophir.dev zoom = [pixels per fractal space]
//
//	https://mandel.gart.nz zoom = 2 * [pixels per fractal space]
//
//	http://mandelset.ru scale = [view height in fractal space] / 2 (smaller is more zoomed in)
//
//	>:(
	
	
	@FXML
	private Canvas canvas;
	@FXML
	private Label label1, label2, label3;

	private MandelbrotCalculator calculator = new MandelbrotCalculator();
	private Image img;
	private Timeline timeline;

	private double xOffset = -2, yOffset = -2;
	private double zoomOffset = 8;

	@Override
	public void onInit() {
		timeline = new Timeline(new KeyFrame(Duration.millis(17), e -> update()));
		timeline.setCycleCount(-1);
		timeline.play();
	}

	public void onCalculate() {
		calculate(xOffset, yOffset, zoomOffset);
	}

	public void onReset() {
		xOffset = -2;
		yOffset = -2;
		zoomOffset = 4;

		calculate(xOffset, yOffset, zoomOffset);
	}

	private double mOldX, mOldY;

	public void onCanvasDrag(MouseEvent e) {
		double xDiff = mOldX - e.getX();
		double yDiff = mOldY - e.getY();

		xOffset += xDiff * .01 * zoomOffset;
		yOffset += yDiff * .01 * zoomOffset;

		mOldX = e.getX();
		mOldY = e.getY();

		calculate(xOffset, yOffset, zoomOffset);
	}

	public void onCanvasClicked(MouseEvent e) {
		mOldX = e.getX();
		mOldY = e.getY();
	}

	public void onCanvasScroll(ScrollEvent e) {
		zoomOffset -= e.getDeltaY() * zoomOffset * .001;

		calculate(xOffset, yOffset, zoomOffset);
	}

	private void calculate(double x, double y, double size) {
		BufferedImage rawImg = calculator.calculate(1080, 1080, x, y, size, 85);

		img = SwingFXUtils.toFXImage(rawImg, null);
	}

	public void update() {
		if (img != null) {
			var g = canvas.getGraphicsContext2D();

			g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

			g.drawImage(img, 0, 0);

			label1.setText("X: " + xOffset);
			label2.setText("Y: " + yOffset);
			label3.setText("Zoom: " + zoomOffset);
		}
	}

}
