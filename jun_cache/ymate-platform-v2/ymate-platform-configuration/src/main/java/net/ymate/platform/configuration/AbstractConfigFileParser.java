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

import net.ymate.platform.configuration.IConfigFileParser;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/7/31 上午12:11
 * @version 1.0
 */
public abstract class AbstractConfigFileParser implements IConfigFileParser {

    public static String DEFAULT_CATEGORY_NAME = "default";

    public static String TAG_NAME_ROOT = "properties";

    public static String TAG_NAME_CATEGORY = "category";

    public static String TAG_NAME_PROPERTY = "property";

    public static String TAG_NAME_ITEM = "item";

    public static String TAG_NAME_VALUE = "value";

    protected Map<String, XMLAttribute> __rootAttributes;

    protected Map<String, XMLCategory> __categories;

    protected boolean __loaded;

    protected boolean __sorted;

    @Override
    public IConfigFileParser load(boolean sorted) {
        if (!__loaded) {
            // 判断是否保证顺序
            if (sorted) {
                __sorted = true;
                __categories = new LinkedHashMap<String, XMLCategory>();
                __rootAttributes = new LinkedHashMap<String, XMLAttribute>();
            } else {
                __categories = new HashMap<String, XMLCategory>();
                __rootAttributes = new HashMap<String, XMLAttribute>();
            }
            __doLoad();
            //
            __loaded = true;
        }
        return this;
    }

    protected abstract void __doLoad();
}
