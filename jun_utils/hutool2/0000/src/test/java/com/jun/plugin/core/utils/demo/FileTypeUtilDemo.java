package com.jun.plugin.core.utils.demo;

import java.io.IOException;

import com.jun.plugin.core.utils.io.FileTypeUtil;
import com.jun.plugin.core.utils.lang.Console;
import com.jun.plugin.core.utils.system.SystemUtil;

/**
 * 文件类型判断Demo
 * @author Looly
 *
 */
public class FileTypeUtilDemo {
	public static void main(String[] args) {
		String javaHome = SystemUtil.get(SystemUtil.HOME);
		Console.log("Java home: {}", javaHome);
		Console.log("-----------------------------------------------------------");
		try {
			Console.log(FileTypeUtil.getTypeByPath(javaHome + "/lib/alt-rt.jar"));
			Console.log(FileTypeUtil.getTypeByPath(javaHome + "/lib/plugin.jar"));
		} catch (IOException e) {
			Console.error(e);
		}
	}
}
