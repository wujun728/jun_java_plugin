package cc.mrbird.febs.common.xss;

import cc.mrbird.febs.common.entity.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Xss攻击拦截器
 *
 * @author MrBird
 */
@Slf4j
public class XssFilter implements Filter {

    private final List<String> excludes = new ArrayList<>();
    /**
     * 是否过滤富文本内容
     */
    private boolean flag = false;

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("------------ xss filter init ------------");
        String isIncludeRichText = filterConfig.getInitParameter("isIncludeRichText");
        if (StringUtils.isNotBlank(isIncludeRichText)) {
            flag = BooleanUtils.toBoolean(isIncludeRichText);
        }
        String temp = filterConfig.getInitParameter("excludes");
        if (temp != null) {
            String[] url = StringUtils.split(temp, Strings.COMMA);
            excludes.addAll(Arrays.asList(url));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (handleExcludeUrl(req)) {
            chain.doFilter(request, response);
            return;
        }
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request, flag);
        chain.doFilter(xssRequest, response);
    }

    @Override
    public void destroy() {
        // do nothing
    }

    private boolean handleExcludeUrl(HttpServletRequest request) {
        if (excludes.isEmpty()) {
            return false;
        }
        String url = request.getServletPath();
        return excludes.stream().map(pattern -> Pattern.compile("^" + pattern)).map(p -> p.matcher(url)).anyMatch(Matcher::find);
    }

}
