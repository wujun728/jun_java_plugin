package book.net.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

/**
 * 聊天室的客户端程序，GUI界面。
 */
public class ChatClient extends JFrame implements ActionListener{
	
	// 登陆聊天室的名字标签和输入框
	JLabel nameLabel = new JLabel();
	JTextField nameTextField = new JTextField(15);

	// 连接和断开连接的按钮
	JButton connectButton = new JButton();
	JButton disConnectButton = new JButton();

	// 聊天室内容的文本域
	JTextArea chatContentTextArea = new JTextArea(9, 30);

	// 发送消息的按钮
	JButton sendMsgButton = new JButton();
	// 消息输入框
	JTextField msgTextField = new JTextField(20);
	JLabel msglabel = new JLabel();
	// 聊天室用户列表
	java.awt.List peopleList = new java.awt.List(10);

	/*以下定义数据流和网络变量*/
	Socket soc = null; 
	PrintStream ps = null; 
	
	// 客户端侦听服务器消息的线程
	ClentListener listener = null; 

	public ChatClient() {
		init();
	}

	//	初始化图形界面
	public void init() {

		this.setTitle("聊天室客户端");
		
		// 初始化按钮和标签
		nameLabel.setText("姓名：");
		connectButton.setText("连 接");
		connectButton.addActionListener(this);
		disConnectButton.setText("断 开");
		disConnectButton.addActionListener(this);
		// 设置聊天内容不可编辑
		chatContentTextArea.setEditable(false);
		sendMsgButton.setText("发 送");
		sendMsgButton.addActionListener(this);
		msgTextField.setText("请输入聊天信息");
		
		//panel1放置输入姓名和连接两个按钮
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.add(nameLabel);
		panel1.add(nameTextField);
		panel1.add(connectButton);
		panel1.add(disConnectButton);
		
		//用于放置聊天信息显示和聊天人员列表
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		JScrollPane pane1 = new JScrollPane(chatContentTextArea);
		pane1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.white, new Color(134, 134, 134)), "聊天内容"));
		panel2.add(pane1);
		JScrollPane pane2 = new JScrollPane(peopleList);
		pane2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.white, new Color(134, 134, 134)), "用户列表"));
		panel2.add(pane2);
		
		//用于放置发送信息区域
		JPanel panel3 = new JPanel(); 
		panel3.setLayout(new FlowLayout());
		panel3.add(msglabel);
		panel3.add(msgTextField);
		panel3.add(sendMsgButton);
		
		// 将组件添加到界面
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panel1, BorderLayout.NORTH);
		this.getContentPane().add(panel2, BorderLayout.CENTER);
		this.getContentPane().add(panel3, BorderLayout.SOUTH);
		
		this.pack();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭聊天室客户端事件
	 */
	protected void processWindowEvent(WindowEvent e){
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			// 如果是关闭聊天室客户端，则断开连接
			disconnect();
			dispose();
			System.exit(0);
		}
	}
	
	/**
	 * 处理按钮事件
	 */
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == connectButton){
			// 如果点击连接按钮
			if (soc == null) {
				try {
					// 使用端口2525实例化一个本地套接字
					soc = new Socket(InetAddress.getLocalHost(), Constants.SERVER_PORT); 
					// 在控制台打印实例化的结果
					System.out.println(soc); 
					//将ps指向soc的输出流
					ps = new PrintStream(soc.getOutputStream()); 
					//定义一个字符缓冲存储发送信息
					StringBuffer info = new StringBuffer(Constants.CONNECT_IDENTIFER).append(Constants.SEPERATOR); 
					//其中INFO为关键字让服务器识别为连接信息
					//并将name和ip用"："分开，在服务器端将用一个
					//StringTokenizer类来读取数据
					String userinfo = nameTextField.getText() + Constants.SEPERATOR
							+ InetAddress.getLocalHost().getHostAddress();
					ps.println(info.append(userinfo));

					ps.flush();
					//将客户端线程实例化，并启动
					listener = new ClentListener(this, nameTextField.getText(), soc);   
					listener.start(); 
				} catch (IOException e) {
					System.out.println("Error:" + e);
					disconnect();
				}
			}
			
		} else if (source == disConnectButton){
			// 如果点击断开连接按钮
			disconnect();
			
		} else if (source == sendMsgButton) {
			//如果点击发送按钮
			if (soc != null) {
				 //定义并实例化一个字符缓冲存储发送的聊天信息
				StringBuffer msg = new StringBuffer(Constants.MSG_IDENTIFER).append(Constants.SEPERATOR);
				//用打印流发送聊天信息
				ps.println(msg.append(msgTextField.getText())); 
				ps.flush();
			}
		}
	}

	/**
	 * 断开与服务器的连接
	 */
	public void disconnect(){
		if (soc != null) {
			try {
				// 用打印流发送QUIT信息通知服务器断开此次通信
				ps.println(Constants.QUIT_IDENTIFER); 
				ps.flush();
				soc.close(); //关闭套接字
				listener.toStop();
				soc = null;
			} catch (IOException e) {
				System.out.println("Error:" + e);
			} 
		}
	}
	
	public static void main(String[] args){
		ChatClient client = new ChatClient();
		client.setVisible(true);
	}

	/**
	 * 客户端线程类用来监听服务器传来的信息
	 */
	class ClentListener extends Thread	{
		//存储客户端连接后的name信息
		String name = null;
		//客户端接受服务器数据的输入流
		BufferedReader br = null;
		//实现从客户端发送数据到服务器的打印流
		PrintStream ps = null;

		//存储客户端的socket信息
		Socket socket = null;
		//存储当前运行的ChatClient实例
		ChatClient parent = null;

		boolean running = true;

		//构造方法
		public ClentListener(ChatClient p, String n, Socket s)	{

			//接受参数
			parent = p;
			name = n;
			socket = s;

			try {
				//实例化两个数据流
				br = new BufferedReader(new InputStreamReader(s
						.getInputStream()));
				ps = new PrintStream(s.getOutputStream());

			} catch (IOException e) {
				System.out.println("Error:" + e);
				parent.disconnect();
			}
		} 
		
		// 停止侦听
		public void toStop(){
			this.running = false;
		}

		//线程运行方法
		public void run(){
			String msg = null;
			while (running) {
				msg = null;
				try {
					// 读取从服务器传来的信息
					msg = br.readLine();
					System.out.println("receive msg: " + msg);
				} catch (IOException e) {
					System.out.println("Error:" + e);
					parent.disconnect();
				}
				// 如果从服务器传来的信息为空则断开此次连接
				if (msg == null) {
					parent.listener = null;
					parent.soc = null;
					parent.peopleList.removeAll();
					running = false;
					return;
				}
				
				//用StringTokenizer类来实现读取分段字符
				StringTokenizer st = new StringTokenizer(msg, Constants.SEPERATOR); 
				//读取信息头即关键字用来识别是何种信息
				String keyword = st.nextToken(); 

				if (keyword.equals(Constants.PEOPLE_IDENTIFER)) {
					//如果是PEOPLE则是服务器发来的客户连接信息
					//主要用来刷新客户端的用户列表
					parent.peopleList.removeAll();
					//遍历st取得目前所连接的客户
					while (st.hasMoreTokens()) 	{
						String str = st.nextToken();
						parent.peopleList.add(str);
					}
					
				} else if (keyword.equals(Constants.MSG_IDENTIFER)) {
					//如果关键字是MSG则是服务器传来的聊天信息, 
					//主要用来刷新客户端聊天信息区将每个客户的聊天内容显示出来
					String usr = st.nextToken();
					parent.chatContentTextArea.append(usr);
					parent.chatContentTextArea.append(st.nextToken("\0"));
					parent.chatContentTextArea.append("\r\n");
					
				} else if (keyword.equals(Constants.QUIT_IDENTIFER)) {
					//如果关键字是QUIT则是服务器关闭的信息, 切断此次连接
					System.out.println("Quit");
					try {
						running = false;
						parent.listener = null;
						parent.soc.close();
						parent.soc = null;
					} catch (IOException e) {
						System.out.println("Error:" + e);
					} finally {
						parent.soc = null;
						parent.peopleList.removeAll();
					}
					
					break;
				}
			}
			//清除用户列表
			parent.peopleList.removeAll();
		}
	} 
} 
