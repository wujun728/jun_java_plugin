package jetty;

import org.eclipse.jetty.server.Server;

public class JettyStart {  

	public static final int PORT = 8080;
	public static final String CONTEXT = "/springrain";
	public static void main(String[] args) throws Exception {
		Server server = JettyUtils.buildDebugServer(PORT, CONTEXT);
		server.start();
		System.out.println("点击回车键后可以停止jetty服务");
		if (System.in.read() != 0) {
			server.stop();
			System.out.println("jetty服务已经停止");
		}
	}
}