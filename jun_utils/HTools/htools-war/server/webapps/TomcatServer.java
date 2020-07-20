
/**
 * 功 能 描 述:<br>
 * tomcat嵌入服务
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-12下午03:16:34
 * <br>项 目 信 息:paramecium:.TomcatServer.java
 */
public class TomcatServer extends BaseWebServerEmbed {
	public String DEFAULT_PATH = "/WebRoot";

	public String[] getContextsAbsolutePath() {
		return new String[] { getSingleContextAbsolutePath() };
	}

	private String getSingleContextAbsolutePath() {
		String path = System.getProperty("user.dir") + DEFAULT_PATH;
		return path;
	}

	public String[] getContextsMappingPath() {
		return new String[] { "/" };
	}
	
	public TomcatServer(String svrName,String path,int port,String coding){
		if(path!=null){
			DEFAULT_PATH=path;
		}
		if(svrName!=null){
			super.DEFAULT_SERVER_NAME=svrName;
		}
		if(port!=0){
			super.DEFAULT_PORT=port;
		}
		if(coding!=null){
			super.DEFAULT_ENCODING=coding;
		}
		initEmbedded();
		initShutdownHook();
	}
	
	public TomcatServer(String svrName,int port,String coding){
		this(svrName,null,80,coding);
	}
	
	public TomcatServer() {
		this(null,null,80,null);
	}
	
	public TomcatServer(int port) {
		this(null,null,port,null);
	}
	
	public String getTomcatPath() {
		try {
			return System.getProperty("user.dir") + "/server";
		} catch (Exception e) {
			return "/D:\\tomcat-embed";
		}
	}

}
