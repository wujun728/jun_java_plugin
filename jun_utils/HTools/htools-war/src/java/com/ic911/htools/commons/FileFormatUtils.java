package com.ic911.htools.commons;

import java.text.DecimalFormat;

public class FileFormatUtils {

	/**
	 * 文件大小转换
	 * @param request
	 * @return
	 */
	public static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00"); 
		String fileSizeString = ""; 
		if (fileS < 1024) { 
			fileSizeString = df.format((double) fileS) + "B"; 
		} else if (fileS < 1048576) { 
			fileSizeString = df.format((double) fileS / 1024) + "KB"; 
		} else if (fileS < 1073741824) { 
			fileSizeString = df.format((double) fileS / 1048576) + "MB"; 
		} else { 
			fileSizeString = df.format((double) fileS / 1073741824) + "GB"; 
		} 
		return fileSizeString; 
	}
	
}
