//package self.conf;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.context.annotation.Configuration;
//import self.AdminApplication;
//
///**
// * @Description: 外置tomcat启动war项目初始化类,内置tomcat启动需要注释掉此类
// */
//@Configuration
//public class WebServletInitializerConf extends SpringBootServletInitializer {
//
//    private static final Log log = LogFactory.getLog(WebServletInitializerConf.class);
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//
//        log.info("-----------------外置tomcat启动war项目初始化-----------------");
//
//        return configureApplication(application);
//    }
//    private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder){
//        return builder.sources(AdminApplication.class);
//    }
//
//}
