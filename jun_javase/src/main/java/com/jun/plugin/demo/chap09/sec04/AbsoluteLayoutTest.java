
package com.jun.plugin.demo.chap09.sec04;
import javax.swing.JButton;
import javax.swing.JFrame;

public class AbsoluteLayoutTest {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("���Բ��ֲ���");
		jFrame.setLayout(null);  
		JButton jb1=new JButton("��ť1");
		JButton jb2=new JButton("��ť2");
		jb1.setBounds(50, 10, 100, 20);
		jb2.setBounds(70,40,200,30);
		jFrame.add(jb1);
		jFrame.add(jb2);
		jFrame.setLocation(400, 200); // ����������λ��
		jFrame.setSize(500,200); // ����������С
		jFrame.setVisible(true); // ��������ʾ
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
