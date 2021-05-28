package com.itstyle.modules.alipay.service.impl;

import java.io.File;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.builder.AlipayTradeRefundRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.model.result.AlipayF2FRefundResult;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.itstyle.common.constants.Constants;
import com.itstyle.common.model.Product;
import com.itstyle.common.util.CommonUtils;
import com.itstyle.modules.alipay.service.IAliPayService;
import com.itstyle.modules.alipay.util.AliPayConfig;
/**
 * 支付宝
 * 创建者 科帮网
 * 创建时间	2018年1月15日
 * ======================
 * 商户端私钥：
 * 由我们自己生成的RSA私钥（必须与商户端公钥是一对），生成后要保存在服务端，绝对不能保存在客户端，也绝对不能从服务端下发。
 * 用来对订单信息进行加签，加签过程一定要在服务端完成，绝对不能在客户端做加签工作，客户端只负责用加签后的订单信息调起支付宝来支付。
 * ======================
 * 商户端公钥：
 * 由我们自己生成的RSA公钥（必须与商户端私钥是一对），生成后需要填写在支付宝开放平台，
 * 用来给支付宝服务端验签经过我们加签后的订单信息，以确保订单信息确实是我们商户端发给支付宝的，并且确保订单信息在传输过程中未被篡改。
 * ======================
 * 支付宝私钥：
 * 支付宝自己生成的，他们自己保存，开发者是无法看到的，用来对支付结果进行加签。
 * ======================
 * 支付宝公钥：
 * 支付宝公钥和支付宝私钥是一对，也是支付宝生成的，当我们把商户端公钥填写在支付宝开放平台后，平台就会给我们生成一个支付宝公钥。
 * 我们可以复制下来保存在服务端，同样不要保存在客户端，并且不要下发，避免被反编译或截获，而被篡改支付结果。
 * 用来让服务端对支付宝服务端返给我们的同步或异步支付结果进行验签，以确保支付结果确实是由支付宝服务端返给我们服务端的，而且没有被篡改。
 * 对支付结果的验签工作也一定要在服务端完成，绝对不能在客户端验签，因为支付宝公钥一旦存储在客户端用来验签，那就可能被反编译，这样就谁都可以验签支付结果并篡改了。
 * ======================
 * 支付宝建议加签方式升级为RSA(SHA256)密钥，以为 SHA 貌似已经被破解了。
 * 
 */
@Service(group = "itstyle-pay", retries = 1, timeout = 10000)
public class AliPayServiceImpl implements IAliPayService {
	private static final Logger logger = LoggerFactory.getLogger(AliPayServiceImpl.class);
	
	@Value("${alipay.notify.url}")
	private String notify_url;
	
