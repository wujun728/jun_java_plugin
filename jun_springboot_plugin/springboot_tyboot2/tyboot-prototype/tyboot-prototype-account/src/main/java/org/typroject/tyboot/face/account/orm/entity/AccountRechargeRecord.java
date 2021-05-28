package org.typroject.tyboot.face.account.orm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 充值记录表，只记录交易成功的充值信息
 * </p>
 *
 * @author Wujun
 * @since 2018-01-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("account_recharge_record" )
public class AccountRechargeRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 用户编号
     */
    @TableField("USER_ID" )
    private String userId;
    /**
     * /**
     * 充值账户编号
     */
    @TableField("ACCOUNT_NO" )
    private String accountNo;
    /**
     * 充值时间
     */
    @TableField("RECHARGE_TIME" )
    private Date rechargeTime;
    /**
     * 充值金额
     */
    @TableField("RECHARGE_AMOUNT" )
    private BigDecimal rechargeAmount;
    /**
     * 充值账单编号
     */
    @TableField("BILL_NO" )
    private String billNo;
    /**
     * 账户类型（用户虚拟账户，优惠额度账户、冻结资金账户）
     */
    @TableField("ACCOUNT_TYPE" )
    private String accountType;
}
