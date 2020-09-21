package com.holder;

import java.sql.Connection;
import java.sql.SQLException;

import com.holder.exception.ConnectionNeededException;
import com.holder.log.ConsoleLog;

public abstract class DBContextHolder {
	private static ThreadLocal<Connection> connectionLocal = new ThreadLocal<Connection>();

	public static void setContextConnection(Connection conn) {
		connectionLocal.set(conn);
	}

	public static Connection getContextConnection() {
		return connectionLocal.get();
	}

	public static void beginTransaction() {
		Connection conn = connectionLocal.get();
		if (conn == null) {
			throw new ConnectionNeededException();
		}
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void rollback() {
		Connection conn = connectionLocal.get();
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * ÊÍ·Å×ÊÔ´
	 */
	public static void release() {
		Connection conn = connectionLocal.get();
		connectionLocal.remove();
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void commit() {
		Connection conn = connectionLocal.get();
		try {
			if (conn.getWarnings() != null
					&& conn.getWarnings().getCause() != null) {
				ConsoleLog.debug("the warning : "
						+ conn.getWarnings().getCause().getMessage());
				conn.rollback();
			} else {
				ConsoleLog.debug("commit the connection start!");
				conn.commit();
				ConsoleLog.debug("commit the connection end!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
