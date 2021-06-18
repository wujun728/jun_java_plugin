package com.monkeyk.sos.web.context;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;

/**
 * 2018/10/14
 * <p>
 * <p>
 * 扩展 Spring Context, 方便获取 bean
 *
 * @author Wujun
 * @since 1.0
 */
public class SOSContextLoaderListener extends ContextLoaderListener {


    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        //ext
        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
        BeanProvider.initialize(applicationContext);
    }
}
