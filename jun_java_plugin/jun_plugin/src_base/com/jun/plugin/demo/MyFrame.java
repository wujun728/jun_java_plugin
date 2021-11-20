package com.jun.plugin.demo;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
public class MyFrame extends JFrame {
	public MyFrame() {
		JButton btn = new JButton("你好");
		System.err.println("btn: is:"+btn.hashCode());
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.err.println("eee:+"+e.getSource().hashCode());
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//获取容器
		Container con= getContentPane();
		//设置布局
		con.setLayout(new FlowLayout());
		con.add(btn);
		
		setSize(300, 300);
		setVisible(true);
	}
	public static void main(String[] args) {
		new MyFrame();
	}
}
