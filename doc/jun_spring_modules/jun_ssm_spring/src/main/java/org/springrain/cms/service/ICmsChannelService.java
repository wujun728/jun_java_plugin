package org.springrain.cms.service;

import java.util.List;
import java.util.Map;

import org.springrain.cms.entity.CmsChannel;
import org.springrain.system.service.IBaseSpringrainService;

import freemarker.core.Environment;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2016-11-10 11:55:17
 * @see org.springrain.cms.entity.demo.service.CmsChannel
 */
public interface ICmsChannelService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	CmsChannel findCmsChannelById(String id) throws Exception;

	String saveChannel(CmsChannel cmsChannel) throws Exception;

	Integer updateChannel(CmsChannel cmsChannel) throws Exception;
	
	
	/**
	 * 根据id和pid生成栏目的Comcode
	 * @param id
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	String findChannelNewComcode(String id,String pid,String siteId) throws Exception ;
	
	/**
	 * 查找CmsChannel的树形结构
	 * @return
	 * @throws Exception
	 */
	List<CmsChannel> findTreeChannel(String siteId)throws Exception;
	
	/**
	 * 根据父类Id 查找CmsChannel的树形结构,根为 null
	 * @return
	 * @throws Exception
	 */
	List<CmsChannel> findTreeByPid(String pid,String siteId)throws Exception;
	
	/**
	 * 根据站点ID与主键集合获取数据
	 * @param asList
	 * @param siteId
	 * @param params 从此获取其他附加参数，可以为null
	 * @return
	 * @throws Exception
	 */
	List<CmsChannel> findListByIdsForTag(List<String> asList, String siteId, Map params) throws Exception;

	/**
	 * 根据站点ID与参数获取数据
	 * @param params
	 * @param env
	 * @param siteId
	 * @return
	 */
	List<CmsChannel> findListForTag(Map params, Environment env, String siteId) throws Exception;

	/**
	 * 根据站点ID与主键获取栏目信息
	 * @param id
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	CmsChannel findCmsChannelById(String id, String siteId) throws Exception;
}
