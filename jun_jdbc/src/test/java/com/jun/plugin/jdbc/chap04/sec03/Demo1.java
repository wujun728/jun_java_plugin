package com.jun.plugin.jdbc.chap04.sec03;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.jun.plugin.jdbc.model.Book;
import com.jun.plugin.jdbc.util.DbUtil;

public class Demo1 {

	private static DbUtil dbUtil=new DbUtil();
	
	/**
	 * ����ͼ��
	 * @param book
	 * @return
	 * @throws Exception
	 */
	private static int updateBook(Book book)throws Exception{
		Connection con=dbUtil.getCon();
		String sql="update t_book set bookName=?,price=?,author=?,bookTypeId=? where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, book.getBookName());
		pstmt.setFloat(2, book.getPrice());
		pstmt.setString(3, book.getAuthor());
		pstmt.setInt(4, book.getBookTypeId());
		pstmt.setInt(5, book.getId());
		int result=pstmt.executeUpdate();
		dbUtil.close(pstmt, con);
		return result;
	}
	
	public static void main(String[] args) throws Exception{
		Book book=new Book(12,"K2", 2, "K", 2);
		int result=updateBook(book);
		if(result==1){
			System.out.println("���³ɹ���");
		}else{
			System.out.println("����ʧ�ܣ�");
		}
	}
}
