layui.define([ 'form', 'laydate', 'table','roleApi'], function(exports) {
	var form = layui.form;
	var laydate = layui.laydate;
	var table = layui.table;
	var checkedRoleId=null;
	var roleApi=layui.roleApi;
	var view ={
		
		init:function(roleId){
			this.initRoles();
			
		},
		
		initRoles:function(){
			var that = this;
			form.on('radio(roleId)', function(data){
				 checkedRoleId = data.value; //被点击的radio的value值
				 that.loadDataAccessByRole(checkedRoleId);
			})
			this.initFirstRole();
			
		},
		initFirstRole:function(){
			var firstRole=$(".layui-form").find(":radio")[0]
			checkedRoleId= $(firstRole).val();
			this.loadDataAccessByRole(checkedRoleId);
			$(firstRole).prop("checked",true);
			
		},
		
		loadDataAccessByRole:function(roleId){
			
			 $("#dataAccess").load(Common.ctxPath+"/admin/role/function/dataAccess.do?roleId="+roleId,{},function(){
				 form.render();
				 form.on('select', function(data){
					  var dom = data.elem;
					  var fnId = $(dom).data("id");
					  var accessType= data.value;
					  roleApi.saveDataAccess(checkedRoleId,fnId,accessType)
					  
				 });
			 });
			
		},
		
		
		
	}
	
	
	
	

	 exports('roleData',view);
	
});