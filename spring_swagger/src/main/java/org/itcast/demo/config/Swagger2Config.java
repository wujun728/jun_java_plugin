package org.itcast.demo.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description  Swagger2配置类 通过@Configuration注解，让spring来加载该配置 再通过@EnableSwagger2注解来启动Swagger2
 * 一定要jdk1.8，不然跑不起swagger
 * @auther: CDHong
 * @date: 2019/6/25-10:14
 **/
//@Configuration
@WebAppConfiguration
@EnableSwagger2
@EnableWebMvc //非springboot框架需要引入且需要在配置文件中配置该类的Bean对象
@ComponentScan(basePackages = {"org.itcast.demo.controller"}) //扫描包
public class Swagger2Config {
    /**
     * 创建API应用
     * appinfo()增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例，用来控制那些接口暴露给Swagger来展现
     * 本例采用置顶扫描的包路径来定义指定要建立API的目录
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))// 对所有api进行监控
            .paths(PathSelectors.any())// 对所有路径进行监控
            .build();
    }


    /**
     * 创建改API的基本信息（这些基本信息会展示在文档页面中）
     * 访问地址： http://项目实际地址/swagger-ui.html
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("简单的SSM框架搭建练习案例")
            .description("使用MyBatisPlus与Swagger来梳理一下知识点")
            .termsOfServiceUrl("http://localhost:80/swagger-ui.html")// 将“url”换成自己的ip:port
            .version("1.0")
            .build();
    }

}
