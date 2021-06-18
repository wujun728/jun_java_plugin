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
 * 虚拟账户金额变更记录表，所有针对账户金额的变动操作都要记录到此表中，
 * </p>
 *
 * @author Wujun
 * @since 2018-01-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("account_serial" )
public class AccountSerial extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 账户编号(预留字段，暂不启用)
     */
    @TableField("ACCOUNT_NO" )
    private String accountNo;
    /**
     * 起始余额
     */
    @TableField("INITIAL_PREFUNDED_BALANCE" )
    private BigDecimal initialPrefundedBalance;
    /**
     * 变更金额
     */
    @TableField("CHANGE_AMOUNT" )
    private BigDecimal changeAmount;
    /**
     * 最终余额
     */
    @TableField("FINAL_BALANCE" )
    private BigDecimal finalBalance;
    /**
     * 操作类型（充值recharge，消费consumption(消费类型还可以再分)，提现cash-out）
     */
    @TableField("OPERATION_TYPE" )
    private String operationType;
    /**
     * 操作时间
     */
    @TableField("OPERATE_TIME" )
    private Date operateTime;
    /**
     * 交易账单编号
     */
    @TableField("BILL_NO" )
    private String billNo;
    /**
     * 账户信息的数据版本
     */
    @TableField("UPDATE_VERSION" )
    private Long updateVersion;
    /**
     * 账户类型（用户虚拟账户，优惠额度账户、冻结资金账户）
     */
    @TableField("ACCOUNT_TYPE" )
    private String accountType;
    /**
     * 记账类型
     */
    @TableField("BOOKKEEPING" )
    private String bookkeeping;

    @TableField("USER_ID" )
    private String userId;
}
