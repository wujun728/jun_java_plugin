package com.osmp.web.system.dataService.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.osmp.web.system.dataService.entity.DataService;
import com.osmp.web.system.dataService.service.DataServiceService;
import com.osmp.web.system.servers.entity.Servers;
import com.osmp.web.system.servers.service.ServersService;
import com.osmp.web.utils.DataGrid;
import com.osmp.web.utils.Pagination;

/**
 * Description: DataService管理
 *
 * @author: 车龙泉
 * @date: 2015年4月17日 上午
 */
@Controller
@RequestMapping("/dataService")
public class DataServiceController {

    @Autowired
    private DataServiceService dataServiceService;
    @Autowired
    private ServersService serversService;

    @RequestMapping("/toList")
    public ModelAndView toList() {
    	ModelAndView mav = new ModelAndView("system/dataService/dataServicelist");
    	List<Servers> list = serversService.getAllServers("");
    	mav.addObject("serverList", list);
        return mav;
    }
    
    @RequestMapping("/todataServicelistOfServer")
    public ModelAndView toserverBundlelist(@RequestParam("serverIp")String serverIp) {//车龙泉
    	ModelAndView mav = new ModelAndView("system/dataService/dataServicelistOfServer");
    	mav.addObject("serverIp", serverIp);
        return mav;
    }
    

    @RequestMapping("/dataServiceList")
    @ResponseBody
    public Map<String, Object> bundleList(DataGrid dg,
            @RequestParam(value = "displayAll", required = false, defaultValue = "N") String displayAll, @RequestParam("serverIp")String serverIp) {
    	Pagination<DataService> p = new Pagination<DataService>(dg.getPage(), dg.getRows());
    	dg.setResult(dataServiceService.getList(p, "loadIp='"+serverIp+"'"));
    	dg.setTotal(p.getTotal());
    	return dg.getMap();
    }
}
