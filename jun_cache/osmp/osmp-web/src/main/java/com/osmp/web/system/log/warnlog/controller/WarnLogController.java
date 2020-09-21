package com.osmp.web.system.log.warnlog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.osmp.web.system.log.warnlog.entity.WarnLog;
import com.osmp.web.system.log.warnlog.service.WarnLogService;

/**
 * Description: BUNDLE管理
 * 
 * @author: wangkaiping
 * @date: 2014年8月22日 上午10:51:30
 */
@Controller
@RequestMapping("/warnlog")
public class WarnLogController {
	
	@Autowired
	private WarnLogService warnLogService;

	@RequestMapping("/toList")
	public String toList() {
		return "system/log/warnlog/list";
	}
	
	@RequestMapping("/toAdd")
	public String toAdd() {
		return "system/log/warnlog/warnLogAdd";
	}
	
	@RequestMapping("/warnList")
	@ResponseBody
	public List<WarnLog> warnList(){
		List<WarnLog> list = warnLogService.getList();
		return list;
	}
	
	@RequestMapping("/addWarnLog")
	@ResponseBody
	public Map<String,Object> addWarnLog(WarnLog warnLog){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			warnLogService.addWarnLog(warnLog);
			map.put("success",true);
			map.put("msg","添加成功");
		}catch(Exception e){
			e.printStackTrace();
			map.put("success",false);
			map.put("msg","添加失败");
		}
		return map;
	}


}
