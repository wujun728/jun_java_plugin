package com.jun.plugin.demo.chap09.sec09;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SwingTest1 {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("Swing�ۺ�ʾ������");
		jFrame.setLayout(null); // ʹ�þ��Բ���
		final JTextField num1Txt=new JTextField();
		final JTextField num2Txt=new JTextField();
		JLabel fuHao=new JLabel("+",JLabel.CENTER);
		final JTextField resultTxt=new JTextField();
		JButton jb=new JButton("=");
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String num1=num1Txt.getText(); // ��ȡ��һ�������ı�
				String num2=num2Txt.getText(); // ��ȡ�ڶ��������ı�
				int result=Integer.parseInt(num1)+Integer.parseInt(num2);
				resultTxt.setText(result+"");
			}
		});
		num1Txt.setBounds(20, 30, 50, 25);
		fuHao.setBounds(80, 30, 40, 25);
		num2Txt.setBounds(130, 30, 50, 25);
		jb.setBounds(190, 30, 50, 25);
		resultTxt.setBounds(250, 30, 50, 25);
		jFrame.add(num1Txt);
		jFrame.add(fuHao);
		jFrame.add(num2Txt);
		jFrame.add(jb);
		jFrame.add(resultTxt);
		jFrame.setLocation(400, 200); // ����������λ��
		jFrame.setSize(350,150); // ����������С
		jFrame.setVisible(true); // ��������ʾ
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
