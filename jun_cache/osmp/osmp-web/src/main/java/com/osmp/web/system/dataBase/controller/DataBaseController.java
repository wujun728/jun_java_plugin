package com.osmp.web.system.dataBase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.osmp.web.system.servers.entity.Servers;
import com.osmp.web.system.servers.service.ServersService;

/**
 * 
 * Description: 
 *
 * @author: chelongquan
 * @date: 2015年4月22日 下午3:07:24
 */
@Controller
@RequestMapping("/dataBase")
public class DataBaseController {

	@Autowired
	private ServersService serversService;

	@RequestMapping("/toList")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView("system/dataBase/dataBaselist");
    	List<Servers> list = serversService.getAllServers("");
    	mav.addObject("serverList", list);
        return mav;
	}

}
