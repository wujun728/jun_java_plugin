package com.java1234.jdbc.chap03.sec02;

import java.sql.Connection;
import java.sql.Statement;

import com.java1234.jdbc.model.Book;
import com.java1234.jdbc.util.DbUtil;

public class Demo2 {

	private static DbUtil dbUtil=new DbUtil();
	
	/**
	 * 添加图书2
	 * @param book
	 * @return
	 * @throws Exception
	 */
	private static int addBook2(Book book)throws Exception{
		Connection con=dbUtil.getCon();  // 获取连接
		String sql="insert into t_book values(null,'"+book.getBookName()+"',"+book.getPrice()+",'"+book.getAuthor()+"',"+book.getBookTypeId()+")";
		Statement stmt=con.createStatement(); // 创建Statement
		int result=stmt.executeUpdate(sql);
		dbUtil.close(stmt, con);  // 关闭Statement和连接
		return result;
	}
	
	/**
	 * 添加图书
	 * @param bookName
	 * @param price
	 * @param author
	 * @param bookTypeId
	 * @return
	 * @throws Exception
	 */
	private static int addBook(String bookName,float price,String author,int bookTypeId)throws Exception{
		Connection con=dbUtil.getCon();  // 获取连接
		String sql="insert into t_book values(null,'"+bookName+"',"+price+",'"+author+"',"+bookTypeId+")";
		Statement stmt=con.createStatement(); // 创建Statement
		int result=stmt.executeUpdate(sql);
		dbUtil.close(stmt, con);  // 关闭Statement和连接
		return result;
	}
	
	public static void main(String[] args) throws Exception{
		/*int result=addBook("Java牛牛", 121, "牛哥", 1);
		if(result==1){
			System.out.println("添加成功！");
		}else{
			System.out.println("添加失败！");
		}*/   
		// 多行注释  ctrl+shift+/
		Book book=new Book("Java牛牛2", 1212, "牛哥2", 2);
		int result=addBook2(book);
		if(result==1){
			System.out.println("添加成功！");
		}else{
			System.out.println("添加失败！");
		}
	}
}
