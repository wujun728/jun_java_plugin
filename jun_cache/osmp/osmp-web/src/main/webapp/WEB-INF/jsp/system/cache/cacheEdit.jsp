<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div align="center">
    <form id="CacheEditForm" method="POST">
        <input type="hidden" name="id" value="${cacheDefined.id}" />
        <table>
            <tr>
                <th>有效时间:</th>
                <td><input name="timeToLive" type="text" value="${cacheDefined.timeToLive}"
                    class="easyui-validatebox" data-options="required:true" /></td>
            </tr>
            <tr>
                <th>空闲时间:</th>
                <td><input name="timeToIdle" type="text" value="${cacheDefined.timeToIdle}"
                    class="easyui-validatebox" data-options="required:true" /></td>
            </tr>
            <tr>
                <th>状态:</th>
                <td><select id="state" name="state" class="easyui-combobox" style="width: 125px;">
                        <option value="0">关闭</option>
                        <option value="1" <c:if test="${cacheDefined.state == 1 }">selected="selected" </c:if>>开启</option>
                </select></td>
            </tr>
        </table>
    </form>
</div>
