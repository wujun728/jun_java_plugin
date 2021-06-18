package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import po.Contract;
import service.ContractService;
import dao.ContractDao;
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=5)
@Service("contractservice")
public class ContractServiceImpl implements ContractService{
	@Autowired
	ContractDao contractdao;
	
	public List<Contract> getContractlist() {
		return contractdao.find("from Contract");
	}






}