	@Override
	public String aliPay(Product product) {
		logger.info("订单号：{}生成支付宝支付码",product.getOutTradeNo());
		String  message = Constants.SUCCESS;
		//二维码存放路径
		System.out.println(Constants.QRCODE_PATH);
		String imgPath= Constants.QRCODE_PATH+Constants.SF_FILE_SEPARATOR+product.getOutTradeNo()+".png";
		String outTradeNo = product.getOutTradeNo();
		String subject = product.getSubject();
		String totalAmount =  CommonUtils.divide(product.getTotalFee(), "100").toString();
		// 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
		String sellerId = "";
		// (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
		String storeId = "test_store_id";
		// 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
		ExtendParams extendParams = new ExtendParams();
		extendParams.setSysServiceProviderId("2088100200300400500");
		// 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
		String body = product.getBody();
		// 支付超时，定义为120分钟
		String timeoutExpress = "120m";
		// 创建扫码支付请求builder，设置请求参数
		AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
		.setSubject(subject)
		.setTotalAmount(totalAmount)
		.setOutTradeNo(outTradeNo)
		.setSellerId(sellerId)
		.setBody(body)//128长度 --附加信息
		.setStoreId(storeId)
		.setExtendParams(extendParams)
		.setTimeoutExpress(timeoutExpress)
        .setNotifyUrl(notify_url);//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
		
		AlipayF2FPrecreateResult result = AliPayConfig.getAlipayTradeService().tradePrecreate(builder);
		switch (result.getTradeStatus()) {
		case SUCCESS:
			logger.info("支付宝预下单成功: )");
			
			AlipayTradePrecreateResponse response = result.getResponse();
			dumpResponse(response);
			ZxingUtils.getQRCodeImge(response.getQrCode(), 256, imgPath);
			break;
			
		case FAILED:
			logger.info("支付宝预下单失败!!!");
			message = Constants.FAIL;
			break;
			
		case UNKNOWN:
			logger.info("系统异常，预下单状态未知!!!");
			message = Constants.FAIL;
			break;
			
		default:
			logger.info("不支持的交易状态，交易返回异常!!!");
			message = Constants.FAIL;
			break;
		}
		return message;
	}
	 // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
        	logger.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
            	logger.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(), response.getSubMsg()));
            }
            logger.info("body:" + response.getBody());
        }
    }
	@Override
	public String aliRefund(Product product) {
		logger.info("订单号："+product.getOutTradeNo()+"支付宝退款");
		String  message = Constants.SUCCESS;
		 // (必填) 外部订单号，需要退款交易的商户外部订单号
        String outTradeNo = product.getOutTradeNo();
        // (必填) 退款金额，该金额必须小于等于订单的支付金额，单位为元
        String refundAmount = CommonUtils.divide(product.getTotalFee(), "100").toString();

        // (必填) 退款原因，可以说明用户退款原因，方便为商家后台提供统计
        String refundReason = "正常退款，用户买多了";

        // (必填) 商户门店编号，退款情况下可以为商家后台提供退款权限判定和统计等作用，详询支付宝技术支持
        String storeId = "test_store_id";

        // 创建退款请求builder，设置请求参数
        AlipayTradeRefundRequestBuilder builder = new AlipayTradeRefundRequestBuilder()
                .setOutTradeNo(outTradeNo)
                .setRefundAmount(refundAmount)
                .setRefundReason(refundReason)
                //.setOutRequestNo(outRequestNo)
                .setStoreId(storeId);

        AlipayF2FRefundResult result = AliPayConfig.getAlipayTradeService().tradeRefund(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
            	logger.info("支付宝退款成功: )");
                break;

            case FAILED:
            	logger.info("支付宝退款失败!!!");
            	message = Constants.FAIL;
                break;

            case UNKNOWN:
            	logger.info("系统异常，订单退款状态未知!!!");
            	message = Constants.FAIL;
                break;

            default:
            	logger.info("不支持的交易状态，交易返回异常!!!");
            	message = Constants.FAIL;
                break;
        }
		return message;
	}
    /**
     * 如果你调用的是当面付预下单接口(alipay.trade.precreate)，调用成功后订单实际上是没有生成，因为创建一笔订单要买家、卖家、金额三要素。
     * 预下单并没有创建订单，所以根据商户订单号操作订单，比如查询或者关闭，会报错订单不存在。
     * 当用户扫码后订单才会创建，用户扫码之前二维码有效期2小时，扫码之后有效期根据timeout_express时间指定。
     * =====只有支付成功后 调用此订单才可以=====
     */
	@Override
	public String aliCloseorder(Product product) {
		logger.info("订单号："+product.getOutTradeNo()+"支付宝关闭订单");
		String  message = Constants.SUCCESS;
		try {
			String imgPath= Constants.QRCODE_PATH+Constants.SF_FILE_SEPARATOR+"alipay_"+product.getOutTradeNo()+".png";
			File file = new File(imgPath);
            if(file.exists()){
            	AlipayClient alipayClient = AliPayConfig.getAlipayClient();
            	AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
            	request.setBizContent("{" +
            			"    \"out_trade_no\":\""+product.getOutTradeNo()+"\"" +
            			"  }");
            	AlipayTradeCloseResponse response = alipayClient.execute(request);
            	if(response.isSuccess()){//扫码未支付的情况
            		logger.info("订单号："+product.getOutTradeNo()+"支付宝关闭订单成功并删除支付二维码");
            		file.delete();
            	}else{
            		if("ACQ.TRADE_NOT_EXIST".equals(response.getSubCode())){
            			logger.info("订单号："+product.getOutTradeNo()+response.getSubMsg()+"(预下单 未扫码的情况)");
            		}else if("ACQ.TRADE_STATUS_ERROR".equals(response.getSubCode())){
            			logger.info("订单号："+product.getOutTradeNo()+response.getSubMsg());
            		}else{
            			logger.info("订单号："+product.getOutTradeNo()+"支付宝关闭订单失败"+response.getSubCode()+response.getSubMsg());
            			message = Constants.FAIL;
            		}
            	}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = Constants.FAIL;
			logger.info("订单号："+product.getOutTradeNo()+"支付宝关闭订单异常");
		}
		return message;
	}
	@Override
	public String downloadBillUrl(String billDate,String billType) {
		logger.info("获取支付宝订单地址:"+billDate);
		String downloadBillUrl = "";
		try {
			AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
			request.setBizContent("{" + "    \"bill_type\":\"trade\","
					                   + "    \"bill_date\":\"2016-12-26\"" + "  }");
			
			AlipayDataDataserviceBillDownloadurlQueryResponse response
			                          = AliPayConfig.getAlipayClient().execute(request);
			if (response.isSuccess()) {
				logger.info("获取支付宝订单地址成功:"+billDate);
				downloadBillUrl = response.getBillDownloadUrl();//获取下载地
			} else {
				logger.info("获取支付宝订单地址失败"+response.getSubMsg()+":"+billDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获取支付宝订单地址异常:"+billDate);
		}
		return downloadBillUrl;
	}
	@Override
	public String aliPayMobile(Product product) {
		logger.info("支付宝手机支付下单");
		AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
		String returnUrl = "回调地址 http 自定义";
		alipayRequest.setReturnUrl(returnUrl);//前台通知
        alipayRequest.setNotifyUrl(notify_url);//后台回调
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", product.getOutTradeNo());
        bizContent.put("total_amount", product.getTotalFee());//订单金额:元
        bizContent.put("subject",product.getSubject());//订单标题
        bizContent.put("seller_id", Configs.getPid());//实际收款账号，一般填写商户PID即可
        bizContent.put("product_code", "QUICK_WAP_PAY");//手机网页支付
		bizContent.put("body", "两个苹果五毛钱");
		String biz = bizContent.toString().replaceAll("\"", "'");
        alipayRequest.setBizContent(biz);
        logger.info("业务参数:"+alipayRequest.getBizContent());
        String form = Constants.FAIL;
        try {
            form = AliPayConfig.getAlipayClient().pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
        	logger.error("支付宝构造表单失败",e);
        }
        return form;
	}
	@Override
	public String aliPayPc(Product product) {
		logger.info("支付宝PC支付下单");
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		String returnUrl = "前台回调地址 http 自定义";
		alipayRequest.setReturnUrl(returnUrl);//前台通知
        alipayRequest.setNotifyUrl(notify_url);//后台回调
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", product.getOutTradeNo());
        bizContent.put("total_amount", product.getTotalFee());//订单金额:元
        bizContent.put("subject",product.getSubject());//订单标题
        bizContent.put("seller_id", Configs.getPid());//实际收款账号，一般填写商户PID即可
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");//电脑网站支付
		bizContent.put("body", "两个苹果五毛钱");
		/**
		 * 这里有三种模式可供选择
		 * 如果在系统内支付，并且是弹出层支付，建议选择模式二、其他模式会跳出当前iframe(亲测有效)
		 */
		bizContent.put("qr_pay_mode", "2");
		String biz = bizContent.toString().replaceAll("\"", "'");
        alipayRequest.setBizContent(biz);
        logger.info("业务参数:"+alipayRequest.getBizContent());
        String form = Constants.FAIL;
        try {
            form = AliPayConfig.getAlipayClient().pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
        	logger.error("支付宝构造表单失败",e);
        }
        return form;
	}
	@Override
	public String appPay(Product product) {
		String orderString = Constants.FAIL;
		// 实例化客户端
		AlipayClient alipayClient = AliPayConfig.getAlipayClient();
		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(product.getBody());
		model.setSubject(product.getSubject());
		model.setOutTradeNo(product.getOutTradeNo());
		model.setTimeoutExpress("30m");
		model.setTotalAmount(product.getTotalFee());
		model.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(model);
		request.setNotifyUrl("商户外网可以访问的异步地址");
		try {
			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient
					.sdkExecute(request);
			orderString  = response.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。
			//System.out.println(response.getBody());
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return orderString ;
	}
	@Override
	public boolean rsaCheckV1(Map<String, String> params) {
		//验证签名 校验签名
		boolean signVerified = false;
	    try {
			signVerified = AlipaySignature.rsaCheckV1(params, Configs.getAlipayPublicKey(), "UTF-8");
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return signVerified;
	}
	@Override
	public boolean rsaCheckV2(Map<String, String> params) {
		//验证签名 校验签名
		boolean signVerified = false;
	    try {
			signVerified = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "UTF-8");
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return signVerified;
	}
}
