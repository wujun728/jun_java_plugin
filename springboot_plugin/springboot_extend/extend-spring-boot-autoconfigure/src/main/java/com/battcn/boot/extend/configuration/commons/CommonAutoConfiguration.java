//package com.battcn.boot.extend.configuration.commons;
//
//import com.battcn.boot.extend.configuration.sensitive.SensitiveProperties;
//import com.battcn.boot.extend.configuration.xss.XssProperties;
//import com.battcn.boot.extend.utils.sensitive.SensitiveProcessor;
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import org.apache.commons.text.StringEscapeUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//
//import java.io.IOException;
//
///**
// * XSS 与 Sensitive 的公共支持
// *
// * @author Levin
// * @since 2019/1/2 0002
// */
//public class CommonAutoConfiguration {
//
//    @Autowired(required = false)
//    private SensitiveProperties sensitiveProperties;
//    @Autowired(required = false)
//    private XssProperties xssProperties;
//
//    @Bean
//    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
//        //解析器
//        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
//        //注册xss解析器
//        SimpleModule simpleModule = new SimpleModule("commonJsonSerializer");
//        simpleModule.addSerializer(new CommonJsonSerializer(sensitiveProperties, xssProperties));
//        objectMapper.registerModule(simpleModule);
//        return objectMapper;
//    }
//
//    static class CommonJsonSerializer extends JsonSerializer<String> {
//        private SensitiveProperties sensitiveProperties;
//        private XssProperties xssProperties;
//
//        CommonJsonSerializer(SensitiveProperties sensitiveProperties, XssProperties xssProperties) {
//            this.sensitiveProperties = sensitiveProperties;
//            this.xssProperties = xssProperties;
//        }
//
//        @Override
//        public Class<String> handledType() {
//            return String.class;
//        }
//
//        @Override
//        public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//            if (value != null) {
//                if (xssProperties != null && sensitiveProperties != null) {
//                    final SensitiveProperties.Filter filter = sensitiveProperties.getFilter();
//                    // 先进行 xss
//                    if (xssProperties.getOrder() >= filter.getOrder()) {
//                        value = StringEscapeUtils.escapeHtml4(value);
//                    } else {
//                        value = SensitiveProcessor.getInstance().format(value, sensitiveProperties.getReplacement());
//                    }
//                } else {
//                    if (sensitiveProperties != null) {
//                        value = SensitiveProcessor.getInstance().format(value, sensitiveProperties.getReplacement());
//                    }
//                    if (xssProperties != null) {
//                        value = StringEscapeUtils.escapeHtml4(value);
//                    }
//                }
//                jsonGenerator.writeString(value);
//            }
//        }
//    }
//}
