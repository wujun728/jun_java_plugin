package com.github.ghsea.rpc.client.proxy;

import org.springframework.cglib.proxy.Enhancer;

import com.github.ghsea.rpc.common.modle.ReferenceProfile;

class RpcProxyFactory {

	public static <T> T newProxy(final Class<T> interfaceClass, final String targetService,
			final ReferenceProfile reference) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(interfaceClass);
		RpcMethodInterceptor interceptor = new RpcMethodInterceptor(reference);
		enhancer.setCallback(interceptor);

		Object proxy = enhancer.create();
		return interfaceClass.cast(proxy);
	}

}
