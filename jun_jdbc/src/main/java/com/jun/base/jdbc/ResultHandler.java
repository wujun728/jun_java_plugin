package com.jun.base.jdbc;

import java.sql.SQLException;

/**
 * 结果的处理
 * 
 * @author gson
 *
 */
public interface ResultHandler<T>{
	
	T doHandle(DataRow rs) throws SQLException;
}
