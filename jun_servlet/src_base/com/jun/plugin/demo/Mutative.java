package com.jun.plugin.demo;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Mutative {
	public static void main(String args[]) {
		Mywin win = new Mywin();
		win.pack();
	}
}

class Mywin extends Frame implements Runnable {
	Button b = new Button("ok");
	int x = 5;
	Thread thread = null;
	Mywin() {
		setBounds(100, 100, 200, 200);
		setLayout(new FlowLayout());
		setVisible(true);
		add(b);
		b.setBackground(Color.green);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
        // ����һ���µ��̣߳�������Ŀ��������߳�threadʵ�ֽӿ�Runnable��
		thread = new Thread(this); 
        // �ڴ�������ʱ�ֿ�ʼ���߳�thread��
		thread.start(); 
	}
	
	public void run() 
	{
		while (true) {
			x = x + 1;
			if (x > 100)
				x = 5;
			b.setBounds(40, 40, x, x);
			try {
				thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}
	}
}
