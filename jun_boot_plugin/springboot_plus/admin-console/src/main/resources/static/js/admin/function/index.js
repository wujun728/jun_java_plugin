layui.define([ 'form', 'laydate', 'table' ], function(exports) {
	var form = layui.form;
	var laydate = layui.laydate;
	var table = layui.table;
	var functionTable = null;
	var view ={
		
		init:function(){
			this.initTable();
			this.initSearchForm();
			this.initToolBar();
			window.dataReload = function(){
				Lib.doSearchForm($("#functionSearchForm"),functionTable)
			}
			
			
		},
		initTable:function(){
			functionTable = table.render({
				elem : '#functionTable',
				height : 'full-280',
				method : 'post',
				url : Common.ctxPath + '/admin/function/list.json' //数据接口
				,page : {"layout":['count','prev', 'page', 'next']} //开启分页
				,limit : 10,
				cols : [ [ //表头
				{
					type : 'checkbox',
					fixed:'left',
				}, 
				{
					field : 'id',
					title : 'id',
					width : 80,
					fixed:'left',
					sort : true
				}, {
					field : 'code',
					title : '功能代码',
					width : 150
				}, {
					field : 'name',
					title : '功能名称',
					width : 150,
					sort : true
				}, {
					field : 'accessUrl',
					title : '访问地址',
					width : 300,
					sort : true
				}, {
					field : 'parentFunctionText',
					title : '上一级功能',
					width : 120,
					sort : true
				},{
					field : 'typeText',
					title : '功能类型',
					width : 120,
					sort : true
				},
				{
					field : 'createTime',
					title : '创建时间',
					width : 120,
					templet:function(d){
						return Common.getDate(d.createTime);
					},
					sort : true
				}

				] ]

			});
		},
		
		initSearchForm:function(){
			Lib.initSearchForm( $("#functionSearchForm"),functionTable,form);
		},
		initToolBar:function(){
			toolbar = {
					add : function() { //获取选中数据
						var url = "/admin/function/add.do";
						Common.openDlg(url,"功能点管理>新增");
					},
					edit : function() { //获取选中数目
						var data = Common.getOneFromTable(table,"functionTable");
						if(data==null){
							return ;
						}
						var url = "/admin/function/edit.do?id="+data.id;
						Common.openDlg(url,"功能点管理>编辑");
						
					},
					del : function() { 
						layui.use(['del'], function(){
							  var delView = layui.del
							  delView.delBatch();
						});
					}
					
				};
			$('.ext-toolbar').on('click', function() {
				var type = $(this).data('type');
				toolbar[type] ? toolbar[type].call(this) : '';
			});
		}
	}

	 exports('index',view);
	
});