package org.typroject.tyboot.face.trade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 交易流水表 model
 * </p>
 *
 * @author Wujun
 * @since 2017-08-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionsSerialModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 本地交易流水号（25位）
     */
    private String serialNo;
    /**
     * 账单编号
     */
    private String billNo;
    /**
     * 商家编码
     */
    private String agencyCode;
    /**
     * 支付渠道返回的流水号
     */
    private String channelSerialNo;
    /**
     * 交易金额
     */
    private BigDecimal tradeAmount;
    /**
     * 减免金额
     */
    private BigDecimal discntFee;
    /**
     * 减免备注说明
     */
    private String discntDesc;
    /**
     * 即时返回时间
     */
    private Date syncFinishTime;
    /**
     * 系统向支付平台pingxx发起请求时间
     */
    private Date sendTime;
    /**
     * 支付结果备注信息(支付渠道，pingxx支付平台都有可能返回结果信息,)
     */
    private String resultMessage;
    /**
     * 支付完成时间(前端返回交易完成更新此时间)
     */
    private Date finishTime;
    /**
     * 异步通知时间
     */
    private Date asyncFinishTime;
    /**
     * 发起支付的终端ip
     */
    private String clientIp;
    /**
     * 支付终端设备平台类型
     */
    private String clientPlatform;
    /**
     * 使用的支付方式(支付宝，微信)
     */
    private String payMethod;
    /**
     * 交易状态：支付申请;支付提交;已返回凭证;前端返回(成功，失败),pingxx异步返回(成功，失败)交易过期，交易被取消
     */
    private String tradeStatus;
    /**
     * 交易类型：支付商品，商家结算，红包，虚拟账户充值
     */
    private String tradeType;

    private String billType;


    private String userId;
}
