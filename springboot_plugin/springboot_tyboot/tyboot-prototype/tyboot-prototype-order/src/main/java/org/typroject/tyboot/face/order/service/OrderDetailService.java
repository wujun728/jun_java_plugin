package org.typroject.tyboot.face.order.service;


import org.springframework.stereotype.Component;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.face.order.model.OrderDetailModel;
import org.typroject.tyboot.face.order.orm.dao.OrderDetailMapper;
import org.typroject.tyboot.face.order.orm.entity.OrderDetail;


/**
 * <p>
 * 订单详细信息子表；
保存订单相关的明细信息包括：用户名称，昵称，商品名称，商品总数，商家明细信息，订单提交和支付附言等 服务类
 * </p>
 *
 * @author Wujun
 * @since 2018-01-14
 */
@Component
public class OrderDetailService extends BaseService<OrderDetailModel,OrderDetail,OrderDetailMapper>
{


}
