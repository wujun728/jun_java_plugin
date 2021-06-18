package com.tanzhou.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
/**
 * 在java中有两种方式实现多线程，一种是继承 Thread类，一种是实现Runnable接口；
 * @author Administrator
 *
 *在实际的开发中可定是以Runnable接口为主
 *
 *1、画界面  2、绘制界面（显示静态时间） 3、调用线程刷新界面
 */


public class Demo extends JFrame implements Runnable{
	Thread clock; 
	public Demo(){
		super("数字时钟"); // 调用父类的构造函数
		
		setFont(new Font("Times",Font.BOLD,60)); // 显示调用时钟的字体
		start();
		setSize(300,100);// 设置界面大小
		setVisible(true); // 窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭窗口的时候退出程序
	}
	
	public void start(){ //开始
		if(clock==null){
			clock = new Thread(this); // 实例化进程
			// jdk提供 一旦调用start方法，则会通过JVM找到run()方法
			clock.start(); // 开始进程
		}
	}
	
	// 运行进程
	public void run() { 
		while(clock!=null){
			repaint(); // 重绘界面
			try {
				Thread.sleep(1000); // 线程暂停1000毫秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void paint(Graphics g){ // 重写组件的paint方法
		Graphics2D g2 = (Graphics2D)g; 
		Calendar now = new GregorianCalendar(); // 提供日历的系统
		String timeInfo = "";
		
		int hour = now.get(Calendar.HOUR_OF_DAY); //得到小时
		int minute = now.get(Calendar.MINUTE);
		int second = now.get(Calendar.SECOND);
		
		if(hour<=9){
			timeInfo+="0"+hour+":";
		}else{
			timeInfo+=hour+":";
		}
		
		if(minute<=9){
			timeInfo+="0"+minute+":";
		}else{
			timeInfo+=minute+":";
		}
		
		if(second<=9){
			timeInfo+="0"+second;
		}else{
			timeInfo+=second;
		}
		g.setColor(Color.white); // 设置颜色 白色
		Dimension dim = getSize(); // 得到窗口大小
		g.fillRect(0, 0, dim.width, dim.height);
		g.setColor(Color.orange); // 橙色
		g.drawString(timeInfo, 20, 80);
	}
	// 程序的入口
	public static void main(String[] args){
		new Demo();
	}
}
