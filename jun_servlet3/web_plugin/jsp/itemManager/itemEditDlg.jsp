<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript">
	var $dg;
	var $tree;
	$(function() {
		$("#tt").tabs({
			border:false,
			width:'auto',
			height:$(this).height()-120
		});
		$("#image").xheditor({
			width:$(this).width()-1100,
			height:$(this).height()-360,
			tools : 'Img,About',
			html5Upload : true,
			upMultiple : 1,
			upImgUrl : '${pageContext.request.contextPath}/bugController.do?method=upload',
			upImgExt : 'jpg,jpeg,gif,png',
			onUpload:function(rst){
				$.each(rst,function(i,e){
					$("#xheImgAlt").val(e.localfile);
				});
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
		
		$("#classId").combobox({
			width:171,
			url:"systemCodeController.do?method=findSystemCodeByType&codeMyid=customerClass",
			valueField: 'codeId',
			textField: 'name',
			onSelect:function(value){
				$("#className").val(value.name);
			}
		});
		$("#brandId").combobox({
			width:171,
			url:"itemController.do?method=findBrandList",
			valueField: 'brandId',
			textField: 'name',
			onSelect:function(value){
				$("#brandName").val(value.name);
			}
		});
		
		$("#form").form({
			url :"itemController.do?method=persistenceItem",
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
	//弹窗增加品牌
	function addBrandOpenDlg() {
		$("<div/>").dialog({
			href : "jsp/itemManager/brandEditDlg.jsp",
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
						url : "itemController.do?method=addBrands",
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
									$("#brandId").combobox("reload");
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
		padding: 6px 8px 6px 8px;
	}
	.table th{
		text-align:left;
		padding: 6px 8px 6px 8px;
	}
	.table td{
		text-align:left;
		padding: 6px 8px 6px 8px;
	}
</style>
	<div data-options="region:'center',border:false" title="">
	<input name="tempId" id="tempId"  type="hidden" value="<%=request.getParameter("tempId")==null?"":request.getParameter("tempId")%>"/>
		<form id="form" method="post">
			<div id="tt">
				<div title="基本资料" data-options="iconCls:'icon-cstbase'" style="padding:10px;height: 100%">
						<fieldset>
							<legend>基本资料编辑</legend>
							<input name="itemId" id="itemId"  type="hidden"/>
							<input name="created" id="created"  type="hidden"/>
							<input name="creater" id="creater"  type="hidden"/>
							<input name="status" id="status"  type="hidden"/>
							 <table class="table">
								 <tr>
								    <th>货品名称</th>
									<td><input name="name" id="name" placeholder="请输入货品名称" class="easyui-textbox easyui-validatebox" type="text" data-options="required:true"/></td>
									<th>货品编码</th>
									<td><input name="myid" id="myid" type="text"  class="easyui-textbox easyui-validatebox" data-options="required:true"/></td>
									<th>货品类别</th>
									<td><input name="classId" id="classId" type="text" class="easyui-textbox easyui-validatebox" /><input name="className" id="className" type="hidden" /></td>
								 </tr>
								 <tr>
									<th>货品规格</th>
									<td><input id="spec" name="spec" type="text" class="easyui-textbox easyui-validatebox" /></td>
								    <th>国际条码</th>
									<td><input name="barcode" id="barcode" type="text" class="easyui-textbox easyui-validatebox" /></td>
								    <th>单位</th>
									<td><input id="unit" name="unit" type="text" class="easyui-textbox easyui-validatebox" /></td>
								 </tr>
								  <tr>
									<th>产地</th>
									<td><input id="cityId" name="cityId" type="text" class="easyui-textbox easyui-validatebox"/><input id="cityName" name="cityName" type="hidden"/><img src="css/extend/area.png" style="margin-left:2px;margin-bottom: -5px;cursor: pointer;" onclick="addAreaOpenDlg();"/></td>
									<th>重量</th>
									<td><input id="weight" name="weight " type="text" class="easyui-textbox easyui-validatebox"/></td>
									 <th>重量单位</th>
									<td><input id="maund" name="maund" type="text" class="easyui-textbox easyui-validatebox"/></td>
								 </tr>
								 <tr>
									<th>品牌</th>
									<td><input name="brandId" id="brandId" type="text" class="easyui-textbox easyui-validatebox" /><input name="brandName" id="brandName" type="hidden" /><img src="css/extend/area.png" style="margin-left:2px;margin-bottom: -5px;cursor: pointer;" onclick="addBrandOpenDlg();"/></td>
									<th>税率</th>
									<td><input id="taxNo" name="taxNo" type="text" class="easyui-textbox easyui-validatebox"/></td>
								 </tr>
								 <tr>
									<th>备注</th>
									<td colspan="5"><textarea class="easyui-textbox" id="description" name="description"  style="width: 710px;height: 80px;"></textarea></td>
								</tr>
							   </table>
						</fieldset>
				</div>
				<div title="包装资料" data-options="iconCls:'icon-help'" style="padding:10px">
						<fieldset>
							<legend>包装资料编辑</legend>
						 <table class="table">
							   <tr>
								    <th>包装单位</th>
									<td><input name="packageUnit" id="packageUnit" class="easyui-textbox easyui-validatebox" type="text"/></td>
									<th>换算率</th>
									<td><input name="converts" id="converts" type="text"  class="easyui-textbox easyui-validatebox" /></td>
								 </tr>
						</table>
						</fieldset>
				</div>
				<div title="库存参数" data-options="iconCls:'icon-help'" style="padding:10px">
						<fieldset>
							<legend>库存参数编辑</legend>
						 <table class="table">
							 <tr>
								    <th>批次管理</th>
									<td><select id="isBatch" class="easyui-combobox" name="isBatch" style="width:171px;">
											<option value="Y">是</option>
											<option value="N">否</option>
										</select>
									</td>
									<th>有效期管理</th>
									<td><select id="isValid" class="easyui-combobox" name="isValid" style="width:171px;">
											<option value="Y">是</option>
											<option value="N">否</option>
										</select>
									</td>
								 </tr>
						</table>
						</fieldset>
				</div>
				<div title="价格资料" data-options="iconCls:'icon-help'" style="padding:10px">
					<fieldset>
							<legend>价格资料编辑</legend>
						 <table class="table">
							   <tr>
								    <th>成本价</th>
									<td><input name="cost" id="cost" class="easyui-textbox easyui-validatebox" type="text"/></td>
									<th>零售价</th>
									<td><input name="retailPrice" id="retailPrice" type="text"  class="easyui-textbox easyui-validatebox" /></td>
								 </tr>
						</table>
						</fieldset>
				</div>
				<div title="货品图片" data-options="iconCls:'icon-help'" style="padding:10px">
						<fieldset>
							<legend>货品图片编辑</legend>
							<textarea class="easyui-textbox" id="image" name="image"></textarea>
						</fieldset>
				</div>
		  </div>
		</form>
	</div>
