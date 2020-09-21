/*
 * Project: 基础组件 FileName: UserController.java Company: Chengdu osmp Technology Co.,Ltd version:
 * V1.0
 */
package com.osmp.web.system.httpproxy.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.osmp.web.base.core.SystemConstant;
import com.osmp.web.core.exception.OsmpWebServiceException;
import com.osmp.web.system.httpproxy.service.HttpContextService;
import com.osmp.web.system.httpproxy.util.HttpResponse;
import com.osmp.web.system.httpproxy.util.HttpUtils;
import com.osmp.web.utils.HttpClientUtils;

/**
 * Description: HTTP代理分发
 *
 * @author: wangkaiping
 * @date: 2014年8月22日 上午10:51:30
 */
@Controller
public class HttpController {

	@Autowired
	private HttpContextService httpContextService;

	@RequestMapping("*/**")
	public void forword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String url = HttpClientUtils.getRequestURL(request);// get,post请求，把参数都拼接到url上。
		String managerUrl = httpContextService.dispathByUrl(request, url);
		String tempUrl = "";
		if ("".equals(managerUrl) || SystemConstant.BUNDLES_MANAGER_URL.equals(managerUrl)) {
			/** 请求不满足分发策略，或者服务器没有运行，或者分发的服务器-没有设置访问url。 */
			managerUrl = httpContextService.getOtherUrl(request, url);
			if ("".equals(managerUrl)) {
				throw new OsmpWebServiceException("该请求服务，没有可用服务器!(The request no server is available-_-)");
			}
		}

		tempUrl = managerUrl.substring(0, managerUrl.indexOf("/system/"));
		Map<String, String> headers = HttpClientUtils.getArgsOrHeaders(request, false);
		HttpResponse httpResponse = null;
		if ("GET".equals(request.getMethod())) {
			httpResponse = HttpUtils.sendGet(tempUrl + url, null, headers);
		} else {
			Map<String, String> params = HttpClientUtils.getArgsOrHeaders(request, true);
			httpResponse = HttpUtils.sendPost(tempUrl + url, params, headers);
		}
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		OutputStream ps = response.getOutputStream();
		ps.write(httpResponse.getContent().getBytes("UTF-8"));
		System.out.println(httpResponse.getContent());
		ps.flush();
	}

	/**
	 * 替换请求的IP和端口
	 * 
	 * @param request
	 * @param tempUrl
	 * @return
	 */
	private String getPostUrl(HttpServletRequest request, String tempUrl) {
		String url = "";
		url = request.getRequestURL().toString();
		String contPath = request.getContextPath();
		url = url.replaceAll(".do", "");
		url = url.substring(url.indexOf(contPath), url.length()).replaceAll(contPath, "");
		return tempUrl + url;
	}

}
