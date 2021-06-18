/**
 * Program  : WebServiceUtil.java
 * Author   : leigq
 * Create   : 2010-11-12 上午09:02:05
 *
 * Copyright 2010 by Embedded Internet Solutions Inc.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Embedded Internet Solutions Inc.("Confidential Information").  
 * You shall not disclose such Confidential Information and shall 
 * use it only in accordance with the terms of the license agreement 
 * you entered into with Embedded Internet Solutions Inc.
 *
 */

package cn.ipanel.apps.portalBackOffice.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cn.ipanel.apps.portalBackOffice.define.Defines;
import cn.ipanel.apps.portalBackOffice.domain.WSAddress;

/**
 * webService 工具类
 * @author leigq
 * @version 1.0.0
 * @2010-11-12 上午09:02:05
 */
public class WebServiceUtil {
	
	private Properties properties = new Properties();
	
	private static Logger logger = Logger.getLogger(WebServiceUtil.class);
	
	public WebServiceUtil() {
		properties = PropertyManager.getConfig();
	}

	/**
	 * 获取WebService配置信息,返回的数据格式为
	 * @return List<WSAddress>
	 * @author leigq
	 * @create 2010-11-12 上午09:58:32
	 */
	public List<WSAddress> getWebServers() {
		List<WSAddress> result = new ArrayList<WSAddress>();

		Enumeration<?> enu = properties.propertyNames();
		Pattern pattern = Pattern.compile("^(wsAddress)X?");
		CONTINUE_POINT: while (enu.hasMoreElements()) {
			try {
				String key = (String) enu.nextElement();
				Matcher matcher = pattern.matcher(key);
				if (!matcher.find())
					continue;

				String propertityValue = (String) properties.get(key);
				// 如果不是以';'分隔的,则不处理
				if (propertityValue.indexOf(";") == -1)
					continue;

				String[] values = propertityValue.split(";");
				// 如果不是三段规则,则不处理
				if (values.length != 4)
					continue;

				for (int i = 0; i < values.length; i++)
					if (values[i] == null || values[i].trim().length() == 0)
						continue CONTINUE_POINT;

				result.add(new WSAddress(key, values[0], values[1], values[2],values[3]));
			} catch (Exception e) {
				logger.warn(e);
			}
		}
		return result;
	}
	
	/**
	 * 检测此key值是否已经被使用,若重复使用相同key值,会以新的配置覆盖旧的配置
	 * @param key
	 * @return
	 * @author leigq
	 * @create 2010-11-12 上午10:01:10
	 */
	public boolean checkKeyIsExist(String key) {
		Set<Object> keys = properties.keySet();
		if (keys.contains(key))
			return true;
		return false;
	}
	/**
	 * 保存WebService配置到property文件
	 * @param wsAddress
	 * @return
	 * @author leigq
	 * @create 2010-11-12 上午10:02:25
	 */
	public boolean storWSAddress(WSAddress wsAddress){
		if (wsAddress == null || checkWSAddressValue(wsAddress))
			throw new RuntimeException("参数不正确,请检查.");

		String wsProperty = wsAddress.getAddress() + ";" + wsAddress.getAccessFolder() + ";" + wsAddress.getPublishFolder() + ";" + wsAddress.getVisitURL();
		String wsKey = wsAddress.getWsName();

		properties.setProperty(wsKey, wsProperty);
		try {
			properties.store(new FileOutputStream(new File(Defines.CONFIG_FILE_PATH)), null);
		} catch (IOException e) {
			throw new RuntimeException("属性配置存储失败，请检查.");
		}
		return true;
	}
	/**
	 *  移除webService配置
	 * @param key
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @author leigq
	 * @create 2010-11-12 上午10:03:51
	 */
	public boolean removeWSAddress(String key){
		try {
			properties.remove(key);
			properties.store(new FileOutputStream(new File(Defines.CONFIG_FILE_PATH)), "");
			return true;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("配置文件未找到，请检查.");
		} catch (IOException e) {
			throw new RuntimeException("文件存储失败，请检查.");
		}
		
		
	}
	

	/**
	 * 检测参数是否正确,任何错误或空值都抛异常
	 * @param wsAddress
	 * @return leigq
	 * @author leigq
	 * @create 2010-11-12 上午10:03:38
	 */
	private boolean checkWSAddressValue(WSAddress wsAddress) {
		String wsName = wsAddress.getWsName();
		if (wsName == null || wsName.trim().length() == 0)
			throw new RuntimeException("参数: wsName为空,请检查.");
		
		String publishFolder = wsAddress.getPublishFolder();
		if (publishFolder == null || publishFolder.trim().length() == 0 || publishFolder.indexOf(";") != -1)
			throw new RuntimeException("参数: publishFolder为空或包含非法字符:';',请检查.");
		
		String address = wsAddress.getAddress();
		if (address == null || address.trim().length() == 0 || address.indexOf(";") != -1)
			throw new RuntimeException("参数: wsAddress为空或包含非法字符:';',请检查.");
		
		String accessFolder = wsAddress.getAccessFolder();
		if (accessFolder == null || accessFolder.trim().length() == 0 || accessFolder.indexOf(";") != -1)
			throw new RuntimeException("参数: accessFolder为空或包含非法字符:';',请检查.");
		String visitURL = wsAddress.getVisitURL();
		if (visitURL == null || visitURL.trim().length() == 0 || visitURL.indexOf(";") != -1)
			throw new RuntimeException("参数: visitURL为空或包含非法字符:';',请检查.");
		return false;
	}
}

