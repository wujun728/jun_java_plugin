package com.itheima.dbutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import com.itheima.util.DBCPUtil;

/*
create database day18;
use day18;
create table student(
	id int primary key,
	name varchar(100),
	birthday date
);
 */
public class DBUtilCRUD {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	@Test
	public void testAdd() throws SQLException{
		qr.update("insert into student values(?,?,?)", 1,"杨洋",new Date());
	}
	@Test
	public void testUpdate() throws SQLException{
		qr.update("update student set birthday=? where id=?", "1993-08-01",1);
	}
	@Test
	public void testDel() throws SQLException{
		qr.update("delete from student where id=?", 1);
	}
	//批处理插入10条
	@Test
	public void testBatch() throws SQLException{
		Object params[][] = new Object[10][];//高维：记录的条数。低维：每条记录需要的参数
		for(int i=0;i<params.length;i++){
			params[i] = new Object[]{i+1,"杨洋"+(i+1),new Date()};
		}
		qr.batch("insert into student values(?,?,?)", params);
	}
	//大文本：了解
	/*
	create table t1(
		id int primary key,
		content longtext
	);
	 */
	@Test//大文本类型===Clob
	public void testClob()throws Exception{
		File file = new File("src/pqy&sx.txt");//文件很大，内存浪费
		Reader reader = new FileReader(file);
		char ch[] = new char[(int)file.length()];
		reader.read(ch);
		reader.close();
		Clob clob = new SerialClob(ch);
		qr.update("insert into t1 values(?,?)", 1,clob);//类型不批配。流不是数据库的类型
	}
	//大二进制：了解
	/*
	create table t2(
		id int primary key,
		content longblob
	);
	 */
	@Test//大二进制类型===Blob
	public void testBlob()throws Exception{
		InputStream in  = new FileInputStream("src/22.jpg");
		byte b[] = new byte[in.available()];
		in.read(b);
		in.close();
		Blob blob = new SerialBlob(b);
		qr.update("insert into t2 values(?,?)", 1,blob);//类型不批配。流不是数据库的类型
	}
}
