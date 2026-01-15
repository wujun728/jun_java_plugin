package io.github.wujun728.db.record.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchSql {
	private List<Map<String, Object>> sqlList = new ArrayList<Map<String, Object>>();

	@SuppressWarnings("rawtypes")
	public void addBatch(String sql, List paramList) {
		this.addBatch(sql, paramList.toArray());
	}

	public void addBatch(String sql, Object[] objects) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sql", sql);
		map.put("objects", objects);
		sqlList.add(map);
	}

	public void addBatch(String sql) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sql", sql);
		map.put("objects", new Object[] {});
		sqlList.add(map);
	}

	public List<Map<String, Object>> getSqlList() {
		return sqlList;
	}

	public void clearBatch() {
		this.sqlList.clear();
	}
}