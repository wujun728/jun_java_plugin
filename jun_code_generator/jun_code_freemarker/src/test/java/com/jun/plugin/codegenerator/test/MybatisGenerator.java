package com.jun.plugin.codegenerator.test;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MybatisGenerator {
	public static void main(String[] args) {
		Boolean ovr = true;
//		File file = new File("D:\\workspace\\github\\jun_code_generator\\jun_code_mybatis\\src\\main\\resources\\mybatis-generator-config.xml");
		try {
			InputStream in = MybatisGenerator.class.getClassLoader().getResourceAsStream("generatorConfig.xml");
			List<String> war = new ArrayList<String>();
	        ConfigurationParser cp = new ConfigurationParser(war);
	        Configuration config = cp.parseConfiguration(in);
//			Configuration config = cp.parseConfiguration(file);
			DefaultShellCallback back = new DefaultShellCallback(ovr);
			MyBatisGenerator my = new MyBatisGenerator(config, back, war);
			my.generate(null);
			for (String s : war) {
				System.out.println(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}