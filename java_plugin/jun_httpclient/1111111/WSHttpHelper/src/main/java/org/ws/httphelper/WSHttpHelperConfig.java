package org.ws.httphelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;


/**
 * <b>描述：</b> 配置文件<br/>
 * <b>作者：</b> gz <br/>
 * <b>创建时间：</b> 2015-10-12 上午10:17:14 <br/>
 */
@Deprecated
public class WSHttpHelperConfig {
	/**
	 * 配置文件名称
	 */
	public static final String CONFIG_FILE_NAME= "/wshttphelper.config.properties";
	
	private static Properties CONFIG_PROPS = null;
	static{
		InputStream is = WSHttpHelperConfig.class.getResourceAsStream(CONFIG_FILE_NAME);
		try {
			Properties props = new Properties();
			props.load(is);
			CONFIG_PROPS = props;
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * <b>描述：</b> 获取值<br/>
	 * <b>作者：</b>gz <br/>
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		return CONFIG_PROPS.getProperty(key);
	}
	
	public static int getInt(String key){
		return Integer.valueOf(getValue(key));
	}
	
	/**
	 * <b>描述：</b> 获取所有key<br/>
	 * <b>作者：</b>gz <br/>
	 * @return
	 */
	public static Set<Object> keySet(){
		return CONFIG_PROPS.keySet();
	}
	
	public static String getCharset(){
		return getValue(WSHttpHelperConstant.HTTP_ENCODE_CHARSET);
	}
}
