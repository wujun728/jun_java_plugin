package org.springrain.cms.service;

import java.util.List;

import org.springrain.cms.entity.CmsProperty;
import org.springrain.system.service.IBaseSpringrainService;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2016-11-10 11:55:20
 * @see org.springrain.cms.entity.demo.service.CmsProperty
 */
public interface ICmsPropertyService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	CmsProperty findCmsPropertyById(String id) throws Exception;
	
	/**
	 * 添加或修改
	 * 
	 * @param en
	 * @throws Exception
	 */
	void saveupdate(CmsProperty en) throws Exception;
	/**
	 * 根据businessId查询  自定义字段
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	List<CmsProperty> findByBusinessId(String businessId,String state)throws Exception;

	
}
