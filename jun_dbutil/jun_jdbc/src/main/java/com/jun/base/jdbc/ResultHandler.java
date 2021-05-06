package com.jun.base.jdbc;

import java.sql.SQLException;

/**
 * 结果的处理
 * 
 * @author Wujun
 *
 */
public interface ResultHandler<T>{
	
	T doHandle(DataRow rs) throws SQLException;
}
