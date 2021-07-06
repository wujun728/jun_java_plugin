package com.huan.study.product;

import com.huan.study.product.properties.ConditionProperties;
import com.huan.study.product.properties.ProductProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author huan.fu 2020/11/18 - 20:51
 */
@Slf4j
@RequiredArgsConstructor
@RefreshScope
@RestController
public class PrintPropertiesTask {

    private final ProductProperties productProperties;

    @Autowired(required = false)
    private ConditionProperties conditionProperties;

    @Value("${taskName}")
    private String taskName;

    @Autowired
    private Environment environment;

    @GetMapping("/printProperties")
    public void printProperties() {
        log.info("=====> taskName:[{}]", taskName);
        log.info("=====> taskName-env:[{}]", environment.getProperty("taskName"));
        log.info("productProperties:[{}]", productProperties);
        log.info("conditionProperties:[{}]", conditionProperties);
        log.info("========================================");
    }

    @PostConstruct
    public void initPrint() {
        new Thread(() -> {
            while (true){
                log.info("=====> taskName:[{}]", taskName);
                log.info("=====> taskName-env:[{}]", environment.getProperty("taskName"));
                log.info("productProperties:[{}]", productProperties);
                log.info("conditionProperties:[{}]", conditionProperties);
                log.info("========================================");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
