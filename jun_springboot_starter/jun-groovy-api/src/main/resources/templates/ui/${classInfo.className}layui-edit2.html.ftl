<!DOCTYPE html>
<html>
<body>
<form class="layui-form p-page">
	<h3 class="pb-3">新增XXX信息</h3>
	<table class="layui-table layui-table-form">

    <input type="hidden" name="${classInfo.className?uncap_first}Id" value="" class="layui-input">


<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem >
         <#assign counter = fieldItem_index + 1>
        <#-- ${counter} - ${fieldItem} <br> -->
        <#if counter%3 == 0><tr></#if>
        <td class="layui-td-gray">${fieldItem.fieldComment}</td>
                    <td><input type="text" name="${fieldItem.fieldName}" autocomplete="off" lay-verify="required"  lay-reqtext="${fieldItem.fieldComment}不能为空"
                     placeholder="请输入${fieldItem.fieldComment}"  class="layui-input"></td>
         <#--<tip>${fieldItem.fieldComment}</tip>-->
<#if counter%3 == 0></tr></#if>
    </#list>
</#if>

        <tr><td colspan="6"><strong>备注信息</strong></td></tr>
        <tr>
            <td colspan="6"><textarea name="remark" placeholder="请输入备注信息" class="layui-textarea"></textarea></td>
        </tr>

    </table>
    	<div class="pt-4">
    		<input type="hidden" name="id" value="0"/>
    		<input type="hidden" name="scene" value="add"/>
    		<button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="webform">立即提交</button>
    		<button type="reset" class="layui-btn layui-btn-primary">重置</button>
    	</div>
    </form>
<script src="￥{request.contextPath}/static/lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
<script>
    layui.use(['form'], function () {
        var form = layui.form,
            layer = layui.layer,
            $ = layui.$;

        //监听提交
        form.on('submit(saveBtn)', function (data) {
            $.ajax({
                type: 'POST',
                url: "￥{request.contextPath}/${classInfo.className?uncap_first}/save",
                data:JSON.stringify(data.field),
                dataType: "json",
                contentType: "application/json",
                success: function (responseData) {
                    if (responseData.code === 200) {
                        layer.msg(responseData.msg, function () {
                            // 关闭弹出层
                            //layer.close(index);
                            var iframeIndex = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(iframeIndex);
                            parent.searchBtn.click();
                        });
                    } else {
                        layer.msg(responseData.msg, function () {
                            //window.location = '/index.html';
                        });
                    }
                }
            });
            return false;
        });

    });
</script>
</body>
</html>