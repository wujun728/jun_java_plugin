package org.typroject.tyboot.face.order.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单主表，订单流转所需的主要字段。
 * 在订单流转结束之后将被转移到订单历史表，当前表只保存流转中的订单信息 model
 * </p>
 *
 * @author Wujun
 * @since 2018-01-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderInfoModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 订单的唯一编号
     */
    private String orderSn;
    /**
     * 账单编号
     */
    private String billNo;
    /**
     * 订单总金额
     */
    private BigDecimal amount;
    /**
     * 商品总价
     */
    private BigDecimal productAmount;
    /**
     * 优惠抵扣金额
     */
    private Integer couponDeduction;
    /**
     * 订单类型（不同的订单类型可能会有不同的主线流程，慎重定义）
     */
    private String orderType;
    /**
     * 订单状态（订单生命周期内的所有状态标识）
     */
    private String orderStatus;
    /**
     * 订单生成时间
     */
    private Date createTime;
    /**
     * 订单支付时间
     */
    private Date payTime;
    /**
     * 支付方式（在交易模块定义)
     */
    private String payMethod;
    /**
     * 支付状态（0未支付,1已支付）
     */
    private String payStatus;
    /**
     * 机构编码
     */
    private String agencyCode;
    /**
     * 订单来源（PUBLIC:公网, AGENCY:商家）
     */
    private String source;


    private String userId;
}
