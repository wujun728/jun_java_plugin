package com.jun.plugin.picturemanage.util;

import com.alibaba.fastjson.JSONObject;
import com.jun.plugin.picturemanage.conf.Constant;
import com.jun.plugin.picturemanage.entity.UserEntity;
import com.jun.plugin.picturemanage.service.ConfService;

import org.apache.commons.lang.StringUtils;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/11/1 16:06
 */
public class UserUtil {

    public static UserEntity getUserInfo() {
        ConfService bean = SpringContextUtils.getBean(ConfService.class);
        String json = bean.get(Constant.USER_KEY_NAME);
        if (StringUtils.isBlank(json)) {
            return null;
        }
        UserEntity entity = JSONObject.parseObject(json, UserEntity.class);
        return entity;
    }


    public static UserEntity setUserInfo(String username, String password) {
        ConfService bean = SpringContextUtils.getBean(ConfService.class);
        UserEntity entity = new UserEntity();
        entity.setPassword(password);
        entity.setUsername(username);
        boolean set = bean.set(Constant.USER_KEY_NAME, JSONObject.toJSONString(entity));
        return set ? entity : null;
    }

}
