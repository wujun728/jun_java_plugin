<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center">
    <form id="ServerUpdateForm" method="POST">
        <input type="hidden" name="id" value="${servers.id}" />
        <table>
            <tr>
                <th>服务器名称:</th>
                <td><input name="serverName" type="text" value="${servers.serverName}" class="easyui-validatebox"
                    data-options="required:true" /></td>
            </tr>
            <tr>
                <th>服务器IP:</th>
                <td><input name="serverIp" type="text" value="${servers.serverIp}" class="easyui-validatebox"
                    data-options="required:true" /></td>
            </tr>
            <tr>
                <th>SSH端口:</th>
                <td><input name="sshPort" type="text" value="${servers.sshPort}" class="easyui-validatebox"
                    data-options="required:true" /></td>
            </tr>
            <tr>
                <th>登陆用户名:</th>
                <td><input name="user" type="text" value="${servers.user}" class="easyui-validatebox"
                    data-options="required:true" /></td>
            </tr>
            <tr>
                <th>登陆密码:</th>
                <td><input name="password" type="text" value="${servers.password}" class="easyui-validatebox"
                    data-options="required:true" /></td>
            </tr>
            <tr>
                <th>管理界面URL:</th>
                <td><input name="manageUrl" type="text" value="${servers.manageUrl}" class="easyui-validatebox"
                    data-options="required:true" /></td>
            </tr>
            <tr>
                <th>执行脚本路径:</th>
                <td><input name="commandPath" type="text" value="${servers.commandPath}" class="easyui-validatebox"
                    data-options="required:true" /></td>
            </tr>
            <tr>
                <th>服务器状态:</th>
                <td><select id="type" name="state" class="easyui-combobox" style="width: 125px;">
                        <option value="1">正常</option>
                        <option value="2">不正常</option>
                </select></td>
            </tr>
            <tr>
                <th>备注:</th>
                <td><input name="remark" type="text" value="${servers.remark}" /></td>
            </tr>
        </table>
    </form>

</div>
