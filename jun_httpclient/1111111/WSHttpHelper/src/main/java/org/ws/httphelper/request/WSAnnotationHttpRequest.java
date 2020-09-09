/*
 * Copyright (c) 2015-2016, AlexGao
 * http://git.oschina.net/wolfsmoke/WSHttpHelper
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ws.httphelper.request;

import org.apache.commons.lang.StringUtils;
import org.ws.httphelper.WSHttpHelperConfig;
import org.ws.httphelper.WSHttpHelperXmlConfig;
import org.ws.httphelper.annotation.Header;
import org.ws.httphelper.annotation.Parameter;
import org.ws.httphelper.annotation.WSRequest;
import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.model.ParameterDefine;
import org.ws.httphelper.model.WSRequestContext;

/**
 * Created by Administrator on 16-1-1.
 */
public abstract class WSAnnotationHttpRequest extends WSAbstractHttpRequest {

    @Override
    protected WSRequestContext builderContext()  throws WSException{
        WSRequestContext context = null;
        WSRequest ann = this.getClass().getAnnotation(WSRequest.class);
        if (ann != null) {
            context = new WSRequestContext();
            context.setName(ann.name());
            if (StringUtils.isEmpty(context.getUrl())) {
                context.setUrl(ann.url());
            }
            if (StringUtils.isEmpty(context.getDescription())) {
                context.setDescription(ann.description());
            }
            if (context.getResponseType() == null) {
                context.setResponseType(ann.responseType());
            }
            if (context.getMethod() == null) {
                context.setMethod(ann.method());
            }

            String charset = ann.charset();
            if (StringUtils.isEmpty(charset)) {
                charset = WSHttpHelperXmlConfig.getInstance().getCharset();
            }
            if (StringUtils.isEmpty(context.getCharset())) {
                context.setCharset(charset);
            }
            if (context.getResultClass() == null) {
                context.setResultClass(ann.resultClass());
            }

            if (ann.headers() != null) {
                for (Header header : ann.headers()) {
                    context.addHeader(header.name(), header.value());
                }
            }
            if (ann.parameters() != null) {
                for (Parameter parameter : ann.parameters()) {
                    context.addParameterDefine(builderParameterDefine(parameter));
                }
            }
        }
        return context;
    }

    /**
     * 获取参数
     *
     * @param parameter
     * @return
     * @throws WSException
     */
    private ParameterDefine builderParameterDefine(Parameter parameter) {
        ParameterDefine parameterDefine = new ParameterDefine();
        parameterDefine.setName(parameter.name());
        parameterDefine.setDescription(parameter.description());
        parameterDefine.setDefaultValue(parameter.defaultValue());
        parameterDefine.setExample(parameter.example());
        parameterDefine.setRequired(parameter.required());
        parameterDefine.setType(parameter.type());
        parameterDefine.setValidateRegex(parameter.validateRegex());
        return parameterDefine;
    }
}
