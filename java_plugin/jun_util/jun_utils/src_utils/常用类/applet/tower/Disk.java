package book.applet.tower;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 汉诺塔游戏中的盘
 */
public class Disk {

	// 盘的宽度
	public int width;
	// 盘的颜色
	public Color color;
	// 盘的标签
	public String label;
	
	// 构造方法
	public Disk(int width, Color color, String label) {
		this.width = width;
		this.color = color;
		this.label = label;
	}
	/**
	 * 画盘，盘的中间为矩形，两边为圆弧
	 * @param g		画笔
	 * @param xPos		盘中心所在的X坐标
	 * @param indexInTower		盘所在塔的层次，最底下的盘的序号为0
	 */
	public void drawDisk(Graphics g, int xPos, int indexInTower) {

		// 先求得盘矩形部分左上角的坐标
		int x = xPos - width / 2;
		int y = Constants.PILLAR_BOTTOM_YPOS - (indexInTower + 1)
				* Constants.DISK_HEIGHT;
		// 中间画矩形
		g.setColor(Color.black);
		g.drawRect(x, y, width - 1, Constants.DISK_HEIGHT - 1);

		// 两边画圆弧，颜色为黑色，先求得左边圆弧的左上角的坐标
		int x1 = x - Constants.DISK_CORNER_WIDTH;
		int y1 = y;
		g.drawOval(x1, y1, Constants.DISK_HEIGHT - 1,
						Constants.DISK_HEIGHT - 1);
		// 再求得右边圆弧的左上角的坐标
		int x2 = x + width - Constants.DISK_CORNER_WIDTH - 1;
		int y2 = y;
		g.drawOval(x2, y2, Constants.DISK_HEIGHT - 1,
						Constants.DISK_HEIGHT - 1);

		// 用盘的颜色填充上面的矩形和圆弧
		g.setColor(color);
		g.fillRect(x + 1, y + 1, width - 2, Constants.DISK_HEIGHT - 2);
		g.fillOval(x1 + 1, y1 + 1, Constants.DISK_HEIGHT - 2,
				Constants.DISK_HEIGHT - 2);
		g.fillOval(x2 + 1, y2 + 1, Constants.DISK_HEIGHT - 2,
				Constants.DISK_HEIGHT - 2);
		// 画黑色的标签
		g.setColor(Color.black);
		g.drawString(label, x, y + Constants.DISK_HEIGHT / 2 + 4);
	}
}