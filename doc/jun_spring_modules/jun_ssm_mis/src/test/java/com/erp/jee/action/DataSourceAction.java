package com.erp.jee.action;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * druid datasource manager Action
 * 
 * @author Wujun
 * 
 */
//@Action(value = "datasourceAction", results = { @Result(name = "druid", location = "/druid/index.html", type = "redirect") })
@Controller
@RequestMapping("/datasourceAction.do")
public class DataSourceAction extends BaseAction {

	private static final Logger logger = Logger.getLogger(DataSourceAction.class);

	/**
	 * 跳转到连接池监控页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=goDruid")
	public String goDruid() {
		return "druid";
	}

}
