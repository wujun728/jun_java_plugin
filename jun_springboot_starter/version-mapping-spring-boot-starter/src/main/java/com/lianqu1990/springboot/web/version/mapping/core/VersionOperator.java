package com.lianqu1990.springboot.web.version.mapping.core;

/**
 * @author hanchao
 * @date 2018/3/9 13:48
 */
public enum VersionOperator {
    NIL(""),
    LT("<"),
    GT(">"),
    LTE("<="),
    GTE(">="),
    NE("!="),
    EQ("==");
    private String code;
    VersionOperator(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static VersionOperator parse(String code){
        for (VersionOperator operator : VersionOperator.values()) {
            if(operator.getCode().equalsIgnoreCase(code)){
                return operator;
            }
        }
        return null;
    }

}
