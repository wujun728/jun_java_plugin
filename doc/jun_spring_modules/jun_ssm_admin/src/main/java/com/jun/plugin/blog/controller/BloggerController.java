package com.jun.plugin.blog.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jun.plugin.blog.entity.Blogger;
import com.jun.plugin.blog.service.BloggerService;
import com.jun.plugin.blog.util.CryptographyUtil;

/**
 * ����Controller��
 * @author Wujun
 *
 */
@Controller
@RequestMapping("/blogger")
public class BloggerController {

	@Resource
	private BloggerService bloggerService;
	
	/**
	 * �û���¼
	 * @param blogger
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(Blogger blogger,HttpServletRequest request){
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(blogger.getUserName(), CryptographyUtil.md5(blogger.getPassword(), "java1234"));
		try{
			subject.login(token); // ��¼��֤
			return "redirect:/admin/main.jsp";
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("blogger", blogger);
			request.setAttribute("errorInfo", "�û������������");
			return "login";
		}
	}
	
	/**
	 * ���Ҳ�����Ϣ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/aboutMe")
	public ModelAndView aboutMe()throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("blogger",bloggerService.find());
		mav.addObject("mainPage", "foreground/blogger/info.jsp");
		mav.addObject("pageTitle","���ڲ���_Java��Դ����ϵͳ");
		mav.setViewName("mainTemp");
		return mav;
	}
}
