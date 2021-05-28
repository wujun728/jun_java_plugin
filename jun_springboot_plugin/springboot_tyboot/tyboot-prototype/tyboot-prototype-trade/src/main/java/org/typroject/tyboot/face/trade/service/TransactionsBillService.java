package org.typroject.tyboot.face.trade.service;

import org.springframework.stereotype.Component;
import org.typroject.tyboot.core.foundation.utils.Bean;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.face.trade.model.TransactionsBillModel;
import org.typroject.tyboot.face.trade.orm.dao.TransactionsBillMapper;
import org.typroject.tyboot.face.trade.orm.entity.TransactionsBill;
import org.typroject.tyboot.prototype.trade.TradeStatus;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 交易账单表 服务类
 * </p>
 *
 * @author Wujun
 * @since 2017-08-31
 */
@Component
public class TransactionsBillService extends BaseService<TransactionsBillModel,TransactionsBill,TransactionsBillMapper>
{



    public static final String  BILL_STATUS_PENDING= "PENDING";//交易账单状态--待处理
    public static final String  BILL_STATUS_FINISHED= "FINISHED";//交易账单状态--处理完成
    public static final String  BILL_STATUS_REFUND= "REFUND";//交易账单状态--已退款


    public TransactionsBillModel selectByBillNo(String billNo) throws Exception {
       return this.queryModelByParams(billNo);
    }






    /**
     *  创建交易账单
     * @param billNo 账单编号，可选参数
     * @param agencyCode
     * @param tradeAmount
     * @param billType
     * @param userId
     * @return
     * @throws Exception
     */
    public  TransactionsBillModel createBill(String billNo, String agencyCode, BigDecimal tradeAmount, String  billType, String  userId) throws Exception
    {
        TransactionsBillModel transactionsBillModel = new TransactionsBillModel();
        transactionsBillModel.setAgencyCode(agencyCode);
        transactionsBillModel.setBillNo(billNo);
        transactionsBillModel.setBillStatus(BILL_STATUS_PENDING);
        transactionsBillModel.setCreateTime(new Date());
        transactionsBillModel.setAmount(tradeAmount);
        transactionsBillModel.setBillType(billType);
        transactionsBillModel.setUserId(userId);
        this.createWithModel(transactionsBillModel);
        return transactionsBillModel;
    }









    /**
     *
     * 支付完成后修改账单信息
     * @param billNo
     * @param tradeStatus
     * @throws Exception
     */
    protected TransactionsBillModel update2Success(String billNo, TradeStatus tradeStatus) throws Exception
    {
        TransactionsBillModel billModel = this.selectByBillNo(billNo);
        billModel.setCheckoutTime(new Date());
        billModel.setBillStatus(TransactionsBillService.BILL_STATUS_FINISHED);
        this.updateWithModel(billModel);
        TransactionsBillModel transactionsBillModel = Bean.copyExistPropertis(billModel, new TransactionsBillModel());
        return transactionsBillModel;
    }














}
