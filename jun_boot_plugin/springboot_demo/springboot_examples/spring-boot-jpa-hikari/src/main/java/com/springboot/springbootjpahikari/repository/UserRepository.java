package com.springboot.springbootjpahikari.repository;

import com.springboot.springbootjpahikari.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Date: 2019/9/17 16:51
 * @Version: 1.0
 * @Desc:
 */
public interface UserRepository extends JpaRepository<UserModel, String> {
    UserModel getByIdIs(String id);

    UserModel findByNickName(String nickName);

    int countByAge(int age);

    List<UserModel> findByNickNameLike(String nickName);
}
