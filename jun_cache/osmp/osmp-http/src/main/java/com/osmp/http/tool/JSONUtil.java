/*   
 * Project: OSMP
 * FileName: JSONUtil.java
 * version: V1.0
 */
package com.osmp.http.tool;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class JSONUtil {
    public static JSONObject string2JSON(String source){
        if(source == null) return null;
        try{
            return JSONObject.fromObject(source);
        }catch(JSONException e){}
        return null;
    }
}
