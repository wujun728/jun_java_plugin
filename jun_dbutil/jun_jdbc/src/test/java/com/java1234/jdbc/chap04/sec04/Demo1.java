package com.java1234.jdbc.chap04.sec04;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.java1234.jdbc.util.DbUtil;

public class Demo1 {

	private static DbUtil dbUtil=new DbUtil();
	
	/**
	 * É¾³ýÍ¼Êé
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private static int deleteBook(int id)throws Exception{
		Connection con=dbUtil.getCon();
		String sql="delete from t_book where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, id);
		int result=pstmt.executeUpdate();
		dbUtil.close(pstmt, con);
		return result;
	}
	
	public static void main(String[] args)throws Exception {
		int result=deleteBook(12);
		if(result==1){
			System.out.println("É¾³ý³É¹¦£¡");
		}else{
			System.out.println("É¾³ýÊ§°Ü£¡");
		}
	}
}
