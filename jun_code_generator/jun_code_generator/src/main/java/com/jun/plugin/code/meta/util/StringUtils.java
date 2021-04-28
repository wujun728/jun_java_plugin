package com.jun.plugin.code.meta.util;

import com.jun.plugin.code.meta.build.TemplateBuilder;

/****
 * @Description:字符串处理
 *****/
public class StringUtils {

    /***
     * 首字母大写
     * @param str
     * @return
     */
    public static String firstUpper(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }

    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String firstLower(String str){
        return str.substring(0,1).toLowerCase()+str.substring(1);
    }

    /***
     * 移除tab_,tb_
     * @return
     */
    public static String replaceTab(String str){
    	str = str.toLowerCase().replaceFirst("tab_","").replaceFirst("tb_","").replaceFirst("t_","");
    	for(String x : ConfigUtils.TABLEREMOVEPREFIXES.split(",")){
    		str = str.replaceFirst(x.toLowerCase(),"");
    	}
        return str;
    }
    public static String replaceRow(String str){
    	str = str.toLowerCase().replaceFirst("tab_","").replaceFirst("tb_","").replaceFirst("t_","");
    	for(String x : ConfigUtils.ROWREMOVEPREFIXES.split(",")){
    		str = str.replaceFirst(x.toLowerCase(),"");
    	}
    	return str;
    }
    public static Boolean checkTab(String str){
    	str = str.toLowerCase();
    	for(String x : ConfigUtils.SKIPTABLE.split(",")){
    		if(str.contains(x.toLowerCase())){
    			return true;
    		}
    	}
    	if(ConfigUtils.INCLUETABLES.equals("*")){
			return false;
		}
    	for(String x : ConfigUtils.INCLUETABLES.split(",")){
    		if(str.contains(x.toLowerCase())){
    			return false;
    		}
    	}
        return true;
    }

    /***
     * 将下划线替换掉
     * @param str
     * @return
     */
    public static String replace_(String str){
        //根据下划线分割
        String[] split = str.split("_");
        //循环组装
        String result = split[0];
        if(split.length>1){
            for (int i = 1; i < split.length; i++) {
                result+=firstUpper(split[i]);
            }
        }
        return result;
    }
}
