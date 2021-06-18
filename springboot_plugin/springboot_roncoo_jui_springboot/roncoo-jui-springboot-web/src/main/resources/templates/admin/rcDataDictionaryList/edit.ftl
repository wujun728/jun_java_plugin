<#include "/macro/base.ftl" />
<div class="pageContent">
	<form method="post" action="${base}/admin/rcDataDictionaryList/update" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<input name="id" value="${bean.id!}" type="hidden"/>
			<p>
				<label>CODE值：</label>
				<input name="fieldCode" readonly="readonly" type="text" size="20" value="${bean.fieldCode!}" />
			</p>
			<p>
				<label>字段KEY：</label>
				<input name="fieldKey" class="required" type="text" size="20" value="${bean.fieldKey}" alt="请输入字段KEY"/>
			</p>
			<p>
				<label>字段VALUE：</label>
				<input name="fieldValue" class="required" type="text" size="20" value="${bean.fieldValue}" alt="请输入字段VALUE"/>
			</p>
			<p>
				<label>排序：</label>
				<input name="sort" class="required digits" type="text" size="20" value="${bean.sort}" />
			</p>
			<p>
				<label>备注：</label>
				<textarea name="remark" cols="20" rows="2">${bean.remark}</textarea>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
