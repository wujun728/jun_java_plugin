package com.chentongwei.core.user.enums.msg;

import com.chentongwei.common.enums.IBaseEnum;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 收藏部分字符串常量
 */
public enum ArticleFavoriteMsgEnum implements IBaseEnum {

    /**
     * 收藏部分
     */
    FAVORITE_COUNT_MAX(2100, "每天最多创建30个收藏夹"),
    SAVE_TO_FAVORITE_COUNT_MAX(2101, "每天最多收藏100个文章"),
    ALREADY_COLLECT(2102, "您已在当前收藏夹下收藏过此篇文章")
    ;

    //状态码
    private int code;
    //消息
    private String msg;

    ArticleFavoriteMsgEnum(int code, String msg) {
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
