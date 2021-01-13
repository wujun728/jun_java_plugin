package book.net;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * 这个类实现了一个灵活的、支持多线程的服务器的通用框架。
 * 它能够侦听任何端口，当收到来自某个端口的连接请求时，
 * 将连接的输入和输出传递给特定的服务对象。由服务对象处理请求。
 * 支持一定数量的并发访问，支持日志功能，将日志写到输出流中。
 **/
public class GeneralServer {

	// 服务器上行的分割符
	// 出于java的安全限制，System.getProperty("line.seperator")是不能够直接取得的
	// 通过下面的方法获取
	public static final String LINE_SEPERATOR = (String) java.security.AccessController
			.doPrivileged(new sun.security.action.GetPropertyAction(
					"line.separator")); 
	// 帮助信息，指示启动服务器必须带有参数，
	// 参数包括：
	// (1)服务器启动的服务的类名、服务对应的端口号
	// (2)如果需要对服务器进行控制，则需要指定控制密码和端口。
	public static final String HELP_MESSAGE = "Usage: java book.net.GeneralServer "
				+ "[-control <password> <port>] "
				+ "[<servicename> <port> ... ]";
	
	// 保存侦听器及其侦听端口的映射
	Map services; 
	// 保存当前的连接信息
	Set connections; 
	// 支持的最大并发连接数
	int maxConnections; 
	// 管理服务器启动的所有线程
	ThreadGroup threadGroup;
	// 日志消息的输出流
	PrintWriter logStream;

	/**
	 * 构造方法
	 * 指定日志消息输出流和最大并发连接数。
	 **/
	public GeneralServer(OutputStream logStream, int maxConnections) {
		// 初始化各实例变量
		this.setLogStream(logStream);
		this.log("Starting server");
		// 创建一个线程组，所有启动的线程都在该组内
		this.threadGroup = new ThreadGroup(GeneralServer.class.getName());
		this.maxConnections = maxConnections;
		this.services = new HashMap();
		this.connections = new HashSet(maxConnections);
	}

	/** 
	 * 设置日志消息输出流，允许参数为null
	 **/
	public synchronized void setLogStream(OutputStream out) {
		if (out != null){
			this.logStream = new PrintWriter(out);
		} else {
			this.logStream = null;
		}
	}
	/**
	 * 写字符串类型的日志信息到日志输出流。
	 */
	protected synchronized void log(String s) {
		if (this.logStream != null) {
			this.logStream.println("[" + new Date() + "] " + s);
			this.logStream.flush();
		}
	}
	/**
	 * 写对象类型的日志信息到日志输出流
	 */
	protected void log(Object o) {
		this.log(o.toString());
	}

	/**
	 * 服务器启动一个新服务，该服务对象运行在指定的端口上。
	 * @param service	待启动的服务对象
	 * @param port		服务对象使用的端口
	 * @throws IOException
	 */
	public synchronized void addService(Service service, int port)
			throws IOException {
		// 首先判断该端口是否已经被服务器使用了
		Integer key = new Integer(port);
		if (this.services.get(key) != null)
			throw new IllegalArgumentException("Port " + port
					+ " already in use.");
		// 为服务和端口创建一个侦听器，侦听连接请求
		Listener listener = new Listener(threadGroup, port, service);
		// 将端口和侦听器保存
		this.services.put(key, listener);
		// 写日志
		this.log("Starting service " + service.getClass().getName() + " on port "
				+ port);
		// 启动侦听器
		listener.start();
	}

	/**
	 * 服务器停止一个服务，它不会中止任何已经接受了的连接，
	 * 但是会使服务器停止接受关于该端口的连接请求
	 * @param port	待停止服务的端口
	 */
	public synchronized void removeService(int port) {
		// 找到该端口上的侦听器
		Integer key = new Integer(port);
		final Listener listener = (Listener) services.get(key);
		// 将侦听器停止
		if (listener == null) {
			return;
		}
		listener.pleaseStop();
		// 将端口上的服务去掉
		this.services.remove(key);
		// 写日志
		this.log("Stopping service " + listener.service.getClass().getName()
				+ " on port " + port);
	}

