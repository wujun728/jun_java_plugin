package com.osmp.web.system.httpproxy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class HttpUtils {
	private static final String defaultContentEncoding = "UTF-8";

	/**
	 * 发�?GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @return
	 * @throws IOException
	 */
	public static final HttpResponse sendGet(String urlString) throws IOException {
		return send(urlString, "GET", null, null);
	}

	/**
	 * 发�?GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @return
	 * @throws IOException
	 */
	public static final HttpResponse sendGet(String urlString, Map<String, String> params) throws IOException {
		return send(urlString, "GET", params, null);
	}

	/**
	 * 发�?GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @param propertys
	 *            请求属�?
	 * @return
	 * @throws IOException
	 */
	public static final HttpResponse sendGet(String urlString, Map<String, String> params, Map<String, String> propertys)
			throws IOException {
		return send(urlString, "GET", params, propertys);
	}

	/**
	 * 发�?POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @return
	 * @throws IOException
	 */
	public static final HttpResponse sendPost(String urlString) throws IOException {
		return send(urlString, "POST", null, null);
	}

	/**
	 * 发�?POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @return
	 * @throws IOException
	 */
	public static final HttpResponse sendPost(String urlString, Map<String, String> params) throws IOException {
		return send(urlString, "POST", params, null);
	}

	/**
	 * 发�?POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @param propertys
	 *            请求属�?
	 * @return
	 * @throws IOException
	 */
	public static final HttpResponse sendPost(String urlString, Map<String, String> params,
			Map<String, String> propertys) throws IOException {
		return send(urlString, "POST", params, propertys);
	}

	/**
	 * 发�?HTTP请求
	 * 
	 * @param urlString
	 *            地址
	 * @param method
	 *            get/post
	 * @param parameters
	 *            添加由键值对指定的请求参�?
	 * @param propertys
	 *            添加由键值对指定的一般请求属�?
	 * @return HttpResponse
	 * @throws IOException
	 */
	private static final HttpResponse send(String urlString, String method, Map<String, String> parameters,
			Map<String, String> propertys) throws IOException {
		HttpURLConnection urlConnection = null;
		if (method.equalsIgnoreCase("GET") && parameters != null) {
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (String key : parameters.keySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append(parameters.get(key));
				i++;
			}
			urlString += param;
		}
		URL url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setConnectTimeout(20000);
		urlConnection.setReadTimeout(20000);
		urlConnection.setRequestMethod(method);
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);
		// urlConnection.setRequestProperty("authName", "008ltwo");
		// urlConnection.setRequestProperty("authPwd", "lt#123");
		if (propertys != null && propertys.size() > 0) {
			for (String key : propertys.keySet()) {
				urlConnection.addRequestProperty(key, propertys.get(key));
			}
		}
		if (method.equalsIgnoreCase("POST") && parameters != null) {
			StringBuffer param = new StringBuffer();
			for (String key : parameters.keySet()) {
				param.append("&");
				param.append(key).append("=").append(parameters.get(key));
			}
			urlConnection.getOutputStream().write(param.toString().getBytes());
			urlConnection.getOutputStream().flush();
			urlConnection.getOutputStream().close();
		}
		return makeContent(urlString, urlConnection);
	}

	/**
	 * 得到响应对象
	 * 
	 * @param urlConnection
	 * @return
	 * @throws IOException
	 */
	private static final HttpResponse makeContent(String urlString, HttpURLConnection urlConnection) throws IOException {
		HttpResponse response = new HttpResponse();
		try {
			InputStream in = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			response.setContentCollection(new Vector<String>());
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				response.getContentCollection().add(line);
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
			String ecod = urlConnection.getContentEncoding();
			if (ecod == null)
				ecod = defaultContentEncoding;
			response.setUrlString(urlString);
			response.setHost(urlConnection.getURL().getHost());
			response.setPath(urlConnection.getURL().getPath());
			response.setPort(urlConnection.getURL().getPort());
			response.setProtocol(urlConnection.getURL().getProtocol());
			response.setQuery(urlConnection.getURL().getQuery());
			response.setContent(new String(temp.toString().getBytes(), ecod));
			response.setContentEncoding(ecod);
			response.setCode(urlConnection.getResponseCode());
			response.setMessage(urlConnection.getResponseMessage());
			response.setContentType(urlConnection.getContentType());
			response.setMethod(urlConnection.getRequestMethod());
			response.setConnectTimeout(urlConnection.getConnectTimeout());
			response.setReadTimeout(urlConnection.getReadTimeout());
			return response;
		} catch (IOException e) {
			throw e;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
	}

	public static void main(String[] args) {
		// String url =
		// "http://10.34.41.89:8080/PayagentCallback/addresult.html";
		// Map<String, String> params = new HashMap<String,String>();
		// params.put("", "");
		for (int i = 0; i < 1; i++) {

			String url = "http://localhost:8181/cxf/http/service/data/olquery";
			Map<String, String> params = new HashMap<String, String>();
			params.put("source", "{\"from\":\"olweb\"}");
			params.put(
					"parameter",
					"{\"_search\":\"false\",\"dtBegTime\":\"2015-02-12 00:00:00\",\"dtEndTime\":\"2015-02-13 23:59\",\"dtTime\":\"2015-02-12 00:00 - 2015-02-13+23:59\",\"filter\":\"++\",\"flag\":\"SL\",\"i\":\"0.8028927557170391\",\"nd\":\"1423828496789\",\"page\":\"1\",\"rows\":\"30\",\"sCardID\":\"\",\"sCardId\":\"\",\"sDis\":\"\",\"sIDCard\":\"\",\"sName\":\"\",\"sNbid\":\"zjmola1,zjmola2,zjmola3,zjmola4,zjmola5,@_-123,OL�Ų���01,OL�Ų���02,zjmola11\",\"sidx\":\"dtTime\",\"sord\":\"desc\",\"sql\":\"osmp.ol.UP_Get_tVIPList_Count\"}");
			try {
				HttpResponse h = HttpUtils.sendGet(url, params);
				System.out.println(h.getContent());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private HttpUtils() {
	}
}
