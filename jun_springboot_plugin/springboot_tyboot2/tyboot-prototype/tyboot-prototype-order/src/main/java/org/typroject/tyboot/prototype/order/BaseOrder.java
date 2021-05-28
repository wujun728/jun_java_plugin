package org.typroject.tyboot.prototype.order;

import lombok.Data;
import org.typroject.tyboot.face.order.model.OrderInfoModel;
import org.typroject.tyboot.prototype.order.standard.BaseAgency;
import org.typroject.tyboot.prototype.order.standard.Customer;
import org.typroject.tyboot.prototype.order.standard.StandardProduct;
import org.typroject.tyboot.prototype.order.state.OrderStatus;

import java.util.List;
import java.util.Map;


@Data
public class BaseOrder {

    /**
     * 订单编号（全局唯一）
     */
    protected String orderSn;

    /**
     * 订单状态
     */
    protected OrderStatus orderStatus;

    /**
     * 客户信息
     */
    protected Customer customer;

    /**
     * 商品信息
     */
    protected List<StandardProduct> products;

    /**
     * 商家信息
     */
    protected BaseAgency agency;

    /**
     * 订单实体
     */
    protected OrderInfoModel orderInfoModel;

    /**
     * 订单交易信息
     */
    protected Map<String, Object> tradeParams;
}
