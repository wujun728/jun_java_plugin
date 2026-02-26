// This file is commented out — part of image spider, dependent on SimpleSpider.
// See jun_crawler or jun_jsoup for web scraping functionality.
/*
package com.jun.plugin.httpclient.base.imagedown2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class JianDanImageCreator implements Runnable {
	private static int count = 0;
	private String imageUrl;
	private int page;
	private static final String basePath = "E:/pic12";
	public JianDanImageCreator(String imageUrl,int page) {
		this.imageUrl = imageUrl;
		this.page = page;
	}
	@Override
	public void run() {
		File dir = new File(basePath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		String imageName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
		try {
			File file = new File( basePath+"/"+page+"--"+imageName);
			OutputStream os = new FileOutputStream(file);
			URL url = new URL("http:"+imageUrl);
			InputStream is = url.openStream();
			byte[] buff = new byte[1024];
			while(true) {
				int readed = is.read(buff);
				if(readed == -1) {
					break;
				}
				byte[] temp = new byte[readed];
				System.arraycopy(buff, 0, temp, 0, readed);
				os.write(temp);
			}
			is.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
*/
