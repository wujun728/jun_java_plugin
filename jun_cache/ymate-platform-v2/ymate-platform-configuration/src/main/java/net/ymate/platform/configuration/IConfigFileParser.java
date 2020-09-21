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
package net.ymate.platform.configuration;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/7/31 上午12:02
 * @version 1.0
 */
public interface IConfigFileParser {

    IConfigFileParser load(boolean sorted);

    boolean writeTo(File targetFile);

    boolean writeTo(OutputStream outputStream);

    XMLAttribute getAttribute(String key);

    Map<String, XMLAttribute> getAttributes();

    XMLCategory getDefaultCategory();

    XMLCategory getCategory(String name);

    Map<String, XMLCategory> getCategories();

    JSONObject toJSON();

    class XMLCategory {

        private String name;

        private Map<String, XMLAttribute> attributeMap;

        private Map<String, XMLProperty> propertyMap;

        private boolean __sorted;

        public XMLCategory(String name, List<XMLAttribute> attributes, List<XMLProperty> properties, boolean sorted) {
            this.name = name;
            this.__sorted = sorted;
            this.attributeMap = new HashMap<String, XMLAttribute>();
            if (__sorted) {
                this.propertyMap = new LinkedHashMap<String, XMLProperty>();
            } else {
                this.propertyMap = new HashMap<String, XMLProperty>();
            }
            if (attributes != null) {
                for (XMLAttribute _attr : attributes) {
                    this.attributeMap.put(_attr.getKey(), _attr);
                }
            }
            if (properties != null) {
                for (XMLProperty _prop : properties) {
                    this.propertyMap.put(_prop.getName(), _prop);
                }
            }
        }

        public String getName() {
            return name;
        }

        public XMLAttribute getAttribute(String key) {
            return this.attributeMap.get(name);
        }

        public Map<String, XMLAttribute> getAttributeMap() {
            return attributeMap;
        }

        public XMLProperty getProperty(String name) {
            return this.propertyMap.get(name);
        }

        public Map<String, XMLProperty> getPropertyMap() {
            return propertyMap;
        }

        public JSONObject toJSON() {
            JSONObject _jsonO = new JSONObject(__sorted);
            _jsonO.put("name", name);

            JSONObject _jsonATTR = new JSONObject();
            for (XMLAttribute _attr : attributeMap.values()) {
                _jsonATTR.put(_attr.getKey(), _attr.getValue());
            }
            _jsonO.put("attributes", _jsonATTR);

            JSONArray _jsonArrayPROP = new JSONArray();
            for (XMLProperty _prop : propertyMap.values()) {
                _jsonArrayPROP.add(_prop.toJSON());
            }
            _jsonO.put("properties", _jsonArrayPROP);
            return _jsonO;
        }
    }

    class XMLProperty {

        private String name;

        private String content;

        private Map<String, XMLAttribute> attributeMap;

        public XMLProperty(String name, String content, List<XMLAttribute> attributes) {
            this.name = name;
            this.content = content;
            this.attributeMap = new HashMap<String, XMLAttribute>();
            if (attributes != null) {
                for (XMLAttribute _attr : attributes) {
                    this.attributeMap.put(_attr.getKey(), _attr);
                }
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public XMLAttribute getAttribute(String key) {
            return this.attributeMap.get(key);
        }

        public Map<String, XMLAttribute> getAttributeMap() {
            return attributeMap;
        }

        public JSONObject toJSON() {
            JSONObject _jsonO = new JSONObject();
            _jsonO.put("name", name);
            _jsonO.put("content", content);

            JSONObject _jsonATTR = new JSONObject();
            for (XMLAttribute _attr : attributeMap.values()) {
                _jsonATTR.put(_attr.getKey(), _attr.getValue());
            }
            _jsonO.put("attributes", _jsonATTR);
            return _jsonO;
        }
    }

    class XMLAttribute {

        private String key;

        private String value;

        public XMLAttribute(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public JSONObject toJSON() {
            JSONObject _jsonO = new JSONObject();
            _jsonO.put(key, value);
            return _jsonO;
        }
    }
}
