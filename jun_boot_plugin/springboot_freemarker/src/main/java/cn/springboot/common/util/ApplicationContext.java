package cn.springboot.common.util;

import java.util.Calendar;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

/**
 * 
 * @Description 将version版本号写入application中，给css,js引用时用
 * @author Wujun
 * @date Oct 9, 2016 8:39:46 PM
 */
@Component
public class ApplicationContext implements ServletContextAware {

    private static final Logger log = LoggerFactory.getLogger(ApplicationContext.class);

    /**
     * 
     * <p>初始化到Application作用域当中</p> 
     * @param context 
     * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
     */
    @Override
    public void setServletContext(ServletContext context) {
        String datetime = DateUtil.dateToString(Calendar.getInstance().getTime(), DateUtil.fm_yyyyMMddHHmmssSSS);
        String contextPath = context.getContextPath();
        log.info("# version={} , contextPath={}", datetime, contextPath);
        context.setAttribute("version_css", datetime);
        context.setAttribute("version_js", datetime);
        context.setAttribute("ctx", contextPath);
    }

}
