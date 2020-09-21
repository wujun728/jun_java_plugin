package com.osmp.web.system.performance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.osmp.web.system.servers.entity.Servers;
import com.osmp.web.system.servers.service.ServersService;

/**
 * 
 * Description: 性能监控
 *
 * @author: chelongquan
 * @date: 2015年4月22日 下午3:33:03
 */
@Controller
@RequestMapping("/performance")
public class performanceController {

	@Autowired
	private ServersService serversService;

	@RequestMapping("/toList")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView("system/performance/performancelist");
    	List<Servers> list = serversService.getAllServers("");
    	mav.addObject("serverList", list);
        return mav;
	}
	
	@RequestMapping("/toperformancelistOfServer.do")
	public ModelAndView toserverBundlelist(@RequestParam("serverIp")String serverIp) {//车龙泉
    	ModelAndView mav = new ModelAndView("system/performance/performancelistOfServer");
    	mav.addObject("serverIp", serverIp);
        return mav;
    }

}
