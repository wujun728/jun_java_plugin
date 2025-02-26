/**
 * Copyright (c) 2011-2015, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.wujun728.db.record.dialect;

import io.github.wujun728.db.record.*;
import io.github.wujun728.db.record.builder.TimestampProcessedRecordBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * AnsiSqlDialect. Try to use ANSI SQL dialect with ActiveRecordPlugin.
 * <p>
 * A clever person solves a problem. A wise person avoids it.
 */
public class AnsiSqlDialect extends Dialect {
	
	public AnsiSqlDialect() {
		this.recordBuilder = TimestampProcessedRecordBuilder.me;
	}
	
	public String forTableBuilderDoBuild(String tableName) {
		return "select * from " + tableName + " where 1 = 2";
	}
	
	public String forDbFindById(String tableName, String[] pKeys) {
		tableName = tableName.trim();
		trimPrimaryKeys(pKeys);
		
		StringBuilder sql = new StringBuilder("select * from ").append(tableName).append(" where ");
		for (int i=0; i<pKeys.length; i++) {
			if (i > 0) {
				sql.append(" and ");
			}
			sql.append(pKeys[i]).append(" = ?");
		}
		return sql.toString();
	}
	
	public String forDbDeleteById(String tableName, String[] pKeys) {
		tableName = tableName.trim();
		trimPrimaryKeys(pKeys);
		
		StringBuilder sql = new StringBuilder("delete from ").append(tableName).append(" where ");
		for (int i=0; i<pKeys.length; i++) {
			if (i > 0) {
				sql.append(" and ");
			}
			sql.append(pKeys[i]).append(" = ?");
		}
		return sql.toString();
	}
	
	public void forDbSave(String tableName, String[] pKeys, Record record, StringBuilder sql, List<Object> paras) {
		tableName = tableName.trim();
		trimPrimaryKeys(pKeys);
		
		sql.append("insert into ");
		sql.append(tableName).append('(');
		StringBuilder temp = new StringBuilder();
		temp.append(") values(");
		
		for (Entry<String, Object> e: record.getColumns().entrySet()) {
			if (paras.size() > 0) {
				sql.append(", ");
				temp.append(", ");
			}
			sql.append(e.getKey());
			temp.append('?');
			paras.add(e.getValue());
		}
		sql.append(temp.toString()).append(')');
	}
	
	public void forDbUpdate(String tableName, String[] pKeys, Object[] ids, Record record, StringBuilder sql, List<Object> paras) {
		tableName = tableName.trim();
		trimPrimaryKeys(pKeys);
		
		// Record 新增支持 modifyFlag
//		Set<String> modifyFlag = CPI.getModifyFlag(record);
		Set<String> modifyFlag = record._getModifyFlag();

		sql.append("update ").append(tableName).append(" set ");
		for (Entry<String, Object> e: record.getColumns().entrySet()) {
			String colName = e.getKey();
			if (modifyFlag.contains(colName) && !isPrimaryKey(colName, pKeys)) {
				if (paras.size() > 0) {
					sql.append(", ");
				}
				sql.append(colName).append(" = ? ");
				paras.add(e.getValue());
			}
		}
		sql.append(" where ");
		for (int i=0; i<pKeys.length; i++) {
			if (i > 0) {
				sql.append(" and ");
			}
			sql.append(pKeys[i]).append(" = ?");
			paras.add(ids[i]);
		}
	}
	
	/**
	 * SELECT * FROM subject t1 WHERE (SELECT count(*) FROM subject t2 WHERE t2.id < t1.id AND t2.key = '123') > = 10 AND (SELECT count(*) FROM subject t2 WHERE t2.id < t1.id AND t2.key = '123') < 20 AND t1.key = '123'
	 */
	public String forPaginate(int pageNumber, int pageSize, StringBuilder findSql) {
		throw new ActiveRecordException("Your should not invoke this method because takeOverDbPaginate(...) will take over it.");
	}
	
