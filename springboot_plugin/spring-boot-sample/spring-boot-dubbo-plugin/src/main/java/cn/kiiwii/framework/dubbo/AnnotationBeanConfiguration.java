package cn.kiiwii.framework.dubbo;

import com.alibaba.dubbo.config.spring.AnnotationBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by zhong on 2017/3/13.
 */
@Configuration
@ConditionalOnMissingClass
@PropertySource(value = "classpath:/application.properties")
public class AnnotationBeanConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AnnotationBean annotationBean(@Value("${dubbo.annotation.package-name}") String packageName) {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage(packageName);
        return annotationBean;
    }
}
