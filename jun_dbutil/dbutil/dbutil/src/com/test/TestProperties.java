package com.test;

import com.config.PropertiesConfig;
import com.config.PropertiesService;

public class TestProperties {
	public static void main(String[] args) {
		PropertiesConfig config = PropertiesService.getApplicationConfig();
		System.out.println(config.getProperty("jdbc.url"));
	}
}
