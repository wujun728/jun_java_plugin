package cn.springmvc.jpa.repository;

import cn.springmvc.jpa.entity.User;
import cn.springmvc.jpa.entity.UserRole;

/**
 * 
 * @author Vincent.wang
 *
 */
public interface UserRoleRepository {

    public void add(UserRole userRole);


    public void updatePassword(User u);

}
