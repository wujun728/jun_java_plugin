$(document).ready(function(){
	//加载部门列表
	loadOrgList();
	//加载用户列表
	loadUserList("");
});
	
	

function loadOrgList(){
	var orgTreesetting = {
		callback: {
			beforeClick:beforeClick
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pid",
				rootPId : "null"
			}
		},
		view:{
			showLine: false,
			showIcon: false,
			selectedMulti: false,
			dblClickExpand: false,
			addDiyDom: addDiyDom,
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom
		}
	};
	
	$.ajax({
		url: ctx+'/system/org/list/json',
		type: 'POST',
		dataType: 'json',
		success:function(ret){
			if(ret.status=="success"){
				$.fn.zTree.init($("#orgListTree"), orgTreesetting,ret.data);
				var treeObj = $("#orgListTree");
				treeObj.hover(function () {
					if (!treeObj.hasClass("showIcon")) {
						treeObj.addClass("showIcon");
					}
				}, function() {
					treeObj.removeClass("showIcon");
				});
			}else{
				console.log("获取部门数据失败");
			}
		}
	});
}
function beforeClick(treeId, treeNode) {
	$("#pageNum").val(1);
	loadUserList(treeNode.id);
	return true;
}

function addDiyDom(treeId, treeNode) {
	var spaceWidth = 5;
	var switchObj = $("#" + treeNode.tId + "_switch"),
	icoObj = $("#" + treeNode.tId + "_ico");
	switchObj.remove();
	icoObj.before(switchObj);
	
	if (treeNode.level > 1) {
		var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
		switchObj.before(spaceStr);
	}
}

function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	/*var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='add node' onclick='showEditorMenu(\""+treeNode.tId+"\");'></span><ul style='float:right;background-color:#d4d4d4;display:none;' id='addBtn_" + treeNode.tId
		+ "_div'><li onclick='addOrg(\""+treeNode.id+"\");'>新增</li><li onclick='editOrg(\""+treeNode.id+"\");'>编辑</li><li onclick='delOrg(\""+treeNode.id+"\");'>删除</li></ul>";*/
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
	+ "' title='add node' onclick='showEditorMenu(\""+treeNode.tId+"\");'></span><span id='addBtn_" + treeNode.tId
	+ "_div'><button class='layui-btn layui-btn-small' style='height:24px;width:24px;border-radius:12px;'><i class='layui-icon'>&#x1002;</i></button></span>";
	sObj.after(addStr);
};

function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
	$("#addBtn_"+treeNode.tId+"_div").unbind().remove();
};

function showEditorMenu(treeNodeId){
	$("#addBtn_"+treeNodeId+"_div").toggle();
}

function loadUserList(orgId){
	var pageNum = $("#pageNum").val();
	$.ajax({
		url: ctx+'/system/user/list/json',
		type: 'POST',
		dataType: 'json',
		data:{orgId:orgId,pageNum:pageNum},
		success:function(ret){
			if(ret.status=="success"){
				var userList = ret.data;
				var htmlStr = '';
				for(var i=0;i<userList.length;i++){
					var tmpData = userList[i];
					htmlStr += '<tr>\
					      			<td>'+tmpData.name+'</td>\
					      			<td>'+tmpData.account+'</td>\
					      			<td>'+tmpData.mobile+'</td>\
					      			<td>'+tmpData.email+'</td>\
					    		</tr>';
				}
				var userList = $("#userList");
				userList.html(htmlStr);
			}else{
				console.log("加载用户列表失败");
			}
		}
	});
}


/**
 * 添加子级部门
 * @param orgId
 */
function addOrg(orgId){
	alert("弹出添加框");
}

/**
 * 编辑部门
 */
function editOrg(orgId){
	alert("弹出编辑框");
}

/**
 * 删除部门
 */
function delOrg(orgId){
	alert("删除");
}