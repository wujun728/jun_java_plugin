package org.springrain.system.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.system.entity.DicData;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.service.IDicDataService;


/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author weicms.net<Auto generate>
 * @version  2013-07-31 15:56:45
 * @see org.springrain.springrain.service.impl.DicData
 */
@Service("dicDataService")
public class DicDataServiceImpl extends BaseSpringrainServiceImpl implements IDicDataService {

   
    @Override
	public String  save(Object entity ) throws Exception{
	      DicData dicData=(DicData) entity;
	       return (String) super.save(dicData);
	}
    @Override
    @CacheEvict(value = GlobalStatic.cacheKey, key = "'findListDicData_'+#pathtypekey")
	public String  saveorupdateDicData(DicData dicData,String pathtypekey) throws Exception{
    	if(StringUtils.isBlank(pathtypekey)||dicData==null){
    		return null;
    	}
    	String typeKey=dicData.getTypekey();
    	if(!pathtypekey.equals(typeKey)){
    		return null;
    	}
    	
    	
		 return super.saveorupdate(dicData).toString();
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 DicData dicData=(DicData) entity;
	return super.update(dicData);
    }
    @Override
	public DicData findDicDataById(Object id) throws Exception{
	 return super.findById(id,DicData.class);
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
        	if(finder==null){
        	 finder=Finder.getSelectFinder(DicData.class).append(" WHERE 1=1 ");
        	}
        	if(o!=null){
        		getFinderWhereByQueryBean(finder, o);
        	}
        	
        	
			 return super.findListDataByFinder(finder,page,clazz,o);
			}
	

	@Override
	public DicData findDicDataById(String id,String pathtypekey) throws Exception {
		if(StringUtils.isBlank(id)||StringUtils.isBlank(pathtypekey)){
			return null;
		}
		Finder finder=Finder.getSelectFinder(DicData.class).append("  where id=:id and  typekey=:typekey   order by name ");
		finder.setParam("typekey",pathtypekey).setParam("id", id);
		DicData dicData=super.queryForObject(finder, DicData.class);
		return dicData;
	}

	@Override
	//@Cacheable(value = GlobalStatic.cacheKey, key = "'findListDicData_'+#pathtypekey")
	public List<DicData> findListDicData(String pathtypekey,Page page,DicData dicData) throws Exception {
		if(StringUtils.isBlank(pathtypekey)){
			return null;
		}
		Finder finder=Finder.getSelectFinder(DicData.class).append(" WHERE typekey=:typekey ");
		finder.setParam("typekey", pathtypekey);
		return super.queryForList(finder, DicData.class,page);
	}

	@Override
	@CacheEvict(value = GlobalStatic.cacheKey, key = "'findListDicData_'+#pathtypekey")
	public void deleteDicDataById(String id, String pathtypekey)
			throws Exception {
		
		if(StringUtils.isBlank(id)||StringUtils.isBlank(pathtypekey)){
			return;
		}
		
		Finder finder=Finder.getDeleteFinder(DicData.class).append(" where id=:id and  typekey=:typekey  ");
		finder.setParam("typekey",pathtypekey).setParam("id", id);
		super.update(finder);
		
		
		
	}

	@Override
	@CacheEvict(value = GlobalStatic.cacheKey, key = "'findListDicData_'+#pathtypekey")
	public void deleteDicDataByIds(List<String> ids, String pathtypekey)
			throws Exception {
		if(CollectionUtils.isEmpty(ids)||StringUtils.isBlank(pathtypekey)){
			return;
		}
		
		Finder finder=Finder.getDeleteFinder(DicData.class).append(" where id in(:ids) and  typekey=:typekey  ");
		finder.setParam("typekey",pathtypekey).setParam("ids", ids);
		super.update(finder);
		
		
	}
	@Override
	public String findCacheNameById(String id, String pathtypekey) throws Exception {
		List<DicData> findListDicData = findListDicData(pathtypekey,null,null);
		if(CollectionUtils.isEmpty(findListDicData)||StringUtils.isBlank(id)){
			return null;
		}
		for(DicData dicData:findListDicData){
			if(dicData.getId().equals(id)){
				return dicData.getName();
			}
		}
		
		
		return null;
	}
	
	
	

	

}
