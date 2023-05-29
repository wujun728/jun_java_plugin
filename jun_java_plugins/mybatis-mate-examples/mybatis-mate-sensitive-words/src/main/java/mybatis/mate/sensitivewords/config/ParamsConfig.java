package mybatis.mate.sensitivewords.config;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.ObjectMapper;
import mybatis.mate.params.IParamsProcessor;
import mybatis.mate.params.SensitiveWordsProcessor;
import mybatis.mate.sensitivewords.entity.SensitiveWords;
import mybatis.mate.sensitivewords.mapper.SensitiveWordsMapper;
import org.ahocorasick.trie.Emit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ParamsConfig {
    @Resource
    private SensitiveWordsMapper sensitiveWordsMapper;

    @Bean
    public IParamsProcessor paramsProcessor() {
        return new SensitiveWordsProcessor() {

            /**
             // 可以指定你需要拦截处理的请求地址，默认 /* 所有请求
             @Override public Collection<String> getUrlPatterns() {
             return super.getUrlPatterns();
             }
             */

            @Override
            public List<String> loadSensitiveWords() {
                // 这里的敏感词可以从数据库中读取，也可以本文方式获取，加载只会执行一次
                return sensitiveWordsMapper.selectList(Wrappers.<SensitiveWords>lambdaQuery().select(SensitiveWords::getWord))
                        .stream().map(t -> t.getWord()).collect(Collectors.toList());
            }

            @Override
            public String handle(String fieldName, String fieldValue, Collection<Emit> emits) {
                if (CollectionUtils.isNotEmpty(emits)) {
                    try {
                        // 这里可以过滤直接删除敏感词，也可以返回错误，提示界面删除敏感词
                        System.err.println("发现敏感词（" + fieldName + " = " + fieldValue + "）" +
                                "存在敏感词：" + toJson(emits));
                        String fv = fieldValue;
                        for (Emit emit : emits) {
                            fv = fv.replaceAll(emit.getKeyword(), "");
                        }
                        return fv;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return fieldValue;
            }
        };
    }

//    @Bean
//    public SensitiveRequestBodyAdvice sensitiveRequestBodyAdvice(IParamsProcessor paramsProcessor) {
//        return new SensitiveRequestBodyAdvice(paramsProcessor);
//    }

//    @Bean
//    @Primary
//    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(IParamsProcessor paramsProcessor) {
//        SimpleModule module = new SimpleModule();
//        // 注入 json 参数反序列化器 ParamsJsonDeserializer
//        module.addDeserializer(String.class, new ParamsJsonDeserializer(paramsProcessor));
//        // 注册解析器
//        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
//        objectMapper.registerModule(module);
//        return new MappingJackson2HttpMessageConverter(objectMapper);
//    }

    private static ObjectMapper OBJECT_MAPPER;

    public static String toJson(Object object) throws Exception {
        if (null == OBJECT_MAPPER) {
            OBJECT_MAPPER = new ObjectMapper();
        }
        return OBJECT_MAPPER.writeValueAsString(object);
    }
}
