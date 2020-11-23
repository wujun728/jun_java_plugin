package com.jun.base.jdbc.test;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.jun.base.jdbc.MysqlJdbc;
import com.jun.base.jdbc.ObjectsParamsHandler;
import com.jun.base.jdbc.ParamsHandler;

/**
 * 会员数据操作
 * 
 * @author Wujun
 *
 */
public class MemberDbControl extends MysqlJdbc {

	public MemberDbControl() throws ClassNotFoundException {
		super("localhost", "supermartket", "root", "root");
	}

	/**
	 * 添加会员(执行带参数SQL语句)
	 * 
	 * @param m
	 * @return
	 * @throws SQLException
	 */
	public int addMember(final Member m) throws SQLException {
		String sql = "insert into member ( `gender`, `countNum`, `telepone`, `mno`, `uname`, `birthday`) values ( ?,?,?,?,?,?)";
		return this.excuteUpdate(sql, new ParamsHandler() {
			@Override
			public void doHandle(PreparedStatement stmt) throws SQLException {
				stmt.setString(1, m.getGender());
				stmt.setInt(2, m.getCountNum());
				stmt.setString(3, m.getTelephone());
				stmt.setString(4, m.getMno());
				stmt.setString(5, m.getUname());
				stmt.setDate(6, new Date(m.getBirthDay().getTime()));
			}
		});
	}

	/**
	 * 积分清零(执行不带参数的SQL语句)
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int clearCountNum() throws SQLException {
		String sql = "update member set countNum = 0";
		return this.excuteUpdate(sql);
	}

	/**
	 * 查询所有会员(没有带参数的查询)
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Member> findAll() throws SQLException {
		String sql = "select * from member";
		return this.queryForList(sql, new MemberResultHander());
	}

	/**
	 * 查询积分大于指定值得会员
	 * 
	 * @param minCountNum
	 *            积分下线
	 * @return
	 * @throws SQLException
	 */
	public List<Member> findMemerByCountNum(int minCountNum) throws SQLException {
		String sql = "select * from member where countNum >= ?";
		return this.queryForList(sql, new ObjectsParamsHandler(minCountNum), new MemberResultHander());
	}

	/**
	 * 返回单挑记录的查询
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Member getMemberById(int id) throws SQLException {
		String sql = "select * from member where id = ?";
		return this.query(sql, new ObjectsParamsHandler(id), new MemberResultHander());
	}

	/**
	 * 只需要返回一个结果字段的查询
	 * @return
	 * @throws SQLException
	 */
	public Long getTotalMemeber() throws SQLException {
		String sql = "select count(1) from member";
		return this.queryUniqueResult(sql, Long.class);
	}
}
