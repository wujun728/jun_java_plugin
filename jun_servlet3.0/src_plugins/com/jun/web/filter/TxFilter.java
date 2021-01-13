package com.jun.web.filter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class TxFilter implements Filter{
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.err.println("thread:"+Thread.currentThread().getName());
		//获取连接
		Connection con = null;
		//在try中开始事务
		try{
//			con = JdbcUtil.getConn();
			//开始事务
			con.setAutoCommit(false);
			//放行
			chain.doFilter(request, response);
			//如果没有出错。
			con.commit();
		}catch(Exception e){
			System.err.println("出错了");
			try {
				if(e instanceof SQLException){
					con.rollback();
				}else{
					con.commit();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void destroy() {
	}
}
