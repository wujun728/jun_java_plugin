package book.net.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

/**
 * 聊天室的服务器端程序，GUI界面
 */
public class ChatServer extends JFrame {

	// 状态栏标签
	static JLabel statusBar = new JLabel();
	// 显示客户端的连接信息的列表
	static java.awt.List connectInfoList = new java.awt.List(10);

	// 保存当前处理客户端请求的处理器线程
	static Vector clientProcessors = new Vector(10);
	// 当前的连接数
	static int activeConnects = 0;

	// 构造方法
	public ChatServer()	{
		init();
		try {
			// 设置界面为系统默认外观
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init(){

		this.setTitle("聊天室服务器");
		statusBar.setText("");

		// 初始化菜单
		JMenu fileMenu = new JMenu();
		fileMenu.setText("文件");
		JMenuItem exitMenuItem = new JMenuItem();
		exitMenuItem.setText("退出");
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitActionPerformed(e);
			}
		});
		fileMenu.add(exitMenuItem);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);

		// 将组件进行布局
		JPanel jPanel1 = new JPanel();
		jPanel1.setLayout(new BorderLayout());
		JScrollPane pane = new JScrollPane(connectInfoList);
		pane.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.white, new Color(134, 134, 134)), "客户端连接信息"));
		jPanel1.add(new JScrollPane(pane), BorderLayout.CENTER);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);
		this.getContentPane().add(jPanel1, BorderLayout.CENTER);

		this.pack();
	}

	/**
	 * 退出菜单项事件
	 * @param e
	 */
	public void exitActionPerformed(ActionEvent e){
		//	向客户端发送断开连接信息
		sendMsgToClients(new StringBuffer(Constants.QUIT_IDENTIFER)); 
		// 关闭所有的连接
		closeAll(); 
		System.exit(0);
	}

	/**
	 * 处理窗口关闭事件
	 */
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			exitActionPerformed(null);
		}
	}

	/**
	 * 刷新聊天室，不断刷新clientProcessors，制造最新的用户列表
	 */
	public static void notifyRoomPeople(){
		StringBuffer people = new StringBuffer(Constants.PEOPLE_IDENTIFER);
		for (int i = 0; i < clientProcessors.size(); i++) {
			ClientProcessor c = (ClientProcessor) clientProcessors.elementAt(i);
			people.append(Constants.SEPERATOR).append(c.clientName);
		}
		//	用sendClients方法向客户端发送用户列表的信息
		sendMsgToClients(people); 
	}

	/**
	 * 向所有客户端群发消息
	 * @param msg
	 */
	public static synchronized void sendMsgToClients(StringBuffer msg) {
		for (int i = 0; i < clientProcessors.size(); i++) {
			ClientProcessor c = (ClientProcessor) clientProcessors.elementAt(i);
			System.out.println("send msg: " + msg);
			c.send(msg);
		}
	}

	/**
	 * 关闭所有连接
	 */
	public static void closeAll(){
		while (clientProcessors.size() > 0)	{
			ClientProcessor c = (ClientProcessor) clientProcessors.firstElement();
			try {
				// 关闭socket连接和处理线程
				c.socket.close();
				c.toStop();
			} catch (IOException e) {
				System.out.println("Error:" + e);
			} finally {
				clientProcessors.removeElement(c);
			}
		}
	}

	/**
	 * 判断客户端是否合法。
	 * 不允许同一客户端重复登陆，所谓同一客户端是指IP和名字都相同。
	 * @param newclient
	 * @return
	 */
	public static boolean checkClient(ClientProcessor newclient){
		if (clientProcessors.contains(newclient)){
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 断开某个连接，并且从连接列表中删除
	 * @param client
	 */
	public static void disconnect(ClientProcessor client){
		disconnect(client, true);
	}
	
	/**
	 * 断开某个连接，根据要求决定是否从连接列表中删除
	 * @param client
	 * @param toRemoveFromList
	 */
	public static synchronized void disconnect(ClientProcessor client, boolean toRemoveFromList){
		try {
			 //在服务器端程序的list框中显示断开信息
			connectInfoList.add(client.clientIP + "断开连接");

			ChatServer.activeConnects--; //将连接数减1
			String constr = "目前有" + ChatServer.activeConnects + "客户相连";
			statusBar.setText(constr);
			//向客户发送断开连接信息
			client.send(new StringBuffer(Constants.QUIT_IDENTIFER)); 
			client.socket.close();

		} catch (IOException e) {
			System.out.println("Error:" + e);
		} finally {
			//从clients数组中删除此客户的相关socket等信息， 并停止线程。
			if (toRemoveFromList) {
				clientProcessors.removeElement(client);
				client.toStop();
			}
		}
	}
	
	public static void main(String[] args) {

		ChatServer chatServer1 = new ChatServer();
		chatServer1.setVisible(true);
		System.out.println("Server starting ...");

		ServerSocket server = null;
		try {
			// 服务器端开始侦听
			server = new ServerSocket(Constants.SERVER_PORT);
		} catch (IOException e) {
			System.out.println("Error:" + e);
			System.exit(1);
		}
		while (true) {
			// 如果当前客户端数小于MAX_CLIENT个时接受连接请求
			if (clientProcessors.size() < Constants.MAX_CLIENT) {
				Socket socket = null;
				try {
					// 收到客户端的请求
					socket = server.accept();
					if (socket != null) {
						System.out.println(socket + "连接");
					}
				} catch (IOException e) {
					System.out.println("Error:" + e);
				}

				//	定义并实例化一个ClientProcessor线程类，用于处理客户端的消息
				ClientProcessor c = new ClientProcessor(socket);
				if (checkClient(c)) {
					// 添加到列表
					clientProcessors.addElement(c);
					// 如果客户端合法，则继续
					int connum = ++ChatServer.activeConnects;
					// 在状态栏里显示连接数
					String constr = "目前有" + connum + "客户相连";
					ChatServer.statusBar.setText(constr);
					// 将客户socket信息写入list框
					ChatServer.connectInfoList.add(c.clientIP + "连接"); 
					c.start();
					// 通知所有客户端用户列表发生变化
					notifyRoomPeople();
				} else {
					//如果客户端不合法
					c.ps.println("不允许重复登陆");
					disconnect(c, false);
				}

			} else {
				//如果客户端超过了MAX_CLIENT个，则等待一段时间再尝试接受请求
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}

/**
 * 处理客户端发送的请求的线程
 */
class ClientProcessor extends Thread {
	//存储一个连接客户端的socket信息
	Socket socket;
	//存储客户端的连接姓名
	String clientName;

	//存储客户端的ip信息
	String clientIP;
	
	//用来实现接受从客户端发来的数据流
	BufferedReader br;
	//用来实现向客户端发送信息的打印流
	PrintStream ps; 

	boolean running = true;
	
	/**
	 * 构造方法
	 * @param s
	 */
	public ClientProcessor(Socket s) {
		socket = s;
		try {
			//	初始化输入输出流
			br = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			ps = new PrintStream(socket.getOutputStream()); 
			// 读取收到的信息，第一条信息是客户端的名字、IP信息
			String clientInfo = br.readLine();
			
			// 读取信息，根据消息分隔符解析消息
			StringTokenizer stinfo = new StringTokenizer(clientInfo, Constants.SEPERATOR);
			String head = stinfo.nextToken(); 
			if (head.equals(Constants.CONNECT_IDENTIFER)){
				if (stinfo.hasMoreTokens()){
					//关键字后的第二段数据是客户名信息
					clientName = stinfo.nextToken(); 
				}
				if (stinfo.hasMoreTokens()){
					//关键字后的第三段数据是客户ip信息
					clientIP = stinfo.nextToken(); 
				}
				System.out.println(head); //在控制台打印头信息
			}
		} catch (IOException e) {
			System.out.println("Error:" + e);
		}
	}

	/**
	 * 向客户端发送消息
	 * @param msg
	 */
	public void send(StringBuffer msg)	{
		ps.println(msg); 
		ps.flush();
	}
	
	//线程运行方法
	public void run() {

		while (running) {
			String line = null;
			try {
				//读取客户端发来的数据流
				line = br.readLine();

			} catch (IOException e) {
				System.out.println("Error" + e);
				ChatServer.disconnect(this);
				ChatServer.notifyRoomPeople();
				return;
			}
			 //客户已离开
			if (line == null){
				ChatServer.disconnect(this);
				ChatServer.notifyRoomPeople();
				return;
			}

			StringTokenizer st = new StringTokenizer(line, Constants.SEPERATOR);
			String keyword = st.nextToken();

			// 如果关键字是MSG则是客户端发来的聊天信息
			if (keyword.equals(Constants.MSG_IDENTIFER)){
				StringBuffer msg = new StringBuffer(Constants.MSG_IDENTIFER).append(Constants.SEPERATOR);
				msg.append(clientName);
				msg.append(st.nextToken("\0"));
				//	再将某个客户发来的聊天信息发送到每个连接客户的聊天栏中
				ChatServer.sendMsgToClients(msg); 
				
			} else if (keyword.equals(Constants.QUIT_IDENTIFER)) {
				//	如果关键字是QUIT则是客户端发来断开连接的信息
				
				//	服务器断开与这个客户的连接
				ChatServer.disconnect(this); 
				//	继续监听聊天室并刷新其他客户的聊天人名list
				ChatServer.notifyRoomPeople(); 
				running = false;
			}
		}
	}
	
	public void toStop(){
		running = false;
	}
	
	// 覆盖Object类的equals方法
	public boolean equals(Object obj){
		if (obj instanceof ClientProcessor){
			ClientProcessor obj1 = (ClientProcessor)obj;
			if (obj1.clientIP.equals(this.clientIP) && 
					(obj1.clientName.equals(this.clientName))){
				return true;
			}
		}
		return false;
	}
	
	// 覆盖Object类的hashCode方法
	public int hashCode(){
		return (this.clientIP + Constants.SEPERATOR + this.clientName).hashCode();
	}
} 