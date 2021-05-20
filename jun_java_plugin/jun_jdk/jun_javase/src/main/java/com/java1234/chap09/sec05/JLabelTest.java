package com.java1234.chap09.sec05;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class JLabelTest {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("JLabel测试");
		JLabel jLabel=new JLabel("JLabel组件",JLabel.CENTER);
		jFrame.add(jLabel);
		jFrame.setLocation(400, 200); // 设置容器的位置
		jFrame.setSize(500,200); // 设置容器大小
		jFrame.setVisible(true); // 让容器显示
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
