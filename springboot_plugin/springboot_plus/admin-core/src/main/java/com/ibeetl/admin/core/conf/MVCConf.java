package com.ibeetl.admin.core.conf;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.core.GroupTemplate;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ibeetl.admin.core.entity.CoreOrg;
import com.ibeetl.admin.core.entity.CoreUser;
import com.ibeetl.admin.core.service.CorePlatformService;
import com.ibeetl.admin.core.service.CoreUserService;
import com.ibeetl.admin.core.util.HttpRequestLocal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Configuration
public class MVCConf implements WebMvcConfigurer, InitializingBean {

    public static final String DEFAULT_APP_NAME = "开发平台";

    /**
     * 系统名称,可以在application.properties中配置
     * app.name=xxx
     */
//    @Value("${app.name}")
//    String appName;

    // 开发用的模拟当前用户和机构
    Long useId;

    Long orgId;

    String mvcTestPath;

    @Autowired
    Environment env;

    @Autowired
    CoreUserService userService;

    @Autowired
    BeetlGroupUtilConfiguration beetlGroupUtilConfiguration;

    @Autowired
    HttpRequestLocal httpRequestLocal;

    @Autowired
    GroupTemplate groupTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new SessionInterceptor(httpRequestLocal, this)).addPathPatterns("/**");
        // super.addInterceptors(registry);

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new MyDateFormatter());
//        registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.useId = env.getProperty("user.id", Long.class);
        this.orgId = env.getProperty("user.orgId", Long.class);
        this.mvcTestPath = env.getProperty("mvc.test.path");
        Map<String, Object> var = new HashMap<>(5);
        String appName =  env.getProperty("app.name");
        if(appName==null) {
        	 var.put("appName",DEFAULT_APP_NAME);
      
        }
        
        var.put("jsVer", System.currentTimeMillis());
       
       groupTemplate.setSharedVars(var);
        
        
   
       
    }


}

class MyDateFormatter implements Formatter<Date>{

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        if(text==null){
            return null;
        }
        if(text.trim().length()==0){
            return null;
        }
        if(text.length()<=10){
            String format = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(format,locale);
            return sdf.parse(text);
        }else{
            String format = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(format,locale);
            return sdf.parse(text);
        }

    }

    @Override
    public String print(Date object, Locale locale) {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format,locale);
        return sdf.format((Date)object);
    }
}

class SessionInterceptor implements HandlerInterceptor {

    MVCConf conf;
    HttpRequestLocal httpRequestLocal;

    public SessionInterceptor(HttpRequestLocal httpRequestLocal, MVCConf conf) {
        this.conf = conf;
        this.httpRequestLocal = httpRequestLocal;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (conf.useId != null && conf.orgId != null
                && request.getSession().getAttribute(CorePlatformService.ACCESS_CURRENT_USER) == null) {
            // 模拟用户登录，用于快速开发,未来用rember么代替？
            CoreUser user = conf.userService.getUserById(conf.useId);
            CoreOrg org = conf.userService.getOrgById(conf.orgId);
            List<CoreOrg> orgs = conf.userService.getUserOrg(conf.useId, org.getId());
            request.getSession().setAttribute(CorePlatformService.ACCESS_CURRENT_USER, user);
            request.getSession().setAttribute(CorePlatformService.ACCESS_CURRENT_ORG, org);
            request.getSession().setAttribute(CorePlatformService.ACCESS_USER_ORGS, orgs);
            request.getSession().setAttribute("ip", request.getRemoteHost());

        }
        httpRequestLocal.set(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // do nothing
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // do nothing
    }

}
