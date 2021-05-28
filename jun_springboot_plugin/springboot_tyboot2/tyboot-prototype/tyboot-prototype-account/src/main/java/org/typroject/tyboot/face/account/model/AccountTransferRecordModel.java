package org.typroject.tyboot.face.account.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 账户转账记录:
 * 特指：内部账户之间主动转账，冻结/解冻资金所引发的转账到冻结账户，内部账户到外部账户的主动转账 model
 * </p>
 *
 * @author Wujun
 * @since 2018-01-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountTransferRecordModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 账单编号
     */
    private String billNo;
    /**
     * 来源账户编号
     */
    private String sourceAccountNo;
    /**
     * 目标账户编号
     */
    private String targetAccountNo;
    /**
     * 转账类型(内部账户间主动转账，冻结/解冻)
     */
    private String transferType;
    /**
     * 转账金额
     */
    private BigDecimal transferAmount;
    /**
     * 转账状态(转账中，转账成功、转账失败)
     */
    private String transferStatus;
    /**
     * 转账时间
     */
    private Date transferTime;
    /**
     * 转账附言
     */
    private String transferPostscript;
    /**
     * 账户类型（用户虚拟账户，优惠额度账户、冻结资金账户）
     */
    private String sourceAccountType;
    /**
     * 账户类型（用户虚拟账户，优惠额度账户、冻结资金账户）
     */
    private String targetAccountType;

    private String userId;
}
