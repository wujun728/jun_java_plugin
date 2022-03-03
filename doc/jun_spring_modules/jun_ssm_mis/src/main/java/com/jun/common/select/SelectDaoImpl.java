package com.jun.common.select;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.jun.common.GerneralDaoImpl;
import com.jun.util.Hql;
import com.jun.util.Where;

/**
 * 下拉框数据Dao接口实现类
 * @author Wujun
 *
 */
@SuppressWarnings("unchecked")
public class SelectDaoImpl extends GerneralDaoImpl<Long,SelectBO> implements ISelectDao {
	/**
	 * 下拉框数据查询
	 * @param selectBO
	 * @return
	 */
	public List<SelectBO> getSelectList(SelectBO selectBO) throws Exception{
		if(null == selectBO){
			return null;
		}
		
		StringBuffer start = new StringBuffer("select new com.lxw.oa.common.select.SelectBO(s.");
		start.append(selectBO.getKeyField()).append(",s.");
		start.append(selectBO.getValueField()).append(") from ").append(selectBO.getEntity()).append(" as s");
		Object preValue = null;
		try {
			preValue = new Long(selectBO.getPreValue());
		} catch (Exception e) {
			preValue = selectBO.getPreValue();
		}
		Where where = Where.get()
			.equal("s."+selectBO.getValueField(), selectBO.getValue())
			.equal("s." + selectBO.getPreProperty(), preValue);
		String params = selectBO.getParams();
		List pName = new ArrayList();
		List pValue = new ArrayList();
		if(null != params && params.length() > 0){
			String[] s1 = params.split("&");
			for (int i = 0; i < s1.length; i++) {
				if(s1[i].equals("")){
					continue;
				}
				pName.add(s1[i].split("=")[0]);
				pValue.add(s1[i].split("=")[1]);
			}
		}
		if(pName.size() > 0 && pValue.size() > 0){
			for (int i = 0; i < pName.size(); i++) {
				where.equal("s." + pName.get(i), pValue.get(i));
			}
		}
		Hql hql = Hql.start(start.toString()).addWhere("where", where);
		if(null != selectBO.getOrderBy() && !selectBO.getOrderBy().equals("")){
			hql.orderby("s."+selectBO.getOrderBy());
		}
		List<SelectBO> list =  (List<SelectBO>)findByHql(hql.getSqlstr(), hql.getParams());
		if(null == list || list.size() == 0){
			return null;
		}
		List<SelectBO> selects = new ArrayList<SelectBO>();
		for (SelectBO select : list) {
			String display = BeanUtils.getProperty(select, "display");
			String value = BeanUtils.getProperty(select, "value");
			BeanUtils.copyProperties(select, selectBO);
			select.setDisplay(display);
			select.setValue(value);
			selects.add(select);
		}
		return selects;
	}
	public static void main(String[] args) {
		String params = "name=zhangsan";
		String[] s1 = params.split("&");
		for (int i = 0; i < s1.length; i++) {
			System.out.println(s1[i]);
		}
		System.out.println(s1.length);
	}
}
