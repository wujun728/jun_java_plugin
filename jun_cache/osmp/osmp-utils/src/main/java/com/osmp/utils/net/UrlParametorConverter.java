/*   
 * Project: OSMP
 * FileName: UrlParametorConverter.java
 * version: V1.0
 */
package com.osmp.utils.net;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class UrlParametorConverter {
    
    /**
     * url查询字符串转化为map
     * @param queryString 查询字符串
     * @return
     */
    public final static Map<String, String> queryString2Map(String queryString){
        if(queryString==null||queryString.trim().length()<=0){  
            return null;  
        }  
        Map<String,String> map =new HashMap<String,String>();  
        String[] params=queryString.split("&");  
        for(int i=0;i<params.length;i++){  
            String[] arr=params[i].split("=");  
            map.put(UrlCodeConverter.decode(arr[0],"UTF-8"), UrlCodeConverter.decode(arr[1],"UTF-8"));  
        }  
        return map;  
    }
    
    /**
     * map转化为url查询字符串
     * @param map
     * @return
     */
    public final static String map2QueryString(Map<String,String> map){
        if(map == null || map.size() == 0){  
            return null;  
        }  
        StringBuffer sb=new StringBuffer();  
        Iterator<String> it =map.keySet().iterator();  
        while(it.hasNext()){  
            String key=it.next();  
            String value =map.get(key);  
            if(sb.length()<=0){  
                sb.append(key+"="+value);  
            }else{  
                sb.append("&"+key+"="+value);  
            }  
        }  
        return sb.toString();  
    }
}
