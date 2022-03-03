package com.erp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.struts2.convention.annotation.Action;
//import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.erp.model.CompanyInfo;
import com.erp.service.ICompanyInfoService;
import com.erp.viewModel.GridModel;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.biz.PageUtil;

//import com.opensymphony.xwork2.ModelDriven;

/**
 * 类功能说明 TODO:公司信息处理action 类修改者 修改日期 修改说明
 * <p>
 * Title: CompanyInfoAction.java
 * </p>
 * <p>
 * Description:福产流通科技
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:福产流通科技有限公司
 * </p>
 * 
 * @author Wujun
 * @date 2013-4-28 下午12:51:26
 * @version V1.0
 */
// @Namespace("/companyInfo")
// @Action(value = "companyInfoAction")
@Controller
@RequestMapping("/companyInfoController.do")
public class CompanyInfoController extends BaseController {
	// private static final Logger logger = Logger.getLogger(LoginAction.class);
	private static final long serialVersionUID = -3276708781259160129L;
	private ICompanyInfoService companyInfoService;

	// private CompanyInfo companyInfo;

	// public CompanyInfo getCompanyInfo()
	// {
	// return companyInfo;
	// }
	// public void setCompanyInfo(CompanyInfo companyInfo )
	// {
	// this.companyInfo = companyInfo;
	// }

	@Autowired
	public void setCompanyInfoService(ICompanyInfoService companyInfoService) {
		this.companyInfoService = companyInfoService;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-4-28修改日期 修改内容
	 * 
	 * @Title: persistenceCompanyInfo
	 * @Description:TODO:持久化persistenceCompanyInfo
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=persistenceCompanyInfo")
	public String persistenceCompanyInfo(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		CompanyInfo companyInfo=new CompanyInfo();
		WebUtil.MapToBean(param, companyInfo);
		
		System.out.println(String.valueOf(param.get("inserted")));
		// String sdfString="[{"name":"123123","tel":"123","fax":"123","address":"123","zip":"123","email":"123123@qq.com","contact":"123","description":"123123"}]";
		Map<String, List<CompanyInfo>> map = new HashMap<String, List<CompanyInfo>>();
		map.put("addList", JSON.parseArray(String.valueOf(param.get("inserted")), CompanyInfo.class));
		map.put("updList", JSON.parseArray(String.valueOf(param.get("updated")), CompanyInfo.class));
		map.put("delList", JSON.parseArray(String.valueOf(param.get("deleted")), CompanyInfo.class));
		OutputJson(getMessage(companyInfoService.persistenceCompanyInfo(map)), response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:CompanyInfo弹出框模式新增 Administrator修改者名字 2013-6-9修改日期 修改内容
	 * 
	 * @Title: addCompanyInfo
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=persistenceCompanyInfoDlg")
	public String persistenceCompanyInfoDlg(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<CompanyInfo> list = new ArrayList<CompanyInfo>();
		CompanyInfo  companyInfo=new CompanyInfo();
		Map param = WebUtil.getAllParameters(request);
		WebUtil.MapToBean(param, companyInfo);
		list.add(companyInfo);
		Integer companyId = companyInfo.getCompanyId();
		if (null == companyId ||0 == companyId || "".equals(companyId)) {
			OutputJson(getMessage(companyInfoService.addCompanyInfo(list)), Constants.TEXT_TYPE_PLAIN, response);
		} else {
			OutputJson(getMessage(companyInfoService.updCompanyInfo(list)), Constants.TEXT_TYPE_PLAIN, response);
		}
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-4-28修改日期 修改内容
	 * 
	 * @Title: findAllCompanyInfoList
	 * @Description: TODO:查询所有或符合条件的CompanyInfo
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=findAllCompanyInfoList")
	public String findAllCompanyInfoList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map param = WebUtil.getAllParameters(request);
		if (null != param.get("searchValue") && !"".equals(String.valueOf(param.get("searchValue")))) {
			map.put(String.valueOf(param.get("searchName")), Constants.GET_SQL_LIKE + String.valueOf(param.get("searchValue")) + Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil = new PageUtil();
		WebUtil.MapToBean(param, pageUtil);
		GridModel gridModel = new GridModel();
		gridModel.setRows(companyInfoService.findAllCompanyInfoList(map, pageUtil));
		gridModel.setTotal(companyInfoService.getCount(map, pageUtil));
		OutputJson(gridModel, response);
		return null;
	}

	/**
	 * 函数功能说明 TODO:删除CompanyInfo Administrator修改者名字 2013-6-14修改日期 修改内容
	 * 
	 * @Title: delCompanyInfo
	 * @Description:
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=delCompanyInfo")
	public String delCompanyInfo(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CompanyInfo  companyInfo=new CompanyInfo();
		Map param = WebUtil.getAllParameters(request);
		WebUtil.MapToBean(param, companyInfo);
		OutputJson(getMessage(companyInfoService.delCompanyInfo(companyInfo.getCompanyId())), response);
		return null;
	}
	// public CompanyInfo getModel()
	// {
	// if(null==companyInfo){
	// f
	// }
	// return companyInfo;
	// }
}
