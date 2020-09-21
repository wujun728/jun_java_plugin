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
import net.ymate.platform.core.lang.PairObject;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基于JDK自带的解析工具处理XML配置文件的读写操作
 *
 * @author 刘镇 (suninformation@163.com) on 14-11-7 下午3:56
 * @version 1.0
 */
public class XMLConfigFileParser extends AbstractConfigFileParser {

    private Element __rootElement;

    public XMLConfigFileParser(File file) throws ParserConfigurationException, IOException, SAXException {
        Document _document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        __rootElement = _document.getDocumentElement();
    }

    public XMLConfigFileParser(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        Document _document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
        __rootElement = _document.getDocumentElement();
    }

    public XMLConfigFileParser(URL url) throws ParserConfigurationException, IOException, SAXException {
        Document _document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openStream());
        __rootElement = _document.getDocumentElement();
    }

    public XMLConfigFileParser(Node node) {
        __rootElement = (Element) node;
    }

    @Override
    public void __doLoad() {
        NamedNodeMap __rootAttrNodes = __rootElement.getAttributes();
        if (__rootAttrNodes != null && __rootAttrNodes.getLength() > 0) {
            // 提取root标签的所有属性
            for (int _attrIdx = 0; _attrIdx < __rootAttrNodes.getLength(); _attrIdx++) {
                String _attrKey = __rootAttrNodes.item(_attrIdx).getNodeName();
                String _attrValue = __rootAttrNodes.item(_attrIdx).getNodeValue();
                if (StringUtils.isNotBlank(_attrKey) && StringUtils.isNotBlank(_attrValue)) {
                    __rootAttributes.put(_attrKey, new XMLAttribute(_attrKey, _attrValue));
                }
            }
        }
        //
        NodeList _nodes = __rootElement.getElementsByTagName(TAG_NAME_CATEGORY);
        if (_nodes.getLength() > 0) {
            for (int _idx = 0; _idx < _nodes.getLength(); _idx++) {
                Element _categoryElement = (Element) _nodes.item(_idx);
                // 1. 处理category的属性
                List<XMLAttribute> _categoryAttrs = new ArrayList<XMLAttribute>();
                PairObject<String, String> _category = __doParseNodeAttributes(_categoryAttrs, _categoryElement, false, false);
                //
                if (_category != null) {
                    // 2. 处理category的property标签
                    List<XMLProperty> _properties = new ArrayList<XMLProperty>();
                    //
                    NodeList _propertyNodes = _categoryElement.getElementsByTagName(TAG_NAME_PROPERTY);
                    if (_propertyNodes.getLength() > 0) {
                        for (int _idy = 0; _idy < _propertyNodes.getLength(); _idy++) {
                            Element _node = (Element) _propertyNodes.item(_idy);
                            // 3. 处理property的属性
                            List<XMLAttribute> _propertyAttrs = new ArrayList<XMLAttribute>();
                            PairObject<String, String> _property = __doParseNodeAttributes(_propertyAttrs, _node, false, false);
                            if (_property != null) {
                                // 是否有子标签
                                boolean _hasSubTag = false;
                                // 4.1 处理property->value标签
                                NodeList _childNodes = _node.getElementsByTagName(TAG_NAME_VALUE);
                                if (_childNodes.getLength() > 0) {
                                    if (_childNodes.getLength() == 1) {
                                        _property.setValue(_childNodes.item(0).getTextContent());
                                    } else {
                                        _hasSubTag = true;
                                        for (int _idxItem = 0; _idxItem < _childNodes.getLength(); _idxItem++) {
                                            Element _nodeItem = (Element) _childNodes.item(_idxItem);
                                            String _value = _nodeItem.getTextContent();
                                            if (StringUtils.isNotBlank(_value)) {
                                                _propertyAttrs.add(new XMLAttribute(_value, ""));
                                            }
                                        }
                                    }
                                } else {
                                    // 4.2 处理property->item标签
                                    _childNodes = _node.getElementsByTagName(TAG_NAME_ITEM);
                                    if (_childNodes.getLength() > 0) {
                                        _hasSubTag = true;
                                        for (int _idxItem = 0; _idxItem < _childNodes.getLength(); _idxItem++) {
                                            __doParseNodeAttributes(_propertyAttrs, (Element) _childNodes.item(_idxItem), true, true);
                                        }
                                    }
                                }
                                //
                                if (!_hasSubTag) {
                                    if (StringUtils.isNotBlank(_property.getValue())) {
                                        _properties.add(new XMLProperty(_property.getKey(), _property.getValue(), _propertyAttrs));
                                    }
                                } else {
                                    _properties.add(new XMLProperty(_property.getKey(), null, _propertyAttrs));
                                }
                            }
                        }
                    }
                    //
                    __categories.put(_category.getKey(), new XMLCategory(_category.getKey(), _categoryAttrs, _properties, __sorted));
                }
            }
        }
        // 必须保证DEFAULT_CATEGORY_NAME配置集合存在
        if (!__categories.containsKey(DEFAULT_CATEGORY_NAME)) {
            __categories.put(DEFAULT_CATEGORY_NAME, new XMLCategory(DEFAULT_CATEGORY_NAME, null, null, __sorted));
        }
    }

    protected PairObject<String, String> __doParseNodeAttributes(List<XMLAttribute> attributes, Element node, boolean collections, boolean textContent) {
        String _propertyName = null;
        String _propertyContent = null;
        //
        NamedNodeMap _attrNodes = node.getAttributes();
        if (_attrNodes != null && _attrNodes.getLength() > 0) {
            for (int _idy = 0; _idy < _attrNodes.getLength(); _idy++) {
                String _attrKey = _attrNodes.item(_idy).getNodeName();
                String _attrValue = _attrNodes.item(_idy).getNodeValue();
                if (collections) {
                    if (_attrKey.equals("name")) {
                        attributes.add(new XMLAttribute(_attrValue, node.getTextContent()));
                    }
                } else {
                    if (textContent && StringUtils.isNotBlank(_attrValue)) {
                        _attrValue = node.getTextContent();
                    }
                    if (_attrKey.equals("name")) {
                        _propertyName = _attrValue;
                    } else if (_attrKey.equals("value")) {
                        _propertyContent = _attrValue;
                    } else {
                        attributes.add(new XMLAttribute(_attrKey, _attrValue));
                    }
                }
            }
        }
        if (!collections && StringUtils.isNotBlank(_propertyName)) {
            return new PairObject<String, String>(_propertyName, _propertyContent);
        }
        return null;
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
        return __rootAttributes;
    }

    public XMLCategory getDefaultCategory() {
        return this.__categories.get(DEFAULT_CATEGORY_NAME);
    }

    public XMLCategory getCategory(String name) {
        return this.__categories.get(name);
    }

    public Map<String, XMLCategory> getCategories() {
        return __categories;
    }

    public JSONObject toJSON() {
        JSONObject _jsonO = new JSONObject(__sorted);
        //
        for (XMLAttribute _attr : __rootAttributes.values()) {
            _jsonO.put(_attr.getKey(), _attr.getValue());
        }
        //
        JSONArray _jsonArrayCATEGORY = new JSONArray();
        for (XMLCategory _category : __categories.values()) {
            _jsonArrayCATEGORY.add(_category.toJSON());
        }
        _jsonO.put("categories", _jsonArrayCATEGORY);
        return _jsonO;
    }
}