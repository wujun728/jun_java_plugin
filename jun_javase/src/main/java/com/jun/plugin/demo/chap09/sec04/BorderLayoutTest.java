package com.jun.plugin.demo.chap09.sec04;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class BorderLayoutTest {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("BorderLayout����");
		//jFrame.setLayout(new BorderLayout());
		jFrame.setLayout(new BorderLayout(5,5));
		jFrame.add(new JButton("��"),BorderLayout.EAST);
		jFrame.add(new JButton("��"),BorderLayout.WEST);
		jFrame.add(new JButton("��"),BorderLayout.SOUTH);
		jFrame.add(new JButton("��"),BorderLayout.NORTH);
		jFrame.add(new JButton("��"),BorderLayout.CENTER);
		
		jFrame.setLocation(400, 200); // ����������λ��
		jFrame.setSize(500,200); // ����������С
		jFrame.setVisible(true); // ��������ʾ
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
