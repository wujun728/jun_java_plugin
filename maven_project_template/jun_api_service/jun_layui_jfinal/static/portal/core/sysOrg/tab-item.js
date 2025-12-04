var	EditTable =function(){
	this.config={
		 tableId:"tab_item_1",
		 addBtnId:"add_btn_1",
		 saveBtnId:"save_btn_1",
		 delBtnId:"del_btn_1",
		 delEvent:"del_btn_1",
		 toolbar:"table_toolbar_1",
		 rowbar:"table_rowbar_1",
		 //数据主键
		 dataId:"id",
		 //表格查询接口
		 queryUrl:null,
		 params:{},
		 //表格保存接口
		 saveUrl:null,
		 //删除接口
		 deleteUrl:null,
		 /*添加表格行数据*/
		 rowData:{}
	}
}

EditTable.prototype.init=function(){
	layui.extend({
		tableEdit: '/static/libs/ext/tableEdit',
	});
}

EditTable.prototype.render=function(config,options){
	layui.use(['table','tableEdit','layer','form'], function () {
 	        var table = layui.table,tableEdit = layui.tableEdit,$ = layui.$,form=layui.form; 	        
 	        var allTableData = {oldData:[]}; // 存储原来从表的数据
	 	  	// 获取默认数据
	 	  	getTableData(table,allTableData,config);
	 	  	// 渲染可编辑表格
	 	  	var cols=renderEditTable(table,config,options).config.cols;
	 	  	/**
	 	  	 * 参数cols是table.render({})中的cols属性值
	 	  	 * aop代理是基于event点击事件进行操作的，
	 	  	 * 因此cols中务必开启event点击事件！
	 	  	 **/
	 	  	var aopTable = tableEdit.aopObj(cols); //获取一个aop对象
	 	  	
	 	  	//tableEdit监听事件
	 	  	tableEditOnClickBefore(tableEdit,config);
	 	  	
	 	  	// 删除可编辑表格的数据
	 	    monitorTableOperation(table,aopTable,config);
	 	     	     
 	    });
}
 	
 	//查询初始化表格数据
 	function getTableData(table,allTableData,config){
 		$.ajax({
 			url: config.queryUrl,
 		    type:"post", 
 		    data: config.params,
 		    success:function(res){
 			    allTableData.oldData = res.list;
 			    table.reload(config.tableId,{
 					data:allTableData.oldData  
 				});
 		    }	
 		});
 	}
	
 	// 渲染可编辑表格
 	function renderEditTable(table,config,options){
 		var tableOptions = $.extend({
 			elem: '#'+config.tableId
 			,id:config.tableId
 	        ,data:[]
 	        ,height: 'full-100'
	        ,toolbar:'#'+config.toolbar
	        ,page : false
 	        ,limit:100 // 每页条数，这里不能产生分页，因为分页后layui.table.cache["edit-table-toolbar"]无法获取所有数据
			,done: function (res, curr, count) {
 				var editTableData = res.data;

 				// 添加一行
 				var rowData=clone(config.rowData);
 				addRow(config, editTableData,rowData);
 				
 				// 保存数据
 				saveData(config,editTableData);
 				
 				// 删除多行
 				deleteRows(config,editTableData);
 	        }
		}, options);
 		
 		return table.render(tableOptions);
 	}
 	 	 	
 	// 监听单元格编辑
 	function monitorTableOperation(table,aopTable,config){
 		/**
         * 注意：
         * 1、 aopTable.on('tool(xxx)',function (obj) {})
         * 2、 table.on('tool(yyy)',function (obj) {})
         * 如果1中的xxx与2中的yyy字符串相同时，
         * 不能同时用，用了会造成后调用者覆盖前调用者。
         * 应该直接用1来代替2，因为1中包含了2中的事件。
         * 如果不相同，则可以同时使用。
         **/
        aopTable.on('tool('+config.tableId+')',function (obj) {debugger
        	//1、修改监听
            var field = obj.field; //单元格字段
            var value = obj.value; //修改后的值
            var data = obj.data; //当前行旧数据
            var event = obj.event; //当前单元格事件属性值
            var update = {};
            update[field] = value;
            //把value更新到行中
            obj.update(update);

       
            //2、删除监听
 	    	// 当前操作的数据的索引
 	    	var nowIndex = parseInt(getQuotationVal(obj.tr.selector)[0]);
 	    	if(obj.event === config.delEvent){
 	        	var confirmIndex = layer.confirm('您确定要删除吗?',{btn: ['确定', '取消'],title:"提示"}, function(){ 	        		
 		       		var editTableData = layui.table.cache[config.tableId];
 		       		var id=editTableData[nowIndex][config.dataId];
 		       		if(id){
 		       			deleteData(config.deleteUrl,[id]);	       			
 		       		}
 		       		editTableData=layui.table.cache[config.tableId].deleteOne(nowIndex);
 			 		table.reload(config.tableId,{data:editTableData}); // 重新渲染表格
 			 		layer.close(confirmIndex); // 关闭弹窗
 				});
 	        }
 	    });
 	}
 	
 	//删除数据
 	function deleteData(deleteUrl,ids){
 		if(deleteUrl&&ids.length>0){
 			// 向服务端发送删除指令
 			jQuery.post(deleteUrl, param({
 				'ids' : ids
 			}), function(feedback) {
 				if (feedback.success) {
 					layer.msg(feedback.msg||"删除成功");
 				} else {
 					layer.alert(feedback.msg||"删除失败",{icon: 2});
 				}
 			});		
 		}
 	}
 	
 	//提交数据
 	function submitData(url,tableTata){
		$.ajax({
			url: url,  
		    type:"post", 
		    data: tableTata,
		    success:function(res){
		    	layer.msg(res.msg)    
		    }	
		});
 	}
 	
 	//tableEdit点击前事件监听
 	function tableEditOnClickBefore(tableEdit,config){
 		/**
         * 级联下拉框 => 点击事件生成下拉框之前的回调函数
         * 主要用于 => 动态获取单元格下拉数据
         * tableEvent => table的lay-filter属性值
         * 如示例代码所示
         */
        tableEdit.on('clickBefore('+config.tableId+')',function (obj) {
            var cascadeSelectData = obj.data; //级联数据
            var cascadeSelectField = obj.field; //级联字段
            if(Array.isArray(cascadeSelectData)){
                cascadeSelectData = cascadeSelectData[0];
            }
            var result = {
                data:[{value:1,name:'语文'},{value:2,name:'数学'},{value:3,name:'英语'},{value:4,name:'物理'},{value:5,name:'化学'}],
                enabled:false //单选 true为多选
            };
            var result1 = {
                data:[{value:6,name:'政治'},{value:7,name:'地理'},{value:8,name:'历史'},{value:9,name:'生物'},{value:10,name:'音乐'}],
                enabled:false //单选 true为多选
            };
            //这里用定时器来模拟异步操作，同步操作直接return即可。
            setTimeout(function () {
                if(cascadeSelectField === 'supSelect'){
                    tableEdit.callbackFn("async("+config.tableId+")",result1);
                }else {
                    tableEdit.callbackFn("async("+config.tableId+")",result);
                }
            },50);
        });
 	}
 	
 	
 // 给数组添加一个方法，delete方法接受一个参数，根据参数删除数组的一个元素，返回一个删除对应索引元素的新数组，不改变原有数组
 	Array.prototype.deleteOne=function(delIndex){
 		var temArray=[];
 		for(var i=0;i<this.length;i++){
 			if(i!=delIndex){
 	    		temArray.push(this[i]);
 			}
 		}
 		return temArray;
 	}
 	
 	// 获取双引号""中的值
 	function getQuotationVal(str) {
 	    var result = str.match(/\"\w*\"/g);
 	    return result.map(function(element){
 	        return element.replace(/\"/g, '');
 	    });
 	}
 	
 	//添加行
 	function addRow(config,editTableData,rowData){
 		$("#"+config.addBtnId).unbind("click");
		$("#"+config.addBtnId).click(function (){
			editTableData.push(rowData);
			layui.table.reload(config.tableId,{
			    data:editTableData   // 将新数据重新载入表格
			});
		});	
 	}
 	
 	// 保存数据
 	function saveData(config,editTableData){
		$("#"+config.saveBtnId).unbind("click");
		$("#"+config.saveBtnId).click(function (){
			if(editTableData.length>0)
			submitData(config.saveUrl,{"tableList":JSON.stringify(editTableData)});
		});	
 	}
 	
 	// 删除多行
 	function deleteRows(config,editTableData){
		$("#"+config.delBtnId).unbind("click");
		$("#"+config.delBtnId).click(function (){
			var checkStatus = layui.table.checkStatus(config.tableId);
			if(checkStatus.data.length>0){
				var confirmIndex = layer.confirm('您确定要删除吗?',{btn: ['确定', '取消'],title:"提示"}, function(){
					var ids = [];
				var newTableData=new Array();
				$.each(editTableData,function(i,obj){
					var checked=obj["LAY_CHECKED"];
					var id=obj[config.dataId];
					if(checked==true){
						if(id){
							ids.push(id);
						}
					}else{
						newTableData.push(obj);
					}
				});
				
				// 向服务端发送删除指令
				if(ids.length>0)
					deleteData(config.deleteUrl,ids);
				editTableData=newTableData;
		 		layui.table.reload(config.tableId,{data:editTableData}); // 重新渲染表格
 			 		layer.close(confirmIndex); // 关闭弹窗
 				});
			}else{
				layer.alert('请选择行',{icon: 7});
			}
		});
 	}
 	
 	//克隆对象
 	function clone(obj){
 		var buf;
 		if(obj instanceof Array){
 			buf=[];
 			var i=obj.length;
 			while(i--){
 				buf=clone(obj[i]);
 			}
 			return buf;
 		}
 		else if(obj instanceof Object){
 			buf={};
 			for(var key in obj){
 				buf[key]=clone(obj[key]);
 			}
 			return buf;
 		}else{
 			return obj;
 		}
 	}
 	
 	var editTable=new EditTable();
 	editTable.init();
 	