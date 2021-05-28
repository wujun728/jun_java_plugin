package org.typroject.tyboot.face.trade.orm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 交易账单表
 * </p>
 *
 * @author Wujun
 * @since 2017-08-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("trade_transactions_bill" )
public class TransactionsBill extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 账单编号
     */
    @TableField("BILL_NO" )
    private String billNo;
    /**
     * 账单总金额
     */
    @TableField("AMOUNT" )
    private BigDecimal amount;
    /**
     * 交易类型：支付商品，商家结算，红包，虚拟账户充值
     */
    @TableField("BILL_TYPE" )
    private String billType;
    /**
     * 账单状态；等待结账，已结账
     */
    @TableField("BILL_STATUS" )
    private String billStatus;
    /**
     * 生成时间
     */
    @TableField("CREATE_TIME" )
    private Date createTime;
    /**
     * 结账时间
     */
    @TableField("CHECKOUT_TIME" )
    private Date checkoutTime;
    @TableField("REFUND_TIME" )
    private Date refundTime;
    /**
     * 商家编码
     */
    @TableField("AGENCY_CODE" )
    private String agencyCode;
    /**
     * 是否有退款(如果有退款，则需要根据账单号在退款记录表中查看具体的退款信息）
     */
    @TableField("REFUND" )
    private String refund;


    @TableField("USER_ID" )
    private String userId;

    /**
     * 退款金额
     */
    @TableField("REFUND_AMOUNT" )
    private BigDecimal refundAmount;
}
