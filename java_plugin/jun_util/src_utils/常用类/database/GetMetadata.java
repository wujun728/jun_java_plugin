package book.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 获取数据库和表的元数据
 * 使用DatabaseMetaData获得数据库的元数据
 * 使用ResultSetMetaData获得表的元数据
 */
public class GetMetadata {
	/**
	 * 获得数据库的元数据
	 * @param con	与数据库的连接
	 */
	public static void showDatabaseMetadata(Connection con){
		try {
			// 得到数据库的元数据
			DatabaseMetaData md = con.getMetaData();
			System.out.println("数据库" + md.getURL() + "的元数据如下：");
			
			// 显示元数据信息
			System.out.println("驱动: " + md.getDriverName());
			System.out.println("驱动版本号: " + md.getDriverVersion());
			System.out.println("登陆用户名: " + md.getUserName());
			System.out.println("数据库产品名: " + md.getDatabaseProductName());
			System.out.println("数据库产品版本号: " + md.getDatabaseProductVersion());
			System.out.println("支持的SQL关键字: ");
			System.out.println(md.getSQLKeywords());
			System.out.println("操作数字的函数: ");
			System.out.println(md.getNumericFunctions());
			System.out.println("操作字符串的函数: ");
			System.out.println(md.getStringFunctions());
			System.out.println("系统函数: ");
			System.out.println(md.getSystemFunctions());
			System.out.println("时间和日期函数: ");
			System.out.println(md.getTimeDateFunctions());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示数据表的元数据，主要是列的信息
	 * @param con	与数据库的连接
	 * @param tableName	 数据表名
	 */
	public static void showTableMetadata(Connection con, String tableName){
		String sql = "SELECT * FROM " + tableName;
		Statement sm = null;
		try {
			// 首先获得表的所有数据
			sm = con.createStatement();
			ResultSet rs = sm.executeQuery(sql);
			
			// 得到结果集的元数据
			ResultSetMetaData md = rs.getMetaData();
			
			System.out.println("数据表" + tableName + "的元数据如下：");
			// 表的列数
			int columnCount = md.getColumnCount();
			System.out.println("column count: " + columnCount);
			System.out.println();
			StringBuffer sb = new StringBuffer("");
			sb.append("sn\tname\t\t").append("type\t\t");
			sb.append("scale\t").append("isNullable");
			System.out.println(sb);
			sb.delete(0, sb.length());
			// 输出列的属性信息
			for (int i=1; i<=columnCount; i++){
				sb.append(i).append("\t");
				sb.append(md.getColumnName(i)).append("\t\t");
				sb.append(md.getColumnTypeName(i)).append("\t\t");
				sb.append(md.getScale(i)).append("\t");
				sb.append(md.isNullable(i));
				System.out.println(sb);
				sb.delete(0, sb.length());
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭Statement
			if (sm != null){
				try {
					sm.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		String dbName = "studentdb";
		String tableName = "student_basic";
		String userName = "test";
		String password = "test";

		Connection con = null;
		try {
			// 获得数据库连接
			con = DBConnector.getMySQLConnection(null, null, null, dbName,
					userName, password);
			// 显示数据库的元信息
			GetMetadata.showDatabaseMetadata(con);
			System.out.println();
			// 显示数据表的元信息
			GetMetadata.showTableMetadata(con, tableName);
		} catch (ClassNotFoundException e1) {
			throw e1;
		} catch (SQLException e2) {
			throw e2;
		} finally {
			// 关闭数据库连接
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}