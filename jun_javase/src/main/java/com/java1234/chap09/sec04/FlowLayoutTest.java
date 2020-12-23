package com.java1234.chap09.sec04;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * FlowLayout流式布局
 * @author caofeng
 *
 */
public class FlowLayoutTest {

	public static void main(String[] args) {
		JFrame jFrame=new JFrame("FlowLayout测试");
		
		//jFrame.setLayout(new FlowLayout());
		//jFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
		jFrame.setLayout(new FlowLayout(FlowLayout.LEFT,15,15));
		JButton jb=null;
		for(int i=0;i<9;i++){
			jb=new JButton("JButton"+i);
			jFrame.add(jb);
		}
		
		jFrame.setLocation(400, 200); // 设置容器的位置
		jFrame.setSize(500,200); // 设置容器大小
		jFrame.setVisible(true); // 让容器显示
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
