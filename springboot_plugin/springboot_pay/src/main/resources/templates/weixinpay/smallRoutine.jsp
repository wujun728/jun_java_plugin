<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>小程序伪代码</title>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
<meta name="format-detection" content="telephone=no"/>
<link   rel="stylesheet" type="text/css" href="<%=basePath%>static/css/pay.css"/>
<script type="text/javascript" src="<%=basePath%>static/js/flexible.js" ></script>
<script type="text/javascript" src="<%=basePath%>static/js/jquery-1.10.2.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body>
		<article class="order-main ">
			<div class="ph_order">
				<div class=" affirm-info">
					<h4 id="orderNo"></h4>
					<h3 id="totalFee"></h3>
					<div class="detail-dl">
						<dl>
							<dt>收款方</dt>
							<dd>科帮网</dd>
						</dl>
						<dl>
							<dt>商&nbsp;&nbsp;&nbsp;品</dt>
							<dd id="productName">在线订单</dd>
						</dl>
					</div>
					<div  onclick="callpay()" class="pay-info">立即支付</div>
				</div>

			</div>
		</article>
</body>
<script type="text/javascript">
//小程序伪代码  请自行获取 complete、fail、success回调函数
	const
	wechatData = payRes.data.payment;//wechatData就是上面的验证信息
	console.log(wechatData);
	wx.requestPayment({
		'appId' : wechatData.appId,
		'timeStamp' : wechatData.timeStamp,
		'nonceStr' : wechatData.nonceStr,
		'package' : wechatData.package,
		'signType' : 'MD5',
		'paySign' : wechatData.paySign,
		'success' : function(res) {
			console.log(res);
			console.log('success');
		},
		'fail' : function(res) {
			console.log(res);
			console.log('fail');
		},
		'complete' : function(res) {
			console.log(res);
			console.log('complete');
		}
	});
</script>
</html>
