package com.jun.plugin.demo.chap09.sec07;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class JPanelTest {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("JPanel������");
		JPanel jPanel=new JPanel();
		jPanel.setLayout(new GridLayout(3,2,10,10));
		jPanel.setBorder(new EmptyBorder(10, 10, 10, 10));// ���ñ߾�
		jFrame.add(jPanel);
		
		JLabel jl=new JLabel("�û�����");
		JTextField jtf=new JTextField();
		JLabel jl2=new JLabel("���룺");
		JPasswordField jpf=new JPasswordField();
		JButton jb1=new JButton("��½");
		JButton jb2=new JButton("����");
		jPanel.add(jl);
		jPanel.add(jtf);
		jPanel.add(jl2);
		jPanel.add(jpf);
		jPanel.add(jb1);
		jPanel.add(jb2);
		jFrame.setLocation(400, 200); // ����������λ��
		jFrame.setSize(250,150); // ����������С
		jFrame.setVisible(true); // ��������ʾ
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
