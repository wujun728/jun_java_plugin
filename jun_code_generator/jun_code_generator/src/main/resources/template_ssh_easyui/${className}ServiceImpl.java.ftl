package com.erp.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.${Table};
import com.erp.service.${Table}Service;
import com.erp.shiro.ShiroUser;
import com.erp.util.Constants;
import com.erp.util.PageUtil;
/**
* 类功能说明 TODO:公司信息处理service
* 类修改者
* 修改日期
* 修改说明
* @author Wujun
* @version V1.0
*/

@Service("${table}Service")
public class ${Table}ServiceImpl implements ${Table}Service {
	private static final Logger logger = Logger.getLogger(${Table}ServiceImpl.class);
	private PublicDao<${Table}> publicDao;
	
	@Autowired
	public void setPublicDao(PublicDao<${Table}> publicDao) {
		this.publicDao = publicDao;
	}
	/* (非 Javadoc) 
	* <p>Title: persistence${Table}</p> 
	* <p>Description: 持久化${Table}</p> 
	* @param map
	* @return 
	* @see com.erp.service.${Table}Service#persistence${Table}(java.util.Map) 
	*/
	public boolean persistence${Table}(Map<String, List<${Table}>> map) 
	{
		this.add${Table}(map.get("addList"));
		this.upd${Table}(map.get("updList"));
		this.del${Table}(map.get("delList"));
		return true;
	}
	
	public boolean add${Table}(List<${Table}> addlist) 
	{
		
		logger.debug("add${Table}");
		if (addlist!=null&&addlist.size()!=0) {
			ShiroUser users = Constants.getCurrendUser();
			for (${Table} ${table} : addlist) {
				${table}.setCreated(new Date());
				${table}.setLastmod(new Date());
				${table}.setStatus("A");
				if (users!=null)
				{
					${table}.setCreater(users.getUserId());
					${table}.setModifyer(users.getUserId());
				}
				publicDao.save(${table});
			}
		}
		return true;
	}
	
	public boolean upd${Table}(List<${Table}>  updlist) 
	{
		logger.debug("upd${Table}");
		if (updlist!=null&&updlist.size()!=0) {
			ShiroUser users = Constants.getCurrendUser();
			for (${Table} ${table} : updlist) {
				${table}.setLastmod(new Date());
				${table}.setModifyer(users.getUserId());
				publicDao.update(${table});
			}
		}
		return true;
	}
	
	public boolean del${Table}(List<${Table}>  dellist)
	{
		logger.debug("del${Table}");
		if (dellist!=null&&dellist.size()!=0) {
			for (${Table} ${table} : dellist) {
				${table}.setStatus("I");
				${table}.setLastmod(new Date());
				publicDao.deleteToUpdate(${table});
			}
		}
		return true;
	}
	
	public boolean del${Table}(Integer companyId)
	{
		${Table} ${table}=publicDao.get(${Table}.class, companyId);
		${table}.setStatus("I");
		${table}.setLastmod(new Date());
		publicDao.deleteToUpdate(${table});
		return true;
	}
	
	/* (非 Javadoc) 
	* <p>Title: findAll${Table}List</p> 
	* <p>Description: 查询所有${Table}</p> 
	* @param param
	* @param page
	* @param rows
	* @return 
	* @see com.erp.service.${Table}Service#findAll${Table}List(java.util.Map, java.lang.Integer, java.lang.Integer) 
	*/
	public List<${Table}>  findAll${Table}List(Map<String, Object> param,PageUtil pageUtil) 
	{
		String hql="from ${Table} t where t.status='A' ";
		hql+=Constants.getSearchConditionsHQL("t", param);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return publicDao.find(hql, param, pageUtil.getPage(), pageUtil.getRows());
	}
	
	/* (非 Javadoc) 
	* <p>Title: getCount</p> 
	* <p>Description:查询条数 </p> 
	* @param param
	* @return 
	* @see com.erp.service.${Table}Service#getCount(java.util.Map) 
	*/
	public Long getCount(Map<String, Object> param,PageUtil pageUtil)
	{
		String hql="select count(*) from ${Table} t where t.status='A' ";
		hql+=Constants.getSearchConditionsHQL("t", param);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return publicDao.count(hql, param);
	}
	
}
