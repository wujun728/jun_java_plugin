<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
</head>
<body>
	<script type="text/javascript">
		var onChange = function(rec){
			if(rec.parentCode == 'roleType'){
				$('#typeRemark').val(rec.name);
				$('#type').val(rec.code);
			}else if(rec.parentCode == 'logical'){
				$('#condition').val(rec.code);
			}else{
				
			}
		}
	</script>
	<div class="easyui-layout" fit="true">
		<div region="center" style="padding: 1px;">
			<div align="center">
				<form id="conditionAddForm" method="POST">
					<input type="hidden" name="strategyId" value="${id}" />
					<input type="hidden" name="type" id="type" value="" />
					<input type="hidden" name="typeRemark" id="typeRemark" value="" />
					<input type="hidden" name="condition" id="condition" value="" />
					<table>
						<tr>
							<th>策略类型:</th>
							<td><z:select dictCode="roleType" id="cllx" onSelectJs="onChange"></z:select></td>
						</tr>
						<tr>
							<th>名称:</th>
							<td><input name="key" type="text"
								class="easyui-validatebox" data-options="required:true" /></td>
						</tr>
						<tr>
							<th>逻辑条件:</th>
							<td><z:select dictCode="logical" id="logical" onSelectJs="onChange"></z:select></td>
						</tr>
						<tr>
							<th>值:</th>
							<td><input name="value" type="text"
								class="easyui-validatebox" data-options="required:true" /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>