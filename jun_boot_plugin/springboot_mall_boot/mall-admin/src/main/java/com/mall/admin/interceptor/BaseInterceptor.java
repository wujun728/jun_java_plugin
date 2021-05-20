package com.mall.admin.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author Wujun
 * @version 1.0
 * @create_at 2018/12/22 19:43
 */
public abstract class BaseInterceptor implements HandlerInterceptor {

    private static final String contentType = "application/json; charset=utf-8";
    private static final String contentTypeForIE = "text/html; charset=utf-8";
    private boolean forIE = false;

    protected void renderJson(HttpServletResponse response, Map resultMap) {
        String dataJson = JSONObject.toJSONString(resultMap);
        PrintWriter writer = null;
        try {
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType(forIE ? contentTypeForIE : contentType);
            writer = response.getWriter();
            writer.write(dataJson);
            writer.flush();
            return;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取json 参数值
     * @param request
     * @return
     */
    public String readData(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            StringBuilder ret;
            br = request.getReader();

            String line = br.readLine();
            if (line != null) {
                ret = new StringBuilder();
                ret.append(line);
            } else {
                return "";
            }

            while ((line = br.readLine()) != null) {
                ret.append('\n').append(line);
            }
            return ret.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
