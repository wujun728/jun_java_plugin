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
package net.ymate.platform.webmvc;

import com.alibaba.fastjson.JSON;
import net.ymate.platform.core.Version;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.beans.BeanMeta;
import net.ymate.platform.core.lang.PairObject;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.annotation.Module;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.webmvc.annotation.*;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.handle.ControllerHandler;
import net.ymate.platform.webmvc.handle.InterceptorRuleHandler;
import net.ymate.platform.webmvc.impl.DefaultInterceptorRuleProcessor;
import net.ymate.platform.webmvc.impl.DefaultModuleCfg;
import net.ymate.platform.webmvc.impl.NullWebCacheProcessor;
import net.ymate.platform.webmvc.support.GenericResponseWrapper;
import net.ymate.platform.webmvc.support.MultipartRequestWrapper;
import net.ymate.platform.webmvc.support.RequestExecutor;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.View;
import net.ymate.platform.webmvc.view.impl.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * MVC框架管理器
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-7 下午10:23:39
 * @version 1.0
 */
@Module
public class WebMVC implements IModule, IWebMvc {

    public static final Version VERSION = new Version(2, 0, 3, WebMVC.class.getPackage().getImplementationVersion(), Version.VersionType.Release);

    private static final Log _LOG = LogFactory.getLog(WebMVC.class);

    private static volatile IWebMvc __instance;

    private YMP __owner;

    private IWebMvcModuleCfg __moduleCfg;

    private boolean __inited;

    private IInterceptorRuleProcessor __interceptorRuleProcessor;

    /**
     * @return 返回默认MVC框架管理器实例对象
     */
    public static IWebMvc get() {
        if (__instance == null) {
            synchronized (VERSION) {
                if (__instance == null) {
                    __instance = YMP.get().getModule(WebMVC.class);
                }
            }
        }
        return __instance;
    }

    /**
     * @param owner YMP框架管理器实例
     * @return 返回指定YMP框架管理器容器内的MVC框架管理器实例
     */
    public static IWebMvc get(YMP owner) {
        return owner.getModule(WebMVC.class);
    }

    public String getName() {
        return IWebMvc.MODULE_NAME;
    }

    public void init(YMP owner) throws Exception {
        if (!__inited) {
            //
            _LOG.info("Initializing ymate-platform-webmvc-" + VERSION);
            //
            __owner = owner;
            __moduleCfg = new DefaultModuleCfg(owner);
            __owner.getEvents().registerEvent(WebEvent.class);
            __owner.registerHandler(Controller.class, new ControllerHandler(this));
            //
            if (__moduleCfg.getErrorProcessor() instanceof IWebInitializable) {
                ((IWebInitializable) __moduleCfg.getErrorProcessor()).init(this);
            }
            if (__moduleCfg.isConventionInterceptorMode()) {
                __interceptorRuleProcessor = new DefaultInterceptorRuleProcessor();
                __interceptorRuleProcessor.init(this);
                __owner.registerHandler(InterceptorRule.class, new InterceptorRuleHandler(this));
            }
            //
            __inited = true;
        }
    }

    public boolean isInited() {
        return __inited;
    }

    public void destroy() throws Exception {
        if (__inited) {
            __inited = false;
            //
            if (__moduleCfg.getErrorProcessor() instanceof IWebInitializable) {
                ((IWebInitializable) __moduleCfg.getErrorProcessor()).destroy();
            }
            //
            __owner = null;
        }
    }

    public IWebMvcModuleCfg getModuleCfg() {
        return __moduleCfg;
    }

    public YMP getOwner() {
        return __owner;
    }

    public boolean registerController(Class<?> targetClass) throws Exception {
        boolean _isValid = false;
        if (targetClass.isAnnotationPresent(Controller.class)) {
            for (Method _method : targetClass.getDeclaredMethods()) {
                if (_method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMeta _meta = new RequestMeta(this, targetClass, _method);
                    __moduleCfg.getRequestMappingParser().registerRequestMeta(_meta);
                    //
                    if (__owner.getConfig().isDevelopMode()) {
                        _LOG.debug("--> " + _meta.getAllowMethods() + ": " + _meta.getMapping() + " : " + _meta.getTargetClass().getName() + "." + _meta.getMethod().getName());
                    }
                    //
                    _isValid = true;
                }
            }
            //
            if (_isValid) {
                if (targetClass.getAnnotation(Controller.class).singleton()) {
                    __owner.registerBean(BeanMeta.create(targetClass.newInstance(), targetClass));
                } else {
                    __owner.registerBean(BeanMeta.create(targetClass));
                }
            }
        }
        return _isValid;
    }

