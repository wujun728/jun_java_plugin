package com.mall.admin.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * @author Wujun
 * @version 1.0
 * @create_at 2018/11/27 20:17
 */
@Component
@Slf4j
public class ApiInterceptor extends BaseInterceptor {

    private static final String title = "\nSpringMvc" + " action report -------- ";
    private static int maxOutputLengthOfParaValue = 512;

    private static final ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //解决ajax跨域问题
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.addHeader("Access-Control-Allow-Methods", "*");
//        response.addHeader("Access-Control-Allow-Headers", "*");
//        response.addHeader("Access-Control-Max-Age", "1800");//30 分钟
//        //允许客户端发送cookies true表示接收，false不接受 默认为false？
//        response.addHeader("Access-Control-Allow-Credentials","true");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        String contentPath = request.getServletContext().getContextPath();
        int contentPathLength = contentPath.length();
        String target = request.getRequestURI();
        if (contentPathLength != 0) {
            target = target.substring(contentPathLength);
        }
        StringBuilder sb = new StringBuilder(title).append(sdf.get().format(new Date())).append(" --------------------------\n");
        sb.append("Url          : ").append(request.getMethod()).append(" ").append(target).append("\n");

        if (handler instanceof HandlerMethod) {
            sb.append("Controller   : ").append(((HandlerMethod) handler).getBean().getClass()).append("\n");
            sb.append("Method       : ").append(((HandlerMethod) handler).getMethod().getName()).append("\n");
        }

        if (modelAndView != null)
            sb.append("ViewName     : ").append(modelAndView.getViewName()).append("\n");

        Enumeration<String> e = request.getParameterNames();
        if (e.hasMoreElements()) {
            sb.append("Parameter    : ");
            while (e.hasMoreElements()) {
                String name = e.nextElement();
                String[] values = request.getParameterValues(name);
                if (values.length == 1) {
                    sb.append(name).append("=");
                    if (values[0] != null && values[0].length() > maxOutputLengthOfParaValue) {
                        sb.append(values[0].substring(0, maxOutputLengthOfParaValue)).append("...");
                    } else {
                        sb.append(values[0]);
                    }
                }
                else {
                    sb.append(name).append("[]={");
                    for (int i=0; i<values.length; i++) {
                        if (i > 0)
                            sb.append(",");
                        sb.append(values[i]);
                    }
                    sb.append("}");
                }
                sb.append("  ");
            }
            sb.append("\n");
        }
        sb.append("--------------------------------------------------------------------------------\n");
        log.info(sb.toString());
    }
}
