package com.jun.admin.util;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 * 
 */
public class ResourceUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");

	private void ResourceUtil() {
	}

	/**
	 * 获得sessionInfo名字
	 * 
	 * @return
	 */
	public static final String getSessionInfoName() {
		return bundle.getString("sessionInfoName");
	}

	/**
	 * 获得上传表单域的名称
	 * 
	 * @return
	 */
	public static final String getUploadFieldName() {
		return bundle.getString("uploadFieldName");
	}

	/**
	 * 获得上传文件的最大大小限制
	 * 
	 * @return
	 */
	public static final long getUploadFileMaxSize() {
		return Long.valueOf(bundle.getString("uploadFileMaxSize"));
	}

	/**
	 * 获得允许上传文件的扩展名
	 * 
	 * @return
	 */
	public static final String getUploadFileExts() {
		return bundle.getString("uploadFileExts");
	}

	/**
	 * 获得上传文件要放到那个目录
	 * 
	 * @return
	 */
	public static final String getUploadDirectory() {
		return bundle.getString("uploadDirectory");
	}

}
