package io.github.wujun728.admin.util;

import cn.hutool.core.io.IoUtil;
import io.github.wujun728.admin.common.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.*;
import java.util.Map.Entry;

@Slf4j
public class TemplateUtil {
	public static String getValue(String template,Map<String,? extends Object> params){
		Context context  = new VelocityContext();
		for(Entry<String,? extends Object> en : params.entrySet()){
			context.put(en.getKey(), en.getValue());
		}
		context.put("jq","$");
		context.put("service",SpringContextUtil.getBean(TemplateService.class));

		StringWriter sw = new StringWriter();

		try {
			Velocity.evaluate(context, sw, "velocity", template);
			sw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sw.toString();
	}

	public static String getValueByPath(String path,Map<String,Object> values){
		Context context  = new VelocityContext();
		//以下都是 模板中的特殊符号 直接使用的话就会冲突报错
		context.put("jq", "$");
		context.put("gt", "get");
		context.put("st", "set");
		context.put("l", "(");
		context.put("r", ")");

		//初始化参数
		Properties properties=new Properties();
		//设置velocity资源加载方式为class
		properties.setProperty("resource.loader", "class");
		//设置velocity资源加载方式为file时的处理类
		properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		//实例化一个VelocityEngine对象
		VelocityEngine velocityEngine=new VelocityEngine(properties);

		for(Entry<String,Object> en : values.entrySet()){
			context.put(en.getKey(), en.getValue());
		}

		StringWriter writer = new StringWriter();

		velocityEngine.mergeTemplate(path, "UTF-8", context, writer);
		String out = writer.toString();
		try {
			writer.close();
		} catch (IOException e) {
			log.error("根据模板获取内容失败",e);
		}
		return out;
	}

	public static String getUi(String path,Map<String,? extends Object> params){
		InputStream in = TemplateUtil.class.getClassLoader().getResourceAsStream("ui-json-template/"+path);
		String template = IoUtil.readUtf8(in);
		IoUtil.close(in);
		return TemplateUtil.getValue(template,params);
	}
	//definitions 属性只允许在最顶层定义
	public static void filterDefinitions(Map<String,Object> json){
		filterDefinitions(json,json);
	}
	public static void filterDefinitions(Map<String,Object> json,Map<String,Object> top){
		Map<String,Object> definitions = (Map<String, Object>) top.get("definitions");
		if(definitions == null){
			definitions = new HashMap<>();
			top.put("definitions",definitions);
		}
		Set<Entry<String, Object>> entries = json.entrySet();
		for(Entry<String, Object> en:entries){
			Object value = en.getValue();
			if(en.getKey().equals("definitions")){
				if(top != json ){
					definitions.putAll((Map<String, ?>) value);

				}
			}else if(value instanceof Map){
				filterDefinitions((Map<String, Object>) value,top);
			}else if(value instanceof List){
				for(Object item: ((List) value)){
					if(item instanceof Map){
						filterDefinitions((Map<String, Object>) item,top);
					}
				}
			}
		}
		if(json != top){
			json.remove("definitions");
		}
	}
}
