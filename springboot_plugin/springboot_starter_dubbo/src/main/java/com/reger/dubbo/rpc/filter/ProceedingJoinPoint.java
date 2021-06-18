package com.reger.dubbo.rpc.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;

public class ProceedingJoinPoint<T> implements JoinPoint<T> {

	private static final Logger log = LoggerFactory.getLogger(ProceedingJoinPoint.class);

	private final Invoker<T> invoker;
	private final Invocation invocation;
	private final List<? extends RpcFilter> filters;
	private volatile int index = 0;
	private volatile int filterCount = 0;
	private Map<String, Object> attributes=new HashMap<String, Object>();

	protected ProceedingJoinPoint(Invoker<T> invoker, Invocation invocation, List<? extends RpcFilter> rpcFilters) {
		super();
		this.invoker = invoker;
		this.invocation = invocation;
		this.filters = rpcFilters;
		if (this.filters != null) {
			this.filterCount = this.filters.size();
		}
	}

	@Override
	public Result proceed() {
		if (index >= filterCount) {
			log.debug("过滤器调用完毕，开始执行真实调用 ,  {}", invoker.getInterface());
			return Utils.decodeException(invoker.invoke(invocation));
		} else {
			log.debug("注册了{}个过滤器，当前调用第{}个,过滤器通过beanName排序", filterCount, index);
			RpcFilter rpcFilter = filters.get(index++);
			return rpcFilter.invoke(this);
		}
	}

	public Invoker<T> getInvoker() {
		return invoker;
	}

	public Invocation getInvocation() {
		return invocation;
	}

	@Override
	public Class<T> getInterface() {
		return invoker.getInterface();
	}

	@Override
	public URL getUrl() {
		return invoker.getUrl();
	}

	@Override
	public boolean isAvailable() {
		return invoker.isAvailable();
	}

	@Override
	public void destroy() {
		invoker.destroy();
	}

	@Override
	public String getMethodName() {
		return invocation.getMethodName();
	}

	@Override
	public Class<?>[] getParameterTypes() {
		return invocation.getParameterTypes();
	}

	@Override
	public Object[] getArguments() {
		return invocation.getArguments();
	}

	@Override
	public Map<String, String> getAttachments() {
		return invocation.getAttachments();
	}

	@Override
	public String getAttachment(String key) {
		return invocation.getAttachment(key);
	}

	@Override
	public String getAttachment(String key, String defaultValue) {
		return invocation.getAttachment(key, defaultValue);
	}

	@Override
	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	@Override
	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}
}
