package com.jun.plugin.demo.chap09.sec03;

import javax.swing.JButton;
import javax.swing.JFrame;

public class JButtonTest {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("JButton����");
		JButton jb=new JButton("����һ����ť");
		jFrame.add(jb);
		
		jFrame.setLocation(400, 200); // ����������λ��
		jFrame.setSize(500,200); // ����������С
		jFrame.setVisible(true); // ��������ʾ
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
