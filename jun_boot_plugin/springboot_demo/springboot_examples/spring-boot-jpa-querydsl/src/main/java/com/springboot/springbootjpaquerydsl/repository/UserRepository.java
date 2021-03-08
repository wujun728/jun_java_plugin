package com.springboot.springbootjpaquerydsl.repository;

import com.springboot.springbootjpaquerydsl.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 *
 * @Date: 2019/9/25
 * @Time: 23:06
 * @email: inwsy@hotmail.com
 * Description:
 */
public interface UserRepository extends JpaRepository<UserModel, String> {
}
