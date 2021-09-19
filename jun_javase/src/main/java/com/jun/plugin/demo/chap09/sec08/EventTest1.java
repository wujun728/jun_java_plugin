package com.jun.plugin.demo.chap09.sec08;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

class JButtonListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
	    System.out.println(e.getActionCommand());
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "�ұ������");
	}
	
}

public class EventTest1 {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("Swing�¼�");
		JButton jb=new JButton("����һ����ť");
		JButtonListener jButtonListener=new JButtonListener();
		jb.addActionListener(jButtonListener);  //  ���/ע���¼�����
		jFrame.add(jb);
		jFrame.setLocation(400, 200); // ����������λ��
		jFrame.setSize(500,200); // ����������С
		jFrame.setVisible(true); // ��������ʾ
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
