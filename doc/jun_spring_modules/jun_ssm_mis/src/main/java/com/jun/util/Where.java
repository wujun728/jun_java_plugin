package com.jun.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 拼接Where条件
 * @author Wujun
 *
 */
@SuppressWarnings("unchecked")
public class Where {
	private String sqlstr;
	private List params = new ArrayList();
	private Where(){}
	
	public static Where get(){
		return new Where();
	}
	
	public List getParams() {
		return params;
	}

	public void setParams(List params) {
		this.params = params;
	}

	public String getSqlstr() {
		return sqlstr;
	}

	public void setSqlstr(String sqlstr) {
		this.sqlstr = sqlstr;
	}
	
	/**
	 * Like条件
	 */
	public Where like(String str,Object param){
		if(null != param && !param.equals("")){
			if(sqlstr == null){
				sqlstr = str + " like ?";
			}else{
				sqlstr+= " and " + str + "like ?";
			}
			params.add("%" + param + "%");
		}
		return this;
	}
	
	/**
	 * or条件
	 */
	public Where or(String str,Object param){
		if(null != param && !param.equals("")){
			if(sqlstr == null){
				sqlstr = "1=1 and " + str + " = ?";
			}else{
				sqlstr+= " or " + str + "= ?";
			}
			params.add(param);
		}
		return this;
	}
	
	/**
	 * =条件
	 */
	public Where equal(String str,Object param){
		if(null != param && !param.equals("")){
			if(sqlstr == null){
				sqlstr = str + " =?";
			}else{
				sqlstr+= " and " + str + " =?";
			}
			params.add(param);
		}
		return this;
	}
	
	/**
	 * !=条件
	 */
	public Where notEqual(String str,Object param){
		if(null != param && !param.equals("")&& !param.equals("A")){
			if(sqlstr == null){
				sqlstr = str + " !=?";
			}else{
				sqlstr+= " and " + str + " !=?";
			}
			params.add(param);
		}
		return this;
	}
	
	/**
	 * <=条件
	 */
	public Where le(String str,Object param){
		if(null != param && !param.equals("")){
			if(sqlstr == null){
				sqlstr = str + " <=?";
			}else{
				sqlstr+= " and " + str + " <=?";
			}
			params.add(param);
		}
		return this;
	}
	
	/**
	 * >=条件
	 */
	public Where ge(String str,Object param){
		if(null != param && !param.equals("")){
			if(sqlstr == null){
				sqlstr = str + " >=?";
			}else{
				sqlstr+= " and " + str + " >=?";
			}
			params.add(param);
		}
		return this;
	}
	
	/**
	 * <条件
	 */
	public Where lt(String str,Object param){
		if(null != param && !param.equals("")){
			if(sqlstr == null){
				sqlstr = str + " <?";
			}else{
				sqlstr+= " and " + str + " <?";
			}
			params.add(param);
		}
		return this;
	}
	
	/**
	 * >条件
	 */
	public Where gt(String str,Object param){
		if(null != param && !param.equals("")){
			if(sqlstr == null){
				sqlstr = str + " >?";
			}else{
				sqlstr+= " and " + str + " >?";
			}
			params.add(param);
		}
		return this;
	}
	
	/**
	 * in条件
	 */
	public Where in(String str,Object param){
		if(null != param && !param.equals("")){
			if(sqlstr == null){
				sqlstr = str + " in(?)";
			}else{
				sqlstr = sqlstr.substring(0,sqlstr.lastIndexOf("?")+1) + ",?)";
			}
			params.add(param);
		}
		return this;
	}
	
	/**
	 * 添加In条件
	 */
	public  Where addIn(Where where,List params,int n,String strfix,Long m){
		if(null == m || m.intValue()<-1){
			m = new Long(-1l);
		}
		
		if(n>0){
			n--;
			int c = m.intValue();
			c++;
			m=new Long(c);
			where = addIn(where.in(strfix, params.get(m.intValue())),params,n,strfix,m);
		}
		return where;
	}
	
	/**
	 * 添加In条件(重载)
	 */
	public  Where addIn(Where where,Object[] params,int n,String strfix,Long m){
		return addIn(where, Arrays.asList(params), n, strfix, m);
	}
	
	/**
	 * between条件
	 */
	public Where between(String str,Object param1,Object param2){
		if(null != param1 && !param1.equals("") && null != param2 && !param2.equals("")){
			if(sqlstr == null){
				sqlstr = str + " between ? and ?";
			}else{
				sqlstr+= " and " + str + " between ? and ?";
			}
			params.add(param1);
			params.add(param2);
		}
		return this;
	}
	
	
	//测试
	public static void main(String[] args) {
		List list = new ArrayList();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		list.add(8);
		Hql hql = Hql.start("select s from Student s").addWhere("where", Where.get().addIn(Where.get(), list, list.size(), "s.id",new Long(-1l)).between("s.birth", new Date(), new Date()));
		String sql = hql.getSqlstr();
		System.out.println("sql:\n"+ sql);
		for (int i = 0; i < hql.getParams().size(); i++) {
			System.out.println(hql.getParams().get(i));
		}
	}
}
