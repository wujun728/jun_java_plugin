package com.chentongwei.core.tucao.enums.msg;

import com.chentongwei.common.enums.IBaseEnum;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 文章点赞枚举
 */
public enum ArticleCommentMsgEnum implements IBaseEnum {
    CHECK_COMMENT_COUNT(3300, "15秒内只能评论一次")
    ;

    //状态码
    private int code;
    //消息
    private String msg;

    ArticleCommentMsgEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
