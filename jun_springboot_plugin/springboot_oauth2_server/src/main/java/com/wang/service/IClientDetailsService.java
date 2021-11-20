package com.wang.service;

import com.wang.domain.OauthClientDetailsEntity;

/**
 * Created by wangxiangyun on 2017/2/14.
 */
public interface IClientDetailsService {
    void save(OauthClientDetailsEntity client);
}
