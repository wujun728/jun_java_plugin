package com.wang.dao;

import com.wang.domain.OauthClientDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wangxiangyun on 2017/2/14.
 */
public interface OauthClientDetailsDao  extends JpaRepository<OauthClientDetailsEntity,String>{
}
