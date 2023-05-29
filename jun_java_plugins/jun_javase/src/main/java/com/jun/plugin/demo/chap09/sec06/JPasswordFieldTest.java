package com.jun.plugin.demo.chap09.sec06;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class JPasswordFieldTest {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("JPasswordField��������");
		jFrame.setLayout(new GridLayout(2,2,10,10));
		JLabel jl=new JLabel("�û�����");
		JTextField jtf=new JTextField();
		JLabel jl2=new JLabel("���룺");
		JPasswordField jpf=new JPasswordField();
		jFrame.add(jl);
		jFrame.add(jtf);
		jFrame.add(jl2);
		jFrame.add(jpf);
		jFrame.setLocation(400, 200); // ����������λ��
		jFrame.setSize(250,250); // ����������С
		jFrame.setVisible(true); // ��������ʾ
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
