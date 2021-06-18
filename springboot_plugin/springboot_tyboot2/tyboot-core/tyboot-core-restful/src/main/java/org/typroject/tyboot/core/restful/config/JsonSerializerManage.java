package org.typroject.tyboot.core.restful.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by yaohelang on 2019/1/18.
 */
@JsonComponent
public class JsonSerializerManage {

    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        /**
         * 序列换成json时,将所有的long变成string
         * 因为js中得数字类型不能包含所有的java long值
         */
        /**
         * 序列换成json时,将所有的long变成string
         * 因为js中得数字类型不能包含所有的java long值
         */
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

        CustomDateSerializer customDateSerializer = new CustomDateSerializer();
        simpleModule.addSerializer(Date.class,customDateSerializer);



        //序列化将BigInteger转String类型
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        //序列化将BigDecimal转String类型
        simpleModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);

        simpleModule.addSerializer(BaseModel.class,new BaseModelSerializer());


        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

}