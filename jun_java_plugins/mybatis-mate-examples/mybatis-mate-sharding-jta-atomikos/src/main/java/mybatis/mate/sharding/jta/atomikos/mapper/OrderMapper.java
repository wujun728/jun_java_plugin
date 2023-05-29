package mybatis.mate.sharding.jta.atomikos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import mybatis.mate.sharding.jta.atomikos.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
