package com.chentongwei.core.system.enums.msg;

import com.chentongwei.common.enums.IBaseEnum;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 举报模块常量枚举
 */
public enum ReportMsgEnum implements IBaseEnum {
    ALREADY_REPORT(1000, "您今天已经举报过此文章，请耐心等待审核"),
    REPORT_COUNT_MAX(1001, "每个用户每天最多举报10次")
    ;

    //状态码
    private int code;
    //提示语
    private String msg;

    ReportMsgEnum(int code, String msg) {
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