	public boolean isTakeOverDbPaginate() {
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	public Page<Record> takeOverDbPaginate(Connection conn, int pageNumber, int pageSize, Boolean isGroupBySql, String totalRowSql, StringBuilder findSql, Object... paras) throws SQLException {
		// String totalRowSql = "select count(*) " + replaceOrderBy(sqlExceptSelect);
//		List result = CPI.query(conn, totalRowSql, paras);
		List result = Db.query(totalRowSql, paras);
		int size = result.size();
		if (isGroupBySql == null) {
			isGroupBySql = size > 1;
		}
		
		long totalRow;
		if (isGroupBySql) {
			totalRow = size;
		} else {
			totalRow = (size > 0) ? ((Number)result.get(0)).longValue() : 0;
		}
		if (totalRow == 0) {
			return new Page<Record>(new ArrayList<Record>(0), pageNumber, pageSize, 0, 0);
		}
		
		int totalPage = (int) (totalRow / pageSize);
		if (totalRow % pageSize != 0) {
			totalPage++;
		}
		if (pageNumber > totalPage) {
			return new Page<Record>(new ArrayList<Record>(0), pageNumber, pageSize, totalPage, (int)totalRow);
		}
		
		// StringBuilder sql = new StringBuilder();
		// sql.append(select).append(" ").append(sqlExceptSelect);
		PreparedStatement pst = conn.prepareStatement(findSql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		for (int i=0; i<paras.length; i++) {
			pst.setObject(i + 1, paras[i]);
		}
		ResultSet rs = pst.executeQuery();
		
		// move the cursor to the start
		int offset = pageSize * (pageNumber - 1);
		for (int i=0; i<offset; i++) {
			if (!rs.next()) {
				break;
			}
		}
		
		List<Record> list = buildRecord(rs, pageSize);
		if (rs != null) rs.close();
		if (pst != null) pst.close();
		return new Page<Record>(list, pageNumber, pageSize, totalPage, (int) totalRow);
	}
	
	private List<Record> buildRecord(ResultSet rs, int pageSize) throws SQLException {
		List<Record> result = new ArrayList<Record>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		String[] labelNames = new String[columnCount + 1];
		int[] types = new int[columnCount + 1];
		buildLabelNamesAndTypes(rsmd, labelNames, types);
		for (int k=0; k<pageSize && rs.next(); k++) {
			Record record = new Record();
			Map<String, Object> columns = record.getColumns();
			for (int i=1; i<=columnCount; i++) {
				Object value;
				if (types[i] < Types.BLOB) {
					value = rs.getObject(i);
				} else if (types[i] == Types.CLOB) {
					value = RecordBuilder.me.handleClob(rs.getClob(i));
				} else if (types[i] == Types.NCLOB) {
					value = RecordBuilder.me.handleClob(rs.getNClob(i));
				} else if (types[i] == Types.BLOB) {
					value = RecordBuilder.me.handleBlob(rs.getBlob(i));
				} else {
					value = rs.getObject(i);
				}
				columns.put(labelNames[i], value);
			}
			result.add(record);
		}
		return result;
	}
	
	private void buildLabelNamesAndTypes(ResultSetMetaData rsmd, String[] labelNames, int[] types) throws SQLException {
		for (int i=1; i<labelNames.length; i++) {
			// 备忘：getColumnLabel 获取 sql as 子句指定的名称而非字段真实名称
			labelNames[i] = rsmd.getColumnLabel(i);
			types[i] = rsmd.getColumnType(i);
		}
	}
	
	public boolean isTakeOverModelPaginate() {
		return true;
	}
	

	public void fillStatement(PreparedStatement pst, List<Object> paras) throws SQLException {
		fillStatementHandleDateType(pst, paras);
	}
	
	public void fillStatement(PreparedStatement pst, Object... paras) throws SQLException {
		fillStatementHandleDateType(pst, paras);
	}
}
