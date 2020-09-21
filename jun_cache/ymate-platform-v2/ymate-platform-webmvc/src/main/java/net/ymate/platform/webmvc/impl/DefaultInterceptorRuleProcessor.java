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
package net.ymate.platform.webmvc.impl;

import net.ymate.platform.core.beans.intercept.IInterceptor;
import net.ymate.platform.core.beans.intercept.InterceptAnnoHelper;
import net.ymate.platform.core.beans.intercept.InterceptContext;
import net.ymate.platform.core.lang.PairObject;
import net.ymate.platform.webmvc.IInterceptorRule;
import net.ymate.platform.webmvc.IInterceptorRuleProcessor;
import net.ymate.platform.webmvc.IRequestContext;
import net.ymate.platform.webmvc.IWebMvc;
import net.ymate.platform.webmvc.annotation.InterceptorRule;
import net.ymate.platform.webmvc.annotation.ResponseCache;
import net.ymate.platform.webmvc.view.IView;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认请求拦截规则处理器接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 16/1/8 下午11:27
 * @version 1.0
 */
public class DefaultInterceptorRuleProcessor implements IInterceptorRuleProcessor {

    private IWebMvc __owner;

    private Map<String, InterceptorRuleMeta> __interceptorRules = new HashMap<String, InterceptorRuleMeta>();

    public void init(IWebMvc owner) throws Exception {
        __owner = owner;
    }

    public void registerInterceptorRule(Class<? extends IInterceptorRule> targetClass) throws Exception {
        Method[] _methods = targetClass.getMethods();
        for (Method _method : _methods) {
            InterceptorRuleMeta _meta = new InterceptorRuleMeta(__owner, targetClass, _method);
            if (StringUtils.isNotBlank(_meta.getMapping())) {
                __interceptorRules.put(_meta.getMapping(), _meta);
            }
        }
    }

    public PairObject<IView, ResponseCache> processRequest(IWebMvc owner, IRequestContext requestContext) throws Exception {
        String _mapping = requestContext.getRequestMapping();
        InterceptorRuleMeta _ruleMeta = __interceptorRules.get(_mapping);
        IView _view = null;
        if (_ruleMeta == null) {
            while (StringUtils.countMatches(_mapping, "/") > 1) {
                _mapping = StringUtils.substringBeforeLast(_mapping, "/");
                _ruleMeta = __interceptorRules.get(_mapping);
                if (_ruleMeta != null && _ruleMeta.isMatchAll()) {
                    break;
                }
            }
        }
        ResponseCache _responseCache = null;
        if (_ruleMeta != null) {
            _responseCache = _ruleMeta.getResponseCache();
            InterceptContext _context = new InterceptContext(IInterceptor.Direction.BEFORE, owner.getOwner(), null, null, null, _ruleMeta.getContextParams());
            //
            for (Class<? extends IInterceptor> _interceptClass : _ruleMeta.getBeforeIntercepts()) {
                IInterceptor _interceptor = _interceptClass.newInstance();
                // 执行前置拦截器，若其结果对象不为空则返回并停止执行
                Object _result = _interceptor.intercept(_context);
                if (_result != null) {
                    _view = (IView) _result;
                    break;
                }
            }
        }
        return new PairObject<IView, ResponseCache>(_view, _responseCache);
    }

    static class InterceptorRuleMeta {
        private String mapping;
        private List<Class<? extends IInterceptor>> beforeIntercepts;
        private Map<String, String> contextParams;
        private boolean matchAll;

        private ResponseCache responseCache;

        public InterceptorRuleMeta(IWebMvc owner, Class<? extends IInterceptorRule> targetClass, Method targetMethod) {
            InterceptorRule _ruleAnno = targetMethod.getAnnotation(InterceptorRule.class);
            if (_ruleAnno != null && StringUtils.trimToNull(_ruleAnno.value()) != null) {
                String _mapping = _ruleAnno.value();
                if (!StringUtils.startsWith(_mapping, "/")) {
                    _mapping += "/";
                }
                //
                _ruleAnno = targetClass.getAnnotation(InterceptorRule.class);
                if (_ruleAnno != null) {
                    mapping = StringUtils.trimToEmpty(_ruleAnno.value());
                    if (StringUtils.endsWith(mapping, "/")) {
                        mapping = StringUtils.substringBeforeLast(mapping, "/");
                    }
                }
                mapping += _mapping;
                //
                if (!StringUtils.startsWith(mapping, "/")) {
                    mapping += "/";
                }
                if (StringUtils.endsWith(mapping, "/*")) {
                    matchAll = true;
                    mapping = StringUtils.substringBeforeLast(mapping, "/*");
                }
                //
                beforeIntercepts = InterceptAnnoHelper.getBeforeIntercepts(targetClass, targetMethod);
                contextParams = InterceptAnnoHelper.getContextParams(owner.getOwner(), targetClass, targetMethod);
                //
                this.responseCache = targetMethod.getAnnotation(ResponseCache.class);
                if (this.responseCache == null) {
                    this.responseCache = targetClass.getAnnotation(ResponseCache.class);
                }
            }
        }

        public String getMapping() {
            return mapping;
        }

        public List<Class<? extends IInterceptor>> getBeforeIntercepts() {
            return beforeIntercepts;
        }

        public Map<String, String> getContextParams() {
            return contextParams;
        }

        public boolean isMatchAll() {
            return matchAll;
        }

        public ResponseCache getResponseCache() {
            return responseCache;
        }
    }
}
