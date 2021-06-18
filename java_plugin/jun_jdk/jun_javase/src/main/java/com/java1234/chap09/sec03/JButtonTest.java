package com.java1234.chap09.sec03;

import javax.swing.JButton;
import javax.swing.JFrame;

public class JButtonTest {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("JButton测试");
		JButton jb=new JButton("这是一个按钮");
		jFrame.add(jb);
		
		jFrame.setLocation(400, 200); // 设置容器的位置
		jFrame.setSize(500,200); // 设置容器大小
		jFrame.setVisible(true); // 让容器显示
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
