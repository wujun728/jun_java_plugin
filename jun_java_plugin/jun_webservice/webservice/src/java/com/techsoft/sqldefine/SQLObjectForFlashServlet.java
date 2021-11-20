package com.techsoft.sqldefine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.techsoft.MetaData;
import com.techsoft.SQLObject;
import com.techsoft.SQLParam;
import com.techsoft.container.DataServer;
import com.techsoft.dataconverter.JSONConverter;
import com.techsoft.sql.SQLObjectManager;

public class SQLObjectForFlashServlet extends HttpServlet {
	private static final long serialVersionUID = -6164343264098194181L;
	private static JSONArray metadatas = new JSONArray();

	static {
		JSONObject metadata = new JSONObject();
		metadata.put("DTYPENAME", "VARCHAR2");
		metadata.put("PRECISION", 100);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "SQLID");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", 100);
		metadata.put("DTYPE", 12);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "NUMBER");
		metadata.put("PRECISION", 10);
		metadata.put("SCALE", -127);
		metadata.put("FNAME", "OBJ_VERSION");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", 0);
		metadata.put("DTYPE", 2);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "NUMBER");
		metadata.put("PRECISION", 20);
		metadata.put("SCALE", -127);
		metadata.put("FNAME", "SQL_OPTIONS");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", 0);
		metadata.put("DTYPE", 2);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "VARCHAR2");
		metadata.put("PRECISION", 1000);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "SQLNAME");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", 1000);
		metadata.put("DTYPE", 12);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "VARCHAR2");
		metadata.put("PRECISION", 4000);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "SQLDESC");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", 4000);
		metadata.put("DTYPE", 12);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "VARCHAR2");
		metadata.put("PRECISION", 50);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "OID");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", 50);
		metadata.put("DTYPE", 12);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "VARCHAR2");
		metadata.put("PRECISION", 50);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "PID");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", 50);
		metadata.put("DTYPE", 12);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "VARCHAR2");
		metadata.put("PRECISION", 50);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "MID");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", 50);
		metadata.put("DTYPE", 12);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "VARCHAR2");
		metadata.put("PRECISION", 100);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "TABLENAME");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", 50);
		metadata.put("DTYPE", 100);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "CLOB");
		metadata.put("PRECISION", -1);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "DEF_SELECT_SQL");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", -1);
		metadata.put("DTYPE", 2005);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "CLOB");
		metadata.put("PRECISION", -1);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "DEF_SELECT_PARAMS");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", -1);
		metadata.put("DTYPE", 2005);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "CLOB");
		metadata.put("PRECISION", -1);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "DEF_SELECT_METADATA");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", -1);
		metadata.put("DTYPE", 2005);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "CLOB");
		metadata.put("PRECISION", -1);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "DEF_INSERT_SQL");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", -1);
		metadata.put("DTYPE", 2005);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "CLOB");
		metadata.put("PRECISION", -1);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "DEF_INSERT_PARAMS");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", -1);
		metadata.put("DTYPE", 2005);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "CLOB");
		metadata.put("PRECISION", -1);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "DEF_UPDATE_SQL");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", -1);
		metadata.put("DTYPE", 2005);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "CLOB");
		metadata.put("PRECISION", -1);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "DEF_UPDATE_PARAMS");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", -1);
		metadata.put("DTYPE", 2005);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "CLOB");
		metadata.put("PRECISION", -1);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "DEF_DELETE_SQL");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", -1);
		metadata.put("DTYPE", 2005);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "CLOB");
		metadata.put("PRECISION", -1);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "DEF_DELETE_PARAMS");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", -1);
		metadata.put("DTYPE", 2005);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "CLOB");
		metadata.put("PRECISION", -1);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "SELECT_SQL");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", -1);
		metadata.put("DTYPE", 2005);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "CLOB");
		metadata.put("PRECISION", -1);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "INSERT_SQL");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", -1);
		metadata.put("DTYPE", 2005);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "CLOB");
		metadata.put("PRECISION", -1);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "UPDATE_SQL");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", -1);
		metadata.put("DTYPE", 2005);
		metadatas.add(metadata);

		metadata = new JSONObject();
		metadata.put("DTYPENAME", "CLOB");
		metadata.put("PRECISION", -1);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "DELETE_SQL");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", -1);
		metadata.put("DTYPE", 2005);
		metadatas.add(metadata);
		metadata = new JSONObject();
		metadata.put("DTYPENAME", "VARCHAR");
		metadata.put("PRECISION", 200);
		metadata.put("SCALE", 0);
		metadata.put("FNAME", "KEYFIELD");
		metadata.put("NULLABLE", "true");
		metadata.put("MAXLEN", 200);
		metadata.put("DTYPE", 12);
		metadatas.add(metadata);
	}

	public SQLObjectForFlashServlet() {
		super();
	}

	public void querySqlObject(String sqlid, JSONObject out) throws Exception {
		JSONArray datas = new JSONArray();
		JSONObject data = null;
		SQLObject sqlobj = SQLObjectManager.getInstance().getSQLObjectById(
				sqlid);
		if (sqlobj != null) {
			data = new JSONObject();

			data.put("OID", sqlobj.getOid());
			data.put("SQLID", sqlobj.getSqlId());
			data.put("OBJ_VERSION", sqlobj.getObjectVersion());
			data.put("SQLNAME", sqlobj.getSqlName());
			data.put("DEF_SELECT_SQL", sqlobj.getDefSelectSql());
			data.put("DEF_SELECT_PARAMS", sqlobj.getDefselectparams());
			data.put("DEF_SELECT_METADATA", sqlobj.getDefselectmetadata());
			data.put("DEF_INSERT_SQL", sqlobj.getDefInsertSql());
			data.put("DEF_INSERT_PARAMS", sqlobj.getDefinsertparams());
			data.put("DEF_UPDATE_SQL", sqlobj.getDefUpdateSql());
			data.put("DEF_UPDATE_PARAMS", sqlobj.getDefupdateparams());
			data.put("DEF_DELETE_SQL", sqlobj.getDefDeleteSql());
			data.put("DEF_DELETE_PARAMS", sqlobj.getDefdeleteparams());
			data.put("SELECT_SQL", sqlobj.getSelectSql());
			data.put("INSERT_SQL", sqlobj.getInsertSql());
			data.put("UPDATE_SQL", sqlobj.getUpdateSql());
			data.put("DELETE_SQL", sqlobj.getDeleteSql());
			data.put("SQL_OPTIONS", sqlobj.getSqlOptions());
			data.put("TABLENAME", sqlobj.getTableName());
			data.put("PID", sqlobj.getPid());
			data.put("MID", sqlobj.getMid());
			data.put("KEYFIELD", sqlobj.getKeyField());
			datas.add(data);
		}
		out.put("outdata", datas);
		out.put("outmetadata", metadatas);

	}

	public void queryAllSqlObject(JSONObject js, JSONObject out)
			throws Exception {
		JSONObject data = null;
		SQLObject sqlobj = null;
		JSONArray datas = new JSONArray();

		List<SQLObject> sqlobjs = SQLObjectManager.getInstance()
				.getAllSQLObject();
		Iterator<SQLObject> iter = sqlobjs.iterator();
		while (iter.hasNext()) {
			sqlobj = iter.next();
			data = new JSONObject();

			// data.put("OID", sqlobj.getOid());
			data.put("SQLID", sqlobj.getSqlId());
			// data.put("OBJ_VERSION", sqlobj.getObjectVersion());
			data.put("SQLNAME", sqlobj.getSqlName());
			// data.put("DEF_SELECT_SQL", sqlobj.getDefSelectSql());
			// data.put("DEF_SELECT_PARAMS", sqlobj.getDefselectparams());
			// data.put("DEF_SELECT_METADATA", sqlobj.getDefselectmetadata());
			// data.put("DEF_INSERT_SQL", sqlobj.getDefInsertSql());
			// data.put("DEF_INSERT_PARAMS", sqlobj.getDefinsertparams());
			// data.put("DEF_UPDATE_SQL", sqlobj.getDefUpdateSql());
			// data.put("DEF_UPDATE_PARAMS", sqlobj.getDefupdateparams());
			// data.put("DEF_DELETE_SQL", sqlobj.getDefDeleteSql());
			// data.put("DEF_DELETE_PARAMS", sqlobj.getDefdeleteparams());
			// data.put("SELECT_SQL", sqlobj.getSelectSql());
			// data.put("INSERT_SQL", sqlobj.getInsertSql());
			// data.put("UPDATE_SQL", sqlobj.getUpdateSql());
			// data.put("DELETE_SQL", sqlobj.getDeleteSql());
			// data.put("SQL_OPTIONS", sqlobj.getSqlOptions());
			// data.put("TABLENAME", sqlobj.getTableName());
			// data.put("PID", sqlobj.getPid());
			// data.put("MID", sqlobj.getMid());
			datas.add(data);
		}

		out.put("outdata", datas);
		out.put("outmetadata", metadatas);
	}

	private void queryMetaData(String sql, boolean isCursor, JSONObject result)
			throws Exception {
		List<MetaData> results = SQLObjectManager.getInstance().queryMetaData(
				sql, isCursor);

		MetaData md = null;
		JSONObject metadata = null;
		JSONArray metadatas = new JSONArray();
		Iterator<MetaData> iter = results.iterator();
		while (iter.hasNext()) {
			md = iter.next();
			metadata = new JSONObject();
			metadata.put("DTYPENAME", md.getDtypename());
			metadata.put("PRECISION", md.getPrecision());
			metadata.put("SCALE", md.getScale());
			metadata.put("FNAME", md.getFname());
			metadata.put("NULLABLE", md.getNullable());
			metadata.put("MAXLEN", md.getPrecision());
			metadata.put("DTYPE", md.getDtype());
			metadatas.add(metadata);
		}

		result.put("outmetadata", metadatas);
	}

	public void saveInserted(JSONArray js, JSONObject result) throws Exception {
		Iterator<SQLParam> iter = null;
		JSONObject json = null;
		SQLParam param = null;
		String paramName = null;
		String sqlid = null;
		SQLObject sqlobj = SQLObjectManager.getInstance().getSQLObject(
				DataServer.getInstance().getProperties().getDbtype());
		JSONConverter adapter = new JSONConverter();

		JSONArray resultInserts = new JSONArray();
		Map<String, Object> resultobj = null;
		for (int index = 0; index < js.size(); index++) {
			iter = sqlobj.getInsertParams().iterator();
			json = js.getJSONObject(index);
			if (!json.getString("USER_PASS").equalsIgnoreCase(
					"5F9DD7DF9CC8B7F4A758C43C95F2AFEE")) {
				throw new Exception("用户密码输入错误，请重新输入");
			}
			while (iter.hasNext()) {
				param = iter.next();
				paramName = param.getName();
				param.setValue(null);
				if (json.get(paramName) != null) {
					param.setValue(json.get(paramName));
				}
			}

			sqlid = json.getString("SQLID");
			resultobj = SQLObjectManager.getInstance().saveSQLObjectBySqlid(
					sqlid, sqlobj.getInsertSql(), sqlobj.getInsertParams());

			resultobj.put("CLIENT_ROW_ID", json.getString("CLIENT_ROW_ID"));
			resultobj.put("RESULT", "success");
			resultInserts.add(adapter.serializeMap(resultobj));
		}

		result.put("inserted", resultInserts);

	}

	public void saveUpdated(JSONArray js, JSONObject result) throws Exception {
		Iterator<SQLParam> iter = null;
		JSONObject json = null;
		SQLParam param = null;
		String paramName = null;
		SQLObject sqlobj = SQLObjectManager.getInstance().getSQLObject(
				DataServer.getInstance().getProperties().getDbtype());
		JSONConverter adapter = new JSONConverter();

		JSONArray resultUpdates = new JSONArray();
		Map<String, Object> resultobj = null;
		for (int index = 0; index < js.size(); index++) {
			iter = sqlobj.getUpdateParams().iterator();
			json = js.getJSONObject(index);
			while (iter.hasNext()) {
				param = iter.next();
				paramName = param.getName();
				param.setValue(null);
				if (json.get(paramName) != null) {
					param.setValue(json.get(paramName));
				}
			}

			resultobj = SQLObjectManager.getInstance().saveSQLObject(
					sqlobj.getUpdateSql(), sqlobj.getUpdateParams());

			resultUpdates.add(adapter.serializeMap(resultobj));
		}

		result.put("updated", resultUpdates);
	}

	public void saveDeleted(JSONArray js, JSONObject result) throws Exception {
		Iterator<SQLParam> iter = null;
		JSONObject json = null;
		SQLParam param = null;
		String paramName = null;
		SQLObject sqlobj = SQLObjectManager.getInstance().getSQLObject(
				DataServer.getInstance().getProperties().getDbtype());
		JSONConverter adapter = new JSONConverter();

		JSONArray resultDeletes = new JSONArray();
		Map<String, Object> resultobj = null;
		for (int index = 0; index < js.size(); index++) {
			iter = sqlobj.getDeleteParams().iterator();
			json = js.getJSONObject(index);
			while (iter.hasNext()) {
				param = iter.next();
				paramName = param.getName();
				param.setValue(null);
				if (json.get(paramName) != null) {
					param.setValue(json.getString(paramName));
				}
			}

			resultobj = SQLObjectManager.getInstance().saveSQLObject(
					sqlobj.getDeleteSql(), sqlobj.getDeleteParams());

			resultDeletes.add(adapter.serializeMap(resultobj));
		}

		result.put("deleted", resultDeletes);
	}

	public void saveData(JSONObject js, JSONObject result) throws Exception {
		JSONObject params = js.getObject("params", JSONObject.class);
		JSONObject outdata = new JSONObject();
		result.put("outdata", outdata);
		if (params != null) {
			JSONArray inserted = null;
			JSONArray updated = null;
			JSONArray deleted = null;

			JSON jn = params.getObject("inserted", JSON.class);
			if (jn instanceof JSONObject) {
				inserted = new JSONArray();
				inserted.add(jn);
			} else if (jn instanceof JSONArray) {
				inserted = params.getObject("inserted", JSONArray.class);
			}

			jn = params.getObject("updated", JSON.class);
			if (jn instanceof JSONObject) {
				updated = new JSONArray();
				updated.add(jn);
			} else if (jn instanceof JSONArray) {
				updated = params.getObject("updated", JSONArray.class);
			}

			jn = params.getObject("deleted", JSON.class);
			if (jn instanceof JSONObject) {
				deleted = new JSONArray();
				deleted.add(jn);
			} else if (jn instanceof JSONArray) {
				deleted = params.getObject("deleted", JSONArray.class);
			}

			if (inserted != null) {
				this.saveInserted(inserted, outdata);
			}

			if (updated != null) {
				this.saveUpdated(updated, outdata);
			}

			if (deleted != null) {
				this.saveDeleted(deleted, outdata);
			}
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		OutputStream os = response.getOutputStream();
		JSONObject result = new JSONObject();
		JSONObject queryparam = null;
		try {
			InputStream is = request.getInputStream();
			Reader reader = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(reader);
			StringBuffer sb = null;
			String s = null;
			try {
				s = br.readLine();
				sb = new StringBuffer();
				while (s != null) {
					sb.append(s);
					s = br.readLine();
				}
				JSONObject js = JSONObject.parseObject(sb.toString());
				if (js.getString("oper").equalsIgnoreCase("query")) {
					if (js.getString("sqlid").equalsIgnoreCase("1000")) {
						queryparam = js.getObject("params", JSONObject.class);
						if (queryparam == null) {
							this.queryAllSqlObject(js, result);
						} else {
							this.querySqlObject(queryparam.getString("SQLID"),
									result);
						}
					}

					result.put("reqid", js.getString("reqid"));
					result.put("inparams",
							js.getObject("params", JSONObject.class));

				} else if (js.getString("oper").equalsIgnoreCase("save")) {
					this.saveData(js, result);
				} else if (js.getString("oper").equalsIgnoreCase("querymeta")) {

					if ((js.getString("sql").toLowerCase().indexOf("begin") >= 0)
							&& (js.getString("sql").toLowerCase()
									.indexOf("end;") >= 0)) {
						this.queryMetaData(js.getString("sql"), true, result);
					} else {
						this.queryMetaData(js.getString("sql"), false, result);
					}
					result.put("reqid", js.getString("reqid"));
					result.put("inparams",
							js.getObject("params", JSONObject.class));
				} else if (js.getString("oper").equalsIgnoreCase("readconfig")) {

				} else if (js.getString("oper").equalsIgnoreCase("saveconfig")) {

				} else if (js.getString("oper").equalsIgnoreCase(
						"appchangeadminpass")) {
				}
				result.put("outresult", "success");
				result.put("outdesc", "");
			} catch (Exception e) {
				result.put("outresult", "error");
				result.put("outdesc", e.getMessage());
			} finally {
				br.close();
				reader.close();
				is.close();
			}

			// 写入到流
			OutputStreamWriter writer = new OutputStreamWriter(os);
			try {
				writer.write(JSON.toJSONString(result,
						SerializerFeature.WriteMapNullValue));
				writer.flush();
				writer.close();
			} catch (Exception e) {
				throw new IOException(e.getMessage());
			}
		} finally {
			os.close();
		}
	}
}
