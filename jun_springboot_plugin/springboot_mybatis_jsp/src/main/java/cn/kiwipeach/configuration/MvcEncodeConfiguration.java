package cn.kiwipeach.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Create Date: 2017/11/05
 * Description: 解决SpringBoot配置文件*.properties中文乱码问题
 * 更多内容参考：http://blog.csdn.net/wangshuang1631/article/details/70753801
 *
 * @author Wujun
 */
@Configuration
public class MvcEncodeConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private StringHttpMessageConverter converter;

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(converter);//保证引用的为同一个实例
//        converters.add(responseBodyConverter());
    }
}
