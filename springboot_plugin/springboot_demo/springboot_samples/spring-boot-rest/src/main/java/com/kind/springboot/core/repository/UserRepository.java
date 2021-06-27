package com.kind.springboot.core.repository;

import com.kind.springboot.core.domain.UserDo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Function:用户数据访问. <br/>
 * Date:     2016年8月11日 下午12:51:16 <br/>
 *
 * @author Wujun
 * @see
 * @since JDK 1.7
 */
@Repository
public interface UserRepository extends CrudRepository<UserDo, Long> {
    /**
     * 根据用户名获取用户.
     *
     * @param username
     * @return
     */
    public UserDo getByUsername(String username);
}
