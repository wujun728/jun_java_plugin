package com.jun.plugin.demo.chap09.sec04;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GridLayoutTest {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("GridLayout����");
		
		jFrame.setLayout(new GridLayout(4,5,5,5));
		JButton jb=null;
		for(int i=0;i<19;i++){
			jb=new JButton("JButton"+i);
			jFrame.add(jb);
		}
		jFrame.setLocation(400, 200); // ����������λ��
		jFrame.setSize(500,200); // ����������С
		jFrame.setVisible(true); // ��������ʾ
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
