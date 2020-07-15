package com.itheima.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Service {
	public void methdo1() throws FileNotFoundException, IOException{
		//--在没有ServletContext的环境下,如果想要读取资源,可以使用类加载器以加载类的方式加载资源,
		//		这里要注意,类加载器从哪个目录加载类,就从哪个目录加载资源,
		//		所以此处的路径一定要给一个相对于类加载目录的路径
		Properties prop = new Properties();
		prop.load(new FileReader(Service.class.getClassLoader().getResource("../../../config.properties").getPath()));
		System.out.println(prop.getProperty("username"));
		System.out.println(prop.getProperty("password"));
	}
}
