package com.yisin.dbc;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.yisin.dbc.form.MainWindow;

public class StartApp {
	JFrame frame = null;
	
	private final static class StartAppHelp {
		static StartApp app = new StartApp();
	}
	
	public static StartApp getInstance(){
		return StartAppHelp.app;
	}
	
	public void runApp(){
		
		frame = new JFrame();
        frame.setResizable(false);
        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();  
        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();  
        frame.setBounds((int)(lx - 400) / 2, (int)(ly - 300) / 2, 400, 300);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel imgLabel = new JLabel("                  初始化中...");
        imgLabel.setFont(new Font("微软雅黑", 0, 26));
        imgLabel.setBounds(0, 0, 400, 300);
        frame.setUndecorated(true);
        frame.add(imgLabel);
        frame.setVisible(true);
        
        MainWindow.main(null);
	}
	
	public void hide(){
		this.frame.setVisible(false);
	}

	public static void main(String[] args) {
		StartApp.getInstance().runApp();
		
	}
	
	class _StartApp {
	    
	}
	
}
