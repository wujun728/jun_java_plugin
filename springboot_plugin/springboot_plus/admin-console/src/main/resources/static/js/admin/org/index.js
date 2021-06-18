layui.define([ 'form', 'laydate', 'table' ], function(exports) {
	var form = layui.form;
	var laydate = layui.laydate;
	var table = layui.table;
	var userTable = null;
	var view ={
		
		init:function(){
			this.initTable();
			this.initSearchForm();
			this.initToolBar();
			window.dataReload = function(){
				Lib.doSearchForm($("#orgSearchForm"),userTable)
			}
			
			
		},
		initTable:function(){
			userTable = table.render({
				elem : '#orgTable',
				height : Lib.getTableHeight(2),
				method : 'post',
				url : Common.ctxPath + '/admin/org/list.json' //数据接口
				,page : {"layout":['count','prev', 'page', 'next']} //开启分页
				,limit : 10,
				cols : [ [ //表头
				{
					type : 'checkbox',
					fixed:'left',
				}, {
					field : 'id',
					title : 'id',
					width : 80,
					fixed:'left',
					sort : true
				}, {
					field : 'code',
					title : '机构代码',
					width : 150
				}, {
					field : 'name',
					title : '机构名称',
					width : 120,
					sort : true
				}, {
					field : 'parentOrgText',
					title : '上一级机构',
					width : 150,
					sort : true
				},
				{
					field : 'typeText',
					title : '机构类型',
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
			Lib.initSearchForm( $("#orgSearchForm"),userTable,form);
		},
		initToolBar:function(){
			toolbar = {
					add : function() { //获取选中数据
						var url = "/admin/org/add.do";
						Common.openDlg(url,"用户管理>新增");
					},
					edit : function() { //获取选中数目
						var data = Common.getOneFromTable(table,"orgTable");
						if(data==null){
							return ;
						}
						var url = "/admin/org/edit.do?id="+data.id;
						Common.openDlg(url,"用户管理>编辑");
						
					},
					del : function() { 
						layui.use(['del'], function(){
							  var delView = layui.del
							  delView.delBatch();
						});
					},
					orgUser : function() { 
						var data = Common.getOneFromTable(table,"orgTable");
						if(data==null){
							return ;
						}
						var url = "/admin/org/user/list.do?orgId="+data.id;
						Common.openDlg(url,"组织管理>用户列表");
						
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