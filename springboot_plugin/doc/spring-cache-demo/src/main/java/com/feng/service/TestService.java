package com.feng.service;

public interface TestService {
	
	String defaultCache(String name);
	
	String guavaCache60seconds(String name);
	String guavaCache10minutes(String name);
	String guavaCache1hour(String name);
	
	String redisCache60seconds(String name);
	String redisCache10minutes(String name);
	String redisCache1hour(String name);
}
