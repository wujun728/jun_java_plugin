package com.yzm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;



public class FileUtil {

	
	public  boolean base64ToImage(String imgStr,String path) { 
		if (imgStr == null) // 图像数据为空
			return false;

		try {
			// Base64解码
			byte[] b = Base64Util.decode(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			if(!new File(path).exists()){
				new File(path).mkdirs();
			}
			// 生成jpeg图片
			String imgFilePath = path+UUID.randomUUID().toString()+".png";// 新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
