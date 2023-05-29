package mybatis.mate.sharding.jta.atomikos.service;

import mybatis.mate.sharding.jta.atomikos.entity.Sku;
import mybatis.mate.sharding.jta.atomikos.mapper.SkuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkuService {
    @Autowired
    private SkuMapper skuMapper;

    /**
     * 减少库存
     */
    public boolean reduceStock(Long id, Integer stock, boolean error) {
        if(error) {
            throw new RuntimeException("制造一个异常，测试事务");
        }
        Sku sku = new Sku();
        sku.setId(id);
        sku.setStock(stock);
        return skuMapper.updateById(sku) > 0;
    }
}
