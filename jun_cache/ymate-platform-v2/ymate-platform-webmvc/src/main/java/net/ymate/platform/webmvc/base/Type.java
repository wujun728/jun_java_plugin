/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.webmvc.base;

import net.ymate.platform.webmvc.IRequestMappingParser;
import net.ymate.platform.webmvc.IRequestProcessor;
import net.ymate.platform.webmvc.impl.DefaultRequestProcessor;
import net.ymate.platform.webmvc.impl.JSONRequestProcessor;
import net.ymate.platform.webmvc.impl.XMLRequestProcessor;
import net.ymate.platform.webmvc.support.RequestMappingParser;
import net.ymate.platform.webmvc.support.RestfulRequestMappingParser;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据常量/枚举类型
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/19 上午1:56
 * @version 1.0
 */
public class Type {

    /**
     * HTTP请求方式枚举
     * <p>
     * Create At 2012-12-10 下午10:34:44
     * </p>
     */
    public enum HttpMethod {
        GET, HEAD, POST, PUT, DELETE, OPTIONS, TRACE
    }

    /**
     * HTTP请求头数据类型
     */
    public enum HeaderType {
        STRING, INT, DATE
    }

    /**
     * 字符串参数转义范围
     */
    public enum EscapeScope {
        JAVA, JS, HTML, XML, SQL, CSV, DEFAULT
    }

    /**
     * 执行字符串参数转义顺序
     */
    public enum EscapeOrder {
        BEFORE, AFTER
    }

    /**
     * 视图类型枚举
     * <p>
     * Create At 2015-6-4 上午08:01:45
     * </p>
     */
    public enum View {
        BINARY, FORWARD, FREEMARKER, VELOCITY, HTML, HTTP_STATES, JSON, JSP, NULL, REDIRECT, TEXT
    }

    public enum ContentType {

        TEXT("text/plain"),
        HTML("text/html"),
        JSON("application/json"),
        JAVASCRIPT("text/javascript"),

        /**
         * 字节流
         */
        OCTET_STREAM("application/octet-stream");

        private String __contentType;

        ContentType(String contentType) {
            __contentType = contentType;
        }

        public String getContentType() {
            return __contentType;
        }

        public String toString() {
            return __contentType;
        }
    }

    public static class Context {

        public static final String SESSION = "net.ymate.platform.webmvc.context.SESSION";

        public static final String APPLICATION = "net.ymate.platform.webmvc.context.APPLICATION";

        public static final String REQUEST = "net.ymate.platform.webmvc.context.REQUEST";

        public static final String PARAMETERS = "net.ymate.platform.webmvc.context.PARAMETERS";

        public static final String LOCALE = "net.ymate.platform.webmvc.context.LOCALE";

        public static final String HTTP_REQUEST = "net.ymate.platform.webmvc.context.HTTP_SERVLET_REQUEST";

        public static final String HTTP_RESPONSE = "net.ymate.platform.webmvc.context.HTTP_SERVLET_RESPONSE";

        public static final String SERVLET_CONTEXT = "net.ymate.platform.webmvc.context.SERVLET_CONTEXT";

        public static final String PAGE_CONTEXT = "net.ymate.platform.webmvc.context.PAGE_CONTEXT";

        public static final String WEB_REQUEST_CONTEXT = "net.ymate.platform.webmvc.context.WEB_REQUEST_CONTEXT";

        public static final String WEB_CONTEXT_OWNER = "net.ymate.platform.webmvc.context.WEB_CONTEXT_OWNER";
    }

    public final static Map<String, Class<? extends IRequestProcessor>> REQUEST_PROCESSORS;

    public final static Map<String, Class<? extends IRequestMappingParser>> REQUEST_MAPPING_PARSERS;

    static {
        REQUEST_PROCESSORS = new HashMap<String, Class<? extends IRequestProcessor>>();
        REQUEST_PROCESSORS.put("default", DefaultRequestProcessor.class);
        REQUEST_PROCESSORS.put("json", JSONRequestProcessor.class);
        REQUEST_PROCESSORS.put("xml", XMLRequestProcessor.class);
        //
        REQUEST_MAPPING_PARSERS = new HashMap<String, Class<? extends IRequestMappingParser>>();
        REQUEST_MAPPING_PARSERS.put("default", RequestMappingParser.class);
        REQUEST_MAPPING_PARSERS.put("restful", RestfulRequestMappingParser.class);
    }
}
