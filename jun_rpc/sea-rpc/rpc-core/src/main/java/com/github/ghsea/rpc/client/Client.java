package com.github.ghsea.rpc.client;

import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import com.github.ghsea.rpc.core.RpcRequest;
import com.github.ghsea.rpc.core.RpcResponseFuture;

/**
 * 
 * @author ghsea 2017-2-10下午8:36:37
 */
public interface Client {
	/**
	 * 发送请求
	 * 
	 * @param request
	 * @return Object 如果是同步请求，则返回最终结果Object,如果是异步请求，则返回Future
	 * @throws RejectedExecutionException
	 * @throws Exception
	 */
	Object request(final RpcRequest request) throws RejectedExecutionException, Exception;

	Future<?> requestAsync(final RpcRequest request) throws RejectedExecutionException, Exception;

	/**
	 * 超时操作
	 * 
	 * @param requetId
	 */
	void timeout(String requestId);

	RpcResponseFuture getFuture(String requestId);
}
