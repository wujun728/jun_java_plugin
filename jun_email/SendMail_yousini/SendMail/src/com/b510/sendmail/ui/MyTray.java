package com.b510.sendmail.ui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MyTray implements ActionListener, MouseListener {
	private Image icon;// 图标
	private TrayIcon trayIcon;
	private SystemTray systemTray;// 系统托盘

	private SendMailUI sendMailUI; // 托盘所属主窗体
	private PopupMenu pop = new PopupMenu(); // 弹出菜单
	// 菜单选项
	/** 还原 */
	private MenuItem open = new MenuItem("Restore");
	/** 退出*/
	private MenuItem exit =new MenuItem("Exit");
	public MyTray(SendMailUI sendMailUI) {
		this.sendMailUI = sendMailUI;
		// 得到托盘的图标
		icon = new ImageIcon(this.getClass().getClassLoader().getResource(
				"resources/mytray.png")).getImage();

		if (SystemTray.isSupported()) {
			systemTray = SystemTray.getSystemTray();
			// 设置鼠标经过图标时，显示的内容
			trayIcon = new TrayIcon(icon, "YouSiNi", pop);
			pop.add(open);
			pop.add(exit);
			// 添加系统托盘
			try {
				systemTray.add(trayIcon);
			} catch (AWTException e1) {
				e1.printStackTrace();
				trayIcon.addMouseListener(this);
			}
		}
		trayIcon.addMouseListener(this);
		exit.addActionListener(this);
		open.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==exit){
			//退出系统
			System.exit(0);
		}else if (e.getSource() == open) {
			// 单点击菜单中的"还原"选项时，还原窗口
			sendMailUI.iconed = false;
			friendListSet(true);
		} 
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// 但鼠标点击一次的时候，进行弹出窗口
		if (e.getClickCount() == 1 && e.getButton() != MouseEvent.BUTTON3) {
			if (!sendMailUI.isVisible()) {
				friendListSet(true);
			} else {
				friendListSet(false);
			}
		}
		// 但鼠标点击两次的时候，进行弹出窗口
		// 如果窗口有显示，则隐藏窗口，否则显示窗口
		if (e.getClickCount() == 2 && e.getButton() != MouseEvent.BUTTON3) {
			if (!sendMailUI.isVisible()) {
				friendListSet(true);
			} else {
				friendListSet(false);
			}
		}
	}

	/**
	 * 设置friendList的可见性
	 */
	private void friendListSet(boolean flag) {
		sendMailUI.setVisible(true);
		sendMailUI.setExtendedState(JFrame.NORMAL);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
