package book.graphic.painter2D;

import java.awt.Color;
import java.awt.Graphics;

// 画矩形
public class Rectangle implements Shape {
	// 矩形左上角位置的坐标，x、y值
	private int x;
	private int y;
	
	// 矩形的长和宽
	private int width;
	private int height;
	
	// 矩形的颜色
	private Color color;

	// 构造方法
	public Rectangle(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	// 画矩形
	public void draw(Graphics g) {
		g.setColor(color);
		// drawRect画矩形
		g.drawRect(x, y, width, height);
	}
}

