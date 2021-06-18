package self.conf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

/**
 * 监听Spring Boot的生命周期
 */
@SuppressWarnings("rawtypes")
@Component
public class ApplicationEventListener implements ApplicationListener {

    private static final Log log = LogFactory.getLog(ApplicationEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        /**
         * 初始化环境变量
         */
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
        	log.info("------------------------------------应用初始化环境变量------------------------------------------------------------");
        }
        /**
         * 初始化完成
         */
        else if (event instanceof ApplicationPreparedEvent) {
        	log.info("------------------------------------应用初始化完成------------------------------------------------------------");
        }
        /**
         * 应用刷新
         */
        else if (event instanceof ContextRefreshedEvent) {
        	log.info("------------------------------------应用刷新-----------------------------------------------------------");
        }
        /**
         * 应用已启动完成
         */
        else if (event instanceof ApplicationReadyEvent) {
        	log.info("------------------------------------应用启动完成------------------------------------------------------------");
        }
        /**
         * 应用启动，需要在代码动态添加监听器才可捕获
         */
        else if (event instanceof ContextStartedEvent) {
        	log.info("------------------------------------应用启动------------------------------------------------------------");
        }
        /**
         * 应用停止
         */
        else if (event instanceof ContextStoppedEvent) {
        	log.info("------------------------------------应用停止------------------------------------------------------------");
        }
        /**
         * 应用关闭
         */
        else if (event instanceof ContextClosedEvent) {
            log.info("------------------------------------应用关闭------------------------------------------------------------");
            
        }
    }

}