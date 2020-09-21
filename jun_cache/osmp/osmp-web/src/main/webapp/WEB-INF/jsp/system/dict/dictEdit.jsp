<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center">
	<form id="DictUpdateForm" method="POST">
		<input type="hidden" name="id" value="${dict.id}" />
		<table>
			<tr>
				<th>编码:</th>
				<td><input name="code" type="text" value="${dict.code}" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>名称:</th>
				<td><input name="name" type="text" value="${dict.name}" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>类型:</th>
				<td>
					<select id="type" name="type"  class="easyui-combobox"  style="width: 125px;">
						<option value="1">一般</option>
						<option value="2">数据库表</option>
						<option value="3">资源文件</option>
					</select>
				</td>
			</tr>
			<tbody id="type2" style="display: none;">
				<tr>
					<th>表名:</th>
					<td><input name="tabName" type="text" value="${dict.tabName}"  />
					</td>
				</tr>
				<tr>
					<th>字典表KEY:</th>
					<td><input name="keyFilde" type="text" value="${dict.keyFilde}" />
					</td>
				</tr>
				<tr>
					<th>字典表VALUE:</th>
					<td><input name="valueFilde" type="text" value="${dict.valueFilde}" />
					</td>
				</tr>
			</tbody>
			<tbody id="type3" style="display: none;">
				<tr>
					<th>资源文件:</th>
					<td><input name="propertiesFile" type="text" value="${dict.propertiesFile}" />
					</td>
				</tr>
			</tbody>
		</table>
	</form>
			<script type="text/javascript">
		$('#type').combobox({  	
			onSelect: function(record){  		
		         if("1" == record.value){
		        	 var ui = document.getElementById("type2");
		             ui.style.display="none";
		        	 var ui = document.getElementById("type3");
		             ui.style.display="none";
		         }else if("2" == record.value){
		        	 var ui = document.getElementById("type2");
		             ui.style.display="";
		        	 var ui = document.getElementById("type3");
		             ui.style.display="none";
		         }else if("3" == record.value){
		        	 var ui = document.getElementById("type3");
		             ui.style.display="";
		        	 var ui = document.getElementById("type2");
		             ui.style.display="none";
		         }
		      }  
		});
		</script>
</div>
