package com.jun.common.basiccode;

import java.util.ArrayList;
import java.util.List;

import com.jun.common.GerneralDaoImpl;
import com.jun.util.Hql;
import com.jun.util.Where;

public class BasicCodeDaoImpl extends GerneralDaoImpl<Long,BasicCode> implements IBasicCodeDao{
	/**
	 * 加载所有的基础数据列表
	 * @return
	 */
	public List<BasicCode> findAllBasicCode(){
		return findAll(BasicCode.class);
	}
	
	/**
	 * 基础数据组合查询
	 * @param basicCode 查询参数对象，
	 *                  若传入对象为null则返回null
	 *                  若参数对象属性值全部为null,则查询所有
	 * @return
	 */
	public List<BasicCode> findBasicCodeList(BasicCode basicCode){
		if(null == basicCode){
			return null;
		}
		Hql hql = Hql.start("from BasicCode b")
                      .addWhere("where", Where.get()
                    	  .equal("b.id", basicCode.getId())
                    	  .equal("b.parentId", basicCode.getParentId())
		                  .equal("b.code", basicCode.getCode())
		                  .equal("b.cname", basicCode.getCname())
		                  .equal("b.ename", basicCode.getEname())
		                  .equal("b.value", basicCode.getValue()));
		return findByHql(hql.getSqlstr(), hql.getParams());
	}
}
