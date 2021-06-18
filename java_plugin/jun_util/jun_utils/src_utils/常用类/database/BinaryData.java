package book.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 存取二进制数据到数据库
 */
public class BinaryData {
	
	/**
	 * 写二进制数据到数据库
	 * @param con
	 */
	public static void writeBinary(Connection con){
        String sql = "INSERT INTO student_address (name, address)"
        	+ " VALUES('john', ?)";
		PreparedStatement psm = null;
		try {
	        // 创建一个Statement，插入记录到数据库
			psm = con.prepareStatement(sql);
	        // 创建要写入的二进制数据
	        byte[] buffer = "Haidian district Beijing China.".getBytes();
	        // 设置SQL中？的值
	        psm.setBytes(1, buffer);
	        // 插入
	        psm.executeUpdate();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	    	OperateDB.closeStatement(psm);
	    }
	}
	/**
	 * 从数据库中读二进制数据
	 * @param con
	 */
	public static void readBinary(Connection con){
        String sql = "SELECT * FROM student_address where name = 'john'";
        Statement sm = null;
		try {
	        // 查询数据库
	        Statement stmt = con.createStatement();
	        ResultSet resultSet = stmt.executeQuery(sql);
	        while (resultSet.next()) {
	            // 取值
	            byte[] bytes = resultSet.getBytes("address");
	            System.out.println(new String(bytes));
	        }
	        resultSet.close();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
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
			// 写二进制数据
			BinaryData.writeBinary(con);
			// 读二进制数据
			BinaryData.readBinary(con);
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