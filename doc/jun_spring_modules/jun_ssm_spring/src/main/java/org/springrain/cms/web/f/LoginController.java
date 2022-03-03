package org.springrain.cms.web.f;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.shiro.FrameAuthenticationToken;
import org.springrain.frame.shiro.ShiroUser;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.SecUtils;
import org.springrain.system.entity.User;

@Controller
public class LoginController extends BaseController  {
	
	
	/**
	 * 首页的映射
	 * @param model
	 * @return
	 * @throws Exception
	 */
		@RequestMapping(value = "/")
		public String index() throws Exception {
				return redirect+"/index";
			
		}
		
	
	
	/**
	 * 首页的映射
	 * @param model
	 * @return
	 * @throws Exception
	 */
		@RequestMapping(value = "/{siteId}/index")
		public String index(Model model,@PathVariable String siteId) throws Exception {
			model.addAttribute("sietId", siteId);
			return "/index"; 
		}
		
		@RequestMapping(value = "/login",method=RequestMethod.GET)
		public String login(Model model,HttpServletRequest request) throws Exception {
			return getLoginUrl(model,request,null);
		}
		
		/**
		 * 渲染登录/login的界面展示,如果已经登录,跳转到首页,如果没有登录,就渲染login.html
		 * @param model
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/{siteId}/login",method=RequestMethod.GET)
		public String siteLogin(Model model,HttpServletRequest request,@PathVariable String siteId) throws Exception {
			model.addAttribute("siteId", siteId);
			return getLoginUrl(model, request, siteId);
		}
		
		
		
		private String getLoginUrl(Model model,HttpServletRequest request,String siteId){
			//判断用户是否登录
			if(SecurityUtils.getSubject().isAuthenticated()){
				return redirect+"/"+siteId+"/index";
			}
			
			
			  String url=request.getParameter("gotourl");
			  if(StringUtils.isNotBlank(url)){
			     model.addAttribute("gotourl", url);
			  }
			
			
			//默认赋值message,避免freemarker尝试从session取值,造成异常
			model.addAttribute("message", "");
			return "/login";
		}
		
		
		
		
		/**
		 * 处理登录提交的方法
		 * @param currUser 自动封装当前登录人的 账号,密码,验证码
		 * @param session
		 * @param model
		 * @param request
		 * @return
		 * @throws Exception
		 */
		
		@RequestMapping(value = "/login",method=RequestMethod.POST)
		public String loginPost(User currUser,HttpSession session,Model model,HttpServletRequest request) throws Exception {
			Subject user = SecurityUtils.getSubject();
			//系统产生的验证码
			  String code = (String) session.getAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM);
			  session.removeAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM);
			  
			  if(StringUtils.isNotBlank(code)){
				  code=code.toLowerCase();
			  }
			  //用户产生的验证码
			String submitCode = WebUtils.getCleanParam(request, GlobalStatic.DEFAULT_CAPTCHA_PARAM);
			  if(StringUtils.isNotBlank(submitCode)){
				  submitCode=submitCode.toLowerCase();
			  }
			  
			  String gotourl=request.getParameter("gotourl");
			
			  
			  //如果验证码不匹配,跳转到登录
			if (StringUtils.isBlank(submitCode) ||StringUtils.isBlank(code)||!code.equals(submitCode)) {
				model.addAttribute("message", "验证码错误!");
				return "/login";
	        }
			//通过账号和密码获取 UsernamePasswordToken token
			FrameAuthenticationToken token = new FrameAuthenticationToken(currUser.getAccount(),currUser.getPassword());
			
			String rememberme=request.getParameter("rememberme");
			if(StringUtils.isNotBlank(rememberme)){
			token.setRememberMe(true);
			}else{
				token.setRememberMe(false);
			}
			
			try {
				//会调用 shiroDbRealm 的认证方法 org.springrain.frame.shiro.ShiroDbRealm.doGetAuthenticationInfo(AuthenticationToken)
				user.login(token);
			} catch (UnknownAccountException e) {
				logger.error(e.getMessage(), e);
				model.addAttribute("message", "账号不存在!");
				if(StringUtils.isNotBlank(gotourl)){
					     model.addAttribute("gotourl", gotourl);
			    }
				return "/login";
			} catch (IncorrectCredentialsException e) {
				logger.error(e.getMessage(), e);
				model.addAttribute("message", "密码错误!");
				if(StringUtils.isNotBlank(gotourl)){
			  	     model.addAttribute("gotourl", gotourl);
		        }
				return "/login";
			} catch (LockedAccountException e) {
				logger.error(e.getMessage(), e);
				model.addAttribute("message", e.getMessage());
				if(StringUtils.isNotBlank(gotourl)){
			  	     model.addAttribute("gotourl", gotourl);
		        }
				return "/login";
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				model.addAttribute("message", "未知错误,请联系管理员.");
				if(StringUtils.isNotBlank(gotourl)){
			  	     model.addAttribute("gotourl", gotourl);
		        }
				return "/login";
			}
		
			//String sessionId = session.getId();
			
			//Cache<Object, Object> cache = shiroCacheManager.getCache(GlobalStatic.authenticationCacheName);
			//cache.put(GlobalStatic.authenticationCacheName+"-"+currUser.getAccount(), sessionId);
			
			/*
			Cache<String, Object> cache = shiroCacheManager.getCache(GlobalStatic.shiroActiveSessionCacheName);
			Serializable oldSessionId = (Serializable) cache.get(currUser.getAccount());
			if(oldSessionId!=null){
				Subject subject=new Subject.Builder().sessionId(oldSessionId).buildSubject();
				subject.logout();
			}
			cache.put(currUser.getAccount(), session.getId());
			*/
			
			if(StringUtils.isBlank(gotourl)){
				gotourl="/index";
			}
			
			//设置tokenkey
			String springraintoken="f_"+SecUtils.getUUID();
			session.setAttribute(GlobalStatic.tokenKey, springraintoken);
			model.addAttribute(GlobalStatic.tokenKey,springraintoken);
			return redirect+gotourl;
		}
		
		
		/**
		 * 退出,防止csrf
		 * @param request
		 */
		@RequestMapping(value="/logout")
	    public String logout(HttpServletRequest request){
	        Subject subject = SecurityUtils.getSubject();
	        if (subject != null) {           
	            subject.logout();
	        }
	        return redirect+"/login";
	    }
		
		
		
		
		
		
		/**
		 * 自动登录,无需账号密码,测试代码,默认注释，根据实际修改
		 * @param model
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		//@RequestMapping(value = "/auto/login")
		public String autologin(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
			ShiroUser shiroUser=new ShiroUser();
			shiroUser.setId("admin");
			shiroUser.setName("admin");
			shiroUser.setAccount("admin");
			 SimplePrincipalCollection principals = new SimplePrincipalCollection(shiroUser, GlobalStatic.authorizingRealmName);
             WebSubject.Builder builder = new WebSubject.Builder(request,response);
             builder.principals(principals);
             builder.authenticated(true);
             WebSubject subject = builder.buildWebSubject();
             ThreadContext.bind(subject);
         	return redirect+"/index";
		}
		
		
		
		
}
