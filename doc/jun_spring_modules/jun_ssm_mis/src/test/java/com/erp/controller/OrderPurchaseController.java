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
import com.erp.model.OrderPurchase;
import com.erp.model.OrderPurchaseLine;
import com.erp.service.IOrderPurchaseService;
import com.erp.viewModel.GridModel;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.biz.PageUtil;

//@Namespace("/orderPurchase")
//@Action(value = "orderPurchaseAction")
@Controller
@RequestMapping("/orderPurchaseController.do")
public class OrderPurchaseController extends BaseController {
	private static final long serialVersionUID = 1635368739863491430L;
	private IOrderPurchaseService orderPurchaseService;
//	private OrderPurchase orderPurchase;

//	public OrderPurchase getOrderPurchase() {
//		return orderPurchase;
//	}
//
//	public void setOrderPurchase(OrderPurchase orderPurchase) {
//		this.orderPurchase = orderPurchase;
//	}

	@Autowired
	public void setOrderPurchaseService(IOrderPurchaseService orderPurchaseService) {
		this.orderPurchaseService = orderPurchaseService;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-7-1修改日期 修改内容
	 * 
	 * @Title: findPurchaseOrderLineList
	 * @Description: TODO:查询采购单明细
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findPurchaseOrderLineList")
	public String findPurchaseOrderLineList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		OrderPurchase orderPurchase = new OrderPurchase();
		WebUtil.MapToBean(param, orderPurchase);
		
		GridModel gridModel = new GridModel();
		gridModel.setRows(orderPurchaseService.findPurchaseOrderLineList(orderPurchase.getOrderPurchaseId()));
		gridModel.setTotal(null);
		OutputJson(gridModel, response);
		return null;
	}

	@RequestMapping(params = "method=findPurchaseOrderList")
	public String findPurchaseOrderList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map param = WebUtil.getAllParameters(request);
		if (null != param.get("searchValue") && !"".equals(String.valueOf(param.get("searchValue")))) {
			map.put(String.valueOf(param.get("searchName")), Constants.GET_SQL_LIKE + String.valueOf(param.get("searchValue")) + Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil = new PageUtil();
		WebUtil.MapToBean(param, pageUtil);
		
//		Map<String, Object> map = new HashMap<String, Object>();
//		if (null != searchValue && !"".equals(searchValue)) {
//			map.put(getSearchName(), Constants.GET_SQL_LIKE + searchValue + Constants.GET_SQL_LIKE);
//		}
//		PageUtil pageUtil = new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
		GridModel gridModel = new GridModel();
		gridModel.setRows(orderPurchaseService.findPurchaseOrderList(map, pageUtil));
		gridModel.setTotal(orderPurchaseService.getCount(map, pageUtil));
		OutputJson(gridModel, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-7-1修改日期 修改内容
	 * 
	 * @Title: persistenceOrderPurchase
	 * @Description: TODO:持久化采购单
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=persistenceOrderPurchase")
	public String persistenceOrderPurchase(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, List<OrderPurchaseLine>> map = new HashMap<String, List<OrderPurchaseLine>>();
		Map param = WebUtil.getAllParameters(request);
		OrderPurchase orderPurchase = new OrderPurchase();
		WebUtil.MapToBean(param, orderPurchase);
		if (param.get("inserted") != null && !"".equals(String.valueOf(param.get("inserted")))) {
			map.put("addList", JSON.parseArray(String.valueOf(param.get("inserted")), OrderPurchaseLine.class));
		}
		if (param.get("updated") != null && !"".equals(String.valueOf(param.get("updated")))) {
			map.put("updList", JSON.parseArray(String.valueOf(param.get("updated")), OrderPurchaseLine.class));
		}
		if (param.get("deleted") != null && !"".equals(String.valueOf(param.get("deleted")))) {
			map.put("delList", JSON.parseArray(String.valueOf(param.get("deleted")), OrderPurchaseLine.class));
		}
		
		OutputJson(getMessage(orderPurchaseService.persistenceOrderPurchase(orderPurchase, map)), Constants.TEXT_TYPE_PLAIN, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-7-1修改日期 修改内容
	 * 
	 * @Title: delOrderPurchase
	 * @Description: TODO:删除采购单
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=delOrderPurchase")
	public String delOrderPurchase(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		OrderPurchase orderPurchase = new OrderPurchase();
		WebUtil.MapToBean(param, orderPurchase);
		OutputJson(getMessage(orderPurchaseService.delOrderPurchase(orderPurchase.getOrderPurchaseId())), response);
		return null;
	}

//	public OrderPurchase getModel() {
//		if (null == orderPurchase) {
//			orderPurchase = new OrderPurchase();
//		}
//		return orderPurchase;
//	}
}
