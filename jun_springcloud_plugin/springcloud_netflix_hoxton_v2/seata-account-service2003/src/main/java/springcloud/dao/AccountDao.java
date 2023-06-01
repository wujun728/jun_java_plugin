package springcloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

@Mapper
public interface AccountDao {
    int decrease(@Param("userId") Long userId,
                 @Param("money") BigDecimal money);
}
