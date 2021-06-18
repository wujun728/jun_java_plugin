package com.jun.plugin.webservlet;

public class UploadUtils {
	 
	  /**
	   * 根据文件的路径获取文件真实名称
	   * 
	   * @param path
	   *      文件的路径
	   * @return 文件名称
	   */
	  public static String getRealName(String path) {
	    int index = path.lastIndexOf("\\");
	 
	    if (index == -1) {
	      index = path.lastIndexOf("/");
	    }
	 
	    return path.substring(index + 1);
	  }
	 
	  /**
	   * 根据文件名返回一个目录
	   * 
	   * @param name
	   *      文件名称
	   * @return 目录
	   */
	  public static String getDir(String name) {
	    int i = name.hashCode();
	    String hex = Integer.toHexString(i);
	    int j = hex.length();
	 
	    for (int k = 0; k < 8 - j; k++) {
	      hex = "0" + hex;
	    }
	 
	    return "/" + hex.charAt(0) + "/" + hex.charAt(1);
	  }
	}