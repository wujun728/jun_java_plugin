package org.typroject.tyboot.prototype.trade.channel.alipay.trade;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.face.trade.model.TransactionsSerialModel;
import org.typroject.tyboot.face.trade.service.TransactionsSerialService;
import org.typroject.tyboot.prototype.trade.Trade;
import org.typroject.tyboot.prototype.trade.TradeResultModel;
import org.typroject.tyboot.prototype.trade.channel.alipay.AlipayProperty;
import org.typroject.tyboot.prototype.trade.PropertyConstants;

import java.math.RoundingMode;
import java.util.Map;

@Component(value = "alipayPayment" )
public class AlipayPayment implements Trade {


    private static final Logger logger = LoggerFactory.getLogger(AlipayPayment.class);

    @Autowired
    private TransactionsSerialService transactionsSerialService;

    @Autowired
    private AlipayProperty alipayProperty;

    @Override
    public TradeResultModel process(TransactionsSerialModel serialModel, Map<String, Object> extra) throws Exception {

        TradeResultModel resultModel = new TradeResultModel();

        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(alipayProperty.getServerUrl(), alipayProperty.getAppid(), alipayProperty.getPrivateKey(), "json" , AlipayConstants.CHARSET_UTF8, alipayProperty.getPublicKey(), "RSA2" );
//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody((String) extra.get(PropertyConstants.PAYMENT_SUBJECT));
        model.setSubject((String) extra.get(PropertyConstants.PAYMENT_SUBJECT));
        model.setOutTradeNo(serialModel.getSerialNo());
        model.setTimeoutExpress("60m" );
        model.setTotalAmount(serialModel.getTradeAmount().setScale(2, RoundingMode.HALF_EVEN).toString());
        model.setProductCode("QUICK_MSECURITY_PAY" );
        request.setBizModel(model);
        request.setNotifyUrl(alipayProperty.getNotifyUrl());
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            //System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            resultModel.setResult(response.getBody());


            TransactionsSerialModel savedModel = transactionsSerialService.selectBySeriaNo(serialModel.getSerialNo());
            savedModel.setChannelSerialNo(response.getTradeNo());
            //savedModel.setDescription(response.getSellerId());
            this.transactionsSerialService.updateWithModel(savedModel);
            resultModel.setCalledSuccess(true);
        } catch (AlipayApiException e) {
            logger.error(e.getErrMsg(), e);
            throw e;
        }


        return resultModel;
    }


}
