/**   
 * @Title: DataAccessException.java 
 * @package com.sysmanage.common.tools.core 
 * @Description: 
 * @author Wujun
 * @date 2011-8-9 上午11:00:31 
 * @version V1.0   
 */

package com.jun.plugin.util.core;

import com.jun.plugin.poi.TgException;

/**
 * @ClassName: DataAccessException
 * @Description: 数据访问异常
 * @author Wujun
 * @date 2011-8-9 上午11:00:31
 * 
 */
public class DataAccessException extends TgException {
	private static final long serialVersionUID = 1L;

	public DataAccessException() {
		super();
	}

	public DataAccessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DataAccessException(String arg0) {
		super(arg0);
	}

	public DataAccessException(Throwable arg0) {
		super(arg0);
	}
}
