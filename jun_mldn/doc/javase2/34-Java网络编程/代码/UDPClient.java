import java.io.* ;
import java.net.* ;

public class UDPClient
{
	public static void main(String args[]) throws Exception
	{
		DatagramSocket ds = null ;
		DatagramPacket dp = null ;
		// 手机发信息和接收信息是有大小限制的
		byte b[] = new byte[1024] ;
		// 在8888端口上一直等待信息到来
		ds = new DatagramSocket(8888) ;
		dp = new DatagramPacket(b,b.length) ;
		ds.receive(dp) ;
		// 从数据包中取出数据
		// System.out.println(dp.getLength()) ;
		String str = new String(dp.getData(),0,dp.getLength()) ;
		System.out.println("接收到的数据为："+str) ;
		ds.close() ;
	}
};