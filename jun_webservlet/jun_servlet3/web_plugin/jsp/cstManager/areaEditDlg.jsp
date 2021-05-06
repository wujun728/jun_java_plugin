<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
	<script type="text/javascript" src="jsp/cstManager/js/areaEditDlg.js"></script>
	<link rel="stylesheet" href="jsp/cstManager/css/areaEditDlg.css" type="text/css"></link>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="form1" method="post">
			<fieldset>
				<legend><img src="extend/fromedit.png" style="margin-bottom: -3px;"/> 区域编辑</legend>
				<input name="cityId" id="cityId"  type="hidden"/>
				<input name="created" id="created"  type="hidden"/>
				<input name="creater" id="creater"  type="hidden"/>
				<input name="status" id="status"  type="hidden"/>
				 <table>
					 <tr>
					    <th>省区名称</th>
						<td><input name="provinceId" id="provinceId"  class="easyui-textbox" type="text" data-options="required:true"/></td>
						<th>城市名称</th>
						<td><input name="name" id="name" type="text"  class="easyui-textbox easyui-validatebox" data-options="required:true"/></td>
					 </tr>
				 </table>
			</fieldset>
		</form>
	</div>
</div>
