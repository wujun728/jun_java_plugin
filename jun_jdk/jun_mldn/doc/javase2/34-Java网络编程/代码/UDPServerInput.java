import java.net.* ;
import java.io.* ;

public class UDPServerInput
{
	public static void main(String args[]) throws Exception
	{
		DatagramSocket ds = null ;
		DatagramPacket dp = null ;
		// 要保证UDP有一个运行的端口
		ds = new DatagramSocket(5000) ;
		// String s = "HELLO MLDN" ;
		// BufferedReader buf = new BufferedReader(new InputStreamReader(System.in)) ;
		System.out.print("请输入要发送的信息：") ;
		// s = buf.readLine() ;
		byte b[] = new byte[1024] ;
		int len = System.in.read(b) ;

		System.out.println(new String(b,0,len)) ;

		dp = new DatagramPacket(b,len,InetAddress.getByName("localhost"),8888) ;
		
		ds.send(dp) ;
		ds.close() ;
	}
};