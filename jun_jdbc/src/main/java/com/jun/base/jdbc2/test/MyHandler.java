package com.jun.base.jdbc2.test;

import java.sql.ResultSet;
/**
 * 接收rs结果集
 * @author Administrator
 *
 * @param <T>
 */
public interface MyHandler<T> {
	T handler(ResultSet rs);
}
