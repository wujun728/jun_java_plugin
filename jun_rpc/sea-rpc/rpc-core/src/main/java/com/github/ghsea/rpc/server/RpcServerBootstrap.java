package com.github.ghsea.rpc.server;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import com.google.common.base.Preconditions;

/**
 * 服务端的启动类，负责启动和停止Netty服务器。 需要将该类配置为Spring Bean
 * 
 * @author ghsea 2017-4-9下午5:27:36
 */
public class RpcServerBootstrap implements ApplicationListener<ApplicationEvent>, ApplicationContextAware {

	private static ApplicationContext appCtx;

	private static AtomicBoolean inited = new AtomicBoolean(false);

	private Logger log = LoggerFactory.getLogger(RpcServerBootstrap.class);

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		log.debug("RpcServerBootstrap onApplicationEvent...");
		Server server = ServerFactory.getInstance();
		if (event instanceof ContextRefreshedEvent) {
			server.start();
			inited.set(true);
			return;
		}

		if (event instanceof ContextClosedEvent) {
			server.shutdown();
			return;
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appCtx = applicationContext;
	}

	public static <T> T getBean(Class<T> clz) {
		ensureInited();
		return appCtx.getBean(clz);
	}

	public static Object getBean(String service) {
		ensureInited();
		return appCtx.getBean(service);
	}

	private static void ensureInited() {
		Preconditions.checkState(inited.get(),
				"RpcServerBootstrap is not started.Make sure you has configed RpcServerBootstrap as a Spring Bean");
	}

}
