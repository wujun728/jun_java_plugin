package com.erp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.service.ICurrencyService;

//@Namespace("/currency")
//@Action(value = "currencyAction")
@Controller
@RequestMapping("/currencyController.do")
public class CurrencyController extends BaseController
{
	private static final long serialVersionUID = -8036970315807711701L;
	private ICurrencyService currencyService;
	@Autowired
	public void setCurrencyService(ICurrencyService currencyService )
	{
		this.currencyService = currencyService;
	}
	
	/**  
	* 函数功能说明 
	* Administrator修改者名字
	* 2013-6-25修改日期
	* 修改内容
	* @Title: findCurrencyList 
	* @Description: TODO:查询币别
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping(params = "method=findCurrencyList")
	public String findCurrencyList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OutputJson(currencyService.findCurrencyList(),response);
		return null;
	}
}
