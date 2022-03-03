package org.springrain.frame.dao;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 配合spirng的事务拦截器,如果有事务就是写库,没有事务就是读库
 * @author caomei
 *
 */
public class TransactionDataSourceRouter extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		if(FrameDataSourceTransactionManager.isExistTransaction()) {
			return "dataSourceWrite";
		}
		return "dataSourceRead";
	}
	

	


	

}
