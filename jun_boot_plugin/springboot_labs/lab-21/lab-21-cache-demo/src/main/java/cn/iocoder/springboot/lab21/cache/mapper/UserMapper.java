package cn.iocoder.springboot.lab21.cache.mapper;

import cn.iocoder.springboot.lab21.cache.dataobject.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserDO> {

    @Cacheable(value = "users", key = "#id")
    UserDO selectById(Integer id);

    @CachePut(value = "users", key = "#user.id")
    default UserDO insert0(UserDO user) {
        // 插入记录
        this.insert(user);
        // 返回用户
        return user;
    }

}
