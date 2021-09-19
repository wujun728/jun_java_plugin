package com.jun.plugin.oa.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jun.plugin.oa.entity.Datagrid;
import com.jun.plugin.oa.entity.SuperviseReceive;
import com.jun.plugin.oa.pagination.Page;
import com.jun.plugin.oa.service.ISuperviseReceiveService;

/**
 * 督察接收
 * @author zhao
 *
 */
@Controller
@RequestMapping("/superviserReveive")
public class SuperviseReceiveController {

	@Autowired
	private ISuperviseReceiveService receiveService;
	
	/**
	 * 跳转到督察接收
	 * @return
	 */
	@RequestMapping(value = "/toSuperviseReceive")
	public ModelAndView toSuperviseReceive() {
		ModelAndView mv = new ModelAndView("/superviseReceive/list_receive");
		return mv;
	}
	
	/**
	 * 加载组信息-easyui
	 * @return
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public Datagrid<SuperviseReceive> getList(@RequestParam(value = "page", required = false) Integer page, 
								@RequestParam(value = "rows", required = false) Integer rows) throws Exception{
		Page<SuperviseReceive> p = new Page<SuperviseReceive>(page, rows);
		this.receiveService.getListPage(p);
		Datagrid<SuperviseReceive> dataGrid = new Datagrid<SuperviseReceive>(p.getTotal(), p.getResult());
		return dataGrid;
	}
}
