<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center">
	<form id="strategyAddForm" method="POST">
		<table>
			<tr>
				<th>策略名称:</th>
				<td><input name="name" type="text" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<th>转发IP:</th>
				<td><input name="forwardIp" type="text" class="easyui-validatebox" data-options="required:true" />
				</td>
			</tr>
			<tr>
                <th>状态:</th>
                <td><select id="type" name="status" class="easyui-combobox" style="width: 125px;" >
                        <option value="1" >开启</option>
                        <option value="0">关闭</option>
                </select></td>
            </tr>
            <tr>
                <th>备注:</th>
                <td><input name="remark" type="text" class="easyui-validatebox" data-options="required:true" /></td>
            </tr>
		</table>
	</form>
</div>
