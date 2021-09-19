package com.jun.plugin.jdbc.chap06.sec01;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
		String sql="insert into t_book values(null,?,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, book.getBookName());  // ����һ��������ֵ
		pstmt.setFloat(2, book.getPrice());  // ���ڶ���������ֵ
		pstmt.setString(3, book.getAuthor()); // ��������������ֵ
		pstmt.setInt(4, book.getBookTypeId());  // �����ĸ�������ֵ
		File context=book.getContext(); // ��ȡ�ļ�
		InputStream inputStream=new FileInputStream(context);
		pstmt.setAsciiStream(5, inputStream,context.length());  // �������������ֵ
		int result=pstmt.executeUpdate();
		dbUtil.close(pstmt, con);
		return result;
	}
	
	public static void getBook(int id)throws Exception{
		Connection con=dbUtil.getCon();
		String sql="select * from t_book where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, id);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			String bookName=rs.getString("bookName");
			float price=rs.getFloat("price");
			String author=rs.getString("author");
			int bookTypeId=rs.getInt("bookTypeId");
			Clob c=rs.getClob("context");
			String context=c.getSubString(1, (int)c.length());
			System.out.println("ͼ�����ƣ�"+bookName);
			System.out.println("ͼ��۸�:"+price);
			System.out.println("ͼ�����ߣ�"+author);
			System.out.println("ͼ������ID��"+bookTypeId);
			System.out.println("ͼ�����ݣ�"+context);
		}
		dbUtil.close(pstmt, con);
	}
	
	public static void main(String[] args)throws Exception {
		/*File context=new File("c:/helloWorld.txt");
		Book book=new Book("helloWorld", 100, "С��", 1,context);
		int result=addBook(book);
		if(result==1){
			System.out.println("��ӳɹ���");
		}else{
			System.out.println("���ʧ�ܣ�");
		}*/
		getBook(16);
	}
}
