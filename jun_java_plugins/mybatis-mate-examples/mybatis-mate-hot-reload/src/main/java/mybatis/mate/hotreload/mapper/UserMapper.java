package mybatis.mate.hotreload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import mybatis.mate.hotreload.entity.User;

public interface UserMapper extends BaseMapper<User> {

    User selectByUsername(String username);
}
