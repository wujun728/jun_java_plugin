

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

import com.jun.plugin.dbutils.utils.DbBuilder;

public class AccountDao {
	
//	private static DbBuilder dbUtil=new DbBuilder();

	/**
	 * @param con
	 * @param accountName
	 * @param account
	 * @throws Exception
	 */
	private static void outCount(Connection con,String accountName,int account)throws Exception{
		String sql="update t_account set accountBalance=accountBalance-? where accountName=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, account);
		pstmt.setString(2, accountName);
		pstmt.executeUpdate();
	}
	
	/**
	 * @param con
	 * @param accountName
	 * @param account
	 * @throws Exception
	 */
	private static void inCount(Connection con,String accountName,int account)throws Exception{
		String sql="update t_account set account=accountBalance+? where accountName=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, account);
		pstmt.setString(2, accountName);
		pstmt.executeUpdate();
	}
	
	
	public static void main(String[] args) {
		Connection con=null;
		Savepoint sp=null;
		try {
			con=DbBuilder.getCon(); 
			con.setAutoCommit(false); 
			System.out.println("设置手动提交事务");
			int account=500;
			outCount(con, "test1", account);
			sp=con.setSavepoint(); 
			inCount(con, "test2", account);
			System.out.println("end");
		} catch (Exception e) {
			try {
				con.rollback(sp); 
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				con.commit();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	 
}
