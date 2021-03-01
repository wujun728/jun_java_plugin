package book.graphic.painter2D;

import java.awt.Color;
import java.awt.Graphics;
/**
 * 画线
 */
public class Line implements Shape {
	// 线的起点位置，x、y坐标
	private int x1;
	private int y1;
	
	// 线的终点位置，x、y坐标
	private int x2;
	private int y2;

	// 线的颜色
	private Color color;
	
	// 构造方法
	public Line(int x1, int y1, int x2, int y2, Color color) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
	}
	// 画直线
	public void draw(Graphics g) {
		// 设置画笔颜色
		g.setColor(color);
		// drawLine方法画线
		g.drawLine(x1, y1, x2, y2);
	}
}