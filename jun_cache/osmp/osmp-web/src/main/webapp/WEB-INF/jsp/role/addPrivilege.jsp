<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
<link href="${BASE_PATH}/public/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<script src="${BASE_PATH}/public/ztree/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script type="text/javascript">
var rpIds=[];
$(function(){
	var roleId = $('#roleId').val();
	var url=BASE_PATH+'/role/ajaxGetPrivilege.do?id='+roleId;
	$.getJSON(url,function(data){
        if(data == ''){
            return;
        }else{
		 	var setting = {
		 			check: {
						enable: true
					},
		 			view: {
		 		        selectedMulti: false        //禁止多点选中
		 	        },
		 		    data: {
		 			    simpleData: {
		 				    enable:true,
		 				    idKey: "id",
		 				    pIdKey: "pId",
		 				    rootPId: ""
		 			    }
		 		    },
		 		    callback: {
		 			    onClick: function(treeId, treeNode) {
		 				    var treeObj = $.fn.zTree.getZTreeObj(treeNode);
		                     var selectedNode = treeObj.getSelectedNodes()[0];
		                     alert('1');
		 			    }
		 		    }
		 		};
		 	var zNodes=eval(data.sptList);
		 	rpIds = data.rpIds;
		 	setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };
		 	
	 		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
        }
    }); 
});	
function getCheckedMenu(){
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	var checkedNodes = zTree.getCheckedNodes(true);
	var checkCount = checkedNodes.length;
	var param={};
	var menuIds = [];
	if(checkCount > 0){
		$.each(checkedNodes,function(i,obj){
			menuIds.push(obj.id);
		});
	}
	param.menuIds=menuIds;
	param.roleId = $('#roleId').val();
	param.rpIds = rpIds;
	return param;
}
</script>
</head>
<body>
	<input type="hidden" id="roleId" value="${roleId}" />
	<div fit="true">
		<ul id="treeDemo" class="ztree">
			<!-- 菜单树 -->
		</ul>
		
	</div>
</body>
</html>
