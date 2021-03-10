package org.typroject.tyboot.prototype.trade.channel.alipay.trade;

import com.alipay.api.*;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.core.foundation.exception.BaseException;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.face.trade.model.TransactionsSerialModel;
import org.typroject.tyboot.face.trade.service.TransactionsSerialService;
import org.typroject.tyboot.prototype.trade.Trade;
import org.typroject.tyboot.prototype.trade.TradeResultModel;
import org.typroject.tyboot.prototype.trade.channel.alipay.AlipayProperty;
import org.typroject.tyboot.prototype.trade.PropertyConstants;

import java.math.RoundingMode;
import java.util.Map;

@Component(value = "alipayRefund" )
public class AlipayRefund implements Trade {


    private static final Logger logger = LoggerFactory.getLogger(AlipayRefund.class);

    @Autowired
    private TransactionsSerialService transactionsSerialService;

    @Autowired
    private AlipayProperty alipayProperty;

    @Override
    public TradeResultModel process(TransactionsSerialModel serialModel, Map<String, Object> extra) throws Exception {

        TradeResultModel resultModel = new TradeResultModel();

        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(alipayProperty.getServerUrl(),
                alipayProperty.getAppid(),
                alipayProperty.getPrivateKey(),
                "json" ,
                AlipayConstants.CHARSET_UTF8,
                alipayProperty.getPublicKey(),
                "RSA2" );


        //初始化退款请求对象
        AlipayTradeRefundRequest refundRequest = new AlipayTradeRefundRequest();
        String oldSerialNo = (String) extra.get(PropertyConstants.SERIALNO);


        //校验流水号
        TransactionsSerialModel oldSerialModel = this.transactionsSerialService.selectBySeriaNo(oldSerialNo);
        if (!ValidationUtil.isEmpty(oldSerialModel)) {

            RefundModel model = new RefundModel();

            //对应原来的流水号
            model.setOut_trade_no(oldSerialNo);

            //退款金额
            model.setRefund_amount(serialModel.getTradeAmount().setScale(2, RoundingMode.HALF_EVEN).toString());
            model.setOut_request_no(serialModel.getSerialNo());
            refundRequest.setBizContent(model.toString());
            try {

                //这里和普通的接口调用不同，使用的是sdkExecute
                AlipayTradeRefundResponse response = alipayClient.execute(refundRequest);
                if (response.isSuccess()) {
                    resultModel.setResult("SUCCESS" );
                    resultModel.setCalledSuccess(true);
                } else {
                    throw new BaseException("退款失败.稍后重试." , "tradeException" , "交易失败." );
                }
            } catch (AlipayApiException e) {
                logger.error(e.getErrMsg(), e);
                throw e;
            }

        }
        return resultModel;
    }


    class RefundModel extends AlipayObject {
        private String out_trade_no;
        private String refund_amount;
        private String out_request_no;

        public String getOut_request_no() {
            return out_request_no;
        }

        public void setOut_request_no(String out_request_no) {
            this.out_request_no = out_request_no;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getRefund_amount() {
            return refund_amount;
        }

        public void setRefund_amount(String refund_amount) {
            this.refund_amount = refund_amount;
        }

        public String toString() {
            return "{" +
                    "\"out_trade_no\":\"" + out_trade_no + "\"," +
                    "\"refund_amount\":" + refund_amount + "," +
                    "\"out_request_no\":\"" + out_request_no + "\"" +
                    "}";
        }
    }


}
