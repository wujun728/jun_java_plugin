package springcloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import springcloud.domain.Order;

@Mapper
public interface OrderDao {

    //1 新建订单
    void create(Order order);

    //2 修改订单状态
    void update(@Param("userId") Long userId,@Param("status")Integer status);

}
