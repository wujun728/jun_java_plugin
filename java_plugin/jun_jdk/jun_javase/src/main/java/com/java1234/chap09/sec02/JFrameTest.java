package com.java1234.chap09.sec02;

import java.awt.Color;

import javax.swing.JFrame;

public class JFrameTest {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("JFrame窗体");
		/*Container c=jFrame.getContentPane();
		c.setBackground(Color.RED);*/
		jFrame.getContentPane().setBackground(Color.RED); // 设置容器的背景颜色
		jFrame.setLocation(400, 200); // 设置容器的位置
		jFrame.setSize(500,200); // 设置容器大小
		jFrame.setVisible(true); // 让容器显示
	}
}
