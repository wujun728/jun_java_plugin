package com.techsoft.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.techsoft.StringConsts;
import com.techsoft.MetaData;
import com.techsoft.SQLObject;
import com.techsoft.SQLParam;
import com.techsoft.TechException;
import com.techsoft.container.DataServer;
import com.techsoft.modules.QueryModule;
import com.techsoft.modules.SaveModule;

public class DataSet {
	private String sqlid;
	private boolean isopen = false;

	private List<Object> inserts = new ArrayList<Object>();
	private List<Object> updates = new ArrayList<Object>();
	private List<Object> deletes = new ArrayList<Object>();
	private List<Map<String, Object>> datas;
	private Map<String, Object> resultParams;

	private Map<String, Object> params;
	private transient SQLObject sqlObj;
	private Integer pageNumber = -1;
	private Integer pageSize = 100;
	private Integer totalrecordcount = -1;

	public DataSet() {
		params = new HashMap<String, Object>();
		resultParams = new HashMap<String, Object>();
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setValue(List<SQLParam> params, SQLParam param) {
		Iterator<SQLParam> iter = params.iterator();
		SQLParam paramValue = null;
		while (iter.hasNext()) {
			paramValue = iter.next();
			if (paramValue.getName().equalsIgnoreCase(param.getName())) {
				paramValue.setValue(param.getValue());
				return;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void open() throws Exception {
		if (isopen) {
			throw new Exception("此数据集已打开， 请先关闭！");
		}

		if ((sqlid == null) || (sqlid.equals(""))) {
			throw new Exception("没的指定SQLID，请先指定数据的SQLID， 然后再打开!");
		}

		Map<String, Object> inputs = new HashMap<String, Object>();
		Map<String, Object> results = new HashMap<String, Object>();
		inputs.put(StringConsts.sqlid, this.sqlid);
		inputs.put(StringConsts.pageNo, this.pageNumber);
		inputs.put(StringConsts.pageSize, this.pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.putAll(this.params);
		inputs.put(StringConsts.params, params);
		try {
			QueryModule.getInstance().queryService(inputs, results);
			if (results.get(StringConsts.ResultType).equals(
					StringConsts.Success)) {
				isopen = true;
				this.totalrecordcount = (Integer) results
						.get(StringConsts.recordcount);
				this.datas = (List<Map<String, Object>>) results
						.get(StringConsts.outdata);
			}
		} catch (Exception e) {
			isopen = false;
		}
	}

	public void close() {
		datas.clear();
		resultParams.clear();
		isopen = false;
	}

	public void saveUpdates() {
		Map<String, Object> inputs = new HashMap<String, Object>();
		Map<String, Object> results = new HashMap<String, Object>();
		inputs.put(StringConsts.sqlid, this.sqlid);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(StringConsts.inserts, this.inserts);
		params.put(StringConsts.updates, this.updates);
		params.put(StringConsts.deletes, this.deletes);
		inputs.put(StringConsts.params, params);

		try {
			SaveModule.getInstance().saveData(inputs, results);
		} catch (TechException e) {
			e.printStackTrace();
		}
	}

	public void insertRecord(Map<String, Object> record) {
		this.inserts.add(record);
	}

	public void updateRecord(Map<String, Object> record) {
		this.updates.add(record);
	}

	public void deleteRecord(Map<String, Object> record) {
		this.deletes.add(record);
	}

	public List<Map<String, Object>> getDatas() {
		return datas;
	}

	public String getSqlid() {
		return sqlid;
	}

	public void setSqlid(String sqlid) {
		this.sqlid = sqlid;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params.clear();
		this.params.putAll(params);
	}

	public List<MetaData> getMetaDatas() throws Exception {
		if ((sqlObj == null)) {
			throw new Exception("数据集还没有open, 取不到对应的元数据");
		}
		return sqlObj.getMetaDatas();
	}

	public int getRecordCount() {
		int result = 0;
		if (this.datas != null) {
			result = this.datas.size();
		}

		return result;
	}

	public Map<String, Object> getResultParams() {
		return resultParams;
	}

	public Integer getTotalrecordcount() {
		return totalrecordcount;
	}

	public boolean isOpen() {
		return isopen;
	}

	public static void main(String args[]) throws Exception {
		new DataServer(new File(
				"E:/toos/apache-tomcat-7.0.4/wtpwebapps/IXWebService")).start();
		DataSet datas = new DataSet();
		datas.setSqlid("100100");
		datas.setPageNumber(1);
		datas.setPageSize(1);
		// data.getParams().put("STATECLASS", "T_ICNI_PUSHCONTENT_PUSHMODE");
		// data.open();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("ORDER_NO", "123456789");
		data.put("state", "1");

		datas.updateRecord(data);
		datas.saveUpdates();
		System.out.println("success");
		// System.out.println(data.getTotalrecordcount());
		// System.out.println(data.getRecordCount());
	}
}
