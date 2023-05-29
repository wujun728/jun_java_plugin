package mybatis.mate.sensitive.jackson.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import mybatis.mate.sensitive.jackson.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
