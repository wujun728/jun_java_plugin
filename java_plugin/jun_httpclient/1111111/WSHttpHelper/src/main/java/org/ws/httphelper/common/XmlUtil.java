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

package org.ws.httphelper.common;

import org.apache.commons.io.FileUtils;
import org.ws.httphelper.exception.WSException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 16-1-2.
 */
public class XmlUtil {
    public static Map<String,Object> xmlToMap(File xmlFile) throws WSException{
        XmlParser parser = new XmlParser();
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            parser.parser(map, FileUtils.readFileToByteArray(xmlFile),null);
        } catch (IOException e) {
            throw new WSException(e.getMessage(),e);
        }
        return map;
    }

    public static Map<String,Object> xmlToMap(String xml)throws WSException{
        XmlParser parser = new XmlParser();
        Map<String,Object> map = new HashMap<String, Object>();
        parser.parser(map,xml.getBytes(),null);
        return map;
    }
}
