package top.linxz.sell.dataobject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.linxz.sell.dataobject.mapper.ProductCategoryMapper;

import java.util.Map;

@Component
public class ProductCategoryDao {
    @Autowired
    ProductCategoryMapper mapper;

    public int insertByMap(Map<String, Object> map) {
        return mapper.insertByMap(map);
    }


}
