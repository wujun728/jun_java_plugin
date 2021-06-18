layui.define([ 'form', 'laydate', 'table' ], function(exports) {
	var form = layui.form;
	var laydate = layui.laydate;
	var table = layui.table;
	var dictTable = null;

	var view = {

		init : function() {
			this.initTable();
			this.initSearchForm();
			this.initToolBar();
			window.dataReload = function() {
				Lib.doSearchForm($("#searchForm"), dictTable)
			}

		},
		initTable : function() {
			dictTable = table.render({
				elem : '#dictTable',
				height : Lib.getTableHeight(1),
				method : 'post',
				url : Common.ctxPath + '/admin/dict/list.json' //数据接口
				,
				page : Lib.tablePage //开启分页
				,
				limit : 10,
				cols : [ [ //表头
				{
					type : 'checkbox',
					fixed : 'left',
				}, {
					field : 'id',
					title : 'id',
					width : 80,
					fixed : 'left',
					sort : true
				}, {
					field : 'value',
					title : '字典值1',
					fixed : 'left',
					width : 120,
				}, {
					field : 'name',
					title : '字典名称',
					width : 180,
				}, {
					field : 'type',
					title : '字典类型',
					width : 180,
				}, {
					field : 'typeName',
					title : '字典类型名称',

					width : 180,
				}, {
					field : 'sort',
					title : '排序',
					width : 60,
				}, {
					field : 'parent',
					title : '父字典',
					width : 100,
				},

				{
					field : 'remark',
					title : '备注',

					width : 100,
				}, {
					field : 'createTime',
					title : '创建时间',

					width : 100,
				}

				] ]

			});
		},

		initSearchForm : function() {
			Lib.initSearchForm($("#searchForm"), dictTable, form);
		},
		initToolBar : function() {
			toolbar = {
				add : function() { //获取选中数据
					var url = "/admin/dict/add.do";
					Common.openDlg(url, "字典数据管理>新增");
				},
				edit : function() { //获取选中数目
					var data = Common.getOneFromTable(table, "dictTable");
					if (data == null) {
						return;
					}
					var url = "/admin/dict/edit.do?id=" + data.id;
					Common.openDlg(url, "字典数据管理>" + data.value + ">编辑");

				},
				del : function() {
					layui.use([ 'del' ], function() {
						var delView = layui.del
						delView.delBatch();
					});
				},
				exportExcel : function() {
					layui.use([ 'dictApi' ], function() {
						var dictApi = layui.dictApi
						Common.openConfirm("确认要导出这些字典数据?", function() {
							dictApi.exportExcel($("#searchForm"), function(fileId) {
								Lib.download(fileId);
							})
						})
					});

				},
				importExcel:function(){
				    //上传路径
				    var uploadUrl = Common.ctxPath+"/admin/dict/excel/import.do";
				    //模板
				    var templatePath= "/admin/dict/dict_upload_template.xls";
				    //公共的简单上传文件处理
				    var url = "/core/file/simpleUpload.do?uploadUrl="+uploadUrl+"&templatePath="+templatePath;
                    Common.openDlg(url, "字典数据管理>上传");
				}

			};
			$('.ext-toolbar').on('click', function() {
				var type = $(this).data('type');
				toolbar[type] ? toolbar[type].call(this) : '';
			});
		}
	}

	exports('index', view);

});