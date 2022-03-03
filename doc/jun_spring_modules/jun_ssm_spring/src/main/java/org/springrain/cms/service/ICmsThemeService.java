package org.springrain.cms.service;

import java.util.List;

import org.springrain.cms.entity.CmsTheme;
import org.springrain.system.service.IBaseSpringrainService;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2016-11-10 11:55:22
 * @see org.springrain.cms.entity.demo.service.CmsTheme
 */
public interface ICmsThemeService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	CmsTheme findCmsThemeById(String id) throws Exception;
	
	/**
	 * 获取所有主题
	 * @return CmsTheme的对象list
	 * @throws Exception 
	 */
	List<CmsTheme> getAllCmsThemes() throws Exception;
	
	/**
	 * 获取可用的主题
	 * @return CmsTheme的对象list
	 * @throws Exception 
	 */
	List<CmsTheme> getTrueCmsThemes() throws Exception;
}
