package jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyUtils {

	/**
	 * 创建用于Debug的Jetty Server, 以src/main/webapp为Web应用目录.
	 */
	public static Server buildDebugServer(int port, String contextPath) {
		WebAppContext webContext = new WebAppContext();
		String cvsResource = "src/main/webapp";
		Resource resource = new ResourceCollection(cvsResource);
		webContext.setBaseResource(resource);
		webContext.setClassLoader(Thread.currentThread()
				.getContextClassLoader());
		webContext.setContextPath(contextPath);
		
		Server server = new Server(port);
		server.setHandler(webContext);
		server.setStopAtShutdown(true);
		return server;
	}

	/**
	 * 创建用于Functional TestJetty Server, 以src/main/webapp为Web应用目录.
	 * 以test/resources/web.xml指向applicationContext-test.xml创建测试环境.
	 */
	public static Server buildTestServer(int port, String contextPath) {
		Server server = new Server(port);
		WebAppContext webContext = new WebAppContext("src/main/webapp",
				contextPath);
		webContext.setClassLoader(Thread.currentThread()
				.getContextClassLoader());
		webContext.setDescriptor("src/test/resources/web.xml");
		server.setHandler(webContext);
		server.setStopAtShutdown(true);
		return server;
	}
}
