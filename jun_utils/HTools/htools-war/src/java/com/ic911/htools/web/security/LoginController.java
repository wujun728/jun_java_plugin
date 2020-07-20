package com.ic911.htools.web.security;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ic911.core.hadoop.HadoopClusterServer;
import com.ic911.htools.commons.Constants;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，

 * 真正登录的POST请求由Filter完成,
 * 
 */
@Controller
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String root(HttpServletRequest request,RedirectAttributes redirectAttributes) {
		return index(request, redirectAttributes);
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String error = request.getParameter("error");
		if(error!=null){
			if(error.equals("403")){
				redirectAttributes.addFlashAttribute(Constants.ERROR_MESSAGE, "该资源无法访问! 原因:您拥有的权限不足!");
				return "redirect:/index";
			}
		}
		request.setAttribute("IS_FIRST_USE", HadoopClusterServer.getMasterConfigs()==null||HadoopClusterServer.getMasterConfigs().isEmpty());
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return "login";
	}

}
