package com.java1234.chap09.sec06;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JTextFieldTest {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("JTextField单行文本框测试");
		jFrame.setLayout(new GridLayout(1,2,10,10));
		JLabel jl=new JLabel("用户名：");
		JTextField jtf=new JTextField();
		jFrame.add(jl);
		jFrame.add(jtf);
		jFrame.setLocation(400, 200); // 设置容器的位置
		jFrame.setSize(250,60); // 设置容器大小
		jFrame.setVisible(true); // 让容器显示
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
