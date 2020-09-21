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

import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.webmvc.IUploadFileWrapper;
import net.ymate.platform.webmvc.IWebMvc;
import net.ymate.platform.webmvc.context.WebContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;

/**
 * 基于XML作为协议格式控制器请求处理器接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/28 上午11:52
 * @version 1.0
 */
public class XMLRequestProcessor extends DefaultRequestProcessor {

    private static final Log _LOG = LogFactory.getLog(XMLRequestProcessor.class);

    private XMLProtocol __doGetProtocol(IWebMvc owner) {
        XMLProtocol _protocol = WebContext.getRequestContext().getAttribute(XMLRequestProcessor.class.getName());
        if (_protocol == null) {
            try {
                _protocol = new XMLProtocol(WebContext.getRequest().getInputStream(), owner.getModuleCfg().getDefaultCharsetEncoding());
            } catch (Exception e) {
                _protocol = new XMLProtocol();
                //
                if (WebContext.getContext().getOwner().getOwner().getConfig().isDevelopMode()) {
                    _LOG.warn("Invalid protocol", RuntimeUtils.unwrapThrow(e));
                }
            }
            WebContext.getRequestContext().addAttribute(XMLRequestProcessor.class.getName(), _protocol);
        }
        return _protocol;
    }

    protected Object __doParseRequestParam(IWebMvc owner, String paramName, String defaultValue, Class<?> paramType, boolean fullScope) {
        Object _returnValue = null;
        XMLProtocol _protocol = __doGetProtocol(owner);
        String[] _paramNameArr = StringUtils.split(paramName, ".");
        if (paramType.isArray()) {
            if (!paramType.equals(IUploadFileWrapper[].class)) {
                String[] _values = null;
                if (_paramNameArr.length > 1) {
                    String _valueStr = _protocol.getSubProperty(_paramNameArr[0], _paramNameArr[1], defaultValue);
                    _values = StringUtils.split(_valueStr, "|");
                } else {
                    String _valueStr = _protocol.getProperty(paramName, defaultValue);
                    _values = StringUtils.split(_valueStr, "|");
                }
                if (_values != null && _values.length > 0) {
                    Class<?> _arrayClassType = ClassUtils.getArrayClassType(paramType);
                    Object[] _tempParams = (Object[]) Array.newInstance(_arrayClassType, _values.length);
                    for (int _tempIdx = 0; _tempIdx < _values.length; _tempIdx++) {
                        _tempParams[_tempIdx] = __doSafeGetParamValue(owner, paramName, _arrayClassType, _values[_tempIdx], null, false);
                    }
                    _returnValue = _tempParams;
                }
            }
        } else if (!paramType.equals(IUploadFileWrapper.class)) {
            if (_paramNameArr.length > 1) {
                _returnValue = __doSafeGetParamValue(owner, paramName, paramType, _protocol.getSubProperty(_paramNameArr[0], _paramNameArr[1], defaultValue), defaultValue, fullScope);
            } else {
                _returnValue = __doSafeGetParamValue(owner, paramName, paramType, _protocol.getProperty(paramName, defaultValue), defaultValue, fullScope);
            }
        }
        return _returnValue;
    }

    /**
     * 简单XML协议处理类
     *
     * @author 刘镇 (suninformation@163.com) on 15/7/25 下午6:16
     * @version 1.0
     */
    private static class XMLProtocol {
        private Element __rootElement;

        private boolean __inited;

        public XMLProtocol() {
        }

        public XMLProtocol(InputStream inputStream, String charsetName) throws ParserConfigurationException, IOException, SAXException {
            InputSource _source = new InputSource(new InputStreamReader(inputStream, charsetName));
            __rootElement = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(_source).getDocumentElement();
            __inited = true;
        }

        public String getProperty(String tagName) {
            return getProperty(tagName, null);
        }

        public String getProperty(String tagName, String defaultValue) {
            String _returnValue = null;
            if (__inited) {
                NodeList _nodes = __rootElement.getElementsByTagName(tagName);
                if (_nodes.getLength() > 0) {
                    Element _node = (Element) _nodes.item(0);
                    _returnValue = _node.getTextContent();
                }
            }
            return StringUtils.defaultIfBlank(_returnValue, defaultValue);
        }

        public String getSubProperty(String subTagName, String tagName) {
            return getSubProperty(subTagName, tagName, null);
        }

        public String getSubProperty(String subTagName, String tagName, String defaultValue) {
            String _returnValue = null;
            if (__inited) {
                NodeList _nodes = __rootElement.getElementsByTagName(subTagName);
                if (_nodes.getLength() > 0) {
                    Element _node = (Element) _nodes.item(0);
                    //
                    NodeList _subNodes = _node.getElementsByTagName(tagName);
                    if (_subNodes.getLength() > 0) {
                        Element _subNode = (Element) _subNodes.item(0);
                        _returnValue = _subNode.getTextContent();
                    }
                }
            }
            return StringUtils.defaultIfBlank(_returnValue, defaultValue);
        }
    }
}
