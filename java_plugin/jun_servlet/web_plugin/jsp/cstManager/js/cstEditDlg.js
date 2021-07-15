
	var $dg;
	var $tree;
	$(function() {
		$("#tt").tabs({
			border:false,
			width:'auto',
			height:$(this).height()-120
		});
		$("#saleId").combotree({
			width:171,
			url:"cstController.do?method=findSaleNameList",
			idFiled:'id',
		 	textFiled:'name',
		 	parentField:'pid',
		 	onBeforeSelect:function(node){
		 		if(node.attributes.status=="o"){
		 			return false;
		 		}else{
		 			$("#saleName").val(node.text);
		 		}
		 	}
		});
		
		$("#cityId").combotree({
			width:171,
			url:"areaController.do?method=findCities",
			idFiled:'id',
		 	textFiled:'name',
		 	parentField:'pid',
		 	onBeforeSelect:function(node){
		 		if(node.attributes.status=="p"){
		 			return false;
		 		}else{
		 			$("#cityName").val(node.text);
		 		}
		 	}
		});
		var typedata=[{"type":"M","typeName":"男"},{"type":"F","typeName":"女"}];
		 $dg = $("#dg");
		 $grid=$dg.datagrid({
			url : "cstContactController.do?method=findCustomerContactList&customerId="+$("#tempId").val(),
			width : 'auto',
			height : $(this).height()-120,
			rownumbers:true,
			border:false,
			singleSelect:true,
			columns : [ [ {field : 'name',title : '联系人名称',width : parseInt($(this).width()*0.07),editor : {type:'validatebox',options:{required:true}}},
			              {field : 'sexName',title : '性别',width : parseInt($(this).width()*0.03), 
							formatter:function(value,row){
		            		  if("F"==row.sexName)
									return "<font color=red>女<font>";
			            		  else
			            			return "男";  
							},
							editor:{
								type:'combobox',
								options:{
									valueField:'type',
									textField:'typeName',
									data:typedata,
									required:true
								}
							}},
			              {field : 'duty',title : '职务',width : parseInt($(this).width()*0.07),editor : "text"},
			              {field : 'tel',title : '电话',width : parseInt($(this).width()*0.05),editor : "validatebox"},
			              {field : 'email',title : '电子邮箱',width : parseInt($(this).width()*0.09),align : 'left',editor:{
			          		type:'validatebox',
			        		options:{
			        			validType:'email'
			        		}
			        		}},
			              {field : 'qq',title : 'QQ',width : parseInt($(this).width()*0.07),align : 'left',editor : "text"},
			              {field : 'birthday',title : '生日',width : parseInt($(this).width()*0.05),align : 'left',editor : "datebox"},
			              {field : 'description',title : '备注',width : parseInt($(this).width()*0.08),align : 'left',editor : "text"}
			              ] ],toolbar:'#tb'
		});
		
		$("#classId").combobox({
			width:171,
			url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=customerClass",
			valueField: 'codeId',
			textField: 'name',
			onSelect:function(value){
				$("#className").val(value.name);
			}
		});
		
		$("#levelId").combobox({
			width:171,
			url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=customerLevel",
			valueField: 'codeId',
			textField: 'name',
			onSelect:function(value){
				$("#levelName").val(value.name);
			}
		});
		
		$("#industryId").combobox({
			width:171,
			url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=industry",
			valueField: 'codeId',
			textField: 'name',
			onSelect:function(value){
				$("#industryName").val(value.name);
			}
		});
		
		$("#sizeId").combobox({
			width:171,
			url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=companySize",
			valueField: 'codeId',
			textField: 'name',
			onSelect:function(value){
				$("#sizeName").val(value.name);
			}
		});
		
		$("#creditId").combobox({
			width:171,
			url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=credit",
			valueField: 'codeId',
			textField: 'name',
			onSelect:function(value){
				$("#creditName").val(value.name);
			}
		});
		
		$("#natureId").combobox({
			width:171,
			url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=customerNature",
			valueField: 'codeId',
			textField: 'name',
			onSelect:function(value){
				$("#natureName").val(value.name);
			}
		});
		
		$("#currencyId").combobox({
			width:171,
			url:"currencyController.do?method=findCurrencyList",
			valueField: 'id',
			textField: 'name',
			onSelect:function(value){
				$("#currencyName").val(value.name);
			}
		});
		
		$("#form").form({
			url :"cstController.do?method=persistenceCustomer",
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.status) {
					parent.reload;
					parent.$.modalDialog.openner.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_datagrid这个对象，是因为页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
					parent.$.messager.show({
						title : result.title,
						msg : result.message,
						timeout : 1000 * 2
					});
				}else{
					parent.$.messager.show({
						title :  result.title,
						msg : result.message,
						timeout : 1000 * 2
					});
				}
			}
		});
	});
	function endEdit(){
		var flag=true;
		var rows = $dg.datagrid('getRows');
		for ( var i = 0; i < rows.length; i++) {
			$dg.datagrid('endEdit', i);
			var temp=$dg.datagrid('validateRow', i);
			if(!temp){flag=false;}
		}
		return flag;
	}
	function addRows(){
		$dg.datagrid('appendRow', {});
		var rows = $dg.datagrid('getRows');
		$dg.datagrid('beginEdit', rows.length - 1);
	}
	function editRows(){
		var rows = $dg.datagrid('getSelections');
		$.each(rows,function(i,row){
			if (row) {
				var rowIndex = $dg.datagrid('getRowIndex', row);
				$dg.datagrid('beginEdit', rowIndex);
			}
		});
	}
	function removeRows(){
		var rows = $dg.datagrid('getSelections');
		$.each(rows,function(i,row){
			if (row) {
				var rowIndex = $dg.datagrid('getRowIndex', row);
				$dg.datagrid('deleteRow', rowIndex);
			}
		});
	}
	function saveRows(){
		if(endEdit()){
			if ($dg.datagrid('getChanges').length) {
				var inserted = $dg.datagrid('getChanges', "inserted");
				var deleted = $dg.datagrid('getChanges', "deleted");
				var updated = $dg.datagrid('getChanges', "updated");
				
				var effectRow = new Object();
				if (inserted.length) {
					effectRow["inserted"] = JSON.stringify(inserted);
				}
				if (deleted.length) {
					effectRow["deleted"] = JSON.stringify(deleted);
				}
				if (updated.length) {
					effectRow["updated"] = JSON.stringify(updated);
				}
				$("#inserted").val(effectRow["inserted"]);
				$("#deleted").val(effectRow["deleted"]);
				$("#updated").val(effectRow["updated"]);
				/*$.post("companyInfo/companyInfoAction!persistenceCompanyInfo.action", effectRow, function(rsp) {
					if(rsp.status){
						$dg.datagrid('acceptChanges');
					}
					$.messager.alert(rsp.title, rsp.message);
				}, "JSON").error(function() {
					$.messager.alert("提示", "提交错误了！");
				});*/
			}
		}else{
			$.messager.alert("提示", "字段验证未通过!请查看");
		}
	}
	//弹窗增加区域
	function addAreaOpenDlg() {
		$("<div/>").dialog({
			href : "jsp/cstManager/areaEditDlg.jsp",
			width : 600,
			height : 400,
			modal : true,
			title : '添加区域',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$("#form1").form({
						url : "areaController.do?method=addCities",
						onSubmit : function() {
							parent.parent.$.messager.progress({
								title : '提示',
								text : '数据处理中，请稍后....'
							});
							var isValid = $(this).form('validate');
							if (!isValid) {
								parent.parent.$.messager.progress('close');
							}
							return isValid;
						},
						success : function(result) {
							try {
								parent.$.messager.progress('close');
								result = $.parseJSON(result);
								if (result.status) {
									$("#cityId").combotree("reload");
									d.dialog('destroy');
									parent.parent.$.messager.show({
										title : result.title,
										msg : result.message,
										timeout : 1000 * 2
									});
								}else{
									parent.parent.$.messager.show({
										title :  result.title,
										msg : result.message,
										timeout : 1000 * 2
									});
								}
							} catch (e) {
								$.messager.alert('提示', result);
							}
						}
					});
					$("#form1").submit();
				}
			},{
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function() {
					 $(this).closest('.window-body').dialog('destroy');
				}
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
