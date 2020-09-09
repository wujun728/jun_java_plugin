package com.github.ghsea.rpc.core.exception;

/**
 * 服务节点不存在时抛出该异常
 * 
 * @author ghsea 2017-4-9下午5:08:11
 */
public class RpcProviderNotExistException extends RpcException {

	private static final long serialVersionUID = -4163216581943359585L;

	public RpcProviderNotExistException(String providerName) {
		super(String.format("Provider named %s does not exist:", providerName));
	}
}
