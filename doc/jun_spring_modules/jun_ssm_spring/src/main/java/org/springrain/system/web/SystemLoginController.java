package org.springrain.system.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.shiro.FrameAuthenticationToken;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.SecUtils;
import org.springrain.system.entity.User;

@Controller
@RequestMapping(value="/system")
public class SystemLoginController extends BaseController   {
	
	/**
	 * 首页的映射
	 * @param model
	 * @return
	 * @throws Exception
	 */
		@RequestMapping(value = "/")
		public String index() throws Exception {
			return redirect+"/system/index";
		}
		
	
	
	/**
	 * 首页的映射
	 * @param model
	 * @return
	 * @throws Exception
	 */
		@RequestMapping(value = "/index")
		public String index(Model model,HttpSession session,HttpServletRequest request) throws Exception {
			String siteId = request.getParameter("systemSiteId");
			if(StringUtils.isNotBlank(siteId)) {
                model.addAttribute("systemSiteId", siteId);
            }
			return "/system/index";
		}
		
	
		
		
		
		/**
		 * 渲染登录/login的界面展示,如果已经登录,跳转到首页,如果没有登录,就渲染login.html
		 * @param model
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/login",method=RequestMethod.GET)
		public String login(Model model,HttpServletRequest request) throws Exception {
			return getLoginUrl(model,request,null);
		}
		
		private String getLoginUrl(Model model,HttpServletRequest request,String siteId){
			
			//判断用户是否登录
			if(SecurityUtils.getSubject().isAuthenticated()){
				return redirect+"/system/index";
			}
			//默认赋值message,避免freemarker尝试从session取值,造成异常
			model.addAttribute("message", "");
			  String url=request.getParameter("gotourl");
			  if(StringUtils.isNotBlank(url)){
			     model.addAttribute("gotourl", url);
			  }
			return "/system/login";
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
			  
			  
			  String systemSiteId=request.getParameter("systemSiteId");
			  if(StringUtils.isNotBlank(systemSiteId)){
					model.addAttribute("systemSiteId", systemSiteId);
				}
			  
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
				return "/system/login";
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
				return "/system/login";
			} catch (IncorrectCredentialsException e) {
				logger.error(e.getMessage(), e);
				model.addAttribute("message", "密码错误!");
				 if(StringUtils.isNotBlank(gotourl)){
				     model.addAttribute("gotourl", gotourl);
				  }
				return "/system/login";
			} catch (LockedAccountException e) {
				logger.error(e.getMessage(), e);
				model.addAttribute("message", "账号被锁定!");
				 if(StringUtils.isNotBlank(gotourl)){
				     model.addAttribute("gotourl", gotourl);
				  }
				return "/system/login";
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				model.addAttribute("message", "未知错误,请联系管理员.");
				 if(StringUtils.isNotBlank(gotourl)){
				     model.addAttribute("gotourl", gotourl);
				  }
				return "/system/login";
			}
			
			if(StringUtils.isBlank(gotourl)){
				gotourl="/system/index";
			}
			//设置tokenkey
			String springraintoken="system_"+SecUtils.getUUID();
			session.setAttribute(GlobalStatic.tokenKey, springraintoken);
			model.addAttribute(GlobalStatic.tokenKey,springraintoken);
			
			return redirect+gotourl;
		}
		
		/**
		 * 退出
		 * @param request
		 */
		@RequestMapping(value="/logout")
	    public String logout(HttpServletRequest request){
	        Subject subject = SecurityUtils.getSubject();
	        if (subject != null) {           
	            subject.logout();
	        }
	        return redirect+"/system/login";
	    }
}
