package org.springrain.frame.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 *  Log基类,所有的类默认继承此类,可以直接使用 logger 记录日志,例如 logger.error("error");
 * @copyright {@link weicms.net}
 * @author springrain<9iuorg@gmail.com>
 * @version  2013-03-19 11:08:15
 * @see org.springrain.frame.common.BaseLogger
 */
public class BaseLogger {
	public   Logger logger = LoggerFactory.getLogger(getClass());

}
