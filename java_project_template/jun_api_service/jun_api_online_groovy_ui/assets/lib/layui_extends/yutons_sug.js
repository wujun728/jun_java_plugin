/**
 * @Title：yutons_sug搜索框提示插件||输入框提示插件
 * @Version：1.0
 * @Auth：yutons
 * @Date: 2018/10/4
 * @Time: 14:07
 */
layui.define(['jquery', 'table'], function(exports) {
	"use strict";
	const $ = layui.jquery,
		table = layui.table;

	let yutons_sug = function() {
		this.v = '1.0.1';
	};
	/**
	 * yutons_sug搜索框提示插件||输入框提示插件初始化
	 */
	yutons_sug.prototype.render = function(opt) {
        opt.urlBak=opt.url;
		//设置默认初始化参数
		opt.type = opt.type || 'sug'; //默认sug，传入sug||sugTable
		opt.elem = "#yutons_sug_" + opt.id;
		opt.height = opt.height || '229';
		opt.cellMinWidth = opt.cellMinWidth || '80'; //最小列宽
		opt.page = opt.page || true;
		opt.limits = opt.limits || [3];
		opt.loading = opt.loading || true;
		opt.limit = opt.limit || 3;
		opt.size = opt.size || 'sm'; //小尺寸的表格

		//初始化输入框提示容器
		$("#" + opt.id).after("<div id='sugItem' style='background-color: #fff;display: none;z-index:1;position: absolute;width:100%;'></div>");
		
		//输入框提示容器移出事件：鼠标移出隐藏输入提示框
		$("#" + opt.id).parent().mouseleave(function() {
			$("#" + opt.id).next().hide().html("");
		});

		
		if (opt.type == "sugTable") {
			//如果type为sugTable，则初始化下拉表格
			/* 输入框鼠标松开事件 */
			$("#" + opt.id).mouseup(function(e) {
				opt.obj = this;
				getSugTable(opt);
			})
			/* 输入框键盘抬起事件 */
			$("#" + opt.id).keyup(function(e) {
				opt.obj = this;
				getSugTable(opt);
			})
		} else if (opt.type == "sug") {
			//如果type为sug，则初始化下拉框
			$("#" + opt.id).next().css("border", "solid #e6e6e6 0.5px");
			/* 输入框鼠标松开事件 */
			$("#" + opt.id).mouseup(function(e) {
				opt.obj = this;
				getSug(opt);
			})
			/* 输入框键盘抬起事件 */
			$("#" + opt.id).keyup(function(e) {
				opt.obj = this;
				getSug(opt);
			})
		}
	}

	//搜索框提示插件||输入框提示插件--sugTable-下拉表格
	function getSugTable(opt) {
		//如果输入信息为"",则隐藏输入提示框,不再执行下边代码
        let kw = $.trim($(opt.obj).val());
        if (kw == "") {
            $("#" + opt.id).next().hide().html("");
            return false;
        }
		//下拉表格初始化table容器
		let html = '<table id="yutons_sug_' + opt.obj.getAttribute("id") + '" lay-filter="yutons_sug_' + opt.obj.getAttribute(
				"id") +
			'"></table>';
		$("#" + opt.obj.getAttribute("id")).next().show().html(html);

		//下拉表格初始化
        opt.url=opt.urlBak+kw;
		table.render(opt);
		//设置下拉表格样式
		$(opt.elem).next().css("margin-top", "0").css("background-color", "#ffffff");
		//监听下拉表格行单击事件（单击||双击事件为：row||rowDouble）设置单击或双击选中对应的行
		table.on('rowDouble(' + "yutons_sug_" + opt.id + ')', function(obj) {
			//获取选中行所传入字段的值
			$("#" + opt.id).val(obj.data[opt.id]);
			$("#" + opt.id).next().hide().html("");
		});
	}

	//搜索框提示插件||输入框提示插件--sug-下拉框
	function getSug(opt) {
		sessionStorage.setItem("inputId", opt.id)
		let kw = $.trim($(opt.obj).val());
		if (kw == "") {
			$("#" + opt.id).next().hide().html("");
			return false;
		}
		//sug下拉框异步加载数据并渲染下拉框
		$.ajax({
			type: "get",
			url: opt.urlBak+kw,
			success: function(data) {
				let html = "";
				layui.each(data.data, (index, item) => {
					//if (item[sessionStorage.getItem("inputId")].indexOf(decodeURI(kw)) >= 0) {
						html +=
							"<div class='item' style='padding: 3px 10px;cursor: pointer;' onmouseenter='getFocus(this)' onClick='getCon(this);'>" +
							item[sessionStorage.getItem("inputId")] + "</div>";
					//}
				});
				if (html != "") {
					$("#" + sessionStorage.getItem("inputId")).next().show().html(html);
				} else {
					$("#" + sessionStorage.getItem("inputId")).next().hide().html("");
				}
			}
		});
	}
	//搜索框提示插件||输入框提示插件--sug-下拉框上下键移动事件和回车事件
	$(document).keydown(function(e) {
		e = e || window.event;
		let keycode = e.which ? e.which : e.keyCode;
		if (keycode == 38) {
			//上键事件
			if ($.trim($("#" + sessionStorage.getItem("inputId")).next().html()) == "") {
				return;
			}
			movePrev(sessionStorage.getItem("inputId"));
		} else if (keycode == 40) {
			//下键事件
			if ($.trim($("#" + sessionStorage.getItem("inputId")).next().html()) == "") {
				return;
			}
			$("#" + sessionStorage.getItem("inputId")).blur();
			if ($(".item").hasClass("addbg")) {
				moveNext();
			} else {
				$(".item").removeClass('addbg').css("background", "").eq(0).addClass('addbg').css("background", "#e6e6e6");
			}
		} else if (keycode == 13) {
			//回车事件
			dojob();
		}
	});
	//上键事件
	let movePrev = function(id) {
		$("#" + id).blur();
		let index = $(".addbg").prevAll().length;
		if (index == 0) {
			$(".item").removeClass('addbg').css("background", "").eq($(".item").length - 1).addClass('addbg').css(
				"background", "#e6e6e6");
		} else {
			$(".item").removeClass('addbg').css("background", "").eq(index - 1).addClass('addbg').css("background", "#e6e6e6");
		}
	}
	//下键事件
	let moveNext = function() {
		let index = $(".addbg").prevAll().length;
		if (index == $(".item").length) {
			$(".item").removeClass('addbg').css("background", "").eq(0).addClass('addbg').css("background", "#e6e6e6");
		} else {
			$(".item").removeClass('addbg').css("background", "").eq(index + 1).addClass('addbg').css("background", "#e6e6e6");
		}
	}
	//回车事件
	let dojob = function() {
		let value = $(".addbg").text();
		$("#" + sessionStorage.getItem("inputId")).blur();
		$("#" + sessionStorage.getItem("inputId")).val(value);
		$("#" + sessionStorage.getItem("inputId")).next().hide().html("");
	}

	//上下键选择和鼠标选择事件改变颜色
	window.getFocus = function(obj) {
		$(".item").css("background", "");
		$(obj).css("background", "#e6e6e6");
	}

	//点击选中事件，获取选中内容并回显到输入框
	window.getCon = function(obj) {
		let value = $(obj).text();
		$("#" + $(".item").parent().prev().attr("id")).val(value);
		$("#" + $(".item").parent().prev().attr("id")).next().hide().html("");
	}

	//自动完成渲染
	yutons_sug = new yutons_sug();
	//暴露方法
	exports("yutons_sug", yutons_sug);
});
