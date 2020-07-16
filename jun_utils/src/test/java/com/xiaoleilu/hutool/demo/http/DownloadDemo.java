package com.xiaoleilu.hutool.demo.http;

import com.jun.plugin.utils2.http.HttpUtil;
import com.jun.plugin.utils2.io.FileUtil;

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
