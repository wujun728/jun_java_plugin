package com.erp.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.model.Bug;
import com.erp.service.IBugService;
import com.erp.viewModel.GridModel;
//import com.jun.demo.rss.RssParse;
//import com.opensymphony.xwork2.ModelDriven;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.biz.PageUtil;

//@Namespace("/bug")
//@Action(value = "bugAction")
@Controller
@RequestMapping("/rssController.do")
public class RssController extends BaseController {
	private static final long serialVersionUID = -3055754336964775139L;
	private IBugService bugService;
//	private File filedata;
//	private String filedataFileName;
//	private String filedataContentType;

	 
 
	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-22修改日期 修改内容
	 * 
	 * @Title: findBugList
	 * @Description: TODO:查询所有bug
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(params = "method=getFeed")
	public String findBugList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageUtil pageUtil = new PageUtil();
		Map<String, Object> map = new HashMap<String, Object>();
		Map param = WebUtil.getAllParameters(request);
		List li= new ArrayList<>();
		GridModel gridModel = new GridModel();
		gridModel.setRows(li);
		gridModel.setTotal(Long.valueOf(li.size()));
		OutputJson(gridModel, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-22修改日期 修改内容
	 * 
	 * @Title: addBug
	 * @Description: TODO:增加bug
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=addBug")
	public String addBug(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Bug bug = new Bug();
		WebUtil.copyBean3(param, bug);
		OutputJson(getMessage(bugService.addBug(bug)), Constants.TEXT_TYPE_PLAIN, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-22修改日期 修改内容
	 * 
	 * @Title: delBug
	 * @Description: TODO:删除bug
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=delBug")
	public String delBug(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Bug bug = new Bug();
		Map param = WebUtil.getAllParameters(request);
		WebUtil.copyBean3(param, bug);
		OutputJson(getMessage(bugService.delBug(bug.getBugId())), response);
		return null;
	}
 
}
