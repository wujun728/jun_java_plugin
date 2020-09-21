<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="/WEB-INF/jsp/pubtag.jsp"%>
    <link rel="stylesheet" type="text/css" href="${BASE_PATH}/css/jquery-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="${BASE_PATH}/css/jquery.multiselect.css" />
    <link rel="stylesheet" type="text/css" href="${BASE_PATH}/css/jquery.multiselect.filter.css" />
</head>
<body>

<div>
    <button class="ui-button" onclick="refresh()">刷新配置</button>
</div>

<div style="width:60%;padding-left:20%;padding-top:50px">
   <div style="float:left">
		<select id="xaleft" multiple="multiple" name="ds1[]">
		    <c:forEach items="${interceptors}" var="icItem">
		      <option title="${icItem.mark}" value="${icItem.interceptorBundle}(${icItem.interceptorVersion})-${icItem.interceptorName}">
		      ${icItem.interceptorBundle}(${icItem.interceptorVersion})-${icItem.interceptorName}
		      </option>
		    </c:forEach>
		</select>
	</div>
	<div style="float:right">
		 <select id="xaright" multiple="multiple" name="ds[]">
		     <c:forEach items="${services}" var="srItem">
              <option title="${srItem.mark}" value="${srItem.serviceBundle}(${srItem.serviceVersion})-${srItem.serviceName}">
              ${srItem.serviceBundle}(${srItem.serviceVersion})-${srItem.serviceName}
              </option>
            </c:forEach>
		 </select>
 </div>
</div>
<div align="center" style="margin-top:700px">
    <button onclick="update()">更新</button>
</div>
<script type="text/javascript" src="${BASE_PATH}/public/jquery-ui.min.js"></script>
<script type="text/javascript" src="${BASE_PATH}/public/jquery.multiselect.min.js"></script>
<script type="text/javascript" src="${BASE_PATH}/public/jquery.multiselect.filter.js"></script>

<script type="text/javascript">

var selectInterceptor = "";
var exsistSelects = [];
var deleteSelects = [];
var addSelects = [];

$("#xaleft").multiselect({
	   noneSelectedText: "=请选择拦截器=",
	   header:false,
       multiple:false,
       minWidth:400,
       height:600,
       autoOpen:true,
       selectedList:1,
       beforeclose:function(){
          return false;
       },
       click:function(event, ui){
    	   loadDsByIc(ui.value);
       }
       });
$("#xaright").multiselect({
       noneSelectedText: "=请选择拦截服务=",
       checkAllText:"全选",
       uncheckAllText: '全不选',
       minWidth:400,
       height:600,
       selectedList:4,
       autoOpen:true,
       beforeclose:function(){
          return false;
       },
       click:function(event,ui){
    	   if(selectInterceptor == ""){
    		   return;
    	   }
    	   var exsist = $.inArray(ui.value,exsistSelects) > -1;
    	   if(ui.checked){
    		   if(exsist){
    			   if($.inArray(ui.value,deleteSelects) > -1){
    				   deleteSelects.splice($.inArray(ui.value,deleteSelects), 1);
    			   }
    		   }else{
    			   if($.inArray(ui.value,addSelects) < 0){
                       addSelects.push(ui.value);
                   }
    		   }
    	   }else{
    		   if(exsist){
                   if($.inArray(ui.value,deleteSelects) < 0){
                       deleteSelects.push(ui.value);
                   }
               }else{
                   if($.inArray(ui.value,addSelects) > -1){
                	   addSelects.splice($.inArray(ui.value,addSelects), 1);
                   }
               }
    	   }
       }
       }).multiselectfilter({
			 label:"服务",
			 placeholder:"服务名"
		});
       

function loadDsByIc(ic){
	$("#xaright").multiselect("uncheckAll");
	selectInterceptor = ic;
	exsistSelects = [];
	deleteSelects = [];
	addSelects = [];
	$.ajax({
		url:"${BASE_PATH}/interceptorMapping/getServices.do",
		type:"post",
		data:"interceptor="+ic,
		dataType:"json",
		success:function(data){
			if(data == null) return;
			$(data).each(function(i,val){
				var info = val.serviceBundle + "(" + val.serviceVersion + ")" + "-" + val.serviceName;
				$("#xaright option[value='"+info+"']").attr("selected",true);
				exsistSelects.push(info);
			});
			$("#xaright").multiselect("refresh");
		}
	});
}

function update(){
	if(selectInterceptor == ""){
		alert("未选中需要进行配置的拦截器");
		return;
	}
	if(addSelects.length == 0 && deleteSelects.length == 0){
        return;
	}
    $.ajax({
        url:"${BASE_PATH}/interceptorMapping/update.do",
        type:"post",
        data:"interceptor="+selectInterceptor+"&addServices="+addSelects+"&delServices="+deleteSelects,
        success:function(data){
            loadDsByIc(selectInterceptor);
            alert(data);
        }
    });
}

function refresh(){
	$.ajax({
        url:"${BASE_PATH}/config/refreshBundleConfig/interceptor.do",
        type:"post",
        success:function(data){
            alert(data);
        }
    });
}
</script>
</body>
</html>
