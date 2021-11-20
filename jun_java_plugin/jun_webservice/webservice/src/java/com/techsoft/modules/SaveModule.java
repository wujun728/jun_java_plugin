package com.techsoft.modules;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.techsoft.Cache;
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

public class SaveModule implements ServletModule {
	private static final Logger Log = LoggerFactory.getLogger(SaveModule.class);
	private static SaveModule instance = null;

	private SaveModule() {
	}

	public static SaveModule getInstance() {
		if (instance == null) {
			instance = new SaveModule();
		}

		return instance;
	}

	@Override
	public String getName() {
		return StringConsts.savemodule;
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
	public void saveDeleted(List<Object> list, Map<String, Object> result,
			DataExecutor executor, SQLObject sqlobj,
			Map<String, Object> globalParams, Connection conn)
			throws TechException {
		Map<String, Object> map = null;
		Map<String, Object> ignoremap = new HashMap<String, Object>();
		Map<String, Object> ignoreCaseGlobalParams = new HashMap<String, Object>();
		List<SQLParam> params = null;
		Iterator<Object> iter = list.iterator();
		Iterator<SQLParam> iterparam = null;
		Iterator<String> keyiter = null;
		String key = null;
		SQLParam param = null;
		String paramName = null;
		Object paramValue = null;
		if (globalParams != null) {
			Iterator<String> strIter = globalParams.keySet().iterator();
			while (strIter.hasNext()) {
				key = strIter.next();
				ignoreCaseGlobalParams.put(key.toLowerCase(),
						globalParams.get(key));
			}
		}
		List<Map<String, Object>> deleted = new ArrayList<Map<String, Object>>();
		while (iter.hasNext()) {
			ignoremap.clear();
			map = (Map<String, Object>) iter.next();
			keyiter = map.keySet().iterator();
			while (keyiter.hasNext()) {
				key = keyiter.next();
				ignoremap.put(key.toLowerCase(), map.get(key));
			}
			params = new ArrayList<SQLParam>(sqlobj.getDeleteParams());

			iterparam = params.iterator();
			while (iterparam.hasNext()) {
				param = iterparam.next();
				paramName = param.getName().toLowerCase();
				if (ignoremap.get(paramName) != null) {
					paramValue = ignoremap.get(paramName);
					param.setValue(paramValue);
				} else if (ignoreCaseGlobalParams.get(paramName) != null) {
					paramValue = ignoreCaseGlobalParams.get(paramName);
					param.setValue(paramValue);
				}
			}

			Map<String, Object> returnparam = new HashMap<String, Object>(map);
			try {
				Map<String, Object> eResult = executor.saveData(
						sqlobj.getDeleteSql(), params, conn);
				iterparam = params.iterator();
				while (iterparam.hasNext()) {
					param = iterparam.next();
					if (param.getIotype().equalsIgnoreCase(
							SQLParam.ParamType.out.name())
							|| param.getIotype().equalsIgnoreCase(
									SQLParam.ParamType.inout.name())) {
						returnparam.put(param.getName(), param.getValue());
					}
				}
				if (eResult.size() > 0) {
					returnparam.put(StringConsts.rowresult,
							eResult.get(StringConsts.rowresult));
					returnparam.put(StringConsts.rowdesc,
							eResult.get(StringConsts.rowdesc));
				} else {
					returnparam.put(StringConsts.rowresult, "1");
					returnparam.put(StringConsts.rowdesc, "");
				}
			} catch (Exception e) {
				throw new TechException(sqlobj.getSqlId()
						+ " delete Exception: " + e.getMessage());
			} finally {
				if (returnparam != null) {
					deleted.add(returnparam);
				}
			}
		}

		result.put(StringConsts.deletes, deleted);

	}

	@SuppressWarnings("unchecked")
	public void saveUpdated(List<Object> list, Map<String, Object> result,
			DataExecutor executor, SQLObject sqlobj,
			Map<String, Object> globalParams, Connection conn)
			throws TechException {
		Map<String, Object> map = null;
		Map<String, Object> ignoremap = new HashMap<String, Object>();
		Map<String, Object> ignoreCaseGlobalParams = new HashMap<String, Object>();
		List<SQLParam> params = null;
		Iterator<Object> iter = list.iterator();
		Iterator<SQLParam> iterparam = null;
		Iterator<String> keyiter = null;
		String key = null;
		SQLParam param = null;
		String paramName = null;
		Object paramValue = null;
		if (globalParams != null) {
			Iterator<String> strIter = globalParams.keySet().iterator();
			while (strIter.hasNext()) {
				key = strIter.next();
				ignoreCaseGlobalParams.put(key.toLowerCase(),
						globalParams.get(key));
			}
		}
		List<Map<String, Object>> updated = new ArrayList<Map<String, Object>>();
		while (iter.hasNext()) {
			ignoremap.clear();
			map = (Map<String, Object>) iter.next();
			keyiter = map.keySet().iterator();
			while (keyiter.hasNext()) {
				key = keyiter.next();
				ignoremap.put(key.toLowerCase(), map.get(key));
			}
			params = new ArrayList<SQLParam>(sqlobj.getUpdateParams());

			iterparam = params.iterator();
			while (iterparam.hasNext()) {
				param = iterparam.next();
				paramName = param.getName().toLowerCase();
				if (ignoremap.get(paramName) != null) {
					paramValue = ignoremap.get(paramName);
					param.setValue(paramValue);
				} else if (ignoreCaseGlobalParams.get(paramName) != null) {
					paramValue = ignoreCaseGlobalParams.get(paramName);
					param.setValue(paramValue);
				}
			}

			Map<String, Object> returnparam = new HashMap<String, Object>(map);
			try {
				Map<String, Object> eResult = executor.saveData(
						sqlobj.getUpdateSql(), params, conn);
				iterparam = params.iterator();
				while (iterparam.hasNext()) {
					param = iterparam.next();
					if (param.getIotype().equalsIgnoreCase(
							SQLParam.ParamType.out.name())
							|| param.getIotype().equalsIgnoreCase(
									SQLParam.ParamType.out.name())) {

						returnparam.put(param.getName(), param.getValue());
					}
				}
				if (eResult.size() > 0) {
					returnparam.put(StringConsts.rowresult,
							eResult.get(StringConsts.rowresult));
					returnparam.put(StringConsts.rowdesc,
							eResult.get(StringConsts.rowdesc));
				} else {
					returnparam.put(StringConsts.rowresult, "1");
					returnparam.put(StringConsts.rowdesc, "");
				}
			} catch (Exception e) {
				returnparam.put(StringConsts.rowresult, "0");
				returnparam.put(StringConsts.rowdesc, e.getMessage());
				throw new TechException(sqlobj.getSqlId()
						+ " update Exception: " + e.getMessage());
			} finally {
				if (returnparam != null) {
					updated.add(returnparam);
				}
			}
		}
		SQLObjectManager.getInstance().loadSqlCache(sqlobj.getSqlId(),
				ignoremap);
		result.put(StringConsts.updates, updated);
	}

	@SuppressWarnings("unchecked")
	public void saveInserted(List<Object> list, Map<String, Object> result,
			DataExecutor executor, SQLObject sqlobj,
			Map<String, Object> globalParams, Connection conn)
			throws TechException {
		Map<String, Object> map = null;
		Map<String, Object> ignoremap = new HashMap<String, Object>();
		Map<String, Object> ignoreCaseGlobalParams = new HashMap<String, Object>();
		List<SQLParam> params = null;
		Iterator<Object> iter = list.iterator();
		Iterator<SQLParam> iterparam = null;
		Iterator<String> keyiter = null;
		String key = null;
		SQLParam param = null;
		String paramName = null;
		Object paramValue = null;

		if (globalParams != null) {
			Iterator<String> strIter = globalParams.keySet().iterator();
			while (strIter.hasNext()) {
				key = strIter.next();
				ignoreCaseGlobalParams.put(key.toLowerCase(),
						globalParams.get(key));
			}
		}
		List<Map<String, Object>> inserted = new ArrayList<Map<String, Object>>();
		while (iter.hasNext()) {
			ignoremap.clear();
			map = (Map<String, Object>) iter.next();
			keyiter = map.keySet().iterator();
			while (keyiter.hasNext()) {
				key = keyiter.next();
				ignoremap.put(key.toLowerCase(), map.get(key));
			}
			params = new ArrayList<SQLParam>(sqlobj.getInsertParams());

			iterparam = params.iterator();
			while (iterparam.hasNext()) {
				param = iterparam.next();
				paramName = param.getName().toLowerCase();
				if (ignoremap.get(paramName) != null) {
					paramValue = ignoremap.get(paramName);
					param.setValue(paramValue);
				} else if (ignoreCaseGlobalParams.get(paramName) != null) {
					paramValue = ignoreCaseGlobalParams.get(paramName);
					param.setValue(paramValue);
				}
			}

			Map<String, Object> returnparam = new HashMap<String, Object>(map);
			try {
				Map<String, Object> eResult = executor.saveData(
						sqlobj.getInsertSql(), params, conn);
				iterparam = params.iterator();
				while (iterparam.hasNext()) {
					param = iterparam.next();
					if (param.getIotype().equalsIgnoreCase(
							SQLParam.ParamType.out.name())
							|| param.getIotype().equalsIgnoreCase(
									SQLParam.ParamType.out.name())) {
						returnparam.put(param.getName(), param.getValue());
					}
				}
				if (eResult.size() > 0) {
					returnparam.put(StringConsts.rowresult,
							eResult.get(StringConsts.rowresult));
					returnparam.put(StringConsts.rowdesc,
							eResult.get(StringConsts.rowdesc));
				} else {
					returnparam.put(StringConsts.rowresult, "1");
					returnparam.put(StringConsts.rowdesc, "");
				}
			} catch (Exception e) {
				returnparam.put(StringConsts.rowresult, "0");
				returnparam.put(StringConsts.rowdesc, e.getMessage());
				throw new TechException(sqlobj.getSqlId()
						+ " Insert Exception: " + e.getMessage());
			} finally {
				if (returnparam != null) {
					inserted.add(returnparam);
				}
			}
		}
		SQLObjectManager.getInstance().loadSqlCache(sqlobj.getSqlId(),
				ignoremap);
		result.put(StringConsts.inserts, inserted);
	}

	@SuppressWarnings("unchecked")
	public void saveData(Map<String, Object> inputs, Map<String, Object> results)
			throws TechException {
		Map<String, Object> paramobj = null;
		String sqlid = null;
		Map<String, Object> outdata = new HashMap<String, Object>();
		try {
			sqlid = (String) inputs.get(StringConsts.sqlid);
			if ((sqlid == null) || (sqlid.equals(""))) {
				throw new TechException("sqlid 不能为空!");
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
			if (paramobj == null) {
				throw new TechException("id值为 [" + sqlid + "]的保存参数并不存在， 请检查!");
			}

			List<Object> inserted = null;
			List<Object> updated = null;
			List<Object> deleted = null;

			Object jn = paramobj.get(StringConsts.inserts);
			if (jn instanceof Map<?, ?>) {
				inserted = new ArrayList<Object>();
				inserted.add(jn);
			} else if (jn instanceof List<?>) {
				inserted = (List<Object>) paramobj.get(StringConsts.inserts);
			}

			jn = paramobj.get(StringConsts.updates);
			if (jn instanceof Map<?, ?>) {
				updated = new ArrayList<Object>();
				updated.add(jn);
			} else if (jn instanceof List<?>) {
				updated = (List<Object>) paramobj.get(StringConsts.updates);
			}

			jn = paramobj.get(StringConsts.deletes);
			if (jn instanceof JSONObject) {
				deleted = new ArrayList<Object>();
				deleted.add(jn);
			} else if (jn instanceof List<?>) {
				deleted = (List<Object>) paramobj.get(StringConsts.deletes);
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

					if ((inserted != null) && (inserted.size() > 0)) {
						this.saveInserted(inserted, outdata, executor, sqlobj,
								inputs, nativeconn);
					}

					if ((updated != null) && (updated.size() > 0)) {
						this.saveUpdated(updated, outdata, executor, sqlobj,
								inputs, nativeconn);
					}

					if ((deleted != null) && (deleted.size() > 0)) {
						this.saveDeleted(deleted, outdata, executor, sqlobj,
								inputs, nativeconn);
					}

					results.put(StringConsts.outdata, outdata);
					nativeconn.commit();
				} catch (Exception e) {
					nativeconn.rollback();
					throw e;
				}
			} finally {
				conn.close();
				conn = null;
			}

			// 同步缓存数据
			if (sqlobj.getIsSQLIDCache() || sqlobj.getIsKeyCache()) {
				Cache<String, Object> sqlcache = CacheFactory.getCache(sqlobj
						.getSqlId());
				String keyfield = sqlobj.getKeyField();
				Map<String, Object> data = null;
				String keyValue = null;
				if ((keyfield != null) && (!keyfield.equals(""))) {

					if ((inserted != null) && (inserted.size() > 0)) {
						Iterator<Object> iter = inserted.iterator();
						while (iter.hasNext()) {
							data = (Map<String, Object>) iter.next();
							if (data.get(keyfield) != null) {
								keyValue = String.valueOf(data.get(keyfield));
							}
							if ((keyValue != null) && (!keyValue.equals(""))) {
								sqlcache.put(keyValue,
										new HashMap<String, Object>(data));
							} else {
								Log.error("SQLID值为: [" + sqlobj.getSqlId()
										+ "] 设置的主键字段不正确, 不能进行缓存, 请检查！");
							}
						}
					}

					if ((updated != null) && (updated.size() > 0)) {
						Iterator<Object> iter = updated.iterator();
						while (iter.hasNext()) {
							data = (Map<String, Object>) iter.next();
							if (data.get(keyfield) != null) {
								keyValue = String.valueOf(data.get(keyfield));
							}
							if ((keyValue != null) && (!keyValue.equals(""))) {
								sqlcache.put(keyValue,
										new HashMap<String, Object>(data));
							} else {
								Log.error("SQLID值为: [" + sqlobj.getSqlId()
										+ "] 设置的主键字段不正确, 不能进行缓存, 请检查！");
							}
						}
					}

					if ((deleted != null) && (deleted.size() > 0)) {
						Iterator<Object> iter = deleted.iterator();
						while (iter.hasNext()) {
							data = (Map<String, Object>) iter.next();
							if (data.get(keyfield) != null) {
								keyValue = String.valueOf(data.get(keyfield));
							}
							if ((keyValue != null) && (!keyValue.equals(""))) {
								sqlcache.remove(keyValue);
							} else {
								Log.error("SQLID值为: [" + sqlobj.getSqlId()
										+ "] 设置的主键字段不正确, 不能进行缓存, 请检查！");
							}
						}
					}

				} else {
					Log.error("SQLID值为: [" + sqlobj.getSqlId()
							+ "] 设置的主键字段不存在, 不能进行缓存, 请检查！");
				}
			}
		} catch (Exception e) {
			Log.error(e.getClass().getName() + " \n" + e.getMessage());
			throw new TechException(e.getClass().getName() + " \n"
					+ e.getMessage());
		}

	}

	@Override
	public void process(Map<String, Object> in, boolean isMultiPart,
			InputStream inputs, OutputStream outs, List<FileItem> list,
			Map<String, Object> result, DataConverter dataConverter)
			throws TechException {
		Map<String, Object> input = this.readParamfromInputStream(in, inputs,
				dataConverter);
		saveData(input, result);

	}

}
