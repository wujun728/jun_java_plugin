package org.typroject.tyboot.prototype.trade.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.face.trade.service.TransactionsBillService;
import org.typroject.tyboot.face.trade.service.TransactionsSerialService;

@Component(value = "baseChannelProcess")
public abstract class BaseChannelProcess {

	@Autowired
	private TransactionsSerialService transactionsSerialService;


	@Autowired
	TransactionsBillService transactionsBillService;



	


	
}
