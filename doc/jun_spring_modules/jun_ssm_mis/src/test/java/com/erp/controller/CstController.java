package com.erp.controller;

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
import com.erp.model.Customer;
import com.erp.model.CustomerContact;
import com.erp.service.ICstService;
import com.erp.viewModel.GridModel;
//import com.opensymphony.xwork2.ModelDriven;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.biz.PageUtil;

//@Namespace("/cst")
//@Action(value = "cstAction")
@Controller
@RequestMapping("/cstController.do")
public class CstController extends BaseController {
	private static final long serialVersionUID = -1829373389433829721L;
	private ICstService cstService;

	// private Customer customer;
	@Autowired
	public void setCstService(ICstService cstService) {
		this.cstService = cstService;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-24修改日期 修改内容
	 * 
	 * @Title: findCustomerList @Description:
	 * TODO:查询所有客户 @param @return @param @throws Exception 设定文件 @return String
	 * 返回类型 @throws
	 */
	@RequestMapping(params = "method=findCustomerList")
	public String findCustomerList(Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map param = WebUtil.getAllParameters(request);
		if (null != param.get("searchValue") && !"".equals(String.valueOf(param.get("searchValue")))) {
			map.put(String.valueOf(param.get("searchName")),
					Constants.GET_SQL_LIKE + String.valueOf(param.get("searchValue")) + Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil = new PageUtil();
		WebUtil.MapToBean(param, pageUtil);

		GridModel gridModel = new GridModel();
		gridModel.setRows(cstService.findCustomerList(map, pageUtil));
		gridModel.setTotal(cstService.getCount(map, pageUtil));
		OutputJson(gridModel, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-7-9修改日期 修改内容
	 * 
	 * @Title: findCustomerListNoPage @Description:
	 * TODO:查询所有客户不分页 @param @return @param @throws Exception 设定文件 @return
	 * String 返回类型 @throws
	 */
	@RequestMapping(params = "method=findCustomerListNoPage")
	public String findCustomerListNoPage(Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map param = WebUtil.getAllParameters(request);
		if (null != param.get("searchValue") && !"".equals(String.valueOf(param.get("searchValue")))) {
			map.put(String.valueOf(param.get("searchName")),
					Constants.GET_SQL_LIKE + String.valueOf(param.get("searchValue")) + Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil = new PageUtil();
		WebUtil.MapToBean(param, pageUtil);

		GridModel gridModel = new GridModel();
		gridModel.setRows(cstService.findCustomerList(map, pageUtil));
		gridModel.setTotal(null);
		OutputJson(gridModel, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-24修改日期 修改内容
	 * 
	 * @Title: persistenceCustomer @Description:
	 * TODO:持久化Customer @param @return @param @throws Exception 设定文件 @return
	 * String 返回类型 @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=persistenceCustomer")
	public String persistenceCustomer(Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Customer customer = new Customer();
		WebUtil.MapToBean(param, customer);

		Map<String, List<CustomerContact>> map = new HashMap<String, List<CustomerContact>>();
		if (param.get("inserted") != null && !"".equals(String.valueOf(param.get("inserted")))) {
			map.put("addList", JSON.parseArray(String.valueOf(param.get("inserted")), CustomerContact.class));
		}
		if (param.get("updated") != null && !"".equals(String.valueOf(param.get("updated")))) {
			map.put("updList", JSON.parseArray(String.valueOf(param.get("updated")), CustomerContact.class));
		}
		if (param.get("deleted") != null && !"".equals(String.valueOf(param.get("deleted")))) {
			map.put("delList", JSON.parseArray(String.valueOf(param.get("deleted")), CustomerContact.class));
		}
		OutputJson(getMessage(cstService.persistenceCustomer(customer, map)), Constants.TEXT_TYPE_PLAIN, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-26修改日期 修改内容
	 * 
	 * @Title: delCustomer @Description: TODO:删除客户 @param @return @param @throws
	 * Exception 设定文件 @return String 返回类型 @throws
	 */
	@RequestMapping(params = "method=delCustomer")
	public String delCustomer(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Customer customer = new Customer();
		Map param = WebUtil.getAllParameters(request);
		WebUtil.MapToBean(param, customer);
		OutputJson(getMessage(cstService.delCustomer(customer.getCustomerId())), response);
		return null;
	}

	// public Customer getModel()
	// {
	// if (null == customer)
	// {
	// customer=new Customer();
	// }
	// return customer;
	// }

	/**
	 * 函数功能说明 TODO:获取所有销售代表 Administrator修改者名字 2013-6-24修改日期 修改内容
	 * 
	 * @Title: findSaleNameList @Description: @param @return @param @throws
	 * Exception 设定文件 @return String 返回类型 @throws
	 */
	@RequestMapping(params = "method=findSaleNameList")
	public String findSaleNameList(Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OutputJson(cstService.findSaleNameList(), response);
		return null;
	}
}
