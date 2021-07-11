package cc.mrbird.febs.common.entity;

import lombok.Getter;

/**
 * @author MrBird
 */
@Getter
public enum DesensitizationType {
    /**
     * 手机号脱敏
     */
    PHONE("phone", "11位手机号", "^(\\d{3})\\d{4}(\\d{4})$", "$1****$2"),
    /**
     * 身份证号脱敏
     */
    ID_CARD("idCard", "16或者18身份证号", "^(\\d{4})\\d{8,10}(\\w{4})$", "$1****$2"),
    /**
     * 银行卡号脱敏
     */
    BANK_CARD("bankCardNo", "银行卡号", "^(\\d{4})\\d*(\\d{4})$", "$1****$2"),
    /**
     * 姓名脱敏
     */
    NAME("name", "真实姓名", "(?<=.{1}).*(?=.{1})", "*"),
    /**
     * 邮箱脱敏
     */
    EMAIL("email", "电子邮箱", "(\\w+)\\w{5}@(\\w+)", "$1***@$2");

    String type;
    String describe;
    String[] regular;

    DesensitizationType(String type, String describe, String... regular) {
        this.type = type;
        this.describe = describe;
        this.regular = regular;
    }
}
