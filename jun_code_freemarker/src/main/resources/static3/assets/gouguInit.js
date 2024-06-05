window.rootPath = (function (src) {
	src = document.currentScript
		? document.currentScript.src
		: document.scripts[document.scripts.length - 1].src;
		console.log("src="+src);
	return src.substring(0, src.lastIndexOf("/") + 1);
})();
console.log("rootPath="+rootPath);
if (typeof $ == "undefined") {
	window.jQuery = layui.jquery;
	window.$ = layui.jquery;
}
if (typeof moduleInit == "undefined") {
	window.moduleInit = [];
}
console.log("moduleInit="+moduleInit);
var module = {
	notice: 'notice/notice',
	admin: 'admin',
	xnUtil: 'xnUtil/xnUtil' 
};

if (moduleInit.length > 0) {
	for (var i = 0; i < moduleInit.length; i++) {
		module[moduleInit[i]] = moduleInit[i];
	}
}
console.log("moduleInit="+moduleInit);
console.log("module="+module);
layui.config({
	base: rootPath + "module/",
	version: "1.0.0"
}).extend(module).use(moduleInit, function () {
	if (typeof gouguInit === 'function') {
		gouguInit();
	}
});


