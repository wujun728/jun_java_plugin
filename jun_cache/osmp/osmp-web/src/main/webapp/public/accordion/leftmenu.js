$(function() {
	InitLeftMenu();
	tabClose();
	tabCloseEven();
	// 释放内存
	$.fn.panel.defaults = $.extend({}, $.fn.panel.defaults, {
		onBeforeDestroy : function() {
			var frame = $('iframe', this);
			if (frame.length > 0) {
				frame[0].contentWindow.document.write('');
				frame[0].contentWindow.close();
				frame.remove();
			}
			if ($.browser.msie) {
				CollectGarbage();
			}
		}
	});
	
	  $('#maintabs').tabs({ onSelect : function(title) {
	  	rowid="";
	  } });
	
});
var rowid="";
// 初始化左侧
function InitLeftMenu() {
	$('.easyui-accordion li div').click(function() {
		var tabTitle = $(this).attr("title");
		var url = $(this).attr("url");
		var icon = $(this).attr("iconCls");
		addTab(tabTitle, url, icon);
		$('.easyui-accordion li div').removeClass("selected");
		$(this).parent().addClass("selected");
	}).hover(function() {
		$(this).parent().addClass("hover");
	}, function() {
		$(this).parent().removeClass("hover");
	});

	
}
// 获取左侧导航的图标
function getIcon(menuid) {
	var icon = 'icon ';
	$.each(_menus.menus, function(i, n) {
		$.each(n.menus, function(j, o) {
			if (o.menuid == menuid) {
				icon += o.icon;
			}
		});
	});

	return icon;
}

function addTab(subtitle, url, icon) {
	rowid="";
	$.messager.progress({
		text : '页面加载中....',
		interval : 200
	});
	if (!$('#maintabs').tabs('exists', subtitle)) {
		if (url){  
            var content = '<iframe scrolling="no" frameborder="0" src="'+url+'" style="width:100%;height:99.5%;overflow-y:auto;"></iframe>';  
        } else {  
            var content = '未实现';  
        } 
		
		$('#maintabs').tabs('add', {
			title : subtitle,
			content:content, 
			closable : true,
			icon : icon
		});
	} else {
		$('#maintabs').tabs('select', subtitle);
		$.messager.progress('close');
	}

	// $('#maintabs').tabs('select',subtitle);
	tabClose();

}
var title_now;
function addLeftOneTab(subtitle, url, icon) {
	rowid="";
	if ($('#maintabs').tabs('exists', title_now)) {
		$('#maintabs').tabs('select', title_now);
			if(title_now!=subtitle)
			{
			addmask();
			$('#maintabs').tabs('update', {
				tab : $('#maintabs').tabs('getSelected'),
				options : {
					title : subtitle,
					href : url,
					closable : false,
					icon : icon
				}
			});
		}
	} else {
		addmask();
		$('#maintabs').tabs('add', {
			title : subtitle,
			href : url,
			closable : false,
			icon : icon
		});
	}
	if ($.browser.msie) {
		CollectGarbage();
	}
	title_now = subtitle;
	// $('#maintabs').tabs('select',subtitle);
	// tabClose();

}
function addmask() {
	$.messager.progress({
		text : '页面加载中....',
		interval : 100
	});
}
function createFrame() {
	var s = '<iframe name="tabiframe" id="tabiframe"  scrolling="no" frameborder="0"  src="about:blank" style="width:100%;height:99.5%;overflow-y:hidden;"></iframe>';
	return s;
}

function tabClose() {
	/* 双击关闭TAB选项卡 */
	$(".tabs-inner").dblclick(function() {
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close', subtitle);
	})
	/* 为选项卡绑定右键 */
	$(".tabs-inner").bind('contextmenu', function(e) {
		$('#mm').menu('show', {
			left : e.pageX,
			top : e.pageY
		});

		var subtitle = $(this).children(".tabs-closable").text();

		$('#mm').data("currtab", subtitle);
		// $('#maintabs').tabs('select',subtitle);
		return false;
	});
}

// 绑定右键菜单事件
function tabCloseEven() {
	// 刷新
	$('#mm-tabupdate').click(function() {
		var currTab = $('#maintabs').tabs('getSelected').find("iframe");
		currTab.attr("src",currTab.attr("src"));
	})
	// 关闭当前
	$('#mm-tabclose').click(function() {
		var currtab_title = $('#mm').data("currtab");
		$('#maintabs').tabs('close', currtab_title);
	})
	
	// 全部关闭
	$('#mm-tabcloseall').click(function() {
		
		var tabs = $("#maintabs").tabs("tabs");
		var length = tabs.length;
		for(var i=1;i<length;i++){
			$('#maintabs').tabs('close', 1);
		}
		return false;
	});
	
	// 关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function() {
		var currTab = $('#maintabs').tabs('getSelected');
		var currIndex = $('#maintabs').tabs('getTabIndex',currTab);
		
		if(currIndex==0){
			return;
		}
		
		var tabs = $("#maintabs").tabs("tabs");
		var length = tabs.length;
		
		//删除当前后面的tab
		for(var i=currIndex+1;i<length;i++){
			$('#maintabs').tabs('close', currIndex+1);
		}
		
		for(var n=1;n<currIndex;n++){
			$('#maintabs').tabs('close', 1);
		}
		
	});

	// 退出
	$("#mm-exit").click(function() {
		$('#mm').menu('hide');
	});
}

$.parser.onComplete = function() {/* 页面所有easyui组件渲染成功后，隐藏等待信息 */
	if ($.browser.msie && $.browser.version < 7) {/* 解决IE6的PNG背景不透明BUG */
	}
	window.setTimeout(function() {
		$.messager.progress('close');
	}, 200);
};
