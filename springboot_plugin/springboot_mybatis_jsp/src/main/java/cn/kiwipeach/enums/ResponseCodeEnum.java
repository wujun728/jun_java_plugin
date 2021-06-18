package cn.kiwipeach.enums;

import org.springframework.util.StringUtils;

/**
 * Create Date: 2017/11/06
 * Description: 返回统一枚举类型
 *
 * @author Wujun
 */
public enum ResponseCodeEnum {
    SUCCESS("10000","请求成功,系统异常"),
    FAIL("-10000","请求失败"),
    EMPNO_NOT_FOUND("-10001","员工编号不存在"),
    EMP_NUMBER_FORMAT("-10002","员工编号转换异常"),
    EMP_NULL_POINTER("-10003","员工信息空指针异常")
    ;
    private String code;
    private String message;

    ResponseCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 由状态码获取枚举信息
     * @param code 状态码
     * @return 枚举信息
     */
    public static ResponseCodeEnum stateOf(String code) {
        if (StringUtils.isEmpty(code)){
            throw new IllegalArgumentException("responseCodeEnum method stateOf's args can not be empty");
        }
        for (ResponseCodeEnum rce : values()) {
            if (rce.getCode().equals(code)) {
                return rce;
            }
        }
        return null;
    }
}
