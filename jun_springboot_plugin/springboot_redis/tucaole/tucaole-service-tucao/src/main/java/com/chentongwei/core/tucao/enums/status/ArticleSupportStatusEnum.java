package com.chentongwei.core.tucao.enums.status;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 文章点赞枚举
 */
public enum ArticleSupportStatusEnum {
    /** 没点赞也没反对 不高亮 */
    NO(-1),
    /** 点赞 */
    SUPPORT(1),
    /** 反对 */
    OPPOSE(0)
    ;

    private int value;

    ArticleSupportStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
