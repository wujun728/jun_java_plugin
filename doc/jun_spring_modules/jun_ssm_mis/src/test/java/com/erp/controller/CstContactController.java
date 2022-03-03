package com.erp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.model.CustomerContact;
import com.erp.service.ICstContactService;
import com.erp.viewModel.GridModel;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.WebUtil;

//@Namespace("/cstContact")
//@Action(value = "cstContactAction")
@Controller
@RequestMapping("/cstContactController.do")
public class CstContactController extends BaseController {
	private static final long serialVersionUID = 5833439394298542947L;
	// private CustomerContact customerContact;
	private ICstContactService cstContactService;

	// public CustomerContact getCustomerContact()
	// {
	// return customerContact;
	// }
	// public void setCustomerContact(CustomerContact customerContact )
	// {
	// this.customerContact = customerContact;
	// }

	@Autowired
	public void setCstContactService(ICstContactService cstContactService) {
		this.cstContactService = cstContactService;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-25修改日期 修改内容
	 * 
	 * @Title: findCustomerContactList
	 * @Description: TODO:查询客户联系人
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findCustomerContactList")
	public String findCustomerContactList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomerContact customerContact = new CustomerContact();
		Map param = WebUtil.getAllParameters(request);
		WebUtil.MapToBean(param, customerContact);
		GridModel gridModel = new GridModel();
		gridModel.setRows(cstContactService.findCustomerContactList(customerContact.getCustomerId()));
		gridModel.setTotal(null);
		OutputJson(gridModel, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-7-9修改日期 修改内容
	 * 
	 * @Title: findCustomerContactListCombobox
	 * @Description: TODO:查询客户联系人
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findCustomerContactListCombobox")
	public String findCustomerContactListCombobox(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomerContact customerContact = new CustomerContact();
		Map param = WebUtil.getAllParameters(request);
		WebUtil.MapToBean(param, customerContact);
		OutputJson(cstContactService.findCustomerContactList(customerContact.getCustomerId()), response);
		return null;
	}

	// public CustomerContact getModel()
	// {
	// if (null == customerContact)
	// {
	// customerContact=new CustomerContact();
	// }
	// return customerContact;
	// }
}
