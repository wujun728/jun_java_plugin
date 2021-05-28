package com.mycompany.myproject;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.Arrays;
import java.util.Map;

@SpringBootApplication
// Springboot jpa提供了自动填充这两个字段的功能，简单配置一下即可。
// @CreatedDate、@LastModifiedDate、@CreatedBy、@LastModifiedBy前两个注解就是起这个作用的
@EnableJpaAuditing(auditorAwareRef = "userIDAuditorAware")
@EnableCaching
//@EnableJpaRepositories(basePackages = "com.mycompany.myproject.dao")
//@EntityScan(basePackages ={"com.mycompany.myproject.entity"})
//@EnableWebMvc
public class MyApplication implements CommandLineRunner {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job jobImportFromOpera;

    @Autowired
    Job importJob;


    public static void main(String[] args) throws Exception {

        SpringApplication.run(MyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//
//        JobParameters params = new JobParametersBuilder()
//                .addString("JobID", String.valueOf(System.currentTimeMillis()))
//                .toJobParameters();
//        //jobLauncher.run(jobImportFromOpera, params);
//
//        jobLauncher.run(importJob, params);

    }


	public class MyInnerClass{
		public String value = "MyApplication$MyInnerClass";
	}

	public static class MyStaticInnerClass {
		public String value = "MyApplication$MyStaticInnerClass";
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			Object obj = ctx.getBean("cacheManager");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

			Map<String, InstantiationAwareBeanPostProcessor> map =
					ctx.getBeansOfType(InstantiationAwareBeanPostProcessor.class );
			for(Map.Entry<String , InstantiationAwareBeanPostProcessor> item : map.entrySet()){
				String key= item.getKey();
				Object value= item.getValue();
				System.out.println("key:"+key+"  "+"value:"+value);
			}


			try {
				AbstractHandlerMethodMapping<RequestMappingInfo> objHandlerMethodMapping =
						(AbstractHandlerMethodMapping<RequestMappingInfo>)ctx
								.getBean("requestMappingHandlerMapping");
				Map<RequestMappingInfo, HandlerMethod> mapRet = objHandlerMethodMapping.getHandlerMethods();
				for(Map.Entry<RequestMappingInfo, HandlerMethod> item : mapRet.entrySet()){
					RequestMappingInfo key=item.getKey();
					Object value=item.getValue();
					System.out.println("key:"+key+"  "+"value:"+value);
				}
			}catch (Exception e) {
				// TODO: handle exception
			}

		};
	}
}
