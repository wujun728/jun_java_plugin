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
 * 提现申请表
 * </p>
 *
 * @author Wujun
 * @since 2018-01-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("account_cashout_record" )
public class AccountCashoutRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名称
     */
    @TableField("USER_NAME" )
    private String userName;
    /**
     * 申请编号
     */
    @TableField("APPLAY_NO" )
    private String applayNo;
    /**
     * 申请类型（支付宝|银行）
     */
    @TableField("APPLAY_TYPE" )
    private String applayType;
    /**
     * 转账账号
     */
    @TableField("TRANSFER_ACCOUNT" )
    private String transferAccount;
    /**
     * 转账用户名称
     */
    @TableField("TRANSFER_NAME" )
    private String transferName;
    /**
     * 开户行
     */
    @TableField("OPEN_BANK" )
    private String openBank;
    /**
     * 提现金额
     */
    @TableField("OUT_AMOUNT" )
    private BigDecimal outAmount;
    /**
     * 申请状态（申请中pending|已确认confirmed|自动确认confirmed_auto|拒绝refuse）
     */
    @TableField("APPLY_STATUS" )
    private String applyStatus;
    /**
     * 提现完成时间
     */
    @TableField("FINISH_TIME" )
    private Date finishTime;
    /**
     * 手续费
     */
    @TableField("POUNDAGE" )
    private BigDecimal poundage;
    /**
     * 申请提现金额
     */
    @TableField("APPLAY_AMOUNT" )
    private BigDecimal applayAmount;

    @TableField("USER_ID" )
    private String userId;
}
