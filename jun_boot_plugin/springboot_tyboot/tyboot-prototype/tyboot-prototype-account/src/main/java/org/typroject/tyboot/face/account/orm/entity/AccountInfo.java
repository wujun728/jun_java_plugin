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
 * 用户虚拟账户表，记录所有公网用户的虚拟账户，account_no字段预留，用以多账户支持
 * </p>
 *
 * @author Wujun
 * @since 2018-01-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("account_info" )
public class AccountInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * agencycode
     */
    @TableField("AGENCY_CODE" )
    private String agencyCode;
    /**
     * 账户编号
     */
    @TableField("ACCOUNT_NO" )
    private String accountNo;
    /**
     * 账户余额
     */
    @TableField("BALANCE" )
    private BigDecimal balance;
    /**
     * 账户类型（用户虚拟账户，优惠额度账户、冻结资金账户）
     */
    @TableField("ACCOUNT_TYPE" )
    private String accountType;
    /**
     * 账户状态(正常\冻结\资金冻结\失效)
     */
    @TableField("ACCOUNT_STATUS" )
    private String accountStatus;
    /**
     * 支付密码（md5加密）
     */
    @TableField("PAYMENT_PASSWORD" )
    private String paymentPassword;
    /**
     * 累计充值总额
     */
    @TableField("CUMULATIVE_BALANCE" )
    private BigDecimal cumulativeBalance;
    /**
     * 数据版本
     */
    @TableField("UPDATE_VERSION" )
    private Long updateVersion;
    @TableField("CREATE_TIME" )
    private Date createTime;
    @TableField("USER_ID" )
    private String userId;
}
