package com.jun.common.json;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.jun.common.Globarle;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

/**
 * Json工具类
 * @author Wujun
 * @createTime   2011-5-8 下午03:06:51
 */
public class JsonUtils {
	/**
	 * List转换成Json字符串
	 */
	public static String listToJson(List list,final String[] filters){
		if(null == list || list.size() == 0){
			return "";
		}
		JSONArray jsonArray = null;
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(
			Date.class,   
		    new DateJsonValueProcessor(Globarle.DEFAULT_DATE_FORMAT)
		);   
		if(null != filters && filters.length != 0){
			config.setJsonPropertyFilter(new PropertyFilter(){
				public boolean apply(Object source, String name, Object value) {
					for (String field : filters) {
						if(name.equals(field)){
							return true;
						}
						if(value instanceof Set && ((Set)value).size() == 0){
							return true;
						}
						if(value instanceof Object[] && ((Object[])value).length == 0){
							return true;
						}
					}
					return false;
				}
			});
			jsonArray = JSONArray.fromObject(list,config);
		}
		else{
			jsonArray = JSONArray.fromObject(list,config);
		}
		return jsonArray.toString();
	}
	
	/**
	 * Object转换成Json字符串
	 * @param object 源对象
	 * @param ignorFields 忽略属性数组
	 * @return
	 */
	public static String objectToJson(Object object,final String[] ignorFields){
		if(null == object){
			return "";
		}
		JSONObject jsonObject = null;
		if(null != ignorFields && ignorFields.length != 0){
			JsonConfig config = new JsonConfig();
			config.setJsonPropertyFilter(new PropertyFilter(){
				public boolean apply(Object source, String name, Object value) {
					for (String field : ignorFields) {
						if(name.equals(field)){
							return true;
						}
						if(value instanceof Set && ((Set)value).size() == 0){
							return true;
						}
						if(value.getClass().isArray() && ((Object[])value).length == 0){
							return true;
						}
					}
					return false;
				}
			});
			jsonObject = JSONObject.fromObject(object, config);
		}
		else{
			jsonObject = JSONObject.fromObject(object);
		}
		return jsonObject.toString();
	}
	
	/**
	 * 输出Json字符串到客户端
	 * @param jsonModel json模型
	 * @param response 输出对象
	 */
	public static void outputJsonString(JSONModel model,HttpServletResponse response) throws IOException{
		StringBuffer sb = new StringBuffer();
		if(!model.isQueryAction()){
			sb.append("{").append(model.SUCCESS_FIELD).append(":")
			       .append(model.isSuccess()).append(",").append(model.MESSAGE_FIELD).append(":'")
			       .append(model.getMessage()).append("'}");
		}
		else{
			sb.append("{").append(model.TOTAL_FIELD).append(":").append(model.getTotalCount())
			.append(",").append(model.ROOT_FIELD).append(":").append(model.getJsonString()).append("}");
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-json");
		//response.setContentType("text/html");
		response.getWriter().write(sb.toString());
	}
}
