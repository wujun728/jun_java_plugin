package cn.springmvc.jpa.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.springmvc.jpa.entity.User;
import cn.springmvc.jpa.entity.UserRole;
import cn.springmvc.jpa.repository.BaseRepository;
import cn.springmvc.jpa.repository.UserRoleRepository;

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
