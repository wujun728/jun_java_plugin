package com.java1234.chap09.sec04;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class BorderLayoutTest {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("BorderLayout测试");
		//jFrame.setLayout(new BorderLayout());
		jFrame.setLayout(new BorderLayout(5,5));
		jFrame.add(new JButton("东"),BorderLayout.EAST);
		jFrame.add(new JButton("西"),BorderLayout.WEST);
		jFrame.add(new JButton("南"),BorderLayout.SOUTH);
		jFrame.add(new JButton("北"),BorderLayout.NORTH);
		jFrame.add(new JButton("中"),BorderLayout.CENTER);
		
		jFrame.setLocation(400, 200); // 设置容器的位置
		jFrame.setSize(500,200); // 设置容器大小
		jFrame.setVisible(true); // 让容器显示
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
