package book.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * 获取、创建、调用数据库的存储过程
 */
public class StorageProcedure {

	/**
	 * 列出数据库中所有的存储过程名
	 * @param con	数据库的连接
	 */
	public static void listStorageProcedureName(Connection con){
		   try {
	        // 获得数据库的元数据
	        DatabaseMetaData md = con.getMetaData();
	        // 获得所有的存储过程的描述
	        ResultSet resultSet = md.getProcedures(null, null, "%");
	    
	        //显示存储过程名，位于结果集的第三个字段
	        System.out.println("数据库现有的存储过程名为：");
	        while (resultSet.next()) {
	            String procName = resultSet.getString(3);
	            System.out.print(procName + "\t");
	        }
	        System.out.println();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	}
	/**
	 * 调用存储过程
	 * @param con
	 */
	public static void callStorageProcedure(Connection con){
	    CallableStatement cs = null;
	    try {
	      /*** 调用无参数的存储过程 ***/
	    	// 该存储过程往数据表中插入一条数据
	        cs = con.prepareCall("{call my_insert_proc()}");
	        cs.execute();
	    
	      /**** 调用有一个输入参数的存储过程 ****/
	        // 该存储过程往数据表中插入一条数据，其中有一列的值为参数值
	        cs = con.prepareCall("{call my_insert_proc1(?)}");
	        //设置参数
	        cs.setInt(1, 18);
	        // 执行
	        cs.execute();
	    
	      /*** 调用有一个输出参数的存储过程 ****/
	        // 该存储过程返回数据表中的记录数
	        cs = con.prepareCall("{call my_count_proc1(?)}");
	        // 注册输出参数的类型
	        cs.registerOutParameter(1, Types.INTEGER);
	        // 执行
	        cs.execute();
	        // 获取输出参数的值
	        int outParam = cs.getInt(1);
	        System.out.println("my_count_proc1() 执行结果：" + outParam);
	    
	      /***	调用有一个输入参数和一个输出参数的存储过程	***/
	        // 该存储过程返回数据表中score>输入参数的记录数
	        cs = con.prepareCall("{call my_count_proc(?,?)}");
	        // 注册输出参数的类型
	        cs.registerOutParameter(2, Types.INTEGER);
	        // 设置输入参数的值
	        cs.setInt(1, 90);
	        // 执行
	        cs.execute();
	        // 获取输出参数的值
	        outParam = cs.getInt(2);
	        System.out.println("my_count_proc 执行结果：" + outParam);
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	    	OperateDB.closeStatement(cs);
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
			// 列出数据库的所有存储过程名
			StorageProcedure.listStorageProcedureName(con);
			// 调用存储过程
			StorageProcedure.callStorageProcedure(con);
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
