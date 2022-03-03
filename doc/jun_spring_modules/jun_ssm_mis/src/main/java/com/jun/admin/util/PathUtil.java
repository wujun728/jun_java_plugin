package com.jun.admin.util;

/**
 * 获取路径的相关类
 */
public class PathUtil {

	public static void main(String[] args) throws Exception {
		PathUtil p = new PathUtil();
		System.out.println("Web Class Path = " + p.getWebClassesPath());
		System.out.println("WEB-INF Path = " + p.getWebInfPath());
		System.out.println("WebRoot Path = " + p.getWebRoot());
	}

	public String getWebClassesPath() {
		String path = getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		return path;

	}

	public String getWebInfPath() throws IllegalAccessException {
		String path = getWebClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF") + 8);
		} else {
			throw new IllegalAccessException("路径获取错误");
		}
		return path;
	}

	public String getWebRoot() throws IllegalAccessException {
		String path = getWebClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF/classes"));
		} else {
			throw new IllegalAccessException("路径获取错误");
		}
		return path;
	}
}
