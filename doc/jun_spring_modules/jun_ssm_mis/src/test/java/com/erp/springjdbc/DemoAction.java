package com.erp.springjdbc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.controller.BaseController;
import com.erp.page.DemoPage;
import com.erp.service.DemoServiceI;

/**   
 * @Title: Action
 * @Description: 用户
 * @author Wujun
 * @date 2011-12-25 20:55:16
 * @version V1.0   
 *
 */
//@Action(value = "demoAction", results = { @Result(name = "demo-jdbc", location = "/sun/test/demo-jdbc.jsp") })
@Controller
@RequestMapping("/demoAction.do")
public class DemoAction extends BaseController{

	private static final Logger logger = Logger.getLogger(DemoAction.class);

	@Autowired(required=true)
	private DemoServiceI demoService;

	DemoPage demoPage= new DemoPage();
//
//	public DemoPage getModel(Model model, HttpServletRequest request, HttpServletResponse response){
//		return demoPage;
//	}


	/**
	 * 获得pageHotel数据表格
	 */
	@RequestMapping(params = "datagridByJdbc")
	public void datagridByJdbc(Model model, HttpServletRequest request, HttpServletResponse response){
		writeJson(demoService.listAllByJdbc(demoPage), response);
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(params = "goDemoJdbc")
	public String goDemoJdbc(Model model, HttpServletRequest request, HttpServletResponse response){
		return "/sun/test/demo-jdbc";
	}


	
//	public DemoPage getDemoPage(Model model, HttpServletRequest request, HttpServletResponse response){
//		return demoPage;
//	}
//
//
//	public void setDemoPage(DemoPage demoPage) {
//		this.demoPage = demoPage;
//	}
}
