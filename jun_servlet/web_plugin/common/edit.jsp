<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> --%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> --%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path;
    request.setAttribute("basePath", basePath);
    Map actDefMap = (Map)request.getAttribute("actDefMap");
    //Map actMap = (Map)request.getAttribute("actMap");
   // String actName = "";
    //if(!actMap.isEmpty()){
    //	actName = (String)actMap.get("ACTNAME");
    //}
    String exeFlag = request.getAttribute("exeFlag").toString();
    String biCode = request.getAttribute("biCode").toString();
    String actCode = request.getAttribute("actCode").toString();
    String billId = request.getAttribute("billId").toString();
    
    String toolbar = "[";
    if ("1".equals(exeFlag)
            && ("".equals(biCode) || "fillForm".equals(actCode))) {
        toolbar += "{id:'fileAdd', text:'上传', iconCls:'icon-upload', handler:uploadFile},"
                + "{id:'fileDel', text:'删除', iconCls:'icon-delete', handler:delFile},";
    }
    toolbar += "{id:'fileDown', text:'下载', iconCls:'icon-download', handler:downloadFile}]";
    String sqlStr = "SELECT BUSSCODE AS id,LPAD('　', (LEVEL - 1) * 4, '　') || BUSSNAME AS text"
            + " FROM DEF_BUSSTYPE WHERE ISUSE = 1 START WITH PBUSSCODE = 0"
            + " CONNECT BY PRIOR BUSSCODE = PBUSSCODE";
    //业务类别为机会已评估及之后状态的客户
    String cusSql = "SELECT A.CUSCODE AS id , A.CUSNAME AS text FROM CM_CUSTOMER A"
            + " WHERE A.CHANGFLAG > 0";
%>

<html>
	<head>

		<title>初步业务活动编辑</title>
		
	<link rel="stylesheet" href="/ptxtag/css/default.css" />
	<script type="text/javascript">
		var basePath = "${requestScope.basePath}"; 
		var id = "${dMap.ID}";
		var bname=btNname;
	</script>
	<script type="text/javascript" src="<%=basePath%>/template/js/edit.js"></script>
	</head>
	<body style="background-color: #FAFCFF;">
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
          <td style="text-align:right;width:30%">
              <%if(!"0".equals(exeFlag.trim())&&biCode!="") {%>
            	<a href="#" class="easyui-linkbutton" id="ok" plain="true" iconCls="icon-ok" onclick="commitMatter();">
            	${requestScope.actMap.BTNNAME}</a>
      		<%}  %>
            	<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="loadAuditInfoBase();">审核情况</a>
          </td>
        </tr>
      </table>
		<div style="width: 100%; text-align: center; font: bold 15px sans-serif; padding: 15px;">
			初步业务活动程序表
		</div>

		<div id="mainDiv"
			style="width: 100%; height: 300px; text-align: center; overflow: auto;">
			<table id="infoTable1" style="width: 98%; margin-bottom: 5px;table-layout:fixed;">
				<tr height="40px"  valign="top">
					<td width="9%"  align="right" >
						被审计单位：
					</td>
					<td width="25%" >
						<input type="hidden" id="khbm" name="khbm" value="${dMap.KHBM}" />
						<input type="text" id="khmc" name="khmc" style="width: 150px;"
							value="${dMap.KHMC}" readonly="readonly" /><input type="button" value="选择" name="selProject"
							class="BUTTON_UP" style="width: 35px;" onclick="selProject();" />&nbsp;<font color="red">*</font>
							<div  style="background-color:#FF00FF;width: 205px;">如果没找到被审计单位,请进行项目委托!</div>
					</td>
					<td width="8%"  align="right" >
						业务类别：
					</td>
					<td width="25%">
						<ptx:Combobox id="ywlb" name="ywlb" style="width:185px;"
							sql="<%=sqlStr%>" panelHeight="200" value="01"
							editable="false" disabled="true" />
					</td>
					<td width="9%"   align="right"  >
						索引号：
					</td>
					<td>
						<input type="text" id="sybh" name="sybh" style="width:185px;"
							onKeyDown="LimitText(this, 16)" onKeyUp="LimitText(this, 16)"
							onkeypress="LimitText(this, 16)" onpaste="LimitText(this, 16)" onblur="this.value = stripscript(this.value);"
							value="${dMap.SYBH}" />
					</td>
				</tr>
				<tr>
					<td align="right" >
						项目名称：
					</td>
					<td>
						<input type="hidden" id="xmbm" name="xmbm" value="${dMap.XMBM}" />
						<input type="text" id="xmmc" name="xmmc" style="width:185px;"
							onKeyDown="LimitText(this, 128)" onKeyUp="LimitText(this, 128)"
							onkeypress="LimitText(this, 128)" onpaste="LimitText(this, 128)"
							value="${dMap.XMMC}" readonly="readonly" />
					</td>
					<td align="right" >
						财务报截&nbsp;&nbsp;&nbsp;&nbsp;<br/>止日/期间：
					</td>
					<td>
						<input type="hidden" id="ksrq" name="ksrq" value="${dMap.KSRQ}" />
