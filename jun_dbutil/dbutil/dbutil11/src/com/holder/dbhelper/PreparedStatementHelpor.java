package com.holder.dbhelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.holder.Assert;
import com.holder.DBContextHolder;
import com.holder.log.ConsoleLog;

public class PreparedStatementHelpor {

	private static Map<String, SelectTypeHolder> sql_bind_column = new HashMap<String, SelectTypeHolder>();

	@SuppressWarnings("unchecked")
	public PreparedStatementHelpor(String sql, Class cla) {
		Assert.notNull(sql);
		this.sql = sql;
		this.cla = cla;
	}

	private String sql;

	@SuppressWarnings("unchecked")
	private Class cla;

	/**
	 * 将查询出来的结果直接绑定到对象的方法
	 * 
	 * @param binder
	 * @return
	 */
	public List<Object> selectWithType(ParameterBinder binder) {
		ConsoleLog.debug("| PreparedStatementHelpor.selectWithType | SQL | "
				+ sql);
		try {
			PreparedStatement pstmt = DBContextHolder.getContextConnection()
					.prepareStatement(this.sql);
			binder.bindPreparedStatement(pstmt);
			ResultSet rs = pstmt.executeQuery();
			initSelectTypeWithResultSet(rs);
			return ResultSetHelper.bindResultSetToTarget(rs, cla,
					getSelectType());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> select(ParameterBinder binder) {
		ConsoleLog.debug("| PreparedStatementHelpor.select | SQL | " + sql);
		try {
			PreparedStatement pstmt = DBContextHolder.getContextConnection()
					.prepareStatement(this.sql);
			binder.bindPreparedStatement(pstmt);
			ResultSet rs = pstmt.executeQuery();
			initSelectTypeWithResultSet(rs);
			return ResultSetHelper.bindResultToMap(rs, getSelectType());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int updateSql(ParameterBinder binder) {
		ConsoleLog.debug("| PreparedStatementHelpor.updateSql | SQL | " + sql);
		try {
			PreparedStatement pstmt = DBContextHolder.getContextConnection()
					.prepareStatement(this.sql);
			binder.bindPreparedStatement(pstmt);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public void updateBatch(ParameterBinder binder){
		ConsoleLog.debug("| PreparedStatementHelpor.updateSql | SQL | " + sql);
		try {
			PreparedStatement pstmt = DBContextHolder.getContextConnection()
					.prepareStatement(this.sql);
			binder.bindPreparedStatement(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initSelectTypeWithResultSet(ResultSet rs) {
		if (sql_bind_column.containsKey(sql)) {
			// do nothing
		} else {
			SelectTypeHolder holder = new SelectTypeHolder();
			holder.parseResultSetToHolder(rs);
			sql_bind_column.put(sql, holder);
		}
	}

	public SelectTypeHolder getSelectType() {
		return sql_bind_column.get(sql);
	}
}
