<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" charset="UTF-8">
	var centerTabs;
	function addTabFun(opts) {
		var options = $.extend({
			title : '',
			content : '<iframe src="' + opts.src + '" frameborder="0" style="border:0;width:100%;height:99.2%;"></iframe>',
			closable : true,
			iconCls : ''
		}, opts);
		if (centerTabs.tabs('exists', options.title)) {
			centerTabs.tabs('close', options.title);
		}
		centerTabs.tabs('add', options);
	};
	$(function() {
		centerTabs = $('#centerTabs').tabs({
			border : false,
			fit : true
		});
		setTimeout(function() {
			var src = 'userController.do?home';
			centerTabs.tabs('add', {
				title : '首页',
				content : '<iframe src="' + src + '" frameborder="0" style="border:0;width:100%;height:99.2%;"></iframe>',
				closable : true,
				iconCls : ''
			});
		}, 0);
	});
</script>
<div id="centerTabs"></div>