package com.github.ghsea.rpc.core.exception;

/**
 * 服务端不存在id=serviceName的Spring bean时，抛出该异常。
 * 
 * @author ghsea 2017-4-9下午5:11:08
 */
public class RpcServiceNotExistException extends RpcException {

	private static final long serialVersionUID = 3150733206859241868L;

	public RpcServiceNotExistException() {

	}

	public RpcServiceNotExistException(String serviceName) {
		super(String.format(
				"Service named %s does not exist。Please make sure there is a Spring Bean whoes id/name is %s",
				serviceName));
	}

	public RpcServiceNotExistException(String serviceName, String methodName) {
		super(
				String.format(
						"Service named %s does not exist。Please make sure there is a Spring Bean whoes id/name is %s and method name is %s",
						serviceName, methodName));
	}
}
