<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>表单管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../layout/script.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui/jquery.jdirk.js"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui/jeasyui.extensions.all.min.js"></script> --%>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui/jeasyui.icons.all.min.js"></script> --%>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/jquery-easyui/jeasyui.extensions.min.css"> --%>
<%-- 	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui/jeasyui.extensions.all.min.js"></script> --%>



    <style type="text/css">
        body {
            padding: 0px;
            margin: 0px;
            font-size: 12px;
        }

        .panel-container {
            padding: 0px 10px 10px 10px;
        }

        .panel-noborder {
            border-left-width: 0px;
            border-top-width: 0px;
            border-right-width: 0px;
        }
    </style>

    <script type="text/javascript">
        var $table;
		var $field;
		var $grid;
		$(function() {
			$("#panel").panel({   
				   width:'auto',   
				   height:$(this).height(),   
				   title: '表字段管理',   
			});
			$table = $("#table");
			$grid=$table.datagrid({
					url : "sysentryController.do?method=findAllSysEntry",
					width : 'auto',
					height : $(this).height()-50,
					pagination:true,
					border:false,
					rownumbers:true,
					singleSelect:true,
					striped:true,
					columns : [ [ 
					              {field : 'entityId',title : '表ID',width : parseInt($(this).width()*0.1),align : 'center',editor : "text",hidden:true},
					              {field : 'entityTablename',title : '表名',width :parseInt($(this).width()*0.1),align : 'center',editor : {type:'validatebox',options:{required:true}}},
					              {field : 'entityChinaname',title : '表中文名',width : parseInt($(this).width()*0.1),align : 'center',editor : "text"},
					              {field : 'dsname',title : '数据源',width : parseInt($(this).width()*0.1),align : 'center',editor : "text"}
// 					              {field : 'sort',title : '排序',width :parseInt($(this).width()*0.1),align : 'center',editor : "numberbox"}
					              ] ]
					,toolbar:'#tbRole',
					onDblClickRow : getFields
				});
				
			
			
			
			$field = $("#field");
			$field.treegrid({
				width : 'auto',
				height : $(this).height()-50,
				url : "sysentryController.do?method=getEntryFields",
				rownumbers:true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				border:false,
				striped:true,
				singleSelect:false,
				cascadeCheck:true,
				deepCascadeCheck:true,
				idField: 'FIELD_ID',
				treeField: 'FIELD_CHINA_NAME',
				parentField : 'FIELD_ENTITY_ID',
				columns : [ [ 
				              {field:'ck',checkbox:true},
				              {field : 'FIELD_CHINA_NAME',title : '字段名称',width : parseInt($(this).width()*0.2)},
				              //{field : 'pName',title : '父程式名称',width : 100,align : 'center'},
				              //{field : 'sort',title : '排序编码',width : 50,align : 'center'},
				              //{field : 'image',title : '程式图标',width : 100},
				              //{field : 'path',title : '程式路径',width : 150,align : 'left'},
				              {field : 'FIELD_NAME',title : '字段编码',width : parseInt($(this).width()*0.1),align : 'center'},
				              {field : 'FIELD_TYPE',title : '字段类型',width : parseInt($(this).width()*0.1),align : 'center' },
							   {field : 'DELETE_MARK',title : '是否启用',width : parseInt($(this).width()*0.1),align : 'center' ,
						            formatter:function(value,row){
						            	if("N"==row.DELETE_MARK)
										  return "<font color=green>是<font>";
						            	else
						            	  return "<font color=red>否<font>";  
										}},
				              {field : 'description',title : '字段描述',width : parseInt($(this).width()*0.2),align : 'left'}
				              ] ]
				,toolbar:'#tb',
				onClickRow:function(row){   
	            	                      //级联选择   
	            	                   $field.treegrid('cascadeCheck',{   
	            	                          id:row.id, //节点ID   
	            	                          deepCascade:true //深度级联   
	            	                     });   
	            	               }
			});
			
			
			//搜索框
			$("#searchbox").searchbox({ 
				 menu:"#mm", 
				 prompt :'模糊查询',
			    searcher:function(value,name){   
			    	var str="{\"roleName\":\""+name+"\",\"value\":\""+value+"\"}";
		            var obj = eval('('+str+')');
		            $table.datagrid('reload',obj); 
			    }
			   
			});
		});
		
		function endEdit(){
			var flag=true;
			var rows = $table.datagrid('getRows');
			for ( var i = 0; i < rows.length; i++) {
				$table.datagrid('endEdit', i);
				var temp=$table.datagrid('validateRow', i);
				if(!temp){flag=false;}
			}
			return flag;
		}
		
		function addRows(){
			$table.datagrid('appendRow', {});
			var rows = $table.datagrid('getRows');
			$table.datagrid('beginEdit', rows.length - 1);
		}
		function editRows(){
			var rows = $table.datagrid('getSelections');
			$.each(rows,function(i,row){
				if (row) {
					var rowIndex = $table.datagrid('getRowIndex', row);
					$table.datagrid('beginEdit', rowIndex);
				}
			});
		}
		function removeRows(){
			var rows = $table.datagrid('getSelections');
			$.each(rows,function(i,row){
				if (row) {
					var rowIndex = $table.datagrid('getRowIndex', row);
					$table.datagrid('deleteRow', rowIndex);
				}
			});
		}
		function saveRows(){
			if(endEdit()){
				if ($table.datagrid('getChanges').length) {
					var inserted =$table.datagrid('getChanges', "inserted");
					var deleted =$table.datagrid('getChanges', "deleted");
					var updated = $table.datagrid('getChanges', "updated");
					
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
					$.post("permissionController.do?method=persistenceRole", effectRow, function(rsp) {
						if(rsp.status){
							$table.datagrid('acceptChanges');
						}
						$.messager.alert(rsp.title, rsp.message);
					}, "JSON").error(function() {
						$.messager.alert("提示", "提交错误了！");
					});
				}
			}else{
				$.messager.alert("提示", "字段验证未通过!请查看");
			}
		}
		function collapseAll(){
			var node = $field.treegrid('getSelected');
			if (node) {
				$field.treegrid('collapseAll', node.id);
			} else {
				$field.treegrid('collapseAll');
			}
		}
		function expandAll(){
			var node = $field.treegrid('getSelected');
			if (node) {
				$field.treegrid('expandAll', node.id);
			} else {
				$field.treegrid('expandAll');
			}
		}
		function refresh(){
			$field.treegrid('reload');
		}
		function selectNode(){
			$field.treegrid('select','1');
		}
		function getLoad(){
			$table.datagrid('load',{ 
				roleName:$("#roleName").val()
			}); 
		}
		
		/************************************************************************/
		    //查询功能  
			//menuItemSearchId为查询条件所在的form的id  
			function searchMenuItem(){  
			    $("#field").treegrid("options").queryParams = getQueryParams("field");  
			    autoLoad($("#field").treegrid("getPager"),1);  
			}  
			  
			/** 
			 * 自动触发分页的加载操作 
			 
			 * @param pager  各个控件对应的pagination组件 
			 */  
			function autoLoad(pager,pageNumber){  
			    var event = jQuery.Event("keydown");//模拟一个键盘事件          
			    event.keyCode = 13;//keyCode=13是回车  
			      
			    pager.find('input.pagination-num').val(pageNumber); //设置跳转页为当前指定页面  
			      
			    pager.find('input.pagination-num').trigger(event);  
			}  
			  
			  
			/** 
			 * 将指定form参数转换为json对象 
			 */  
			function getQueryParams(conditionFormId){  
			    var searchCondition = getJqueryObjById(conditionFormId).serialize();  
			    var obj = {};  
			    var pairs = searchCondition.split('&');  
			    var name,value;  
			      
			    $.each(pairs, function(i,pair) {  
			        pair = pair.split('=');  
			        name = decodeURIComponent(pair[0]);  
			        value = decodeURIComponent(pair[1]);  
			          
			        obj[name] =  !obj[name] ? value :[].concat(obj[name]).concat(value);              //若有多个同名称的参数，则拼接  
			    });  
			      
			    return obj;  
			}  
			  
			/** 
			 * 根据id获取jquery对象 
			 * @param id 
			 */  
			function getJqueryObjById(id){  
			    return $("#" + id);  
			}   
		/************************************************************************/
		function getFields(rowIndex, rowData){ 
			console.log($field.treegrid('getData').length);
			$.post("sysentryController.do?method=getEntryFields", {fieldEntityId:rowData.entityId}, function(rsp) {
							if(rsp.length!=0){
								$field.treegrid('loadData', rsp);
							}else{
								parent.$.messager.show({
									title :"提示",
									msg :"该角色暂无权限!",
									timeout : 1000 * 2
								});
							}
						}, "JSON").error(function() {
							parent.$.messager.show({
								title :"提示",
								msg :"获取权限失败!",
								timeout : 1000 * 2
							});
						});
       } 
		function savePermission(){
			var selections=$field.treegrid('getSelections');
			var selectionRole=$table.datagrid('getSelected');
			var checkedIds=[];
			$.each(selections,function(i,e){
				checkedIds.push(e.id);
			});
			if(selectionRole){
				$.ajax({
					url:"permissionController.do?method=savePermission",
					data: "roleId="+selectionRole.roleId+"&checkedIds="+(checkedIds.length==0?"":checkedIds),
					success: function(rsp){
						parent.$.messager.show({
							title :rsp.title,
							msg : rsp.message,
							timeout : 1000 * 2
						});
					},
					error:function(){
						parent.$.messager.show({
							title :"提示",
							msg : "分配失败！",
							timeout : 1000 * 2
						});
					}

				});
			}else{
				 parent.$.messager.show({
						title :"提示",
						msg : "请选择角色！",
						timeout : 1000 * 2
					});
			 }
		}
		function delRows(){
			var row = $table.datagrid('getSelected');
			if(row){
				var rowIndex = $table.datagrid('getRowIndex', row);
				$table.datagrid('deleteRow', rowIndex);
				$.ajax({
					url:"permissionController.do?method=delRole",
					data: "roleId="+row.roleId,
					success: function(rsp){
						parent.$.messager.show({
							title : rsp.title,
							msg : rsp.message,
							timeout : 1000 * 2
						});
					}
				});
			}else{
				parent.$.messager.show({
					title : "提示",
					msg : "请选择行数据!",
					timeout : 1000 * 2
				});
			}
		}
		//弹窗修改
		function updRowsOpenDlg() {
			var row = $table.datagrid('getSelected');
			if (row) {
				parent.$.modalDialog({
					title : "编辑角色",
					width : 600,
					height : 400,
					href : "jsp/permission/roleEditDlg.jsp",
					onLoad:function(){
						var f = parent.$.modalDialog.handler.find("#form");
						f.form("load", row);
					},			
					buttons : [ {
						text : '编辑',
						iconCls : 'icon-ok',
						handler : function() {
							parent.$.modalDialog.openner= $grid;//因为添加成功之后，需要刷新这个treegrid，所以先预定义好
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
				title : "添加角色",
				width : 600,
				height : 400,
				href : "jsp/permission/roleEditDlg.jsp",
				buttons : [ {
					text : '保存',
					iconCls : 'icon-ok',
					handler : function() {
						parent.$.modalDialog.openner= $grid;//因为添加成功之后，需要刷新这个treegrid，所以先预定义好
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
	</script>
</head>
<body>
   <div id="containerTabs" class="easyui-tabs" data-options="fit: true, border: false, showOption: true, enableNewTabMenu: true, tabPosition: 'left', headerWidth: 100">
        <div id="ds" class="panel-container" data-options="title: '表字段管理', iconCls: 'icon-hamburg-advertising', selected: true" style="position: relative;">
             	<div id="panel" data-options="border:false">
					<div class="easyui-layout" data-options="fit:true">
						<div data-options="region:'west',split:true,border:true" style="width:500px;">
							<div id="tbRole" style="padding:2px 0">
								<table cellpadding="0" cellspacing="0">
									<tr>
										<td style="padding-left:4px;padding-bottom:4px;">
											<shiro:hasPermission name="perConfig">
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-config" plain="true" onclick="savePermission();">保存设置</a>
											</shiro:hasPermission>
											<shiro:hasPermission name="roleAdd">
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRowsOpenDlg();">添加</a>
											</shiro:hasPermission>
											<shiro:hasPermission name="roleEdit">
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updRowsOpenDlg();">编辑</a>
											</shiro:hasPermission>
											<shiro:hasPermission name="roleDel">
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delRows();">删除</a>
											</shiro:hasPermission>
											<!--<shiro:hasPermission name="roleEndEdit">
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="endEdit();">结束编辑</a>
											</shiro:hasPermission>
											<shiro:hasPermission name="roleSave">
												<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveRows();">保存</a>
											</shiro:hasPermission>-->
										</td>
									</tr>
									<tr>
										<td style="padding-left:4px;padding-bottom:4px;">
											<input id="searchbox" type="text"/>
										</td>
									</tr>
								</table>
							</div>
							<div id="mm">
									<div name="name">角色名称</div>
									<div name="description">角色描述</div>
							</div>
							<table id="table" title="数据表管理"></table>
						</div>
						<div data-options="region:'center',border:true">
							 <div id="tb">
								<div style="margin:5px 5px 5px 5px;">
									<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="expandAll();">展开</a>
									<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="collapseAll();">收缩</a>
									<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refresh();">刷新</a>
								</div>
							</div>
							<table id="field" title="数据字段管理"></table>
						</div>
					</div>
				</div>
        </div>
        
        <div id="ds" class="panel-container" data-options="title: '表单管理', iconCls: 'icon-hamburg-advertising', selected: false" style="position: relative;">
            <p>demo</p>
<%--             <jsp:include page="/page/jee/druidDataSource.jsp"></jsp:include> --%>
        </div>
        
        <div id="demo" class="panel-container" data-options="title: '功能演示', iconCls: 'icon-hamburg-advertising', selected: false" style="position: relative;">
            <p></p>
        </div>
        
        
        <div id="view" class="panel-container" data-options="title: '关于...', iconCls: 'icon-hamburg-library'">
            <hr />
        </div>
    </div>
    <div style="display: none;">
        <script type="text/javascript">
        </script>
    </div>
</body>
</html>