    public boolean registerInterceptorRule(Class<? extends IInterceptorRule> targetClass) throws Exception {
        if (__interceptorRuleProcessor != null) {
            __interceptorRuleProcessor.registerInterceptorRule(targetClass);
            return true;
        }
        return false;
    }

    private IWebCacheProcessor __doGetWebCacheProcessor(ResponseCache responseCache) {
        IWebCacheProcessor _cacheProcessor = null;
        if (responseCache != null) {
            if (!NullWebCacheProcessor.class.equals(responseCache.processorClass())) {
                _cacheProcessor = ClassUtils.impl(responseCache.processorClass(), IWebCacheProcessor.class);
            }
            if (_cacheProcessor == null) {
                _cacheProcessor = getModuleCfg().getCacheProcessor();
            }
        }
        return _cacheProcessor;
    }

    public void processRequest(IRequestContext context,
                               ServletContext servletContext,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {

        StopWatch _consumeTime = null;
        long _threadId = 0;
        boolean _devMode = __owner.getConfig().isDevelopMode();
        try {
            if (_devMode && _LOG.isDebugEnabled()) {
                _threadId = Thread.currentThread().getId();
                _consumeTime = new StopWatch();
                _consumeTime.start();
                //
                _LOG.debug("--> [" + _threadId + "] Process request start: " + context.getHttpMethod() + ":" + context.getRequestMapping());
                _LOG.debug("--- [" + _threadId + "] Parameters: " + JSON.toJSONString(request.getParameterMap()));
            }
            //
            RequestMeta _meta = __moduleCfg.getRequestMappingParser().doParse(context);
            if (_meta != null) {
                //
                if (_devMode && _LOG.isDebugEnabled()) {
                    _LOG.debug("--- [" + _threadId + "] Request mode: controller");
                }
                // 先判断当前请求方式是否允许
                if (_meta.allowHttpMethod(context.getHttpMethod())) {
                    // 判断允许的请求头
                    Map<String, String> _allowMap = _meta.getAllowHeaders();
                    for (Map.Entry<String, String> _entry : _allowMap.entrySet()) {
                        String _value = WebContext.getRequest().getHeader(_entry.getKey());
                        if (StringUtils.trimToEmpty(_entry.getValue()).equals("*")) {
                            if (StringUtils.isBlank(_value)) {
                                if (_devMode && _LOG.isDebugEnabled()) {
                                    _LOG.debug("--- [" + _threadId + "] Check request allowed: NO");
                                }
                                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                                return;
                            }
                        } else {
                            if (_value == null || !_value.equalsIgnoreCase(_entry.getValue())) {
                                if (_devMode && _LOG.isDebugEnabled()) {
                                    _LOG.debug("--- [" + _threadId + "] Check request allowed: NO");
                                }
                                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                                return;
                            }
                        }
                    }
                    // 判断允许的请求参数
                    _allowMap = _meta.getAllowParams();
                    for (Map.Entry<String, String> _entry : _allowMap.entrySet()) {
                        if (StringUtils.trimToEmpty(_entry.getValue()).equals("*")) {
                            if (!WebContext.getRequest().getParameterMap().containsKey(_entry.getKey())) {
                                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                                //
                                if (_devMode && _LOG.isDebugEnabled()) {
                                    _LOG.debug("--- [" + _threadId + "] Check request allowed: NO");
                                }
                                return;
                            }
                        } else {
                            String _value = WebContext.getRequest().getParameter(_entry.getKey());
                            if (_value == null || !_value.equalsIgnoreCase(_entry.getValue())) {
                                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                                //
                                if (_devMode && _LOG.isDebugEnabled()) {
                                    _LOG.debug("--- [" + _threadId + "] Check request allowed: NO");
                                }
                                return;
                            }
                        }
                    }
                    // 判断是否需要处理文件上传
                    if (context.getHttpMethod().equals(Type.HttpMethod.POST) && _meta.getMethod().isAnnotationPresent(FileUpload.class)) {
                        if (!(request instanceof IMultipartRequestWrapper)) {
                            // 避免重复处理
                            request = new MultipartRequestWrapper(this, request);
                        }
                        //
                        if (_devMode && _LOG.isDebugEnabled()) {
                            _LOG.debug("--- [" + _threadId + "] Include file upload: YES");
                        }
                    }
                    WebContext.getContext().addAttribute(Type.Context.HTTP_REQUEST, request);
                    //
                    IWebCacheProcessor _cacheProcessor = __doGetWebCacheProcessor(_meta.getResponseCache());
                    IView _view = null;
                    // 首先判断是否可以使用缓存
                    if (_cacheProcessor != null) {
                        // 尝试从缓存中加载执行结果
                        if (_cacheProcessor.processResponseCache(this, _meta.getResponseCache(), context, null)) {
                            // 加载成功, 则
                            _view = View.nullView();
                            //
                            if (_devMode && _LOG.isDebugEnabled()) {
                                _LOG.debug("--- [" + _threadId + "] Load data from the cache: YES");
                            }
                        }
                    }
                    if (_view == null) {
                        _view = RequestExecutor.bind(this, _meta).execute();
                        if (_view != null) {
                            if (_cacheProcessor != null) {
                                try {
                                    // 生成缓存
                                    if (_cacheProcessor.processResponseCache(this, _meta.getResponseCache(), context, _view)) {
                                        _view = View.nullView();
                                        //
                                        if (_devMode && _LOG.isDebugEnabled()) {
                                            _LOG.debug("--- [" + _threadId + "] Results data cached: YES");
                                        }
                                    }
                                } catch (Exception e) {
                                    // 缓存处理过程中的任何异常都不能影响本交请求的正常响应, 仅输出异常日志
                                    _LOG.warn(e.getMessage(), RuntimeUtils.unwrapThrow(e));
                                }
                            }
                            _view.render();
                        } else {
                            HttpStatusView.NOT_FOUND.render();
                        }
                    } else {
                        _view.render();
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                }
            } else if (__moduleCfg.isConventionMode()) {
                boolean _isAllowConvention = true;
                if (!__moduleCfg.getConventionViewNotAllowPaths().isEmpty()) {
                    for (String _vPath : __moduleCfg.getConventionViewNotAllowPaths()) {
                        if (context.getRequestMapping().startsWith(_vPath)) {
                            _isAllowConvention = false;
                            break;
                        }
                    }
                }
                if (_isAllowConvention && !__moduleCfg.getConventionViewAllowPaths().isEmpty()) {
                    _isAllowConvention = false;
                    for (String _vPath : __moduleCfg.getConventionViewAllowPaths()) {
                        if (context.getRequestMapping().startsWith(_vPath)) {
                            _isAllowConvention = true;
                            break;
                        }
                    }
                }
                if (_isAllowConvention) {
                    //
                    if (_devMode && _LOG.isDebugEnabled()) {
                        _LOG.debug("--- [" + _threadId + "] Request mode: convention");
                    }
                    //
                    IView _view = null;
                    ResponseCache _responseCache = null;
                    if (__interceptorRuleProcessor != null) {
                        // 尝试执行Convention拦截规则
                        PairObject<IView, ResponseCache> _result = __interceptorRuleProcessor.processRequest(this, context);
                        _view = _result.getKey();
                        _responseCache = _result.getValue();
                    }
                    // 判断是否可以使用缓存
                    IWebCacheProcessor _cacheProcessor = __doGetWebCacheProcessor(_responseCache);
                    // 首先判断是否可以使用缓存
                    if (_cacheProcessor != null) {
                        // 尝试从缓存中加载执行结果
                        if (_cacheProcessor.processResponseCache(this, _responseCache, context, null)) {
                            // 加载成功, 则
                            _view = View.nullView();
                            //
                            if (_devMode && _LOG.isDebugEnabled()) {
                                _LOG.debug("--- [" + _threadId + "] Load data from the cache: YES");
                            }
                        }
                    }
                    if (_view == null) {
                        // 处理Convention模式下URL参数集合
                        String _requestMapping = context.getRequestMapping();
                        String[] _urlParamArr = getModuleCfg().isConventionUrlrewriteMode() ? StringUtils.split(_requestMapping, '_') : new String[]{_requestMapping};
                        if (_urlParamArr != null && _urlParamArr.length > 1) {
                            _requestMapping = _urlParamArr[0];
                            List<String> _urlParams = Arrays.asList(_urlParamArr).subList(1, _urlParamArr.length);
                            WebContext.getRequest().setAttribute("UrlParams", _urlParams);
                            //
                            if (_devMode && _LOG.isDebugEnabled()) {
                                _LOG.debug("--- [" + _threadId + "] With parameters : " + _urlParams);
                            }
                        }
                        //
                        if (__moduleCfg.getErrorProcessor() != null) {
                            _view = __moduleCfg.getErrorProcessor().onConvention(this, context);
                        }
                        if (_view == null) {
                            // 采用系统默认方式处理约定优于配置的URL请求映射
                            String[] _fileTypes = {".html", ".jsp", ".ftl", ".vm"};
                            for (String _fileType : _fileTypes) {
                                File _targetFile = new File(__moduleCfg.getAbstractBaseViewPath(), _requestMapping + _fileType);
                                if (_targetFile.exists()) {
                                    if (".html".equals(_fileType)) {
                                        _view = HtmlView.bind(this, _requestMapping.substring(1));
                                        //
                                        if (_devMode && _LOG.isDebugEnabled()) {
                                            _LOG.debug("--- [" + _threadId + "] Rendering template file : " + _requestMapping + _fileType);
                                        }
                                        break;
                                    } else if (".jsp".equals(_fileType)) {
                                        _view = JspView.bind(this, _requestMapping.substring(1));
                                        //
                                        if (_devMode && _LOG.isDebugEnabled()) {
                                            _LOG.debug("--- [" + _threadId + "] Rendering template file : " + _requestMapping + _fileType);
                                        }
                                        break;
                                    } else if (".ftl".equals(_fileType)) {
                                        _view = FreemarkerView.bind(this, _requestMapping.substring(1));
                                        //
                                        if (_devMode && _LOG.isDebugEnabled()) {
                                            _LOG.debug("--- [" + _threadId + "] Rendering template file : " + _requestMapping + _fileType);
                                        }
                                        break;
                                    } else if (".vm".equals(_fileType)) {
                                        _view = VelocityView.bind(this, _requestMapping.substring(1));
                                        //
                                        if (_devMode && _LOG.isDebugEnabled()) {
                                            _LOG.debug("--- [" + _threadId + "] Rendering template file : " + _requestMapping + _fileType);
                                        }
                                    }
                                }
                            }
                        }
                        //
                        if (_view != null && _cacheProcessor != null) {
                            try {
                                if (_cacheProcessor.processResponseCache(this, _responseCache, context, _view)) {
                                    _view = View.nullView();
                                    //
                                    if (_devMode && _LOG.isDebugEnabled()) {
                                        _LOG.debug("--- [" + _threadId + "] Results data cached: YES");
                                    }
                                }
                            } catch (Exception e) {
                                // 缓存处理过程中的任何异常都不能影响本交请求的正常响应, 仅输出异常日志
                                _LOG.warn(e.getMessage(), RuntimeUtils.unwrapThrow(e));
                            }
                        }
                    }
                    if (_view != null) {
                        _view.render();
                    } else {
                        HttpStatusView.NOT_FOUND.render();
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } finally {
            if (_consumeTime != null && _devMode && _LOG.isDebugEnabled()) {
                _consumeTime.stop();
                _LOG.debug("--- [" + _threadId + "] Total execution time: " + _consumeTime.getTime() + "ms");
                _LOG.debug("<-- [" + _threadId + "] Process request completed: " + context.getHttpMethod() + ":" + context.getRequestMapping() + ": " + ((GenericResponseWrapper) response).getStatus());
            }
        }
    }
}
