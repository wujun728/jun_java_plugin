package org.tangjl.multidatasource.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * 该类继承org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
	 * Spring会在获取数据源时自动调用
	 * 详情请查看Spring源码
	 * org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource.determineTargetDataSource()
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		// 返回使用的数据源的key
		return DBContextHolder.getDBType();
	}
}