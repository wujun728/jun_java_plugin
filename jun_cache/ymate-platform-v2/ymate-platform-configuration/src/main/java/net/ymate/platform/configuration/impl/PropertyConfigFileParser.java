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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.ymate.platform.configuration.AbstractConfigFileParser;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * 基于Properties解析工具处理properties配置文件的读写操作
 *
 * @author 刘镇 (suninformation@163.com) on 2010-9-5 下午06:37:36
 * @version 1.0
 */
public class PropertyConfigFileParser extends AbstractConfigFileParser {

    public static String TAG_NAME_ATTRIBUTE = "attributes";

    private Properties __rootProps;

    public PropertyConfigFileParser(File file) throws IOException {
        __rootProps = new Properties();
        FileReader _reader = new FileReader(file);
        try {
            __rootProps.load(_reader);
        } finally {
            _reader.close();
        }
    }

    public PropertyConfigFileParser(InputStream inputStream) throws IOException {
        __rootProps = new Properties();
        __rootProps.load(inputStream);
    }

    public PropertyConfigFileParser(URL url) throws ParserConfigurationException, IOException, SAXException {
        __rootProps = new Properties();
        InputStream _in = url.openStream();
        try {
            __rootProps.load(_in);
        } finally {
            _in.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void __doLoad() {
        Enumeration<String> _propNames = (Enumeration<String>) __rootProps.propertyNames();
        while (_propNames.hasMoreElements()) {
            String _propName = _propNames.nextElement();
            if (StringUtils.startsWith(_propName, TAG_NAME_ROOT)) {
                String _newPropName = StringUtils.substringAfter(_propName, TAG_NAME_ROOT.concat("."));
                // _propArr[0] = categoryName
                // _propArr[1] = propertyName
                // _propArr[2] = attributes关键字
                // _propArr[3] = attrName
                String[] _propArr = StringUtils.split(_newPropName, ".");
                if (_propArr.length > 1) {
                    // 若为根属性
                    if (_propArr[0].equalsIgnoreCase(TAG_NAME_ATTRIBUTE)) {
                        __rootAttributes.put(_propArr[1], new XMLAttribute(_propArr[1], __rootProps.getProperty(_propName)));
                        continue;
                    }
                    // 若为正常的category, 若category对象不存在, 则创建它
                    XMLCategory _category = __categories.get(_propArr[0]);
                    if (_category == null) {
                        _category = new XMLCategory(_propArr[0], null, null, __sorted);
                        __categories.put(_propArr[0], _category);
                    }
                    //
                    if (_propArr.length == 4) {
                        if (_propArr[2].equalsIgnoreCase(TAG_NAME_ATTRIBUTE)) {
                            XMLProperty _prop = __safeGetProperty(_category, _propName, _propArr[1]);
                            if (_prop != null) {
                                __fixedSetAttribute(_prop, _propName, _propArr[3]);
                            }
                        } else {
                            _category.getPropertyMap().put(_propArr[3], new XMLProperty(_propArr[3], __rootProps.getProperty(_propName), null));
                        }
                    } else if (_propArr.length == 2) {
                        __fixedSetProperty(_category, _propName, _propArr[1]);
                    } else {
                        if (_propArr[1].equalsIgnoreCase(TAG_NAME_ATTRIBUTE)) {
                            _category.getAttributeMap().put(_propArr[2], new XMLAttribute(_propArr[2], __rootProps.getProperty(_propName)));
                        } else {
                            XMLProperty _prop = __safeGetProperty(_category, _propName, _propArr[1]);
                            if (_prop != null) {
                                __fixedSetAttribute(_prop, _propName, _propArr[2]);
                            }
                        }
                    }
                }
            }
        }
        // 必须保证DEFAULT_CATEGORY_NAME配置集合存在
        if (!__categories.containsKey(DEFAULT_CATEGORY_NAME)) {
            __categories.put(DEFAULT_CATEGORY_NAME, new XMLCategory(DEFAULT_CATEGORY_NAME, null, null, __sorted));
        }
    }

    protected XMLProperty __safeGetProperty(XMLCategory category, String propName, String newPropName) {
        XMLProperty _property = category.getProperty(newPropName);
        if (_property == null) {
            _property = new XMLProperty(newPropName, null, null);
            category.getPropertyMap().put(newPropName, _property);
        }
        return _property;
    }

    protected void __fixedSetAttribute(XMLProperty property, String propName, String newPropName) {
        XMLAttribute _attr = property.getAttribute(newPropName);
        String _attrValue = __rootProps.getProperty(propName);
        if (_attr == null) {
            _attr = new XMLAttribute(newPropName, _attrValue);
            property.getAttributeMap().put(newPropName, _attr);
        } else {
            _attr.setKey(newPropName);
            _attr.setValue(_attrValue);
        }
    }

    protected void __fixedSetProperty(XMLCategory category, String propName, String newPropName) {
        XMLProperty _prop = category.getProperty(newPropName);
        String _propContent = __rootProps.getProperty(propName);
        if (_prop == null) {
            _prop = new XMLProperty(newPropName, _propContent, null);
            category.getPropertyMap().put(newPropName, _prop);
        } else {
            _prop.setName(newPropName);
            _prop.setContent(_propContent);
        }
    }

    public boolean writeTo(File targetFile) {
        // TODO write file
        return false;
    }

    public boolean writeTo(OutputStream outputStream) {
        // TODO write file
        return false;
    }

    public XMLAttribute getAttribute(String key) {
        return this.__rootAttributes.get(key);
    }

    public Map<String, XMLAttribute> getAttributes() {
        return Collections.unmodifiableMap(__rootAttributes);
    }

    public XMLCategory getDefaultCategory() {
        return this.__categories.get(DEFAULT_CATEGORY_NAME);
    }

    public XMLCategory getCategory(String name) {
        return this.__categories.get(name);
    }

    public Map<String, XMLCategory> getCategories() {
        return Collections.unmodifiableMap(__categories);
    }

    public JSONObject toJSON() {
        JSONObject _jsonO = new JSONObject(__sorted);
        //
        JSONObject _jsonATTR = new JSONObject();
        for (XMLAttribute _attr : __rootAttributes.values()) {
            _jsonATTR.put(_attr.getKey(), _attr.getValue());
        }
        _jsonO.put("attributes", _jsonATTR);
        //
        JSONArray _jsonArrayCATEGORY = new JSONArray();
        for (XMLCategory _category : __categories.values()) {
            _jsonArrayCATEGORY.add(_category.toJSON());
        }
        _jsonO.put("categories", _jsonArrayCATEGORY);
        return _jsonO;
    }

}
