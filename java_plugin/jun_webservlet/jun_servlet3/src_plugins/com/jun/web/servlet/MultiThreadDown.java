package com.jun.web.servlet;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * 多线程下载同一个文件
       思想：
 *
 */
public class MultiThreadDown {
	public MultiThreadDown() throws Exception {
		//声明url
		String path = "http://localhost:6666/day23/up/bin.zip";
		//第一步：声明url对象
		URL url = new URL(path);
		//第二步：返回连接对象
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		//第三步：设置请求类型
		con.setRequestMethod("GET");
		//第四步接收信息
		con.setDoInput(true);
		//第五步：连接
		con.connect();
		//6:状态码
		int code = con.getResponseCode();
		if(code==200){
			//7：数据的长度
			int sum = con.getContentLength();
			System.err.println("总文件的大小："+sum);
			//7.1有了文件的长度，直接创建一个相同大小的文件
			String fileName = "d:/a/bin.zip";
			RandomAccessFile file = new RandomAccessFile(new File(fileName),"rw");
			file.setLength(sum);
			file.close();
			//8：声明线程的个数
			int threadCount = 3;
			//9:计算每个线程的下载量
			int threadSize = sum/threadCount +(sum%threadCount==0?0:1);
			System.err.println("每个线程下载的数据量："+threadSize);
			//10:计算每个线程下载的数据量
			for(int i=0;i<threadCount;i++){
				int start =i*threadSize;
				int end = start+(threadSize-1);
				System.err.println("第"+(i+1)+"个线程应该下载的是:bytes="+start+"-"+end);
				//启动多个线程
				new MyDownThread(url,fileName,start,end).start();
			}
		
		}
		con.disconnect();
	}
	public static void main(String[] args) throws Exception {
		new MultiThreadDown();
	}
}
/**
 * 所有线程要知
 * 道url地址
 * 写哪一个文件
 * 从哪儿开始写
 * 一共多少字节,数据
 */
class MyDownThread extends Thread{
	private URL url;
	private String fileName;
	private int start;
	private int end;
	public MyDownThread(URL url, String fileName, int start, int end) {
		this.url = url;
		this.fileName = fileName;
		this.start = start;
		this.end = end;
	}
	@Override
	public void run() {
		try{
			//打开连接
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			//设置
			con.setRequestMethod("GET");
			con.setDoInput(true);
			//设置从哪儿开始下载数据
			con.setRequestProperty("range","bytes="+start+"-"+end);
			con.connect();
			int code = con.getResponseCode();
			if(code==206){
				int size = con.getContentLength();
				System.err.println("线程:"+this.getName()+",下载的数据量为:"+size);
				InputStream in = con.getInputStream();
				//写同一文件
				RandomAccessFile file = new RandomAccessFile(new File(fileName),"rw");
				//设置从文件的什么位置开始写数据
				file.seek(start);
				//读取数据
				byte[] b = new byte[1024];
				int len = 0;
				while((len=in.read(b))!=-1){
					file.write(b,0,len);
				}
				file.close();
			}
			con.disconnect();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
