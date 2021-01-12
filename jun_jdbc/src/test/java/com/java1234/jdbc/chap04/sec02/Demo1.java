package com.java1234.jdbc.chap04.sec02;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.java1234.jdbc.model.Book;
import com.java1234.jdbc.util.DbUtil;

public class Demo1 {

	private static DbUtil dbUtil=new DbUtil();
	
	/**
	 * 添加图书
	 * @param book
	 * @return
	 * @throws Exception
	 */
	private static int addBook(Book book)throws Exception{
		Connection con=dbUtil.getCon(); // 获取连接
		String sql="insert into t_book values(null,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, book.getBookName());  // 给第一个坑设置值
		pstmt.setFloat(2, book.getPrice());  // 给第二个坑设置值
		pstmt.setString(3, book.getAuthor()); // 给第三个坑设置值
		pstmt.setInt(4, book.getBookTypeId());  // 给第四个坑设置值
		int result=pstmt.executeUpdate();
		dbUtil.close(pstmt, con);
		return result;
	}
	
	public static void main(String[] args) throws Exception{
		Book book=new Book("Java叉叉2", 1, "叉叉", 1);
		int result=addBook(book);
		if(result==1){
			System.out.println("添加成功！");
		}else{
			System.out.println("添加失败！");
		}
	}
}
