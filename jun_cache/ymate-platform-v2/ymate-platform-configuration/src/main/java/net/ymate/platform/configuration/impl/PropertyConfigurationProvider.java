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
package net.ymate.platform.configuration.impl;

import net.ymate.platform.configuration.AbstractConfigurationProvider;
import net.ymate.platform.configuration.IConfigFileParser;
import org.apache.commons.lang.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 基于properties文件的配置提供者接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 15/10/26 下午5:21
 * @version 1.0
 */
public class PropertyConfigurationProvider extends AbstractConfigurationProvider {

    @Override
    protected IConfigFileParser __buildConfigFileParser(URL cfgFileName) throws Exception {
        return new PropertyConfigFileParser(cfgFileName);
    }

    @Override
    public List<String> getList(String category, String key) {
        List<String> _returnValue = new ArrayList<String>();
        XMLConfigFileParser.XMLProperty _prop = __getConfigFileParser().getCategory(category).getProperty(key);
        if (_prop != null && StringUtils.isNotBlank(_prop.getContent())) {
            _returnValue.addAll(Arrays.asList(StringUtils.split(_prop.getContent(), "|")));
        }
        return _returnValue;
    }
}
