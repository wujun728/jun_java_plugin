/**   
  * @Title: BusinessException.java 
  * @package com.sysmanage.common.tools.core 
  * @Description: 
  * @author Wujun
  * @date 2011-8-9 上午10:59:18 
  * @version V1.0   
  */

package com.jun.plugin.util.core;

import com.jun.plugin.poi.TgException;

/** 
 * @ClassName: BusinessException 
 * @Description: 业务异常
 * @author Wujun
 * @date 2011-8-9 上午10:59:18 
 *  
 */
public class BusinessException extends TgException{
	private static final long serialVersionUID = 1L;

	public BusinessException() {
		super();
	}

	public BusinessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BusinessException(String arg0) {
		super(arg0);
	}

	public BusinessException(Throwable arg0) {
		super(arg0);
	}
}
