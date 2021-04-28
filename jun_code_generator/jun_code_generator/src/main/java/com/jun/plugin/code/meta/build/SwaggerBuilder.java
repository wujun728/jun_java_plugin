package com.jun.plugin.code.meta.build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jun.plugin.code.meta.util.ConfigUtils;

/*****
 *  生成Swagger
 ****/
public class SwaggerBuilder {

    /***
     * ServiceImpl构建
     * @param modelMap
     */
    public static void builder(Map<String,Object> modelMap){
    	List<Map<String,Object>> srcFiles = new ArrayList<Map<String,Object>>();
    	BuilderFactory.getFile(ConfigUtils.TEMPLATE_PATH,srcFiles);
    	for(int i = 0; i < srcFiles.size(); i++) {
            HashMap<String, Object> m = (HashMap<String, Object>) srcFiles.get(i);
            Set<String> set = m.keySet();
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String templateFileName = key;
                String templateFilePathAndName = String.valueOf(m.get(key));
                String templateFilePath = templateFilePathAndName.replace("\\"+templateFileName, "");
                if(key.contains(".json")) {
                	//log.info("templateFilePath="+templateFilePath);;
                	//生成swagger层文件
                	modelMap.put("Table","swagger");
                	BuilderFactory.builder(modelMap,
                			templateFilePath,
                			"swagger.json",
                			ConfigUtils.SWAGGERUI_PATH,
                			".json");
                }else {
                	continue;
                }
            }}
        //swagger的文件名字

    }
}
