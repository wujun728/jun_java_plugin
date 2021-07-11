package com.cl.search.utils;

import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SolrUtil {

	@Autowired
	private ConfigUtil configUtil;
	
	public HttpSolrServer getSolrHttpConnect() {
		
		HttpSolrServer ss = new HttpSolrServer(configUtil.getCasServerUrl());
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