<!-- 						<input type="text" id="jzrq" name="jzrq" style="width:185px;" -->
<!-- 							class="Wdate" value="${fn:substring(dMap.JZRQ, 0, 10)}" -->
<!-- 							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true});" -->
<!-- 							readonly="readonly" /> -->
							&nbsp;<font color="red">*</font>
					</td>
					<td width="9%"  align="right" >
						编制人员：
					</td>
					<td>
						<input type="hidden" id="bzrybm" name="bzrybm"
							value="${biCode eq '' ? userCode : dMap.BZRYBM}" />
						<input type="text" id="bzrymc" name="bzrymc" style="width:185px;"
							value="${biCode eq '' ? userName : dMap.BZRYMC}"
							readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right" >
						编制日期：
					</td>
					<td>
						<input type="hidden" id="bzrq" name="bzrq"
							value="${biCode eq '' ? now : dMap.BZRQ}" />
<!-- 						<input type="text" id="bzrqView" name="bzrqView" -->
<!-- 							style="width:185px;" class="Wdate" -->
<!-- 							value="${fn:substring(biCode eq '' ? now : dMap.BZRQ, 0, 10)}" -->
<!-- 							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true});" readonly="readonly" -->
<!-- 							 /> -->
					</td>
					<td align="right" >
						复核日期：
					</td>
					<td>
						<input type="hidden" id="fhrq" name="fhrq"
							value="${actCode eq 'audit' && exeFlag eq '1' ? now : dMap.FHRQ}" />
