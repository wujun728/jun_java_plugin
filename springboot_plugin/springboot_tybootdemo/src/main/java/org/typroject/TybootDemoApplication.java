package org.typroject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by magintursh on 2017-05-03.
 */


@MapperScan({"org.typroject.tyboot.demo.face.orm.dao*",
        "org.typroject.tyboot.face.*.orm.dao*",
        "org.typroject.tyboot.core.auth.face.orm.dao*",
        "org.typroject.tyboot.component.*.face.orm.dao*"})
@SpringBootApplication
public class TybootDemoApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(TybootDemoApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
