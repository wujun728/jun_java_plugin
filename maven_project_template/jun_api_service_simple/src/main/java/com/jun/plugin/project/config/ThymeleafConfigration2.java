package com.jun.plugin.project.config;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfigration2 {

    @Bean
    public SpringResourceTemplateResolver firstTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        //templateResolver.setPrefix("classpath:/templates/");

        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
            //file:/data/github/testmanagement/target/testmanagement-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!
            System.out.println(path.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //server.tomcat.basedir=outsidefile/jacococoverage
        String outside_templates=path.getParentFile().getParentFile().getParent()+File.separator;
        //String outside_templates=path.getParentFile().getParentFile().getParent()+File.separator+"outsidefile"+File.separator+"jacococoverage"+File.separator;

        System.out.println(outside_templates);
        outside_templates=outside_templates.substring(5,outside_templates.length());
        //file:/data/github/testmanagement/target/outsidefile/jacococoverage/

        System.out.println("new outside_templates is "+outside_templates);
        templateResolver.setPrefix("file://"+outside_templates);

        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(0);
        templateResolver.setCheckExistence(true);
        //Spring Boot中Thymeleaf引擎动态刷新
        templateResolver.setCacheable(false);
        return templateResolver;
    }

//    @Bean
//    public ClassLoaderTemplateResolver secondaryTemplateResolver() {
//        ClassLoaderTemplateResolver secondaryTemplateResolver = new ClassLoaderTemplateResolver();
//        secondaryTemplateResolver.setPrefix("templates2/");
//        secondaryTemplateResolver.setSuffix(".html");
//        secondaryTemplateResolver.setTemplateMode(TemplateMode.HTML);
//        secondaryTemplateResolver.setCharacterEncoding("UTF-8");
//        secondaryTemplateResolver.setOrder(1);
//        secondaryTemplateResolver.setCacheable(false);
//        secondaryTemplateResolver.setCheckExistence(true);
//        return secondaryTemplateResolver;
//    }
    @Bean
    public ClassLoaderTemplateResolver secondaryTemplateResolver3() {
        ClassLoaderTemplateResolver secondaryTemplateResolver = new ClassLoaderTemplateResolver();
        secondaryTemplateResolver.setPrefix("templates3/");
        secondaryTemplateResolver.setSuffix(".html");
        secondaryTemplateResolver.setTemplateMode(TemplateMode.HTML);
        secondaryTemplateResolver.setCharacterEncoding("UTF-8");
        secondaryTemplateResolver.setOrder(1);
        secondaryTemplateResolver.setCacheable(false);
        secondaryTemplateResolver.setCheckExistence(true);
        return secondaryTemplateResolver;
    }
}