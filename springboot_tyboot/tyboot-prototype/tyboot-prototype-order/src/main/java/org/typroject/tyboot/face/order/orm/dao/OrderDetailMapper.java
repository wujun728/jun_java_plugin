package org.typroject.tyboot.face.order.orm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.typroject.tyboot.face.order.orm.entity.OrderDetail;

/**
 * <p>
  * 订单详细信息子表；
保存订单相关的明细信息包括：用户名称，昵称，商品名称，商品总数，商家明细信息，订单提交和支付附言等 Mapper 接口
 * </p>
 *
 * @author Wujun
 * @since 2018-01-14
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}