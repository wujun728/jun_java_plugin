window.rootPath = (function (src) {
	src = document.currentScript
		? document.currentScript.src
		: document.scripts[document.scripts.length - 1].src;
	return src.substring(0, src.lastIndexOf("/") + 1);
})();
if (typeof $ == "undefined") {
	window.jQuery = layui.jquery;
	window.$ = layui.jquery;
}
if (typeof moduleInit == "undefined") {
	window.moduleInit = [];
}
var module = {};
if (moduleInit.length > 0) {
	for (var i = 0; i < moduleInit.length; i++) {
		module[moduleInit[i]] = moduleInit[i];
	}
}
layui.config({
	base: /* rootPath +  */"/assets/module/",
	version: "1.0.0"
}).extend(module).use(moduleInit, function () {
	if (typeof gouguInit === 'function') {
		gouguInit();
	}
});


