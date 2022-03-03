package com.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.erp.model.Suplier;
import com.erp.model.SuplierContact;
import com.erp.service.ISupService;
import com.erp.viewModel.GridModel;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.biz.PageUtil;
//@Namespace("/sup")
//@Action(value = "supAction")
@Controller
@RequestMapping("/supController.do")
public class SupController extends BaseController {
	private static final long serialVersionUID = -1687326503418955787L;
//	private Suplier suplier;
	private ISupService supService;

//	public Suplier getSuplier() {
//		return suplier;
//	}
//
//	public void setSuplier(Suplier suplier) {
//		this.suplier = suplier;
//	}

	@Autowired
	public void setSupService(ISupService supService) {
		this.supService = supService;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-26修改日期 修改内容
	 * 
	 * @Title: findSuplierContactList
	 * @Description: TODO:查询供应商联系人
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findSuplierContactList")
	public String findSuplierContactList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Suplier suplier=new Suplier();
		WebUtil.MapToBean(param, suplier);
		GridModel gridModel = new GridModel();
		gridModel.setRows(supService.findSuplierContactList(suplier.getSuplierId()));
		gridModel.setTotal(null);
		OutputJson(gridModel, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-7-1修改日期 修改内容
	 * 
	 * @Title: findSuplierContactListCombobox
	 * @Description: TODO:查询供应商联系人下拉框格式
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findSuplierContactListCombobox")
	public String findSuplierContactListCombobox(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Suplier suplier=new Suplier();
		WebUtil.MapToBean(param, suplier);
		OutputJson(supService.findSuplierContactList(suplier.getSuplierId()), response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-26修改日期 修改内容
	 * 
	 * @Title: findSuplierList
	 * @Description: TODO:查询所有客户列表
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findSuplierList")
	public String findSuplierList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map param = WebUtil.getAllParameters(request);
		if (null != param.get("searchValue") && !"".equals(String.valueOf(param.get("searchValue")))) {
			map.put(String.valueOf(param.get("searchName")), Constants.GET_SQL_LIKE + String.valueOf(param.get("searchValue")) + Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil = new PageUtil();
		WebUtil.MapToBean(param, pageUtil);
		GridModel gridModel = new GridModel();
		gridModel.setRows(supService.findSuplierList(map, pageUtil));
		gridModel.setTotal(supService.getCount(map, pageUtil));
		OutputJson(gridModel, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-7-1修改日期 修改内容
	 * 
	 * @Title: findSuplierListNoPage
	 * @Description: TODO:无分页查询所有供应商
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findSuplierListNoPage")
	public String findSuplierListNoPage(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map param = WebUtil.getAllParameters(request);
		if (null != param.get("searchValue") && !"".equals(String.valueOf(param.get("searchValue")))) {
			map.put(String.valueOf(param.get("searchName")), Constants.GET_SQL_LIKE + String.valueOf(param.get("searchValue")) + Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil = new PageUtil();
		WebUtil.MapToBean(param, pageUtil);
		GridModel gridModel = new GridModel();
		gridModel.setRows(supService.findSuplierListNoPage(map, pageUtil));
		gridModel.setTotal(null);
		OutputJson(gridModel, response);
		return null;
//		return super.execute();
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-24修改日期 修改内容
	 * 
	 * @Title: persistenceSuplier
	 * @Description: TODO:持久化Suplier
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=persistenceSuplier")
	public String persistenceSuplier(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, List<SuplierContact>> map = new HashMap<String, List<SuplierContact>>();
		Map param = WebUtil.getAllParameters(request);
		Suplier suplier=new Suplier();
		WebUtil.MapToBean(param, suplier);

		if (param.get("inserted") != null && !"".equals(String.valueOf(param.get("inserted")))) {
			map.put("addList", JSON.parseArray(String.valueOf(param.get("inserted")), SuplierContact.class));
		}
		if (param.get("updated") != null && !"".equals(String.valueOf(param.get("updated")))) {
			map.put("updList", JSON.parseArray(String.valueOf(param.get("updated")), SuplierContact.class));
		}
		if (param.get("deleted") != null && !"".equals(String.valueOf(param.get("deleted")))) {
			map.put("delList", JSON.parseArray(String.valueOf(param.get("deleted")), SuplierContact.class));
		}
		OutputJson(getMessage(supService.persistenceSuplier(suplier, map)), Constants.TEXT_TYPE_PLAIN, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-26修改日期 修改内容
	 * 
	 * @Title: delSuplier
	 * @Description: TODO:删除Suplier
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=delSuplier")
	public String delSuplier(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Suplier suplier=new Suplier();
		WebUtil.MapToBean(param, suplier);
		OutputJson(getMessage(supService.delSuplier(suplier.getSuplierId())), response);
		return null;
	}

	// public Suplier getModel()
	// {
	// if (null==suplier)
	// {
	// suplier=new Suplier();
	// }
	// return suplier;
	// }

}
