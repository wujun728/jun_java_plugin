package com.jun.plugin.jdbc.jdbc2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

import oracle.jdbc.OracleTypes;

public class CallProdouce {
	static CallableStatement cs = null;
	static ResultSet rs = null;
	public static void main(String[] args) {
		Connection conn = null;
		String sql = "{call add_pro(?,?,?)}";
		try {
			conn = JdbcUtil.getConnection();
			cs = conn.prepareCall(sql);
			cs.setInt(1,100);
			cs.setInt(2,200);
			cs.registerOutParameter(3,Types.INTEGER);
			boolean flag = cs.execute();
			System.out.println("flag="+flag);
			int sum = cs.getInt(3);
			System.out.println("sum="+sum);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
	}
	
	
	/*
	 * 对应的存储过程语句 --有参数无返回值 create or replace procedure updateName(byNo in
	 * number,useName in varchar2) as begin update emp e set e.ename = useName
	 * where e.empno = byNo; end;
	 */
	public void callProcedureY(Connection conn) throws Exception {
		// 指定调用的存储过程
		cs = conn.prepareCall("{call updateName(?,?)}");
		cs.setInt(1, 7499);// 设置存储过程对应的输入参数
		cs.setString(2, "www");// 对应下标从1 开始
		// 执行存储过程调用
		cs.execute();
	}

	/**
	 * 
	 * @Discription 执行无参数，无返回值的存储过程
	 * @return void
	 * @param conn
	 * @throws Exception
	 */
	/*
	 * 对应的存储过程语句 --无参数 create or replace procedure insertLine as begin insert
	 * into emp
	 * values(7333,'ALLEN','SAL',7698,to_date('2011/11/11','yyyy-MM-dd'),1600,
	 * 300,30); end;
	 */
	public void callProcedure(Connection conn) throws Exception {
		// 指定调用的存储过程
		cs = conn.prepareCall("{call insertLine}");
		// 执行存储过程的调用
		cs.execute();
	}

	/**
	 * 
	 * @Discription 执行有参数，有返回值的存储过程
	 * @return void
	 * @param conn
	 * @throws Exception
	 */
	/*
	 * 对应的存储过程语句 --有参数，有返回值 create or replace procedure deleteLine(byNo in
	 * number,getCount out number) as begin delete from emp e where e.empno =
	 * byNo; select count(*) into getCount from emp e; end;
	 */
	public void callProcedureYY(Connection conn) throws Exception {
		// 指定调用的存储过程
		cs = conn.prepareCall("{call deleteLine(?,?)}");
		// 设置参数
		cs.setInt(1, 7839);
		// 这里需要配置OUT的参数新型
		cs.registerOutParameter(2, OracleTypes.NUMBER);
		// 执行调用
		cs.execute();
		// 输入返回值
		System.out.println(cs.getString(2));
	}

	/**
	 * 
	 * @Discription 执行有参数，返回集合的存储过程
	 * @return void
	 * @param conn
	 * @throws Exception
	 */
	/*
	 * 对应的存储过程语句 --有参数返回一个列表，使用package create or replace package someUtils as
	 * type cur_ref is ref cursor; procedure selectRows(cur_ref out
	 * someUtils.cur_ref); end someUtils; create or replace package body
	 * someUtils as procedure selectRows(cur_ref out someUtils.cur_ref) as begin
	 * open cur_ref for select * from emp e; end selectRows; end someUtils;
	 */
	public void callProcedureYYL(Connection conn) throws Exception {
		// 执行调用的存储过程
		cs = conn.prepareCall("{call someUtils.selectRows(?)}");
		// 设置返回参数
		cs.registerOutParameter(1, OracleTypes.CURSOR);
		// 执行调用
		cs.execute();
		// 获取结果集 结果集是一个Object类型，需要进行强制转换 rs = (ResultSet)
		rs = (ResultSet) cs.getObject(1);
		// 输出返回值
		while (rs.next()) {
			System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
		}
	}

	/**
	 * 
	 * @Discription 执行有参数的函数
	 * @return void
	 * @param conn
	 * @throws Exception
	 */
	/*
	 * 对应的存储过程语句 --创建函数，有参数 create or replace function useOther(byNo in number)
	 * return String as returnValue char(10); begin select count(*) into
	 * returnValue from emp e where e.empno > byNo; return returnValue; end;
	 */
	public void callProcedureFY(Connection conn) throws Exception {
		// 指定调用的函数
		cs = conn.prepareCall("{? = call useOther(?)}");
		// 配置OUT参数信息
		cs.registerOutParameter(1, OracleTypes.CHAR);
		// 配置输入参数
		cs.setInt(2, 1111);
		// 执行过程调用
		cs.execute();
		// 输入返回值
		System.out.println(cs.getString(1));
	}

	 
}





