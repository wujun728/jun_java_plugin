package org.ws.httphelper.common;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by Administrator on 15-12-29.
 */
public class TestXmlUtil extends TestCase{
    public void testHttpHelperConfig()throws Exception{
        File xmlFile = new File(XmlUtil.class.getResource("/httphelper-config.xml").toURI());
        String xml = FileUtils.readFileToString(xmlFile);
        Map map = XmlUtil.xmlToMap(xml);
        Map clientMap = (Map)map.get("httpclient-config");

        System.out.print(((Map)clientMap.get("http")).get("@charset"));
    }
}
