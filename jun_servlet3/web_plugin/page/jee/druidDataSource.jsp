<%@ page language="java" import="java.util.*,com.alibaba.druid.pool.*,com.alibaba.druid.stat.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="overflow: hidden;">
		<table class="easyui-datagrid" fit="true" border="false" showHeader="false" fitColumns="true" nowrap="false">
			<thead>
				<tr>
					<th field="value" width="150"></th>
					<th field="name" width="150"></th>
				</tr>
			</thead>
			<tbody>
				<%
					Set<DruidDataSource> dataSources = DruidDataSourceStatManager.getDruidDataSourceInstances();
					Iterator<DruidDataSource> druidDataSourceIterator = dataSources.iterator();
					while (druidDataSourceIterator.hasNext()) {
						DruidDataSource druidDataSource = druidDataSourceIterator.next();
						out.println("<tr><td>数据源的名称</td><td>" + druidDataSource.getName() + "</td>");
						out.println("<tr><td>数据源创建的时间</td><td>" + druidDataSource.getCreatedTime() + "</td>");
						out.println("<tr><td>数据源的JdbcURL</td><td>" + druidDataSource.getUrl() + "</td>");
						out.println("<tr><td>物理连接创建次数</td><td>" + druidDataSource.getCreateCount() + "</td>");
						out.println("<tr><td>物理连接关闭数量</td><td>" + druidDataSource.getDestroyCount() + "</td>");
						out.println("<tr><td>申请连接的次数</td><td>" + druidDataSource.getConnectCount() + "</td>");
						out.println("<tr><td>关闭连接的次数</td><td>" + druidDataSource.getCloseCount() + "</td>");
						out.println("<tr><td>正在打开连接的数量</td><td>" + druidDataSource.getActiveCount() + "</td>");
						out.println("<tr><td>正在空闲连接的数量</td><td>" + druidDataSource.getPoolingCount() + "</td>");
						out.println("<tr><td>获取连接时锁的的等待队列长度，如果这个指标数值偏大，那说明DruidDataSource存在问题，请联系我们</td><td>" + druidDataSource.getLockQueueLength() + "</td>");
						out.println("<tr><td>正在等待获得连接的连接数量，如果着指标数值偏大，说明连接池的最大值可能偏小了</td><td>" + druidDataSource.getWaitThreadCount() + "</td>");
						out.println("<tr><td>初始连接池大小</td><td>" + druidDataSource.getInitialSize() + "</td>");
						out.println("<tr><td>连接池最大值</td><td>" + druidDataSource.getMaxActive() + "</td>");
						out.println("<tr><td>连接池最小值</td><td>" + druidDataSource.getMinIdle() + "</td>");
						out.println("<tr><td>是否缓存PreparedStatement</td><td>" + druidDataSource.getPreparedStatementCount() + "</td>");
						out.println("<tr><td>关闭连接的空闲时间</td><td>" + druidDataSource.getMinEvictableIdleTimeMillis() + "</td>");
						out.println("<tr><td>建立物理连接错误数</td><td>" + druidDataSource.getConnectErrorCount() + "</td>");
						out.println("<tr><td>连接物理连接耗费的时间（毫秒）</td><td>" + druidDataSource.getCreateTimespanMillis() + "</td>");
						out.println("<tr><td>数据库类型</td><td>" + druidDataSource.getDbType() + "</td>");
						out.println("<tr><td>检测连接是否有效的SQL</td><td>" + druidDataSource.getValidationQuery() + "</td>");
						out.println("<tr><td>检测连接是否有效的超时时间</td><td>" + druidDataSource.getValidationQueryTimeout() + "</td>");
						out.println("<tr><td>Jdbc驱动的名称</td><td>" + druidDataSource.getDriverClassName() + "</td>");
						out.println("<tr><td>用户名称</td><td>" + druidDataSource.getUsername() + "</td>");
						out.println("<tr><td>关闭已“丢弃”连接的数量，如果RemoveAbandoned配置为false，这个指标值会一直为0</td><td>" + druidDataSource.getRemoveAbandonedCount() + "</td>");
						out.println("<tr><td>等待非空次数</td><td>" + druidDataSource.getNotEmptyWaitCount() + "</td>");
						out.println("<tr><td>等待非空耗费的毫秒数</td><td>" + druidDataSource.getNotEmptyWaitMillis() + "</td>");
						out.println("<tr><td>启动事务数量</td><td>" + druidDataSource.getStartTransactionCount() + "</td>");
						out.println("<tr><td>提交数量</td><td>" + druidDataSource.getCommitCount() + "</td>");
						out.println("<tr><td>回滚数量</td><td>" + druidDataSource.getRollbackCount() + "</td>");
						out.println("<tr><td>错误数量</td><td>" + druidDataSource.getErrorCount() + "</td>");
					}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>
