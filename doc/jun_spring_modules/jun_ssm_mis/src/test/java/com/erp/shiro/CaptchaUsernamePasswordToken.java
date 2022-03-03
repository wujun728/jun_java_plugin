package com.erp.shiro;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 类功能说明 TODO:密码以及验证码Token 类修改者 修改日期 修改说明
 * <p>
 * Title: CaptchaUsernamePasswordToken.java
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
 * @date 2013-5-29 上午11:31:18
 * @version V1.0
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {
	private static final long serialVersionUID = -3217596468830869181L;
	private String captcha;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public CaptchaUsernamePasswordToken(String username, String password, boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}

	public CaptchaUsernamePasswordToken() {
		super();
	}

  
	
}
