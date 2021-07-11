package com.luo.job;

import org.springframework.beans.factory.annotation.Autowired;

import com.luo.service.LuceneService;

public class LuceneSyncJob {
	@Autowired
	private LuceneService luceneService;
	
	
	public void execute(){
		luceneService.createIndex();
	}
}
