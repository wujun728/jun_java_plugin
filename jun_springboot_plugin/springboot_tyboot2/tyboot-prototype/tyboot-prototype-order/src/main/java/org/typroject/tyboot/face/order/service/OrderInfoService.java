package org.typroject.tyboot.face.order.service;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.foundation.exception.BaseException;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.face.order.model.OrderInfoModel;
import org.typroject.tyboot.face.order.orm.dao.OrderInfoMapper;
import org.typroject.tyboot.face.order.orm.entity.OrderInfo;

import java.util.Date;


/**
 * <p>
 * 订单主表，订单流转所需的主要字段。
在订单流转结束之后将被转移到订单历史表，当前表只保存流转中的订单信息 服务类
 * </p>
 *
 * @author Wujun
 * @since 2018-01-14
 */
@Component
public class OrderInfoService extends BaseService<OrderInfoModel,OrderInfo,OrderInfoMapper>
{


    @Transactional(rollbackFor = {Exception.class, BaseException.class})
    public OrderInfoModel createOrderInfo(OrderInfoModel orderInfoModel)throws Exception
    {

        //创建订单
        orderInfoModel.setAgencyCode(RequestContext.getAgencyCode());
        orderInfoModel.setUserId(RequestContext.getExeUserId());
        orderInfoModel.setCreateTime(new Date());
        this.createWithModel(orderInfoModel);

        //创建订单商品关联

        //创建
        return null;
    }
}
