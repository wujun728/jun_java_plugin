<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
</head>
<script type="text/javascript">
			/* var kaikai = function (rec){
				if('bjs' == rec.code){
					alert('hh');
				}
			}
			var gege = function (rec){
				alert(rec.code);
				alert(rec.name);
			} */
			function post(){
				$.post(BASE_PATH+'/cxf/http/service/data/olquery?source={"from":"olweb"}&parameter={"acctype":4,"dbName":"COMMON","flag":"Y","sql":"osmp.menu.snbid"}',function(i){
					alert(i);
				});
			}
			function get(){
				$.get(BASE_PATH+'/cxf/http/service/data/olquery',
						{"source":'{"from":"olweb"}',"parameter":'{"acctype":4,"dbName":"COMMON","flag":"Y","sql":"osmp.menu.nbid"}'},function(i){
					alert(i);
				});
			}
			
</script>
<body>

<button onclick="post()"> post</button>
<button onclick="get()"> get</button>

<a href="${BASE_PATH }/test/toAdd.do">ddddddddd</a>

<a href="http://localhost:8080/osmp-web/cxf/http/service/data/olquery?source={'from':'olweb'}&parmeter={'account':'OLtest4','dbName':'common','flag':'N','sql':'osmp.account.mobile'}">测试一下</a>

<%-- <div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		策略条件：<z:select dictCode="roleType" id="cllx" child="child"></z:select><br>
		<z:select id="child" onSelectJs="kaikai"></z:select>
		性别：<z:select id="radioContian" type="REDIO" dictCode="1001" name="sex"></z:select>
		地区：<z:select id="checkboxContian" type="CHECKBOX" dictCode="area" name="diqu"></z:select>
		
<!-- 		<script type="text/javascript">
			$.getJSON(BASE_PATH+"/dict/dictJson.do?code=1001",function(o){
				var html = ""
				$.each(o,function(i,k){
					html += k.name + ':<input name="sex" type="checkbox" value="'+k.code+'"/>'
				});
				$('#checkboxContian1').html(html);
			});
		</script>
		<div id="checkboxContian1"></div> -->
		
		
		
		------------------------------------------------------------------------
		<z:select dictCode="area" id="ksks" child="ds" ></z:select>
		<z:select id="ds" onSelectJs="kaikai" ></z:select>
	</div>
</div>
</body> --%>

<form action="http://localhost:8080/osmp-web/cxf/http/service/data/olquery">
	<input id="source" name="source" />
	<input id="parameter" name="parameter" />
	<input type="submit" value="提交" />
</form>

</html>
