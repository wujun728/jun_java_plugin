package com.test.client.directcamel2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;

public class HttpClient {

	public static final String CODEFORMAT = "UTF-8";

	public static String doPost(String requestBody, int timeout, HttpURLConnection http) throws Exception {
		String retResult = "";
		try {
			// 设置是否从HttpURLConnection读入 ，默认是true
			http.setDoInput(true);
			// post请求的时候需要将DoOutput 设置成true 参数要放在http正文内，因此需要设为true ，默认是false
			http.setDoOutput(true);
			// post请求UseCaches 设置成false 不能设置缓存
			http.setUseCaches(false);
			// 连接主机超时时间
			http.setConnectTimeout(timeout);
			// 从主机读取数据超时 （都是毫秒）
			http.setReadTimeout(timeout);
			// 默认是get
			http.setRequestMethod("POST");
			http.setRequestProperty("accept", "*/*");
			http.setRequestProperty("connection", "Keep-Alive");
			http.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 参数设置完成之后进行连接
			http.connect();
			OutputStreamWriter osw = new OutputStreamWriter(http.getOutputStream(), Charset.forName("UTF-8"));
			osw.write(requestBody);
			osw.flush();
			osw.close();
			if (http.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), Charset.forName("UTF-8")));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					retResult += inputLine;
				}
				in.close();
			} else {
				throw new Exception("the http.getResponseCode() is " + http.getResponseCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (http != null) {
				http.disconnect();
				http = null;
			}
		}
		return retResult;
	}
}
