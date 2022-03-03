package org.springrain.selenium.service;

import org.springrain.selenium.entity.Validaterule;
import org.springrain.system.service.IBaseSpringrainService;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2017-10-25 13:53:20
 * @see org.springrain.selenium.service.Validaterule
 */
public interface IValidateruleService extends IBaseSpringrainService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Validaterule findValidateruleById(Object id) throws Exception;
	
	
	
}
