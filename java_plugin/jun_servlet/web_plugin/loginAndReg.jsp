<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" charset="UTF-8">
	var loginAndRegDialog;
	var regDialog;
	var loginTabs;
	var loginInputForm;
	var regForm;
	var loginDatagridName;
	var loginDatagridForm;
	var loginComboboxForm;
	var loginComboboxName;
	$(function() {

		loginInputForm = $('#loginInputForm').form({
			url : 'userController.do?login',
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					loginAndRegDialog.dialog('close');
					$('#indexLayout').layout('panel', 'center').panel('setTitle', sy.fs('[{0}]，欢迎您！[{1}]', d.obj.name, d.obj.ip));
				} else {
					loginInputForm.find('input[name=password]').focus();
				}
				$.messager.show({
					msg : d.msg,
					title : '提示'
				});
			}
		});

		loginInputForm.find('input').on('keyup', function(event) {/* 增加回车提交功能 */
			if (event.keyCode == '13') {
				loginInputForm.submit();
			}
		});

		regForm = $('#regForm').form({
			url : 'userController.do?reg',
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					regDialog.dialog('close');
					loginInputForm.find('input[name=name]').val(regForm.find('input[name=name]').val());
					loginInputForm.find('input[name=password]').val(regForm.find('input[name=password]').val());
					loginInputForm.submit();
				} else {
					regForm.find('input[name=name]').focus();
				}
				$.messager.show({
					msg : d.msg,
					title : '提示'
				});
			}
		});

		regForm.find('input').on('keyup', function(event) {/* 增加回车提交功能 */
			if (event.keyCode == '13') {
				regForm.submit();
			}
		});

		regDialog = $('#regDialog').show().dialog({
			modal : true,
			title : '注册',
			closed : true,
			buttons : [ {
				text : '注册',
				handler : function() {
					regForm.submit();
				}
			} ],
			onOpen : function() {
				setTimeout(function() {
					regForm.find('input[name=name]').focus();
				}, 1);
			},
			onClose : function() {
				loginTabs.tabs('select', 0);
			}
		});

		loginDatagridForm = $('#loginDatagridForm').form({
			url : 'userController.do?login',
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					loginAndRegDialog.dialog('close');
					$('#indexLayout').layout('panel', 'center').panel('setTitle', sy.fs('[{0}]，欢迎您！[{1}]', d.obj.name, d.obj.ip));
				} else {
					loginDatagridForm.find('input[name=password]').focus();
				}
				$.messager.show({
					msg : d.msg,
					title : '提示'
				});
			}
		});

		loginDatagridName = $('#loginDatagridName').show().combogrid({
			loadMsg : '数据加载中....',
			panelWidth : 440,
			panelHeight : 180,
			required : true,
			fitColumns : true,
			value : '',
			idField : 'name',
			textField : 'name',
			mode : 'remote',
			url : 'userController.do?loginDatagrid',
			pagination : true,
			pageSize : 5,
			pageList : [ 5, 10 ],
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 60,
				hidden : true
			}, {
				field : 'name',
				title : '登录名',
				width : 100
			}, {
				field : 'createdatetime',
				title : '创建时间',
				width : 150
			}, {
				field : 'modifydatetime',
				title : '最后修改时间',
				width : 150
			} ] ],
			delay : 500,
			keyHandler : $.extend($.fn.combo.defaults.keyHandler, {
				query : function(q) {
					loginDatagridName.combogrid('grid').datagrid('load', {
						name : q
					});
				}
			})
		});

		loginDatagridForm.find('input,.combo-text').on('keyup', function(event) {/* 增加回车提交功能 */
			if (event.keyCode == '13') {
				loginDatagridForm.submit();
			}
		});

		loginComboboxForm = $('#loginComboboxForm').form({
			url : 'userController.do?login',
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					loginAndRegDialog.dialog('close');
					$('#indexLayout').layout('panel', 'center').panel('setTitle', sy.fs('[{0}]，欢迎您！[{1}]', d.obj.name, d.obj.ip));
				} else {
					loginComboboxForm.find('input[name=password]').focus();
				}
				$.messager.show({
					msg : d.msg,
					title : '提示'
				});
			}
		});

		loginComboboxName = $('#loginComboboxName').show().combobox({
			required : true,
			url : 'userController.do?loginCombobox',
			textField : 'name',
			valueField : 'name',
			mode : 'remote',
			panelHeight : 'auto',
			delay : 500,
			value : ''
		});

		loginComboboxForm.find('[name=password],.combo-text').bind('keyup', function(event) {/* 增加回车提交功能 */
			if (event.keyCode == '13') {
				loginComboboxForm.submit();
			}
		});

		loginTabs = $('#loginTabs').tabs({
			fit : true,
			border : false,
			onSelect : function(title) {
				if (title == '输入方式') {
					setTimeout(function() {
						loginInputForm.find('input[name=name]').focus();
					}, 1);
				} else if (title == '表格方式') {
					setTimeout(function() {
						loginDatagridName.combogrid('textbox').focus();
					}, 1);
				} else if (title == '补全方式') {
					setTimeout(function() {
						loginComboboxName.combobox('textbox').focus();
					}, 1);
				}

			}
		});

		loginAndRegDialog = $('#loginAndRegDialog').show().dialog({
			modal : true,
			title : '系统登录',
			closable : false,
			buttons : [ {
				id : 'btnReg',
				text : '注册',
				handler : function() {
					regDialog.dialog('open');
				}
			}, {
				id : 'btnLogin',
				text : '登录',
				handler : function() {
					var tab = loginTabs.tabs('getSelected');
					var f = tab.find('form');
					f.submit();
				}
			} ]
		});

	});
</script>
<div id="loginAndRegDialog" style="width: 250px;height: 170px;display: none;">
	<div id="loginTabs">
		<div title="输入方式" align="center" style="overflow: hidden;padding: 5px;">
			<form id="loginInputForm" method="post">
				<table class="tableForm">
					<tr>
						<th>登录名</th>
						<td><input name="name" class="easyui-validatebox" required="true" value="" /></td>
					</tr>
					<tr>
						<th>密码</th>
						<td><input name="password" type="password" class="easyui-validatebox" required="true" value="" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div title="表格方式" style="overflow: hidden;padding: 5px;">
			<form id="loginDatagridForm" method="post">
				<table class="tableForm">
					<tr>
						<th style="width: 50px;">登录名</th>
						<td><select id="loginDatagridName" name="name" style="display: none;width:157px;"></select>
						</td>
					</tr>
					<tr>
						<th>密码</th>
						<td><input name="password" type="password" class="easyui-validatebox" required="true" value="" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div title="补全方式" style="overflow: hidden;padding: 5px;">
			<form id="loginComboboxForm" method="post">
				<table class="tableForm">
					<tr>
						<th style="width: 50px;">登录名</th>
						<td><select id="loginComboboxName" name="name" style="display: none;width:157px;"></select>
						</td>
					</tr>
					<tr>
						<th>密码</th>
						<td><input name="password" type="password" class="easyui-validatebox" required="true" value="" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<div id="regDialog" style="width:250px;display: none;padding: 5px;" align="center">
	<form id="regForm" method="post">
		<table class="tableForm">
			<tr>
				<th>登录名</th>
				<td><input name="name" class="easyui-validatebox" required="true" /></td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input name="password" type="password" class="easyui-validatebox" required="true" /></td>
			</tr>
			<tr>
				<th>重复密码</th>
				<td><input name="rePassword" type="password" class="easyui-validatebox" required="true" validType="eqPassword['#regForm input[name=password]']" /></td>
			</tr>
		</table>
	</form>
</div>