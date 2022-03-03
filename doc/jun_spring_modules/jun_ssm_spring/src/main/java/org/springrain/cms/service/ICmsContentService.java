package org.springrain.cms.service;

import java.util.List;
import java.util.Map;

import org.springrain.cms.entity.CmsContent;
import org.springrain.frame.util.Page;
import org.springrain.system.service.IBaseSpringrainService;

import freemarker.core.Environment;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2016-11-10 11:55:19
 * @see org.springrain.cms.entity.demo.service.CmsContent
 */
public interface ICmsContentService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	CmsContent findCmsContentById(String findCmsContentById,String id) throws Exception;

	String saveContent(CmsContent cmsContent) throws Exception;

	Integer updateCmsContent(CmsContent cmsContent) throws Exception;

	/**
	 * 根据站点id分页查找
	 * @param siteId
	 * @param page
	 * @return
	 * @throws Exception 
	 */
	List<CmsContent> findListBySiteId(String siteId, Page page) throws Exception;

	/**
	 * 根据栏目id分页查找内容列表
	 * @param siteId
	 * @param channelId 
	 * @param page
	 * @return
	 * @throws Exception 
	 */
	List<CmsContent> findContentByChannelId(String siteId, String channelId, Page page) throws Exception;

	void deleteById(String id, String siteId) throws Exception;

	void deleteByIds(List<String> ids, String siteId) throws Exception;

	/**
	 * 获取内容列表
	 * @param params
	 * @param env
	 * @param siteId
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	List<CmsContent> findListForTag(Map params, Environment env, String siteId,String channelId ) throws Exception;

	/**
	 * 根据ID获取内容列表
	 * @param idList
	 * @param orderBy
	 * @return
	 * @throws Exception
	 */
	List<CmsContent> findListByIdsForTag(List<String> idList, int orderBy) throws Exception;

	/**
	 * 查找内容的上一个或下一个
	 * @param id
	 * @param siteId
	 * @param channelId
	 * @param next true：下一个内容；false：下一个内容
	 * @return
	 */
	CmsContent findCmsContentSide(String id, String siteId, String channelId,boolean next) throws Exception ;

	/**
	 * 根据siteId和businessId获取内容列表
	 * @param siteId
	 * @param businessId
	 * @param page
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<CmsContent> findListBySiteIdAndBusinessId(String siteId,String businessId, 
			Page page, Map<String,String> params) throws Exception ;
	
	/**
	 * 获取内容列表
	 * @param cmsContent
	 * @param siteId
	 * @param businessId
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<CmsContent> findCmsContentList(CmsContent cmsContent, String siteId,
			String businessId, Page page) throws Exception;

}
