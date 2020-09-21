/*   
 * Project: OSMP
 * FileName: DefaultCxfClient.java
 * version: V1.0
 */
package com.osmp.http.client.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import com.osmp.http.client.CxfClient;
import com.osmp.http.client.define.ResponseData;
import com.osmp.http.client.exception.CxfClientException;
import com.osmp.http.client.util.JSONUtils;
import com.osmp.http.client.zookeeper.ZookeeperService;

/**
 * 默認cxf客戶端
 * 
 * @author heyu
 *
 */
public class DefaultCxfClient implements CxfClient {

	private final static Logger log = Logger.getLogger(DefaultCxfClient.class);

	/**
	 * http接口地址url
	 */
	private String cxfServerUrl;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 建立连接超时时间
	 */
	private long connectTimeout = 10000;
	/**
	 * 通讯超时时间
	 */
	private long socketTimeout = 10000;

	public void setCxfServerUrl(String cxfServerUrl) {
		this.cxfServerUrl = cxfServerUrl;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setConnectTimeout(long connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public void setSocketTimeout(long socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	
	public static Map<String, WebClient> map = new ConcurrentHashMap<String,WebClient>();

	private WebClient getWebClient(String service) throws Exception {
		if (cxfServerUrl == null || "".equals(cxfServerUrl)) {
			throw new CxfClientException("CxfClient cxfServerUrl未设置");
		}
		if (projectName == null || "".equals(projectName)) {
			throw new CxfClientException("CxfClient projectName未设置");
		}
		
		String ip = ZookeeperService.getServiceAdd(service.replaceAll("/", ""));
		if(null == ip || ip.trim().length() == 0){
			throw new Exception("can not find service : " + service + " in zookeeper regeister!!!");
		}
		WebClient webClient = DefaultCxfClient.map.get(ip);

		if (webClient == null) {
			synchronized (DefaultCxfClient.class) {
				if (webClient == null) {
					List<Object> providerList = new ArrayList<Object>();
					JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
					ObjectMapper mapper = new ObjectMapper();
					mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);
					jsonProvider.setMapper(mapper);
					providerList.add(jsonProvider);
					webClient = WebClient.create(cxfServerUrl.replaceAll("IP", ip), providerList, true);
					WebClient.getConfig(webClient).getHttpConduit().getClient().setConnectionTimeout(connectTimeout);
					WebClient.getConfig(webClient).getHttpConduit().getClient().setReceiveTimeout(socketTimeout);
					DefaultCxfClient.map.put(ip, webClient);
				}
			}
		}
		webClient.reset();
		return webClient;
	}

	private <T> List<T> _get(String path, Object parameter, Class<T> clz, boolean isList) throws Exception {
		Response res = getWebClient(path).accept(MediaType.APPLICATION_JSON + ";charset=UTF-8").encoding("UTF-8")
				.replaceQueryParam("source", "{\"from\":\"" + projectName + "\"}")
				.replaceQueryParam("parameter", JSONUtils.object2JsonString(parameter).replaceAll("%", "%25"))
				.path(path).get();
		log.debug("Cxf Client send get request: " + path + "?source=" + "{\"from\":\"" + projectName + "\"}&parameter="
				+ JSONUtils.object2JsonString(parameter).replaceAll("%", "%25"));
		if (res == null)
			return null;

		InputStream in = (InputStream) res.getEntity();
		String resString = null;
		try {
			byte[] bytes = IOUtils.readBytesFromStream(in);
			resString = IOUtils.newStringFromBytes(bytes, "UTF-8");
		} catch (IOException e) {
			throw new CxfClientException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new CxfClientException(e);
				}
			}
		}
		log.debug("Cxf Client send get response: ");
		log.debug(resString);
		ResponseData resData = JSONUtils.jsonString2Bean(resString, ResponseData.class);
		if (resData == null)
			return null;
		if (!"0".equals(resData.getCode())) {
			throw new CxfClientException(resData.getMessage());
		}

		String dataStr = resData.getData();

		if (dataStr == null || "".equals(dataStr)) {
			return isList ? new ArrayList<T>() : null;
		}

		List<T> retList = null;

		if (isList) {
			return JSONUtils.jsonString2List(dataStr, clz);
		}

		retList = new ArrayList<T>();
		T item = JSONUtils.jsonString2Bean(dataStr, clz);
		if (item == null)
			return null;
		retList.add(item);
		return retList;
	}

	private <T> List<T> _post(String path, Object parameter, Class<T> clz, boolean isList) throws Exception {
		StringBuffer paraStr = new StringBuffer();
		paraStr.append("source={\"from\":\"" + projectName + "\"}");
		paraStr.append("&parameter=").append(JSONUtils.object2JsonString(parameter).replaceAll("%", "%25"));
		Response res = getWebClient(path).type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON + ";charset=UTF-8").encoding("UTF-8").path(path)
				.post(paraStr.toString());

		log.debug("Cxf Client send post request: " + path + "?source=" + "{\"from\":\"" + projectName
				+ "\"}&parameter=" + JSONUtils.object2JsonString(parameter).replaceAll("%", "%25"));
		if (res == null)
			return null;

		InputStream in = (InputStream) res.getEntity();
		String resString = null;
		try {
			byte[] bytes = IOUtils.readBytesFromStream(in);
			resString = IOUtils.newStringFromBytes(bytes, "UTF-8");
		} catch (IOException e) {
			throw new CxfClientException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new CxfClientException(e);
				}
			}
		}
		log.debug("Cxf Client send post response: ");
		log.debug(resString);
		ResponseData resData = JSONUtils.jsonString2Bean(resString, ResponseData.class);
		if (resData == null)
			return null;
		if (!"0".equals(resData.getCode())) {
			throw new CxfClientException(resData.getMessage());
		}

		String dataStr = resData.getData();

		if (dataStr == null || "".equals(dataStr)) {
			return isList ? new ArrayList<T>() : null;
		}

		List<T> retList = null;

		if (isList) {
			return JSONUtils.jsonString2List(dataStr, clz);
		}

		retList = new ArrayList<T>();
		T item = JSONUtils.jsonString2Bean(dataStr, clz);
		if (item == null)
			return null;
		retList.add(item);
		return retList;
	}

	@Override
	public <T> T getForObject(String path, Object parameter, Class<T> clz) throws Exception {
		List<T> list = _get(path, parameter, clz, false);
		if (list == null)
			return null;
		return list.get(0);
	}

	@Override
	public <T> List<T> getForList(String path, Object parameter, Class<T> clz) throws Exception {
		return _get(path, parameter, clz, true);
	}

	@Override
	public <T> T postForObject(String path, Object parameter, Class<T> clz) throws Exception {
		List<T> list = _post(path, parameter, clz, false);
		if (list == null)
			return null;
		return list.get(0);
	}

	@Override
	public <T> List<T> postForList(String path, Object parameter, Class<T> clz) throws Exception {
		return _post(path, parameter, clz, true);
	}

}
