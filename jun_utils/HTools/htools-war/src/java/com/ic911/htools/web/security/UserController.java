package com.ic911.htools.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ic911.core.commons.Collections3;
import com.ic911.core.security.ShiroDbRealm;
import com.ic911.core.security.ShiroUser;
import com.ic911.core.security.User;
import com.ic911.htools.commons.Constants;
import com.ic911.htools.service.security.AccountService;

@Controller
@RequestMapping("/security/user")
public class UserController {

	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value="/page/{no}")
	public String page(@PathVariable int no,HttpServletRequest request) {
		PageRequest page = new PageRequest(no, 10,Sort.Direction.DESC,"id");
		request.setAttribute("page", accountService.findAll(page));
		return "/security/user/index";
	}

	@RequestMapping(value="/input/{id}")
	public String input(@PathVariable Long id,HttpServletRequest request){
		User user = null;
		if(id!=null&&id>0){
			user = accountService.findOne(id);
		}
		reloadUserAndForward(request, user, null);
		return "/security/user/input";
	}
	
	private String reloadUserAndForward(HttpServletRequest request,User user,Exception e){
		if(user!=null){
			request.setAttribute("user", user);
			request.setAttribute("userRoles", Collections3.convertToList(user.getRoles(), ","));
		}
		request.setAttribute("roles", ShiroDbRealm.getRoleMaps());
		if(e!=null){
			request.setAttribute(Constants.ERROR_MESSAGE, Constants.ERROR_INFO+e.getMessage());
		}
		return "/security/user/input";
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(User user,String[] roles,HttpServletRequest request, RedirectAttributes redirectAttributes){
		if(user!=null){
			try{
				if(roles!=null && roles.length>0){
					user.setRoles(Collections3.convertToString(roles, ","));
				}else{
					throw new RuntimeException("请为该用户选择权限");
				}
				accountService.saveOrUpdate(user);
			}catch(Exception e){
				return reloadUserAndForward(request, user, e);
			}
			redirectAttributes.addFlashAttribute(Constants.SUCCESS_MESSAGE, Constants.SUCCESS_INFO);
		}
		return "redirect:/security/user/page/0";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(@Valid @ModelAttribute("preloadUser") User user,String[] roles,HttpServletRequest request, RedirectAttributes redirectAttributes){
		if(user!=null){
			try{
				if(roles!=null && roles.length>0){
					user.setRoles(Collections3.convertToString(roles, ","));
				}else{
					throw new RuntimeException("请为该用户选择权限");
				}
				accountService.saveOrUpdate(user);
			}catch(Exception e){
				return reloadUserAndForward(request, user, e);
			}
			redirectAttributes.addFlashAttribute(Constants.SUCCESS_MESSAGE, Constants.SUCCESS_INFO);
		}
		return "redirect:/security/user/page/0";
	}
	
	@RequestMapping(value="/delete/{id}")
	public String delete(@PathVariable Long id,HttpServletRequest request, RedirectAttributes redirectAttributes){
		if(id!=null){
			try{
				accountService.delete(id);
			}catch(Exception e){
				redirectAttributes.addFlashAttribute(Constants.ERROR_MESSAGE, Constants.ERROR_INFO+e.getMessage());
				return "redirect:/security/user/page/0";
			}
			redirectAttributes.addFlashAttribute(Constants.SUCCESS_MESSAGE, Constants.SUCCESS_INFO);
		}
		return "redirect:/security/user/page/0";
	}
	
	@RequestMapping(value="/enabled/{id}")
	public String enabled(@PathVariable Long id,HttpServletRequest request, RedirectAttributes redirectAttributes){
		if(id!=null){
			try{
				accountService.enabledUser(id);
			}catch(Exception e){
				redirectAttributes.addFlashAttribute(Constants.ERROR_MESSAGE, Constants.ERROR_INFO+e.getMessage());
				return "redirect:/security/user/page/0";
			}
			redirectAttributes.addFlashAttribute(Constants.SUCCESS_MESSAGE, Constants.SUCCESS_INFO);
		}
		return "redirect:/security/user/page/0";
	}
	
	@RequestMapping(value="/disabled/{id}")
	public String disabled(@PathVariable Long id,HttpServletRequest request, RedirectAttributes redirectAttributes){
		if(id!=null){
			try{
				accountService.disabledUser(id);
			}catch(Exception e){
				redirectAttributes.addFlashAttribute(Constants.ERROR_MESSAGE, Constants.ERROR_INFO+e.getMessage());
				return "redirect:/security/user/page/0";
			}
			redirectAttributes.addFlashAttribute(Constants.SUCCESS_MESSAGE, Constants.SUCCESS_INFO);
		}
		return "redirect:/security/user/page/0";
	}
	
	@ModelAttribute("preloadUser")
	public User getUser(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			User user = accountService.findOne(id);
			user.mergeOpera();
			return user;
		}
		return null;
	}
	
	@RequestMapping(value="/update/password",method=RequestMethod.POST)
	@ResponseBody
	public Object updatePassword(HttpServletRequest request) {
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		if(user.loginName.equalsIgnoreCase("admin")){
			return "-1";
		}
		String newPassword = request.getParameter("newPassword");
		String oldPassword = request.getParameter("oldPassword");
		boolean s = accountService.updatePassword(user.loginName, oldPassword, newPassword);
		return s?'1':'0';
	}

	
}
