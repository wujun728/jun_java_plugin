package cn.springmvc.jpa.common.utils.fmt.json;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 
 * @author Wujun
 *
 */
public class CustomDateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        Long time = parser.getLongValue();
        Date createTime = new Date(time * 1000);
        return createTime;
    }

}
