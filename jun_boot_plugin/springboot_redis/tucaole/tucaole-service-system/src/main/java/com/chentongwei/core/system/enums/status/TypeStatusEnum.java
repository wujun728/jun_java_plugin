package com.chentongwei.core.system.enums.status;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 举报功能的类型枚举状态; 1：吐槽的文章；2：吐槽文章的评论；
 */
public enum TypeStatusEnum {
    /** 吐槽的文章 */
    ARTICLE(1),
    /** 吐槽文章的评论 */
    ARTICLE_COMMENT(2)
    ;

    private int value;

    TypeStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

}
