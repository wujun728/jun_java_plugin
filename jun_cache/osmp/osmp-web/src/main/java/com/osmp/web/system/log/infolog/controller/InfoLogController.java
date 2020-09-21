package com.osmp.web.system.log.infolog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.osmp.web.system.log.infolog.entity.InfoLog;
import com.osmp.web.system.log.infolog.service.InfoService;
import com.osmp.web.utils.DataGrid;
import com.osmp.web.utils.Pagination;

/**
 * Description:INFO日志控制器
 * 
 * @author: zhangjunming
 * @date: 2014年11月13日 下午17:47:30
 */
@Controller
@RequestMapping("/infoLog")
public class InfoLogController {

	@Autowired
	public InfoService infoService;

	/**
	 * 跳转到列表界面
	 * 
	 * @return
	 */
	@RequestMapping("/toList")
	public String list() {
		return "system/log/infolog/list";
	}

	/**
	 * 列表取数据
	 * 
	 * @param dg
	 * @param response
	 * @return
	 */
	@RequestMapping("/infoLogList")
	@ResponseBody
	public Map<String, Object> errorLogList(DataGrid dg, HttpServletResponse response, InfoLog infoLog,
			String startTime, String endTime) {
		Pagination<InfoLog> p = new Pagination<InfoLog>(dg.getPage(), dg.getRows());
		dg.setResult(infoService.getList(p, infoLog, startTime, endTime));
		dg.setTotal(p.getTotal());
		return dg.getMap();
	}

	/**
	 * 删除日志
	 * 
	 * @param p
	 * @return
	 */
	@RequestMapping("/deleteInfoLog")
	@ResponseBody
	public Map<String, Object> deleteErrorLog(InfoLog p) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			infoService.delete(p);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}
	
	@RequestMapping("/toInfoLogDetail")
	public ModelAndView toEditUser(InfoLog infoLog) {
		ModelAndView mav = new ModelAndView("system/log/infolog/infoLogDetail");
		InfoLog infoLogDetail = infoService.get(infoLog);
		mav.addObject("infoLogDetail", infoLogDetail);
		return mav;
	}

}
