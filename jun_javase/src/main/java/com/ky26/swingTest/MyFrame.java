package com.ky26.swingTest;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

class MyJDialog extends JDialog {
	public MyJDialog(MyFrame frame){
		super(frame,"第一个dialog窗体",true);
		Container container=new Container();
		container.add(new JLabel("这是一个对话框"));
		setBounds(120, 120, 100, 100);
	}
}


public class MyFrame extends JFrame{
	public static void main(String[] args) {
		new MyFrame();
	}
	public MyFrame(){
		Container container=new Container();
		container.setLayout(null);
		JLabel jl=new JLabel("这是一个jf窗体");
		jl.setHorizontalAlignment(SwingConstants.CENTER);
		container.add(jl);
		JButton bl=new JButton("弹出对话框");
		bl.setBounds(10,10,100,21);
		bl.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new MyJDialog(MyFrame.this).setVisible(true);
			}
		});
		container.add(bl);
	}
}
