package com.yzm.common;

import com.yzm.util.FileUtil;

public class ImgToFile implements Runnable{
	
	private String img;
	
	private String path;
	
	public ImgToFile(String img,String path){
		this.img = img;
		this.path = path;
	}

	@Override
	public void run() {
		new FileUtil().base64ToImage(img, path);
		
	}

	

}
