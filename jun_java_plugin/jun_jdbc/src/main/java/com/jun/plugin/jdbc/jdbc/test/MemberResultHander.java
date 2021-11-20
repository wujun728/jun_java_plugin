package com.jun.plugin.jdbc.jdbc.test;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.apache.commons.beanutils.BeanUtils;

import com.jun.plugin.jdbc.jdbc.DataRow;
import com.jun.plugin.jdbc.jdbc.ResultHandler;

/**
 * 会员参数处理器
 * @author Wujun
 *
 */
public class MemberResultHander implements ResultHandler<Member> {

	@Override
	public Member doHandle(DataRow rs) throws SQLException {
		Member m = new Member();
		try {
			BeanUtils.copyProperties(m, rs);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return m;
	}
}