	/**
	 * 启动服务器的方法，需要配置参数。
	 */
	public static void main(String[] args) {
		
		try {
			// 参数数目必须大于等于2。
			if (args.length < 2) // Check number of arguments
				throw new IllegalArgumentException("Must specify a service");

			// 本例使用标准的输出流当作日志信息输出流，同时连接数最大为10
			GeneralServer server = new GeneralServer(System.out, 10);

			// 解析参数
			int i = 0;
			while (i < args.length) {
				// 处理-control参数
				if (args[i].equals("-control")) {
					i++;
					// 获取控制的密码
					String password = args[i++];
					// 获取控制的端口
					int port = Integer.parseInt(args[i++]);
					// 加载控制服务实例，在端口上工作。
					server.addService(new Control(server, password), port);
				} else {
					// 处理初始启动的服务参数，并动态加载服务实例
					// 获取服务的类名
					String serviceName = args[i++];
					// 根据服务类名生成实例
					Class serviceClass = Class.forName(serviceName);
					Service service = (Service) serviceClass.newInstance();
					// 获取端口
					int port = Integer.parseInt(args[i++]);
					// 启动服务
					server.addService(service, port);
				}
			}
		} catch (Exception e) {
			// 参数错误
			System.err.println("Server: " + e);
			System.err.println(HELP_MESSAGE);
			System.exit(1);
		}
	}
	
	/**
	 * 增加一个连接。
	 * 当侦听器收到客户端的连接请求时，会调用该方法。
	 * 这里会创建一个连接对象，并保存，如果连接数已满，则关闭连接。
	 * @param s		连接的客户端socket
	 * @param service	连接请求的服务
	 */
	protected synchronized void addConnection(Socket s, Service service) {
		// 判断连接数是否已满
		if (this.connections.size() >= this.maxConnections) {
			try {
				// 拒绝客户端
				PrintWriter out = new PrintWriter(s.getOutputStream());
				out.print("Connection refused; "
						+ "the server is busy; please try again later." + LINE_SEPERATOR);
				out.flush();
				// 关闭socket连接
				s.close();
				// 写日志
				this.log("Connection refused to "
						+ s.getInetAddress().getHostAddress() + ":"
						+ s.getPort() + ": max connections reached.");
			} catch (IOException e) {
				this.log(e);
			}
		} else {
			// 如果连接数没满，则接受连接请求
			// 创建一个连接Connection对象
			Connection c = new Connection(s, service);
			// 保存并写日志
			this.connections.add(c);
			this.log("Connected to " + s.getInetAddress().getHostAddress() + ":"
					+ s.getPort() + " on port " + s.getLocalPort()
					+ " for service " + service.getClass().getName());
			// 启动连接线程
			c.start();
		}
	}

	/**
	 * 结束一个连接
	 * @param c
	 */
	protected synchronized void endConnection(Connection c) {
		// 从连接列表中清除
		this.connections.remove(c);
		this.log("Connection to " + c.client.getInetAddress().getHostAddress() + ":"
				+ c.client.getPort() + " closed.");
	}

	/**
	 * 设置服务器的并行最大访问数
	 * @param max
	 */
	public synchronized void setMaxConnections(int max) {
		this.maxConnections = max;
	}
	
	/**
	 * 显示服务器状态，有利于调试和控制服务器
	 * @param out	状态信息的输出流
	 */
	public synchronized void displayStatus(PrintWriter out) {
		// 显示服务器提供的所有服务的信息
		Iterator keys = services.keySet().iterator();
		while (keys.hasNext()) {
			Integer port = (Integer) keys.next();
			Listener listener = (Listener) services.get(port);
			out.print("SERVICE " + listener.service.getClass().getName()
					+ " ON PORT " + port + LINE_SEPERATOR);
		}

		// 显示服务器当前连接数的限制
		out.print("MAX CONNECTIONS: " + this.maxConnections + LINE_SEPERATOR);

		// 显示当前所有连接的信息
		Iterator conns = this.connections.iterator();
		while (conns.hasNext()) {
			Connection c = (Connection) conns.next();
			out.print("CONNECTED TO "
					+ c.client.getInetAddress().getHostAddress() + ":"
					+ c.client.getPort() + " ON PORT "
					+ c.client.getLocalPort() + " FOR SERVICE "
					+ c.service.getClass().getName() + LINE_SEPERATOR);
		}
	}

