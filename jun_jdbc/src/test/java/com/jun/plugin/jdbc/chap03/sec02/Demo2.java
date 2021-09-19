package com.jun.plugin.jdbc.chap03.sec02;

import java.sql.Connection;
import java.sql.Statement;

import com.jun.plugin.jdbc.model.Book;
import com.jun.plugin.jdbc.util.DbUtil;

public class Demo2 {

	private static DbUtil dbUtil=new DbUtil();
	
	/**
	 * ���ͼ��2
	 * @param book
	 * @return
	 * @throws Exception
	 */
	private static int addBook2(Book book)throws Exception{
		Connection con=dbUtil.getCon();  // ��ȡ����
		String sql="insert into t_book values(null,'"+book.getBookName()+"',"+book.getPrice()+",'"+book.getAuthor()+"',"+book.getBookTypeId()+")";
		Statement stmt=con.createStatement(); // ����Statement
		int result=stmt.executeUpdate(sql);
		dbUtil.close(stmt, con);  // �ر�Statement������
		return result;
	}
	
	/**
	 * ���ͼ��
	 * @param bookName
	 * @param price
	 * @param author
	 * @param bookTypeId
	 * @return
	 * @throws Exception
	 */
	private static int addBook(String bookName,float price,String author,int bookTypeId)throws Exception{
		Connection con=dbUtil.getCon();  // ��ȡ����
		String sql="insert into t_book values(null,'"+bookName+"',"+price+",'"+author+"',"+bookTypeId+")";
		Statement stmt=con.createStatement(); // ����Statement
		int result=stmt.executeUpdate(sql);
		dbUtil.close(stmt, con);  // �ر�Statement������
		return result;
	}
	
	public static void main(String[] args) throws Exception{
		/*int result=addBook("Javaţţ", 121, "ţ��", 1);
		if(result==1){
			System.out.println("��ӳɹ���");
		}else{
			System.out.println("���ʧ�ܣ�");
		}*/   
		// ����ע��  ctrl+shift+/
		Book book=new Book("Javaţţ2", 1212, "ţ��2", 2);
		int result=addBook2(book);
		if(result==1){
			System.out.println("��ӳɹ���");
		}else{
			System.out.println("���ʧ�ܣ�");
		}
	}
}
