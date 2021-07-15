<%@ page language="java" import="java.util.*,com.jun.plugin.mybatisplus.pool.*,com.jun.plugin.mybatisplus.stat.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	var datagrid;
	var sqlInfoDialog;
	var sqlInfoDataGrid;
	$(function() {

		datagrid = $('#datagrid').datagrid({
			fit : true,
			title : 'SQL语句监控',
			fitColumns : true,
			singleSelect : true,
			border : false,
			rownumbers : true,
			onClickRow : function(rowIndex, rowData) {
				sqlInfoDataGrid.datagrid('loadData', [ {
					value : '数据源名称',
					name : rowData.Name
				}, {
					value : 'SQL语句',
					name : rowData.SQL
				}, {
					value : 'ExecuteLastStartTime',
					name : rowData.ExecuteLastStartTime
				}, {
					value : '执行次数',
					name : rowData.ExecuteCount
				}, {
					value : '读取行数',
					name : rowData.FetchRowCount
				}, {
					value : 'ExecuteMillisTotal',
					name : rowData.ExecuteMillisTotal
				}, {
					value : 'ResultSetHoldTime',
					name : rowData.ResultSetHoldTime
				}, {
					value : '执行错误数',
					name : rowData.ErrorCount
				} ]);
				sqlInfoDialog.dialog('open');
				$(this).datagrid('unselectAll');
			}
		});

		sqlInfoDialog = $('#sqlInfoDialog').show().dialog({
			modal : true,
			closed : true,
			title : '明细'
		});

		sqlInfoDataGrid = $('#sqlInfoDataGrid').datagrid({
			showHeader : false,
			fit : true,
			fitColumns : true,
			scrollbarSize : 0,
			border : false,
			nowrap : false,
			columns : [ [ {
				title : '',
				width : 100,
				field : 'value'
			}, {
				title : '',
				width : 150,
				field : 'name'
			} ] ]
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
					<th field="ExecuteLastStartTime">ExecuteLastStartTime</th>
					<th field="ExecuteCount">执行次数</th>
					<th field="FetchRowCount">读取行数</th>
					<th field="ExecuteMillisTotal">ExecuteMillisTotal</th>
					<th field="ResultSetHoldTime">ResultSetHoldTime</th>
					<th field="ErrorCount">执行错误数</th>
					<th field="Name">数据源名称</th>
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

	<div id="sqlInfoDialog" style="display: none;width: 500px;height: 350px;">
		<table id="sqlInfoDataGrid"></table>
	</div>
</body>
</html>
