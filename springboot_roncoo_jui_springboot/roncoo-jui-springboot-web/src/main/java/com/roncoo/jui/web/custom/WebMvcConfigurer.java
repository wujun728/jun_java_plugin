package com.roncoo.jui.web.custom;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.roncoo.jui.common.util.Constants;
import com.roncoo.jui.common.util.base.Base;
import com.roncoo.jui.common.util.jui.Jui;
import com.roncoo.jui.web.bean.vo.SysMenuVO;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;

/**
 * 拦截器
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

	@Bean
	ShiroInterceptor shiroInterceptor() {
		return new ShiroInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(shiroInterceptor()).addPathPatterns("/admin/**");
		super.addInterceptors(registry);
	}
}

/**
 * 权限拦截器
 */
class ShiroInterceptor extends Base implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getServletPath();
		if (!checkUri(uri)) {
			logger.error("没此权限，当前访问路径为：{}", uri);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			Jui bj = new Jui();
			bj.setStatusCode(300);
			bj.setMessage("测试账号，没此权限！");
			out.print(JSONUtil.toJsonStr(bj));
			out.flush();
			out.close();
			return false;
		}
		return true;
	}

	private static Boolean checkUri(String uri) {
		@SuppressWarnings("unchecked")
		List<SysMenuVO> menuVOList = (List<SysMenuVO>) SecurityUtils.getSubject().getSession().getAttribute(Constants.Session.MENU);
		Set<String> menuSet = new HashSet<>();
		listMenu(menuSet, menuVOList);
		if (StringUtils.hasText(uri) && uri.endsWith("/")) {
			uri = uri.substring(0, uri.length() - 1);
		}
		for (String s : menuSet) {
			if (s.equalsIgnoreCase(uri)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param list
	 * @return
	 */
	private static void listMenu(Set<String> menuSet, List<SysMenuVO> menuVOList) {
		if (CollectionUtil.isNotEmpty(menuVOList)) {
			for (SysMenuVO sm : menuVOList) {
				if (StringUtils.hasText(sm.getMenuUrl())) {
					menuSet.add(sm.getMenuUrl());
				}
				listMenu(menuSet, sm.getList());
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
