import java.io.*;
import java.net.*;
import javax.net.ssl.*;

//使用SSL套接字的HTTPS服务器端
//接受客户端的一个连接,并返回Hello,world.
public class HTTPSExample {

    public static void main(String[] args) {
  	try{

    //使用8080端口创建SSL服务器套接字
    SSLServerSocketFactory sslSocket =
      (SSLServerSocketFactory)SSLServerSocketFactory.getDefault(); //返回缺省的SocketFactory对象
    ServerSocket serSocket = sslSocket.createServerSocket(8080);  //用8080端口创建一个ServerSocket对象

    //等待连接
    while (true) {
        Socket socket = serSocket.accept();  //临听连接

        //获取客户端请求
        BufferedReader in = new BufferedReader(
          new InputStreamReader(socket.getInputStream()));
        System.out.println(in.readLine());

        //返回客户端的回应
        PrintWriter out = new PrintWriter( socket.getOutputStream()); //生成PrintWriter对象,用于输出信息
        StringBuffer strBuffer = new StringBuffer();  //返回的HTML流信息
        strBuffer.append("<HTML><HEAD><TITLE>HTTPS Example</TITLE></HEAD>");  //增加返回信息
        strBuffer.append("<BODY><H2>Congratulate!</H2></BODY></HTML>");  //增加返回信息
        out.println(strBuffer.toString());  //输出信息

        //关闭连接
        out.close();
        socket.close();
        }
      } catch (Exception e) {
          e.printStackTrace();
      }
    }

}
