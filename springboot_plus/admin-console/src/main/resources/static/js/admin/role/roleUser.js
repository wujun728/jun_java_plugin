layui.define([ 'form', 'laydate', 'table' ], function(exports) {
	var form = layui.form;
	var laydate = layui.laydate;
	var table = layui.table;
	var userTable = null;
	var view ={
		
		init:function(roleId){
			this.initTable(roleId);
			this.initSearchForm();
			this.initClose();
		},
		initClose:function(){
			$("#close").click(function(){
				Lib.closeFrame();
			});
		},
		
		initTable:function(roleId){
			userTable = table.render({
				elem : '#userTable',
				height : 'full-180',
				method : 'post',
				url : Common.ctxPath + '/admin/role/user/list.json?roleId='+roleId //数据接口
				,page : Lib.tablePage //开启分页
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
					title : '用户名',
					width : 150
				}, {
					field : 'name',
					title : '姓名',
					width : 120,
					sort : true
				}, {
					field : 'orgIdText',
					title : '任职机构',
					width : 300,
					sort : true
				},{
					field : 'orgId1Text',
					title : '兼职机构',
					width : 300,
					sort : true
				}, {
					field : 'stateText',
					title : '状态',
					width : 80,
					sort : true
				},
				 {
					field : 'jobTypeText',
					title : '职位',
					width : 80,
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
			Lib.initSearchForm( $("#roleUserSearchForm"),userTable,form);
		}
	}

	 exports('roleUser',view);
	
});