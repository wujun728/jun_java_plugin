<#include "/macro/base.ftl" />
<div class="pageContent">
	<form method="post" action="${base}/admin/rcDataDictionaryList/save" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<p>
				<label>CODE值：</label>
				<input name="fieldCode" readonly="readonly" type="text" size="20" value="${fieldCode!}" />
			</p>
			<p>
				<label>字段KEY：</label>
				<input name="fieldKey" class="required" type="text" size="20" value="" alt="请输入字段KEY"/>
			</p>
			<p>
				<label>字段VALUE：</label>
				<input name="fieldValue" class="required" type="text" size="20" value="" alt="请输入字段VALUE"/>
			</p>
			<p>
				<label>备注：</label>
				<textarea name="remark" cols="20" rows="2"></textarea>
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
