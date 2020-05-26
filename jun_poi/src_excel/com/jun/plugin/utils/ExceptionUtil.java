package com.jun.plugin.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

/**
 * 类功能说明 TODO:Exception工具类
 * 类修改者	修改日期
 * 修改说明
 * <p>Title: BaseService.java</p>
 * <p>Description:福产流通科技</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company:福产流通科技</p>
 * @author shouyin 756514656@qq.com
 * @date 2013-4-19 下午03:18:05
 * @version V1.0
 */
public class ExceptionUtil {

	private static final Logger logger = Logger.getLogger(ExceptionUtil.class);

	/**
	 * 返回错误信息字符串
	 * 
	 * @param ex
	 *            Exception
	 * @return 错误信息字符串
	 */
	public static String getExceptionMessage(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String errorMessage = sw.toString();
		pw.close();
		try {
			sw.close();
		} catch (IOException e) {
			logger.error(e);
		}
		return errorMessage;
	}
	
	/**
	 * 返回错误信息字符串
	 * 
	 * @param ex
	 *            Exception
	 * @return 错误信息字符串
	 */
	public static String getExceptionMessage2(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}

}
