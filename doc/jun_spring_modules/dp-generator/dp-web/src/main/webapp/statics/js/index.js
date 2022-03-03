//生成菜单

// iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height() - 146);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();

var vm = new Vue({
	el : '#dpLTE',
	data : {
		user : {},
		menuList : {},
		main : "system/index/main.html",
		pswd : null,
		newPswd : null,
		navTitle : "控制台"
	},
	methods : {
		code : function() {
			vm.main = "base/generator/list.html";
			vm.navTitle = "代码生成器";
		}
	}
});