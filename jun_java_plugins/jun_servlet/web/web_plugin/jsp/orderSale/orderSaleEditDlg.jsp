<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript">
	var $dg;
	var $cb;
	$(function() {
		 $dg = $("#dg");
		 $dg.datagrid({
			 url :"orderSaleController.do?method=findOrderSaleLineList&orderSaleId="+$("#tempId").val(),
			width : 'auto',
			height : $(this).height()-300,
			rownumbers:true,
			border:false,
			singleSelect:true,
			columns : [ [ {field : 'myid',title : '物料编码',width : parseInt($(this).width()*0.07),editor : {type:'validatebox',options:{required:true}}},
			              {field : 'itemName',title : '物料名称',width : parseInt($(this).width()*0.2),editor :"text"},
			              {field : 'unit',title : '单位',width : parseInt($(this).width()*0.07),editor :"text"},
			              {field : 'orderQty',title : '数量',width : parseInt($(this).width()*0.05),editor : {
			            	  type:'numberbox',
			            	  options:{
			            		  value:0,
			            		  min:0,
			            		  editable:true
			            	  }
			              }},
			              {field : 'price',title : '单价(或最近成交价)',width : parseInt($(this).width()*0.09),align : 'left',editor : {
			            	  type:'numberbox',
			            	  options:{
			            		  value:0,
			            		  min:0,
			            		  prefix:"￥",
			            		  precision:2
			            	  }
			              }},
			              {field : 'discountNo',title : '折扣',width : parseInt($(this).width()*0.07),align : 'left',editor : {
			            	  type:'numberbox',
			            	  options:{
			            		  precision:2,
			            		  value:0,
			            		  min:0,
			            		  max:1
			            	  }
			              }},
			              {field : 'amount',title : '金额',width : parseInt($(this).width()*0.08),align : 'left',
			            	  /*formatter:function(value,row){
			            		  if(row.discountNo==0)
			            			  return parseFloat((row.price*row.orderQty).toFixed(2));
			            		  else
			            			  return parseFloat((row.price*row.orderQty*(parseFloat(row.discountNo))).toFixed(2));  
							},*/
							editor : {
				            	  type:'numberbox',
				            	  options:{
				            		  precision:2,
				            		  value:0,
				            		  min:0
				            	  }
				              }},
			              {field : 'taxAmount',title : '税金',width : parseInt($(this).width()*0.08),align : 'left',
								/*formatter:function(value,row){
								   if(row.discountNo==0)
			            			  return parseFloat((row.price*row.orderQty*(parseFloat(row.taxNo*0.01))).toFixed(2));  
								   else
									  return parseFloat((parseFloat(row.discountNo)*row.price*row.orderQty*(parseFloat(row.taxNo*0.01))).toFixed(2));   
							} ,*/
				             editor : {
				            	  type:'numberbox',
				            	  options:{
				            		  precision:2,
				            		  value:0,
				            		  min:0
				            	  }
				              }},
			              {hidden:true,field : 'itemId',title : '物料ID',width : parseInt($(this).width()*0.1),align : 'left',editor : "numberbox"},
			              {hidden:true,field : 'taxNo',title : '税率',width : parseInt($(this).width()*0.1),align : 'left',editor : "numberbox"}
			              ] ],toolbar:'#tb'
		});
		 
		 $cb = $("#customerId");
		 $cb.combogrid({
			url : "cstController.do?method=findCustomerListNoPage",
			width : 171,
			panelWidth: 400,
			panelHeight: 300,
			rownumbers:true,
			border:false,
			singleSelect:true,
			idField: 'customerId',
			textField: 'myid',
			columns : [ [ {field : 'myid',title : '客户编码',width : 150},
			              {field : 'name',title : '客户名称',width : 150}
			              ]],toolbar:'#tb2',onSelect:function(rowIndex,rowData){
			            	  $("#customerMyid").val(rowData.myid);
			            	  $("#customerName").val(rowData.name);
			            	  $("#customerContact").combobox({
			          			width:171,
			        			url:"cstContactController.do?method=findCustomerContactListCombobox&customerId="+rowData.customerId,
			        			valueField: 'name',
			        			textField: 'name',
			        			onSelect:function(value){
			        				$("#customerTel").val(value.tel);
			        			}
			        		});
			            	  //$("#currencyId").combobox("setValue",rowData.currencyId);
			            	  //$("#currencyName").val(rowData.currencyName);
			              }
		});
		//搜索框
			$("#searchbox").searchbox({ 
				 menu:"#mm", 
				 prompt :'模糊查询',
			    searcher:function(value,name){   
			    	var str="{\"searchName\":\""+name+"\",\"searchValue\":\""+value+"\"}";
		            var obj = eval('('+str+')');
		            var $g = $cb.combogrid("grid");
		            $g.datagrid('reload',obj); 
			    }
			   
			}); 
		$("#classId").combobox({
			width:171,
			url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=purchaseClass",
			valueField: 'codeId',
			textField: 'name',
			onSelect:function(value){
				$("#className").val(value.name);
			}
		});
		
		$("#deliveryMode").combobox({
			width:171,
			url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=deliveryMode",
			valueField: 'codeId',
			textField: 'name',
			onSelect:function(value){
				$("#deliveryModeName").val(value.name);
			}
		});
		
		$("#projectId").combobox({
			width:171,
			url:"projectController.do?method=findProjectListCombobox",
			valueField: 'projectId',
			textField: 'name',
			onSelect:function(value){
				$("#projectName").val(value.name);
			}
		});
		
		$("#warehouseId").combobox({
			width:171,
			url:"warehouseController.do?method=findWarehouseListCombobox",
			valueField: 'warehouseId',
			textField: 'name',
			onSelect:function(value){
				$("#warehouseName").val(value.name);
			}
		});
		
		$("#buyerOrganizationId").combotree({
			width:171,
			url:"orderSaleController.do?method=findOrganizationList",
			idFiled:'id',
		 	textFiled:'name',
		 	parentField:'pid',
		 	onSelect:function(value){
		 		$("#buyerOrganizationName").val(value.text);
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
			url :"orderSaleController.do?method=persistenceOrderSale",
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form("validate");
				if (!isValid) {
					parent.$.messager.progress("close");
				}
				if(isValid&&saveRows()){
					$(document).unbind("keyDown");
					return true;
				}else{
					return false;
				}
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
		$(document).bind("keydown",function(event){
			var keyDown=$("#keyDown").val();
			if(keyDown=="keyDown"){
					switch(event.keyCode) { 
					case (38): 
						var index =$dg.datagrid("getRowIndex",$dg.datagrid("getSelected"));
						var temp=$dg.datagrid('validateRow', index);
						if(temp){
							if(index>0){
								$dg.datagrid('selectRow', index-1);
								$dg.datagrid('beginEdit', index-1);
								var ed = $dg.datagrid('getEditor', {index:index-1,field:'myid'});
								$(ed.target).focus();
								var editemName = $dg.datagrid('getEditor', {index:index-1,field:'itemName'});
								$(editemName.target).attr('disabled',true);
								var edunit = $dg.datagrid('getEditor', {index:index-1,field:'unit'});
								$(edunit.target).attr('disabled',true);
								var edamount = $dg.datagrid('getEditor', {index:index-1,field:'amount'});
								$(edamount.target).attr('disabled',true);
								var edtaxAmount = $dg.datagrid('getEditor', {index:index-1,field:'taxAmount'});
								$(edtaxAmount.target).attr('disabled',true);
								
								$dg.datagrid('endEdit', index);
							}
						}
						return false ;
						break ;
					case (40):
						var rows = $dg.datagrid('getRows');
					    var index =$dg.datagrid("getRowIndex",$dg.datagrid("getSelected"));
					    var length=rows.length;
					    if(index==(length-1)){
								addRows();
					    }else{
						    	$dg.datagrid('selectRow', index+1);
								$dg.datagrid('beginEdit', index+1);
								var ed = $dg.datagrid('getEditor', {index:index+1,field:'myid'});
								$(ed.target).focus();
								var editemName = $dg.datagrid('getEditor', {index:index+1,field:'itemName'});
								$(editemName.target).attr('disabled',true);
								var edunit = $dg.datagrid('getEditor', {index:index+1,field:'unit'});
								$(edunit.target).attr('disabled',true);
								var edamount = $dg.datagrid('getEditor', {index:index+1,field:'amount'});
								$(edamount.target).attr('disabled',true);
								var edtaxAmount = $dg.datagrid('getEditor', {index:index+1,field:'taxAmount'});
								$(edtaxAmount.target).attr('disabled',true);
							
							
							var edorderQty = $dg.datagrid('getEditor', {index:index,field:'orderQty'});
							if(edorderQty){
							var edorderQtyValue=$(edorderQty.target).numberbox("getValue");
							var edprice = $dg.datagrid('getEditor', {index:index,field:'price'});
							var edpriceValue=$(edprice.target).numberbox("getValue");
							var eddiscountNo = $dg.datagrid('getEditor', {index:index,field:'discountNo'});
							var eddiscountNoValue=$(eddiscountNo.target).numberbox("getValue");
							var edtaxNo = $dg.datagrid('getEditor', {index:index,field:'taxNo'});
							var edtaxNoValue=$(edtaxNo.target).numberbox("getValue");
							if(eddiscountNoValue==0){
								 var v1=parseFloat((edpriceValue*edorderQtyValue).toFixed(2));
								 var v2=parseFloat((edpriceValue*edorderQtyValue*(parseFloat(edtaxNoValue*0.01))).toFixed(2)); 
								 amount+= v1;
								 taxAmount+=v2;
								 var edamount = $dg.datagrid('getEditor', {index:index,field:'amount'});
								 $(edamount.target).numberbox("setValue", v1);
								 var edtaxAmount = $dg.datagrid('getEditor', {index:index,field:'taxAmount'});
								 $(edtaxAmount.target).numberbox("setValue", v2);
							 }else{
								 var v3=parseFloat((edpriceValue*edorderQtyValue*(parseFloat(eddiscountNoValue))).toFixed(2));
								 var v4=parseFloat((parseFloat(eddiscountNoValue)*edpriceValue*edorderQtyValue*(parseFloat(edtaxNoValue*0.01))).toFixed(2));
								 amount+= v3;
								 taxAmount+=v4;
								 var edamount = $dg.datagrid('getEditor', {index:index,field:'amount'});
								 $(edamount.target).numberbox("setValue", v3);
								 var edtaxAmount = $dg.datagrid('getEditor', {index:index,field:'taxAmount'});
								 $(edtaxAmount.target).numberbox("setValue", v4);
							  }
							}
							$dg.datagrid('endEdit', index);
					    }
						return false;
						break; 
					case (13):
						//addRows();
							var customerId=$cb.combogrid("getValue");
							var index =$dg.datagrid("getRowIndex",$dg.datagrid("getSelected"));
							var ed = $dg.datagrid('getEditor', {index:index,field:'myid'});
							var myid=$(ed.target).val();
							$.ajax({
								url:"itemController.do?method=findItemByMyid",
								type:"POST",
								data:"myid="+myid+"&suplierId="+customerId,
								success:function(res){
									if(res){
										var editemName = $dg.datagrid('getEditor', {index:index,field:'itemName'});
										$(editemName.target).val(res.name);
										var edunit = $dg.datagrid('getEditor', {index:index,field:'unit'});
										$(edunit.target).val(res.unit);
										
										var edorderQty = $dg.datagrid('getEditor', {index:index,field:'orderQty'});
										$(edorderQty.target).numberbox("setValue", 0);
										var edprice = $dg.datagrid('getEditor', {index:index,field:'price'});
										$(edprice.target).numberbox("setValue", res.cost);
										var eddiscountNo = $dg.datagrid('getEditor', {index:index,field:'discountNo'});
										$(eddiscountNo.target).numberbox("setValue", 0);
										var edamount = $dg.datagrid('getEditor', {index:index,field:'amount'});
										$(edamount.target).numberbox("setValue", 0.00);
										var edtaxAmount = $dg.datagrid('getEditor', {index:index,field:'taxAmount'});
										$(edtaxAmount.target).numberbox("setValue", 0.00);
										var editemId = $dg.datagrid('getEditor', {index:index,field:'itemId'});
										$(editemId.target).numberbox("setValue", res.itemId);
										var edtaxNo = $dg.datagrid('getEditor', {index:index,field:'taxNo'});
										$(edtaxNo.target).numberbox("setValue", res.taxNo);
										$(edorderQty.target).focus();
									}else{
										parent.$.messager.show({
											title :  "提示",
											msg : "编码："+myid+" 物料不存在!",
											timeout : 1000 * 2
										});
									}
									/*var ed = $dg.datagrid('getEditors', index);
									$.each(ed,function(i,e){
										//alert(e.field);
										if(i>0){
										 $(e.target).numberbox("setValue", 5);
										}
									});*/
								}
							});
						return false;
						break; 
					}
			}else{
				$(document).unbind("keyDown");
			}
	  });
	});
	//结束编辑
	function endEdit(){
		var amount=0;
		var taxAmount=0;
		var flag=true;
		var rows = $dg.datagrid('getRows');
		for ( var i = 0; i < rows.length; i++) {
			row=rows[i];
			var edorderQty = $dg.datagrid('getEditor', {index:i,field:'orderQty'});
			if(edorderQty){
				var edorderQtyValue=$(edorderQty.target).numberbox("getValue");
				var edprice = $dg.datagrid('getEditor', {index:i,field:'price'});
				var edpriceValue=$(edprice.target).numberbox("getValue");
				var eddiscountNo = $dg.datagrid('getEditor', {index:i,field:'discountNo'});
				var eddiscountNoValue=$(eddiscountNo.target).numberbox("getValue");
				var edtaxNo = $dg.datagrid('getEditor', {index:i,field:'taxNo'});
				var edtaxNoValue=$(edtaxNo.target).numberbox("getValue");
				if(eddiscountNoValue==0){
					 var v1=parseFloat((edpriceValue*edorderQtyValue).toFixed(2));
					 var v2=parseFloat((edpriceValue*edorderQtyValue*(parseFloat(edtaxNoValue*0.01))).toFixed(2)); 
					 //amount+= v1;
					// taxAmount+=v2;
					 var edamount = $dg.datagrid('getEditor', {index:i,field:'amount'});
					 $(edamount.target).numberbox("setValue", v1);
					 var edtaxAmount = $dg.datagrid('getEditor', {index:i,field:'taxAmount'});
					 $(edtaxAmount.target).numberbox("setValue", v2);
				 }else{
					 var v3=parseFloat((edpriceValue*edorderQtyValue*(parseFloat(eddiscountNoValue))).toFixed(2));
					 var v4=parseFloat((parseFloat(eddiscountNoValue)*edpriceValue*edorderQtyValue*(parseFloat(edtaxNoValue*0.01))).toFixed(2));
					 //amount+= v3;
					 //taxAmount+=v4;
					 var edamount = $dg.datagrid('getEditor', {index:i,field:'amount'});
					 $(edamount.target).numberbox("setValue", v3);
					 var edtaxAmount = $dg.datagrid('getEditor', {index:i,field:'taxAmount'});
					 $(edtaxAmount.target).numberbox("setValue", v4);
				  }
			}
			$dg.datagrid('endEdit', i);
			var temp=$dg.datagrid('validateRow', i);
			if(!temp){flag=false;}
			 if(row.discountNo==0){
				 amount+= parseFloat((row.price*row.orderQty).toFixed(2));
				 taxAmount+=parseFloat((row.price*row.orderQty*(parseFloat(row.taxNo*0.01))).toFixed(2));  
			 }else{
				 amount+= parseFloat((row.price*row.orderQty*(parseFloat(row.discountNo))).toFixed(2));  
				 taxAmount+=parseFloat((parseFloat(row.discountNo)*row.price*row.orderQty*(parseFloat(row.taxNo*0.01))).toFixed(2)); 
			  }
		}
		var totalAmount=amount+taxAmount;
		$("#amount").val(amount.toFixed(2));
		$("#taxAmount").val(taxAmount.toFixed(2));
		$("#totalAmount").val(totalAmount.toFixed(2));
		return flag;
	}
	//增加行数据
	function addRows(){
		var isValid = $("form").form("validate");
		var suplierId=$cb.combogrid("getValue");
		if(isValid){
			if(suplierId){
				$dg.datagrid('appendRow', {
					orderQty:0,
					price:0.00,
					discountNo:0.00,
					amount:0.00,
					taxAmount:0.00,
					taxNo:0
				});
				
				var rows = $dg.datagrid('getRows');
				var ll=rows.length;
				if(ll>=1){
					$dg.datagrid('beginEdit', ll - 1);
					$dg.datagrid('selectRow', ll - 1);
				}
				if(ll>=2){
					//$dg.datagrid('endEdit', ll - 2);
				}
				var ed = $dg.datagrid('getEditor', {index:ll-1,field:'myid'});
				$(ed.target).focus();
				
				var editemName = $dg.datagrid('getEditor', {index:ll-1,field:'itemName'});
				$(editemName.target).attr('disabled',true);
				var edunit = $dg.datagrid('getEditor', {index:ll-1,field:'unit'});
				$(edunit.target).attr('disabled',true);
				var edamount = $dg.datagrid('getEditor', {index:ll-1,field:'amount'});
				$(edamount.target).attr('disabled',true);
				var edtaxAmount = $dg.datagrid('getEditor', {index:ll-1,field:'taxAmount'});
				$(edtaxAmount.target).attr('disabled',true);
			}else{
				parent.$.messager.show({
					title : "提示",
					msg : "请选择供应商!",
					timeout : 1000 * 2
				});
			}
		}
	}
	//编几行
	function editRows(){
		var rows = $dg.datagrid('getSelections');
		$.each(rows,function(i,row){
			if (row) {
				var index = $dg.datagrid('getRowIndex', row);
				$dg.datagrid('beginEdit', index);
				var editemName = $dg.datagrid('getEditor', {index:index,field:'itemName'});
				$(editemName.target).attr('disabled',true);
				var edunit = $dg.datagrid('getEditor', {index:index,field:'unit'});
				$(edunit.target).attr('disabled',true);
				var edamount = $dg.datagrid('getEditor', {index:index,field:'amount'});
				$(edamount.target).attr('disabled',true);
				var edtaxAmount = $dg.datagrid('getEditor', {index:index,field:'taxAmount'});
				$(edtaxAmount.target).attr('disabled',true);
			}
		});
	}
	//移除行数据
	function removeRows(){
		var rows = $dg.datagrid('getSelections');
		$.each(rows,function(i,row){
			if (row) {
				var rowIndex = $dg.datagrid('getRowIndex', row);
				$dg.datagrid('deleteRow', rowIndex);
			}
		});
	}
	//保存行数据
	function saveRows(){
		if(endEdit()){
			if ($dg.datagrid('getChanges').length>0) {
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
			}
				return true;
		}else{
			return false;
		}
	}
	//弹窗增加
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
</script>
<style>
	.easyui-textbox{
		height: 18px;
		width: 170px;
		line-height: 16px;
	    /*border-radius: 3px 3px 3px 3px;*/
	    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
	    transition: border 0.2s linear 0s, box-shadow 0.2s linear 0s;
	}
	
	textarea:focus, input[type="text"]:focus{
	    border-color: rgba(82, 168, 236, 0.8);
	    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px rgba(82, 168, 236, 0.6);
	    outline: 0 none;
		}
		
	
	fieldset {
	    border: 0 none;
	    margin: 0;
	    padding: 0;
	}
	legend {
	    -moz-border-bottom-colors: none;
	    -moz-border-left-colors: none;
	    -moz-border-right-colors: none;
	    -moz-border-top-colors: none;
	    border-color: #E5E5E5;
	    border-image: none;
	    border-style: none none solid;
	    border-width: 0 0 1px;
	    color: #999999;
	    line-height: 20px;
	    display: block;
	    margin-bottom: 10px;
	    padding: 0;
	    width: 100%;
	}
	input, textarea {
	    font-weight: normal;
	}
	.table {
	    background-color: transparent;
	    border-collapse: collapse;
	    border-spacing: 0;
	    max-width: 100%;
	}
	
	.table{
		text-align:left;
		padding: 6px 6px 6px 6px;
	}
	.table th{
		text-align:left;
		padding: 6px 6px 6px 6px;
	}
	.table td{
		text-align:left;
		padding: 6px 6px 6px 6px;
	}
</style>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="">
	<input name="keyDown" id="keyDown"  type="hidden" value="keyDown"/>
	<input name="tempId" id="tempId"  type="hidden" value="<%=request.getParameter("tempId")==null?"":request.getParameter("tempId")%>"/>
		<form id="form" method="post">
				<div title="基本资料" data-options="iconCls:'icon-cstbase'" style="padding:10px">
						<fieldset>
							<legend><img src="extend/fromedit.png" style="margin-bottom: -3px;"/>基本资料编辑</legend>
							<input name="orderSaleId" id="orderSaleId"  type="hidden"/>
							<input name="created" id="created"  type="hidden"/>
							<input name="creater" id="creater"  type="hidden"/>
							<input name="status" id="status"  type="hidden"/>
							<input name="inserted" id="inserted"  type="hidden"/>
							<input name="updated" id="updated"  type="hidden"/>
							<input name="deleted" id="deleted"  type="hidden"/>
							 <table class="table">
								 <tr>
								    <th>销售单号</th>
									<td><input name="myid" id="myid" class="easyui-textbox easyui-validatebox" data-options="required:true" type="text"/></td>
									<th>销售日期</th>
									<td><input name="setupDate" id="setupDate" type="text"  class="easyui-textbox easyui-datetimebox" data-options="required:true"/></td>
									<th>销售类型</th>
									<td><input name="classId" id="classId" type="text" class="easyui-validatebox" /><input name="className" id="className" type="hidden" /></td>
									<th>批号</th>
									<td><input id="batchId" name="batchId" type="text" class="easyui-textbox easyui-validatebox" /><input id="batchNo" name="batchNo" type="hidden"/></td>
								 </tr>
								 <tr>
								    <th>客户编码</th>
									<td><input name="customerId" id="customerId" type="text" class="easyui-textbox easyui-validatebox" /><input name="customerMyid" id="customerMyid" type="hidden"/></td>
								    <th>客户名称</th>
									<td><input id="customerName" name="customerName" type="text" class="easyui-textbox easyui-validatebox" style="background-color:#cccccc;"  readonly/></td>
									<th>联系人</th>
									<td><input id="customerContact" name="customerContact" type="text" class="easyui-textbox easyui-validatebox"/><img src="css/extend/area.png" style="margin-left:2px;margin-bottom: -5px;cursor: pointer;" onclick="addAreaOpenDlg();"/></td>
									<th>电话</th>
									<td><input id="customerTel" name="customerTel" type="text" class="easyui-textbox easyui-validatebox" style="background-color:#cccccc;"  readonly/></td>
								 </tr>
								  <tr>
									 <th>项目名称</th>
									<td><input id="projectId" name="projectId" type="text" class="easyui-textbox easyui-validatebox"/><input name="projectName" id="projectName" type="hidden" /></td>
									<th>销售部门</th>
									<td><input id="saleOrganizationId" name="saleOrganizationId" type="text" class="easyui-textbox easyui-validatebox"/><input id="saleOrganizationName" name="saleOrganizationName" type="hidden"/><img src="css/extend/area.png" style="margin-left:2px;margin-bottom: -5px;cursor: pointer;" onclick="addAreaOpenDlg();"/></td>
									<th>币别</th>
									<td><input id="currencyId" name="currencyId" type="text" class="easyui-textbox easyui-validatebox"/><input id="currencyName" name="currencyName" type="hidden"/></td>
									 <th>仓库名称</th>
									<td><input id="warehouseId" name="warehouseId" type="text" class="easyui-textbox easyui-validatebox"/><input name="warehouseName" id="warehouseName" type="hidden" /></td>
								 </tr>
								  <tr>
									<th>预计交货日</th>
									<td><input id="estimatedDelivery" name="estimatedDelivery" type="text" class="easyui-textbox easyui-datebox"/></td>
									<th>交货方式</th>
									<td><input id="deliveryMode" name="deliveryMode" type="text" class="easyui-textbox easyui-validatebox"/><input id="deliveryModeName" name="deliveryModeName" type="hidden"/></td>
									 <th>扣税类别</th>
									<td><select id="deductionTax" class="easyui-combobox" name="deductionTax" style="width:171px;">
											<option value="Y">含税</option>
											<option value="N">未税</option>
										</select>
									</td>
									<th>客户单号</th>
									<td><input id="customerOrderNo" name="customerOrderNo" type="text" class="easyui-textbox easyui-validatebox"/></td>
								 </tr>
								  <tr>
									<th>合计未税价</th>
									<td><input id="amount" name="amount" type="text" class="easyui-textbox" style="background-color:#cccccc;"  readonly/></td>
									 <th>合计税金</th>
									<td><input id="taxAmount" name="taxAmount" type="text" class="easyui-textbox" style="background-color:#cccccc;"  readonly/></td>
									<th>合计总价</th>
									<td><input id="totalAmount" name="totalAmount" type="text" class="easyui-textbox" style="background-color:#cccccc;"  readonly/></td>
									<th>订金</th>
									<td><input id="advancePayment" name="advancePayment" type="text" class="easyui-textbox"/></td>
								 </tr>
								  <tr>
									<th>业务员</th>
									<td><input id="saleId" name="saleId" type="text" class="easyui-textbox" /><input id="saleName" name="saleName" type="hidden" class="easyui-textbox"/></td>
									 <th>立账方式</th>
									<td><!--  <input id="taxAmount1" name="taxAmount1" type="text" class="easyui-textbox"/>--></td>
									<th>备注</th>
									<td colspan="2"><!-- <input id="description" name="description" type="text" class="easyui-textbox"/>--></td>
								 </tr>
							   </table>
						</fieldset>
				</div>
							<table id="dg"></table>
							<div id="tb" style="padding:2px 0">
								<table cellpadding="0" cellspacing="0">
									<tr>
										<td style="padding-left:2px">
											<shiro:hasPermission name="cstConEdit">
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRows();">添加</a>
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRows();">编辑</a>
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-end" plain="true" onclick="endEdit();">结束编辑</a>
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeRows();">删除</a>
											</shiro:hasPermission>
										</td>
									</tr>
								</table>
							</div>
		</form>
		<div id="tb2" style="padding:2px 0">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:2px">
						<input id="searchbox" type="text"/>
					</td>
				</tr>
			</table>
		</div>
		<div id="mm">
				<div data-options="name:'name'">客户名称</div>
				<div data-options="name:'myid'">客户编码</div>
		</div> 
	</div>
</div>
