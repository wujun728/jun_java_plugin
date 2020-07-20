
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.net.URI;

import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Embedded;

/**
 * 功 能 描 述:<br>
 * tomcat嵌入应用集成设置
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-12下午03:16:48
 * <br>项 目 信 息:paramecium:.BaseWebServerEmbed.java
 */
public abstract class  BaseWebServerEmbed{
	public int DEFAULT_PORT = 80;
	public String DEFAULT_ENCODING = "UTF-8";
	public String DEFAULT_SERVER_NAME = "app_server";
	public Embedded tomcat;

	public BaseWebServerEmbed(String svrName,int port,String coding) {
		if(svrName!=null){
			DEFAULT_SERVER_NAME=svrName;
		}
		if(port!=0){
			DEFAULT_PORT=port;
		}
		if(coding!=null){
			DEFAULT_ENCODING=coding;
		}
		initEmbedded();
		initShutdownHook();
	}
	public BaseWebServerEmbed() {
		initEmbedded();
		initShutdownHook();
	}

	public abstract String[] getContextsAbsolutePath();

	public abstract String[] getContextsMappingPath();

	public abstract String getTomcatPath();

	public int getPort() {
		return DEFAULT_PORT;
	}

	public void start() {
		try {
			try {
				org.h2.tools.Console console = new org.h2.tools.Console();
				console.runTool();
			} catch (Exception e) {
				e.printStackTrace();
			}
			long startTime = System.currentTimeMillis();
			tomcat.start();
			long l = (System.currentTimeMillis() - startTime);
			System.err.println("本次启动共耗时"+l+"毫秒!");
			if (java.awt.Desktop.isDesktopSupported()) {
				try {
					String ip = "http://127.0.0.1";
					if(DEFAULT_PORT!=80){
						ip = ip.concat(":"+DEFAULT_PORT);
					}
					URI uri = java.net.URI.create(ip);
					Desktop dp = java.awt.Desktop.getDesktop();
					if (dp.isSupported(Action.BROWSE)) {
						dp.browse(uri);
					}
				} catch (java.lang.NullPointerException e) {
				} catch (java.io.IOException e) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void initEmbedded() {
		tomcat = new Embedded();
		tomcat.setCatalinaBase(getTomcatPath());
		Host host = tomcat.createHost("127.0.0.1", tomcat.getCatalinaHome()+ "/webapps");
		String[] contexts = getContextsMappingPath();
		String[] contextsPath = getContextsAbsolutePath();
		Context context = null;
		for (int i = 0; i < contexts.length; ++i) {
			context = tomcat.createContext(contexts[i], contextsPath[i]);
			host.addChild(context);
		}
		Engine engine = tomcat.createEngine();
		engine.setName(DEFAULT_SERVER_NAME);
		engine.addChild(host);
		engine.setDefaultHost(host.getName());
		tomcat.addEngine(engine);
		// 只能本机访问
		//Connector connector = tomcat.createConnector("127.0.0.1", getPort(),false);
		// 可从其它机器访问
		Connector connector = tomcat.createConnector((java.net.InetAddress) null, DEFAULT_PORT, false);
		connector.setURIEncoding(DEFAULT_ENCODING);
		tomcat.addConnector(connector);
	}

	public void initShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				stopTomcat();
			}
		});
	}

	public void stopTomcat() {
		try {
			tomcat.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
