package com.andaily.springoauth.web;

import net.sf.json.JSON;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Wujun
 */
public abstract class WebUtils {


    private WebUtils() {
    }


    /*
     *  Save state to ServletContext, key = value = state
     */
    public static void saveState(HttpServletRequest request, String state) {
        final ServletContext servletContext = request.getSession().getServletContext();
        servletContext.setAttribute(state, state);
    }

    /*
     *  Validate state when callback from Oauth Server.
     *  If validation successful, will remove it from ServletContext.
     */
    public static boolean validateState(HttpServletRequest request, String state) {
        if (StringUtils.isEmpty(state)) {
            return false;
        }
        final ServletContext servletContext = request.getSession().getServletContext();
        final Object value = servletContext.getAttribute(state);

        if (value != null) {
            servletContext.removeAttribute(state);
            return true;
        }
        return false;
    }


    public static void writeJson(HttpServletResponse response, JSON json) {
        response.setContentType("application/json;charset=UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            json.write(writer);
            writer.flush();
        } catch (IOException e) {
            throw new IllegalStateException("Write json to response error", e);
        }

    }

}