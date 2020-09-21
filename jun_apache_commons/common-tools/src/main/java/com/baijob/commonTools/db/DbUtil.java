package com.baijob.commonTools.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库操作工具类
 * 
 * @author Luxiaolei
 * 
 */
public class DbUtil {
	private static Logger logger = LoggerFactory.getLogger(DbUtil.class);

	private DbUtil() {
		// 非可实例化类
	}
	
	/**
	 * 实例化一个新的SQL运行对象
	 * 
	 * @param ds 数据源
	 * @return SQL执行类
	 */
	public static SqlRunner newSqlRunner(DataSource ds) {
		return new SqlRunner(ds);
	}
	
	/**
	 * 拼接JDBC连接字符串
	 * @param protocol 协议
	 * @param host 主机名
	 * @param port 端口号
	 * @param dbName 数据库名
	 * @param jdbcParam 参数
	 * @return JDBC字符串
	 */
	public static String buildJdbcUrl(String protocol, String host, int port, String dbName, String jdbcParam) {
		jdbcParam = jdbcParam == null ? "" : jdbcParam;
		if(! jdbcParam.startsWith("?")) {
			jdbcParam = "?" + jdbcParam;
		}
		
		String sep = "//";
		if(protocol.contains("jdbc:oracle")) {
			sep = "@";
		}
		return protocol + ":" + sep + host + ":" + port + "/" + dbName + jdbcParam;
	}
	
	/**
	 * 连续关闭一系列的SQL相关对象<br/>
	 * 这些对象必须按照顺序关闭，否则会出错。
	 * 
	 * @param objsToClose 需要关闭的对象
	 */
	public static void close(Object... objsToClose) {
		for (Object obj : objsToClose) {
			try {
				if (obj != null) {
					if (obj instanceof ResultSet) {
						((ResultSet) obj).close();
					} else if (obj instanceof Statement) {
						((Statement) obj).close();
					} else if (obj instanceof PreparedStatement) {
						((PreparedStatement) obj).close();
					} else if (obj instanceof Connection) {
						((Connection) obj).close();
					} else {
						logger.warn("对象" + obj.getClass().getName() + "不是可关闭的类型");
					}
				}
			} catch (SQLException e) {
			}
		}
	}
	
	/**
	 * 获得JNDI数据源
	 * @param jndiName JNDI名称
	 * @return 数据源
	 */
	public static DataSource getJndiDs(String jndiName) {
		try {
			Context ctx = new InitialContext();
			return (DataSource) ctx.lookup(jndiName);
		} catch (NamingException e) {
			logger.error("Find JNDI datasource error!", e);
		}
		return null;
	}
}
