package org.smartboot.restful.controller;

import javax.servlet.http.HttpServletRequest;

import org.smartboot.restful.BaseController;
import org.smartboot.service.api.ApiAuthBean;
import org.smartboot.service.api.RestApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 动态API适配接口(Facade), 这里的代码任何时候都不会改变.
 * 
 * @author Wujun
 * @version DynApiController.java, v 0.1 2016年2月10日 下午2:25:45 Seer Exp.
 */
@RestController
@RequestMapping("/api")
public class RestApiController extends BaseController {

	@Autowired
	private RestApiService restApiService;

	/**
	 * 执行api服务
	 * 
	 * @param srvname
	 * @param request
	 * @return
	 */
	@RequestMapping(
		value = "/{srvname}", method = { RequestMethod.POST, RequestMethod.GET },
		produces = { "application/json; charset=UTF-8" })
	public ModelAndView api(@PathVariable("srvname") String srvname, HttpServletRequest request) {
		ApiAuthBean authBean = new ApiAuthBean();
		authBean.setApiVersion(request.getHeader("ver"));
		authBean.setSrvname(srvname);
		return executeAndReturnModel(restApiService, request, authBean);
	}

	/**
	 * 执行API服务下的某个接口
	 * 
	 * @param srvname
	 * @param actName
	 * @param request
	 * @return
	 */
	@RequestMapping(
		value = "/{srvname}/{actname}", method = { RequestMethod.POST, RequestMethod.GET },
		produces = { "application/json; charset=UTF-8" })
	public ModelAndView apiAct(@PathVariable("srvname") String srvname, @PathVariable("actname") String actName,
		HttpServletRequest request) {
		ApiAuthBean authBean = new ApiAuthBean();
		authBean.setApiVersion(request.getHeader("ver"));
		authBean.setSrvname(srvname);
		authBean.setActName(actName);
		return executeAndReturnModel(restApiService, request, authBean);
	}

}
