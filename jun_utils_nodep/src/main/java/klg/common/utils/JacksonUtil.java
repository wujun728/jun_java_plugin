package klg.common.utils;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class JacksonUtil {
	// 数据
	private static final ObjectMapper DATA_MAPPER = new ObjectMapper();
	
	// 控制台打印，或者格式化输出
	private static final ObjectMapper PRINTER_MAPPER=new ObjectMapper();
	

	
	static{
		// 对象的所有字段全部列入
		DATA_MAPPER.setSerializationInclusion(Include.ALWAYS);
		// 允许双引号
		DATA_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		// 允许转移的特殊字符
		DATA_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		// 允许单引号
		DATA_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		// @Transient 忽略
		DATA_MAPPER.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
		
		
		// iso 时间输出
		PRINTER_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		// 忽略null值
		PRINTER_MAPPER.setSerializationInclusion(Include.NON_NULL);
	}
	
	public static <T> T parseObject(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException{
		return DATA_MAPPER.readValue(json, clazz);
	}
	
	public static String toJSONString(Object object) throws JsonProcessingException{
		return DATA_MAPPER.writeValueAsString(object);
	}
	
	public static String toJSONStringPretty(Object object) throws JsonProcessingException{
		return DATA_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
	}
	
	
	public static String printJSON(Object object) throws JsonProcessingException{
		return PRINTER_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
	}
	
}