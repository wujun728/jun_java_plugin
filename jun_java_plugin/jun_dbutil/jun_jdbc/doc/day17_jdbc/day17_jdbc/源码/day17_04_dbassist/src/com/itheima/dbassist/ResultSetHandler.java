package com.itheima.dbassist;

import java.sql.ResultSet;

public interface ResultSetHandler {
	/**
	 * 把结果中的数据封装到指定的对象中
	 * @param rs
	 * @return 封装了数据的对象
	 */
	Object handle(ResultSet rs);
}
