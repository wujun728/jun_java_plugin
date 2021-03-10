layui.define([ 'form', 'laydate', 'table' ], function(exports) {
	var form = layui.form;
	var laydate = layui.laydate;
	var table = layui.table;
	var userRoleTable = null;
	var userId = null;
	var view ={
		
		init:function(id){
			userId = id;
			this.initTable();
			this.initSearchForm();
			this.initToolBar();
			
			$("#close").click(function(){
				Lib.closeFrame();
			});
			window.dataReload = function(){
				Lib.doSearchForm($("#userRoleSearchForm"),userRoleTable)
			}
			
		},
		initTable:function(){
			userRoleTable = table.render({
				elem : '#userRoleTable',
				height : Lib.getTableHeight(1),
				method : 'post',
				url : Common.ctxPath + '/admin/user/role/list.json?userId='+userId //数据接口
				,page : false
				,limit : 10000,
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
				},{
					field : 'orgName',
					title : '机构名称',
					width : 150
				}, {
					field : 'roleName',
					title : '角色名称',
					width : 120,
					sort : true
				}

				] ]

			});
		},
		
		initSearchForm:function(){
			Lib.initSearchForm( $("#userRoleSearchForm"),userRoleTable,form);
		},
		initToolBar:function(){
			toolbar = {
					add : function() { //获取选中数据
						var url = "/admin/user/role/add.do?id="+userId;
						Common.openDlg(url,"用户管理>角色管理>新增角色");
					},
					del : function() { 
						layui.use(['roleDel'], function(){
							  var delView = layui.roleDel
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

	 exports('userRole',view);
	
});