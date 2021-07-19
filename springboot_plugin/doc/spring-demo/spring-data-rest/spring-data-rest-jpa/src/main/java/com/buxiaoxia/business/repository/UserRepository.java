package com.buxiaoxia.business.repository;

import com.buxiaoxia.business.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xw on 2017/6/4.
 * 2017-06-04 15:33
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
