layui.define(['element'], function (exports) {
	var element = layui.element;
	var tab = {
		/*新增一个Tab页面
		* @id，tab页面唯一标识，可是标签中data-id的属性值
		* @url,tab页面地址
		* @name,tab页面标题，
		*/
		tabTem: function (id, url, title) {
			element.tabAdd('gg-admin-tab', {
				id: id,
				url:url,
				title: '<span class="gg-tab-active"></span>' + title
			});
			$('#GouguAppBody').append('<div class="gg-tab-page" title="'+title+'" id="tabItem' + id + '" data-id="' + id + '" data-url="' + url + '"></div>');
		},
		tabAdd: function (id, url, title) {
			var thetabs = $('#pageTabUl').find('li');
			if (thetabs.length > 12) {
				layer.tips('点击LOGO快速关闭已开的TAB页面', $('.layui-logo'));
			}
			if (thetabs.length > 16) {
				layer.msg('你已打开了太多TAB页面了，请关闭部分TAB再使用');
				return false;
			}
			element.tabAdd('gg-admin-tab', {
				id: id,
				url:url,
				title: '<span class="gg-tab-active"></span>' + title
			});
			$('#GouguAppBody').append('<div class="gg-tab-page" title="'+title+'" id="tabItem' + id + '" data-id="' + id + '"><iframe id="' + id + '" data-frameid="' + id + '" src="' + url + '" frameborder="0" align="left" width="100%" height="100%" scrolling="yes"></iframe></div>');
			this.tabChange(id);
		},
		//从子页面打开新的Tab页面，防止id重复，使用时间戳作为唯一标识
		sonAdd: function (url, title,id) {
			this.tabAdd( id, url,title);
		},
		//根据传入的id传入到指定的tab项，并滚动定位
		tabChange: function (id) {
			element.tabChange('gg-admin-tab', id);
		},
		//删除tab页面
		tabDelete: function (id) {
			if (id == 0) {
				return;
			}
			element.tabDelete('gg-admin-tab', id);
		},
		/*删除所有tab页面
		*@ids 是一个数组，存放多个id，调用tabDelete方法分别删除
		*/
		tabDeleteAll: function (ids) {
			var that = this;
			$.each(ids, function (i, item) {
				that.tabDelete(item);
			})
		},
		//tab页面关闭自己，需要使用父iframe配合一起调用，如：parent.tab.sonClose();
		sonClose: function (id) {
			$('.layui-tab .layui-tab-title .layui-this i').click();
		},
		//滚动tab
		tabRoll: function (event, index) {
			var $tabTitle = $("#pageTabs .layui-tab-title"),
				$tabs = $tabTitle.children("li"),
				$outerWidth = ($tabTitle.prop("scrollWidth"), $tabTitle.outerWidth()),
				$left = parseFloat($tabTitle.css("left"));
			if ("left" === event) {
				if (!$left && $left <= 0) return;
				var roll = -$left - $outerWidth;
				$tabs.each(function (item, i) {
					var $item = $(i),
						$itemleft = $item.position().left;
					if ($itemleft >= roll) {
						return $tabTitle.css("left", -$itemleft), false;
					}
				})
			} else "auto" === event ? ! function () {
				var $itemleft, $item = $tabs.eq(index);
				if ($item[0]) {
					if ($itemleft = $item.position().left, $itemleft < -$left) return $tabTitle.css("left", -$itemleft);
					if ($itemleft + $item.outerWidth() >= $outerWidth - $left) {
						var $diff = $itemleft + $item.outerWidth() - ($outerWidth - $left);
						$tabs.each(function (e, i) {
							var $tabitem = $(i),
								$tabitemleft = $tabitem.position().left;
							if ($tabitemleft + $left > 0 && $tabitemleft - $left > $diff) return $tabTitle.css("left", -$tabitemleft), false;
						})
					}
				}
			}() : $tabs.each(function (item, i) {
				var $item = $(i),
					$itemleft = $item.position().left;
				if ($itemleft + $item.outerWidth() >= $outerWidth - $left) return $tabTitle.css("left", -$itemleft), false;
			})
		},
		refresh:function(id){
			if(parent.document.getElementById(id)){
				var src = parent.document.getElementById(id).contentWindow.location.href ? parent.document.getElementById(id).contentWindow.location.href : iframe.src;
				document.getElementById(id).src = src;
			}
		},
		tabCookie:function(){
			let thetabs = $('#pageTabUl').find('li');
			let tab_id = 0,tab_array=[];
			$('#pageTabUl li').each(function(index,item){
				let _id = $(item).attr('lay-id'),_url = $(item).attr('lay-url'),_title = $(item).text();
				if(_id>0){
					tab_array.push({'id':_id,'url':_url,'title':"_title"});
				}
				if($(item).hasClass('layui-this')){
					tab_id = _id;
				}				
			})
			if(tab_array.length>0){
				let tabs_obj = {
					'tab_id':tab_id,
					'tab_array':tab_array
				}
				//console.log(tabs_obj);
				tab.setCookie('gougutab',JSON.stringify(tabs_obj));
			}
			else{
				tab.delCookie('gougutab');
			}
		},
		setCookie: function (name, value, days) {
			if (days) {
				var date = new Date();
				date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
				var expires = "; expires=" + date.toGMTString();
			}
			else {
				var expires = "";
			}
			document.cookie = name + "=" + value + expires + "; path=/";
		},
		getCookie: function (name) {
			var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
			if (arr != null) {
				return unescape(arr[ 2 ]);
			}
			return null;
		},
		delCookie: function (name) {
			this.setCookie(name, "", -1);
		}
	};
	//切换tab
	element.on('tab(gg-admin-tab)', function (data) {
		let thisPage = $('#GouguAppBody').find('.gg-tab-page').eq(data.index);
		if(thisPage.find('iframe').length==0){
			let id = thisPage.data('id');
			let url = thisPage.data('url');
			thisPage.html('<iframe id="' + id + '" data-frameid="' + id + '" src="' + url + '" frameborder="0" align="left" width="100%" height="100%" scrolling="yes"></iframe>');
		}
		$('#GouguAppBody').find('.gg-tab-page').removeClass('layui-show');
		thisPage.addClass('layui-show');
		if(data.index==0){
			tab.refresh(0);
		}
		tab.tabRoll("auto", data.index);
		tab.tabCookie();
	});
	//删除tab
	element.on('tabDelete(gg-admin-tab)', function (data) {
		$('#GouguAppBody').find('.gg-tab-page').eq(data.index).remove();
		tab.tabRoll("auto", data.index);
		tab.tabCookie();
	});

	//右滚动tab
	$('[gg-event="tabRollRight"]').click(function () {
		tab.tabRoll("right");
	})
	//左滚动tab
	$('[gg-event="tabRollLeft"]').click(function () {
		tab.tabRoll("left");
	})

	//关闭全部tab，只保留首页
	$("#GouguApp").on('click', '[gg-event="closeAllTabs"]', function () {
		var thetabs = $('#pageTabs .layui-tab-title').find('li'), ids = [];
		for (var i = 0; i < thetabs.length; i++) {
			var id = thetabs.eq(i).attr('lay-id');
			ids.push(id);
		}
		tab.tabDeleteAll(ids);
		return false;
	})

	//关闭其他tab
	$("#GouguApp").on('click', '[gg-event="closeOtherTabs"]', function () {
		var thetabs = $('#pageTabs .layui-tab-title').find('li'), ids = [];
		var thisid = $('#pageTabs .layui-tab-title').find('.layui-this').attr('lay-id');
		for (var i = 0; i < thetabs.length; i++) {
			var id = thetabs.eq(i).attr('lay-id');
			if (id != thisid) {
				ids.push(id);
			}
		}
		tab.tabDeleteAll(ids);
		return false;
	})

	//关闭当前tab
	$("#GouguApp").on('click', '[gg-event="closeThisTabs"]', function () {
		var thisid = $('#pageTabs .layui-tab-title').find('.layui-this').attr('lay-id');
		tab.tabDelete(thisid);
		return false;
	})

	//当点击有side-menu-item属性的标签时，即左侧菜单栏中内容 ，触发tab
	$('body').on('click', 'a.side-menu-item', function () {
		var that = $(this);
		var url = that.data("href"), id = that.data("id"), title = that.data("title");
		if (url == '' || url == '/') {
			return false;
		}
		//这时会判断右侧.layui-tab-title属性下的有lay-id属性的li的数目，即已经打开的tab项数目
		$('.site-menu-active').removeClass('layui-this');
		that.addClass('layui-this');
		$('#GouguApp').removeClass('side-spread-sm');
		if ($(".layui-tab-title li[lay-id]").length <= 0) {
			//打开新的tab项
			tab.tabAdd(id, url, title);
		} else {
			//否则判断该tab项是否以及存在
			var isHas = false;
			$.each($(".layui-tab-title li[lay-id]"), function () {
				//如果点击左侧菜单栏所传入的id 在右侧tab项中的lay-id属性可以找到，则说明该tab项已经打开
				if ($(this).attr("lay-id") == id) {
					isHas = true;
					$('[data-frameid="' + id + '"]').attr('src', url);
					//最后不管是否新增tab，最后都转到要打开的选项页面上
					tab.tabChange(id);
				}
			})
			if (isHas == false) {
				//标志为false 新增一个tab项
				tab.tabAdd(id, url, title);
			}
		}
		try {
			layer.close(window.openTips);
		} catch (e) {
			console.log(e.message);
		}
	});

	//左侧菜单展开&收缩
	$('#GouguApp').on('click', '[gg-event="shrink"]', function () {
		if (window.innerWidth > 992) {
			$('#GouguApp').toggleClass('side-spread');
		} else {
			$('#GouguApp').toggleClass('side-spread-sm');
		}
	})
	$('#GouguApp').on('click', '[gg-event="shade"]', function () {
		$('#GouguApp').removeClass('side-spread-sm');
	})

	//左上角清除缓存
	$('#GouguApp').on('click', '[gg-event="cache"]', function (e) {
		let that = $(this);
		let url = $(this).data('href');
		if (that.attr('class') === 'clearThis') {
			layer.tips('正在努力清理中...', this);
			return false;
		}
		layer.tips('正在清理系统缓存...', this);
		that.attr('class', 'clearThis');
		$.ajax({
			url: url,
			success: function (res) {
				if (res.code == 1) {
					setTimeout(function () {
						that.attr('class', '');
						layer.tips(res.msg, that);
					}, 1000)
				} else {
					layer.tips(res.msg, that);
				}
			}
		})
	})

	//右上角刷新当前tab页面
	$('#GouguApp').on('click', '[gg-event="refresh"]', function () {
		var that = $(this);
		if ($(this).hasClass("refreshThis")) {
			that.removeClass("refreshThis");
			var iframe = $(".gg-tab-page.layui-show").find("iframe")[0];
			if (iframe) {
				tab.refresh(iframe.id);
			}
			setTimeout(function () {
				that.attr("class", "refreshThis");
			}, 2000)
		} else {
			layer.tips("每2秒只可刷新一次", this, {
				tips: 1
			});
		}
		return false;
	})

	//右上角全屏&退出全屏
	$('#GouguApp').on("click", ".fullScreen", function () {
		if ($(this).hasClass("layui-icon-screen-restore")) {
			screenFun(2).then(function () {
				$(".fullScreen").eq(0).removeClass("layui-icon-screen-restore");
			});
		} else {
			screenFun(1).then(function () {
				$(".fullScreen").eq(0).addClass("layui-icon-screen-restore");
			});
		}
	});

	//小菜单展现多级菜单
	$("#GouguApp").on("mouseenter", ".layui-nav-tree .menu-li", function () {
		var tips = $(this).prop("innerHTML");
		if ($('#GouguApp').hasClass('side-spread') && tips) {
			tips = "<ul class='layuimini-menu-left-zoom layui-nav layui-nav-tree layui-this'><li class='layui-nav-item layui-nav-itemed'>" + tips + "</li></ul>";
			window.openTips = layer.tips(tips, $(this), {
				tips: [2, '#2f4056'],
				time: 300000,
				skin: "popup-tips",
				success: function (el) {
					var left = $(el).position().left - 10;
					$(el).addClass('side-layout').css({ left: left });
					element.render();
				}
			});
		}
	});

	$("body").on("mouseleave", ".popup-tips", function () {
		try {
			layer.close(window.openTips);
		} catch (e) {
			console.log(e.message);
		}
	});

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

	function isFullscreen() {
		return document.fullscreenElement ||
			document.msFullscreenElement ||
			document.mozFullScreenElement ||
			document.webkitFullscreenElement || false;
	}

	window.onresize = function () {
		if (!isFullscreen()) {
			$(".fullScreen").eq(0).removeClass("layui-icon-screen-restore");
		}
	}
	exports('ggadmin', tab);
});  