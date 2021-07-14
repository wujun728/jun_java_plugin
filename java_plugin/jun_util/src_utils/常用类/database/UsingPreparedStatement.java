package book.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 使用PreparedStatement传递变量
 */
public class UsingPreparedStatement {

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		String dbName = "studentdb";
		String userName = "test";
		String password = "test";
		// SQL中有多个问号，表示这些地方的值还不确定
		String sql = "INSERT INTO student_basic (name, age, score) VALUES (?,?,?)";
		Connection con = null;
		PreparedStatement psm = null;
		try {
			// 获得数据库连接
			con = DBConnector.getMySQLConnection(null, null, null, dbName,
					userName, password);
			psm = con.prepareStatement(sql);
			
			// 将SQL语句中的？赋值
			psm.setString(1, "wangwu");
			psm.setInt(2, 17);
			psm.setDouble(3, 98);
			psm.executeUpdate();
		} catch (ClassNotFoundException e1) {
			throw e1;
		} catch (SQLException e2) {
			throw e2;
		} finally {
			OperateDB.closeStatement(psm);
			// 关闭数据库连接
			OperateDB.closeConnection(con);
		}
	}
}
