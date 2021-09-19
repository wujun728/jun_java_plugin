package com.jun.plugin.solr.util;

import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrServer;


public class SolrUtil {

	private static String solrUrl = PropertyFile.getProps().getProperty("solrUrl");
	
	public static HttpSolrServer getSolrHttpConnect() {
		
		HttpSolrServer ss = new HttpSolrServer(solrUrl);
		ss.setSoTimeout(500000);
		ss.setConnectionTimeout(500000);
		ss.setDefaultMaxConnectionsPerHost(1000);
		ss.setMaxTotalConnections(1000);
		ss.setFollowRedirects(false);
		ss.setAllowCompression(true);
		ss.setRequestWriter(new BinaryRequestWriter());
		ss.setMaxRetries(5);
		return ss;
	}
	
}
