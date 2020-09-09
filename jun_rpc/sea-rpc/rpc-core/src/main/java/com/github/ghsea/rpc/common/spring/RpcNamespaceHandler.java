package com.github.ghsea.rpc.common.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class RpcNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		super.registerBeanDefinitionParser("provider", new RpcProviderBeanDefinitionParser());
		super.registerBeanDefinitionParser("client", new RpcClientBeanDefinitionParser());
	}

}
