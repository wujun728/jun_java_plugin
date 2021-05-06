package com.jun.plugin.demo;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class JSplashWindow extends JWindow implements Runnable {
	Thread splashThread = null;
	
	public JSplashWindow() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		JPanel splash = new JPanel(new BorderLayout());
		// ���ͼƬ��Դ�ľ��·��
		URL url = getClass().getResource("/images/tu.gif");  
		if (url != null) { //ͼƬǶ��JLabel����ʾ
			splash.add(new JLabel(new ImageIcon(url)), BorderLayout.CENTER);
		}
		setContentPane(splash);  // �����������������ݴ���
		Dimension screen = getToolkit().getScreenSize(); //�����Ļ�Ĵ�С
		pack(); 
		//�趨������������Ļ�е�λ��
		setLocation((screen.width - getSize().width) / 2,
				(screen.height - getSize().height) / 2); 
	}

	public void start() {
		this.toFront();  // ���������ڷ��ڵ�ǰλ��
		splashThread = new Thread(this);
		splashThread.start();
	}

	public void run() {
		try {
			show();
			Thread.sleep(5000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.dispose();
	}

	static void showFrame(String title) {
		JFrame frame = new JFrame(title);
		frame.setSize(400, 300);
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

		frame.setVisible(true);
	}

	public static void main(String[] args) {
		showFrame("Demo splash window"); //��ʾ����
		JSplashWindow splash = new JSplashWindow();
		splash.start(); // ��ʾ��������,��ά��һ��ʱ��
	}
}