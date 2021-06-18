package org.typroject.tyboot.prototype.trade.channel.apple;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.prototype.trade.channel.BaseChannelProcess;
import org.typroject.tyboot.prototype.trade.channel.ChannelProcessor;
import org.typroject.tyboot.prototype.trade.TradeResultModel;
import org.typroject.tyboot.prototype.trade.TradeStatus;
import org.typroject.tyboot.prototype.trade.TradeType;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.face.trade.model.TransactionsSerialModel;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

@Component(value = "appleChannel" )
public class AppleChannel extends BaseChannelProcess implements ChannelProcessor {


    //购买凭证验证地址
    private static final String certificateUrl = "https://buy.itunes.apple.com/verifyReceipt";

    //测试的购买凭证验证地址
    private static final String certificateUrlTest = "https://sandbox.itunes.apple.com/verifyReceipt";


    /**
     * 重写X509TrustManager
     */
    private static TrustManager myX509TrustManager = new X509TrustManager() {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }
    };


    private static final String CHANNEL_PIX = "apple";

    @Override
    public TradeResultModel processTradeRequest(TransactionsSerialModel serialModel, TradeType tradeType, Map<String, Object> extraParams)
            throws Exception {
        TradeResultModel resultModel = new TradeResultModel();//交易结果
        resultModel.setCalledSuccess(true);
        Map<String, String> result = new HashMap<>();
        result.put("serialNo" , serialModel.getSerialNo());
        result.put("billNo" , serialModel.getBillNo());
        resultModel.setResult(result);
        return resultModel;
    }


    public TradeResultModel processTradeResult(String serialNo, TradeStatus tradeStatus, Object result)
            throws Exception {

        TradeResultModel resultModel = new TradeResultModel();//交易结果
        if (!ValidationUtil.isEmpty(result)) {
            Map applePayInfo = (Map) result;
            final String certificateCode = (String) applePayInfo.get("receipt" );

            String resultStr = sendHttpsCoon(certificateUrl, certificateCode);

            JSONObject jsonObject = new JSONObject(resultStr);
            int status = (Integer) jsonObject.get("status" );

            if (status == 21007) {
                resultStr = sendHttpsCoon(certificateUrlTest, certificateCode);

                jsonObject = new JSONObject(resultStr);
                status = (Integer) jsonObject.get("status" );
            }
            if (status == 0) {
                resultModel.setCalledSuccess(true);
                applePayInfo.put("receipt" , resultStr);
                resultModel.setResult(applePayInfo);
                resultModel.setResultMessage("交易成功." );
            }
        }
        return resultModel;
    }


    /**
     * 发送请求
     *
     * @param url
     * @param
     * @return
     */
    private String sendHttpsCoon(String url, String code) {
        if (url.isEmpty()) {
            return null;
        }
        try {
            //设置SSLContext
            SSLContext ssl = SSLContext.getInstance("SSL" );
            ssl.init(null, new TrustManager[]{myX509TrustManager}, null);

            //打开连接
            HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
            //设置套接工厂
            conn.setSSLSocketFactory(ssl.getSocketFactory());
            //加入数据
            conn.setRequestMethod("POST" );
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type" , "application/json" );

            JSONObject obj = new JSONObject();
            obj.put("receipt-data" , code);


            BufferedOutputStream buffOutStr = new BufferedOutputStream(conn.getOutputStream());
            buffOutStr.write(obj.toString().getBytes());
            buffOutStr.flush();
            buffOutStr.close();

            //获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();

        } catch (Exception e) {
            return null;
        }
    }

}
