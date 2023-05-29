package com.jun.plugin.demo.chap09.sec06;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class JTextAreaTest {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("JTextArea�ı�������");
		jFrame.setLayout(new GridLayout(1,2,10,10));
		JLabel jl=new JLabel("������");
		JTextArea jta=new JTextArea();
		jFrame.add(jl);
		jFrame.add(jta);
		jFrame.setLocation(400, 200); // ����������λ��
		jFrame.setSize(250,100); // ����������С
		jFrame.setVisible(true); // ��������ʾ
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
