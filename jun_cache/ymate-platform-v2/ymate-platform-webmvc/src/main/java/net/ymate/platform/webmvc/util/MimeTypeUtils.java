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
package net.ymate.platform.webmvc.util;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author 刘镇 (suninformation@163.com) on 15/5/10 上午2:45
 * @version 1.0
 */
public class MimeTypeUtils {

    private static final Map<String, String> __MIME_TYPE_MAPS = new HashMap<String, String>();

    static {
        Properties _configs = new Properties();
        InputStream _in = MimeTypeUtils.class.getClassLoader().getResourceAsStream("mimetypes-conf.properties");
        if (_in == null) {
            _in = MimeTypeUtils.class.getClassLoader().getResourceAsStream("META-INF/mimetypes-default-conf.properties");
        }
        if (_in != null) try {
            _configs.load(_in);
            for (Object _key : _configs.keySet()) {
                String[] _values = StringUtils.split(_configs.getProperty((String) _key, ""), "|");
                for (String _value : _values) {
                    __MIME_TYPE_MAPS.put(_value, (String) _key);
                }
            }
        } catch (Exception ignored) {
        } finally {
            try {
                _in.close();
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * @param extName 文件扩展名
     * @return 根据文件扩展名获取对应的MIME_TYPE类型
     */
    public static String getFileMimeType(String extName) {
        if (extName.charAt(0) == '.') {
            extName = extName.substring(1);
        }
        return __MIME_TYPE_MAPS.get(extName);
    }
}
