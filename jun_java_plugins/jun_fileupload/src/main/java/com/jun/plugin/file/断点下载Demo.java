package com.jun.plugin.file;

/**
 * 说明：
 * 每一个线程下载的位置计算方式：
 * 开始位置：
 * (线程id - 1)*每一块大小
 * 结束位置：
 * (线程id*每一块大小) - 1 
 *  ---注意有时候不一定能够整除，所以最后一个线程的结束位置应该是文件的末尾
 *  
 *  步骤：
 *  1.本地创建一个大小跟服务器文件相同的临时文件
 *  2.计算分配几个线程去下载服务器上的资源，知道每个线程下载文件的位置
 *  3.开启三个线程，每一个线程下载对应位置的文件
 *  4.如果所有的线程，都把自己的数据下载完毕后，服务器上的资源都被下载到本地了
 *  
 *  断点下载：
 *  1.使用文件记录每一个线程的下载长度
 *  2.每一个下载开始之前，读取文件，如果文件存在并且长度大于0，则取出长度
 *  3.将每一个线程的起始位置+已经下载的长度
 *  4.所有的线程下载完毕后，删除保存下载长度的文件
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class 断点下载Demo {
	public static String path = "http://softdownload.hao123.com/hao123-soft-online-bcs/soft/Y/2013-07-18_YoudaoDict_baidu.alading.exe";//"http://softdownload.hao123.com/hao123-soft-online-bcs/soft/Y/2013-07-18_YoudaoDict_baidu.alading.exe";
	public static int threadCount = 10;
	public static int runningThread = 10;
	public static void main(String[] args) throws Exception{
		//1.连接服务器，获取一个文件，获取文件的长度，在本地创建一个跟服务器一样大小的临时文件
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		int code = conn.getResponseCode();
		if (code == 200) {
			//服务器端返回的数据的长度，实际上就是文件的长度
			int length = conn.getContentLength();
			System.out.println("文件总长度："+length);
			//在客户端本地创建出来一个大小跟服务器端一样大小的临时文件
			RandomAccessFile raf = new RandomAccessFile("setup.exe", "rwd");
			//指定创建的这个文件的长度
			raf.setLength(length);
			raf.close();
			//假设是3个线程去下载资源。
			//平均每一个线程下载的文件大小.
			int blockSize = length / threadCount;
			for (int threadId = 1; threadId <= threadCount; threadId++) {
				//第一个线程下载的开始位置
				int startIndex = (threadId - 1) * blockSize;
				int endIndex = threadId * blockSize - 1;
				if (threadId == threadCount) {//最后一个线程下载的长度要稍微长一点
					endIndex = length;
				}
				System.out.println("线程："+threadId+"下载:---"+startIndex+"--->"+endIndex);
				new DownLoadThread(path, threadId, startIndex, endIndex).start();
			}
		
		}else {
			System.out.printf("服务器错误!");
		}
	}
	
	/**
	 * 下载文件的子线程  每一个线程下载对应位置的文件
	 * @author jie
	 *
	 */
	public static class DownLoadThread extends Thread{
		private int threadId;
		private int startIndex;
		private int endIndex;
		/**
		 * @param path 下载文件在服务器上的路径
		 * @param threadId 线程Id
		 * @param startIndex 线程下载的开始位置
		 * @param endIndex	线程下载的结束位置
		 */
		public DownLoadThread(String path, int threadId, int startIndex, int endIndex) {
			super();
			this.threadId = threadId;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}

		@Override
		public void run() {
			try {
				//检查是否存在记录下载长度的文件，如果存在读取这个文件
				File tmp_file = new File(threadId+".txt");
				if (tmp_file.exists() && tmp_file.length() > 0) {
					FileInputStream fio = new FileInputStream(tmp_file);
					byte[] temp = new byte[1024];
					int len = fio.read(temp);
					String downloadlen = new String(temp, 0, len);
					int downloadInt = Integer.parseInt(downloadlen);
					startIndex = downloadInt;//修改下载的真实的开始位置
					System.out.println("线程："+threadId+"真实的下载位置："+startIndex+"--->"+endIndex);
					fio.close();
				}
				
				
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				//重要:请求服务器下载部分文件 指定文件的位置
				conn.setRequestProperty("Range", "bytes="+startIndex+"-"+endIndex);
				//从服务器请求全部资源返回200 ok如果从服务器请求部分资源 返回 206 ok
				int code = conn.getResponseCode();
				System.out.println("code:"+code);
				InputStream is = conn.getInputStream();//已经设置了请求的位置，返回的是当前位置对应的文件的输入流
				RandomAccessFile raf = new RandomAccessFile("setup.exe", "rwd");
				//随机写文件的时候从哪个位置开始写
				raf.seek(startIndex);//定位文件
			
				int len = 0;
				byte[] buffer = new byte[1024];
				int total = 0;//已经下载的数据长度
				while ((len = is.read(buffer)) != -1) {
					RandomAccessFile file = new RandomAccessFile(threadId+".txt", "rwd");
					raf.write(buffer, 0, len);
					total += len;
					file.write((""+(total+startIndex)).getBytes());
					file.close();
				}
				is.close();
				raf.close();
				System.out.println("线程："+threadId+"下载完毕");
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				runningThread--;
				if (runningThread == 0) {//所有的线程执行完毕
					for (int i = 1; i <= threadCount; i++) {
						File file = new File(i+".txt");
						file.delete();
					}
					System.out.println("文件全部下载完毕!");
				}
			}
		}
		
	}
}