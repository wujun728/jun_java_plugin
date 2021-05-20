<%@ page language="java"  pageEncoding="GBK"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
      	String userCode = request.getAttribute("userCode").toString();
      	String deptSql = "";
      	String flag = "1";
%>

<html>
	<head>

	<title>列表</title>
	<link rel="stylesheet" href="/ptxtag/css/default.css" />
	<script type="text/javascript" src="/ptxtag/js/ptxtag.easyui.js"></script>
	<script type="text/javascript" src="/ptxtag/js/common.js"></script>
	<script type="text/javascript">
			var basePath = "<%=basePath%>";
	</script>
	<script type="text/javascript" src="<%=basePath%>/template/js/list.js"></script>
	</head>

	<body scroll="no">
		<div style="background-color: #F1F6FF;">
			<table cellpadding="0" cellspacing="0"
				style="margin: 0px; text-align: left; vertical-align: middle;">
				<tr height="30px">
					<td style="width: 60px; text-align: right; padding: 0 5px 0 0;">
						项目名称
					</td>
					<td style="width: 200px;">
						<input type="text" id="pjName" style="width: 100%;" value="" />
					</td>
					<td style="width: 60px; text-align: right; padding: 0 5px 0 0;">
						项目编码
					</td>
					<td style="width: 150px;">
						<input type="text" id="pjCode" style="width: 100%;" value="" />
					</td>
					<td style="width: 60px; text-align: right; padding: 0 5px 0 0;">
						承做部门
					</td>
					<td style="width: 155px;">
						<ptx:Combobox id="deptCode" name="deptCode" valueField="ID"
							textField="TEXT" style="width:150px" panelHeight="200px"
							sql="<%=deptSql%>" editable="false" />
					</td>
					<td style="width: 75px; text-align: center;">
						<label>
							<input type="button" name="btSearch"
								value="&nbsp;&nbsp;查询&nbsp;&nbsp;" onclick="shInfoList(1);"
								class="button" />
						</label>
					</td>
				</tr>
			</table>
		</div>
		<table id="infoList"></table>
	</body>
</html>
