package com.erp.jee.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.jee.service.RepairServiceI;

/**
 * 修复数据库ACTION
 */
//@Action(value = "repairAction", results = { @Result(name = "success", location = "/index.jsp") })
@Controller
@RequestMapping("/repairAction.do")
public class RepairAction extends BaseAction {

	private RepairServiceI repairService;

	public RepairServiceI getRepairService() {
		return repairService;
	}

	@Autowired
	public void setRepairService(RepairServiceI repairService) {
		this.repairService = repairService;
	}

	/**
	 * 修复数据库
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=repair")
	public String repair() {
		repairService.repair();
		return "success";
	}

	/**
	 * 先清空数据库，然后再修复数据库
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=deleteAndRepair")
	public String deleteAndRepair() {
		repairService.deleteAndRepair();
		return "success";
	}

}
