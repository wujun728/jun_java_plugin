package org.typroject.tyboot.face.order.orm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.typroject.tyboot.face.order.orm.entity.OrderInfo;

/**
 * <p>
  * 订单主表，订单流转所需的主要字段。
在订单流转结束之后将被转移到订单历史表，当前表只保存流转中的订单信息 Mapper 接口
 * </p>
 *
 * @author Wujun
 * @since 2018-01-14
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

}