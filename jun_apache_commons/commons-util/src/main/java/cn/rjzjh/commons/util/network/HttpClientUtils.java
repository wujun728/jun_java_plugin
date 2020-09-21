package cn.rjzjh.commons.util.network;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import cn.rjzjh.commons.util.exception.ExceptAll;
import cn.rjzjh.commons.util.exception.ProjectException;

public abstract class HttpClientUtils {
	protected static Log log = LogFactory.getLog(HttpClientUtils.class);

	/**
	 * 发送Get请求
	 * 
	 * @param url
	 *            发送请求地址
	 * @return 返回内容
	 * */
	public static String sendGet(String url, String ecode)
			throws ProjectException {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		InputStream in = null;
		try {
			HttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				entity = new BufferedHttpEntity(entity);
				in = entity.getContent();
				byte[] read = new byte[1024];
				byte[] all = new byte[0];
				int num;
				while ((num = in.read(read)) > 0) {
					byte[] temp = new byte[all.length + num];
					System.arraycopy(all, 0, temp, 0, all.length);
					System.arraycopy(read, 0, temp, all.length, num);
					all = temp;
				}
				result = new String(all, ecode);
			}
		} catch (Exception e) {
			log.error("客户端连接错误。");
			throw new ProjectException(ExceptAll.net_clienterror, "客户端连接错误");
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					log.error("关闭流错误。");
					throw new ProjectException(ExceptAll.net_streamclose,
							"关闭流错误");
				}
			get.abort();
		}
		return result;
	}

	public static String sendGet(String url) throws ProjectException {
		return sendGet(url, "utf-8");
	}

	/**
	 * 发送带参数的Get请求
	 * 
	 * @param url
	 *            发送请求地址
	 * @param params
	 *            发送请求的参数
	 * @return 返回内容
	 * */
	public static String sendGet(String url, Map<String, String> params)
			throws ProjectException {
		Set<String> keys = params.keySet();
		StringBuilder urlBuilder = new StringBuilder(url + "?");
		for (String key : keys) {
			urlBuilder.append(key).append("=").append(params.get(key))
					.append("&");
		}
		urlBuilder.delete(urlBuilder.length() - 1, urlBuilder.length());
		return sendGet(urlBuilder.toString());
	}

	/**
	 * 发送带参数的post请求
	 * 
	 * @param url
	 *            发送请求地址
	 * @param params
	 *            发送请求的参数
	 * @return 返回内容
	 * */
	public static String sendPost(String url, Map<String, String> params)
			throws ProjectException {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost get = new HttpPost(url);
		// 创建表单参数列表
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		Set<String> keys = params.keySet();
		for (String key : keys) {
			qparams.add(new BasicNameValuePair(key, params.get(key)));
		}
		try {
			// 填充表单
			get.setEntity(new UrlEncodedFormEntity(qparams, "utf-8"));

			HttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				entity = new BufferedHttpEntity(entity);

				InputStream in = entity.getContent();
				byte[] read = new byte[1024];
				byte[] all = new byte[0];
				int num;
				while ((num = in.read(read)) > 0) {
					byte[] temp = new byte[all.length + num];
					System.arraycopy(all, 0, temp, 0, all.length);
					System.arraycopy(read, 0, temp, all.length, num);
					all = temp;
				}
				result = new String(all, "UTF-8");
				if (null != in) {
					in.close();
				}
			}
			get.abort();

			return result;
		} catch (Exception e) {
			throw new ProjectException(ExceptAll.default_Project, "请求错误");
		}

	}

}
