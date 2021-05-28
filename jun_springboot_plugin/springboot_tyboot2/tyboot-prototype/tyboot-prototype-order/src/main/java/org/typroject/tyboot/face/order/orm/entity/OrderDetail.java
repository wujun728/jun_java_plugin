package org.typroject.tyboot.face.order.orm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

/**
 * <p>
 * 订单详细信息子表；
 * 保存订单相关的明细信息包括：用户名称，昵称，商品名称，商品总数，商家明细信息，订单提交和支付附言等
 * </p>
 *
 * @author Wujun
 * @since 2018-01-14
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("order_detail" )
public class OrderDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单的编号（全局唯一）
     */
    @TableField("ORDER_SN" )
    private String orderSn;
    /**
     * 公网用户姓名
     */
    @TableField("USER_NAME" )
    private String userName;
    /**
     * 商品名称西文逗号分割
     */
    @TableField("PRODUCT_NAMES" )
    private String productNames;
    /**
     * 客户类型（0非会员,1会员,2其它）
     */
    @TableField("CUSTOMER_TYPE" )
    private String customerType;
    /**
     * 订单附言
     */
    @TableField("ORDER_POSTSCRIPT" )
    private String orderPostscript;
    /**
     * 付款备注
     */
    @TableField("PAY_NOTE" )
    private String payNote;
}
