package org.springrain.frame.util;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 扩展实现jackson的ObjectMapper
 * @author caomei
 *
 */
public class FrameObjectMapper extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FrameObjectMapper(){
		   
		       //为 null 的不转换
		       // this.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL); 
		   this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			   //支持 属性不对应
		   this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES , false);
			   //为bean 为null时不报异常
		   this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
			   //键 支持 不带 双引号 ""
		   this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		       //设置日期格式
		   this.setDateFormat(new SimpleDateFormat(DateUtils.DATETIME_FORMAT));
		   
			   //过滤敏感字符
		   this.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
			   
		   //属性别名:@JsonProperty 序列化/反序列化都有效
		   //排除属性:@JsonIgnore 一般标记在属性或方法上,作用于序列化与反序列化
		   //排除属性:@JsonIgnoreProperties 如果是代理类,由于无法标记在属性或方法上,可以标记在类声明上,也作用于反序列化时的字段解析
		   //属性排序:@JsonPropertyOrder
		   //自定义序列化:@JsonSerialize
		   //自定义反序列化:@JsonDeserialize
		   
		   //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
		   //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=DateUtils.DATETIME_FORMAT,timezone = DateUtils.DATE_TIMEZONE)
	}
}
