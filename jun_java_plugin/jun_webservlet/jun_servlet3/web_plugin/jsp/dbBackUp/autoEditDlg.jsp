<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript" src="jsp/dbBackUp/js/autoEditDlg.js"></script>
<link rel="stylesheet" href="jsp/dbBackUp/css/autoEditDlg.css" type="text/css"></link>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="form" method="post">
			<fieldset>
				<legend><img src="extend/fromedit.png" style="margin-bottom: -3px;"/> 手动备份服务</legend>
				 <div class="well well-small">
				<span class="badge">说明</span>
				<p>
					手动备份并不<span class="label-info"><strong>影响定时备份服务</strong></span>，定时备份会根据配置<span class="label-info"><strong>正常进行!</strong></span>
				</p>
		</div>
			</fieldset>
		</form>
	</div>
</div>
