package io.itit.itf.okhttp;

import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.itit.itf.okhttp.ssl.X509TrustManagerImpl;
import okhttp3.OkHttpClient;

/**
 * 
 * @author icecooly
 *
 */
public class FastHttpClient {
	//
	public static Logger logger = LoggerFactory.getLogger(FastHttpClient.class);
	//
	public static OkHttpClient okHttpClient=getDefaultOkHttpClient();
	//
	public static OkHttpClient getDefaultOkHttpClient() {
		OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
		final X509TrustManager trustManager=new X509TrustManagerImpl();
		SSLSocketFactory sslSocketFactory=null;
		try {
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[] { trustManager }, new SecureRandom());
			sslSocketFactory = sslContext.getSocketFactory();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return builder.sslSocketFactory(sslSocketFactory, trustManager).hostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		}).build();
	}
	//
	public static GetBuilder get() {
		return new GetBuilder();
	}

	//
	public static PostBuilder post() {
		return new PostBuilder();
	}
}
