package org.springrain.weixin.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springrain.cms.util.SiteUtils;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.shiro.ShiroUser;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.system.entity.User;
import org.springrain.system.service.IUserService;
import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.api.IWxMpConfigService;
import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.mp.api.IWxMpService;
import org.springrain.weixin.sdk.mp.api.IWxMpUserService;
import org.springrain.weixin.sdk.mp.bean.result.WxMpOAuth2AccessToken;

@Controller
@RequestMapping(value = "/mp/mpautologin/{siteId}")
public class WxMpAutoLoginController extends BaseController {
	@Resource
	IWxMpService wxMpService;
	@Resource
	IWxMpConfigService wxMpConfigService;
	
	@Resource
	IWxMpUserService wxMpUserService;
	@Resource
	private IUserService userService;

	/**
	 * 跳转到微信认证页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/oauth2")
	public String oauth2(@PathVariable String siteId,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = request.getParameter("url");
		if(StringUtils.isBlank(url)||StringUtils.isBlank(siteId)){
			return null;
		}
		
		
		IWxMpConfig wxmpconfig = wxMpConfigService.findWxMpConfigById(siteId);
		
		
		String _url=SiteUtils.getBaseURL(request)+"/mp/mpautologin/"+siteId+"/callback?url=" + url;
		
		String oauthUrl = wxMpService.oauth2buildAuthorizationUrl(wxmpconfig,_url, WxConsts.OAUTH2_SCOPE_BASE, null);
		
		return redirect + oauthUrl;
	}

	/**
	 * 获取微信用户信息（openid）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/callback")
	public String callback(@PathVariable String siteId,HttpServletRequest request, HttpServletResponse response) throws Exception {
		//WxMpUser wxMpUser = new WxMpUser();
		String url = request.getParameter("url");
		String code = request.getParameter("code");
		
		if(StringUtils.isBlank(url)||StringUtils.isBlank(code)||StringUtils.isBlank(siteId)){
			return null;
		}
		
		
		
		
		IWxMpConfig wxmpconfig = wxMpConfigService.findWxMpConfigById(siteId);
		try {
			// 获取OpenId
			WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(wxmpconfig, code);
//			 wxMpUser=wxMpService.oauth2getUserInfo(wxmpconfig,accessToken,"zh_CN");
//			WxMpUser wxMpUser = wxMpUserService.userInfo(wxmpconfig, accessToken.getOpenId());
			request.getSession().setAttribute("openId", accessToken.getOpenId());
			request.getSession().setAttribute("unionId", accessToken.getUnionId());
			
		} catch (WxErrorException e) {
			logger.error(e.getMessage(),e);
		}
		url = StringUtils.replace(url, "---", "&");
		return redirect + url;
	}
	
	private void autoLogin(HttpServletRequest req, HttpServletResponse rep,
			User user) {
		ShiroUser shiroUser = new ShiroUser(user);
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				shiroUser, GlobalStatic.authorizingRealmName);
		WebSubject.Builder builder = new WebSubject.Builder(req, rep);
		builder.principals(principals);
		builder.authenticated(true);
		WebSubject subject = builder.buildWebSubject();
		ThreadContext.bind(subject);
	}

}
