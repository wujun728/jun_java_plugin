package com.buxiaoxia.enableDemo.config;

import com.buxiaoxia.enableDemo.bean.Demo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xw on 2017/3/24.
 * 2017-03-24 20:38
 */
@Configuration
public class CustomAnnotation {


	@Bean
	public Demo demo(){
		return new Demo();
	}


}
