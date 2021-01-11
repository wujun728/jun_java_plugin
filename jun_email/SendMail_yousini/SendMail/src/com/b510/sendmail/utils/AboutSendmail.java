/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b510.sendmail.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * 
 * @author Hongten 菜单-帮助
 */
public class AboutSendmail extends JFrame {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 5248482602468160509L;

	public AboutSendmail(String title) {
		super(title);
		initComponents();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				AboutSendmail.this.setVisible(false);
			}
		});
	}

	/**
	 *关闭按钮
	 */
	private JButton closeButton = new JButton();
	/**
	 * 应用程序名称
	 */
	javax.swing.JLabel appTitleLabel = new javax.swing.JLabel();
	/**
	 * 版本号 前
	 */
	javax.swing.JLabel versionLabel = new javax.swing.JLabel();
	/**
	 * 版本号
	 */
	javax.swing.JLabel appVersionLabel = new javax.swing.JLabel();
	/**
	 * 主页 前
	 */
	javax.swing.JLabel homepageLabel = new javax.swing.JLabel();
	/**
	 * Homepage
	 */
	javax.swing.JLabel appHomepageLabel = new javax.swing.JLabel();
	/**
	 * 说明
	 */
	javax.swing.JLabel appDescLabel = new javax.swing.JLabel();
	/**
	 * 图片
	 */
	javax.swing.JLabel imageLabel = new javax.swing.JLabel();

	private void initComponents() {

		this.setVisible(true);
		// 设置大小不能变
		setResizable(false);
		this.setLocation(530, 410);// 设置窗体的初始位置

		closeButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				closeButton.setIcon(new ImageIcon(""));
			}

			public void mouseExited(MouseEvent evt) {
				closeButton.setIcon(new ImageIcon(""));
			}
		});

		appTitleLabel.setFont(appTitleLabel.getFont().deriveFont(
				appTitleLabel.getFont().getStyle() | java.awt.Font.BOLD,
				appTitleLabel.getFont().getSize() + 4));
		appTitleLabel.setText("应用程序名称："); // NOI18N
		appTitleLabel.setName("appTitleLabel"); // NOI18N

		versionLabel.setFont(versionLabel.getFont().deriveFont(
				versionLabel.getFont().getStyle() | java.awt.Font.BOLD));
		versionLabel.setText("版本号:"); // NOI18N
		versionLabel.setName("versionLabel"); // NOI18N

		appVersionLabel.setText("1.0"); // NOI18N
		appVersionLabel.setName("appVersionLabel"); // NOI18N

		homepageLabel.setFont(homepageLabel.getFont().deriveFont(
				homepageLabel.getFont().getStyle() | java.awt.Font.BOLD));
		homepageLabel.setText("主页:"); // NOI18N
		homepageLabel.setName("homepageLabel"); // NOI18N

		appHomepageLabel.setText("http://www.cnblogs.com/hongten"); // NOI18N
		appHomepageLabel.setName("appHomepageLabel"); // NOI18N

		appDescLabel.setText("这是一个小应用程序，定时向目标邮箱发送邮件"); // NOI18N
		appDescLabel.setName("appDescLabel"); // NOI18N

		imageLabel.setIcon(new ImageIcon("")); // NOI18N
		imageLabel.setName("imageLabel"); // NOI18N

		closeButton.setIcon(new ImageIcon(""));
		closeButton.setText("hongten");
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addComponent(imageLabel)
										.addGap(18, 18, 18)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																layout
																		.createSequentialGroup()
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								versionLabel)
																						.addComponent(
																								homepageLabel))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								appVersionLabel)
																						.addComponent(
																								appHomepageLabel)))
														.addComponent(
																appTitleLabel,
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																appDescLabel,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																266,
																Short.MAX_VALUE)
														.addComponent(
																closeButton))
										.addContainerGap()));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(imageLabel,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addGroup(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(appTitleLabel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												appDescLabel,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																versionLabel)
														.addComponent(
																appVersionLabel))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																homepageLabel)
														.addComponent(
																appHomepageLabel))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												19, Short.MAX_VALUE)
										.addComponent(closeButton)
										.addContainerGap()));
		pack();
	}
}
