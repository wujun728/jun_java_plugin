package com.jun.plugin.jdbc.chap04.sec02;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.jun.plugin.jdbc.model.Book;
import com.jun.plugin.jdbc.util.DbUtil;

public class Demo1 {

	private static DbUtil dbUtil=new DbUtil();
	
	/**
	 * ���ͼ��
	 * @param book
	 * @return
	 * @throws Exception
	 */
	private static int addBook(Book book)throws Exception{
		Connection con=dbUtil.getCon(); // ��ȡ����
		String sql="insert into t_book values(null,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, book.getBookName());  // ����һ��������ֵ
		pstmt.setFloat(2, book.getPrice());  // ���ڶ���������ֵ
		pstmt.setString(3, book.getAuthor()); // ��������������ֵ
		pstmt.setInt(4, book.getBookTypeId());  // �����ĸ�������ֵ
		int result=pstmt.executeUpdate();
		dbUtil.close(pstmt, con);
		return result;
	}
	
	public static void main(String[] args) throws Exception{
		Book book=new Book("Java���2", 1, "���", 1);
		int result=addBook(book);
		if(result==1){
			System.out.println("��ӳɹ���");
		}else{
			System.out.println("���ʧ�ܣ�");
		}
	}
}
