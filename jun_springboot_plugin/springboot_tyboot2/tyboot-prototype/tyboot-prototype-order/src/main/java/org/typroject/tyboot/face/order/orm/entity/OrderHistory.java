package org.typroject.tyboot.face.order.orm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

import java.util.Date;

/**
 * <p>
 * 订单归档表
 * </p>
 *
 * @author Wujun
 * @since 2018-01-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("order_history" )
public class OrderHistory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单的唯一编号
     */
    @TableField("ORDER_SN" )
    private String orderSn;
    /**
     * 账单编号
     */
    @TableField("BILL_NO" )
    private String billNo;
    /**
     * 订单总金额
     */
    @TableField("AMOUNT" )
    private Integer amount;
    /**
     * 商品总价
     */
    @TableField("PRODUCT_AMOUNT" )
    private Integer productAmount;
    /**
     * 优惠抵扣金额
     */
    @TableField("COUPON_DEDUCTION" )
    private Integer couponDeduction;
    /**
     * 订单类型（不同的订单类型可能会有不同的主线流程，慎重定义）
     */
    @TableField("ORDER_TYPE" )
    private String orderType;
    /**
     * 订单状态（订单生命周期内的所有状态标识）
     */
    @TableField("ORDER_STATUS" )
    private String orderStatus;
    /**
     * 订单生成时间
     */
    @TableField("CREATE_TIME" )
    private Date createTime;
    /**
     * 订单支付时间
     */
    @TableField("PAY_TIME" )
    private Date payTime;
    /**
     * 支付方式（在交易模块定义)
     */
    @TableField("PAY_METHOD" )
    private String payMethod;
    /**
     * 支付状态（0未支付,1已支付）
     */
    @TableField("PAY_STATUS" )
    private String payStatus;
    /**
     * 机构编码
     */
    @TableField("AGENCY_CODE" )
    private String agencyCode;
    /**
     * 订单来源（PUBLIC:公网, AGENCY:商家）
     */
    @TableField("SOURCE" )
    private String source;

    @TableField("USER_ID" )
    private String userId;
}
