package com.luo.util;

import java.net.MalformedURLException;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

public class SolrContext {
	private static final String url="http://localhost:8080/solr";
	private static CommonsHttpSolrServer server=null;
	
	
	static{
		try {
			server=new CommonsHttpSolrServer(url);//创建solrServer
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public static SolrServer getServer(){
		return server;
	}
}
