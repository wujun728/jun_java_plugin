package com.jun.base.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 参数处理
 * @author gson
 *
 */
public interface ParamsHandler {

	void doHandle(PreparedStatement stmt) throws SQLException;
}
