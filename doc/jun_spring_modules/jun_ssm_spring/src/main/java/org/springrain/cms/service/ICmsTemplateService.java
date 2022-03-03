package org.springrain.cms.service;

import org.springrain.cms.entity.CmsTemplate;
import org.springrain.system.service.IBaseSpringrainService;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2016-11-10 11:55:22
 * @see org.springrain.cms.entity.demo.service.CmsTemplate
 */
public interface ICmsTemplateService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	CmsTemplate findCmsTemplateById(String id) throws Exception;
	
	
	
}
