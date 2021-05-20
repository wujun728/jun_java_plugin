package io.springside.springtime.springboot;

import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import io.springside.springtime.jetty.SpringTimeHandler;

/**
 * 定制化Jetty，加入Http/Http2支持，指定专门的
 */
@Component
@ConfigurationProperties(prefix = "server.jetty", ignoreUnknownFields = true)
public class SpringTimeJettyCustomizer implements EmbeddedServletContainerCustomizer, ApplicationContextAware {

	private ApplicationContext applicationContext;

	private Integer maxThreads = Runtime.getRuntime().availableProcessors() * 20;

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		final ServerProperties sp = applicationContext.getBean(ServerProperties.class);

		JettyEmbeddedServletContainerFactory jettyFactory = (JettyEmbeddedServletContainerFactory) container;

		customizeThreadPool(jettyFactory);

		jettyFactory.addServerCustomizers(new JettyServerCustomizer() {

			@Override
			public void customize(Server server) {
				customizeHttp2Connector(server);
				customizeSpringTimeHanlder(server);
			}

			private void customizeHttp2Connector(Server server) {
				HttpConfiguration config = new HttpConfiguration();

				// HTTP/1.1 support.
				HttpConnectionFactory http1 = new HttpConnectionFactory(config);

				// HTTP/2 cleartext support.
				HTTP2CServerConnectionFactory http2c = new HTTP2CServerConnectionFactory(config);

				int acceptors = -1;
				if (sp.getJetty().getAcceptors() != null) {
					acceptors = sp.getJetty().getAcceptors();
				}

				int selectors = -1;
				if (sp.getJetty().getSelectors() != null) {
					selectors = sp.getJetty().getSelectors();
				}

				ServerConnector connector = new ServerConnector(server, null, null, null, acceptors, selectors, http1,
						http2c);

				connector.setPort(sp.getPort());

				server.setConnectors(new Connector[] { connector });
			}

			private void customizeSpringTimeHanlder(Server server) {
				// add SpringTimeHandler at the beginning
				Handler[] oldHandlers = server.getHandlers();
				SpringTimeHandler springTimeHandler = new SpringTimeHandler(applicationContext);
				HandlerList handlerList = new HandlerList();
				handlerList.addHandler(springTimeHandler);
				for (Handler handler : oldHandlers) {
					handlerList.addHandler(handler);
				}
				server.setHandler(handlerList);
			}

		});
	}

	private void customizeThreadPool(JettyEmbeddedServletContainerFactory jettyFactory) {
		QueuedThreadPool threadPool = (QueuedThreadPool) jettyFactory.getThreadPool();
		if (threadPool == null) {
			threadPool = new QueuedThreadPool();
			jettyFactory.setThreadPool(threadPool);
		}
		threadPool.setMaxThreads(maxThreads);
		threadPool.setIdleTimeout(10000);
	}

	public void setMaxThreads(Integer maxThreads) {
		this.maxThreads = maxThreads;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
