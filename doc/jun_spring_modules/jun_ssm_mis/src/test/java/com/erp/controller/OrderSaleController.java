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
import com.erp.model.OrderPurchaseLine;
import com.erp.model.OrderSale;
import com.erp.model.OrderSaleLine;
import com.erp.service.IOrderSaleService;
import com.erp.viewModel.GridModel;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.biz.PageUtil;

//@Namespace("/orderSale")
//@Action(value = "orderSaleAction")
@Controller
@RequestMapping("/orderSaleController.do")
public class OrderSaleController extends BaseController {
	private static final long serialVersionUID = -7570327381936186436L;
	private IOrderSaleService orderSaleService;
//	private OrderSale orderSale;

//	public OrderSale getOrderSale() {
//		return orderSale;
//	}
//
//	public void setOrderSale(OrderSale orderSale) {
//		this.orderSale = orderSale;
//	}

	@Autowired
	public void setOrderSaleService(IOrderSaleService orderSaleService) {
		this.orderSaleService = orderSaleService;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-7-8修改日期 修改内容
	 * 
	 * @Title: findOrderSaleLineList
	 * @Description: TODO:查询客户订单明细
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=findOrderSaleLineList")
	public String findOrderSaleLineList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		OrderSale orderSale=new OrderSale();
		WebUtil.MapToBean(param, orderSale);
		GridModel gridModel = new GridModel();
		gridModel.setRows(orderSaleService.findOrderSaleLineList(orderSale.getOrderSaleId()));
		gridModel.setTotal(null);
		OutputJson(gridModel, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-7-8修改日期 修改内容
	 * 
	 * @Title: findOrderSaleList
	 * @Description: TODO:查询客户订单
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=findOrderSaleList")
	public String findOrderSaleList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map param = WebUtil.getAllParameters(request);
		if (null != param.get("searchValue") && !"".equals(String.valueOf(param.get("searchValue")))) {
			map.put(String.valueOf(param.get("searchName")), Constants.GET_SQL_LIKE + String.valueOf(param.get("searchValue")) + Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil = new PageUtil();
		WebUtil.MapToBean(param, pageUtil);
		GridModel gridModel = new GridModel();
		gridModel.setRows(orderSaleService.findOrderSaleList(map, pageUtil));
		gridModel.setTotal(orderSaleService.getCount(map, pageUtil));
		OutputJson(gridModel, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-7-8修改日期 修改内容
	 * 
	 * @Title: persistenceOrderSale
	 * @Description: TODO:持久化客户订单
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=persistenceOrderSale")
	public String persistenceOrderSale(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, List<OrderSaleLine>> map = new HashMap<String, List<OrderSaleLine>>();
		Map param = WebUtil.getAllParameters(request);
		OrderSale orderSale=new OrderSale();
		WebUtil.MapToBean(param, orderSale);

		if (param.get("inserted") != null && !"".equals(String.valueOf(param.get("inserted")))) {
			map.put("addList", JSON.parseArray(String.valueOf(param.get("inserted")), OrderSaleLine.class));
		}
		if (param.get("updated") != null && !"".equals(String.valueOf(param.get("updated")))) {
			map.put("updList", JSON.parseArray(String.valueOf(param.get("updated")), OrderSaleLine.class));
		}
		if (param.get("deleted") != null && !"".equals(String.valueOf(param.get("deleted")))) {
			map.put("delList", JSON.parseArray(String.valueOf(param.get("deleted")), OrderSaleLine.class));
		}
		
		OutputJson(getMessage(orderSaleService.persistenceOrderSale(orderSale, map)), Constants.TEXT_TYPE_PLAIN, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-7-8修改日期 修改内容
	 * 
	 * @Title: delOrderSale
	 * @Description: TODO:删除客户订单
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=delOrderSale")
	public String delOrderSale(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, List<OrderPurchaseLine>> map = new HashMap<String, List<OrderPurchaseLine>>();
		Map param = WebUtil.getAllParameters(request);
		OrderSale orderSale=new OrderSale();
		WebUtil.MapToBean(param, orderSale);
		OutputJson(getMessage(orderSaleService.delOrderSale(orderSale.getOrderSaleId())), response);
		return null;
	}

	// public OrderSale getModel()
	// {
	// if (null==orderSale)
	// {
	// orderSale=new OrderSale();
	// }
	// return orderSale;
	// }
}
