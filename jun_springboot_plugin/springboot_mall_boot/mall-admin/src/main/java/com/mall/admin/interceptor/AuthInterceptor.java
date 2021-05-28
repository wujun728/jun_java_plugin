/**
 * 
 */
package com.mall.admin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mall.common.constants.Constants;
import com.mall.common.model.JwtToken;
import com.mall.common.utils.ResultCode;
import com.mall.common.utils.StrKit;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户认证拦截器
 * @author Wujun
 * @version 1.0
 * @create_at 2017年5月18日 下午2:41:05
 */
@Component
public class AuthInterceptor extends BaseInterceptor {

	private JwtToken jwtToken = JwtToken.getJwtToken(Constants.SECRET_KEY);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		//获取token
		String token = request.getHeader("token");
		String userId = jwtToken.parseTokenToString(token);
		if (StrKit.isEmpty(token) || StrKit.isEmpty(userId)) { //token不存在 或者token失效
			Map resultMap = new HashMap<>();
			resultMap.put("code", ResultCode.UN_AUTH_ERROR_CODE);
			resultMap.put("message", "用户未认证");
			renderJson(response, resultMap);
			return false;
		}
		request.getSession().setAttribute(Constants.USER_SESSION_ID, Integer.parseInt(userId));
		return true;
	}
}
