package com.baijob.commonTools;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CSV文件相关工具（逗号分隔符文件）
 * @author Luxiaolei
 *
 */
public class CsvUtil {
	private static Logger logger = LoggerFactory.getLogger(CsvUtil.class);
	
	/**
	 * 将结果集存储为CSV文件
	 * @param rs 结果集
	 * @param pathWithName 要存储到文件的路径
	 */
	public static void toCSV(ResultSet rs, String pathWithName, String charset) {
		try {
			PrintWriter writer = FileUtil.getPrintWriter(pathWithName, charset, false);
			while (rs.next()) {
				int count = rs.getMetaData().getColumnCount();
				// 处理一行
				StringBuffer sb = new StringBuffer();
				for (int i = 1; i <= count; i++) {
					sb.append(rs.getObject(i));
					if (i == count)
						break;
					sb.append(",");
				}
				String line = sb.toString();
				logger.debug("写入：" + line);
				writer.println(line);
			}
			writer.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将集合中的内容写入CSV文件
	 * @param collection 要写入文件的集合集合
	 * @param pathWithName CSV文件路径，带文件名
	 * @param charset 字符集
	 */
	public static void toCSV(Collection<String> collection, String pathWithName, String charset){
		try {
			PrintWriter writer = FileUtil.getPrintWriter(pathWithName, charset, false);
			for (String line : collection) {
				logger.debug("写入：" + line);
				writer.println(line);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
