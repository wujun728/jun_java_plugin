package com.jun.dao;

import java.io.Serializable;
import java.util.List;

import com.jun.entity.User;

/*************************
 *@ClassName IUserDao
 *@Description TODO
 *@Author yejf
 *@Date 2019-11-29 8:31
 *@Version 1.0
 */
public interface IUserDao {

    void save(User user);

    User findById(Serializable id);

    List<User> findAll();

}
