package org.typroject.tyboot.face.account.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 充值记录表，只记录交易成功的充值信息 model
 * </p>
 *
 * @author Wujun
 * @since 2018-01-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountRechargeRecordModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 用户编号
     */
    private String userId;
    /**
     * 充值账户编号
     */
    private String accountNo;
    /**
     * 充值时间
     */
    private Date rechargeTime;
    /**
     * 充值金额
     */
    private BigDecimal rechargeAmount;
    /**
     * 充值账单编号
     */
    private String billNo;
    /**
     * 账户类型（用户虚拟账户，优惠额度账户、冻结资金账户）
     */
    private String accountType;

}
