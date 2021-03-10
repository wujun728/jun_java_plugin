package org.typroject.tyboot.face.account.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户虚拟账户表，记录所有公网用户的虚拟账户，account_no字段预留，用以多账户支持 model
 * </p>
 *
 * @author Wujun
 * @since 2018-01-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountInfoModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * agencycode
     */
    private String agencyCode;
    /**
     * 账户编号
     */
    private String accountNo;
    /**
     * 账户余额
     */
    private BigDecimal balance;
    /**
     * 账户类型（用户虚拟账户，优惠额度账户、冻结资金账户）
     */
    private String accountType;
    /**
     * 账户状态(正常\冻结\资金冻结\失效)
     */
    private String accountStatus;
    /**
     * 支付密码（md5加密）
     */
    private String paymentPassword;
    /**
     * 累计充值总额
     */
    private BigDecimal cumulativeBalance;
    /**
     * 数据版本
     */
    private Long updateVersion;
    private Date createTime;

    private String userId;
}
