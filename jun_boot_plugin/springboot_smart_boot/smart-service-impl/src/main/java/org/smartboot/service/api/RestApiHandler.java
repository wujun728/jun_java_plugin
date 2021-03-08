package org.smartboot.service.api;

import java.util.Map;

/**
 * API处理Handler
 * 
 * @author Wujun
 * @version RestApiHandler.java, v 0.1 2016年2月10日 下午3:18:54 Seer Exp.
 */
public interface RestApiHandler {
	/**
	 * 执行API业务
	 * 
	 * @param authBean
	 *            鉴权实体
	 * @param params
	 *            参数
	 * @return
	 */
	public Object execute(ApiAuthBean authBean, Map<String, String> params);

	/**
	 * 当前handler处理是否进行事务控制
	 * 
	 * @param authBean
	 * @param params
	 * @return
	 */
	public boolean needTransaction(ApiAuthBean authBean, Map<String, String> params);
}
