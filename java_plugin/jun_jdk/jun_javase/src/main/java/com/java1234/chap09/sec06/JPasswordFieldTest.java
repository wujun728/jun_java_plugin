package com.java1234.chap09.sec06;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class JPasswordFieldTest {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("JPasswordField密码框测试");
		jFrame.setLayout(new GridLayout(2,2,10,10));
		JLabel jl=new JLabel("用户名：");
		JTextField jtf=new JTextField();
		JLabel jl2=new JLabel("密码：");
		JPasswordField jpf=new JPasswordField();
		jFrame.add(jl);
		jFrame.add(jtf);
		jFrame.add(jl2);
		jFrame.add(jpf);
		jFrame.setLocation(400, 200); // 设置容器的位置
		jFrame.setSize(250,250); // 设置容器大小
		jFrame.setVisible(true); // 让容器显示
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
