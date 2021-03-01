package book.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 操作数据库，包括增删改查数据库的记录
 */
public class OperateDB {

	/**
	 * 查询数据库
	 * @param sm	与数据库连接的Statement
	 * @param sql	查询SQL语句
	 * @return 返回一个ResultSet结果集
	 */
	public static ResultSet queryDB(Statement sm, String sql){
		ResultSet rs = null;
		try {
			// 首先获得表的所有数据
			rs = sm.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return rs;
	}
	/**
	 * 修改数据库
	 * @param con	数据库的连接
	 * @param sql	修改SQL语句
	 * @return	返回修改影响的行数，为0表示一行数据都没有被修改
	 * 			为Statement.EXECUTE_FAILED表示执行失败。
	 */
	public static int updateDB(Connection con, String sql){
		Statement sm = null;
		int affectRows = 0;
		try {
			// 首先获得表的所有数据
			sm = con.createStatement();
			affectRows = sm.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			// 如果出现异常，则表示执行失败。
			affectRows = Statement.EXECUTE_FAILED;
		} finally {
			// 关闭Statement
			closeStatement(sm);
		}
		return affectRows;
	}
	/**
	 * 显示一个ResultSet结果集
	 * 在显示之前必须保证它所在的Statement是活着的
	 * @param rs
	 */
	public static void showResultSet(ResultSet rs){
		if (rs == null){
			return;
		}
		try {
			ResultSetMetaData md = rs.getMetaData();
			// 获得该ResultSet中的列数
			int columnCount = md.getColumnCount();
			
			// 如果结果集的指针没有位于第一条记录的前面
			// 将结果集的指针指向第一条记录的前面
			if(!rs.isBeforeFirst()){
				rs.beforeFirst();
			}

			// 从前往后的移动结果集指针，处理每条记录
			while (rs.next()){
				// 每条记录都包含columnCount个列
				for (int i=1; i<columnCount; i++){
					// 由于不知道该列的类型，所以用getObject方法获取值
					System.out.print(rs.getObject(i) + "\t");
				}
				System.out.print(rs.getObject(columnCount) + "\r\n");
			}
			rs.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭Statement
	 * @param sm
	 */
	public static void closeStatement(Statement sm){
		if (sm != null){
			try {
				sm.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	} 
	
	/**
	 * 关闭连接
	 * @param con
	 */
	public static void closeConnection(Connection con){
		if (con != null){
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	} 
	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		String dbName = "studentdb";
		String userName = "test";
		String password = "test";
		String querySQL = "SELECT * FROM student_basic";
		String updateSQL = "UPDATE student_basic SET score=82 where name='mary'";
		String insertSQL = "INSERT INTO student_basic (name, age, score)"
			+ " VALUES ('zhangsan', 17, 86)";
		String deleteSQL = "DELETE FROM student_basic where name='wade'";
		
		Connection con = null;
		Statement sm = null;
		try {
			// 获得数据库连接
			con = DBConnector.getMySQLConnection(null, null, null, dbName,
					userName, password);
			sm = con.createStatement();
			// 查询
			ResultSet rs = OperateDB.queryDB(sm, querySQL);
			System.out.println("修改数据表之前的数据：");
			OperateDB.showResultSet(rs);
			// 修改
			OperateDB.updateDB(con, updateSQL);
			OperateDB.updateDB(con, insertSQL);
			OperateDB.updateDB(con, deleteSQL);
			System.out.println();
			System.out.println("修改数据表之后的数据：");
			// 再查询
			rs = OperateDB.queryDB(sm, querySQL);
			OperateDB.showResultSet(rs);
			System.out.println();
			
			// 从ResultSet中提取值时可以指定列名和类型
			// 将结果集指针指向第一条数据
			rs.absolute(1);
			System.out.print("name: " + rs.getString("name") + "\t");
			System.out.println("age: " + rs.getInt("age"));
			
			rs.absolute(3);
			System.out.print("name: " + rs.getString("name") + "\t");
			System.out.println("age: " + rs.getInt("age"));
			// 关闭结果集
			rs.close();
		} catch (ClassNotFoundException e1) {
			throw e1;
		} catch (SQLException e2) {
			throw e2;
		} finally {
			// 关闭数据库连接
			closeStatement(sm);
			closeConnection(con);
		}
	}
	// TODO 需要确定statement或者connection关闭后，ResultSet是否还能用
	/***
	 * 注意：
	 * （1）一个Statement对象同时只能有一个结果集在活动.即使没有调用ResultSet的close()方法,
	 * 只要打开第二个结果集就隐含着对上一个结果集的关闭.所以如果你想同时对多个结果集操作,
	 * 就要创建多个Statement对象,如果不需要同时操作,
	 * 那么可以在一个Statement对象上顺序操作多个结果集.
	 * 
	 * （2）TODO
	 * 垃圾回收机制可以自动关闭它们
2.Statement关闭会导致ResultSet关闭
3.Connection关闭不会（？）导致Statement关闭
4.由于垃圾回收的线程级别是最低的，为了充分利用数据库资源，有必要显式关闭它们，尤其是使用Connection Pool的时候。
5.最优经验是按照ResultSet，Statement，Connection的顺序执行close
6.如果一定要传递ResultSet，应该使用RowSet，RowSet可以不依赖于Connection和Statement。
Java传递的是引用，所以如果传递ResultSet，你会不知道Statement和Connection何时关闭，不知道ResultSet何时有效
	 * 
	 */
}
