<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">//src="jsp/bugManager/js/bugEditDlg.js"
$(function() {
	$("#description").xheditor({
		//width:$(this).width()-900,
		//height:$(this).height()-900,
		tools : 'full',
		html5Upload : true,
		upMultiple : 4,
		upLinkUrl : 'bugController.do?method=upload',
		upLinkExt : 'zip,rar,txt,doc,docx,xls,xlsx',
		upImgUrl : 'bugController.do?method=upload',
		upImgExt : 'jpg,jpeg,gif,png',
		onUpload:function(rst){
			$.each(rst,function(i,e){
				//alert(e.localfile);
				$("#xheImgAlt").val(e.localfile);
				$("#xheLinkText").val(e.localfile);
			});
		}
	});
	$("#form").form({
		url :"bugController.do?method=addBug",
		onSubmit : function() {
			parent.$.messager.progress({
				title : '提示',
				text : '数据处理中，请稍后....'
			});
			var isValid = $(this).form('validate');
			if (!isValid) {
				parent.$.messager.progress('close');
			}
			return isValid;
		},
		success : function(result) {
			parent.$.messager.progress('close');
			result = $.parseJSON(result);
			if (result.status) {
				parent.reload;
				parent.$.modalDialog.openner.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_datagrid这个对象，是因为role.jsp页面预定义好了
				parent.$.modalDialog.handler.dialog('close');
				parent.$.messager.show({
					title : result.title,
					msg : result.message,
					timeout : 1000 * 2
				});
			}else{
				parent.$.messager.show({
					title :  result.title,
					msg : result.message,
					timeout : 1000 * 2
				});
			}
		}
	});
});
</script>
<!-- <style>
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
		font-size: 14px;
	}
</style> -->
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="form" method="post">
			<fieldset>
				<legend><img src="css/extend/fromedit.png" style="margin-bottom: -3px;"/> BUG编辑</legend>
				<input name="bugId" id="bugId"  type="hidden"/>
				<input name="created" id="created"  type="hidden"/>
				<input name="creater" id="creater"  type="hidden"/>
				<input name="status" id="status"  type="hidden"/>
				 <table>
					 <tr>
					    <th>BUG名称</th>
						<td><input name="bugName" id="bugName" placeholder="请输入BUG名称" class="easyui-textbox easyui-validatebox" data-options="required:true" type="text"/></td>
						<th>BUG简要</th>
						<td><input name="attachmentUrl" id="attachmentUrl" type="text"  class="easyui-textbox easyui-validatebox" data-options="required:true"/></td>
					 </tr>
					 <tr>
						<th>BUG附件描述</th>
						<td colspan="3"><textarea class="xheditor" id="description" name="description"  style="width: 420px;height: 180px;"></textarea></td>
					</tr>
				 </table>
			</fieldset>
		</form>
	</div>
