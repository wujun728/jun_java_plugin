/*   
 * Project: OSMP
 * FileName: DefaultObjectMapper.java
 * version: V1.0
 */
package com.osmp.config;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;


/**
 * 自定义objectMapper
 * @author heyu
 *
 */
public class DefaultObjectMapper extends ObjectMapper {

    public DefaultObjectMapper() {
        getSerializerProvider().setNullValueSerializer(new CustomNullValueSerializer());
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
