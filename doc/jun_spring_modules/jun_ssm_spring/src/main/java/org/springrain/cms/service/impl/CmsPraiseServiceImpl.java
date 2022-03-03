package org.springrain.cms.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springrain.cms.entity.CmsPraise;
import org.springrain.cms.service.ICmsPraiseService;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.system.service.BaseSpringrainServiceImpl;


/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2017-02-15 15:02:34
 * @see org.springrain.cms.base.service.impl.CmsPraise
 */
@Service("cmsPraiseService")
public class CmsPraiseServiceImpl extends BaseSpringrainServiceImpl implements ICmsPraiseService {

   
    @Override
	public String  saveCmsPraise( CmsPraise cmsPraise ) throws Exception{
    	Object saveId = super.save(cmsPraise);
    	
    	String userId=cmsPraise.getUserId();
    	String siteId=cmsPraise.getSiteId();
    	String businessId=cmsPraise.getBusinessId();
    	
    	
    	String cacheKey1="cmsPraiseService_findPraiseIsExist_"+userId+"_"+businessId;
		String cacheKey2="cmsPraiseService_findPraiseNumByBusinessId_"+siteId+"_"+businessId;
		super.evictByKey(siteId, cacheKey1);
		super.evictByKey(siteId, cacheKey2);
	       return saveId.toString();
	}
    
    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	CmsPraise cmsPraise=(CmsPraise) entity;
    	String userId = SessionUser.getUserId();
    	String businessId = cmsPraise.getBusinessId();
    	String siteId=cmsPraise.getSiteId();
    	boolean exist = findPraiseIsExist(siteId,userId,businessId);
    	if(exist){//存在，执行删除
    		deletePraise(siteId,userId, businessId);
    	}else{//不存在，新增
    		return saveCmsPraise(cmsPraise);
    	}
	    return "";
	}
	
	
    @Override
	public CmsPraise findCmsPraiseById(Object id) throws Exception{
	 return super.findById(id,CmsPraise.class);
	}
	
/**
 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
 * @param finder
 * @param page
 * @param clazz
 * @param o
 * @return
 * @throws Exception
 */
        @Override
    public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz,
			Object o) throws Exception{
			 return super.findListDataByFinder(finder,page,clazz,o);
			}
	/**
	 * 根据查询列表的宏,导出Excel
	 * @param finder 为空则只查询 clazz表
	 * @param ftlurl 类表的模版宏
	 * @param page 分页对象
	 * @param clazz 要查询的对象
	 * @param o  querybean
	 * @return
	 * @throws Exception
	 */
		@Override
	public <T> File findDataExportExcel(Finder finder,String ftlurl, Page page,
			Class<T> clazz, Object o)
			throws Exception {
			 return super.findDataExportExcel(finder,ftlurl,page,clazz,o);
		}

	@Override
	public Integer findPraiseNumByBusinessId(String siteId,String businessId) throws Exception {
		
		String cacheKey="cmsPraiseService_findPraiseNumByBusinessId_"+siteId+"_"+businessId;
		
		Integer countNum = super.getByCache(siteId, cacheKey, Integer.class);
		if(countNum!=null){
			return countNum;
		}
		
		
		Finder finder = new Finder("SELECT COUNT(*)  FROM ")
				.append(Finder.getTableName(CmsPraise.class))
				.append(" WHERE businessId=:businessId");
		finder.setParam("businessId", businessId);
		 countNum = super.queryForObject(finder,Integer.class);
	    if(countNum==null){
	    	countNum=0;
	    }
	    
	    super.putByCache(siteId, cacheKey, countNum);
	    
	    
	    return countNum;
	}

	@Override
	public boolean findPraiseIsExist(String siteId,String userId, String businessId) throws Exception {
		
		String cacheKey="cmsPraiseService_findPraiseIsExist_"+userId+"_"+businessId;
		
		String id = super.getByCache(siteId, cacheKey, String.class);
		if(id!=null){
			return true;
		}
		
		
		
		Finder finder = Finder.getSelectFinder(CmsPraise.class,"id").append(" where userId=:userId and businessId=:businessId");
		finder.setParam("userId", userId).setParam("businessId", businessId);
		 id = super.queryForObject(finder,String.class);
		if(StringUtils.isEmpty(id)){
			return false;
		}
		
		super.putByCache(siteId, cacheKey, id);
		
		return true;
	}

	@Override
	public void deletePraise(String siteId,String userId, String businessId) throws Exception {
		
		String cacheKey1="cmsPraiseService_findPraiseIsExist_"+userId+"_"+businessId;
		String cacheKey2="cmsPraiseService_findPraiseNumByBusinessId_"+siteId+"_"+businessId;
		super.evictByKey(siteId, cacheKey1);
		super.evictByKey(siteId, cacheKey2);
		
		Finder finder = Finder.getDeleteFinder(CmsPraise.class).append(" where userId=:userId and businessId=:businessId");
		finder.setParam("userId", userId).setParam("businessId", businessId);
		super.update(finder);
	}

}
