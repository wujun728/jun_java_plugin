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

import junit.framework.TestCase;
import org.ws.httphelper.WSHttpHelperXmlConfig;

import java.io.File;
import java.util.Map;

/**
 * Created by Administrator on 16-1-2.
 */
public class TestXmlToMapUtil extends TestCase{
    public void testXmlToMap()throws Exception{
        File xmlFile = new File(XmlUtil.class.getResource(WSHttpHelperXmlConfig.CONFIG_XML_PATH).toURI());
        Map map= XmlUtil.xmlToMap(xmlFile);
        System.out.print(map);
    }
}
