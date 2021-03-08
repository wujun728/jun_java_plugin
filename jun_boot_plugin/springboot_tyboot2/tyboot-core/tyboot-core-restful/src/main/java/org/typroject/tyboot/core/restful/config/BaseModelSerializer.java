
package org.typroject.tyboot.core.restful.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.typroject.tyboot.core.foundation.utils.Bean;
import org.typroject.tyboot.core.foundation.utils.DateTimeUtil;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class BaseModelSerializer extends StdSerializer<BaseModel> {
    private final Logger logger = LogManager.getLogger(BaseModelSerializer.class);


    static final String recDate = "recDate";
    static final String recUserId = "recUserId";

    public BaseModelSerializer()
    {
        super(BaseModel.class);
    }

    @Override
    public void serialize(BaseModel value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        try {
            Map map = Bean.BeantoMap(value);
            map.remove(recDate);
            map.remove(recUserId);
            jgen.writeObject(map);
        } catch (Exception e) {

            throw  new IOException(e.getMessage());
        }


    }
}