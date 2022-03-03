package org.springrain.cms.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springrain.cms.entity.CmsSite;
import org.springrain.system.service.IBaseSpringrainService;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2016-11-10 11:55:21
 * @see org.springrain.cms.entity.demo.service.CmsSite
 */
public interface ICmsSiteService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	CmsSite findCmsSiteById(String id) throws Exception;
	/**
	 * 保存站点
	 * @param entity
	 * @return
	 * @throws Exception
	 */
   String  saveCmsSite(CmsSite cmsSite ) throws Exception;
   
   /**
    * 更新站点
    * @param cmsSite
    * @return
    * @throws Exception
    */
   String  updateCmsSite(CmsSite cmsSite) throws Exception;
   
   /**
    * 根据 站点ID 查找 站点的类型
    * @param siteId
    * @return
    * @throws Exception
    */
   Integer findSiteTypeById(String siteId) throws Exception;
	
   /**
    * 根据用户ID查找用户下的站点列表
    * @param userId
	* @return
	* @throws Exception 
   */
	List<CmsSite> findSiteByUserId(String userId) throws Exception;
	
	/**
	 * 上传logo保存临时文件
	 * @param tempFile
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	String saveTmpLogo(MultipartFile tempFile, String siteId) throws IOException;
	/**
	 * 根据用户ID查找该用户下的微信服务号站点列表
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	List<CmsSite> findMpSiteByUserId(String userId) throws Exception;
}
