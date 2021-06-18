package com.jun.plugin.demo;

import java.io.File;
import java.net.URL;

public class DemoLoaderAll {
	public static void main(String[] args) throws Exception {
		URL url = DemoLoaderAll.class.getClassLoader().getResource("cn/itcast/demo");
		System.err.println(url.getPath());
		File file = new File(url.getPath());
		String[] fs = file.list();
		for(String f:fs){
			System.err.println(f);
		}
	}
}
