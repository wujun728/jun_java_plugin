package com.chentongwei.common.enums;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 通用枚举信息接口
 */
public interface IBaseEnum {

    /**
     * 获取状态码
     *
     * @return
     */
    int getCode();

    /**
     * 获取消息
     *
     * @return
     */
    String getMsg();
}
