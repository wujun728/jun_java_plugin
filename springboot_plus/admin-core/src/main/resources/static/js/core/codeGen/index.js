layui.define([ 'form', 'laydate', 'table','codeApi' ], function(exports) {
	var form = layui.form;
	var laydate = layui.laydate;
	var table = layui.table;
	var codeApi = layui.codeApi;
	var codeTable = null;
	var view ={
		
		init:function(){
			this.initTable();
			this.initToolBar();
		},
		initTable:function(){
			codeTable = table.render({
				elem : '#codeTable',
				height : Lib.getTableHeight(1),
				method : 'post',
				url : Common.ctxPath + '/core/codeGen/table.json' //数据接口
				,page : false
				,limit : 10000,
				cols : [ [
					
					{
						type : 'checkbox',
						fixed:'left',
					}, 
					{
						field : 'tableName',
						title : '表名称',
						width : 400,
						sort : true
						
					}, 
					{
						field : 'name',
						title : '类名',
						width : 400,
						sort : true
					}
					]  ]

			});
		},
		initToolBar:function(){
			toolbar = {
					edit : function() { //获取选中数目
						var data = Common.getOneFromTable(table,"codeTable");
						if(data==null){
							return ;
						}
						var url = "/core/codeGen/tableDetail.do?table="+data.tableName;
						Common.openDlg(url,"代码生成>"+data.tableName+">代码生成");
						
					},
					refresh:function(){
						codeApi.refresh(function(){
							codeTable.reload();
						})
						
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