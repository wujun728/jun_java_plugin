package com.zh.springbootmultidatasource.dao.test1;

import com.zh.springbootmultidatasource.model.test1.Order;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

/**
 * @author Wujun
 * @date 2019/6/6
 */
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    @Select("SELECT id,user_id userId,product_id productId,create_time createTime,rmk FROM `order` WHERE product_id = #{productId}")
    Order findByProductId(Integer productId);
}