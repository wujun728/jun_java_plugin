package klg.db.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author kevin
 *
 */
public class DBAccess {

	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	public DBAccess(Connection connection) {
		this.conn = connection;
	}


	public Statement getStmt() {
		return stmt;
	}

	public ResultSet getRs() {
		return rs;
	}
	
	
	/**
	 * 查看是否有结果集
	 * 
	 * @param sql
	 * @return
	 */
	public boolean hasResultSet(String sql) {

		boolean b = false;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				b = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	/*
	 * 查询
	 */

	public void query(String sql) {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 更新
	 */

	public boolean update(String sql) {
		boolean b = false;
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
			b = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	public boolean next() {
		boolean b = false;
		try {
			if (rs.next())
				b = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	public String getValue(String field) {
		String value = null;
		if (rs != null)
			try {
				value = rs.getString(field);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return value;
	}

	public String getValue(int columnIndex) {
		String value = null;
		if (rs != null)
			try {
				value = rs.getString(columnIndex);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return value;
	}

	public void closeAll() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();// 关闭
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
