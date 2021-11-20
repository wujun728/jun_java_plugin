package cn.qingyi.Dao;

import cn.qingyi.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * UserDaoSDJ接口的描述
 *
 * @author qingyi xuelongqy@foxmail.com
 * @version V1.0
 * @InterfaceName: UserDaoSDJ
 * @Package cn.qingyi.Dao
 * @Description: 这里用一句话描述这个接口的作用
 * @date 2017/4/24 10:24
 */
public interface UserDaoSDJ extends JpaRepository<User,Integer> {
    //通过姓名查找用户
    public List<User> findByUsername(String username);
}
