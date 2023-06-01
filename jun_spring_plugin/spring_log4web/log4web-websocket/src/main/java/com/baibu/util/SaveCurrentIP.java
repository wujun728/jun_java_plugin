package com.baibu.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/8/11.
 */
public class SaveCurrentIP {

    /**
     * 保存当前ip访问记录
     */
    public static String currentIp(HttpServletRequest httpRequest){
        String ip = httpRequest.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpRequest.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpRequest.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpRequest.getRemoteAddr();
        }
        return StrList(ip)[0];
    }

    public static String[] StrList(String valStr){
        int i = 0;
        String TempStr = valStr;
        String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
        valStr = valStr + ",";
        while (valStr.indexOf(',') > 0)
        {
            returnStr[i] = valStr.substring(0, valStr.indexOf(','));
            valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());

            i++;
        }
        return returnStr;
    }

}
