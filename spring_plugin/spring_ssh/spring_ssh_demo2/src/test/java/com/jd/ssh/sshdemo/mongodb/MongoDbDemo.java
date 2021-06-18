/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.jd.ssh.sshdemo.mongodb;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;

import com.jd.ssh.sshdemo.test.spring.SpringTransactionalTestCase;

@ContextConfiguration(locations = { "/mongodb/applicationContext-mongodb.xml", "/applicationContext.xml" })
public class MongoDbDemo extends SpringTransactionalTestCase {

	//@Autowired
	//private SpyMemcachedClient spyMemcachedClient;
	@Autowired
	private MongoTemplate mongoTemplate;
	String SNAPSHOT = "SNAPSHOT";
	
	@Test
	public void normal() {
		Snapshot snapshot = new Snapshot();
		snapshot.setName("xumin");
		snapshot.setProperties("test");
		mongoTemplate.insert(snapshot, SNAPSHOT);
		
		Snapshot snapshot2 = getByName("xumin");
		
		//assertEquals(snapshot,snapshot2);
		System.out.println(snapshot2.getProperties());
	}

	
	public Snapshot getByName(String name){
		return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), Snapshot.class, SNAPSHOT);
	}
}
