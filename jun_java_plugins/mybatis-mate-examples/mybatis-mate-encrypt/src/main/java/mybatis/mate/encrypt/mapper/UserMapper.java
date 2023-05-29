package mybatis.mate.encrypt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import mybatis.mate.encrypt.entity.User;
import mybatis.mate.encrypt.entity.vo.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Update("update user set password=#{u.password},email=#{u.email} where id=#{id}")
    Integer testUpdateById(@Param("id") Long id, @Param("u") User user);

    Integer insertBatchTest(@Param("userList") List<User> userList);

    Integer updateBatchUserById(@Param("userList") List<User> userList);

    List<UserDto> selectUserDtoList();

    UserDto selectUserDto(@Param("id") Long id);
}
