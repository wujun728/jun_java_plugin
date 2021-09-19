package com.jun.plugin.demo.chap09.sec08;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * ������
 * @author caofeng
 *
 */
class MyWindowAdapter extends WindowAdapter{

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		super.windowClosing(e);
		System.out.println("���ڹر�...........");
	}
	
	
}

public class EventTest3 {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("Swing�¼�");
		// MyWindowAdapter myWindowAdapter=new MyWindowAdapter();
		// jFrame.addWindowListener(myWindowAdapter);
		jFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				System.out.println("���ڹرա�����");
			}
			
			
		});
		jFrame.setLocation(400, 200); // ����������λ��
		jFrame.setSize(500,200); // ����������С
		jFrame.setVisible(true); // ��������ʾ
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
