package com.itheima.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import org.junit.Test;

import com.itheima.util.JdbcUtil;

/*
delimiter $$

CREATE PROCEDURE demoSp(IN inputParam VARCHAR(255), INOUT inOutParam varchar(255))
BEGIN
    SELECT CONCAT('zyxw---', inputParam) into inOutParam;
END $$

delimiter ;
 */

//如何调用已经存在的存储过程
public class CallableDemo {
	@Test
	public void test1() throws Exception{
		Connection conn = JdbcUtil.getConnection();
		CallableStatement stmt = conn.prepareCall("{call demoSp(?,?)}");
		//输入参数：设置值
		//输出参数：注册数据类型即可
		stmt.setString(1, "YY");
		stmt.registerOutParameter(2, Types.VARCHAR);
		
		stmt.execute();
		//打印执行的结果
		System.out.println(stmt.getString(2));
		JdbcUtil.release(null, stmt, conn);
	}
}
