package com.jun.plugin.picturemanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jun.plugin.picturemanage.entity.ConfEntity;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/10/31 15:11
 */
public interface ConfService extends IService<ConfEntity> {
    boolean set(String key, String value);

    String get(String key, String defaultValue);

    String get(String key);

    boolean del(String key);
}
