package com.zh.springbootatomikosjta.dao.test2;

import com.zh.springbootatomikosjta.model.test2.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author Wujun
 * @date 2019/6/6
 */
public interface StockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);

    @Select("SELECT id,product_id productId,`count` FROM stock WHERE product_id = #{productId}")
    Stock findByProductId(@Param("productId") Integer productId);

    @Update("UPDATE stock SET `count` = `count` - 1 WHERE product_id = #{productId}")
    void decrementByProductId(@Param("productId") Integer productId);
}