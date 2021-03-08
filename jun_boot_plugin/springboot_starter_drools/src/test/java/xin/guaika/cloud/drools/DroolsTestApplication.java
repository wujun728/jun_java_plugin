package xin.guaika.cloud.drools;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

import xin.guaika.cloud.drools.configuration.DroolsAutoConfiguration;

/**
 * Created by homeant on 2017/8/10.
 */
@EnableAutoConfiguration
@Import(DroolsAutoConfiguration.class)
public class DroolsTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(DroolsTestApplication.class, args);
    }
}
