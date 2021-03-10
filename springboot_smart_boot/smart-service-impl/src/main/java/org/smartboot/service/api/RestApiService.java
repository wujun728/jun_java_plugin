package org.smartboot.service.api;

import java.util.Map;

/**
 * 动态API接口
 * 
 * @author Wujun
 * @version DynApiService.java, v 0.1 2016年2月10日 下午2:20:56 Seer Exp.
 */
public interface RestApiService {
	/**
	 * 执行特定服务的API
	 * 
	 * @param authBean
	 *             鉴权实体
	 * @param param
	 *            入参
	 * @return
	 */
	public RestApiResult<Object> execute(ApiAuthBean authBean, Map<String, String> requestMap);

}
