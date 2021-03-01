package book.net.udp;

import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 该实例实现一个发送UDP报文的类。
 * UDP与TCP不同在于，UDP在发送报文前无需建立连接，直接发送；而TCP需要建立连接。
 * 即TCP比UDP更可靠。另外，UDP报文会出现舍弃的情况，因为发送端和接收端的报文大小可能不一致。
 * 体现在编程时，UDP编程无需ServerSocket和Socket，只需要DatagramSocket类即可。
 **/
public class UDPSend {
    public static final String usage = 
	"Usage: java book.net.udp.UDPSend <hostname> <port> <msg>...\n" +
	"   or: java book.net.udp.UDPSend <hostname> <port> -f <file>";

    public static void main(String args[]) {
        try { 
            // 检查参数个数
            if (args.length < 3){ 
                throw new IllegalArgumentException("Wrong number of args");
            }
            
            // 域名和端口
            String host = args[0];
            int port = Integer.parseInt(args[1]);
	    
            // 下面构造待发送报文的字节数组  
            byte[] message;
            if (args[2].equals("-f")) {
            	// 如果第三个参数为 -f，则表示将文件的内容以UDP方式发送
            	// 获得待发送的文件对象以及文件的长度
                File f = new File(args[3]);
                int len = (int)f.length(); 
                // 创建一个足够容纳文件内容的字节数组
                message = new byte[len]; 
                FileInputStream in = new FileInputStream(f);
                // 将文件内容以字节的方式读到字节数组中
                int bytes_read = 0, n;
                do {
                    n = in.read(message, bytes_read, len-bytes_read);
                    bytes_read += n;
                } while((bytes_read < len)&& (n != -1));
            }
            else { 
            	// 如果第三个参数不是 -f，则将后面的参数当作消息发送
                String msg = args[2];  
                for (int i = 3; i < args.length; i++){
                	msg += " " + args[i];
                }
                message = msg.getBytes();
            }
            
            // 根据域名获取IP地址
            InetAddress address = InetAddress.getByName(host);
	    
            // 初始化一个UDP包。
            // DatagramPacket的构造方法中必须使用InetAddress，而不能是IP地址或者域名
            DatagramPacket packet = new DatagramPacket(message, message.length,
						       address, port);
	    
            // 创建一个DatagramSocket，以发送UDP包
            DatagramSocket dsocket = new DatagramSocket();
            dsocket.send(packet);
            System.out.println("send: " + new String(message));
            dsocket.close();
            
            // 注意：如果在构造DatagramPacket时，不提供IP地址和端口号，
            // 则需要调用DatagramSocket的connect方法，否则无法发送UDP包
            packet = new DatagramPacket(message, message.length);
            dsocket = new DatagramSocket();
            dsocket.connect(address, port);
            dsocket.send(packet);
            System.out.println("Send: " + new String(message));
            dsocket.close();
            
        } catch (Exception e) {
            System.err.println(e);
            System.err.println(usage);
        }
    }
}
