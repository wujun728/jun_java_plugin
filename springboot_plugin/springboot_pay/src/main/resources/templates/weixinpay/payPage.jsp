<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String appId = request.getParameter("appid");
String timeStamp = request.getParameter("timeStamp");
String nonceStr = request.getParameter("nonceStr");
String packageValue = request.getParameter("package");
String paySign = request.getParameter("paySign");

String orderNo = request.getParameter("orderNo");
String totalFee  = request.getParameter("totalFee");
%>
<!DOCTYPE html>
<html>
<head>
<title>微信支付</title>
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
var orderNo = '<%=orderNo%>';
var totalFee  = '<%=totalFee%>';
$(function(){
	$(".affirm-info").height($(window).height());
	$(window).resize(function(){
		//main-body高度适应屏幕
		$(".affirm-info").height($(window).height());
	});
	init();
});
function onBridgeReady(){
    WeixinJSBridge.invoke('getBrandWCPayRequest',{
		     "appId" : "<%=appId%>",
		     "timeStamp" : "<%=timeStamp%>",
		     "nonceStr" : "<%=nonceStr%>", 
		     "package" : "<%=packageValue%>",
		     "signType" : "MD5",
		     "paySign" : "<%=paySign%>" 
	     },function(res){
		    //使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
         if(res.err_msg == "get_brand_wcpay_request:ok"){
        	 //微信 自带 支付成功效果
             var url = "自定义http 跳转到支付系统页面";
             window.location.href=url;
         }else if(res.err_msg == "get_brand_wcpay_request:cancel"){  
             alert("用户取消支付!");  
         }else if(res.err_msg == "get_brand_wcpay_request：fail"){  
             alert("支付失败!");  
         }  
	})
}
function callpay(){  
      if (typeof WeixinJSBridge == "undefined"){
         if( document.addEventListener ){
               document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
           }else if (document.attachEvent){
               document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
               document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
          }
       }else{
         onBridgeReady();
       }
}
function init(){
	$("#orderNo").html("科帮网-订单编号："+orderNo);
	totalFee = accDiv(totalFee,100);
	$("#totalFee").html("￥"+totalFee);
}
function accDiv(arg1,arg2){
    var t1=0,t2=0,r1,r2;
    try{t1=arg1.toString().split(".")[1].length;}catch(e){}
    try{t2=arg2.toString().split(".")[1].length;}catch(e){}
    with(Math){
        r1=Number(arg1.toString().replace(".",""));
        r2=Number(arg2.toString().replace(".",""));
        return (r1/r2)*pow(10,t2-t1);
    }
}
</script>
</html>
