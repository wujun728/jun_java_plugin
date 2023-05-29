package com.jun.plugin.util4j.net.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class TrustAnyHostnameVerifier implements HostnameVerifier {
	
	public boolean verify(String hostname, SSLSession session) {
		return true;
	}
}