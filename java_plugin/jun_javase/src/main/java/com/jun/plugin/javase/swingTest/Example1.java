package com.jun.plugin.javase.swingTest;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class Example1 extends JFrame {
	public void CreateJFrame(String title){
		JFrame jf=new JFrame(title);
		Container container=jf.getContentPane();
		JLabel jl=new JLabel("����һ������");
		
		jl.setHorizontalAlignment(SwingConstants.CENTER);
		container.add(jl);
		container.setBackground(Color.white);
		jf.setVisible(true);
		jf.setSize(1200,1500);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new Example1().CreateJFrame("����һ������");
	}
}
