package org.springrain.frame.util.patchca.background;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GradientBackgroundFactory implements BackgroundFactory {

	public enum Direction {
		Horizontal,
		Vertical,
		TopLeftBottomRight,
		BottomLeftTopRight
	}

	private Color startColor;
	private Color endColor;
	private Direction direction;

	public GradientBackgroundFactory() {
		this(new Color(192, 192, 0), new Color(192, 128, 128), Direction.Horizontal);
	}

	public GradientBackgroundFactory(Color startColor, Color endColor, Direction direction) {
		this.startColor = startColor;
		this.endColor = endColor;
		this.direction = direction;
	}

	@Override
    public void fillBackground(BufferedImage dest) {

		float x1, y1, x2, y2;

		switch (direction) {
			default:
			case Horizontal:
				x1 = 0;
				y1 = 0;
				x2 = dest.getWidth();
				y2 = 0;
				break;
			case Vertical:
				x1 = 0;
				y1 = 0;
				x2 = 0;
				y2 = dest.getHeight();
				break;
			case BottomLeftTopRight:
				x1 = 0;
				y1 = dest.getHeight();
				x2 = dest.getWidth();
				y2 = 0;
				break;
			case TopLeftBottomRight:
				x1 = 0;
				y1 = 0;
				x2 = dest.getWidth();
				y2 = dest.getHeight();
				break;
		}

		GradientPaint gp = new GradientPaint(x1, y1, startColor, x2, y2, endColor);

		Graphics2D g = dest.createGraphics();
		g.setPaint(gp);
		g.fillRect(0, 0, dest.getWidth(), dest.getHeight());
	}

}