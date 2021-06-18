package org.typroject.tyboot.face.trade.service;

import org.springframework.stereotype.Component;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.foundation.utils.Bean;
import org.typroject.tyboot.core.foundation.utils.Sequence;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.face.trade.model.TransactionsBillModel;
import org.typroject.tyboot.face.trade.model.TransactionsSerialModel;
import org.typroject.tyboot.face.trade.orm.dao.TransactionsSerialMapper;
import org.typroject.tyboot.face.trade.orm.entity.TransactionsSerial;
import org.typroject.tyboot.prototype.trade.DefaultTradeStatus;
import org.typroject.tyboot.prototype.trade.TradeStatus;
import org.typroject.tyboot.prototype.trade.TradeType;
import org.typroject.tyboot.prototype.trade.channel.ChannelType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 交易流水表 服务类
 * </p>
 *
 * @author Wujun
 * @since 2017-08-31
 */
@Component
public class TransactionsSerialService extends BaseService<TransactionsSerialModel,TransactionsSerial,TransactionsSerialMapper>
{


    public TransactionsSerialModel selectBySeriaNo(String serialNo) throws Exception
    {
        return queryModelByParams(serialNo);
    }

    public TransactionsSerialModel selectByBillNo(String billNo,String tradeType)throws Exception
    {
        TransactionsSerialModel model = new TransactionsSerialModel();
        model.setBillNo(billNo);
        model.setTradeType(tradeType);
        model.setTradeStatus(DefaultTradeStatus.SUCCESS.getStatus());
        return this.queryByModel(model);
    }



    /**
     * 支付成功修改流水信息
     * @param serialNo
     * @param channelSerialNo
     * @param tradStatus
     * @param resultMessage
     * @throws Exception
     */
    public  TransactionsSerialModel update2Success(String serialNo, String channelSerialNo, TradeStatus tradStatus, String resultMessage) throws Exception {
        TransactionsSerialModel transactionsSerial = this.selectBySeriaNo(serialNo);
        transactionsSerial.setTradeStatus(tradStatus.getStatus());
        transactionsSerial.setFinishTime(new Date());
        transactionsSerial.setChannelSerialNo(channelSerialNo);
        transactionsSerial.setResultMessage(resultMessage);
        this.updateWithModel(transactionsSerial);
        TransactionsSerialModel transactionsSerialModel = Bean.copyExistPropertis(transactionsSerial, new TransactionsSerialModel());
        return transactionsSerialModel;
    }


    /**
     * 创建交易流水
     * @param billModel
     * @param channelType
     * @return
     * @throws Exception
     */
    public TransactionsSerialModel createSerial(TransactionsBillModel billModel, ChannelType channelType, TradeType tradeType) throws Exception
    {
        TransactionsSerialModel serial = new TransactionsSerialModel();
        serial.setSerialNo(Sequence.generatorSerialNo());
        serial.setUserId(billModel.getUserId());
        serial.setAgencyCode(billModel.getAgencyCode());
        serial.setBillNo(billModel.getBillNo());
        serial.setTradeAmount(billModel.getAmount());
        serial.setClientIp(RequestContext.getRequestIP());
        serial.setPayMethod(channelType.getChannel());
        serial.setSyncFinishTime(new Date());
        serial.setSendTime(new Date());
        serial.setTradeStatus(DefaultTradeStatus.REQUESTED.name());
        serial.setTradeType(tradeType.getType());
        serial.setBillType(billModel.getBillType());
        return  this.createWithModel(serial);

    }


    /**
     * 创建交易流水
     * @param
     * @param channelType
     * @return
     * @throws Exception
     */
    public TransactionsSerialModel createSerialByAmount(String agencyCode, String userId, String billNo, BigDecimal amount, ChannelType channelType,String billType,TradeType tradeType) throws Exception
    {
        TransactionsSerialModel serial = new TransactionsSerialModel();
        serial.setSerialNo(Sequence.generatorSerialNo());
        serial.setUserId(userId);
        serial.setAgencyCode(agencyCode);
        serial.setBillNo(billNo);
        serial.setTradeAmount(amount);
        serial.setClientIp(RequestContext.getRequestIP());
        serial.setPayMethod(channelType.getChannel());
        serial.setSyncFinishTime(new Date());
        serial.setSendTime(new Date());
        serial.setTradeStatus(DefaultTradeStatus.REQUESTED.name());
        serial.setTradeType(tradeType.getType());
        serial.setBillType(billType);
        return  this.createWithModel(serial);

    }





}
