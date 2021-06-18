package com.demo.weixin.clients;

import com.demo.weixin.entity.QQUser;
import com.qq.connect.QQConnectException;

/**
 * @author Wujun
 * @description
 * @date 2017/7/27
 * @since 1.0
 */
public interface IQQClient {

    QQUser getQQUser(String token, String openId) throws QQConnectException;

}
