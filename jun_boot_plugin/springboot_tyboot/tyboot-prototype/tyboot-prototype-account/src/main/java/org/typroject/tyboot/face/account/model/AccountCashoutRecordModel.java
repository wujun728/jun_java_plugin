package org.typroject.tyboot.face.account.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 提现申请表 model
 * </p>
 *
 * @author Wujun
 * @since 2018-01-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountCashoutRecordModel extends BaseModel {

    private static final long serialVersionUID = 1L;

                /**
         * 用户名称
         */
    private String userName;
                /**
         * 申请编号
         */
    private String applayNo;
                /**
         * 申请类型（支付宝|银行）
         */
    private String applayType;
                /**
         * 转账账号
         */
    private String transferAccount;
                /**
         * 转账用户名称
         */
    private String transferName;
                /**
         * 开户行
         */
    private String openBank;
                /**
         * 提现金额
         */
    private BigDecimal outAmount;
                /**
         * 申请状态（申请中pending|已确认confirmed|自动确认confirmed_auto|拒绝refuse）
         */
    private String applyStatus;
                /**
         * 提现完成时间
         */
    private Date finishTime;
                /**
         * 手续费
         */
    private BigDecimal poundage;
                /**
         * 申请提现金额
         */
    private BigDecimal applayAmount;

    private String userId;
}
