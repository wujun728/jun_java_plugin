import java.io.*;
import javax.microedition.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

// 从网络上下载数据

public class DownloadDemo extends MIDlet {
	private Display display; //显示器
	String url = "http://women.sohu.com/xingxiang/newxz/mz/images/kuhua.jpg"; //待访问的地址
	
	public DownloadDemo() {
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
		InputStream in;
		try{	
			in = (InputStream) Connector.openInputStream(url); //打开输入流
			Image img = null; //Image对象
  			ByteArrayOutputStream bos = new ByteArrayOutputStream(); //字节输入流
  			int ch;
  			while ((ch = in.read()) != -1){ //读取字节数
    			bos.write(ch); //写字节到输出流中
    		}  
  			byte imageData[] = bos.toByteArray(); // 获取图像数据
      		in.close();  //关闭输入流
  			img = Image.createImage(imageData, 0, imageData.length); //创建Image对象
  			ImageItem imageItem = new ImageItem(null,img,ImageItem.LAYOUT_CENTER,"img"); //创建ImageItem对象

			Form form = new Form("下载图像");  // 创建表单
			form.append(imageItem); //增加显示图像到表单上
			display.setCurrent(form); //设置当前显示为表单
		}
		catch (Exception ex){}
	}
}