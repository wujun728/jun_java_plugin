package com.holder.dbhelper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.holder.Assert;

public class SelectTypeHolder {

	private static final String TWICE_INIT = "can not initialize SelectTypeHolder twice with the same sql!";

	private Map<String, Integer> columnTypeHolder = new HashMap<String, Integer>();

	public void parseResultSetToHolder(ResultSet rs) {
		Assert.notNull(rs);
		if (!columnTypeHolder.isEmpty()) {
			throw new RuntimeException(TWICE_INIT);
		}
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int length = metaData.getColumnCount();
			for (int i = 1; i <= length; i++) {
				columnTypeHolder.put(metaData.getColumnName(i).toUpperCase(),
						metaData.getColumnType(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Map<String, Integer> getColumnTypes() {
		return columnTypeHolder;
	}
}
