<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
	<script type="text/javascript" src="jsp/dbBackUp/js/timeEditDlg.js"></script>
	<link rel="stylesheet" href="jsp/dbBackUp/css/timeEditDlg.css" type="text/css"></link>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="form" method="post">
			<fieldset>
				<legend><img src="extend/fromedit.png" style="margin-bottom: -3px;"/> 定时备份服务</legend>
				<div class="well well-small">
				<span class="badge">说明</span>
				<p>
					定时备份数据任务执行频率为<span class="label-info"><strong>每天</strong></span>，时间(24小时制)，<span class="label-info"><strong>服务启动</strong></span>备份开始生效!
				</p>
		</div>
				 <table>
					 <tr>
					    <th>小时(H)</th>
						<td><input name="scheduleHour" id="scheduleHour"  class="easyui-textbox easyui-numberbox" data-options="min:1,max:24,required:true" type="text"/></td>
					    <th>分钟(M)</th>
						<td><input name="scheduleMinute" id="scheduleMinute"  class="easyui-textbox easyui-numberbox" data-options="min:1,max:59,required:true" type="text"/></td>
					 </tr>
				 </table>
			</fieldset>
		</form>
	</div>
</div>
