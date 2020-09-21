package com.chentongwei.common.enums;

/**
 * @author TongWei.Chen 2017-05-16 13:29:48
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
