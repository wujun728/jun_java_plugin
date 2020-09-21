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

import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.webmvc.annotation.*;
import net.ymate.platform.webmvc.base.Type;
import org.apache.commons.lang.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 控制器请求映射元数据描述
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-10 下午10:59:14
 * @version 1.0
 */
@ParameterEscape
public class RequestMeta {

    private static Map<Class<?>, Map<String, ParameterMeta>> __CLASS_PARAMETER_METAS;

    static {
        __CLASS_PARAMETER_METAS = new ConcurrentHashMap<Class<?>, Map<String, ParameterMeta>>();
    }

    private List<ParameterMeta> __methodParameterMetas;

    private Class<?> targetClass;
    private String name;
    private String mapping;
    private Class<? extends IRequestProcessor> processor;
    private Method method;

    private List<String> methodParamNames;

    private boolean singleton;

    private ResponseCache responseCache;

    /**
     * 参数转义注解, 若未设置则该值为null
     */
    private ParameterEscape parameterEscape;

    private ResponseView responseView;
    private Set<Header> responseHeaders;

    private Set<Type.HttpMethod> allowMethods;
    private Map<String, String> allowHeaders;
    private Map<String, String> allowParams;

    public RequestMeta(IWebMvc owner, Class<?> targetClass, Method method) throws Exception {
        this.targetClass = targetClass;
        this.method = method;
        //
        this.allowMethods = new HashSet<Type.HttpMethod>();
        this.allowHeaders = new HashMap<String, String>();
        this.allowParams = new HashMap<String, String>();
        //
        Controller _controller = targetClass.getAnnotation(Controller.class);
        this.name = StringUtils.defaultIfBlank(_controller.name(), targetClass.getName());
        this.singleton = _controller.singleton();
        //
        this.responseCache = method.getAnnotation(ResponseCache.class);
        if (this.responseCache == null) {
            this.responseCache = targetClass.getAnnotation(ResponseCache.class);
        }
        //
        this.parameterEscape = method.getAnnotation(ParameterEscape.class);
        if (this.parameterEscape == null) {
            this.parameterEscape = targetClass.getAnnotation(ParameterEscape.class);
            if (this.parameterEscape == null && owner.getModuleCfg().isParameterEscapeMode()) {
                this.parameterEscape = this.getClass().getAnnotation(ParameterEscape.class);
            }
        }
        if (this.parameterEscape != null && this.parameterEscape.skiped()) {
            this.parameterEscape = null;
        }
        //
        this.responseView = method.getAnnotation(ResponseView.class);
        if (this.responseView == null) {
            this.responseView = targetClass.getAnnotation(ResponseView.class);
        }
        //
        this.responseHeaders = new HashSet<Header>();
        ResponseHeader _respHeader = targetClass.getAnnotation(ResponseHeader.class);
        if (_respHeader != null) {
            Collections.addAll(this.responseHeaders, _respHeader.value());
        }
        _respHeader = method.getAnnotation(ResponseHeader.class);
        if (_respHeader != null) {
            Collections.addAll(this.responseHeaders, _respHeader.value());
        }
        //
        RequestMapping _reqMapping = targetClass.getAnnotation(RequestMapping.class);
        String _root = null;
        if (_reqMapping != null) {
            _root = _reqMapping.value();
            __doSetAllowValues(_reqMapping);
        }
        _reqMapping = method.getAnnotation(RequestMapping.class);
        __doSetAllowValues(_reqMapping);
        //
        if (this.allowMethods.isEmpty()) {
            this.allowMethods.add(Type.HttpMethod.GET);
        }
        //
        this.mapping = __doBuildRequestMapping(_root, _reqMapping);
        //
        RequestProcessor _reqProcessor = method.getAnnotation(RequestProcessor.class);
        if (_reqProcessor != null) {
            this.processor = _reqProcessor.value();
        }
        if (this.processor == null) {
            _reqProcessor = targetClass.getAnnotation(RequestProcessor.class);
            if (_reqProcessor != null) {
                this.processor = _reqProcessor.value();
            }
        }
        //
        Map<String, ParameterMeta> _targetClassParameterMetas = __CLASS_PARAMETER_METAS.get(targetClass);
        if (_targetClassParameterMetas == null) {
            ClassUtils.BeanWrapper<?> _wrapper = ClassUtils.wrapper(targetClass);
            if (_wrapper != null) {
                _targetClassParameterMetas = new HashMap<String, ParameterMeta>();
                //
                for (String _fieldName : _wrapper.getFieldNames()) {
                    if (!_targetClassParameterMetas.containsKey(_fieldName)) {
                        ParameterMeta _meta = new ParameterMeta(this, _wrapper.getField(_fieldName));
                        if (_meta.isParamField()) {
                            _targetClassParameterMetas.put(_fieldName, _meta);
                        }
                    }
                }
                __CLASS_PARAMETER_METAS.put(targetClass, _targetClassParameterMetas);
            }
        }
        //
        this.__methodParameterMetas = new ArrayList<ParameterMeta>();
        this.methodParamNames = Arrays.asList(ClassUtils.getMethodParamNames(method));
        if (!this.methodParamNames.isEmpty()) {
            Class<?>[] _paramTypes = method.getParameterTypes();
            Annotation[][] _paramAnnotations = method.getParameterAnnotations();
            for (int _idx = 0; _idx < this.methodParamNames.size(); _idx++) {
                ParameterMeta _meta = new ParameterMeta(this, _paramTypes[_idx], this.methodParamNames.get(_idx), _paramAnnotations[_idx]);
                if (_meta.isParamField()) {
                    this.__methodParameterMetas.add(_meta);
                }
            }
        }
    }

