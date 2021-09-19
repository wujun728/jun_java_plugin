package com.jun.plugin.demo.chap09.sec06;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JTextFieldTest {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("JTextField�����ı������");
		jFrame.setLayout(new GridLayout(1,2,10,10));
		JLabel jl=new JLabel("�û�����");
		JTextField jtf=new JTextField();
		jFrame.add(jl);
		jFrame.add(jtf);
		jFrame.setLocation(400, 200); // ����������λ��
		jFrame.setSize(250,60); // ����������С
		jFrame.setVisible(true); // ��������ʾ
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
