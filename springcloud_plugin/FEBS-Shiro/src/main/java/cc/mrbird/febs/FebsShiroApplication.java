package cc.mrbird.febs;

import cc.mrbird.febs.common.annotation.FebsShiro;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author MrBird
 */
@FebsShiro
public class FebsShiroApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FebsShiroApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
