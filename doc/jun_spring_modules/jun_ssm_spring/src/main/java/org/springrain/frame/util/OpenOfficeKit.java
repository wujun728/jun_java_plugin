package org.springrain.frame.util;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.frame.util.property.DbPropertyUtil;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * openoffice工具类 wupeilei
 *
 */
public class OpenOfficeKit {
	private OpenOfficeKit(){
		throw new IllegalAccessError("工具类不能实例化");
	}
	
	
	private static final Logger logger = LoggerFactory.getLogger(OpenOfficeKit.class);
	private static final String OpenOffice_HOME = DbPropertyUtil
			.getValue("openoffice.home");
	private static final String HOST = DbPropertyUtil
			.getValue("openoffice.host");;
	private static final String PORT = DbPropertyUtil
			.getValue("openoffice.port");;
	private static Process pro = null;
	private static OpenOfficeConnection connection = null;
	static {
		createOpenOfficeConnection();
	}

	/**
	 * 启动openoffice连接
	 * 
	 * @return
	 * @throws Exception
	 */
	static void createOpenOfficeConnection() {
		try {
			if (connection == null || (!connection.isConnected())) {
				if (StringUtils.isNotBlank(OpenOffice_HOME)
						&& StringUtils.isNotBlank(HOST)
						&& StringUtils.isNotBlank(PORT)) {
					String command = OpenOffice_HOME
							+ " -headless -accept=\"socket,host=" + HOST
							+ ",port=" + PORT + ";urp;\"";
					pro = Runtime.getRuntime().exec(command);
					connection = new SocketOpenOfficeConnection(HOST,
							Integer.parseInt(PORT));
					connection.connect();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	/**
	 * 关闭openoffice连接
	 * 
	 * @throws Exception
	 */
	static void closeOpenOfficeConnection() {
		try {
			if (connection != null && connection.isConnected()) {
				connection.disconnect();
			}
			if (pro != null) {
				pro.destroy();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	public static void cvtXls(File inputFile, File outputFile) throws Exception {
		createOpenOfficeConnection();
		if (connection != null && connection.isConnected()) {
			DocumentConverter converter = new OpenOfficeDocumentConverter(
					connection);
			converter.convert(inputFile, outputFile);
		} else {
			throw new Exception();
		}
	}

}
