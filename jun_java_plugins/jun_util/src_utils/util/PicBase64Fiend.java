/**
 * Program  : PicBase64Fiend.java
 * Author   : zhangym
 * Create   : 2009-7-20 下午02:28:29
 *
 * Copyright 2006 by Embedded Internet Solutions Inc.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Embedded Internet Solutions Inc.("Confidential Information").  
 * You shall not disclose such Confidential Information and shall 
 * use it only in accordance with the terms of the license agreement 
 * you entered into with Embedded Internet Solutions Inc.
 *
 */

package cn.ipanel.apps.portalBackOffice.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 
 * @author zhangym
 * @version 1.0.0
 * @2009-7-20 下午02:28:29
 */
public class PicBase64Fiend {

	/**
	 * 图片BASE64 编码
	 * 
	 * @author zhangym
	 * @create 2009-7-20 下午02:28:47
	 * @since
	 * @param picPath
	 * @return
	 */
	public static String getPicBASE64(String picPath) {
		String content = null;
		try {
			FileInputStream fis = new FileInputStream(picPath);
			byte[] bytes = new byte[fis.available()];
			fis.read(bytes);
			content = new sun.misc.BASE64Encoder().encode(bytes); // 具体的编码方法
			fis.close();
			System.out.println(content.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 图片BASE64 编码
	 * 
	 * @author zhangym
	 * @create 2009-7-20 下午02:28:54
	 * @since
	 * @param base64str
	 * @param outPicPath
	 */
	public static void getPicFromBASE64(String base64str, String outPicPath) {
		try {
			byte[] result = new sun.misc.BASE64Decoder().decodeBuffer(base64str
					.trim());
			FileOutputStream fos = new FileOutputStream(outPicPath); // r,rw,rws,rwd
			fos.write(result);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
