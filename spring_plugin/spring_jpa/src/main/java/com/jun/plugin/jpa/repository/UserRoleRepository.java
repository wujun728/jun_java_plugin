package com.jun.plugin.jpa.repository;

import com.jun.plugin.jpa.entity.User;
import com.jun.plugin.jpa.entity.UserRole;

/**
 * 
 * @author Vincent.wang
 *
 */
public interface UserRoleRepository {

    public void add(UserRole userRole);


    public void updatePassword(User u);

}
