//package com.jun.plugin.common.config;
//
//import cn.hutool.extra.spring.SpringUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.web.context.WebServerInitializedEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.net.Inet4Address;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
///**
// * @Description 通过实现ApplicationListener接口动态获取tomcat启动端口和访问路径，通过InetAddress类获取主机的ip地址，最后控制台打印项目访问地址
// **/
//@Component
//@Slf4j
//public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
//
//    @Override
//    public void onApplicationEvent(WebServerInitializedEvent event) {
//        try {
//            InetAddress inetAddress = Inet4Address.getLocalHost();
//            String hostAddress = inetAddress.getHostAddress();
//            int serverPort = event.getWebServer().getPort();
//            String serverPath = event.getApplicationContext().getApplicationName();
//            log.info("项目启动成功！访问地址: http://{}:{}{}", hostAddress, serverPort, serverPath);
//            log.info("本机地址: http://localhost:{}{}", serverPort, serverPath);
//
//        	Environment env = SpringUtil.getApplicationContext().getEnvironment();
//            log.info("\n----------------------------------------------------------\n\t" +
//                          "SpringbootApplication '{}' is running! Access URLs:\n\t" +
//                          "Login: \thttp://{}:{}/\n\t" +
//                          "----------------------------------------------------------",
//                  env.getProperty("spring.application.name"),
//                  InetAddress.getLocalHost().getHostAddress(),
//                  env.getProperty("server.port"),
//                  InetAddress.getLocalHost().getHostAddress(),
//                  env.getProperty("server.port"));
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//
//}
