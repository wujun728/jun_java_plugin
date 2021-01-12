import java.io.* ;
import java.net.* ;

public class TCPServer
{
	public static void main(String args[]) throws Exception
	{
		// 使用ServerSocket
		ServerSocket server = null ;
		// 每一个用户在程序中就是一个Socket
		Socket client = null ;
		server = new ServerSocket(8888) ;
		// 等待客户端连接
		client = server.accept() ;
		// 向客户端打印信息：HELLO MLDN
		PrintWriter out = null ;
		// 准备向客户端打印信息
		out = new PrintWriter(client.getOutputStream()) ;
		out.println("HELLO MLDN") ;
		out.close() ;
		client.close() ;
		server.close() ;
	}
};
