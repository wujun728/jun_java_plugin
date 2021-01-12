import java.io.*;
import javax.microedition.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

// 建立Http连接

public class HttpConnectionDemo extends MIDlet {
	private Display display; //显示器
	String url = "http://www.baidu.com/index.htm"; //待访问的地址
	
	public HttpConnectionDemo() {
		display = Display.getDisplay(this); //获取显示器
	}
	
	public void startApp() {
			getConnection(url);
	}
	
	public void pauseApp() {
	}
	
	public void destroyApp(boolean unconditional) {
	}
	
	public void getConnection(String url){ 
		try{	
			ContentConnection connection = (ContentConnection) Connector.open(url); //获取连接
			TextBox tb = null; //显示文本的TextBox对象
			StringBuffer sb = new StringBuffer(); //字符串缓冲			
			InputStream is = connection.openInputStream(); //获取输入流
			int ch; 
			while((ch = is.read()) != -1) { //读取字符
				sb.append((char)ch); //增加字符到缓冲区
			}
			tb = new TextBox("取得文本信息", sb.toString(), 1024, 0); //显示文本信息
			display.setCurrent(tb); //设置当前的显示屏幕
		}
		catch (Exception ex){}
	}

}