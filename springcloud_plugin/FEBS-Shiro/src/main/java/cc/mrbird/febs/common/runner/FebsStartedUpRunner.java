package cc.mrbird.febs.common.runner;

import cc.mrbird.febs.common.properties.FebsProperties;
import cc.mrbird.febs.common.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @author MrBird
 * @author FiseTch
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FebsStartedUpRunner implements ApplicationRunner {

    private final ConfigurableApplicationContext context;
    private final FebsProperties febsProperties;

    @Value("${server.port:8080}")
    private String port;
    @Value("${server.servlet.context-path:}")
    private String contextPath;
    @Value("${spring.application.package-time:'1970-01-01T00:00:00Z'}")
    private String packageTime;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (context.isActive()) {
            InetAddress address = InetAddress.getLocalHost();
            String url = String.format("http://%s:%s", address.getHostAddress(), port);
            String loginUrl = febsProperties.getShiro().getLoginUrl();
            if (StringUtils.isNotBlank(contextPath)) {
                url += contextPath;
            }
            if (StringUtils.isNotBlank(loginUrl)) {
                url += loginUrl;
            }
            log.info(" __    ___   _      ___   _     ____ _____  ____ ");
            log.info("/ /`  / / \\ | |\\/| | |_) | |   | |_   | |  | |_  ");
            log.info("\\_\\_, \\_\\_/ |_|  | |_|   |_|__ |_|__  |_|  |_|__ ");
            log.info("                                                      ");
            log.info("FEBS权限系统启动完毕，系统编译打包时间：{}，地址：{}", DateUtil.formatUtcTime(packageTime), url);
        }
    }
}
