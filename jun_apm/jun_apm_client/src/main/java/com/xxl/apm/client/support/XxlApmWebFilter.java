package com.xxl.apm.client.support;

import com.xxl.apm.client.XxlApm;
import com.xxl.apm.client.message.impl.XxlApmTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xuxueli 2019-01-17
 */
public class XxlApmWebFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(XxlApmWebFilter.class);

    public static final String PARENT_MSGID = "xxl_apm_parent_msgid";
    public static final String EXCLUDE_URLS = "excluded_urls";

    private String excludedUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludedUrls = filterConfig.getInitParameter(EXCLUDE_URLS);

        logger.info(">>>>>>>>>>> xxl-apm, XxlApmWebFilter init.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // pass
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();

        // pass
        if (excludedUrls != null && excludedUrls.contains(path)) {
            chain.doFilter(request, response);
            return;
        }

        // transaction
        XxlApmTransaction transaction = new XxlApmTransaction("URL", path);
        try {
            chain.doFilter(request, response);
        } catch (Throwable e) {
            transaction.setStatus(e);
            XxlApm.report(e);
            throw e;
        } finally {

            // set parentMsgId, support trance
            String parentMsgId = req.getHeader(PARENT_MSGID);
            if (parentMsgId!=null && parentMsgId.trim().length()>0) {
                XxlApm.setParentMsgId(parentMsgId);
            }

            // do report
            XxlApm.report(transaction);

            // set parentMsgId
            if (parentMsgId!=null && parentMsgId.trim().length()>0) {
                XxlApm.removeParentMsgId();
            }
        }

    }

    @Override
    public void destroy() {
        logger.info(">>>>>>>>>>> xxl-apm, XxlApmWebFilter destroy.");
    }


}
