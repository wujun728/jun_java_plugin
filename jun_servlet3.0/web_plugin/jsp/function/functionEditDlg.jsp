<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript" src="jsp/function/js/functionEditDlg.js"></script>
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
	table {
	    background-color: transparent;
	    border-collapse: collapse;
	    border-spacing: 0;
	    max-width: 100%;
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
	table ,th,td{
		text-align:left;
		padding: 6px;
		font-weight:normal;
	}
</style>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<input name="tempId" id="tempId"  type="hidden" value="<%=request.getParameter("tempId") %>"/>
		<form id="form" method="post">
			<input name="cp" id="cp"  type="hidden" value="<%=request.getParameter("cp") %>"/>
			<fieldset>
				<legend><img src="extend/fromedit.png" style="margin-bottom: -3px;"/> 菜单编辑</legend>
				<input name="permissionId" id="permissionId"  type="hidden"/>
				<input name="created" id="created"  type="hidden"/>
				<input name="creater" id="creater"  type="hidden"/>
				<input name="status" id="status"  type="hidden"/>
				<input name="state" id="state"  type="hidden"/>
				 <table>
					 <tr>
					    <th>菜单名称</th>
						<td><input name="name" id="name" placeholder="请输入菜单名称" class="easyui-textbox easyui-validatebox" type="text"/></td>
						<th>父菜单名称</th>
						<td><input name="pid"  class="easyui-textbox" id="pid" type="text"/><input name="pname" id="pname"  type="hidden"/></td>
					 </tr>
					 <tr>
					    <th>排序编码</th>
						<td><input name="sort" id="sort" type="text" class="easyui-textbox easyui-validatebox" required="required"/></td>
						<th>菜单图标</th>
						<td><input id="iconCls" name="iconCls" type="text"/></td>
					 </tr>
					  <tr>
					    <th>菜单路径</th>
						<td><input id="url" name="url" type="text" class="easyui-textbox easyui-validatebox" required="required"/></td>
						<th>菜单编码</th>
						<td><input id="myid" name="myid" type="text" class="easyui-textbox easyui-validatebox" required="required"/></td>
					 </tr>
					 <tr>
						<th>菜单类型</th>
						<td>
							<select id="type" class="easyui-combobox" name="type" style="width:171px;" data-options="required:true">
								<option value="F">菜单</option>
								<option value="O">操作</option>
							</select>
						</td>
						<th>是否启用</th>
						<td><select id="isused" class="easyui-combobox" name="isused" style="width:171px;" data-options="required:true">
								<option value="Y">是</option>
								<option value="N">否</option>
							</select>
						</td>
					</tr>
					 <tr>
						<th>描述</th>
						<td colspan="3"><textarea class="easyui-textbox" name="description"   id="description"  style="width: 435px;height: 100px;"></textarea></td>
					</tr>
				 </table>
			</fieldset>
		</form>
	</div>
</div>