<!-- 						<input type="text" id="fhrqView" name="fhrqView" -->
<!-- 							style="width:185px;" class="Wdate" -->
<!-- 							value="${fn:substring(actCode eq 'audit' && exeFlag eq '1' ? now : dMap.FHRQ, 0, 10)}" -->
<!-- 							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true});" -->
<!-- 							readonly="readonly" /> -->
					</td>
					<td style="font-weight: bold;">
						<!-- 复核人员： -->
						<input type="hidden" id="fhrybm" name="fhrybm"
							value="${actCode eq 'audit' && exeFlag eq '1' ? userCode : dMap.FHRYBM}" />
						<input type="hidden" id="fhrymc" name="fhrymc" style="width:185px;"
							value="${actCode eq 'audit' && exeFlag eq '1' ? userName : dMap.FHRYMC}"
							readonly="readonly" />
					</td>
					<%if(!billId.equals("")){%>
					<td style="width:15%;text-align: left;">
					  <label>
					   <input type="button" name="queryInfo" style="width:185px;" value="&nbsp;&nbsp;&nbsp;&nbsp;查看项目委托信息&nbsp;&nbsp;&nbsp;&nbsp;" onclick="show();" class="button" />
					  </label>
					</td>
					<%}  %>
				</tr>
			</table>

			<table id="infoTable2" style="width: 98%;table-layout:fixed;">
				<tr>
					<td style="font-weight: bold;">
						一、注册会计师的目标：
					</td>
				</tr>
				<tr>
					<td>
						${biCode eq '' ? dataList[0].ZKMB : dMap.ZKMB}
					</td>
				</tr>
				<tr height="5px">
					<td></td>
				</tr>
				<tr>
					<td style="font-weight: bold;">
						二、审计工作核对表
					</td>
				</tr>
			</table>

			<table id="detailTable" class="mimicHtc" cellpadding="1"
				cellspacing="1" style="width: 98%;table-layout:fixed;">
				<tr class="mimicHtc_tr">
					<th class="mimicHtc_item" width="50%" >
						初步业务活动程序
					</th>
					<th class="mimicHtc_item" width="10%" >
						索引号
					</th>
					<th class="mimicHtc_item" >
						执行人
					</th>
				</tr>
				<c:forEach var="data" items="${dataList}" varStatus="status" >
					<tr class="mimicHtc_tr"  style="height:40px;">
						<td ><span style="width:99.8%; height:40px;overflow:auto;" >${data.HDNR}</span></td>
						<td style="background-color: #C9F1C9;" >
							<input type="text" name="sybh" value="${data.SYBH}" class="centerInput"
								onKeyDown="LimitText(this, 16)" onKeyUp="LimitText(this, 16)"
								onkeypress="LimitText(this, 16)" onpaste="LimitText(this, 16)"  onblur="this.value = stripscript(this.value);"/>
						</td>
						<td>
							<input type="hidden" id="hdid${status.index}"
								name="hdid" value="${data.HDID}" /> <input type="hidden"
								id="pid${status.index}" name="pid" value="${data.PID}" /> 
							<input type="hidden" id="isend${status.index}" name="isend"
								value="${data.ISEND}" />
							<input type="hidden" id="zxrybm${status.index}" name="zxrybm" 
								value="${data.ZXRYBM}"/>
							<input type="text" id="zxrymc${status.index}" name="zxrymc" 
								value="${data.ZXRYMC}" class="centerInput" style="width: 60%;"
								readonly="readonly" />
								<label>
								<input type="button" value="&nbsp;&nbsp;&nbsp;不适用&nbsp;&nbsp;&nbsp;" name="unActor" 
								class="button" style="width: 40px;"
								onclick="unActor(${status.index})" />
								
								</label>
								<label>
								&nbsp;&nbsp;<input type="button"
								value="&nbsp;&nbsp;选择&nbsp;&nbsp;" name="selActor" class="button" style="width: 40px;"
								onclick="selActor(${status.index})" />
								</label>
						</td>
					</tr>
					</c:forEach>
			</table>
		</div>
		<div style="width: 100%;">
			<table id="fileTable" width="100%">
				<tr class="table_title" height="25">
					<td>确认底稿</td>
				</tr>
				<tr>
					<td width="30%">
						<input type="checkbox" id="ywpjb" name="gzdg" value="1" />
						
						<c:choose>
						   <c:when test="${requestScope.dMap.UDTAKETYPE == '业务保持'}"> 
						   		<span id="dgText1" style="margin-right: 30px;"
							onclick="$('#ywpjb').click();">业务保持-评价表</span>
						   </c:when>
						   <c:otherwise> 
						   		<span id="dgText1" style="margin-right: 30px;"
							onclick="$('#ywpjb').click();">业务承接-评价表</span>
						   </c:otherwise>
	      				</c:choose>			
      			
							
						<input type="checkbox" id="qtgzdg" name="gzdg" value="1" />
						<span onclick="$('#qtgzdg').click();">其他工作底稿</span>
					</td>
				</tr>
				<tr>
					<td valign="top">
						<ptx:DataGrid border="true" id="fileList" pagination="false" height="120" singleSelect="false" remoteSort="false" striped="true" ondblclick="uploadFileLook" idField="IATTACHMENTID" toolbar="<%=toolbar%>">
							<ptx:DataGridRow>
								<ptx:DataGridField field="ck" title="   " checkbox="true" />
								<ptx:DataGridField field="SATTACHMENNAME" title="附件名称"
									width="300" align="center" dataAlign="left" />
								<ptx:DataGridField field="SREMARK" title="附件描述" width="400"
									align="center" dataAlign="left" />
								<ptx:DataGridField field="SUPSTAFFNAME" title="上传人员 "
									width="125" align="center" dataAlign="left" />
								<ptx:DataGridField field="SUPLOADTIME" title="上传时间 " width="125"
									align="center" dataAlign="center" />
								<ptx:DataGridField field="SSVRFILEPATH" title="服务器附件路径"
									hidden="true" />
								<ptx:DataGridField field="SSVRFILENAME" title="服务器附件名 "
									hidden="true" />
								<ptx:DataGridField field="ISTEMP" title="是否临时文件" hidden="true" />
								<ptx:DataGridField field="IATTACHMENTID" title="主键ID"
									hidden="true" />
							</ptx:DataGridRow>
						</ptx:DataGrid>
					</td>
				</tr>
			</table>
			<form action="<%=basePath%>/FileUpload" id="iForm" name="iForm"
				method="post" target="iFrame">
				<input type="hidden" id="ExeMethod" name="ExeMethod" value="" />
				<input type="hidden" id="isTempFile" name="isTempFile" value="" />
				<input type="hidden" id="newFileName" name="newFileName" value="" />
				<input type="hidden" id="oldFileName" name="oldFileName" value="" />
			</form>
			<iframe name="iFrame" id="iFrame" height="0px;" width="0px;"
				style="display: none"></iframe>
		</div>

		<div style="width: 100%;">
			<table style="width: 100%;">
				<tr>
					<td align="center" valign="middle" style="height: 40px; padding-bottom: 10px;">
						<c:if test="${requestScope.exeFlag == '1' &&('fillForm'==actCode||(biCode eq ''))}"><!-- && (biCode eq '') "filing"==actCode-->
							<label>
								<input type="button" name="btSave"
									value="&nbsp;&nbsp;保存&nbsp;&nbsp;" onclick="saveOrSubmit(0,0);"
									class="button" />
							</label>
						&nbsp;&nbsp;
						</c:if>
						<label>
							<input type="button" name="btClose"
								value="&nbsp;&nbsp;关闭&nbsp;&nbsp;" onclick="window.close();"
								class="button" />
						</label>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
