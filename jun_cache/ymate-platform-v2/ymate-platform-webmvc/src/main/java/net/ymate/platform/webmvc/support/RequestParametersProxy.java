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
package net.ymate.platform.webmvc.support;

import com.alibaba.fastjson.JSON;
import net.ymate.platform.core.beans.annotation.Order;
import net.ymate.platform.core.beans.annotation.Proxy;
import net.ymate.platform.core.beans.proxy.IProxy;
import net.ymate.platform.core.beans.proxy.IProxyChain;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.validation.ValidateResult;
import net.ymate.platform.validation.Validations;
import net.ymate.platform.webmvc.IWebMvc;
import net.ymate.platform.webmvc.RequestMeta;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.view.IView;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求参数代理, 用于处理控制器请求参数验证等
 *
 * @author 刘镇 (suninformation@163.com) on 16/3/21 下午8:42
 * @version 1.0
 */
@Proxy(annotation = Controller.class, order = @Order(-888))
public class RequestParametersProxy implements IProxy {

    public Object doProxy(IProxyChain proxyChain) throws Throwable {
        // 该代理仅处理控制器中声明@RequestMapping的方法
        if (proxyChain.getTargetMethod().getAnnotation(RequestMapping.class) != null) {
            IWebMvc __owner = WebContext.getContext().getOwner();
            RequestMeta __requestMeta = WebContext.getContext().getAttribute(RequestMeta.class.getName());
            Map<String, Object> _paramValues = WebContext.getContext().getAttribute(RequestParametersProxy.class.getName());
            //
            Map<String, ValidateResult> _resultMap = new HashMap<String, ValidateResult>();
            if (!__requestMeta.isSingleton()) {
                _resultMap = Validations.get(__owner.getOwner()).validate(__requestMeta.getTargetClass(), _paramValues);
            }
            if (!__requestMeta.getMethodParamNames().isEmpty()) {
                _resultMap.putAll(Validations.get(__owner.getOwner()).validate(__requestMeta.getTargetClass(), __requestMeta.getMethod(), _paramValues));
            }
            if (!_resultMap.isEmpty()) {
                IView _validationView = null;
                if (__owner.getModuleCfg().getErrorProcessor() != null) {
                    _validationView = __owner.getModuleCfg().getErrorProcessor().onValidation(__owner, _resultMap);
                }
                if (_validationView == null) {
                    throw new IllegalArgumentException(JSON.toJSONString(_resultMap.values()));
                } else {
                    return _validationView;
                }
            }
            if (__owner.getModuleCfg().isParameterEscapeMode() && Type.EscapeOrder.AFTER.equals(__owner.getModuleCfg().getParameterEscapeOrder())) {
                // 若执行转义顺序为after时, 取出暂存在WebContext中已被转义处理的参数
                _paramValues = WebContext.getContext().getAttribute(Type.EscapeOrder.class.getName());
                // 因为进行了参数转义, 所以需要重新组装方法所需参数
                for (int _idx = 0; _idx < __requestMeta.getMethodParamNames().size(); _idx++) {
                    proxyChain.getMethodParams()[_idx] = _paramValues.get(__requestMeta.getMethodParamNames().get(_idx));
                }
            }
            //
            if (!__requestMeta.isSingleton()) {
                ClassUtils.wrapper(proxyChain.getTargetObject()).fromMap(_paramValues);
            }
        }
        //
        return proxyChain.doProxyChain();
    }
}
