/*   
 * Project: OSMP
 * FileName: LocalDtdResolver.java
 * version: V1.0
 */
package com.osmp.jdbc.parse;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 本地dtd文件解析,只支持SYSTEM(本地dtd)
 * @author heyu
 *
 */
public class LocalDtdResolver implements EntityResolver {

    @Override
    public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException, IOException {
        if (systemId != null)  
        {  
              
            int index = systemId.lastIndexOf('/');  
            if (index != -1)  
            {  
                systemId = systemId.substring(index + 1);  
            }  
            systemId = "/" + systemId;  
            InputStream istr = Thread.currentThread().getContextClassLoader().getResourceAsStream(systemId);  
            if (istr != null)  
            {  
                return new InputSource(istr);  
            }  
        }  
        return null;
    }

}
