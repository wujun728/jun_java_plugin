package book.net;

import java.io.*;
import java.net.*;

/**
 * 本类为支持多线程服务器框架的GeneralServer类提供了一个相关的服务Service，
 * 使GeneralServer提供代理服务。内部类ProxyService实现了GeneralServer.Service接口。
 **/
public class ProxyServer {
	// 命令行的格式是：代理服务器连接的目标服务器的域名、端口以及代理服务开启的本地端口
	public static final String usage =  
		"Usage: java book.net.ProxyServer <remotehost> <remoteport> <localport> ...";
    /** 
     * 创建一个GeneralServer对象，并添加代理服务ProxyService对象到这个服务器上。
     **/
    public static void main(String[] args) {
        try {
            // 检验参数
            if ((args.length == 0) || (args.length % 3 != 0)){
                throw new IllegalArgumentException("Wrong number of args");
            }
	    
            // 创建一个服务器：GeneralServer对象，用于管理服务，
            // 第一个参数为null表示服务器的日志输出在标准输出上，第二个参数12表示最大连接数
            GeneralServer server = new GeneralServer(null, 12);
	    
            // 从命令行中解析参数，可以创建多个代理服务
            int i = 0;
            while(i < args.length) {
            	// 目标服务器的域名和地址
                String remotehost = args[i++];
                int remoteport = Integer.parseInt(args[i++]);
                // 代理服务在本地的端口
                int localport = Integer.parseInt(args[i++]);
                // 创建一个连接到目标服务器的代理服务对象，并部署在本地服务器上的localport端口上。
                server.addService(new ProxyService(remotehost, remoteport), localport);
            }
        }
        catch (Exception e) {
            System.err.println(e);
            System.err.println(usage);
            System.exit(1);
        }
    }
    
    /**
     * 实现代理服务的功能，实现了GeneralServer.Service接口的serve方法。
     * 首先与目标服务器建立连接，然后将来自客户端的输入流输出到目标服务器端，
     * 将来自目标服务器的输入流输出到客户端。
     * 输入输出流的流向控制是通过2个线程实现的。
     */
    public static class ProxyService implements GeneralServer.Service {
    	// 目标服务器的域名和端口
        String remotehost;
        int remoteport;
	
        /**
         * 构造方法
         */
        public ProxyService(String host, int port) {
            this.remotehost = host;
            this.remoteport = port;
        }
	
        /**
         * 当客户端调用GeneralServer上的代理服务时，将调用serve方法
         */
        public void serve(InputStream in, OutputStream out) {
        	// 来自客户端的输入流
            final InputStream from_client = in;
            // 输出到客户端的输出流
            final OutputStream to_client = out;
            // 与目标服务器的输入输出流
            final InputStream from_server;
            final OutputStream to_server;

            // 连接到目标服务器的socket对象
            final Socket server;
			try {
				// 连接目标服务器，初始化from_server，to_server流
				server = new Socket(remotehost, remoteport);
				from_server = server.getInputStream();
				to_server = server.getOutputStream();
			} catch (Exception e) {
				// 如果连接失败，则向客户端发送失败的消息
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
				pw.print("Proxy server could not connect to " + remotehost
						+ ":" + remoteport + "\n");
				pw.flush();
				pw.close();
				try {
					in.close();
				} catch (IOException ex) {
				}
				return;
			}
	    
            // 创建一个线程，将来自客户端的输入流字节输出到目标服务器上
            Thread client2server = new Thread() {
				public void run() {
					byte[] buffer = new byte[2048];
					int bytes_read;
					try {
						// 从来自客户端的输入流中读取数据，写到目标服务器上
						while ((bytes_read = from_client.read(buffer)) != -1) {
							to_server.write(buffer, 0, bytes_read);
							to_server.flush();
						}
					} catch (IOException e) {
					} finally {
						// 当线程结束时，关闭与目标服务器的socket连接和2个输入输出流
						try {
							server.close(); 
							to_client.close(); 
							from_client.close();
						} catch (IOException e) {
						}
					}
				}
			};

			// 创建一个线程，用于将来自目标服务器的输入流输出到客户端
			Thread server2client = new Thread() {
				public void run() {
					byte[] buffer = new byte[2048];
					int bytes_read;
					try {
						// 读取来自目标服务器的输入流from_server，然后输出到to_client
						while ((bytes_read = from_server.read(buffer)) != -1) {
							to_client.write(buffer, 0, bytes_read);
							to_client.flush();
						}
					} catch (IOException e) {
					} finally {
						try {
							server.close(); 
							to_client.close();
							from_client.close();
						} catch (IOException e) {
						}
					}
				}
			};

			// 启动这两个线程
			client2server.start();
			server2client.start();

			// 当这个两个线程运行结束时，本次服务才结束
			try {
				client2server.join();
				server2client.join();
			} catch (InterruptedException e) {
			}
        }
    }
}
