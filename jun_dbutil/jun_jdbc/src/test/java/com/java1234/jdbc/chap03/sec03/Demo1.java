package com.java1234.jdbc.chap03.sec03;

import java.sql.Connection;
import java.sql.Statement;

import com.java1234.jdbc.model.Book;
import com.java1234.jdbc.util.DbUtil;

public class Demo1 {

	private static DbUtil dbUtil = new DbUtil();

	/**
	 * 更新图书
	 * @param book
	 * @return
	 * @throws Exception
	 */
	private static int updateBook(Book book) throws Exception {
		Connection con = dbUtil.getCon(); // 获取连接
		String sql = "update t_book set bookName='" + book.getBookName()
				+ "',price=" + book.getPrice() + ",author='" + book.getAuthor()
				+ "',bookTypeId=" + book.getBookTypeId() + " where id="
				+ book.getId();  // ctrl+a 全选  ctrl+shift+F 格式化代码
		Statement stmt = con.createStatement(); // 创建Statement
		int result = stmt.executeUpdate(sql);
		dbUtil.close(stmt, con); // 关闭Statement和连接
		return result;
	}

	public static void main(String[] args) throws Exception{
		Book book=new Book(3,"Java牛牛2222", 121, "牛哥222", 1);
		int result=updateBook(book);
		if(result==1){
			System.out.println("更新成功！");
		}else{
			System.out.println("更新败！");
		}
		
	}
}
