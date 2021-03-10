/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.test.httpclient.HttpclientTest.java
 * Class:			HttpclientTest
 * Date:			2012-12-16
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.learn.test.httpclient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2012-12-16 下午3:05:53 
 */

public class HttpclientTest {
	/**
	 * 测试uri构造
	 * 描述
	 * @throws URISyntaxException
	 */
	@Test
	public void testURiUtils() throws URISyntaxException {
		URI uri = URIUtils.createURI("http", "www.google.com", -1, "/search",  
			    "q=httpclient&btnG=Google+Search&aq=f&oq=", null);

		Assert.assertEquals("http://www.google.com/search?q=httpclient&btnG=Google+Search&aq=f&oq=", uri.toString());
		
		List<NameValuePair> qparams = new ArrayList<NameValuePair>(); 
		qparams.add(new BasicNameValuePair("q", "httpclient")); 
		qparams.add(new BasicNameValuePair("btnG", "Google Search")); 
		qparams.add(new BasicNameValuePair("aq", "f")); 
		qparams.add(new BasicNameValuePair("oq", null)); 
		URI uri2 = URIUtils.createURI("http", "www.google.com", -1, "/search",  
		    URLEncodedUtils.format(qparams, "UTF-8"), null); 
	}
	
	/**
	 * 测试response的状态
	 * 描述
	 */
	@Test
	public void testHttpResponse() {
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK"); 
		System.out.println(response.getProtocolVersion()); 
		System.out.println(response.getStatusLine().getStatusCode()); 
		System.out.println(response.getStatusLine().getReasonPhrase()); 
		System.out.println(response.getStatusLine().toString()); 
	}
	
	/**
	 * 测试Header
	 * 描述
	 */
	@Test
	public void testHeader() {
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK"); 
		response.addHeader("Set-Cookie", "c1=a; path=/; domain=localhost"); 
		response.addHeader("Set-Cookie", "c2=b; path=\"/\", c3=c; domain=\"localhost\""); 
		Header h1 = response.getFirstHeader("Set-Cookie"); 
		System.out.println(h1); 
		Header h2 = response.getLastHeader("Set-Cookie"); 
		System.out.println(h2); 
		Header[] hs = response.getHeaders("Set-Cookie"); 
		System.out.println(hs.length); 
	}
}
