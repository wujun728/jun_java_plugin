/^http(s*):\/\//.test(location.href) || alert('请先部署到 localhost 下再访问');

var objOkTab = "";
layui.use(["element", "form", "layer", "okUtils", "okTab", "okLayer", "okContextMenu", "okHoliday", "laydate"], function () {
	var okUtils = layui.okUtils;
	var $ = layui.jquery;
	var form = layui.form;
	var laydate = layui.laydate;
	var layer = layui.layer;
	var okLayer = layui.okLayer;
	var okHoliday = layui.okHoliday;
	var okTab = layui.okTab({
		// 菜单请求路径
		url: "data/navs.json",
		// 允许同时选项卡的个数
		openTabNum: 30,
		// 如果返回的结果和navs.json中的数据结构一致可省略这个方法
		parseData: function (data) {
			return data;
		}
	});
	var config = okUtils.local("okConfig") || okConfig || {};
	objOkTab = okTab;
	okLoading && okLoading.close();
	/**关闭加载动画*/

	$(".layui-layout-admin").removeClass("orange_theme blue_theme");
	$(".layui-layout-admin").addClass(config.theme);

	if (config.menuArrow) { //tab箭头样式
		$("#navBar").addClass(config.menuArrow);
	}

	/**
	 * 左侧导航渲染完成之后的操作
	 */
	okTab.render(function () {
		/**tab栏的鼠标右键事件**/
		$("body .ok-tab").okContextMenu({
			width: 'auto',
			itemHeight: 30,
			menu: [
				{
					text: "定位所在页",
					icon: "ok-icon ok-icon-location",
					callback: function () {
						okTab.positionTab();
					}
				},
				{
					text: "关闭当前页",
					icon: "ok-icon ok-icon-roundclose",
					callback: function () {
						okTab.tabClose(1);
					}
				},
				{
					text: "关闭其他页",
					icon: "ok-icon ok-icon-roundclose",
					callback: function () {
						okTab.tabClose(2);
					}
				},
				{
					text: "关闭所有页",
					icon: "ok-icon ok-icon-roundclose",
					callback: function () {

						okTab.tabClose(3);
					}
				}
			]
		});
	});

	/**系统设置*/
	$("body").on("click", "#okSetting", function () {
		layer.open({
			type: 2,
			title: "系统设置",
			shadeClose: true,
			closeBtn: 0, //不显示关闭按钮
			skin: "slideInRight ok-setting",
			area: ['340px', '100%'],
			offset: 'r', //右边
			time: 200000, //2秒后自动关闭
			anim: -1,
			content: "./pages/system/setting.html"
		});
	});

	/**
	 * 添加新窗口
	 */
	$("body").on("click", "#navBar .layui-nav-item a, #userInfo a", function () {
		// 如果不存在子级
		if ($(this).siblings().length == 0) {
			okTab.tabAdd($(this));
		}
		// 关闭其他展开的二级标签
		$(this).parent("li").siblings().removeClass("layui-nav-itemed");
		if (!$(this).attr("lay-id")) {
			var topLevelEle = $(this).parents("li.layui-nav-item");
			var childs = $("#navBar > li > dl.layui-nav-child").not(topLevelEle.children("dl.layui-nav-child"));
			childs.removeAttr("style");
		}
	});

	/**
	 * 左侧菜单展开动画
	 */
	$("#navBar").on("click", ".layui-nav-item a", function () {
		if (!$(this).attr("lay-id")) {
			var superEle = $(this).parent();
			var ele = $(this).next('.layui-nav-child');
			var height = ele.height();
			ele.css({"display": "block"});
			// 是否是展开状态
			if (superEle.is(".layui-nav-itemed")) {
				ele.height(0);
				ele.animate({height: height + "px"}, function () {
					ele.css({height: "auto"});
				});
			} else {
				ele.animate({height: 0}, function () {
					ele.removeAttr("style");
				});
			}
		}
	});

	/**
	 * 左边菜单显隐功能
	 */
	$(".ok-menu").click(function () {
		$(".layui-layout-admin").toggleClass("ok-left-hide");
		$(this).find("i").toggleClass("ok-menu-hide");
		localStorage.setItem("isResize", false);
		setTimeout(function () {
			localStorage.setItem("isResize", true);
		}, 1200);
	});

	/**
	 * 移动端的处理事件
	 */
	$("body").on("click", ".layui-layout-admin .ok-left a[data-url], .ok-make", function () {
		if ($(".layui-layout-admin").hasClass("ok-left-hide")) {
			$(".layui-layout-admin").removeClass("ok-left-hide");
			$(".ok-menu").find('i').removeClass("ok-menu-hide");
		}
	});

	/**
	 * tab左右移动
	 */
	$("body").on("click", ".okNavMove", function () {
		var moveId = $(this).attr("data-id");
		var that = this;
		okTab.navMove(moveId, that);
	});

	/**
	 * 刷新当前tab页
	 */
	$("body").on("click", ".ok-refresh", function () {
		okTab.refresh(this, function (okTab) {
			//刷新之后所处理的事件
		});
	});

	/**
	 * 关闭tab页
	 */
	$("body").on("click", "#tabAction a", function () {
		var num = $(this).attr("data-num");
		okTab.tabClose(num);
	});

	/**
	 * 键盘的事件监听
	 */
	$("body").on("keydown", function (event) {
		event = event || window.event || arguments.callee.caller.arguments[0];

		// 按 Esc
		if (event && event.keyCode === 27) {
			console.log("Esc");
			$("#fullScreen").children("i").eq(0).removeClass("layui-icon-screen-restore");
		}
		// 按 F11
		if (event && event.keyCode == 122) {
			console.log("F11");
			$("#fullScreen").children("i").eq(0).addClass("layui-icon-screen-restore");
		}
	});

	/**
	 * 全屏/退出全屏
	 */
	$("body").on("click", "#fullScreen", function () {
		if ($(this).children("i").hasClass("layui-icon-screen-restore")) {
			screenFun(2).then(function () {
				$("#fullScreen").children("i").eq(0).removeClass("layui-icon-screen-restore");
			});
		} else {
			screenFun(1).then(function () {
				$("#fullScreen").children("i").eq(0).addClass("layui-icon-screen-restore");
			});
		}
	});

	/**
	 * 全屏和退出全屏的方法
	 * @param num 1代表全屏 2代表退出全屏
	 * @returns {Promise}
	 */
	function screenFun(num) {
		num = num || 1;
		num = num * 1;
		var docElm = document.documentElement;

		switch (num) {
			case 1:
				if (docElm.requestFullscreen) {
					docElm.requestFullscreen();
				} else if (docElm.mozRequestFullScreen) {
					docElm.mozRequestFullScreen();
				} else if (docElm.webkitRequestFullScreen) {
					docElm.webkitRequestFullScreen();
				} else if (docElm.msRequestFullscreen) {
					docElm.msRequestFullscreen();
				}
				break;
			case 2:
				if (document.exitFullscreen) {
					document.exitFullscreen();
				} else if (document.mozCancelFullScreen) {
					document.mozCancelFullScreen();
				} else if (document.webkitCancelFullScreen) {
					document.webkitCancelFullScreen();
				} else if (document.msExitFullscreen) {
					document.msExitFullscreen();
				}
				break;
		}

		return new Promise(function (res, rej) {
			res("返回值");
		});
	}

	/**
	 * 系统公告
	 */
	$(document).on("click", "#notice", noticeFun);
	!function () {
		var notice = sessionStorage.getItem("notice");
		if (notice != "true") {
			noticeFun();
		}
	}();

	function noticeFun() {
		var srcWidth = okUtils.getBodyWidth();
		layer.open({
			type: 0, title: "系统公告", btn: "我知道啦", btnAlign: 'c', content: okHoliday.getContent(),
			yes: function (index) {
				if (srcWidth > 800) {
					layer.tips('公告跑到这里去啦', '#notice', {
						tips: [1, '#000'],
						time: 2000
					});
				}
				sessionStorage.setItem("notice", "true");
				layer.close(index);
			},
			cancel: function (index) {
				if (srcWidth > 800) {
					layer.tips('公告跑到这里去啦', '#notice', {
						tips: [1, '#000'],
						time: 2000
					});
				}
			}
		});
	}

	/**
	 * 捐赠作者
	 */
	$(".layui-footer button.donate").click(function () {
		layer.tab({
			area: ["330px", "350px"],
			tab: [{
				title: "支付宝",
				content: "<img src='images/zfb.jpg' width='200' height='300' style='margin: 0 auto; display: block;'>"
			}, {
				title: "微信",
				content: "<img src='images/wx.jpg' width='200' height='300' style='margin: 0 auto; display: block;'>"
			}]
		});
	});

	/**
	 * QQ群交流
	 */
	$("body").on("click", ".layui-footer button.communication, #noticeQQ", function () {
		layer.tab({
			area: ["auto", "370px"],
			tab: [{
				title: "QQ群5", 
				content: "<img src='images/qq5.png' width='200' height='300' style='margin: 0 auto; display: block;'/>"
			},{
				title: "QQ群4（已满）",
				content: "<img src='images/qq4.png' width='200' height='300' style='margin: 0 auto; display: block;'/>"
			}, {
				title: "QQ群3（已满）",
				content: "<img src='images/qq3.png' width='200' height='300' style='margin: 0 auto; display: block;'/>"
			}, {
				title: "QQ群2（已满）",
				content: "<img src='images/qq2.png' width='200' height='300' style='margin: 0 auto; display: block;'/>"
			}, {
				title: "QQ群1（已满）",
				content: "<img src='images/qq1.png' width='200' height='300' style='margin: 0 auto; display: block;'/>"
			}]
		});
	});

	/**
	 * 弹窗皮肤
	 */
	$("#alertSkin").click(function () {
		okLayer.open("皮肤动画", "pages/system/alertSkin.html", "50%", "45%", function (layero) {
		}, function () {
		});
	});

	/**
	 * 退出操作
	 */
	$("#logout").click(function () {
		okLayer.confirm("确定要退出吗？", function (index) {
			okTab.removeTabStorage(function (res) {
				okTab.removeTabStorage();
				window.location = "pages/login.html";
			});
		});
	});

	/**
	 * 锁定账户
	 */
	var lock_inter = "";
	lockShowInit(okUtils);
	$("#lock").click(function () {
		okLayer.confirm("确定要锁定账户吗？", function (index) {
			layer.close(index);
			okUtils.local("isLock", '1');//设置锁屏缓存防止刷新失效
			lockShowInit(okUtils);//锁屏
		});
	});

	/**锁屏方法*/
	function lockShowInit(okUtils) {
		let localLock = okUtils.local("isLock");
		$("#lockPassword").val("");
		if (!localLock) {
			return;
		}

		$(".lock-screen").show();
		Snowflake("snowflake"); // 雪花

		var lock_bgs = $(".lock-screen .lock-bg img");
		$(".lock-content .time .hhmmss").html(okUtils.dateFormat("", "hh <p lock='lock'>:</p> mm"));
		$(".lock-content .time .yyyymmdd").html(okUtils.dateFormat("", "yyyy 年 M 月 dd 日"));

		var i = 0, k = 0;
		lock_inter = setInterval(function () {
			i++;
			if (i % 8 == 0) {
				k = k + 1 >= lock_bgs.length ? 0 : k + 1;
				i = 0;
				lock_bgs.removeClass("active");
				$(lock_bgs[k]).addClass("active");
			}
			$(".lock-content .time .hhmmss").html(okUtils.dateFormat("", "hh <p lock='lock'>:</p> mm"));
		}, 1000);

		//提交密码
		form.on('submit(lockSubmit)', function (data) {
			console.log(data);
			if (data.field.lock_password !== "123456") {
				layer.msg("密码不正确", {
					icon: 5,
					zIndex: 999999991
				});
			} else {
				layer.msg("密码输入正确", {
					icon: 6,
					zIndex: 999999992,
					end: function () {
						okUtils.local("isLock", null);   //清除锁屏的缓存
						$("#lockPassword").val("");   //清除输入框的密码
						$(".lock-screen").hide();
						clearInterval(lock_inter);
					}
				});
			}
			return false;
		});

		//退出登录
		$("#lockQuit").click(function () {
			// window.location.href = "./pages/login.html";
			window.location.replace("./pages/login.html");  //替换当前页面
		});
	}

	console.log("        __                         .___      .__        \n" +
		"  ____ |  | __         _____     __| _/_____ |__| ____  \n" +
		" /  _ \\|  |/ /  ______ \\__  \\   / __ |/     \\|  |/    \\ \n" +
		"(  <_> )    <  /_____/  / __ \\_/ /_/ |  Y Y  \\  |   |  \\\n" +
		" \\____/|__|_ \\         (____  /\\____ |__|_|  /__|___|  /\n" +
		"            \\/              \\/      \\/     \\/        \\/\n" +
		"" +
		"版本：v2.0\n" +
		"作者：bobi\n" +
		"邮箱：bobi1234@foxmail.com\n" +
		"企鹅：833539807\n" +
		"描述：一个很赞的，扁平化风格的，响应式布局的后台管理模版，旨为后端程序员减压！");
});
