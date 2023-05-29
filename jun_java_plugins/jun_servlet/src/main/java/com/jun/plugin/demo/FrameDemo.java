package com.jun.plugin.demo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FrameDemo {
	public static void main(String args[]){
		JFrame frame = new JFrame("������ʾ");
		// ʹ�ô��ڼ������������
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		JLabel label = new JLabel("JFrame"); //����һ���ı���ǩ
		label.setPreferredSize(new Dimension(200,100)); //�趨label�Ĵ�С
		frame.getContentPane().add(label, BorderLayout.CENTER); //���ò���
		frame.pack();   // ʹJFrame�Ĵ�С�Զ��ʺ��������С
		frame.setVisible(true); //�趨Ϊ�ɼ��
	}
}