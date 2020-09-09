package com.github.ghsea.rpc.common.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

import com.github.ghsea.rpc.client.constants.CallTypeEnum;
import com.github.ghsea.rpc.client.proxy.RpcProxy;

public class RpcClientBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
	protected Class<RpcProxy> getBeanClass(Element element) {
		return RpcProxy.class;
	}

	protected void doParse(Element element, BeanDefinitionBuilder bean) {
		String poolName = element.getAttribute("poolName");
		String groupName = element.getAttribute("groupName");
		String version = element.getAttribute("version");
		Integer timeoutMs = Integer.valueOf(element.getAttribute("timeoutMs"));
		Integer retryTimes = Integer.valueOf(element.getAttribute("retryTimes"));
		CallTypeEnum callType = CallTypeEnum.valueOf(element.getAttribute("callType"));
		String targetService = element.getAttribute("targetService");
		String targetInterface = element.getAttribute("targetInterface");

		bean.addPropertyValue("poolName", poolName);
		bean.addPropertyValue("groupName", groupName);
		bean.addPropertyValue("version", version);
		bean.addPropertyValue("timeoutMs", timeoutMs);

		bean.addPropertyValue("retryTimes", retryTimes);
		bean.addPropertyValue("callType", callType);
		bean.addPropertyValue("targetService", targetService);
		bean.addPropertyValue("targetInterface", targetInterface);
	}
}
