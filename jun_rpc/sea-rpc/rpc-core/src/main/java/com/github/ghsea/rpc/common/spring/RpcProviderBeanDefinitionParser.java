package com.github.ghsea.rpc.common.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

import com.github.ghsea.rpc.server.RpcServiceExporter;

public class RpcProviderBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
	protected Class<RpcServiceExporter> getBeanClass(Element element) {
		return RpcServiceExporter.class;
	}

	protected void doParse(Element element, BeanDefinitionBuilder bean) {
		String targetInterface = element.getAttribute("interface");
		String targetService = element.getAttribute("ref");
		String pool = element.getAttribute("pool");
		String version = element.getAttribute("version");
		bean.addPropertyValue("serviceInterface", targetInterface);
		bean.addPropertyValue("serviceTarget", targetService);
		bean.addPropertyValue("pool", pool);
		bean.addPropertyValue("version", version);
	}
}
