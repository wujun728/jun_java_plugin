package com.java1234.chap09.sec08;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * 适配器
 * @author caofeng
 *
 */
class MyWindowAdapter extends WindowAdapter{

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		super.windowClosing(e);
		System.out.println("窗口关闭...........");
	}
	
	
}

public class EventTest3 {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("Swing事件");
		// MyWindowAdapter myWindowAdapter=new MyWindowAdapter();
		// jFrame.addWindowListener(myWindowAdapter);
		jFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				System.out.println("窗口关闭。。。");
			}
			
			
		});
		jFrame.setLocation(400, 200); // 设置容器的位置
		jFrame.setSize(500,200); // 设置容器大小
		jFrame.setVisible(true); // 让容器显示
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
