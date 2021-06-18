package org.smartboot.service.api.handler;

import java.util.Map;

import org.smartboot.service.api.ApiAuthBean;
import org.smartboot.service.api.RestApiHandler;

/**
 * 示例Handler
 * 
 * @author Wujun
 * @version DemoHandler.java, v 0.1 2016年2月10日 下午3:19:07 Seer Exp.
 */
public class DemoHandler implements RestApiHandler {

	@Override
	public Object execute(ApiAuthBean authBean, Map<String, String> params) {
		return "Hello World";
	}

	@Override
	public boolean needTransaction(ApiAuthBean authBean, Map<String, String> params) {
		// TODO Auto-generated method stub
		return false;
	}

}
