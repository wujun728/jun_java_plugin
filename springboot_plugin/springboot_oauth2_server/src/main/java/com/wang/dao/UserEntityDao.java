package com.wang.dao;

import com.wang.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2017/2/11 0011.
 */
public interface UserEntityDao extends JpaRepository<UserEntity,Integer> {
    /**
     * 匹配姓名得到用户
     * @param username
     * @return
     */
    UserEntity findByUsername(String username);
}
