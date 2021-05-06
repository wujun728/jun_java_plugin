<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>

<html>
	<head>
	<title>初步业务活动监控</title>
	<script type="text/javascript">
		var basePath = "<%=basePath%>";
	</script>
	<script type="text/javascript" src="<%=basePath%>/template/js/control.js"></script>
	</head>
	<body scroll="no">
		<table id="ctrlTable" cellpadding="0" cellspacing="0"
			style="width: 100%; height: 100%; margin: 0px;">
			<tr>
				<td valign="top">
					<ptx:DataGrid id="controlMatterList" pagination="true"
						pageSize="25" pageList="[25,50,100]" singleSelect="true"
						remoteSort="false" striped="true" ondblclick="clickMatter"
						border="0"
						toolbar="[{text:'流程图',iconCls:'icon-photo',handler:showWfPic}]">
						<ptx:DataGridRow>
							<ptx:DataGridField field="ACTSTATENAME" width="100"
								align="center" title="办理进度" sortable="true" />
							<ptx:DataGridField field="BICODE" width="200" align="center"
								title="活动单号" dataAlign="left" sortable="true"/>
							<ptx:DataGridField field="KHMC" width="250" align="center"
								title="被审计单位" dataAlign="left" sortable="true" />
							<ptx:DataGridField field="BUSSNAME" width="120" align="center"
								title="业务类别" dataAlign="left" sortable="true" />
							<ptx:DataGridField field="SYBH" width="100" align="center"
								title="索引号" dataAlign="left" sortable="true"/>
							<ptx:DataGridField field="XMMC" width="250" align="center"
								title="项目名称" dataAlign="left" sortable="true"/>
							<ptx:DataGridField field="BZRYMC" width="120" align="center"
								title="编制人员" dataAlign="left" sortable="true"/>
							<ptx:DataGridField field="BZRQ" width="120" align="center"
								title="编制日期" dataAlign="center" sortable="true"/>
							<ptx:DataGridField field="FHRYMC" width="120" align="center"
								title="复核人员" dataAlign="left" sortable="true" />
							<ptx:DataGridField field="FHRQ" width="120" align="center"
								title="复核日期" dataAlign="center" sortable="true"/>

							<ptx:DataGridField field="BILLID" width="" title="单据ID"
								hidden="true" />
							<ptx:DataGridField field="THBZ" width="" title="退回标识"
								hidden="true" />
							<ptx:DataGridField field="WFINSTID" width="" title="流程实例ID"
								hidden="true" />
							<ptx:DataGridField field="ACTINSTID" width="" title="活动实例ID"
								hidden="true" />
							<ptx:DataGridField field="ACTCODE" width="" title="活动定义编码"
								hidden="true" />
							<ptx:DataGridField field="YNCODE" width="" title="编辑权限"
								hidden="true" />
							<ptx:DataGridField field="QYJDID" width="" title="所属机构"
								hidden="true" />
						</ptx:DataGridRow>
					</ptx:DataGrid>
				</td>
			</tr>
		</table>
	</body>
</html>
