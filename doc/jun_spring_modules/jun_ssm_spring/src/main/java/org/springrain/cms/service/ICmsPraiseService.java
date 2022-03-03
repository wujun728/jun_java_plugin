package org.springrain.cms.service;

import org.springrain.cms.entity.CmsPraise;
import org.springrain.system.service.IBaseSpringrainService;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2017-02-15 15:02:34
 * @see org.springrain.cms.base.service.CmsPraise
 */
public interface ICmsPraiseService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	CmsPraise findCmsPraiseById(Object id) throws Exception;

	/**
	 * 根据业务id查询评论数量
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	Integer findPraiseNumByBusinessId(String siteId,String businessId) throws Exception;
	
	/**
	 * 查询用户是否点过赞，点过返回true，没点过返回false
	 * @param userId
	 * @param businessId
	 * @return
	 * @throws Exception 
	 */
	boolean findPraiseIsExist(String siteId,String userId,String businessId) throws Exception;
	
	/**
	 * 删除该条点赞信息
	 * @param userId
	 * @param businessId
	 * @throws Exception 
	 */
	void deletePraise(String siteId,String userId,String businessId) throws Exception;
/**
 * 保存点赞
 * @param cmsPraise
 * @return
 * @throws Exception
 */
	String saveCmsPraise(CmsPraise cmsPraise) throws Exception;
}
