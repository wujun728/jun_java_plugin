package com.jun.plugin.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BizService {
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getDatagrid(Map param){
		BizDao bd=new BizDao();
		Map map = new HashMap();
		int page=Integer.valueOf(String.valueOf(param.get("page")));
		int rows=Integer.valueOf(String.valueOf(param.get("rows")));
		String method=BizSQL.getMapStr(param, "method");
		switch (method) {
		case BizSQL.TEST:
			map.put("total", bd.getTotal(BizSQL.TEST_COUNT));
			map.put("rows", bd.getRows(BizSQL.TEST_SELECT_LIMIT,page,rows));
			break;
		default:
			map.put("total", 0);
			map.put("rows", new ArrayList());
			break;
		}
		return map;
	}
	
	@SuppressWarnings({ "rawtypes", "static-access" })
	public List queryForList(Map param){
		List list=new ArrayList();
		BizDao bd=new BizDao();
		int page=Integer.valueOf(String.valueOf(param.get("page")==null?0:param.get("page")));
		int rows=Integer.valueOf(String.valueOf(param.get("rows")==null?0:param.get("page")));
		String method=BizSQL.getMapStr(param, "method");
		switch (method) {
		case BizSQL.TEST:
			list=bd.queryForList(BizSQL.TEST_SELECT);
			break;
		default:
			break;
		}
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "static-access" })
	public Map queryForMap(Map param){
		Map map = new HashMap();
		BizDao bd=new BizDao();
		String method=BizSQL.getMapStr(param, "method");
		switch (method) {
		case BizSQL.TEST:
			try {
				map=bd.queryForMap(BizSQL.TEST_SELECT_ONE, BizSQL.getDeleteParam_test(param));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		return map;
	}
	
	
	@SuppressWarnings({ "rawtypes", "static-access" })
	public String queryForString(Map param){
		String val=new String();
		BizDao bd=new BizDao();
		String method=BizSQL.getMapStr(param, "method");
		switch (method) {
		case BizSQL.TEST:
			try {
				val=bd.queryForStr(BizSQL.TEST_SELECT_STR, BizSQL.getDeleteParam_test(param));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		return val;
	}
	
	@SuppressWarnings({ "static-access", "rawtypes" })
	public List getList(Map param){
		List list=new ArrayList();
		BizDao bd=new BizDao();
		String method=BizSQL.getMapStr(param, "method");
		switch (method) {
			case BizSQL.SYS_TABLES:
				list = bd.queryForList(BizSQL.SELECT_SYS_TABLES_COLUMNS_TABLENAME);
				break;
			case BizSQL.SYS_FILEUPLOAD:
				list = bd.queryForList(BizSQL.SELECT_SYS_TABLES_COLUMNS_TABLENAME,new Object[]{"%sys_fileupload%"});
				break;
			default:
				list = bd.queryForList(BizSQL.SELECT_SYS_TABLES_COLUMNS_TABLENAME,new Object[]{"%"+method+"%"});
				break;
		}
		return list;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Integer getTotal(Map param){
		Integer total = 0;
		BizDao bd=new BizDao();
		String method=BizSQL.getMapStr(param, "method");
		switch (method) {
			case BizSQL.SYS_FILEUPLOAD:
				total = bd.getTotal(BizSQL.SYS_FILEUPLOAD_COUNT);
				break;
			case BizSQL.TEST:
				total = bd.getTotal(BizSQL.TEST_COUNT);
				break;	
			default:
				total = 0;
				break;
		}
		return total;
	}
	
	public Boolean delete(Map param){
		Boolean flag=false;
		BizDao bd=new BizDao();
		String method=BizSQL.getMapStr(param, "method");
		switch (method) {
		case BizSQL.TEST:
			flag = bd.execute(BizSQL.TEST_DELETE, BizSQL.getDeleteParam_test(param));
			break;
		default:
			flag = false;
			break;
		}
		return flag;
	}
	
	public Boolean save(Map param){
		Boolean flag=false;
		BizDao bd=new BizDao();
		String method=BizSQL.getMapStr(param, "method");
		switch (method) {
		case BizSQL.TEST:
			flag = bd.execute(BizSQL.TEST_INSERT, BizSQL.getInsertParam_test(param));
			break;
		default:
			flag = false;
			break;
		}
		return flag;
	}
 
	
	public Boolean update(Map param){
		Boolean flag=false;
		BizDao bd=new BizDao();
		String method=BizSQL.getMapStr(param, "method");
		switch (method) {
		case BizSQL.TEST:
			flag = bd.execute(BizSQL.TEST_UPDATE, BizSQL.getUpdateParam_test(param));
			break;
		default:
			flag = false;
			break;
		}
		return flag;
	}
	
	
}
