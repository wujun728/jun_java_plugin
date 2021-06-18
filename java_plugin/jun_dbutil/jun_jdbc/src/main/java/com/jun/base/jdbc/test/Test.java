package com.jun.base.jdbc.test;

import java.sql.*;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		try {
			MemberDbControl mdb = new MemberDbControl();
			Member m = new Member();
			m.setBirthDay(new java.util.Date());
			m.setCountNum(100);
			m.setGender("女");
			m.setUname("王八");
			m.setMno("m111100");
			m.setTelephone("13838388383");
			int rs = mdb.addMember(m);
			System.out.println(rs > 0 ? "创建成功！" : "操作失败！");

			List<Member> members = mdb.findAll();
			if (members.isEmpty()) {
				System.out.println("没有查询到数据");
			} else {
				for (Member member : members) {
					member.print();
				}
			}
			System.out.println();
			// 查询积分大于等于400分的会员
			members = mdb.findMemerByCountNum(400);
			if (members.isEmpty()) {
				System.out.println("没有查询到数据");
			} else {
				for (Member member : members) {
					member.print();
				}
			}
			// 把所有会员的积分归零
			mdb.clearCountNum();

			m = mdb.getMemberById(1);
			System.out.println(m.getUname());
			
			System.out.println("总会员数："+mdb.getTotalMemeber());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
