/*   
 * Project: OSMP
 * FileName: DefaultObjectMapper.java
 * version: V1.0
 */
package com.osmp.http.tool;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.time.DateFormatUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;


/**
 * 自定义objectMapper
 * @author heyu
 *
 */
public class DefaultObjectMapper extends ObjectMapper {

    public DefaultObjectMapper() {
        getSerializerProvider().setNullValueSerializer(new CustomNullValueSerializer());
        SimpleModule module = new SimpleModule("dateSerializer", version());
        module.addSerializer(Date.class, new CustomDateValueSerializer());
        registerModule(module);
    }
    
}

/**
 * 自定义json处理器对null值，设置为""
 * @author heyu
 *
 */
class CustomNullValueSerializer extends JsonSerializer<Object>{

    public void serialize(Object value, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException {
        jgen.writeString("");
    }
    
}

class CustomDateValueSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date arg0, JsonGenerator arg1,
            SerializerProvider arg2) throws IOException,
            JsonProcessingException {
            arg1.writeString(DateFormatUtils.format(arg0, "yyyy-MM-dd HH:mm:ss", Locale.CHINA));
        
    }
    
}
