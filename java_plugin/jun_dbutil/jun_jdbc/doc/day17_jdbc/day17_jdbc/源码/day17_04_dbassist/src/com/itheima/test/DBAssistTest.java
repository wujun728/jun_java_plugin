package com.itheima.test;

import java.util.List;

import org.junit.Test;

import com.itheima.dbassist.BeanHanlder;
import com.itheima.dbassist.BeanListHanlder;
import com.itheima.dbassist.DBAssist;
import com.itheima.domain.Account;
import com.itheima.util.DBCPUtil;

public class DBAssistTest {
	private DBAssist da = new DBAssist(DBCPUtil.getDataSource());
	@Test
	public void testAdd(){
		da.update("insert into account values (?,?,?)", 7,"ggg",1000);
	}
	@Test
	public void testUpdate(){
		da.update("update account set money=? where id=?", 10000,7);
	}
	@Test
	public void testDel(){
		da.update("delete from account where id=?",7);
	}
	@Test
	public void testQueryOne(){
		Object obj = da.query("select * from account where id=?",new BeanHanlder(Account.class),2);
		System.out.println(obj);
	}
	@Test
	public void testQueryAll(){
		List list = (List)da.query("select * from account",new BeanListHanlder(Account.class));
		for(Object o:list)
			System.out.println(o);
	}
}
