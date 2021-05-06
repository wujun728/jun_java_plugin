
			var $dg;
			var $grid;
			$(function() {
				 $dg = $("#dg");
				 $grid=$dg.datagrid({
					url : "cstController.do?method=findCustomerList",
					width : 'auto',
					height : $(this).height()-85,
					pagination:true,
					rownumbers:true,
					border:true,
					striped:true,
					singleSelect:true,
					columns : [ [ {field : 'name',title : '客户名称',width : parseInt($(this).width()*0.1)},
					              {field : 'myid',title : '客户编码',width : parseInt($(this).width()*0.1)},
					              {field : 'customerStatus',title : '客户状态',width : parseInt($(this).width()*0.1), 
					            	  formatter:function(value,row){
				            		  if("T"==row.customerStatus)
											return "<font color=green>交易中<font>";
					            		  else
					            			return "<font color=red>禁用<font>";  
									}},
					              {field : 'tel',title : '电话',width : parseInt($(this).width()*0.1),align : 'left'},
					              {hidden : 'cityId',title : '城市',width : parseInt($(this).width()*0.1),align : 'left',
					            	  formatter:function(value,row){
					            			return "0"+row.cityId;  
									}},
					              {hidden : 'saleId',title : '销售',width : parseInt($(this).width()*0.1),align : 'left',
										formatter:function(value,row){
					            			return "0"+row.saleId;  
									}},
					              {field : 'fax',title : '传真',width :parseInt($(this).width()*0.1),align : 'left'},
					              {field : 'email',title : '邮箱',width : parseInt($(this).width()*0.1),align : 'left'},
					              {field : 'address',title : '地址',width : parseInt($(this).width()*0.3),align : 'left'}
					              ] ],toolbar:'#tb'
				});
				//搜索框
				$("#searchbox").searchbox({ 
					 menu:"#mm", 
					 prompt :'模糊查询',
				    searcher:function(value,name){   
				    	var str="{\"searchName\":\""+name+"\",\"searchValue\":\""+value+"\"}";
			            var obj = eval('('+str+')');
			            $dg.datagrid('reload',obj); 
				    }
				   
				}); 
			});
		
			//删除
			function delRows(){
				var row = $dg.datagrid('getSelected');
				if(row){
					var rowIndex = $dg.datagrid('getRowIndex', row);
					parent.$.messager.confirm("提示","确定要删除记录吗?",function(r){  
					    if (r){  
					    	$dg.datagrid('deleteRow', rowIndex);
					    	$.ajax({
								url:"cstController.do?method=delCustomer",
								data: "customerId="+row.customerId,
								success: function(rsp){
									parent.$.messager.show({
										title : rsp.title,
										msg : rsp.message,
										timeout : 1000 * 2
									});
								}
							});
					    }  
					});
				}else{
					parent.$.messager.show({
						title : "提示",
						msg :"请选择一行记录!",
						timeout : 1000 * 2
					});
				}
			}
			//弹窗修改
			function updRowsOpenDlg() {
				var row = $dg.datagrid('getSelected');
				if (row) {
					parent.$.modalDialog({
						title : '编辑客户',
						width : 900,
						height :550,
						href : "jsp/cstManager/cstEditDlg.jsp?tempId="+row.customerId,
						onLoad:function(){
							var f = parent.$.modalDialog.handler.find("#form");
							row.saleId=(typeof(row.saleId)=="undefined")?row.saleId:"0"+row.saleId;
							row.cityId=(typeof(row.cityId)=="undefined")?row.cityId:"0"+row.cityId;
							f.form("load", row);
						},			
						buttons : [ {
							text : '编辑',
							iconCls : 'icon-ok',
							handler : function() {
								parent.$.modalDialog.openner= $grid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
								var f = parent.$.modalDialog.handler.find("#form");
								f.submit();
							}
						}, {
							text : '取消',
							iconCls : 'icon-cancel',
							handler : function() {
								parent.$.modalDialog.handler.dialog('destroy');
								parent.$.modalDialog.handler = undefined;
							}
						}
						]
					});
				}else{
					parent.$.messager.show({
						title :"提示",
						msg :"请选择一行记录!",
						timeout : 1000 * 2
					});
				}
			}
			//弹窗增加
			function addRowsOpenDlg() {
				parent.$.modalDialog({
					title : '添加客户',
					width : 900,
					height :550,
					href : "jsp/cstManager/cstEditDlg.jsp",
					buttons : [ {
						text : '保存',
						iconCls : 'icon-ok',
						handler : function() {
							parent.$.modalDialog.openner= $grid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
							var f = parent.$.modalDialog.handler.find("#form");
							f.submit();
						}
					}, {
						text : '取消',
						iconCls : 'icon-cancel',
						handler : function() {
							parent.$.modalDialog.handler.dialog('destroy');
							parent.$.modalDialog.handler = undefined;
						}
					}
					]
				});
			}
			
			//高级搜索 删除 row
			function tbCompanySearchRemove(curr) {
					$(curr).closest('tr').remove();
			}
			//高级查询
			function tbsCompanySearch() {
				jqueryUtil.gradeSearch($dg,"#tbCompanySearchFm","jsp/company/companySearchDlg.jsp");
			}
		