package book.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 判断数据库是否支持事务，如果支持，如何实现事务的提交与回滚。
 * MySQL中如果要使用事物，必须使用InnoDB存储引擎，在创建表时，后面加上ENGINE=InnoDB。
 * MySQL默认的存储引擎是MyISAM，不支持事物
 */
public class Transaction {

	/**
	 * 判断数据库是否支持事务
	 * @param con	数据库的连接
	 * @return
	 */
	public static boolean supportTransaction(Connection con){
		try {
			// 得到数据库的元数据
			DatabaseMetaData md = con.getMetaData();
			return md.supportsTransactions();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 将一组SQL语句放在一个事务里执行，要某全部执行通过，要某全部不执行
	 * @param con	数据库的连接
	 * @param sqls	待执行的SQL数组
	 */
	public static void goTransaction(Connection con, String[] sqls){
		if (sqls == null){
			return ;
		}
		Statement sm = null;
		try {
			// 事务开始
			System.out.println("事务开始！");
			// 设置连接不自动提交，即用该连接进行的操作都不更新到数据库
			con.setAutoCommit(false);
			sm = con.createStatement();
			for (int i=0; i<sqls.length; i++){
				// 执行SQL语句，但是没更新到数据库
				sm.execute(sqls[i]);
			}
			// 提交，立即更新到数据库
			System.out.println("事务提交！");
			con.commit();
			System.out.println("事务结束！");
			// 事务结束
		} catch (SQLException e) {
			try {
				// 出现异常时，进行回滚，取消前面执行的操作
				System.out.println("事务执行失败，进行回滚！");
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			OperateDB.closeStatement(sm);
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		String dbName = "studentdb";
		String userName = "test";
		String password = "test";
		String[] sqls = new String[3];
		sqls[0] = "UPDATE student_basic_innodb SET score=93 where name='john'";
		sqls[1] = "INSERT INTO student_basic_innodb (name, age, score)"
			+ " VALUES ('zhangsan', 17, 86)";
		// 执行这条语句会引起错误，因为表student_basic_innodb没有xxxxxxx列
		sqls[2] = "DELETE FROM student_basic_innodb where xxxxxxx='wade'";
		
		Connection con = null;
		try {
			// 获得数据库连接
			con = DBConnector.getMySQLConnection(null, null, null, dbName,
					userName, password);
			// 判断是否支持批处理
			boolean supportTransaction = Transaction.supportTransaction(con);
			System.out.println("支持事务？ " + supportTransaction);
			if (supportTransaction){
				// 执行事务
				Transaction.goTransaction(con, sqls);
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