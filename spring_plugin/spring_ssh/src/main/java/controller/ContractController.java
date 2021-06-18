package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pagemodel.DataGrid;
import po.Contract;
import service.ContractService;

@Controller
public class ContractController {
	@Autowired
	ContractService contractservice;
	//获取所有合同列表
	@RequestMapping("/getcontracts/{current}")
	@ResponseBody
	DataGrid<Contract> getcontracts(@PathVariable("current") int current){
		List<Contract> list = contractservice.getContractlist();
		DataGrid<Contract> grid=new DataGrid<Contract>();
		grid.setCurrent(current);
		grid.setTotal(list.size());
		grid.setRows(list);
		return grid;
	}
		
	
}
