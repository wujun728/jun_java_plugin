/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.test.httpclient.FirstHttpclient.java
 * Class:			FirstHttpclient
 * Date:			2012-12-16
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.learn.test.httpclient;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-12-16 下午2:12:32 
 */

public class FirstHttpclient {

	/**  
	 * 描述
	 * @param args  
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://www.scjj.gov.cn:8635/");
		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		HttpEntity httpEntity = httpResponse.getEntity();
		InputStream in = null;
		
		if (httpEntity != null) {
			in = httpEntity.getContent();
		}
		
		int r = 0;
		byte[] buffer = new byte[2048];
		while ((r = in.read(buffer)) != -1) {
			System.out.print(new String(buffer));
			buffer = new byte[2048];
		}
		
		in.close();
	}

}
