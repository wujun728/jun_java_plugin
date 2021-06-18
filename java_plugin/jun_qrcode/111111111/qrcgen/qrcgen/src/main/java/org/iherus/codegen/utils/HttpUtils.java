package org.iherus.codegen.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

public class HttpUtils {

	private HttpUtils() {

	}

	public static byte[] readStreamToByteArray(final String url) {
		HttpURLConnection connection = null;
		InputStream ins = null;
		ByteArrayOutputStream ous = null;
		try {
			URL i = new URL(url);
			connection = (HttpURLConnection) i.openConnection();
			connection.setUseCaches(false);
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(5000);
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			if ("HTTPS".equalsIgnoreCase(i.getProtocol())) {
				trustHttps(connection);
			}
			if (HttpStatus.SC_OK == connection.getResponseCode()) {
				ins = connection.getInputStream();
				ous = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len;
				while ((len = ins.read(buffer)) != -1) {
					ous.write(buffer, 0, len);
				}
				ous.flush();
				return ous.toByteArray();
			}
			
			return new byte[] {};
			
		} catch (Exception e) {
			throw new HttpConnectionException("Failed to obtain ByteArray by URL.", e);
		} finally {
			IOUtils.closeQuietly(ous);
			IOUtils.closeQuietly(ins);
			IOUtils.close(connection);
		}
	}

	private static void trustHttps(URLConnection connection) throws SSLException {
		if (!(connection instanceof HttpsURLConnection))
			return;
		HttpsURLConnection conn = (HttpsURLConnection) connection;
		TrustManager[] trustManagers = { new X509TrustManager() };
		try {
			SSLContext sc = SSLContext.getInstance("TLSv1.2");
			sc.init(null, trustManagers, new java.security.SecureRandom());
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new HostnameVerifier());
		} catch (Exception e) {
			throw new SSLException("Trusts HttpsURLConnection failed.", e);
		}
	}

	public final static class HostnameVerifier implements javax.net.ssl.HostnameVerifier {

		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}

	}

	public final static class X509TrustManager implements javax.net.ssl.X509TrustManager {

		@Override
		public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
				throws java.security.cert.CertificateException {

		}

		@Override
		public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
				throws java.security.cert.CertificateException {

		}

		@Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return new java.security.cert.X509Certificate[] {};
		}
	}

}
