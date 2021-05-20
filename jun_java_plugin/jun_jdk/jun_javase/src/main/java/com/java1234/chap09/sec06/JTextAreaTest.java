package com.java1234.chap09.sec06;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class JTextAreaTest {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("JTextArea文本域框测试");
		jFrame.setLayout(new GridLayout(1,2,10,10));
		JLabel jl=new JLabel("描述：");
		JTextArea jta=new JTextArea();
		jFrame.add(jl);
		jFrame.add(jta);
		jFrame.setLocation(400, 200); // 设置容器的位置
		jFrame.setSize(250,100); // 设置容器大小
		jFrame.setVisible(true); // 让容器显示
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
