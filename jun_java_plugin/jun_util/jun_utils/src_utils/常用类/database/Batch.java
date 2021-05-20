package book.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 执行一批SQL语句（批处理）
 */
public class Batch {
	
	/**
	 * 判断数据库是否支持批处理
	 * @param con
	 * @return
	 */
	public static boolean supportBatch(Connection con){
		try {
			// 得到数据库的元数据
			DatabaseMetaData md = con.getMetaData();
			return md.supportsBatchUpdates();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 执行一批SQL语句
	 * @param con	数据库的连接
	 * @param sqls	待执行的SQL数组
	 * @return
	 */
	public static int[] goBatch(Connection con, String[] sqls){
		if (sqls == null){
			return null;
		}
		Statement sm = null;
		try {
			sm = con.createStatement();
			// 将所有的SQL语句添加到Statement中
			for (int i=0; i<sqls.length; i++){
				sm.addBatch(sqls[i]);
			}
			// 一次执行多条SQL语句
			return sm.executeBatch();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OperateDB.closeStatement(sm);
		}
		return null;
	}

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		String dbName = "studentdb";
		String tableName = "student_basic";
		String userName = "test";
		String password = "test";
		String[] sqls = new String[3];
		sqls[0] = "UPDATE student_basic SET score=95 where name='john'";
		sqls[1] = "INSERT INTO student_basic (name, age, score) VALUES ('lisi', 17, 78)";
		sqls[2] = "DELETE FROM student_basic where name='zhangsan'";
		
		Connection con = null;
		try {
			// 获得数据库连接
			con = DBConnector.getMySQLConnection(null, null, null, dbName,
					userName, password);
			// 判断是否支持批处理
			boolean supportBatch = Batch.supportBatch(con);
			System.out.println("支持Batch？ " + supportBatch);
			if (supportBatch){
				// 执行一批SQL语句
				int[] results = Batch.goBatch(con, sqls);
				// 分析执行的结果
				for (int i=0; i<sqls.length; i++){
					if (results[i] >= 0){
						System.out.println("语句: " + sqls[i] + " 执行成功，影响了"
								+ results[i] + "行数据");
					} else if (results[i] == Statement.SUCCESS_NO_INFO){
						System.out.println("语句: " + sqls[i] + " 执行成功，影响的行数未知");
					} else if (results[i] == Statement.EXECUTE_FAILED){
						System.out.println("语句: " + sqls[i] + " 执行失败");
					}
				}
			}
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