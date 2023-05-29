package com.jun.plugin.demo;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawDemo {
	public static void main(String[] args) {
		DrawFrame frame = new DrawFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.show();
	}
}
/**
   һ��frame�а�һ��panel������ͼ
 */
class DrawFrame extends JFrame {
	public DrawFrame() {
		setTitle("DrawDemo");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		// �� panel �ӵ� frame
		DrawPanel panel = new DrawPanel();
		Container contentPane = getContentPane();
		contentPane.add(panel);
	}

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;
}

/**
 ������ʾ���κ���Բ��panel
 */
class DrawPanel extends JPanel {
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// ������
		double leftX = 100;
		double topY = 100;
		double width = 200;
		double height = 150;

		Rectangle2D rect = new Rectangle2D.Double(leftX, topY, width, height);
		g2.draw(rect);

		// ��rect��������Բ
		Ellipse2D ellipse = new Ellipse2D.Double();
		ellipse.setFrame(rect);
		g2.draw(ellipse);

		// ��һ���Խ���
		g2.draw(new Line2D.Double(leftX, topY, leftX + width, topY + height));

		// ��һ��ͬ�ĵ�Բ
		double centerX = rect.getCenterX();
		double centerY = rect.getCenterY();
		double radius = 150;

		Ellipse2D circle = new Ellipse2D.Double();
		circle.setFrameFromCenter(centerX, centerY, centerX + radius, centerY
				+ radius);
		g2.draw(circle);
	}
}