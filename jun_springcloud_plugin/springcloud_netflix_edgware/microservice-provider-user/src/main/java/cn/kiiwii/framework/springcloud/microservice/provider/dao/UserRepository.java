package cn.kiiwii.framework.springcloud.microservice.provider.dao;

import cn.kiiwii.framework.springcloud.microservice.provider.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhong on 2017/4/18.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
