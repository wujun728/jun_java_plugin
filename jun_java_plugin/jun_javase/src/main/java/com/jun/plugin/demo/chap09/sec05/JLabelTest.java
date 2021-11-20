package com.jun.plugin.demo.chap09.sec05;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class JLabelTest {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("JLabel����");
		JLabel jLabel=new JLabel("JLabel���",JLabel.CENTER);
		jFrame.add(jLabel);
		jFrame.setLocation(400, 200); // ����������λ��
		jFrame.setSize(500,200); // ����������С
		jFrame.setVisible(true); // ��������ʾ
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
