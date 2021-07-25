<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>任务列表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
		table{width: 90%;margin: 0 auto;text-align: center;border-right:1px solid #999;border-bottom: 1px solid #999;}
		table tr{}
		table td,table th{border-left: 1px solid #999;border-top: 1px solid #999;}
		.btn{border: 0;border-radius:4px;background-color: #428bca;color: #fff;padding: 3px 5px;cursor: pointer;}
	</style>
  </head>
  
  <body style="background: beige;">
  <div style="text-align: center;">
  	<span>欢迎${name }登录！</span>
  	<span><a href="${pageContext.request.contextPath }/user/logout">退出</a></span>
  </div>
  <h2 style="text-align: center;">任务列表</h2>
   <table id="table_report" class="table table-striped table-bordered table-hover" cellpadding="0" cellspacing="0">
                    <thead>
                        <tr>
                            <!-- th class="center">序号</th-->
                            <th class="center">任务组名称</th>
                            <th class="center">定时任务名称</th>
                            <!-- <th class="center">触发器组名称</th>
                            <th class="center">触发器名称</th> -->
                            <th class="center">时间表达式</th>
                            <th class="center">上次运行时间</th>
                            <th class="center">下次运行时间</th>
                            <th class="center">任务状态</th>
                            <!-- <th class="center">已经运行时间</th> -->
                            <!-- <th class="center">持续运行时间</th> -->
                            <th class="center">开始时间</th>
                            <th class="center">结束时间</th>
                            <th class="center">任务类名</th>
                            <!-- <th class="center">方法名称</th> -->
                            <!-- <th class="center">jobObject</th> -->
                            <!-- <th class="center">运行次数</th> -->
                            <th class="center" width="15%">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- 开始循环 -->
                        <c:choose>
                            <c:when test="${not empty jobInfos && jobInfos.size()>0}">
                                <c:forEach items="${jobInfos}" var="var" varStatus="vs">
                                    <tr>
                                        <td class='center' style="width: auto;">${var.jobGroup}</td>
                                        <td class='center' style="width: auto;">${var.jobName}</td>
                                        <%-- <td class='center' style="width: auto;">${var.triggerGroupName}</td>
                                        <td class='center' style="width: auto;">${var.triggerName}</td> --%>
                                        <td class='center' style="width: auto;">${var.cronExpr}</td>
                                        <td class='center' style="width: auto;"><fmt:formatDate value="${var.previousFireTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td class='center' style="width: auto;"><fmt:formatDate value="${var.nextFireTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td class='center' style="width: auto;">
                                            <c:if test="${var.jobStatus == 'NONE'}">
                                            <span class="label">未知</span>
                                            </c:if>
                                            <c:if test="${var.jobStatus == 'NORMAL'}">
                                            <span class="label label-success arrowed">正常运行</span>
                                            </c:if>
                                            <c:if test="${var.jobStatus == 'PAUSED'}">
                                            <span class="label label-warning">暂停状态</span>
                                            </c:if>
                                            <c:if test="${var.jobStatus == 'COMPLETE'}">
                                            <span class="label label-important arrowed-in">完成状态</span>
                                            </c:if>
                                            <c:if test="${var.jobStatus == 'ERROR'}">
                                            <span class="label label-info arrowed-in-right arrowed">错误状态</span>
                                            </c:if>
                                            <c:if test="${var.jobStatus == 'BLOCKED'}">
                                            <span class="label label-inverse">锁定状态</span>
                                            </c:if>
                                        </td>
                                        <%-- <td class='center' style="width: auto;">${var.runTimes}</td> --%>
                                        <%-- <td class='center' style="width: auto;">${var.duration}</td> --%>
                                        <td class='center' style="width: auto;"><fmt:formatDate value="${var.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td class='center' style="width: auto;"><fmt:formatDate value="${var.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td class='center' style="width: auto;">${var.jobClass}</td>
                                        <%-- <td class='center' style="width: auto;">${var.jobMethod}</td> --%>
                                        <%-- <td class='center' style="width: auto;">${var.jobObject}</td> --%>
                                        <%-- <td class='center' style="width: auto;">${var.count}</td> --%>
                                        <td class='center' style="width: auto;">
                                            <%-- <a class="btn btn-minier btn-info" onclick="triggerJob('${var.jobName}','${var.jobGroup}');"><i class="icon-edit"></i>运行</a> --%>
                                            <a class="btn btn-minier btn-success" onclick="edit('${var.jobName}','${var.jobGroup}');"><i class="icon-edit"></i>编辑</a><br>
                                            <a class="btn btn-minier btn-warning" onclick="pauseJob('${var.jobName}','${var.jobGroup}');"><i class="icon-edit"></i>暂停</a>
                                            <a class="btn btn-minier btn-purple" onclick="resumeJob('${var.jobName}','${var.jobGroup}');"><i class="icon-edit"></i>恢复</a>
                                            <a class="btn btn-minier btn-danger" onclick="deleteJob('${var.jobName}','${var.jobGroup}','${var.triggerName}','${var.triggerGroupName}');"><i class="icon-edit"></i>删除</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr class="main_info">
                                    <td colspan="100" class="center">没有相关数据</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
 
  <div style="width: 90%;margin: 0 auto;text-align: center;margin-top: 25px;">
  	<button type="button" onclick="add();" class="btn">新增任务</button>
  </div>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
  <script type="text/javascript">
  	var url = "${pageContext.request.contextPath}";
  	function add(){
  		window.location.href = url + "/quartz/toAdd";
  	}
  	
  	function edit(jobName,jobGroup){
  		window.location.href = url + "/quartz/toEdit?jobName="+jobName+"&jobGroup="+jobGroup;
  	}
  	
  //暂停任务
  	function pauseJob(jobName,jobGroupName){
  		$.post(url + "/quartz/pauseJob",{"jobName":jobName,"jobGroupName":jobGroupName},function(data){
  			if(data.status = 'success'){
  				window.location.href = window.location.href;
  			}else{
  				alert("操作失败，请刷新重新！");
  			}
  		});
  	}
  
  //恢复任务
  	function resumeJob(jobName,jobGroupName){
  		$.post(url + "/quartz/resumeJob",{"jobName":jobName,"jobGroupName":jobGroupName},function(data){
  			if(data.status = 'success'){
  				window.location.href = window.location.href;
  			}else{
  				alert("操作失败，请刷新重新！");
  			}
  		});
  	}
  	//删除
  	function deleteJob(jobName,jobGroupName,triggerName,triggerGroupName){
  		$.post(url + "/quartz/deleteJob",{"jobName":jobName,"jobGroupName":jobGroupName,"triggerName":triggerName,"triggerGroupName":triggerGroupName},
  				function(data){
  			if(data.status = 'success'){
  				window.location.href = window.location.href;
  			}else{
  				alert("操作失败，请刷新重新！");
  			}
  		});
  	}
  	
  	/* //执行任务
  	function triggerJob(a,b){
  		var url = "triggerJob";
  		var d = {jobName:a,jobGroupName:b};
  		$.post(url,d,function(data){
  			if(data.status = 'ok'){
  				window.location.href = window.location.href;
  			}
  		});
  	} */
  </script>
  </body>
</html>
