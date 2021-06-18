package net.dreamlu.weixin.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

class WebUtils {
    private static final Log logger = LogFactory.getLog(WebUtils.class);
    /**
     * 返回json
     *
     * @param response HttpServletResponse
     * @param text     文本
     */
    public static void renderText(HttpServletResponse response, String text) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset:utf-8;");
        try (PrintWriter out = response.getWriter()) {
            out.append(text);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
