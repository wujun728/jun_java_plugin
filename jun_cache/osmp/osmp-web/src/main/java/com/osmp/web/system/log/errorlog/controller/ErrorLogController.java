package com.osmp.web.system.log.errorlog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.osmp.web.system.log.errorlog.entity.ErrorLog;
import com.osmp.web.system.log.errorlog.service.ErrorLogService;
import com.osmp.web.utils.DataGrid;
import com.osmp.web.utils.Pagination;

/**
 * Description:
 * 
 * @author: zhangjunming
 * @date: 2014年11月13日 下午17:47:30
 */
@Controller
@RequestMapping("/errorLog")
public class ErrorLogController {

	@Autowired
	public ErrorLogService errorLogService;

	@RequestMapping("/toList")
	public String list() {
		return "system/log/errorLog/list";
	}

	@RequestMapping("/errorLogList")
	@ResponseBody
	public Map<String, Object> errorLogList(DataGrid dg, HttpServletResponse response, 
			ErrorLog errorLog,String startTime, String endTime) {
		Pagination<ErrorLog> p = new Pagination<ErrorLog>(dg.getPage(), dg.getRows());
		List<ErrorLog> list = errorLogService.getList(p, errorLog, startTime, endTime);
		dg.setResult(list);
		dg.setTotal(p.getTotal());
		return dg.getMap();
	}

	@RequestMapping("/deleteErrorLog")
	@ResponseBody
	public Map<String, Object> deleteErrorLog(ErrorLog p) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			errorLogService.delete(p);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}
	
	@RequestMapping("/toErrorLogDetail")
	public ModelAndView toEditUser(ErrorLog errorLog) {
		ModelAndView mav = new ModelAndView("system/log/errorLog/errorLogDetail");
		ErrorLog errorLogDetail = errorLogService.get(errorLog);
		mav.addObject("errorLogDetail", errorLogDetail);
		return mav;
	}

}
