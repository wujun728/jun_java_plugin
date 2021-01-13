package com.techsoft;

import java.util.Map;

import com.techsoft.Interceptor.SQLIDInterceptor;

public class SqlIdProcessImpl implements SQLIDInterceptor {

	@Override
	public void interceptSQLID(SQLObject arg0, Session arg1, DMLType arg2,
			boolean arg3, Map<String, Object> arg4)
			throws SQLIDRejectedException {

	}

}
