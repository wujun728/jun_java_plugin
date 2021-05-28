package com.demo.weixin.api;

import com.demo.weixin.entity.QQUser;
import com.demo.weixin.exception.WeixinException;
import com.qq.connect.QQConnectException;

/**
 * @author Wujun
 * @description
 * @date 2017/7/25
 * @since 1.0
 */
public interface IQQService {

    /**
     * 获取QQ的授权url
     *
     * @return
     */
    String getAuthUrl(String from) throws QQConnectException, WeixinException;

    /**
     * 获取唯一的state
     *
     * @return
     */
    String getUniqueState();

    /**
     * 获取登录授权的qq用户信息，用户信息存于redis，返回openId
     *
     * @param code
     * @param state
     * @return
     */
    String getQQUserOpenId(String code, String state) throws QQConnectException;


    /**
     * 根据openId 获取QQ用户信息
     *
     * @param openId
     * @return
     */
    QQUser getQQUserByOpenId(String openId);
}
