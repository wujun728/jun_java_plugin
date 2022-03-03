package com.erp.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * 类功能说明 TODO:自定义表单过滤，加入验证码 类修改者 修改日期 修改说明
 * <p>
 * Title: CaptchaFormAuthenticationFilter.java
 * </p>
 * <p>
 * Description:福产流通科技
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:福产流通科技有限公司
 * </p>
 * 
 * @author Wujun
 * @date 2013-5-29 上午11:30:45
 * @version V1.0
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	public String getCaptchaParam() {
		return captchaParam;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	protected CaptchaUsernamePasswordToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String captcha = getCaptcha(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		return new CaptchaUsernamePasswordToken(username, password, rememberMe, host, captcha);
	}
}
