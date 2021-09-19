package com.jun.plugin.demo;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FillDemo {
	public static void main(String[] args) {
		FillFrame frame = new FillFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.show();
	}
}

/**
 ��һ������panel��frame
 */
class FillFrame extends JFrame {
	public FillFrame() {
		setTitle("FillTest");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		// ��panel�ӵ�frame��
		FillPanel panel = new FillPanel();
		Container contentPane = getContentPane();
		contentPane.add(panel);
	}

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;
}

/**
 ������ʾ�����κ���Բ��panel
 */
class FillPanel extends JPanel {
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// ������
		double leftX = 100;
		double topY = 100;
		double width = 200;
		double height = 150;

		Rectangle2D rect = new Rectangle2D.Double(leftX, topY, width, height);
		g2.setPaint(Color.RED);
		g2.fill(rect);

		// ����Բ
		Ellipse2D ellipse = new Ellipse2D.Double();
		ellipse.setFrame(rect);
		g2.setPaint(Color.WHITE);
		g2.fill(ellipse);
	}
}