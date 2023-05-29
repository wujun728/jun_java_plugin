package mybatis.mate.sharding.jta.atomikos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import mybatis.mate.annotation.Sharding;
import mybatis.mate.sharding.jta.atomikos.entity.Log;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Sharding("test3")
public interface LogMapper extends BaseMapper<Log> {

}
