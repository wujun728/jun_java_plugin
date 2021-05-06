package com.itheima.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.itheima.dao.AccountDao;
import com.itheima.util.DBCPUtil;
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
public class AccountDaoImpl implements AccountDao {
	private QueryRunner qr = new QueryRunner();
	public void transfer(String sourceAccountName, String targetAccontName,
			float money) {
		Connection conn = null;
		try {
			conn = DBCPUtil.getConnection();
			conn.setAutoCommit(false);//¿ªÆôÊÂÎñ
			qr.update(conn,"update account set money=money-? where name=?", money,sourceAccountName);
//			int i=1/0;
			qr.update(conn,"update account set money=money+? where name=?", money,targetAccontName);
		} catch (Exception e) {
			if(conn!=null){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			if(conn!=null){
				try {
					conn.commit();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
