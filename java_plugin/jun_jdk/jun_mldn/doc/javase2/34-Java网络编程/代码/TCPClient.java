import java.io.* ;
import java.net.* ;
public class TCPClient
{
	public static void main(String args[]) throws Exception
	{
		// 表示一个客户端的Socket
		Socket client = null ;
		// 表示一个客户端的输入信息
		BufferedReader buf = null ;
		client = new Socket("localhost",8888) ;
		buf = new BufferedReader(new InputStreamReader(client.getInputStream())) ;
		System.out.println(buf.readLine()) ;
		buf.close() ;
		client.close() ;
	}
};