	/** 
	 * 内部类，实现侦听器，负责侦听端口的连接请求，使用了ServerSocket
	 * 当收到一个连接请求时，调用Server的addConnection方法，决定是否接受连接请求。
	 * 服务器上每个服务都有一个侦听器。
	 **/
	public class Listener extends Thread {
		// 侦听连接的socket
		ServerSocket listen_socket;
		// 侦听端口
		int port;
		// 在该端口上的服务
		Service service; 

		/**
		 * 表示是否需要停止侦听
		 * 使用volatile 声明的变量的值的时候，系统总是重新从它所在的内存读取数据，
		 * 即使它前面的指令刚刚从该处读取过数据。而且读取的数据立刻被保存
		 */
		volatile boolean stop = false; 
	
		/**
		 * 构造方法
		 * 创建了一个线程，放入服务器的线程组中。
		 * 创建一个ServerSocket对象用于侦听指定端口。
		 * @param group		线程组
		 * @param port		端口
		 * @param service	端口上的服务
		 * @throws IOException
		 */
		public Listener(ThreadGroup group, int port, Service service)
				throws IOException {
			super(group, "Listener:" + port);
			listen_socket = new ServerSocket(port);
			// 如果10分钟没有收到连接请求，ServerSocket自动关闭
			listen_socket.setSoTimeout(600000);
			this.port = port;
			this.service = service;
		}

		/** 
		 * 停止侦听器工作
		 ***/
		public void pleaseStop() {
			// 设置停止标志
			this.stop = true;
			// 中断接受操作
			this.interrupt();
			try {
				// 关闭ServerSocket
				listen_socket.close();
			} catch (IOException e) {
			}
		}

		/**
		 * 侦听器的线程体，等待连接请求，接受连接。
		 **/
		public void run() {
			// 如果标识要停止侦听器，则一直运行
			while (!stop) {
				try {
					// 等待连接请求
					Socket client = listen_socket.accept();
					// 将收到的请求加入到服务器上
					addConnection(client, service);
				} catch (InterruptedIOException e) {
				} catch (IOException e) {
					log(e);
				}
			}
		}
	}

	/**
	 * 内部类，描述连接，处理客户端和服务之间连接。
	 * 因为每个连接都用线程，可以独立运行。
	 * 这是实现服务器支持多线程的关键点。
	 */
	public class Connection extends Thread {
		// 连接的客户端
		Socket client;
		// 客户端请求的服务
		Service service;

		/**
		 * 构造方法。被侦听器线程调用，由于侦听器线程属于服务器线程组，
		 * 所以连接的线程也属于服务器线程组。
		 * @param client	
		 * @param service	
		 */
		public Connection(Socket client, Service service) {
			super("Server.Connection:"
					+ client.getInetAddress().getHostAddress() + ":"
					+ client.getPort());
			this.client = client;
			this.service = service;
		}
		/**
		 * 连接的线程体
		 * 获取来自客户端的输入流和输出流，然后调用服务的serve方法。
		 * 服务处理完请求后，关闭连接。
		 */
		public void run() {
			try {
				InputStream in = client.getInputStream();
				OutputStream out = client.getOutputStream();
				// 调用具体的服务
				service.serve(in, out);
			} catch (IOException e) {
				log(e);
			} finally {
				// 关闭连接
				endConnection(this);
			}
		}
	}

	/**
	 * 服务的接口定义。服务器上所有服务都必须实现该接口。
	 * 由于服务器使用了反射机制通过服务类的无参数构造方法创建服务的实例，
	 * 所以所有的服务实现类都必须提供一个无参数的构造方法。
	 */
	public interface Service {
		/**
		 * 服务方法
		 * @param in  客户端的输入流
		 * @param out	客户端的输出流
		 * @throws IOException
		 */
		public void serve(InputStream in, OutputStream out) throws IOException;
	}

