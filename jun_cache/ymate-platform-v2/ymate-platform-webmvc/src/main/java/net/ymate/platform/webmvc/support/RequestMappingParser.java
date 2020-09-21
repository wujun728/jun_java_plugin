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

import net.ymate.platform.core.lang.PairObject;
import net.ymate.platform.webmvc.IRequestContext;
import net.ymate.platform.webmvc.IRequestMappingParser;
import net.ymate.platform.webmvc.RequestMeta;
import net.ymate.platform.webmvc.base.Type;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * 默认WebMVC请求映射路径分析器
 *
 * @author 刘镇 (suninformation@163.com) on 2011-7-26 上午11:11:45
 * @version 1.0
 */
public class RequestMappingParser implements IRequestMappingParser {

    private Map<String, RequestMeta> __MAPPING_META_FOR_GET;

    private Map<String, RequestMeta> __MAPPING_META_FOR_POST;

    private Map<String, RequestMeta> __MAPPING_META_FOR_DELETE;

    private Map<String, RequestMeta> __MAPPING_META_FOR_PUT;

    private Map<String, RequestMeta> __MAPPING_META_FOR_OPTIONS;

    private Map<String, RequestMeta> __MAPPING_META_FOR_HEAD;

    private Map<String, RequestMeta> __MAPPING_META_FOR_TRACE;

    public RequestMappingParser() {
        __MAPPING_META_FOR_GET = new HashMap<String, RequestMeta>();
        __MAPPING_META_FOR_POST = new HashMap<String, RequestMeta>();
        __MAPPING_META_FOR_DELETE = new HashMap<String, RequestMeta>();
        __MAPPING_META_FOR_PUT = new HashMap<String, RequestMeta>();
        __MAPPING_META_FOR_OPTIONS = new HashMap<String, RequestMeta>();
        __MAPPING_META_FOR_HEAD = new HashMap<String, RequestMeta>();
        __MAPPING_META_FOR_TRACE = new HashMap<String, RequestMeta>();
    }

    public final void registerRequestMeta(RequestMeta requestMeta) {
        for (Type.HttpMethod _method : requestMeta.getAllowMethods()) {
            switch (_method) {
                case POST:
                    __MAPPING_META_FOR_POST.put(requestMeta.getMapping(), requestMeta);
                    break;
                case DELETE:
                    __MAPPING_META_FOR_DELETE.put(requestMeta.getMapping(), requestMeta);
                    break;
                case PUT:
                    __MAPPING_META_FOR_PUT.put(requestMeta.getMapping(), requestMeta);
                    break;
                case OPTIONS:
                    __MAPPING_META_FOR_OPTIONS.put(requestMeta.getMapping(), requestMeta);
                    break;
                case HEAD:
                    __MAPPING_META_FOR_HEAD.put(requestMeta.getMapping(), requestMeta);
                    break;
                case TRACE:
                    __MAPPING_META_FOR_TRACE.put(requestMeta.getMapping(), requestMeta);
                    break;
                default:
                    __MAPPING_META_FOR_GET.put(requestMeta.getMapping(), requestMeta);
            }
        }
    }

    /**
     * @param partStr 参数段
     * @return 返回去掉首尾'/'字符的串
     */
    protected String __doFixMappingPart(String partStr) {
        partStr = StringUtils.trimToEmpty(partStr);
        if (StringUtils.startsWith(partStr, "/")) {
            partStr = StringUtils.substringAfter(partStr, "/");
        }
        if (StringUtils.endsWith(partStr, "/")) {
            partStr = StringUtils.substringBeforeLast(partStr, "/");
        }
        return partStr;
    }

    /**
     * @param mappingParamPart 请求映射参数段
     * @param mappingMatcher   请求映射参数匹配规则
     * @return 返回根据mappingMatcher提取请求映射中的参数值
     */
    private Map<String, String> __doParserMappingParams(String mappingParamPart, String mappingMatcher) {
        Map<String, String> _params = new HashMap<String, String>();
        String[] _paramNames = StringUtils.split(mappingMatcher, "/");
        String[] _parts = StringUtils.split(mappingParamPart, "/");
        if (_parts.length >= _paramNames.length) {
            for (int _index = 0; _index < _paramNames.length; _index++) {
                String _pName = StringUtils.substringBetween(_paramNames[_index], "{", "}");
                if (_pName != null) {
                    _params.put(_pName, _parts[_index]);
                }
            }
        }
        return _params;
    }

    public final RequestMeta doParse(IRequestContext context) {
        Map<String, RequestMeta> _mappingMap = null;
        switch (context.getHttpMethod()) {
            case POST:
                _mappingMap = __MAPPING_META_FOR_POST;
                break;
            case DELETE:
                _mappingMap = __MAPPING_META_FOR_DELETE;
                break;
            case PUT:
                _mappingMap = __MAPPING_META_FOR_PUT;
                break;
            case OPTIONS:
                _mappingMap = __MAPPING_META_FOR_OPTIONS;
                break;
            case HEAD:
                _mappingMap = __MAPPING_META_FOR_HEAD;
                break;
            case TRACE:
                _mappingMap = __MAPPING_META_FOR_TRACE;
                break;
            default:
                _mappingMap = __MAPPING_META_FOR_GET;
        }
        RequestMeta _meta = _mappingMap.get(context.getRequestMapping());
        if (_meta == null) {
            return __doParse(context, Collections.unmodifiableMap(_mappingMap));
        }
        return _meta;
    }

    protected RequestMeta __doParse(IRequestContext context, Map<String, RequestMeta> mappings) {
        String _requestMapping = this.__doFixMappingPart(context.getRequestMapping());
        // 过滤出匹配程度较高的映射键值集合：PairObject<Mapping分解数组[原始，前缀段，参数段], 参数段数量>
        Set<PairObject<String[], Integer>> _filteredMapping = new HashSet<PairObject<String[], Integer>>();
        for (String _key : mappings.keySet()) {
            if (_key.contains("{")) {
                String _mappingKey = StringUtils.substringBefore(_key, "{");
                String _fixedMappingKey = this.__doFixMappingPart(_mappingKey);
                if (StringUtils.startsWithIgnoreCase(_requestMapping, _fixedMappingKey)) {
                    // 截取参数段，并分析参数段数量
                    String _paramKey = _key.substring(_key.indexOf('{'));
                    _filteredMapping.add(new PairObject<String[], Integer>(new String[]{_key, _mappingKey, _fixedMappingKey, _paramKey}, StringUtils.split(_paramKey, "/").length));
                }
            }
        }
        // 遍历已过滤映射集合并通过与请求映射串比较参数数量，找出最接近的一个
        PairObject<String[], Integer> _result = null;
        String _mappingParamPart = null;
        for (PairObject<String[], Integer> _item : _filteredMapping) {
            // 提取当前请求映射的参数段
            _mappingParamPart = StringUtils.substringAfter(_requestMapping, _item.getKey()[2]);
            // 计算当前请求映射的参数段数量
            int _paramPartCount = StringUtils.split(_mappingParamPart, "/").length;
            // 匹配参数段数量，若数量相同则直接返回
            if (_paramPartCount == _item.getValue()) {
                _result = _item;
                break;
            }
        }
        if (_result != null) {
            Map<String, String> _params = this.__doParserMappingParams(_mappingParamPart, _result.getKey()[3]);
            // 参数变量存入WebContext容器中的PathVariable参数池
            for (Map.Entry<String, String> _entry : _params.entrySet()) {
                context.addAttribute(_entry.getKey(), _entry.getValue());
            }
            return mappings.get(_result.getKey()[0]);
        }
        return null;
    }
}
