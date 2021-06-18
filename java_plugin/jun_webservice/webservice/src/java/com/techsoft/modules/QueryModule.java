package com.techsoft.modules;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techsoft.Cache;
import com.techsoft.DBException;
import com.techsoft.DataConverter;
import com.techsoft.DataExecutor;
import com.techsoft.SQLObject;
import com.techsoft.SQLParam;
import com.techsoft.ServletModule;
import com.techsoft.StringConsts;
import com.techsoft.TechException;
import com.techsoft.cache.CacheFactory;
import com.techsoft.container.DataServer;
import com.techsoft.executor.DataExecutorFactory;
import com.techsoft.sql.SQLObjectManager;

public class QueryModule implements ServletModule {
	private static final Logger Log = LoggerFactory
			.getLogger(QueryModule.class);
	private static QueryModule instance = null;

	private QueryModule() {
	}

	public static QueryModule getInstance() {
		if (instance == null) {
			instance = new QueryModule();
		}

		return instance;
	}

	@Override
	public String getName() {
		return StringConsts.querymodule;
	}

	private List<Map<String, Object>> queryData(SQLObject sqlobj,
			Map<String, Object> paramobj, Map<String, Object> globalParams,
			Integer pageNo, Integer pageSize, DataExecutor executor,
			Connection nativeconn, Map<String, Object> results)
			throws SQLException, IOException, DBException {
		/*
		 * Log.info("sqlid=" + sqlobj.getSqlId() + " params is " +
		 * paramobj.toString());
		 */
		List<Map<String, Object>> result = null;
		Map<String, Object> ignoreCaseParams = new HashMap<String, Object>();
		Map<String, Object> ignoreCaseGlobalParams = new HashMap<String, Object>();
		Integer startnum = 0;
		Integer endnum = 0;
		if ((pageNo != null) && (pageNo > 0)) {
			startnum = (pageNo - 1) * pageSize + 1;
			endnum = pageNo * pageSize;

			paramobj.put("piStartNo", startnum);
			paramobj.put("piEndNo", endnum);
		}

		// 处理参数名称的大小写
		if (paramobj != null) {
			String key = null;
			Iterator<String> strIter = paramobj.keySet().iterator();
			while (strIter.hasNext()) {
				key = strIter.next();
				ignoreCaseParams.put(key.toLowerCase(), paramobj.get(key));
			}
		}

		if (globalParams != null) {
			String key = null;
			Iterator<String> strIter = globalParams.keySet().iterator();
			while (strIter.hasNext()) {
				key = strIter.next();
				ignoreCaseGlobalParams.put(key.toLowerCase(),
						globalParams.get(key));
			}
		}

		SQLParam sqlparam = null;
		SQLParam itersparam = null;
		List<SQLParam> params = new ArrayList<SQLParam>();
		Iterator<SQLParam> sqliters = sqlobj.getSelectParams().iterator();
		while (sqliters.hasNext()) {
			sqlparam = new SQLParam();
			itersparam = sqliters.next();
			sqlparam.setDtype(itersparam.getDtype());
			sqlparam.setDtypename(itersparam.getDtypename());
			sqlparam.setIndex(itersparam.getIndex());
			sqlparam.setIotype(itersparam.getIotype());
			sqlparam.setName(itersparam.getName().toLowerCase());
			// sqlparam.buildPositions();
			params.add(sqlparam);
		}

		Iterator<SQLParam> iter = params.iterator();
		SQLParam param = null;
		while (iter.hasNext()) {
			param = iter.next();
			if (param.getName().equalsIgnoreCase(StringConsts.pageNo)) {
				param.setValue(pageNo);
			} else if (param.getName().equalsIgnoreCase(StringConsts.pageSize)) {
				param.setValue(pageSize);
			} else if (ignoreCaseParams.get(param.getName()) != null) {
				param.setValue(ignoreCaseParams.get(param.getName()));
			} else if (ignoreCaseGlobalParams.get(param.getName()) != null) {
				param.setValue(ignoreCaseGlobalParams.get(param.getName()));
			}
		}

		if (!sqlobj.getIsCursor()) {
			if (pageNo > 0) {
				results.put(StringConsts.recordcount, executor
						.queryRecordCount(sqlobj.getSelectSql(), params,
								nativeconn));
				result = executor.queryDataByPage(sqlobj.getSelectSql(), false,
						pageNo, pageSize, params, nativeconn);
			} else {
				result = executor.queryData(sqlobj.getSelectSql(), false,
						params, nativeconn);
				results.put(StringConsts.recordcount, result.size());
			}
		} else {
			result = executor.queryData(sqlobj.getSelectSql(),
					sqlobj.getIsCursor(), params, nativeconn);

			if ((pageNo != null) && (pageNo > 0)) {
				Iterator<SQLParam> iterparam = params.iterator();
				while (iterparam.hasNext()) {
					param = iterparam.next();
					if (param.getIotype().equalsIgnoreCase(
							SQLParam.ParamType.out.name())) {
						results.put(param.getName().toLowerCase(),
								param.getValue());
					}
				}

				if (results.get("piTotalRecord".toLowerCase()) != null) {
					results.put(StringConsts.recordcount,
							results.get("piTotalRecord".toLowerCase()));
				}
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> readParamfromInputStream(Map<String, Object> in,
			InputStream input, DataConverter dataConverter) {
		Map<String, Object> result = null;
		if (input != null) {
			try {
				Object obj = dataConverter.readFromInputStream(input);
				if (obj != null) {
					result = (Map<String, Object>) dataConverter
							.deserializeObject(obj);
				} else {
					result = new HashMap<String, Object>();
				}
			} catch (Exception e) {
				// 输入流中可能不存在内容，在Ajax调用时
				result = new HashMap<String, Object>();
			}

		} else {
			result = new HashMap<String, Object>();
		}
		String key = null;
		Object value = null;
		Iterator<String> iter = in.keySet().iterator();
		while (iter.hasNext()) {
			key = iter.next();
			value = in.get(key);
			value = dataConverter.deserializeObject(value);
			result.put(key, value);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> queryService(Map<String, Object> inputs,
			Map<String, Object> results) throws TechException {
		String sqlid = null;
		Integer pageno = 0;
		Integer pagesize = 0;
		Map<String, Object> paramobj = null;
		try {
			sqlid = (String) inputs.get(StringConsts.sqlid);
			if ((sqlid == null) || (sqlid.equals(""))) {
				throw new Exception("sqlid不能为空!");
			}
			if ((inputs.get(StringConsts.pageNo) != null)
					&& (!inputs.get(StringConsts.pageNo).equals(""))) {
				pageno = (Integer) inputs.get(StringConsts.pageNo);
			}
			if ((inputs.get(StringConsts.pageSize) != null)
					&& (!inputs.get(StringConsts.pageSize).equals(""))) {
				pagesize = (Integer) inputs.get(StringConsts.pageSize);
			}
			if ((inputs.get(StringConsts.params) != null)
					&& (!inputs.get(StringConsts.params).equals(""))) {
				paramobj = (Map<String, Object>) inputs
						.get(StringConsts.params);
			}
			SQLObject sqlobj = SQLObjectManager.getInstance().getSQLObjectById(
					sqlid);
			if (sqlobj == null) {
				throw new TechException("id值为 [" + sqlid
						+ "]的SQLObject并不存在， 请检查!");
			}

			Connection conn = DataServer.getInstance().getPool()
					.getConnection();
			try {
				Connection nativeconn = DataServer.getInstance().getPool()
						.getNativeConnection(conn);
				try {
					DataExecutor executor = DataExecutorFactory
							.getExecutor(DataServer.getInstance()
									.getProperties().getDbtype());

					List<Map<String, Object>> datas = this.queryData(sqlobj,
							paramobj, inputs, pageno, pagesize, executor,
							nativeconn, results);

					results.put(StringConsts.outdata, datas);
					results.put(StringConsts.outmetadata, sqlobj.getMetaDatas());
					nativeconn.commit();

					// 同步缓存数据
					if (sqlobj.getIsSQLIDCache() || sqlobj.getIsKeyCache()) {
						Cache<String, Object> sqlcache = CacheFactory
								.getCache(sqlobj.getSqlId());
						String keyfield = sqlobj.getKeyField();
						if ((keyfield != null) && (!keyfield.equals(""))) {

							Iterator<Map<String, Object>> iter = datas
									.iterator();
							Map<String, Object> data = null;
							String keyValue = null;
							while (iter.hasNext()) {
								data = iter.next();
								if (data.get(keyfield) != null) {
									keyValue = String.valueOf(data
											.get(keyfield));
								}
								if ((keyValue != null)
										&& (!keyValue.equals(""))) {
									sqlcache.put(keyValue,
											new HashMap<String, Object>(data));
								} else {
									Log.error("SQLID值为: [" + sqlobj.getSqlId()
											+ "] 设置的主键字段不正确, 不能进行缓存, 请检查！");
								}
							}
						} else {
							Log.error("SQLID值为: [" + sqlobj.getSqlId()
									+ "] 设置的主键字段不存在, 不能进行缓存, 请检查！");
						}
					}
				} catch (Exception e) {
					nativeconn.rollback();
					throw e;
				}
			} finally {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			Log.error(e.getClass().getName() + " \n" + e.getMessage());
			throw new TechException(e.getClass().getName() + " \n"
					+ e.getMessage());
		}

		return results;
	}

	@Override
	public void process(Map<String, Object> in, boolean isMultiPart,
			InputStream inputs, OutputStream outs, List<FileItem> list,
			Map<String, Object> result, DataConverter dataConverter)
			throws TechException {
		Map<String, Object> input = this.readParamfromInputStream(in, inputs,
				dataConverter);
		this.queryService(input, result);

	}

}
