package org.typroject.tyboot.prototype.trade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.typroject.tyboot.core.foundation.context.SpringContextHelper;
import org.typroject.tyboot.core.foundation.exception.BaseException;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.face.trade.model.TransactionsBillModel;
import org.typroject.tyboot.face.trade.model.TransactionsRecordModel;
import org.typroject.tyboot.face.trade.model.TransactionsSerialModel;
import org.typroject.tyboot.face.trade.service.TransactionsBillService;
import org.typroject.tyboot.face.trade.service.TransactionsRecordService;
import org.typroject.tyboot.face.trade.service.TransactionsSerialService;
import org.typroject.tyboot.prototype.trade.channel.ChannelProcessor;
import org.typroject.tyboot.prototype.trade.channel.ChannelType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component("tradeProcess")
public class TradeProcess {




	@Autowired
	TransactionsBillService transactionsBillService;


	@Autowired
	TransactionsSerialService transactionsSerialService;


	@Autowired
	TransactionsRecordService transactionsRecordService;


	/**
	 * 发起交易
	 * @param tradeType
	 * @param channelType
	 * @throws Exception 
	 * @param billModel  待结账的账单
	 * @param tradeType	交易类型
	 * @param channelType 交易渠道
	 */
	public TradeResultModel sendTradeRequest(TransactionsBillModel billModel, TradeType tradeType, ChannelType channelType, Map<String,Object> extraParams) throws Exception
	{
		TradeResultModel resultModel 			= new TradeResultModel();
		DefaultTradeType defaultTradeType = (DefaultTradeType) tradeType;

		switch (defaultTradeType)
		{
			case PAYMENT:
				resultModel = this.sendPaymentRequest(billModel,tradeType,channelType,extraParams);
				break;
			case REFUND:
				resultModel = this.sendRefundRequest(billModel,tradeType,channelType,extraParams);

		}


		return resultModel;
	}



	private TradeResultModel sendPaymentRequest(TransactionsBillModel billModel, TradeType tradeType,ChannelType channelType, Map<String,Object> extraParams)throws Exception
	{
		TradeResultModel resultModel 			= new TradeResultModel();
		Map<String,String>  matadata 			= (Map<String,String>)extraParams.get(PropertyConstants.METADATA);
		if(ValidationUtil.isEmpty(matadata))
		{
			matadata = new HashMap<>();
		}
		if(!ValidationUtil.isEmpty(billModel))
		{
			//#1.生成流水单
			TransactionsSerialModel serialModel 		= transactionsSerialService.createSerial( billModel, channelType,DefaultTradeType.PAYMENT);

			//#2.附加參數
			matadata.put(PropertyConstants.BILLNO, serialModel.getBillNo());
			matadata.put(PropertyConstants.SERIALNO, serialModel.getSerialNo());
			matadata.put(PropertyConstants.USERID,String.valueOf(serialModel.getUserId()));
			matadata.put(PropertyConstants.AGENCYCODE, serialModel.getAgencyCode());
			extraParams.put(PropertyConstants.METADATA,matadata);

			//发起交易
			ChannelProcessor channelProcessor 	= (ChannelProcessor)SpringContextHelper.getBean(channelType.getChannelProcess());
			resultModel 	  				  	= channelProcessor.processTradeRequest(serialModel, tradeType, extraParams);
		}else{
			throw new Exception("賬單信息不存在.");
		}
		//返回交易結果
		return resultModel;
	}


	private TradeResultModel sendRefundRequest(TransactionsBillModel billModel, TradeType tradeType,ChannelType channelType, Map<String,Object> extraParams) throws Exception
	{
		TradeResultModel resultModel 			= new TradeResultModel();
		if(!ValidationUtil.isEmpty(billModel))
		{

			TransactionsSerialModel oldSerialModel = this.transactionsSerialService.selectByBillNo(billModel.getBillNo(),DefaultTradeType.PAYMENT.getType());
			//#1.生成流水单
			TransactionsSerialModel serialModel 		= transactionsSerialService.createSerialByAmount( billModel.getAgencyCode(),billModel.getUserId(),billModel.getBillNo(),(BigDecimal) extraParams.get(PropertyConstants.REFUND_AMOUNT), channelType,billModel.getBillType(),DefaultTradeType.REFUND);

			extraParams.put(PropertyConstants.SERIALNO,oldSerialModel.getSerialNo());
				//发起交易
				ChannelProcessor channelProcessor 	= (ChannelProcessor)SpringContextHelper.getBean(channelType.getChannelProcess());
				resultModel 	  				  	= channelProcessor.processTradeRequest(serialModel, tradeType, extraParams);

				if(resultModel.isCalledSuccess())
				{
					serialModel.setTradeStatus(DefaultTradeStatus.SUCCESS.getStatus());
					serialModel.setFinishTime(new Date());
					serialModel.setAsyncFinishTime(new Date());
					transactionsSerialService.updateWithModel(serialModel);


					billModel.setRefund(String.valueOf(true));
					billModel.setRefundTime(new Date());

					this.transactionsBillService.updateWithModel(billModel);
				}
		}else{
			throw new Exception("賬單信息不存在.");
		}
		return resultModel;
	}





	/**
	 * 交易结果处理
	 * @param serialNo	流水號
	 * @param channelSerialNo	交易渠道交易單編號
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = {Exception.class, BaseException.class})
	public TradeResultModel resultProcess(String  serialNo, String channelSerialNo, ChannelType channelType,String tradeResultProcessor,Map result) throws Exception
	{


		//交易渠道结果处理
		ChannelProcessor channelProcessor  = (ChannelProcessor)SpringContextHelper.getBean(channelType.getChannelProcess());
		TradeResultModel tradeResultModel = channelProcessor.processTradeResult(serialNo,DefaultTradeStatus.SUCCESS,result);

		TransactionsRecordModel record = this.transactionsRecordService.selectSerialNo(serialNo);
		if(!ValidationUtil.isEmpty(record))
			throw new Exception("重复的交易记录.");

		//交易账单和流水处理，并生成交易结果
		if(!ValidationUtil.isEmpty(tradeResultModel) && tradeResultModel.isCalledSuccess())
		{

			//#1.將流水置為成功
			TransactionsSerialModel serialModel = transactionsSerialService.selectBySeriaNo(serialNo);
			serialModel.setTradeStatus(DefaultTradeStatus.SUCCESS.getStatus());
			serialModel.setFinishTime(new Date());
			serialModel.setChannelSerialNo(channelSerialNo);
			serialModel.setAsyncFinishTime(new Date());
			transactionsSerialService.updateWithModel(serialModel);

			//#2.將賬單設置為已結賬
			TransactionsBillModel billModel	= transactionsBillService.selectByBillNo(serialModel.getBillNo());
			billModel.setBillStatus(TransactionsBillService.BILL_STATUS_FINISHED);
			billModel.setCheckoutTime(new Date());
			transactionsBillService.updateWithModel(billModel);

			//#生成交易記錄
			record 	    = transactionsRecordService.createTransactionsRecord(serialModel);


			//业务处理
			if(!ValidationUtil.isEmpty(tradeResultProcessor))
			{
				TradeResultProcessor resultProcessor = (TradeResultProcessor)SpringContextHelper.getBean(tradeResultProcessor);
				if(!ValidationUtil.isEmpty(resultProcessor))
					tradeResultModel = resultProcessor.processResult(billModel.getBillNo(),serialNo,result);
			}

			tradeResultModel.setResult(record);
		}
		return tradeResultModel;
	}
	



	
	
}

