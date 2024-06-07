package com.jun.plugin.rest.filter;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.http.ContentType;
import cn.hutool.log.Log;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * Servlet Filter implementation class ResourceFilter
 */
@WebFilter(urlPatterns = "*")
public class ResourceFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletRequest respone = (HttpServletRequest) arg0;
        String url = request.getRequestURI();
        Log.get().info("ResourceFilter URL:"+url);
        if (url.startsWith("/jun-groovy-api/")) {
            String filename = url.substring("/jun-groovy-api/".length());
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("template/" + filename);
            if(in!=null){
                try {
                    if(url.contains(".html") || url.contains(".htm")){
                        arg1.setContentType(ContentType.TEXT_HTML.getValue());
                    }/*else{
                        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
                        String mimeType = mimetypesFileTypeMap.getContentType(FileUtil.getName(url));
                        arg1.setContentType(mimeType);
                    }*/
                    IOUtils.copy(in, arg1.getOutputStream());
                } finally {
                    IOUtils.closeQuietly(in);
                }
            }else{
                Log.get().error("error url:"+url);
            }
        } else {
            arg2.doFilter(arg0, arg1);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

}
