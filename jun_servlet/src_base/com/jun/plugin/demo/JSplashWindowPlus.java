package com.jun.plugin.demo;
// JSplashWindowPlus.java

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

public class JSplashWindowPlus extends JWindow implements Runnable {
	Thread splashThread = null;
	private JProgressBar progress;

	public JSplashWindowPlus() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		JPanel splash = new JPanel(new BorderLayout());
		URL url = getClass().getResource("/images/tu.gif");
		if (url != null) {
			splash.add(new JButton(new ImageIcon(url)), BorderLayout.CENTER);
		}
		progress = new JProgressBar(1, 100);
		// ���ý��������ʱ����ʾ�ַ�
		progress.setStringPainted(true);
		// ���ý�����߿�
		progress.setBorderPainted(true);
		progress.setString("Program is Now Loading...");
		progress.setBackground(Color.white);
		// ��������������ݴ�����
		splash.add(progress, BorderLayout.SOUTH);
		setContentPane(splash);
		Dimension screen = getToolkit().getScreenSize();
		pack();
		// ������ʾλ��
		setLocation((screen.width - getSize().width) / 2,
				(screen.height - getSize().height) / 2);
	}

	public void start() {
		this.toFront();
		splashThread = new Thread(this);
		splashThread.start();
	}

	public void run() {
		show();
		try {
			for (int i = 0; i < 100; i++) {
				Thread.sleep(100);
				progress.setValue(progress.getValue() + 1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		dispose();
	}

	static void showFrame(String title) {
		JFrame frame = new JFrame(title);
		frame.setSize(640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ������������Ļ����
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);

		try {
			Thread.sleep(10000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		JSplashWindowPlus splash = new JSplashWindowPlus();
		splash.start();
		showFrame("Splash window demo");
	}
}