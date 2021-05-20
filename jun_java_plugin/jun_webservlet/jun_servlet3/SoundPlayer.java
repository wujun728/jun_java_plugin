package com.tanzhou.jmf;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


import javax.swing.*;
// JFrame java的GUI程序的基本思路是以JFrame为基础，它是屏幕上window的对象，能够最大化、最小化、关闭。
/**
 * ActionListener是Java中关于事件处理的一个接口，继承自EventListener。
ActionListener用于接收操作事件的侦听器接口。对处理操作事件感兴趣的类可以实现此接口，
而使用该类创建的对象可使用组件的 addActionListener 
方法向该组件注册。在发生操作事件时，调用该对象的 actionPerformed 方法。
 */
public class SoundPlayer extends JFrame implements ActionListener {
	AudioClip adc; // 声明音频剪辑对象adc

	URL url; // 声明资源定位符对象url

	JLabel lb = new JLabel();// 创建一个标签对象lb

	JLabel la = new JLabel();// 创建一个标签对象la

	File file;// 声明文件对象file

	String fileName;// 声明fileName变量，用于记录方件的名称

	boolean loop = false;// 定义loop变量，用来判断是否进行偱环操作

	JFileChooser chooser = new JFileChooser();// 创建一个文件选择器对象chooser

	//Icon a = new ImageIcon("C:\\Documents and Settings\\Administrator\\桌面\\image\\ying.jpg");// 创建一个组件图片

	JPanel pn1 = new JPanel();// 创建一个面板容器对象，默认部局为：FlowLayout

	public static void main(String[] args) {
		new SoundPlayer();// 给程序传入title
	}

	public SoundPlayer() {// 本类的构造器，起到初始化的作用，相当于Applect的init方法
		super("音乐播放器");// 调用JFrame的构造方法，其作用是创建一个新的、初始不可见的、具有指定标题的 Frame
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {// 用户单击窗口系统菜单的关闭按钮，调用dispose以执行windowClosed
				dispose();
				if (adc != null) {// 在音频剪辑adc不为null的情况下，关闭窗口的同时，声音也消失
					adc.stop();
				}
			}
		});
		JButton[] bn = new JButton[3];// 创建JButton数组
		/**
		 * FlowLayout（流式布局）       组件按照加入的先后顺序按照设置的对齐方式从左向右排列，一行排满到下一行开始继续排列
		   BorderLayout（边界布局）     容器划分为东、西、南、北、中五个区域，每个区域只能放置一个组件。
		   GridLayout（网格布局）       容器的空间划分成M×N列的网格区域, 每个区域只能放置一个组件。
		   CardLayout（卡片布局）       如同一叠牌，每个牌对应一个组件，但每次只能显示其中的一张牌。适用于在一个空间中防止多个组件的情况
		   GridBagLayout（网格包布局）  GridLayout的升级版，组件仍然是按照行、列放置，但是每个组件可以占据多个网格
		 */
		JPanel pn = new JPanel(new GridLayout());// 创建一个面板容器对象pn，部局为：GridLayout

		Icon[] ic = new Icon[3];// 创建Icon数组
		for (int i = 0; i < ic.length; i++) {// 为每个Icon对象赋图像
			ic[i] = new ImageIcon("C:\\Users\\Tony Liu\\Desktop\\image\\"
					+ (i + 1) + ".gif");
		}
		bn[0] = new JButton("开始", ic[0]);// 创建一个有文字，带图标的按扭对象
		bn[0].addActionListener(this);// 添加事件侦听
		bn[1] = new JButton("停止", ic[1]);
		bn[1].addActionListener(this);
		bn[2] = new JButton("循环", ic[2]);
		bn[2].addActionListener(this);
		pn.add(bn[0], 0);// 将开始按扭添加到面板pn的第1个位置上
		pn.add(bn[1], 1);// 同上
		pn.add(bn[2], 2);// 同上
		this.add(pn, BorderLayout.SOUTH);// 将面板pn添加到Frame中
//		// 创建播放器的菜单
		JMenu fileMenu = new JMenu("文件");
		JMenuItem openMemuItem = new JMenuItem("打开");
		openMemuItem.addActionListener(this);
		fileMenu.add(openMemuItem);
		fileMenu.addSeparator();// 添加一个分割条
		JMenuItem exitMemuItem = new JMenuItem("退出");
		exitMemuItem.addActionListener(this);
		fileMenu.add(exitMemuItem);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		String flag = "欢迎光临";
		this.setFrame(flag);// 调用setFrame方法
		this.setJMenuBar(menuBar);
		this.setSize(300, 360);// 设置Frame的大小
		this.setVisible(true);// 将Frame设置为可见

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("退出")) {// 如果单击退出菜单项，关闭windows窗口
			dispose();// 调用dispose以便执行windowClosed
			return;
		}
		if (e.getActionCommand().equals("打开")) {// 如果单击打开菜单项，选择音乐播放文件
			int val = chooser.showOpenDialog(this);// 接收文件选择器的返回状态
			if (val == JFileChooser.APPROVE_OPTION) {// 如果返回状态为APPROVE_OPTION
				file = chooser.getSelectedFile();// 返回选中的文件
				fileName = file.getName();// 获取选中的文件的文件名
				String flag = "您正在欣赏：" + fileName;
				this.setFrame(flag);
				try {
					URL url = new URL("file:" + file.getPath());// 创建资源定位符
					adc = JApplet.newAudioClip(url);// 为音频剪辑对象adc赋值
					adc.play();// 开始播放此音频剪辑
				} catch (MalformedURLException e1) {
					System.out.println("不能播放此文件");
				}
			}
		}
		// 如果用户放弃选择文件，则返回
		if (e.getActionCommand().equals("开始")) {
			String flag = "您正在欣赏：" + fileName;
			if (adc == null) {
				flag = "请选择播放文件";
				this.setFrame(flag);
				return;
			}
			adc.play();
			this.setFrame(flag);
		}
		if (e.getActionCommand().equals("停止")) {
			adc.stop();// 停止播放此音频剪辑。
			String flag = "停止播放：" + fileName;
			this.setFrame(flag);
		}
		if (e.getActionCommand().equals("循环")) {
			loop = !loop;
			String flag = "";
			if (loop) {
				adc.play();
				adc.loop();// 以循环方式开始播放此音频剪辑
				flag = "循环播放：" + fileName;
			} else {
				adc.play();
				flag = "顺序播放：" + fileName;
			}
			this.setFrame(flag);
		}

	}
    /**
     * 提取公共的部分
     * 
     * 
     * @param flag
     */
	public void setFrame(String flag) {
		la.setText(flag);
		//lb.setIcon(a);
		pn1.add(la, 0);
		//pn1.add(lb, 1);
		this.add(pn1, FlowLayout.CENTER);
	}
}
