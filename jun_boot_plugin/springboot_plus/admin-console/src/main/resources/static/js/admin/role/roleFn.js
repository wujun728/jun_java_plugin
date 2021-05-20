layui.define([ 'form', 'laydate', 'table','roleApi'], function(exports) {
	var form = layui.form;
	var laydate = layui.laydate;
	var table = layui.table;
	var zTreeObj = null;
	var roleApi = layui.roleApi;
	var checkedRoleId=null;
	var view ={
		
		init:function(roleId){
			this.initFunctionTree();
			this.initSaveFunction();
			
		},
		initFunctionTree:function(){
			var that = this;
			$.post(Common.ctxPath + "/admin/function/tree.json", {}, function(response) {
				var zNodes = response.data;
				// select parent node does not affect the children node
				var setting  = {"check":{"chkStyle":"checkbox","enable":true, "chkboxType": { "Y": "p", "N": "p" }}};
				zTreeObj = $.fn.zTree.init($("#functionTree"), setting, zNodes);
				//功能树加载完毕后在初始化其他控件
				that.initFirstRole();
				that.initRoles();
			
			})
		},
		initSaveFunction:function(){
			
			$("#save").click(function(){
				var nodes = zTreeObj.getCheckedNodes(true);
				var ids = Common.concatBatchId(nodes,"id")
				roleApi.saveFunctions(checkedRoleId,ids)
				
			});
		},
		initRoles:function(){
			var that = this;
			form.on('radio(roleId)', function(data){
				 checkedRoleId = data.value; //被点击的radio的value值
				 that.loadFunctionByRole(checkedRoleId);
			})
			
		},
		initFirstRole:function(){
			var firstRole=$(".layui-form").find(":radio")[0]
			checkedRoleId= $(firstRole).val();
			this.loadFunctionByRole(checkedRoleId);
			$(firstRole).prop("checked",true);
			form.render();
			
		},
		loadFunctionByRole:function(roleId){
//			debugger;
			var nodes = zTreeObj.getCheckedNodes(true);
			for(var i=0;i<nodes.length;i++){
				zTreeObj.checkNode(nodes[i], false, true);
			}
			roleApi.queryFunctionByRole(roleId,function(fnIds){
				for(var i=0;i<fnIds.length;i++){
					var node = zTreeObj.getNodesByParam("id",fnIds[i]);
					zTreeObj.checkNode(node[0], true, true);
				}
			});
		}
		
	}
	
	
	
	

	 exports('roleFn',view);
	
});