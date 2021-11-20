package book.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 该程序接收来自指定端口得UDP报文。
 **/
public class UDPReceive {
	// 帮助信息
	public static final String usage = "Usage: java book.net.udp.UDPReceive <port>";

	public static void main(String args[]) {
		try {
			if (args.length != 1){
				throw new IllegalArgumentException("Wrong number of args");
			}
			// 从命令行中获取端口号参数
			int port = Integer.parseInt(args[0]);

			// 创建一个socket，侦听这个端口。
			DatagramSocket dsocket = new DatagramSocket(port);

			// 保存接收到的UDP报文的字节数组
			byte[] buffer = new byte[2048];

			// 创建一个DatagramPacket，将收到的报文写入buffer中。
			// 注意，这里指定了报文的长度，如果收到的UDP报文比2048大，多余的信息被舍弃
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			// 不断循环，接收数据
			for ( ; ;) {
				// 等待收到一个数据包
				dsocket.receive(packet);

				// 将收到的报文的字节数组封装成字符串。
				String msg = new String(buffer, 0, packet.getLength());
				// 从数据包中取得消息来源的地址
				System.out.println("Receive: " + packet.getAddress().getHostAddress() + ": "
						+ msg);

				// 如果收到QUIT指令，则退出循环。
				if (msg.equals("QUIT")){
					System.out.println("Exit the UDPReceive!");
					break;
				}
				
				// 重设数据包的长度
				packet.setLength(buffer.length);
			}
			// 关闭socket
			dsocket.close();
		} catch (Exception e) {
			System.err.println(e);
			System.err.println(usage);
		}
	}
}