	/**
	 * 一个简单的服务，向客户端通知服务器上的当前时间
	 **/
	public static class Time implements Service {
		public void serve(InputStream i, OutputStream o) throws IOException {
			PrintWriter out = new PrintWriter(o);
			out.print(new Date() + LINE_SEPERATOR);
			out.close();
			i.close();
		}
	}

	/**
	 * 倒序字符串的服务。将客户端输入的字符串，倒序后返回。
	 * 当客户端输入一行"."时，关闭连接。
	 */
	public static class Reverse implements Service {
		public void serve(InputStream i, OutputStream o) throws IOException {
			BufferedReader in = new BufferedReader(new InputStreamReader(i));
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(o)));
			out.print("Welcome to the line reversal server." + LINE_SEPERATOR);
			out.print("Enter lines.  End with a '.' on a line by itself." + LINE_SEPERATOR);
			for (;;) {
				out.print("> ");
				out.flush();
				// 从客户端的输入流中取出一行
				String line = in.readLine();
				if ((line == null) || line.equals(".")){
					break;
				}
				// 将字符串倒序返回
				for (int j = line.length() - 1; j >= 0; j--){
					out.print(line.charAt(j));
				}
				out.print(LINE_SEPERATOR);
			}
			out.close();
			in.close();
		}
	}


	/**
	 * 计数器服务，这个服务通过实例变量保存访问该服务的连接数。
	 * 每一次连接都将计数器加1。
	 **/
	public static class UniqueID implements Service {
		public int id = 0;

		public synchronized int nextId() {
			return id++;
		}

		public void serve(InputStream i, OutputStream o) throws IOException {
			PrintWriter out = new PrintWriter(o);
			out.print("You are client #: " + nextId() + LINE_SEPERATOR);
			out.close();
			i.close();
		}
	}

	/**
	 * 控制服务器的服务。通过密码认证。
	 * 客户端提供命令，该服务执行命令，控制服务器的状态。
	 * 命令有：
	 * （1）password: 输入密码，只有密码正确才能够执行其他命令
	 * （2）add: 增加服务命令，后面跟着服务名和端口号
	 * （3）remove: 删除服务命令，后面跟端口号
	 * （4）max: 修改服务器的最大并发连接数
	 * （5）status: 显示服务器的状态
	 * （6）help: 显示帮助信息
	 * （7）quit: 退出控制服务
	 * 一个服务器同时只能有一个客户端连接到它的控制服务上。
	 */
	public static class Control implements Service {
		// 待控制的服务器
		GeneralServer server;
		// 正确的密码
		String password;
		// 标识是否已经有客户端连接到该服务上
		boolean connected = false; 
		// 帮助信息
		String helpMsg = "COMMANDS:" + LINE_SEPERATOR + "\tpassword <password>" + LINE_SEPERATOR
				+ "\tadd <service> <port>" + LINE_SEPERATOR + "\tremove <port>" + LINE_SEPERATOR
				+ "\tmax <max-connections>" + LINE_SEPERATOR + "\tstatus" + LINE_SEPERATOR 
				+ "\thelp" + LINE_SEPERATOR	+ "\tquit" + LINE_SEPERATOR;

		// 默认构造方法，私有方法，
		// 表示该服务不能被服务器动态加载，只能构在启动服务器时静态加载
		private Control(){
			// do nothig
		}
	
		/**
		 * 构造控制服务。控制特定的服务器。
		 * 根据密码认证客户端是否有权限控制服务器。
		 * @param server
		 * @param password
		 */
		public Control(GeneralServer server, String password) {
			this.server = server;
			this.password = password;
		}

		/**
		 * 提供的服务。读取客户端的输入，使用java.util.StringTokenizer解析命令。
		 * 根据命令，调用服务器的控制操作。
		 **/
		public void serve(InputStream i, OutputStream o) throws IOException {
			BufferedReader in = new BufferedReader(new InputStreamReader(i));
			PrintWriter out = new PrintWriter(o);
			String line; 
			// 标识是否经过的认证
			boolean authorized = false;

			// 这里使用了同步锁，
			// 标识如果有客户端连接到了该服务，其他客户端将不能进入该服务。
			synchronized (this) {
				if (connected) {
					out.print("ONLY ONE CONTROL CONNECTION ALLOWED." + LINE_SEPERATOR);
					out.close();
					return;
				} else {
					connected = true;
				}
			}

			// 解析命令，执行命令
			for (;;) { 
				// 给客户端一个提示符
				out.print("> "); 
				out.flush(); 
				// 读取客户端输入
				line = in.readLine();
				if (line == null){
					// 如果没有输入，结束
					break;
				}
				try {
					// 使用StringTokenizer分析命令
					StringTokenizer t = new StringTokenizer(line);
					if (!t.hasMoreTokens()){
						// 如果是一个空串，则继续下一次循环
						continue;
					}
					// 获取第一个命令
					String command = t.nextToken().toLowerCase();
					//根据命令作不同的处理
					if (command.equals("password")) {
						// 获取输入的密码
						String p = t.nextToken();
						// 匹配密码
						if (p.equals(this.password)) { 
							// 匹配成功
							out.print("OK" + LINE_SEPERATOR);
							authorized = true;
						} else {
							// 匹配失败
							out.print("INVALID PASSWORD" + LINE_SEPERATOR); 
						}
						
					} else if (command.equals("add")) { 
						// 增加服务
						// 首先判断是否已经通过认证
						if (!authorized) {
							out.print("PASSWORD REQUIRED" + LINE_SEPERATOR);
						} else {
							// 获取服务名，并动态加载
							String serviceName = t.nextToken();
							Class serviceClass = Class.forName(serviceName);
							Service service;
							try {
								service = (Service) serviceClass.newInstance();
							} catch (NoSuchMethodError e) {
								throw new IllegalArgumentException(
										"Service must have a "
												+ "no-argument constructor");
							}
							// 获取端口号
							int port = Integer.parseInt(t.nextToken());
							// 添加到服务器
							server.addService(service, port);
							out.print("SERVICE ADDED" + LINE_SEPERATOR);
						}
						
					} else if (command.equals("remove")) {
						// 删除服务
						if (!authorized) {
							out.print("PASSWORD REQUIRED" + LINE_SEPERATOR);
						} else {
							// 获取端口号
							int port = Integer.parseInt(t.nextToken());
							// 从服务器上删除
							server.removeService(port);
							out.print("SERVICE REMOVED" + LINE_SEPERATOR);
						}
						
					} else if (command.equals("max")) {
						// 设置服务器最大并发连接数
						if (!authorized) {
							out.print("PASSWORD REQUIRED" + LINE_SEPERATOR);
						} else {
							int max = Integer.parseInt(t.nextToken());
							server.setMaxConnections(max);
							out.print("MAX CONNECTIONS CHANGED" + LINE_SEPERATOR);
						}
						
					} else if (command.equals("status")) {
						// 显示服务器状态
						if (!authorized) {
							out.print("PASSWORD REQUIRED" + LINE_SEPERATOR);
						} else {
							server.displayStatus(out);
						}
						
					} else if (command.equals("help")) { 
						// 显示帮助信息
						out.print(helpMsg);
						
					} else if (command.equals("quit")) {
						// 退出命令
						break; 
						
					} else {
						out.print("UNRECOGNIZED COMMAND" + LINE_SEPERATOR); 
					}
				} catch (Exception e) {
					out.print("ERROR WHILE PARSING OR EXECUTING COMMAND:" + LINE_SEPERATOR
							+ e	+ LINE_SEPERATOR);
				}
			}
			// 执行完客户端命令后，将标志位置为false，其他客户端便可访问该服务了。
			connected = false;
			out.close();
			in.close();
		}
	}
}
