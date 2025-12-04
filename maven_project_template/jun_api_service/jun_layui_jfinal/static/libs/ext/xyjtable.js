/** common.js By Beginner Emain:zheng_jinfan@126.com HomePage:http://www.zhengjinfan.cn */
layui.define(['table'], function(exports) {
	"use strict";
	var table = layui.table;
	var xyjtable = {
		render: function(options) {
			return table.render(options);
		}
	};

	exports('xyjtable', xyjtable);
});