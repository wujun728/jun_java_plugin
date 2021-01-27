/**
 * 
 */
window.wangEditor.fullscreen = {
	// editor create之后调用
	init: function(editorSelector) {
		$(editorSelector + " .w-e-toolbar").append('<div class="w-e-menu"><a class="_wangEditor_btn_fullscreen" href="###" onclick="window.wangEditor.fullscreen.toggleFullscreen(\'' + editorSelector + '\')" data-toggle="tooltip" data-placement="bottom" title data-original-title="全屏编辑"><i class="fa fa-expand"></i></a></div>');
	},
	toggleFullscreen: function(editorSelector) {
		$(editorSelector).toggleClass('fullscreen-editor');
		var $a = $(editorSelector + ' ._wangEditor_btn_fullscreen');
		var $i = $a.find("i:first-child");
		if($i.hasClass("fa-expand")) {
			$a.attr("data-original-title", "退出全屏");
			$i.removeClass("fa-expand").addClass("fa-compress");
		} else {
			$a.attr("data-original-title", "全屏编辑");
			$i.removeClass("fa-compress").addClass("fa-expand");
		}
	}
};