package com.erp.jee.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.jee.pageModel.Online;
import com.erp.jee.service.OnlineServiceI;

/**
 * 在线列表ACTION
 * 
 * @author Wujun
 * 
 */
//@Action(value = "onlineAction")
@Controller
@RequestMapping("/onlineAction.do")
public class OnlineAction extends BaseAction{

	private static final Logger logger = Logger.getLogger(OnlineAction.class);

	private Online online = new Online();

	public Online getModel() {
		return online;
	}

	private OnlineServiceI onlineService;

	public OnlineServiceI getOnlineService() {
		return onlineService;
	}

	@Autowired
	public void setOnlineService(OnlineServiceI onlineService) {
		this.onlineService = onlineService;
	}

	/**
	 * 首页获得在线列表datagrid
	 */
	@RequestMapping(params = "method=deleteAndRepair")
	public void onlineDatagrid(Model model, HttpServletRequest request, HttpServletResponse response) {
		writeJson(response, onlineService.datagrid(online));
	}

}
