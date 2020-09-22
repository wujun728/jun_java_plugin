package com.jun.plugin.commons.util.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 结果集处理类
 * @author Luxiaolei
 *
 */
public interface RsHandler<T> {
	public T handle(ResultSet rs) throws SQLException;
}
