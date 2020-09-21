/*   
 * Project: OSMP
 * FileName: UrlCodeConverter.java
 * version: V1.0
 */
package com.osmp.utils.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Url参数编码转换
 * @author heyu
 *
 */
public class UrlCodeConverter {
    
    /**
     * url参数编码
     * @param source 源字符串
     * @param encoding 编码
     * @return
     */
    public final static String encode(String source,String encoding){
        String result = null;
        try {
            result = URLEncoder.encode(source, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * url参数解码
     * @param source 源字符串
     * @param encoding 编码
     * @return
     */
    public final static String decode(String source,String encoding){
        String result = null;
        try {
            result = URLDecoder.decode(source, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
