package cn.springboot.common.authority.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @Description xss参数防注入
 * @author Wujun
 * @date Mar 24, 2017 7:42:45 PM  
 */
@WebFilter(urlPatterns = "/*", filterName = "XSSCheck", initParams = { @WebInitParam(name = "securityconfig", value = "/*") })
@SuppressWarnings("all")
public class XSSCheckFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(XSSCheckFilter.class);

    @SuppressWarnings("unused")
    private FilterConfig config;
    // 出错跳转的目的地
    private static String errorPath;
    // 不进行拦截的url
    private static String[] excludePaths;
    // 需要拦截的JS字符关键字
    private static String[] safeless;

    /******************** xss攻击防注入参数 begin ************************/
    // 出错跳转的目的地
    public final static String XSS_ERROR_PATH = "/WEB-INF/views/common/error.jsp";
    // 不进行拦截的url
    public final static String XSS_EXCLUDE_PATHS = "";
    // 需要拦截的JS字符关键字
    public final static String XSS_SAFELESS = "<script, </script, <iframe, </iframe, <frame, </frame, set-cookie, %3cscript, %3c/script, %3ciframe, %3c/iframe, %3cframe, %3c/frame, src=\"javascript:, <body, </body, %3cbody, %3c/body, <, >, </, />, %3c, %3e, %3c/, /%3e";

    /******************** xss攻击防注入参数 end ************************/

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        Enumeration params = req.getParameterNames();
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        boolean isSafe = true;
        String requestUrl = request.getRequestURI();
        if (isSafe(requestUrl)) {
            requestUrl = requestUrl.substring(requestUrl.indexOf("/"));
            if (!excludeUrl(requestUrl)) {
                while (params.hasMoreElements()) {
                    String cache = req.getParameter((String) params.nextElement());
                    if (!"".equals(cache) && null != cache) {
                        if (!isSafe(cache)) {
                            isSafe = false;
                            break;
                        }
                    }
                }
            }
        } else {
            isSafe = false;
        }
        if (!isSafe) {
            request.setAttribute("err", "您输入的参数有非法字符，请输入正确的参数！");
            request.getRequestDispatcher(errorPath).forward(request, response);
            return;
        }
        filterChain.doFilter(req, resp);
    }

    private static boolean isSafe(String str) {
        if (!"".equals(str) && null != str) {
            for (String s : safeless) {
                if (str.toLowerCase().contains(s))
                    return false;
            }
        }
        return true;
    }

    private boolean excludeUrl(String url) {
        if (excludePaths != null && excludePaths.length > 0) {
            for (String path : excludePaths) {
                if (url.toLowerCase().equals(path))
                    return true;
            }
        }
        return false;
    }

    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        try {
            errorPath = XSS_ERROR_PATH;
            String excludePath = XSS_EXCLUDE_PATHS;
            if (!"".equals(excludePath) && null != excludePath)
                excludePaths = excludePath.split(",");
            String safeles = XSS_SAFELESS;
            if (!"".equals(safeles) && null != safeles) {
                safeless = safeles.split(",");
                log.debug(safeless.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
