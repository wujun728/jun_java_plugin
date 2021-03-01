package book.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 本例演示使用ResultSet更新数据库的数据. 包括修改、插入和删除
 */
public class UpdateWithResultSet {
	/**
	 * 使用ResultSet可以更新数据库的数据，前提是与之相连的Statement没有被关闭。
	 * @param con
	 */
	public static void update(Connection con){
		String sql = "SELECT * FROM student_basic";
		Statement sm = null;
		ResultSet rs = null;
		try {
			// 创建Statement
			// ResultSet.TYPE_SCROLL_SENSITIVE表示在ResultSet中可以随心所欲的先前向后移动游标，
			// 同时ResultSet的值有所改变的时候，它可以得到改变后的最新的值。
			// ResultSet.CONCUR_UPDATABLE表示在ResultSet中的数据记录可以任意修改，然后更新会数据库
			sm = con.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = sm.executeQuery(sql);
			
			/***用ResultSet更新第一条数据***/
			// 指针移到第一条数据
			rs.absolute(1);
			// 修改数据
			rs.updateDouble("score", 70);// 修改score列
			rs.updateString(5, "updated by ResultSet!");// 修改第5列
			// 如果想取消对当前记录的修改，可以取消
			rs.cancelRowUpdates();
			System.out.println("准备用ResultSet修改一条记录！");
			// 如果决定修改，则使用updateRow方法提交修改
			rs.absolute(1);// 必须再调用一次absolute，因为，
			rs.updateDouble("score", 70);
			rs.updateString(5, "updated by ResultSet!");
			// 将修改提交到数据源
			rs.updateRow();
			OperateDB.showResultSet(rs);
			
			/***用ResultSet插入一条数据***/
			System.out.println("准备用ResultSet插入一条记录！");
			// 指针移动到插入点
			rs.moveToInsertRow();
			// 为新数据设置值
			rs.updateString("name", "mike");
			rs.updateInt("age", 18);
			rs.updateDouble("score", 88);
			// 将插入提交到数据源
			rs.insertRow();
			OperateDB.showResultSet(rs);
			
			/***用ResultSet删除一条数据***/
			rs.last();
			System.out.println("准备用ResultSet删除最后一条记录！" );
			rs.deleteRow();
			OperateDB.showResultSet(rs);
			
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭Statement
			OperateDB.closeStatement(sm);
		}
	}
	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		String dbName = "studentdb";
		String userName = "test";
		String password = "test";
		
		Connection con = null;
		try {
			// 获得数据库连接
			con = DBConnector.getMySQLConnection(null, null, null, dbName,
					userName, password);
			// 更新数据库
			UpdateWithResultSet.update(con);
		} catch (ClassNotFoundException e1) {
			throw e1;
		} catch (SQLException e2) {
			throw e2;
		} finally {
			// 关闭数据库连接
			OperateDB.closeConnection(con);
		}
	}
}