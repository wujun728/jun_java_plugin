package cc.mrbird.febs.common.annotation;

import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.meta.TypeQualifierNickname;
import java.lang.annotation.*;

/**
 * @author MrBird
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootApplication
@TypeQualifierNickname
@EnableAsync
@EnableTransactionManagement
public @interface FebsShiro {

    @AliasFor(
            annotation = EnableAutoConfiguration.class
    )
    Class<?>[] exclude() default {};

    @AliasFor(
            annotation = EnableAutoConfiguration.class
    )
    String[] excludeName() default {};

    @AliasFor(
            annotation = ComponentScan.class,
            attribute = "basePackages"
    )
    String[] scanBasePackages() default {};

    @AliasFor(
            annotation = ComponentScan.class,
            attribute = "basePackageClasses"
    )
    Class<?>[] scanBasePackageClasses() default {};

    @AliasFor(
            annotation = ComponentScan.class,
            attribute = "nameGenerator"
    )
    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    @AliasFor(
            annotation = Configuration.class
    )
    boolean proxyBeanMethods() default true;
}
