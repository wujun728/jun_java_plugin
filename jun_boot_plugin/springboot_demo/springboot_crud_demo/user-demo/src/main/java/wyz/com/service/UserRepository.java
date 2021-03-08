package wyz.com.service;

import org.springframework.data.jpa.repository.JpaRepository;

import wyz.com.domain.User;

import java.util.List;

/**
 * Created by qinyg on 2017/11/20.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    // 根据username查看多个实体类
    public List<User> findByPw(String pw);
}
