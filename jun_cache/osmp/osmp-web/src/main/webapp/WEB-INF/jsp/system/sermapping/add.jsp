<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	function getDataList(url,selId){
		if("bundle" == selId){
			value = ${serMapping.id};
		}
		$.ajax({
			url : url,
			dataType : 'json',
			success : function(result){
				var json = eval(result);
				var value = "";
				if("bundle" == selId){
					value = '${serMapping.bundle}';
				}else if("version" == selId){
					value = '${serMapping.version}';
				}else{
					value = '${serMapping.serviceName}';
				}
				for(var i = 0; i < json.length; i++){
					var selected = (value == json[i]) ? "selected" : "";
					$("#"+selId).append("<option value='"+json[i]+"' "+ selected +" >"+json[i]+"</option>");
				}
			}
		})
	}
	
	$(document).ready(function(){
// 		$('#bundle').combobox({ 
// 			multiple:false,
// 			editable:false,
// 			valueField:'text',    
// 		    textField:'text', 
// 			width:150,
// 		    url:'${BASE_PATH}/sermapping/bundles.do'
// 		    })
		getDataList("${BASE_PATH}/sermapping/bundles.do","bundle");
		getDataList("${BASE_PATH}/sermapping/versions.do","version");
		getDataList("${BASE_PATH}/sermapping/services.do","serviceName");
	})
</script>
<div align="center">
	<form id="SerMappingAddForm" method="POST">
		<input type="hidden" name="id" value="${serMapping.id}" />
		<table>
			<tr>
				<th>接口名称:</th>
				<td><input name="interfaceName" type="text" style="width: 150px;" value = "${serMapping.interfaceName }" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>组件名称:</th>
				<td>
					<select id="bundle" name="bundle"  style="width: 150px;">
					</select>
				</td>
			</tr>
			<tr>
				<th>版本:</th>
				<td>
					<select id="version" name="version"  style="width: 150px;">
					</select>
				</td>
			</tr>
			<tr>
				<th>服务名称:</th>
				<td>
					<select id="serviceName" name="serviceName"  style="width: 150px;">
					</select>
				</td>
			</tr>
		</table>
	</form>
</div>
