package com.wang.service.Impl;

import com.wang.dao.OauthClientDetailsDao;
import com.wang.domain.OauthClientDetailsEntity;
import com.wang.service.IClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangxiangyun on 2017/2/14.
 */
@Service
public class ClientDetailsServiceImpl implements IClientDetailsService {
    @Autowired
    private OauthClientDetailsDao oauthClientDetailsDao;
    @Override
    public void save(OauthClientDetailsEntity client) {
        oauthClientDetailsDao.save(client);
    }
}
