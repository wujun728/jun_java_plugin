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
 * 交易记录表
 * </p>
 *
 * @author Wujun
 * @since 2017-08-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("trade_transactions_record" )
public class TransactionsRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 账单编号
     */
    @TableField("BILL_NO" )
    private String billNo;
    /**
     * 本地交易流水号
     */
    @TableField("SERIAL_NO" )
    private String serialNo;
    /**
     * 交易金额
     */
    @TableField("TRADE_AMOUNT" )
    private BigDecimal tradeAmount;
    /**
     * 商家编码
     */
    @TableField("AGENCY_CODE" )
    private String agencyCode;
    /**
     * 支付完成时间(前端返回交易完成更新此时间)
     */
    @TableField("FINISHED_TIME" )
    private Date finishedTime;
    /**
     * 异步通知时间
     */
    @TableField("ASYNC_FINISH_TIME" )
    private Date asyncFinishTime;
    /**
     * 交易类型：payment:支付(用户支付订单金额到系统)
     */
    @TableField("TRADE_TYPE" )
    private String tradeType;

    /**
     * 交易类型：支付商品，商家结算，红包，虚拟账户充值
     */
    @TableField("BILL_TYPE" )
    private String billType;

    /**
     * 使用的支付方式(支付宝，微信)
     */
    @TableField("PAY_METHOD" )
    private String payMethod;


    @TableField("USER_ID" )
    private String userId;
}
