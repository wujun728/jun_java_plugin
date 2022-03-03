package com.erp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



//import org.apache.struts2.convention.annotation.Action;
//import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.model.Item;
import com.erp.service.IItemService;
import com.erp.viewModel.GridModel;
//import com.opensymphony.xwork2.ModelDriven;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.biz.PageUtil;

//@Namespace("/item")
//@Action(value = "itemAction")
@Controller
@RequestMapping("/itemController.do")
public class ItemController extends BaseController {
	private static final long serialVersionUID = -5649548096030365832L;
	private IItemService itemService;
//	private Item item;

	// private Integer suplierId;

	// public Integer getSuplierId()
	// {
	// return suplierId;
	// }
	// public void setSuplierId(Integer suplierId )
	// {
	// this.suplierId = suplierId;
	// }

	@Autowired
	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}

//	public Item getItem() {
//		return item;
//	}
//	public void setItem(Item item) {
//		this.item = item;
//	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-7-2修改日期 修改内容
	 * 
	 * @Title: findItemByMyid
	 * @Description: TODO:根据myid查询商品
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findItemByMyid")
	public String findItemByMyid(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Item item = new Item();
		WebUtil.MapToBean(param, item);
		OutputJson(itemService.findItemByMyid(item.getMyid(),Integer.valueOf(param.get("suplierId").toString())), response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-27修改日期 修改内容
	 * 
	 * @Title: findBrandList
	 * @Description: TODO:查询品牌
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findBrandList")
	public String findBrandList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputJson(itemService.findBrandList(), response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-27修改日期 修改内容
	 * 
	 * @Title: addBrands
	 * @Description: TODO:增加品牌
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=addBrands")
	public String addBrands(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Item item = new Item();
		WebUtil.MapToBean(param, item);
		OutputJson(getMessage(itemService.addBrands(item.getName())), response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-27修改日期 修改内容
	 * 
	 * @Title: findItemList
	 * @Description: TODO:查询商品列表
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findItemList")
	public String findItemList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map param = WebUtil.getAllParameters(request);
		if (null != param.get("searchValue") && !"".equals(String.valueOf(param.get("searchValue")))) {
			map.put(String.valueOf(param.get("searchName")), Constants.GET_SQL_LIKE + String.valueOf(param.get("searchValue")) + Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil = new PageUtil();
		WebUtil.MapToBean(param, pageUtil);

		GridModel gridModel = new GridModel();
		gridModel.setRows(itemService.findItemList(map, pageUtil));
		gridModel.setTotal(itemService.getCount(map, pageUtil));
		OutputJson(gridModel, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-27修改日期 修改内容
	 * 
	 * @Title: persistenceItem
	 * @Description: TODO:持久化item
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=persistenceItem")
	public String persistenceItem(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Item item = new Item();
		WebUtil.MapToBean(param, item);
		OutputJson(getMessage(itemService.persistenceItem(item)), Constants.TEXT_TYPE_PLAIN, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-27修改日期 修改内容
	 * 
	 * @Title: delItem
	 * @Description: TODO:删除ITem
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "method=delItem")
	public String delItem(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Item item = new Item();
		WebUtil.MapToBean(param, item);
		OutputJson(getMessage(itemService.delItem(item.getItemId())), response);
		return null;
	}

//	public Item getModel() {
//		if (null == item) {
//			item = new Item();
//		}
//		return item;
//	}
}
