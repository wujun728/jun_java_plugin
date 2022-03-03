package com.erp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.service.IWarehouseService;

//@Namespace("/warehouse")
//@Action(value = "warehouseAction")
@Controller
@RequestMapping("/warehouseController.do")
public class WarehouseController extends BaseController
{
	private static final long serialVersionUID = -4202679640252934032L;
//	private Warehouse warehouse;
	private IWarehouseService warehouseService;
	@Autowired
	public void setWarehouseService(IWarehouseService warehouseService )
	{
		this.warehouseService = warehouseService;
	}

//	public Warehouse getWarehouse()
//	{
//		return warehouse;
//	}
//
//	public void setWarehouse(Warehouse warehouse )
//	{
//		this.warehouse = warehouse;
//	}
	
	/**  
	* 函数功能说明
	* Administrator修改者名字
	* 2013-7-1修改日期
	* 修改内容
	* @Title: findWarehouseListCombobox 
	* @Description: TODO:查询所有仓库下拉框格式
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping(params = "method=findWarehouseListCombobox")
	public String findWarehouseListCombobox(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OutputJson(warehouseService.findWarehouseListCombobox(), response);
		return null;
	}

//	public Warehouse getModel()
//	{
//		if (null==warehouse)
//		{
//			warehouse=new Warehouse();
//		}
//		return warehouse;
//	}

}
