package com.github.ghsea.rpc.core;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import com.github.ghsea.rpc.core.message.RpcResponseMsg;

public interface RpcResponseFuture extends Future<Object> {

	/**
	 * 获取Future的创建时间
	 * 
	 * @return
	 */
	Date getCreateTime();

	void operationComplete(final RpcResponseMsg response);
	
	void addListener(RpcResponseFutureCallback callback,Executor executor);
}
