package cn.springmvc.jpa.common.utils.fmt.json;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author Vincent.Wang
 *
 */
public class CustomDateSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date createTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        Long time = createTime.getTime() / 1000;
        jsonGenerator.writeNumber(time);
    }

}