    private void __doSetAllowValues(RequestMapping requestMapping) {
        this.allowMethods.addAll(Arrays.asList(requestMapping.method()));
        for (String _header : requestMapping.header()) {
            String[] _headerParts = StringUtils.split(StringUtils.trimToEmpty(_header), "=");
            if (_headerParts.length == 2) {
                this.allowHeaders.put(_headerParts[0].trim(), _headerParts[1].trim());
            }
        }
        for (String _param : requestMapping.param()) {
            String[] _paramParts = StringUtils.split(StringUtils.trimToEmpty(_param), "=");
            if (_paramParts.length == 2) {
                this.allowParams.put(_paramParts[0].trim(), _paramParts[1].trim());
            }
        }
    }

    private String __doBuildRequestMapping(String root, RequestMapping requestMapping) {
        StringBuilder _mappingSB = new StringBuilder(__doCheckMappingSeparator(root));
        String _mapping = requestMapping.value();
        if (StringUtils.isBlank(_mapping)) {
            _mappingSB.append("/").append(method.getName());
        } else {
            _mappingSB.append(__doCheckMappingSeparator(_mapping));
        }
        return _mappingSB.toString();
    }

    private String __doCheckMappingSeparator(String mapping) {
        if (StringUtils.isBlank(mapping)) {
            return "";
        }
        if (!mapping.startsWith("/")) {
            mapping = "/".concat(mapping);
        }
        if (mapping.endsWith("/")) {
            mapping = mapping.substring(0, mapping.length() - 1);
        }
        return mapping;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public String getMapping() {
        return mapping;
    }

    public Class<? extends IRequestProcessor> getProcessor() {
        return processor;
    }

    public Method getMethod() {
        return method;
    }

    public List<String> getMethodParamNames() {
        return Collections.unmodifiableList(methodParamNames);
    }

    public String getName() {
        return name;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public ResponseCache getResponseCache() {
        return responseCache;
    }

    public ParameterEscape getParameterEscape() {
        return parameterEscape;
    }

    public ResponseView getResponseView() {
        return responseView;
    }

    public Set<Header> getResponseHeaders() {
        return Collections.unmodifiableSet(responseHeaders);
    }

    /**
     * @param method 方法名称
     * @return 判断是否允许method方式的HTTP请求，若允许的请求方式集合为空，则默认不限制
     */
    public boolean allowHttpMethod(Type.HttpMethod method) {
        // 若允许的请求方式集合为空，则默认不限制
        return this.allowMethods.isEmpty() || this.allowMethods.contains(method);
    }

    public Set<Type.HttpMethod> getAllowMethods() {
        return Collections.unmodifiableSet(allowMethods);
    }

    public Map<String, String> getAllowHeaders() {
        return Collections.unmodifiableMap(allowHeaders);
    }

    public Map<String, String> getAllowParams() {
        return Collections.unmodifiableMap(allowParams);
    }

    public Collection<ParameterMeta> getClassParameterMetas() {
        return Collections.unmodifiableCollection(__CLASS_PARAMETER_METAS.get(targetClass).values());
    }

    public List<ParameterMeta> getMethodParameterMetas() {
        return Collections.unmodifiableList(__methodParameterMetas);
    }
}
