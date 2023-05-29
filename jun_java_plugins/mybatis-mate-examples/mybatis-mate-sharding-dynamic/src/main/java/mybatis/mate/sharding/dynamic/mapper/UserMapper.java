package mybatis.mate.sharding.dynamic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import mybatis.mate.sharding.dynamic.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
