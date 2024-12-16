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

package io.github.wujun728.db.record;

import cn.hutool.core.util.StrUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * DbKit
 */
@SuppressWarnings("rawtypes")
public final class DbKit {
	
	public static final int DB_BATCH_COUNT = 1024;

	static final Object[] NULL_PARA_ARRAY = new Object[0];
	public static final String MAIN_CONFIG_NAME = "main";
	public static final int DEFAULT_TRANSACTION_LEVEL = Connection.TRANSACTION_REPEATABLE_READ;

	private DbKit() {}



	static final void close(ResultSet rs, Statement st) throws SQLException {
		if (rs != null) {rs.close();}
		if (st != null) {st.close();}
	}

	static final void close(ResultSet rs) throws SQLException {
		if (rs != null) {rs.close();}
	}

	static final void close(Statement st) throws SQLException {
		if (st != null) {st.close();}
	}

	private static List<Integer> batchModelList(List list, int batchSize, String db, Map<String, BatchInfo> modelUpdateMap) {
		List<Integer> ret = new ArrayList<>(list.size());
		DbPro dbPro;
		if(StrUtil.isBlank(db)){
			dbPro = Db.use();
		}else{
			dbPro=Db.use(db);
		}
		//批量更新
		for (Map.Entry<String, BatchInfo> entry : modelUpdateMap.entrySet()) {
			int[] batch = dbPro.batch(entry.getValue().sql, entry.getKey(), entry.getValue().list, batchSize);
			for (int i : batch) {
				ret.add(i);
			}
		}
		return ret;
	}

	/**
	 * 原有框架方法更新只会取modelList第一个元素的字段状态，批量插入的SQL全部相同，只是参数值不同
	 * 本方法会根据modelList中所有元素，生成不同的SQL和参数，分批分别执行
	 * 自动过滤所有null值属性
	 *
//	 * @param modelList
//	 * @param batchSize
//	 * @param db 使用的数据源，为空时使用默认
//	 * @return
//	 * @see ：https://jfinal.com/share/2629
	 */

	public static List<Integer> batchListSave(String tableName,List<? extends Record> recordList, int batchSize, String db) {
		if (recordList == null || recordList.size() == 0)
			return new ArrayList<>();
		Map<String, BatchInfo> updateMap = new HashMap<>();

		for (Record record : recordList) {
			Map<String, Object> attrs = record.getColumns();
			int index = 0;
			StringBuilder columns = new StringBuilder();
			// the same as the iterator in Dialect.forModelSave() to ensure the order of the attrs
			for (Map.Entry<String, Object> e : attrs.entrySet()) {
				if (index++ > 0) {
					columns.append(',');
				}
				columns.append(e.getKey());
			}
			String cs = columns.toString();
			BatchInfo batchInfo = updateMap.get(cs);
			if (batchInfo == null) {
				batchInfo = new BatchInfo();
				batchInfo.list = new ArrayList<>();
				StringBuilder sql = new StringBuilder();
				Db.use().getDialect().forDbSave(tableName, new String[0], record, sql, new ArrayList<>());
				batchInfo.sql = sql.toString();
				updateMap.put(cs, batchInfo);
			}
			batchInfo.list.add(record);
		}
		return batchModelList(recordList, batchSize,db, updateMap);
	}
	public static List<Integer> batchListSave(String tableName,List<? extends Record> recordList) {
		return batchListSave(tableName,recordList,DB_BATCH_COUNT,null);
	}
	/**
	 * 设置IN查询的sql和参数
	 *
	 * @param paras
	 * @param sb
	 * @param inParas
	 * @return
	 */
	public static StringBuilder buildInSqlPara(List<Object> paras, StringBuilder sb, Object[] inParas) {
		sb.append("(");
		for (int i = 0; i < inParas.length; i++) {
			paras.add(inParas[i]);
			if (i < inParas.length - 1) {
				sb.append("?,");
			} else {
				sb.append("?)");
			}
		}
		return sb;
	}

	public static class BatchInfo {
		public String sql;
		public List list;
	}



}




