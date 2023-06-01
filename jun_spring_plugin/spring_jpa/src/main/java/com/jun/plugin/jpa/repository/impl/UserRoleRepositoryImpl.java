package com.jun.plugin.jpa.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jun.plugin.jpa.entity.User;
import com.jun.plugin.jpa.entity.UserRole;
import com.jun.plugin.jpa.repository.BaseRepository;
import com.jun.plugin.jpa.repository.UserRoleRepository;

/**
 * @author Wujun
 *
 */
@Repository
public class UserRoleRepositoryImpl implements UserRoleRepository {

    @Autowired
    protected BaseRepository baseRepository;

    @Override
    public void add(UserRole userRole) {
        baseRepository.save(userRole);
    }


    @Override
    public void updatePassword(User u) {
        baseRepository.saveOrUpdate(u);
    }

}
