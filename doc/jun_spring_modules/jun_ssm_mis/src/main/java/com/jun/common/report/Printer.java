package com.jun.common.report;

import java.io.IOException;
import java.io.OutputStream;
/**
 * 报表打印接口
 * @author Wujun
 *
 */
public interface Printer {
	/**
	 * 打印报表
	 * @param report              报表             
	 * @param outputStream        输出流
	 * @throws ReportException
	 * @throws IOException
	 */
	public abstract void print(Report report, OutputStream outputStream) throws ReportException, IOException;
}
