$(function() {
	var browser = {
		device : function() {
			var u = navigator.userAgent;
			return {
				trident : u.indexOf('Trident') > -1,
				presto : u.indexOf('Presto') > -1, // opera内核
				webKit : u.indexOf('AppleWebKit') > -1, // 苹果、谷歌内核
				gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, // 火狐内核
				deskWebkit : (u.indexOf("Android") == -1 && u.indexOf("Mobile") == -1),
				mobileWebKit : !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/) || !!u.match(/.*Mobile.*/), // 是否为移动终端
				ios : !!u.match(/(i[^;]+\;(U;)? CPU.+Mac OS X)/), // ios终端
				android : u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, // android终端
				iPhone : u.indexOf('iPhone') > -1 && u.indexOf('Mac') > -1, // 是否为iPhoneD
				iPad : u.indexOf('iPad') > -1, // 是否iPad
				webApp : u.indexOf('Safari') == -1
			// 是否为App应用程序，没有头部与底部
			};
		}(),
		language : (navigator.browserLanguage || navigator.language).toLowerCase(),
		version : $.browser.version
	};

	//$("#baseMsg").html('<em>您的设备基础信息:</em>' + navigator.userAgent);
	var chromeUrl = '<div>推荐使用<strong><a href="https://www.google.com/intl/zh-CN/chrome/browser/" target="_blank">谷歌浏览器</a></strong>来获得更快的页面相应效果。</div>';
	/* 判断webkit内核下的版本 */
	if (browser.device.webKit) {
		if (browser.device.deskWebkit) {
			return;
		} else if (browser.device.mobileWebKit) {
			if (browser.device.ios) {
				if (browser.device.iPhone) {
					$("#deviceInfoBox").html("当前使用的浏览器是: <span>iOS系统iPhone版Safari</span>" + "  " + ",内核版本号为：" + " <span>" + browser.version + "</span>").parent().slideDown(600);
				} else if (browser.device.iPad) {
					$("#deviceInfoBox").html("当前使用的浏览器是: <span>iOS系统iPad版Safari</span>" + "  " + ",内核版本号为：" + " <span>" + browser.version + "</span>").parent().slideDown(600);
				}
			} else if (browser.device.android) {
				$("#deviceInfoBox").html("当前使用的浏览器是: <span>android系统Webkit</span>" + "  " + ",内核版本号为：" + " <span>" + browser.version + "</span>").parent().slideDown(600);
			}
		}
	}/* 判断IE浏览器 */
	else if (browser.device.trident) {
		getDeviceInfo("IE浏览器");
	}
	/* 判断FireFox浏览器 */
	else if  (browser.device.gecko) {
		return;
//		getDeviceInfo("火狐浏览器");
	}
	/* 判断Opera浏览器 */
	else if (browser.device.presto) {
		return;
//		getDeviceInfo("欧朋浏览器");
	}
	/*其他浏览器*/
	else{
		$("#deviceInfoBox").html("当前使用的浏览器是: <strong>欧朋浏览器</strong>" + "  " + ",内核版本号为：" + " <strong>" + browser.version + "</strong>").parent().slideDown(600);
		$("#deviceInfoBox").append(chromeUrl);
		$(".unsupported-browser").slideDown("1000");
	}
	function getDeviceInfo(browserMsg){
		$("#deviceInfoBox").html("当前使用的浏览器是: <strong>"+browserMsg+"</strong>" + "  " + ",内核版本号为：" + " <strong>" + browser.version + "</strong>").parent().slideDown(600);
		$("#deviceInfoBox").append(chromeUrl);
		$(".unsupported-browser").slideDown("1000");
	}
});