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

import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.webmvc.IRequestProcessor;
import net.ymate.platform.webmvc.IWebMvc;
import net.ymate.platform.webmvc.RequestMeta;
import net.ymate.platform.webmvc.annotation.Header;
import net.ymate.platform.webmvc.annotation.ResponseView;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.impl.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * WebMVC请求执行器
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-14 下午4:30:27
 * @version 1.0
 */
public final class RequestExecutor {

    private IWebMvc __owner;

    private RequestMeta __requestMeta;

    private IRequestProcessor __requestProcessor;

    public static RequestExecutor bind(IWebMvc owner, RequestMeta requestMeta) {
        return new RequestExecutor(owner, requestMeta);
    }

    private RequestExecutor(IWebMvc owner, RequestMeta requestMeta) {
        __owner = owner;
        __requestMeta = requestMeta;
        if (requestMeta.getProcessor() != null) {
            __requestProcessor = ClassUtils.impl(requestMeta.getProcessor(), IRequestProcessor.class);
        }
        if (__requestProcessor == null) {
            __requestProcessor = __owner.getModuleCfg().getRequestProcessor();
        }
    }

    public IView execute() throws Exception {
        // 将当前RequestMeta对象放入WebContext中, 便于其它环节中获取并使用
        WebContext.getContext().addAttribute(RequestMeta.class.getName(), __requestMeta);
        // 取得当前控制器方法参数的名称集合
        List<String> _methodParamNames = __requestMeta.getMethodParamNames();
        // 根据参数名称, 从请求中提取对应的参数值
        Map<String, Object> _paramValues = __requestProcessor.processRequestParams(__owner, __requestMeta);
        WebContext.getContext().addAttribute(RequestParametersProxy.class.getName(), _paramValues);
        // 提取控制器类实例
        Object _targetObj = __owner.getOwner().getBean(__requestMeta.getTargetClass());
        if (!_methodParamNames.isEmpty()) {
            // 组装方法所需参数
            Object[] _mParamValues = new Object[_methodParamNames.size()];
            for (int _idx = 0; _idx < _methodParamNames.size(); _idx++) {
                _mParamValues[_idx] = _paramValues.get(_methodParamNames.get(_idx));
            }
            return __doProcessResultToView(__requestMeta.getMethod().invoke(_targetObj, _mParamValues));
        } else {
            return __doProcessResultToView(__requestMeta.getMethod().invoke(_targetObj));
        }
    }

    private IView __doProcessResultToView(Object result) throws Exception {
        IView _view = null;
        if (result == null) {
            if (__requestMeta.getResponseView() != null) {
                ResponseView _respView = __requestMeta.getResponseView();
                String[] _viewParts = StringUtils.split(_respView.value(), ":");
                switch (_respView.type()) {
                    case BINARY:
                        _view = BinaryView.bind(new File(_viewParts[0])).useAttachment(_viewParts.length > 1 ? _viewParts[1] : null);
                        break;
                    case FORWARD:
                        _view = ForwardView.bind(_respView.value());
                        break;
                    case FREEMARKER:
                        _view = FreemarkerView.bind(__owner, _respView.value());
                        break;
                    case VELOCITY:
                        _view = VelocityView.bind(__owner, _respView.value());
                        break;
                    case HTML:
                        _view = HtmlView.bind(__owner, _respView.value());
                        break;
                    case HTTP_STATES:
                        _view = HttpStatusView.bind(Integer.parseInt(_viewParts[0]), _viewParts.length > 1 ? _viewParts[1] : null);
                        break;
                    case JSON:
                        _view = JsonView.bind(_viewParts[0]);
                        break;
                    case JSP:
                        _view = JspView.bind(__owner, _viewParts[0]);
                        break;
                    case REDIRECT:
                        _view = RedirectView.bind(_viewParts[0]);
                        break;
                    case TEXT:
                        _view = TextView.bind(_viewParts[0]);
                        break;
                    default:
                        _view = NullView.bind();
                }
            } else {
                //
                _view = JspView.bind(__owner);
            }
        } else if (result instanceof IView) {
            _view = (IView) result;
        } else if (result instanceof String) {
            String[] _parts = StringUtils.split((String) result, ":");
            if (ArrayUtils.isNotEmpty(_parts) && _parts.length > 1) {
                switch (Type.View.valueOf(_parts[0].toUpperCase())) {
                    case BINARY:
                        _view = BinaryView.bind(new File(_parts[1])).useAttachment(_parts.length >= 3 ? _parts[2] : null);
                        break;
                    case FORWARD:
                        _view = ForwardView.bind(_parts[1]);
                        break;
                    case FREEMARKER:
                        _view = FreemarkerView.bind(__owner, _parts[1]);
                        break;
                    case VELOCITY:
                        _view = VelocityView.bind(__owner, _parts[1]);
                        break;
                    case HTML:
                        _view = HtmlView.bind(__owner, _parts[1]);
                        break;
                    case HTTP_STATES:
                        _view = HttpStatusView.bind(Integer.parseInt(_parts[1]), _parts.length >= 3 ? _parts[2] : null);
                        break;
                    case JSON:
                        _view = JsonView.bind(_parts[1]);
                        break;
                    case JSP:
                        _view = JspView.bind(__owner, _parts[1]);
                        break;
                    case NULL:
                        _view = NullView.bind();
                        break;
                    case REDIRECT:
                        _view = RedirectView.bind(_parts[1]);
                        break;
                    case TEXT:
                        _view = TextView.bind(_parts[1]);
                }
            } else {
                _view = HtmlView.bind((String) result);
            }
        }
        //
        if (_view != null) {
            for (Header _header : __requestMeta.getResponseHeaders()) {
                switch (_header.type()) {
                    case STRING:
                        _view.addHeader(_header.name(), _header.value());
                        break;
                    case DATE:
                        _view.addDateHeader(_header.name(), BlurObject.bind(_header.value()).toLongValue());
                        break;
                    case INT:
                        _view.addIntHeader(_header.name(), BlurObject.bind(_header.value()).toIntValue());
                }
            }
        }
        return _view;
    }
}
