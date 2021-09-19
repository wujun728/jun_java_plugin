package com.jun.plugin.solr.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigUtil {
	
	private @Value("${solrUrl}")String solrUrl;
	
	public String getCasServerUrl() {
		return solrUrl;
	}
	
}
