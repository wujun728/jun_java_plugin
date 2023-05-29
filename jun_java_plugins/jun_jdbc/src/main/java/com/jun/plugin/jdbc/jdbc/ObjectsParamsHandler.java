package com.jun.plugin.jdbc.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 默认参数处理器
 * 
 * @author Wujun
 *
 */
public class ObjectsParamsHandler implements ParamsHandler {

	private Object[] params;

	public ObjectsParamsHandler(Object... objects) {
		this.params = objects;
	}

	@Override
	public void doHandle(PreparedStatement stmt) throws SQLException {
		for (int i = 1; i <= params.length; i++) {
			stmt.setObject(i, params[i - 1]);
		}
	}

}
