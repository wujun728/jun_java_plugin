

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.junit.Test;
public class NetTestConn {
	/**
	 * 发�?get请求
	 * @throws Exception
	 */
	@Test
	public void testConn() throws Exception{
		//第一步：声明url
		String urlPath = "http://localhost:6666/day22_cos/OneServlet?name=Jack";
		//第二步：声明URL对象
		URL url = new URL(urlPath);
		//第三步：从url上获取连�?
		HttpURLConnection con=  (HttpURLConnection) url.openConnection();
		//第四步：设置访问的类�?
		con.setRequestMethod("GET");
		//第五步：设置可以向服务器发信息�?也可以从服务器接收信�?
		con.setDoInput(true);//设置可以向服务器发信�?
		con.setDoOutput(true);//也可以从服务器接收信�?
		//第六步：连接
		con.connect();
		//7:�?��连接状�?
		int code = con.getResponseCode();
		if(code==200){
			//8：从服务器读取数�?
			InputStream in = con.getInputStream();
			byte[] b = new byte[1024];
			int len = 0;
			while((len=in.read(b))!=-1){
				String s = new String(b,0,len,"UTF-8");
				System.err.print(s);
			}
		}
		//9：断�?
		con.disconnect();
	}
	/**
	 * 以下发�?post请求
	 */
	@Test
	public void post() throws Exception{
		//第一步：声明url
		String urlPath = "http://localhost:6666/day22_cos/OneServlet";
		//第二步：声明URL对象
		URL url = new URL(urlPath);
		//第三步：从url上获取连�?
		HttpURLConnection con=  (HttpURLConnection) url.openConnection();
		//第四步：设置访问的类�?
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		//第五步：设置可以向服务器发信息�?也可以从服务器接收信�?
		con.setDoInput(true);//设置可以向服务器发信�?
		con.setDoOutput(true);//也可以从服务器接收信�?
		//第六步：发信�?
		//获取输出�?
		OutputStream out = con.getOutputStream();
		out.write("name=张三".getBytes("UTF-8"));
		
		
		//7:�?��连接状�?
		int code = con.getResponseCode();
		if(code==200){
			//8：从服务器读取数�?
			InputStream in = con.getInputStream();
			byte[] b = new byte[1024];
			int len = 0;
			while((len=in.read(b))!=-1){
				String s = new String(b,0,len,"UTF-8");
				System.err.print(s);
			}
		}
		//9：断�?
		con.disconnect();
	}
}
