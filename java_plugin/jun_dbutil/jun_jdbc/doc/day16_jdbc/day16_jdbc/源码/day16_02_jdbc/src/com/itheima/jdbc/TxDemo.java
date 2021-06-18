package com.itheima.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

import com.itheima.util.JdbcUtil;

//事务控制案例
/*


create table account(
	id int primary key auto_increment,
	name varchar(40),
	money float
)character set utf8 collate utf8_general_ci;

insert into account(name,money) values('aaa',1000);
insert into account(name,money) values('bbb',1000);
insert into account(name,money) values('ccc',1000);

 */
public class TxDemo {
	@Test
	public void test1(){
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			conn = JdbcUtil.getConnection();
			conn.setAutoCommit(false);//相当于start transaction
			
			stmt = conn.prepareStatement("update account set money=money-100 where name='bbb'");
			stmt.executeUpdate();
			
//			int i=1/0;
			
			stmt = conn.prepareStatement("update account set money=money+100 where name='aaa'");
			stmt.executeUpdate();
			
			conn.commit();// 提交事务
		}catch(Exception e){
//			if(conn!=null){
//				try {
//					conn.rollback();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(null, stmt, conn);
		}
	}
}
