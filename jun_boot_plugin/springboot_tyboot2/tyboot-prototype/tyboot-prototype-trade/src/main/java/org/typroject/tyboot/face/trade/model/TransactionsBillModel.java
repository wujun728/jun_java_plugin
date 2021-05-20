package org.typroject.tyboot.face.trade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 交易账单表 model
 * </p>
 *
 * @author Wujun
 * @since 2017-08-31
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionsBillModel extends BaseModel {

    private static final long serialVersionUID = 15615648132L;

    /**
     * 账单编号L
     */
    private String billNo;
    /**
     * 账单总金额
     */
    private BigDecimal amount;
    /**
     * 交易类型：支付商品，商家结算，红包，虚拟账户充值
     */
    private String billType;
    /**
     * 账单状态；等待结账，已结账
     */
    private String billStatus;
    /**
     * 生成时间
     */
    private Date createTime;
    /**
     * 结账时间
     */
    private Date checkoutTime;
    private Date refundTime;
    /**
     * 商家编码
     */
    private String agencyCode;
    /**
     * 是否有退款(如果有退款，则需要根据账单号在退款记录表中查看具体的退款信息）
     */
    private String refund;


    private String userId;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;
}
