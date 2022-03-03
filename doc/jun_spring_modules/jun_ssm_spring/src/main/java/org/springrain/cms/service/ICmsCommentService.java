package org.springrain.cms.service;

import java.io.IOException;
import java.util.List;

import org.springrain.cms.entity.CmsComment;
import org.springrain.frame.util.Page;
import org.springrain.system.service.IBaseSpringrainService;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2016-11-10 11:55:18
 * @see org.springrain.cms.entity.demo.service.CmsComment
 */
public interface ICmsCommentService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	CmsComment findCmsCommentById(String id) throws Exception;

	/**
	 * 根据业务id查询评论数量
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	Integer findCommentsNumByBusinessId(String siteId,String businessId) throws Exception;
	
	List<CmsComment> findCommentListByBusinessId(String siteId,String businessId, Page page) throws Exception;

	/**
	 * 根据微信 文件serverId保存文件到本地
	 * @param bak1
	 * @param businessId 
	 * @param siteId 
	 * @param businessId2 
	 * @return
	 * @throws WxErrorException 
	 * @throws IOException 
	 */
	String saveWechatFile(String serverIds, String siteType, String siteId, String businessId) throws WxErrorException, IOException;

	void deleteCommentById(String id,String siteId,String businessId)throws Exception ;

	void deleteByCommentIds(List<String> ids,String siteId,String businessId)throws Exception ;

	/**
	 * 根据openId获取评论信息
	 * @param openId
	 * @param siteId
	 * @param businessId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	List<CmsComment> findListByOpenId(String openId, String siteId,
			String businessId,Integer type) throws Exception;
	
}
