/**   
 * Copyright © 2019 dream horse Info. Tech Ltd. All rights reserved.
 * @Package: com.gitee.mybatis.fl.convert
 * @author: flying-cattle  
 * @date: 2019年4月9日 下午8:15:25 
 */
package com.gitee.flying.cattle.mdg.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.gitee.flying.cattle.mdg.entity.BasisInfo;
import com.gitee.flying.cattle.mdg.entity.ResultJson;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明：  创建文件</P>
 * @version: v3.0.0
 * @author: flying-cattle
 * 
 * Modification History:
 * Date         	Author          Version          Description
 *---------------------------------------------------------------*
 * 2019年4月9日      		flying-cattle   v3.0.0           initialize
 */
public class FreemarkerUtil {
	 
	public static ResultJson createFile(BasisInfo dataModel, String templateName, String filePath) {
		ResultJson result=new ResultJson();
		FileWriter out = null;
		String fileName=dataModel.getEntityName()+messageStr(templateName);
		try {
			// 通过FreeMarker的Confuguration读取相应的模板文件
	        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
	        // 设置模板路径
	        configuration.setClassForTemplateLoading(FreemarkerUtil.class, "/freemarker/ftl");
	        // 设置默认字体
	        configuration.setDefaultEncoding("utf-8");
	        // 获取模板
			Template template = configuration.getTemplate(templateName);
			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if(!file.exists()) {
                file.createNewFile();
            }else {
            	result.setCode(-1);
            	result.setMessage("已经存在"+fileName);
            	return result;
            }
			
			//设置输出流
            out = new FileWriter(file);
            //模板输出静态文件
            template.process(dataModel, out);
            result.setCode(1);
        	result.setMessage("创建"+fileName+"成功");
        	return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            if(null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		result.setCode(-1);
    	result.setMessage("创建"+fileName+"失败");
    	return result;
	}
	
	public static String messageStr(String name) {
		if (name.equals("entity.ftl")) {
			name=".java";
		} else if(name.equals("dao.ftl")){
			name="Dao.java";
		} else if(name.equals("mapper.ftl")){
			name="Mapper.xml";
		} else if(name.equals("service.ftl")){
			name="Service.java";
		} else if(name.equals("serviceImpl.ftl")){
			name="ServiceImpl.java";
		} else if(name.equals("controller.ftl")){
			name="Controller.java";
		} 
		return name;
	}
}
