package io.github.wujun728.generator.entity;

import lombok.Data;

import java.util.Map;

/**
 * Post data - ParamInfo
 */
@Data
public class ParamInfo {

    private String tableSql;
    private Map<String,Object> options;

    @Data
    public static class NAME_CASE_TYPE {
        public static String CAMEL_CASE = "CamelCase";
        public static String UNDER_SCORE_CASE = "UnderScoreCase";
        public static String UPPER_UNDER_SCORE_CASE = "UpperUnderScoreCase";
    }

}
