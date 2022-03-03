/**
 * 代码生成器js
 */

$(function() {
	initialPage();
	getGrid();
});

function initialPage() {
	$(window).resize(function() {
		$('#dataGrid').bootstrapTable('resetView', {
			height : $(window).height() - 56
		});
	});
}

function getGrid() {
	$('#dataGrid').bootstrapTableEx({
		url : '../../sys/generator/list?_' + $.now(),
		height : $(window).height() - 56,
		queryParams : function(params) {
			params.name = vm.keyword;
			return params;
		},
		columns : [ {
			checkbox : true
		}, {
			field : "tableName",
			title : "表格名称"
		}, {
			field : "objName",
			title : "数据库引擎类型"
		}, {
			field : "tableComment",
			title : "备注"
		}, {
			field : "createTime",
			title : "创建时间"
		}]
	})
}

var vm = new Vue({
	el : '#dpLTE',
	data : {
		keyword : null
	},
	methods : {
		load : function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
		generate : function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections'), names = [];
			if (checkedRow(ck)) {
				$.each(ck, function(idx, item) {
					names[idx] = item.tableName;
				});
				dialogOpen({
					title : '生成代码',
					url : 'base/generator/code.html?_' + $.now(),
					width : '530px',
					height : '500px',
					success : function(iframeId) {
						top.frames[iframeId].vm.generator.tables = names;
					},
					yes : function(iframeId) {
						top.frames[iframeId].vm.acceptClick();
					},
				});
			}
		}
	}
})