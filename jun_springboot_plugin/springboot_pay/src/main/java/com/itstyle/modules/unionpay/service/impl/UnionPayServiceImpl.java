package com.itstyle.modules.unionpay.service.impl;

import com.itstyle.common.constants.Constants;
import com.itstyle.common.constants.PayWay;
import com.itstyle.common.model.Product;
import com.itstyle.common.util.CommonUtils;
import com.itstyle.modules.unionpay.service.IUnionPayService;
import com.itstyle.modules.unionpay.util.AcpService;
import com.itstyle.modules.unionpay.util.SDKConfig;
import com.itstyle.modules.unionpay.util.UnionConfig;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Service(group = "itstyle-pay", retries = 1, timeout = 10000)
public class UnionPayServiceImpl implements IUnionPayService{
	private static final Logger logger = LoggerFactory.getLogger(UnionPayServiceImpl.class);
	
	@Value("${unionpay.notify.url}")
	private String notify_url;
	
    /**
     * 银联支付返回一个form表单
     * @author Wujun
     * @param product
     * @return 
     * @Date	2017年8月2日
     * 更新日志
     * 2017年8月2日  科帮网 首次创建
     *
     */
	@Override
	public String unionPay(Product product) {
		Map<String, String> requestData = new HashMap<String, String>();
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		requestData.put("version", UnionConfig.version);   			  //版本号，全渠道默认值
		requestData.put("encoding", UnionConfig.encoding_UTF8); 	  //字符集编码，可以使用UTF-8,GBK两种方式
		requestData.put("signMethod", "01");            			  //签名方法，只支持 01：RSA方式证书加密
		requestData.put("txnType", "01");               			  //交易类型 ，01：消费
		requestData.put("txnSubType", "01");            			  //交易子类型， 01：自助消费
		requestData.put("bizType", "000201");           			  //业务类型，B2C网关支付，手机wap支付
		//渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机
		if(product.getPayWay()==PayWay.MOBILE.getCode()){//手机支付
			requestData.put("channelType", "08");     
		}else{//PC支付
			requestData.put("channelType", "07");
		}
		//前台回调地址(自定义)
		String frontUrl = "http://git.oschina.net/52itstyle";
		requestData.put("frontUrl", frontUrl);
		/***商户接入参数 测试账号***/
		requestData.put("merId", UnionConfig.merId);    	          //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
		requestData.put("accessType", "0");             			  //接入类型，0：直连商户 
		requestData.put("orderId", product.getOutTradeNo());          //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则		
		requestData.put("txnTime", UnionConfig.getCurrentTime());     //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		requestData.put("currencyCode", "156");         			  //交易币种（境内商户一般是156 人民币）
		requestData.put("txnAmt", CommonUtils.subZeroAndDot(product.getTotalFee()));             //交易金额，单位分，不要带小数点
		//这里组织穿透数据 业务以及交易类型(使用json数据报错)
		requestData.put("reqReserved","自定义参数");	      //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节		
		
		//前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
		//如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
		//异步通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		//requestData.put("frontUrl", UnionConfig.frontUrl);
		
		//后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
		//后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		//注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
		//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
		//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
		requestData.put("backUrl", notify_url);
		
		//////////////////////////////////////////////////
		//
		//       报文中特殊用法请查看 PCwap网关跳转支付特殊用法.txt
		//
		//////////////////////////////////////////////////
		
		/**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
		Map<String, String> submitFromData = AcpService.sign(requestData,UnionConfig.encoding_UTF8);  //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();  //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
		String form = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData,UnionConfig.encoding_UTF8);   //生成自动跳转的Html表单
		
		logger.info("打印请求HTML，此为请求报文，为联调排查问题的依据：{}",form);
		//将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过
		//resp.getWriter().write(html);
		return form;
	}

	@Override
	public String validate(Map<String, String> valideData, String encoding) {
		String message = Constants.SUCCESS;
		if (!AcpService.validate(valideData, encoding)) {
			message = Constants.FAIL;
		}
		return message;
	}
	@Override
	public void fileTransfer() {
		Map<String, String> data = new HashMap<String, String>();

		/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/

		// 版本号 全渠道默认值
		data.put("version", UnionConfig.version);
		
		// 字符集编码 可以使用UTF-8,GBK两种方式
		data.put("encoding", UnionConfig.encoding_UTF8);

		// 签名方法
		data.put("signMethod", "01");

		// 交易类型 76-对账文件下载
		data.put("txnType", "76");

		// 交易子类型 01-对账文件下载
		data.put("txnSubType", "01");

		// 业务类型，固定
		data.put("bizType", "000000");

		/*** 商户接入参数 ***/

		// 接入类型，商户接入填0，不需修改

		data.put("accessType", "0");

		// 商户代码，请替换正式商户号测试，如使用的是自助化平台注册的777开头的商户号，该商户号没有权限测文件下载接口的，

		// 请使用测试参数里写的文件下载的商户号和日期测。如需777商户号的真实交易的对账文件，请使用自助化平台下载文件。

		data.put("merId", UnionConfig.merId);

		// 清算日期，如果使用正式商户号测试则要修改成自己想要获取对账文件的日期，
		// 测试环境如果使用700000000000001商户号则固定填写0119

		//data.put("settleDate", settleDate);

		// 订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效

		data.put("txnTime", UnionConfig.getCurrentTime());

		// 文件类型，一般商户填写00即可

		data.put("fileType", "00");

		/** 请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文-------------> **/

		Map<String, String> reqData = AcpService.sign(data,
				UnionConfig.encoding_UTF8);

		// 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。

		String url = SDKConfig.getConfig().getFileTransUrl();

		// 获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.fileTransUrl

		Map<String, String> rspData = AcpService.post(reqData, url,
				UnionConfig.encoding_UTF8);

		if (!rspData.isEmpty()) {

			if (AcpService.validate(rspData, UnionConfig.encoding_UTF8)) {

				logger.info("验证签名成功");

				String respCode = rspData.get("respCode");

				if ("00".equals(respCode)) {

					// 交易成功，解析返回报文中的fileContent并落地

					/*	String zipFilePath = AcpService.deCodeFileContent(rspData,
							"d:\\", UnionConfig.encoding_UTF8);

					// 对落地的zip文件解压缩并解析

					String outPutDirectory = "d:\\";

					List<String> fileList = UnionConfig.unzip(zipFilePath,
							outPutDirectory);

					// 解析ZM，ZME文件

					for (String file : fileList) {

						if (file.indexOf("ZM_") != -1) {

							List<Map> ZmDataList = UnionConfig.parseZMFile(file);

							fileContentDispaly = UnionConfig.getFileContentTable(
									ZmDataList, file);

						} else if (file.indexOf("ZME_") != -1) {

							UnionConfig.parseZMEFile(file);

						}

					}*/
					// TODO
				} else {
					// 其他应答码为失败请排查原因
					// TODO
				}

			} else {
				logger.info("验证签名失败");
				// TODO 检查验证签名失败的原因
			}
		} else {
			// 未返回正确的http状态
			logger.info("未获取到返回报文或返回http状态码非200");
		}
	}
}
