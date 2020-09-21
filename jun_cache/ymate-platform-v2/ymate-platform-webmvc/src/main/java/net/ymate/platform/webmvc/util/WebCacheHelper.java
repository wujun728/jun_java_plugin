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
package net.ymate.platform.webmvc.util;

import net.ymate.platform.cache.ICaches;
import net.ymate.platform.core.lang.PairObject;
import net.ymate.platform.core.util.DateTimeUtils;
import net.ymate.platform.webmvc.PageMeta;
import net.ymate.platform.webmvc.base.Type;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 刘镇 (suninformation@163.com) on 16/3/28 下午7:56
 * @version 1.0
 */
public class WebCacheHelper {

    private static final int EMPTY_GZIPPED_CONTENT_SIZE = 20;

    private HttpServletRequest __request;

    private HttpServletResponse __response;

    private PageMeta __element;

    private ICaches.Scope __scope;

    private WebCacheHelper(HttpServletRequest request, HttpServletResponse response, PageMeta pageMeta, ICaches.Scope scope) {
        __request = request;
        __response = response;
        __element = pageMeta;
        __scope = scope;
        //
        if (ICaches.Scope.DEFAULT.equals(__scope)) {
            for (final Iterator<Map.Entry<String, PairObject<Type.HeaderType, Object>>> _headerIt = __element.getHeaders().entrySet().iterator(); _headerIt.hasNext(); ) {
                final Map.Entry<String, PairObject<Type.HeaderType, Object>> _header = _headerIt.next();
                if ("Last-Modified".equalsIgnoreCase(_header.getKey()) ||
                        "Expires".equalsIgnoreCase(_header.getKey()) ||
                        "Cache-Control".equalsIgnoreCase(_header.getKey()) ||
                        "ETag".equalsIgnoreCase(_header.getKey())) {
                    _headerIt.remove();
                }
            }
            long _lastModified = __element.getLastUpdateTime();
            _lastModified = TimeUnit.MILLISECONDS.toSeconds(_lastModified);
            _lastModified = TimeUnit.SECONDS.toMillis(_lastModified);
            //
            long _expiresTime = System.currentTimeMillis() + __element.getTimeout();
            //
            __element.getHeaders().put("Last-Modified", new PairObject<Type.HeaderType, Object>(Type.HeaderType.DATE, _lastModified));
            __element.getHeaders().put("Expires", new PairObject<Type.HeaderType, Object>(Type.HeaderType.DATE, _expiresTime));
            __element.getHeaders().put("Cache-Control", new PairObject<Type.HeaderType, Object>(Type.HeaderType.STRING, "max-age=" + __element.getTimeout()));
            __element.getHeaders().put("ETag", new PairObject<Type.HeaderType, Object>(Type.HeaderType.STRING, __doGenerateEtag(__element.getTimeout())));
        }
    }

    private String __doGenerateEtag(long ttlMilliseconds) {
        StringBuilder stringBuffer = new StringBuilder();
        Long eTagRaw = System.currentTimeMillis() + ttlMilliseconds;
        return stringBuffer.append("\"").append(eTagRaw).append("\"").toString();
    }

    public static WebCacheHelper bind(HttpServletRequest request, HttpServletResponse response, PageMeta pageMeta, ICaches.Scope scope) {
        return new WebCacheHelper(request, response, pageMeta, scope);
    }

    public void writeResponse() throws Exception {
        if (ICaches.Scope.DEFAULT.equals(__scope)) {
            for (final Map.Entry<String, PairObject<Type.HeaderType, Object>> _header : __element.getHeaders().entrySet()) {
                if ("ETag".equals(_header.getKey())) {
                    String _ifNoneMatch = __request.getHeader("If-None-Match");
                    if (_header.getValue() != null && _header.getValue().getValue().equals(_ifNoneMatch)) {
                        __response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
                        return;
                    }
                    break;
                }
                if (_header.getValue().getValue() != null && "Last-Modified".equals(_header.getKey())) {
                    long _ifModifiedSince = __request.getDateHeader("If-Modified-Since");
                    if (_ifModifiedSince != -1) {
                        final Date _requestDate = new Date(_ifModifiedSince);
                        final Date _pageDate;
                        switch (_header.getValue().getKey()) {
                            case STRING:
                                _pageDate = DateTimeUtils.parseDateTime((String) _header.getValue().getValue(), "EEE, dd MMM yyyy HH:mm:ss z", "0");
                                break;
                            case DATE:
                                _pageDate = new Date((Long) _header.getValue().getValue());
                                break;
                            default:
                                throw new IllegalArgumentException("Header " + _header + " is not supported as type: " + _header.getValue().getKey());
                        }
                        if (!_requestDate.before(_pageDate)) {
                            __response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
                            __response.setHeader("Last-Modified", __request.getHeader("If-Modified-Since"));
                            return;
                        }
                    }
                }
            }
        }
        //
        __response.setContentType(__element.getContentType());
        __doSetHeaders();
        //
        byte[] _body;
        if (__element.isStoreGzipped() && StringUtils.contains(__request.getHeader("Accept-Encoding"), "gzip")) {
            _body = __element.getGzippedBody();
            if (_body.length == EMPTY_GZIPPED_CONTENT_SIZE) {
                _body = new byte[0];
            } else {
                __response.setHeader("Content-Encoding", "gzip");
            }
        } else {
            _body = __element.getUngzippedBody();
        }
        __response.setContentLength(_body.length);
        OutputStream out = new BufferedOutputStream(__response.getOutputStream());
        out.write(_body);
        out.flush();
    }

    private void __doSetHeaders() {
        for (final Map.Entry<String, PairObject<Type.HeaderType, Object>> _header : __element.getHeaders().entrySet()) {
            final String name = _header.getKey();
            if (_header.getValue() != null) {
                switch (_header.getValue().getKey()) {
                    case STRING:
                        if (__response.containsHeader(name)) {
                            __response.addHeader(name, (String) _header.getValue().getValue());
                        } else {
                            __response.setHeader(name, (String) _header.getValue().getValue());
                        }
                        break;
                    case DATE:
                        if (__response.containsHeader(name)) {
                            __response.addDateHeader(name, (Long) _header.getValue().getValue());
                        } else {
                            __response.setDateHeader(name, (Long) _header.getValue().getValue());
                        }
                        break;
                    case INT:
                        if (__response.containsHeader(name)) {
                            __response.addIntHeader(name, (Integer) _header.getValue().getValue());
                        } else {
                            __response.setIntHeader(name, (Integer) _header.getValue().getValue());
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("No mapping for Header: " + _header.getValue().getKey());
                }
            }
        }
    }
}
