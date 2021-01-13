<%@ page language="java" import="java.util.*,com.alibaba.druid.pool.*,com.alibaba.druid.stat.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	var datagrid;
	var mxDialog;
	var mxForm;
	$(function() {
		mxForm = $('#mxForm').form();
		mxDialog = $('#mxDialog').dialog({
			title : '明细',
			closed : true,
			width : 500,
			height : 400,
			modal : true
		});

		datagrid = $('#datagrid').datagrid({
			fit : true,
			title : 'SQL语句监控',
			fitColumns : false,
			singleSelect : true,
			border : false,
			rownumbers : true,
			onClickRow : function(rowIndex, rowData) {
				mxDialog.find('[name=SQL]').html(rowData.SQL);
				mxDialog.find('[name=ExecuteLastStartTime]').html(rowData.ExecuteLastStartTime);
				mxDialog.find('[name=ExecuteCount]').html(rowData.ExecuteCount);
				mxDialog.find('[name=FetchRowCount]').html(rowData.FetchRowCount);
				mxDialog.find('[name=ExecuteMillisTotal]').html(rowData.ExecuteMillisTotal);
				mxDialog.find('[name=ResultSetHoldTime]').html(rowData.ResultSetHoldTime);
				mxDialog.find('[name=ErrorCount]').html(rowData.ErrorCount);
				mxDialog.find('[name=Name]').html(rowData.Name);
				mxDialog.dialog('open');
			}
		});
	});
</script>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="overflow: hidden;">
		<table id="datagrid">
			<thead>
				<tr>
					<th field="SQL" width="300">SQL语句</th>
					<th field="ExecuteLastStartTime" width="150">ExecuteLastStartTime</th>
					<th field="ExecuteCount" width="150">执行次数</th>
					<th field="FetchRowCount" width="150">读取行数</th>
					<th field="ExecuteMillisTotal" width="150">ExecuteMillisTotal</th>
					<th field="ResultSetHoldTime" width="150">ResultSetHoldTime</th>
					<th field="ErrorCount" width="150">执行错误数</th>
					<th field="Name" width="150">数据源名称</th>
				</tr>
			</thead>
			<tbody>
				<%
					Set<DruidDataSource> dataSources = DruidDataSourceStatManager.getDruidDataSourceInstances();
					Iterator<DruidDataSource> druidDataSourceIterator = dataSources.iterator();
					while (druidDataSourceIterator.hasNext()) {
						DruidDataSource druidDataSource = druidDataSourceIterator.next();
						JdbcDataSourceStat jdbcDataSourceStat = druidDataSource.getDataSourceStat();
						Map<String, JdbcSqlStat> sqlStatMap = jdbcDataSourceStat.getSqlStatMap();
						for (Object o : sqlStatMap.keySet()) {
							out.println("<tr>");
							out.println("<td>" + o + "</td>");
							JdbcSqlStat stat = sqlStatMap.get(o);
							out.println("<td>" + stat.getExecuteLastStartTime() + "</td>");
							out.println("<td>" + stat.getExecuteCount() + "</td>");
							out.println("<td>" + stat.getFetchRowCount() + "</td>");
							out.println("<td>" + stat.getExecuteMillisTotal() + "</td>");
							out.println("<td>" + stat.getResultSetHoldTimeMilis() + "</td>");
							out.println("<td>" + stat.getErrorCount() + "</td>");
							out.println("<td>" + druidDataSource.getName() + "</td>");
							out.println("</tr>");
						}
					}
				%>
			</tbody>
		</table>
	</div>

	<div id="mxDialog">
		<table class="tableCss" style="width: 100%;">
			<tr>
				<th>SQL</th>
				<td name="SQL"></td>
			</tr>
			<tr>
				<th>ExecuteLastStartTime</th>
				<td name="ExecuteLastStartTime"></td>
			</tr>
			<tr>
				<th>执行次数</th>
				<td name="ExecuteCount"></td>
			</tr>
			<tr>
				<th>读取行数</th>
				<td name="FetchRowCount"></td>
			</tr>
			<tr>
				<th>ExecuteMillisTotal</th>
				<td name="ExecuteMillisTotal"></td>
			</tr>
			<tr>
				<th>ResultSetHoldTime</th>
				<td name="ResultSetHoldTime"></td>
			</tr>
			<tr>
				<th>执行错误数</th>
				<td name="ErrorCount"></td>
			</tr>
			<tr>
				<th>数据源名称</th>
				<td name="Name"></td>
			</tr>
		</table>
	</div>
</body>
</html>
