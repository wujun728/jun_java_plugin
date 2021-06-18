package com.lhb.lhbauth.jwt.demo.authentication.social.qq.apapter;

import com.lhb.lhbauth.jwt.demo.authentication.social.qq.service.QQService;
import com.lhb.lhbauth.jwt.demo.authentication.social.qq.model.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author Wujun
 * @description
 * @date 2019/1/3 0003 10:21
 */
public class QQAdapter implements ApiAdapter<QQService> {
    @Override
    public boolean test(QQService api) {
        return true;
    }

    @Override
    public void setConnectionValues(QQService api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();

        //用户的名字
        values.setDisplayName(userInfo.getNickname());
        //用户的头像
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        //个人主页，QQ没个人主页
        values.setProfileUrl(null);
        //用户的ID
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQService qqService) {
        return null;
    }

    @Override
    public void updateStatus(QQService qqService, String s) {

    }
}
