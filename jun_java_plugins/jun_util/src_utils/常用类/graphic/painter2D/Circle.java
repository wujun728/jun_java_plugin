package book.graphic.painter2D;

import java.awt.Color;
import java.awt.Graphics;
/**
 * 画圆
 */
public class Circle implements Shape {
	// 圆的颜色
	private Color color;
	// 圆心位置，x、y坐标
	private int x;
	private int y;
	// 圆的半径
	private int radius;

	// 构造方法
	public Circle(int x, int y, int radius, Color color) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = color;
	}
	// 画圆
	public void draw(Graphics g) {
		// 设置画笔颜色
		g.setColor(color);
		// drawArc画弧，当弧的宽度和高度一样，而且弧度从0到360度时，便是圆了。
		g.drawArc(x, y, radius, radius, 0, 360);
	}
}