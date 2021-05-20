package com.ssm.dubbo.util;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author Wujun
 * @project_name ssm-dubbo
 * @date 2019-01-02
 */
public class ResponseUtil {

    public static void write(HttpServletResponse response, Object o) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        out.println(o.toString());
        out.flush();
        out.close();
    }
}
