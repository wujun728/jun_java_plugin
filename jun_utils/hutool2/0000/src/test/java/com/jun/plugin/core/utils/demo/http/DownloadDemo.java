package com.jun.plugin.core.utils.demo.http;

import com.jun.plugin.core.utils.http.HttpUtil;
import com.jun.plugin.core.utils.io.FileUtil;

/**
 * 下载样例
 * @author Looly
 *
 */
public class DownloadDemo {
	public static void main(String[] args) {
		// 下载文件
		long size = HttpUtil.downloadFile("https://www.baidu.com/", FileUtil.file("e:/"));
		System.out.println("Download size: " + size);
	}
}
