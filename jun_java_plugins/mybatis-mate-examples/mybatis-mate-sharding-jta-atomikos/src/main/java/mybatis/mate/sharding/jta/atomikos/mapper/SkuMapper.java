package mybatis.mate.sharding.jta.atomikos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import mybatis.mate.annotation.Sharding;
import mybatis.mate.sharding.jta.atomikos.entity.Sku;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Sharding("test2")
public interface SkuMapper extends BaseMapper<Sku> {

}
