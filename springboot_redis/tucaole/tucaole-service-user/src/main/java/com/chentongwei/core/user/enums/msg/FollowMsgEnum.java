package com.chentongwei.core.user.enums.msg;

import com.chentongwei.common.enums.IBaseEnum;

/**
 * @author Wujun
 * @Project tucaole
 * @Description:关注枚举常量
 */
public enum FollowMsgEnum implements IBaseEnum {

    /**
     * 关注部分
     */
    FOLLOW_USER_IS_SELF(2200, "被关注者不能是自己")
    ;

    //状态码
    private int code;
    //消息
    private String msg;

    FollowMsgEnum(int code, String msg) {
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
