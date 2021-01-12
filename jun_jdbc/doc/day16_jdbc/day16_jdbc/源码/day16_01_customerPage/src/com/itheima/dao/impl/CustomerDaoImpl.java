package com.itheima.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.itheima.dao.CustomerDao;
import com.itheima.domain.Customer;
import com.itheima.util.JdbcUtil;

public class CustomerDaoImpl implements CustomerDao {

	public List<Customer> findAll() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.prepareStatement("select * from customers");
			rs = stmt.executeQuery();
			List<Customer> cs = new ArrayList<Customer>();
			while(rs.next()){
				Customer c = new Customer();
				c.setId(rs.getString("id"));
				c.setName(rs.getString("name"));
				c.setGender(rs.getString("gender"));
				c.setBirthday(rs.getDate("birthday"));
				c.setPhonenum(rs.getString("phonenum"));
				c.setEmail(rs.getString("email"));
				c.setHobby(rs.getString("hobby"));
				c.setType(rs.getString("type"));
				c.setDescription(rs.getString("description"));
				cs.add(c);
			}
			return cs;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
	}

	public void save(Customer c) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.prepareStatement("insert into customers (id,name,gender,birthday,phonenum,email,hobby,type,description) values (?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, c.getId());
			stmt.setString(2, c.getName());
			stmt.setString(3, c.getGender());
			stmt.setDate(4, new java.sql.Date(c.getBirthday().getTime()));
			stmt.setString(5, c.getPhonenum());
			stmt.setString(6, c.getEmail());
			stmt.setString(7, c.getHobby());
			stmt.setString(8, c.getType());
			stmt.setString(9, c.getDescription());
			
			stmt.executeUpdate();
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
	}

	public Customer findById(String customerId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.prepareStatement("select * from customers where id=?");
			stmt.setString(1, customerId);
			rs = stmt.executeQuery();
			if(rs.next()){
				Customer c = new Customer();
				c.setId(rs.getString("id"));
				c.setName(rs.getString("name"));
				c.setGender(rs.getString("gender"));
				c.setBirthday(rs.getDate("birthday"));
				c.setPhonenum(rs.getString("phonenum"));
				c.setEmail(rs.getString("email"));
				c.setHobby(rs.getString("hobby"));
				c.setType(rs.getString("type"));
				c.setDescription(rs.getString("description"));
				return c;
			}
			return null;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
	}

	public void update(Customer c) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.prepareStatement("update customers set name=?,gender=?,birthday=?,phonenum=?,email=?,hobby=?,type=?,description=? where id=?");
			
			stmt.setString(1, c.getName());
			stmt.setString(2, c.getGender());
			stmt.setDate(3, new java.sql.Date(c.getBirthday().getTime()));
			stmt.setString(4, c.getPhonenum());
			stmt.setString(5, c.getEmail());
			stmt.setString(6, c.getHobby());
			stmt.setString(7, c.getType());
			stmt.setString(8, c.getDescription());
			stmt.setString(9, c.getId());
			
			stmt.executeUpdate();
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
	}

	public void delete(String customerId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.prepareStatement("delete from customers where id=?");
			
			stmt.setString(1, customerId);
			
			stmt.executeUpdate();
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
	}

	public int getTotalRecordsNum() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.prepareStatement("select count(*) from customers");
			rs = stmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
			return 0;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
	}

	public List<Customer> getPageRecords(int startIndex, int offset) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtil.getConnection();
			stmt = conn.prepareStatement("select * from customers limit ?,?");
			stmt.setInt(1, startIndex);
			stmt.setInt(2, offset);
			rs = stmt.executeQuery();
			List<Customer> cs = new ArrayList<Customer>();
			while(rs.next()){
				Customer c = new Customer();
				c.setId(rs.getString("id"));
				c.setName(rs.getString("name"));
				c.setGender(rs.getString("gender"));
				c.setBirthday(rs.getDate("birthday"));
				c.setPhonenum(rs.getString("phonenum"));
				c.setEmail(rs.getString("email"));
				c.setHobby(rs.getString("hobby"));
				c.setType(rs.getString("type"));
				c.setDescription(rs.getString("description"));
				cs.add(c);
			}
			return cs;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			JdbcUtil.release(rs, stmt, conn);
		}
	}

}
