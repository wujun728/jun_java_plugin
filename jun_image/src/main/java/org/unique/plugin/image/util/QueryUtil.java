package org.unique.plugin.image.util;

import java.util.HashMap;
import java.util.Map;

import org.unique.plugin.image.Const;


/**
 * 参数转换类
 * @author rex
 *
 */
public class QueryUtil {

    /**
     * 将{"", "", ""}转换为需要的map
     * @param querys
     * @return
     */
    public static Map<String, String> query2Map(String[] querys){
        Map<String, String> map = new HashMap<String, String>();
        if(null != querys && querys.length > 0){
            map.put(Const.MAP_FIELD, querys[0]);
        }
        
        for(int i=1, len = querys.length; i<len; i+=2){
            if(len >= (i+2)){
                map.put(querys[i], querys[i+1]);
            }
        }
        return map;
    }
    
    public static void main(String[] args) {
		String s = "20!";
		System.out.println(s.endsWith("!"));
	}
}
