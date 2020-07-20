<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
  <head>
    <title>Hadoop文件管理 - HDFS</title>
    
    <link href="${base}/static/scripts/jquery-alert/css/jquery-ui.css" type="text/css" rel="stylesheet" /> 
    <link href="${base}/static/scripts/jquery-alert/css/jquery.alerts.css" type="text/css" rel="stylesheet" /> 
  	<link href="${base}/static/scripts/ztree/css/zTreeStyle.css" type="text/css" rel="stylesheet" />  
  	
  	
  	<script type="text/javascript" src="${base}/static/scripts/ztree/js/jquery-1.8.1.js"></script> 
  	<script type="text/javascript" src="${base}/static/scripts/ztree/js/jquery.ztree.core-3.5.js"></script> 
  	<script type="text/javascript" src="${base}/static/scripts/ztree/js/jquery.ztree.excheck-3.5.js"></script> 
  	<script type="text/javascript" src="${base}/static/scripts/ztree/js/jquery.ztree.exedit-3.5.js"></script> 

	<script type="text/javascript" src="${base}/static/scripts/jquery-alert/js/jquery-ui.js"></script>

	<script type="text/javascript" src="${base}/static/scripts/jquery-alert/js/jquery。alerts.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/jquery-alert/js/jquery.contextmenu.r2.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/jquery-alert/js/jquery。ui.draggable.js"></script>
	
	<script type="text/javascript">
		<!--
			//定义全局zTree，用于使用zTree。getNodesByParam("checked",true,null);取得选择节点
			var zTree;
			var IDMark_A = "_a";
			
			var zNodes = [{id:"1",pid:"",name:"菜单",isParent:true,open:true}];  //初始化一个顶层默认节点
			var setting = {
				treeNodeKey:"id",  //在isSimpleData格式下，当前节点id属性
				treeNodeParentKey:"pid", //在isSimpleData格式下，当前节点的父节点id属性
				showLine:true,   //是否显示节点间的连线
				view:{
					selectedMulti:false,
					dblClickExpand:false,
				},
				async:{
					enable:true,  //需要采用异步方式获取子节点数据，默认false   
					dataType:"json", //数据类型为json
					type:"get",	//利用get方法提交数据
					contentType:"application/json", //返回格式类型  ，下面解释，当asyn=true时，设置异步获取节点的URL地址
					url:"${base}/hadoop/hdfs",   
					autoParam:["id=pid"],  //1、将需要作为参数提交的属性名称，制作成Array即可，例如：["id","name"]。2、可以设置提交时的参数名称，例如server只接受zId:["id=zId"]
					otherParam:{"otherParam":"zTreeAsyncTest"},
					dataFilter:filter   //用于对Ajax返回数据进行预处理的函数。[setting.async.enable=true时生效]默认值：null
				},
				callback:{
					onAsyncError:zTreeOnAsyncError,
					onAsyncSuccess:zTreeOnAsyncSuccess,
					beforeDrag:beforeDrag,
					beforeDrop:beforeDrop,
					beforeDblClick:zTreeBeforeDblClick,
					beforeRename:beforeRename,
					onClick:zTreeClick,
					onRightClick:OnRightClick,
					beforeRemove:beforeRemove, //点击删除时触发，用来提示用户是否确定删除
					beforeEditName:beforeEditName, //点击编辑时触发，用来判断该节点是否能编辑
					//beforeRename:beforeRename, //编辑结束时触发，用来验证输入的数据是否符合要求
					onRemove:onRemove, //删除节点后触发，用户后台操作
					onRename:onRename, //编辑后触发，用于操作后台
					
				},
				key:{
					name:"name"
				},
				edit:{
					enable:true,
					showRemoveBtn:false,
					showRenameBtn:false,
					removeTitle:'删除',
					renameTitle:'重命名'
				},
				data:{
					keep:{
						parent:true,
						leaf:true
					},
					simpleData:{
						enable:true
					}
				}
			};
			
			
			
			/**对左侧树形目录进行右键定义开始*/
			//在删除节点前，确认是否真的删除
			function beforeRemove(e,treeId,treeNode){
				return confirm("你确定要删除吗？");
			}
			//删除选中节点及以下所有子节点
			function onRemove(e,treeId,treeNode){
				if(treeNode.isParent){
					var childNodes = zTree.removeChildNodes(treeNode);
					var paramsArray = new Array();
					for(var i = 0 ; i < childNodes.length ; i ++){
						paramsArray.push(childNodes[i].id);	
					}	
					alert("删除父节点的id为：" + treeNode.id + "\r\n他的孩子节点有：" + paramsArray.join("'"));
					return;
				}
				alert("你点击要删除的节点的名称为：" + treeNode.name + "\r\n" + "节点id为：" + treeNode.id);
			}
			
			//编辑文件夹名字前做判断，看看是否是叶子节点
			function beforeEditName(treeId,treeNode){
				if(treeNode.isParent){
					alert("不准编辑非叶子节点！");
					return false;	
				}	
				return true;
			}
			//重命名处理
			function onRename(e,treeId,treeNode,isCancel){
				alert("修改节点的id为：" + treeNode.id + "\n修改后的名称为：" + treeNode.name);
			}
			//点击右键触发事件
			function OnRightClick(event,treeId,treeNode){
				if(!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0){
					zTree.cancelSelectedNode();
					showRMenu("root",event.clientX,event.clientY);	
				}else if(treeNode && !treeNode.noR){
					zTree.selectNode(treeNode);	
					showRMenu("node",event.clientX,event.clientY);
				}
			}
			//弹出菜单定位和显示内容的限定
			function showRMenu(type,x,y){
				$("#rMenu ul").show();
				if(type == "root"){
					$("#m_del").hide();
					$("#m_check").hide();
					$("#m_unCheck").hide();	
				}else{
					$("#m_del").show();
					$("#m_check").show();
					$("#m_uncheck").show();	
				}	
			
				rMenu.css({"top" : y + "px" , "left" : x + "px" , "visibility" : "visible"});
				$("body").bind("mousedown",onBodyMouseDown);
			}
			//弹出菜单隐藏
			function hideRMenu(){
				if(rMenu)
					rMenu.css({"visibility" : "hidden"});	
				$("body").unbind("mousedown" , onBodyMouseDown);
			}
			//判断鼠标是否点击弹出菜单内部，是否隐藏
			function onBodyMouseDown(event){
				if(!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0 )){
					rMenu.css({"visibility" : "hidden"});
				}
			}
			
			
			var addCount = 1;  //用于统计添加节点的个数，给节点命名
			//添加新的tree节点
			function addTreeNode(){
				hideRMenu();  //调用隐藏弹出菜单
				var newNode = {name:"增加" + (addCount ++),isParent:true};	//name：为节点命名
				if(zTree.getSelectedNodes()[0]){
					//newNode.checked = zTree.getSelectedNodes()[0].checked;
					alert(zTree.getSelectedNodes()[0].id);
					zTree.addNodes(zTree.getSelectedNodes()[0] , newNode);	
				}else{
					zTree.addNodes(null , newNode);	
				}
			}
			//删除选中的节点
			function removeTreeNode(){
				hideRMenu();  //调用隐藏弹出菜单
				var nodes = zTree.getSelectedNodes();   //获得选中的节点
				if(nodes && nodes.length > 0){			//判断是否有选中的节点
					if(nodes[0].children && nodes[0].children.length > 0){	//判断选中的节点是否有子节点，下面提醒用户是否删除
						var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
						if(confirm(msg) == true){
							zTree.removeNode(nodes[0]);	//连同子节点一起删除		
						}	
					}else{
						zTree.removeNode(nodes[0]);		//删除选中的节点
					}
				}	
			}
			
			//重置树
			function resetTree(){
				hideRMenu();
				$.fn.zTree.init($("#tree"),setting,zNodes);	
			}
			/**对左侧树形目录进行右键定义结束*/
		
		
			//判断节点是否在已选择的节点中，判断重复选择
			function isNodeInChecked(curId){
				if(zTree == null){
						return false;
					}
				
				var selectedNodes = zTree.getNodesByParam("checked",true,null);
				var selectedNode;
				
				if(selectedNodes == null || selectedNodes.length == 0){
						return false;
					}
					
				for(var i = 0 ; i < selectedNodes.length ; i ++){
						selectedNode = selectedNodes[i];
						if(selectedNode.id == curId){
								return true;
							}
					}
					return false;
			}
		
			
			
			/**处理单击事件，在右侧显示文本中的内容开始*/
			function zTreeClick(event,treeId,treeNode){
				var zTree = $.fn.zTree.getZTreeObj("tree");
				var nodes = zTree.getSelectedNodes();
				var treeNode = nodes[0];
				if(treeNode.id == ''){
					treeNode.id = 0;
				}
				$.ajax({
					async:false,
					type:"GET",
					dataType:"json",
					data:"pid=" + treeNode.id,   //pid用于存储目标位置的父节点id值，name为记录移动的name值，isParent是记录移动的标志(判断是否是文件夹或文件)
					contentType:"application/json",
					url:"${base}menu/getMenu",
					success:function(data){
						var obj = eval(data);
						$("#fileList").empty();
						$(obj).each(function(index){
							var val = obj[index];
							var str = "<li id = '" + val.id + "' name = 'showli' onclick='setBg(" + val.id + ")' onmouseover='setOver(" + val.id + ")' onmouseout='setOut(" + val.id + ")'";
							if(val.isParent){
								str += " class = 'foldfile draggable droppable' ondblclick = 'openFold(" + val.id + ",\"" + val.name + "\")' ";
							}else{
								str += " class = 'foldfile draggable' ";
							}
							str += "style='float:left; list-style:none; width:84px; margin:10px; text-align:center; border:1px solid #ffffff;'><div style='width:80px; padding:2px;'>";
							if(val.isParent){
								str += "<img src='${base}/static/scripts/jquery-alert/images/fold.png' width='80px' height='80px'/>";
							}else{
								str += "<img src='${base}/static/scripts/jquery-alert/images/file.png' width='80px' height='80px'/>";
							}
							str += "</div><div id='" + val.id + "name' style='width:84px; text-align:center; font-size:12px; line-height:20px;' onclick='selName(" + val.id + ")' onkeydown='updName(" + val.id + ")'>" + val.name + "</div></li>";
							$("#fileList").append(str);
						});
						dragdrop();
						ztc(treeNode);
					}
				});		
			}
			
			function ztc(treeNode){
				$.ajax({
					async:true,
					type:"GET",
					dataType:"json",
					data:"pid=" + treeNode.id,   //pid用于存储目标位置的父节点id值，name为记录移动的name值，isParent是记录移动的标志(判断是否是文件夹或文件)
					contentType:"application/json",
					url:"${base}menu/getPid",
					success:function(data){
						$("#pathtxt").val(data);
					}
				});		
			}
			
			/**处理单击事件，在右侧显示文本中的内容结束*/
			
			
			function zTreeBeforeDblClick(treeId , treeNode){
				return true;
			}
			//修改文件或者文件夹名称后，对文件名做校验
			function beforeRename(treeId , treeNode , newName){
				if(newName.length == 0){
					alert("节点名称不能为空。");
					var zTree = $.fn.zTree.getZTreeObj("tree");
					setTimeout(function(){zTree.editName(treeNode)},10);
					return false;
				}
				return true;
			}
			
			
			/**异步处理开始**/
			function zTreeOnAsyncError(treeId,parentNode,responseData){
				alert("shibai");
			}
			
			function zTreeOnAsyncSuccess(event,treeId,treeNode,msg){
				//alert(msg);
			}
			
			function filter(treeId,parentNode,childNodes){  //treeId(String)对应zTree的treeId，便于用户操控，parentNode(JSON)进行异步加载的父节点JSON数据对象，对根进行异步加载时，parentNode=null，responseData(Array(JSON)/JSON/String)异步加载获取到的数据转换后的Array(JSON)/JSON/String数据对象
				if(!childNodes){
				 	return null;
				 }
				for(var i = 0 , l = childNodes.length ; i < l ; i ++){
					childNodes[i].name = childNodes[i].name.replace(/\.n/g,'.');
				}
				return childNodes;
			}
			/**异步处理结束**/
			
			/**拖拽部分开始**/
			/**
			*	方法名:beforeDrag
			*	方法描述:用于捕获节点被拖拽之前的时间回调函数，并且根据返回值确定是否允许开启拖拽操作.默认值：null
			*	参数treeId(String):被拖拽的节点treeNodes所在zTree的treeId，便于用户操控
			*	参数treeNodes(Array(JSON)):要拖拽的节点JSON数据集合  v3.0允许多个同级节点同时被拖拽，因此将参数修改为Array(JSON)，如果拖拽时多个被选择的节点不是同级关系，则只能拖拽鼠标当前所在位置的节点
			*	返回值(Boolean):返回值是true/false  如果返回false，zTree将终止拖拽，也无法触发onDrag/beforeDrop/onDrop事件回调函数
			*/
			function beforeDrag(treeId , treeNodes){
				for(var i = 0 , l = treeNodes.length ; i < l ; i ++){
					if(treeNodes[i].drag === false){
						return false;
					}
				}
				return true;
			}
			
			/**
			*	方法名:beforeDrop
			*	方法描述:用于捕获节点被拖拽之前的时间回调函数，并且根据返回值确定是否允许此拖拽操作  默认值：null   如未拖拽到有效位置，则不触发此回调函数，直接将节点恢复原位置
			*	参数treeId(String):目标节点targetNode所在zTree的treeId，便于用户控制
			*	参数treeNodes(Array(JSON)):被拖拽的节点JSON数据集合   无论拖拽操作作为复制还是移动，treeNodes都是当前被拖拽节点的数据集合
			*	参数targetNode(JSON):treeNodes被拖拽放开的目标节点，JSON数据对象。如果拖拽成为根节点，则targetNode=null
			*	参数moveType(String):指定移动到目标节点的相对位置  “inner”：成为子节点，“prev”：成为同级前一个节点，”next“：成为同级后一个节点
			*	参数isCopy(Boolean):拖拽节点操作是复制或移动  true:复制；false:移动
			*	返回值(Boolean):返回值是true/false 如果返回false，zTree将恢复被拖拽的节点，也无法触发onDrop事件回调函数
			*/
			function beforeDrop(treeId , treeNodes , targetNode , moveType,isCopy){
				if(targetNode ? targetNode.drop !== false : true){  
					if(isCopy){  //暂不执行
						$.ajax({
							async:true,
							type:"GET",
							dataType:"json",
							data:"&pid=" + targetNode.id + "&name=" + treeNodes[0].name + "&isParent=" + treeNodes[0].isParent,   //pid用于存储目标位置的父节点id值，name为记录移动的name值，isParent是记录移动的标志(判断是否是文件夹或文件)
							contentType:"application/json",
							url:"${base}menu/copyMenu",
							success:function(data){
								
							}
						});
					}else{
						$.ajax({
							async:true,
							type:"GET",
							dataType:"json",
							data:"id=" + treeNodes[0].id + "&pid=" + targetNode.id + "&name=" + treeNodes[0].name + "&isParent=" + treeNodes[0].isParent,   //pid用于存储目标位置的父节点id值，name为记录移动的name值，isParent是记录移动的标志(判断是否是文件夹或文件)
							contentType:"application/json",
							url:"${base}menu/moveMenu",
							success:function(data){
								
							}
						});
					}
				}
				return targetNode ? targetNode.drop !== false : true;
			}
			
			function setCheck() {
				var zTree = $.fn.zTree.getZTreeObj("tree"),
				isCopy = $("#copy").attr("checked"),
				isMove = $("#move").attr("checked"),
				prev = $("#prev").attr("checked"),
				inner = $("#inner").attr("checked"),
				next = $("#next").attr("checked");
				zTree.setting.edit.drag.isCopy = isCopy;
				zTree.setting.edit.drag.isMove = isMove;
				showCode(1, ['setting.edit.drag.isCopy = ' + isCopy, 'setting.edit.drag.isMove = ' + isMove]);

				zTree.setting.edit.drag.prev = prev;
				zTree.setting.edit.drag.inner = inner;
				zTree.setting.edit.drag.next = next;
				showCode(2, ['setting.edit.drag.prev = ' + prev, 'setting.edit.drag.inner = ' + inner, 'setting.edit.drag.next = ' + next]);
			}
			
			function showCode(id, str) {
				var code = $("#code" + id);
				code.empty();
				for (var i=0, l=str.length; i<l; i++) {
					code.append("<li>"+str[i]+"</li>");
				}
			}
			
			function initZNodes(){
				$.ajax({
						async:true,
						type:"GET",
						dataType:"json",
						data:"id=" + treeNodes[0].id + "&pid=" + targetNode.id + "&name=" + treeNodes[0].name + "&isParent=" + treeNodes[0].isParent,   //pid用于存储目标位置的父节点id值，name为记录移动的name值，isParent是记录移动的标志(判断是否是文件夹或文件)
						contentType:"application/json",
						url:"${base}menu/moveMenu",
						success:function(data){
							zNodes = data
						}
					});
			}
			
			/**拖拽部分结束**/
			var rMenu;
			$(document).ready(function(){
				
				
				
				zTree = $.fn.zTree.init($("#tree"),setting,zNodes);
				rMenu = $("#rMenu");
			});
		-->
		</script>
		
		
		<script type="text/javascript">
		function setBg(obj){
			var objli = $('[name=showli]');
			for(var i = 0 ; i < objli.length ; i ++){
				if(objli.eq(i).attr('id') == obj){
					objli.eq(i).css("background","#cee2fa");
					objli.eq(i).css("border","1px solid #98a6b3");
					objli.eq(i).attr("checked",true);
				}else{
					objli.eq(i).css("background","none");
					objli.eq(i).css("border","1px solid #ffffff");
					objli.eq(i).attr("checked",false);
				}
			}
		}
		
		
		function setOver(obj){
			if(!$("#" + obj).attr("checked")){
				$("#" + obj).css("background","#f3f6fd");
				$("#" + obj).css("border","1px solid #eaf0f7");
			}
		}
		
		function setOut(obj){
			if(!$("#" + obj).attr("checked")){
				$("#" + obj).css("background","none");
				$("#" + obj + "name").css({"background":"none"});
				$("#" + obj).css("border","1px solid #ffffff");
			}
		}
		
		function selName(obj){
			if($("#" + obj).attr("checked")){
				$("#" + obj + "name").css({"background":"white"});
				$("#" + obj + "name").attr("contentEditable",true);
				
				
				var objDiv = document.getElementById("" + obj + "name");
					if (document.selection) {
    					// for IE
    					var r1 = document.body.createTextRange();
    					r1.moveToElementText(objDiv);
    					r1.moveEnd("character");
    					r1.select();
					} else {
    					// For others
    					var s1 = window.getSelection();
    					var r1 = document.createRange();
   						r1.selectNode(objDiv);
    					s1.addRange(r1);
					}
				
				$("#" + obj + "name").focus();
			}
		}
		
		function updName(obj){
			var $updobj = $("#" + obj + "name");
			var oldname = $updobj.text();
			var newname = '';
			$updobj.bind('keydown',function(e){
				e = (e) ? e : ((window.event) ? window.event : "");
				var key = e.keyCode ? e.keyCode : e.which;
				//var key = e.which;
				if(key == 13){
					e.returnValue = false;
					e.preventDefault();
					//e.returnValue == true ? e.returnValue = false : e.preventDefault();  
					if($updobj.text().length <= 0){
						$updobj.text(oldname);
					}else{
						newname = $updobj.text();
						$.ajax({
							async:false,
							type:"GET",
							dataType:"json",
							data:"id=" + obj + "&oldname=" + oldname + "&newname=" + newname,   //根据id值进行改名，oldname为旧名，newname为新名
							contentType:"application/json",
							url:"${base}menu/updName",
							success:function(data){
								$updobj.css({"background":"none"});
								$updobj.attr("contentEditable",false);
							}
						});
					}
				}
			});
		}
		
		function back(){
			var path = $("#pathtxt").val();
			var endPath = path.substring(0,path.lastIndexOf('\\'));
			$("#pathtxt").val(endPath);
			var parentName = endPath.substring(endPath.lastIndexOf('\\') + 1 , endPath.length);
			alert(parentName);
			$.ajax({
				async:false,
				type:"GET",
				dataType:"json",
				data:"name=" + parentName,
				contentType:"application/json",
				url:"${base}menu/showByName",
				success:function(data){
						var obj = eval(data);
						alert(obj);
						$("#fileList").empty();
						$(obj).each(function(index){
							var val = obj[index];
							
							var str = "<li id = '" + val.id + "' name = 'showli' onclick='setBg(" + val.id + ")' onmouseover='setOver(" + val.id + ")' onmouseout='setOut(" + val.id + ")'";
							
							if(val.isParent){
								str += " class = 'foldfile draggable droppable' ondblclick = 'openFold(" + val.id + ",\"" + val.name + "\")' ";
							}else{
								str += " class = 'foldfile draggable' ";
							}
							str += "style='float:left; list-style:none; width:84px; margin:10px; text-align:center; border:1px solid #ffffff;'><div style='width:80px; padding:2px;'>";
							
							if(val.isParent){
								str += "<img src='${base}/static/scripts/jquery-alert/images/fold.png' width='80px' height='80px'/>";
							}else{
								str += "<img src='${base}/static/scripts/jquery-alert/images/file.png' width='80px' height='80px'/>";
							}
							str += "</div><div id='" + val.id + "name' style='width:84px; text-align:center; font-size:12px; line-height:20px;' onclick='selName(" + val.id + ")' onkeydown='updName(" + val.id + ")'>" + val.name + "</div></li>";
							$("#fileList").append(str);
						});
						
						dragdrop();
					}
					
			});
		}
		
		
		function openFold(pid,menuname){
			$.ajax({
				async:false,
				type:"GET",
				dataType:"json",
				data:"pid=" + pid,
				contentType:"application/json",
				url:"${base}menu/getMenu",
				success:function(data){
						var obj = eval(data);
						alert(obj);
						$("#fileList").empty();
						$(obj).each(function(index){
							var val = obj[index];
							
							var str = "<li id = '" + val.id + "' name = 'showli' onclick='setBg(" + val.id + ")' onmouseover='setOver(" + val.id + ")' onmouseout='setOut(" + val.id + ")'";
							
							if(val.isParent){
								str += " class = 'foldfile draggable droppable' ondblclick = 'openFold(" + val.id + ",\"" + val.name + "\")' ";
							}else{
								str += " class = 'foldfile draggable' ";
							}
							str += "style='float:left; list-style:none; width:84px; margin:10px; text-align:center; border:1px solid #ffffff;'><div style='width:80px; padding:2px;'>";
							
							if(val.isParent){
								str += "<img src='${base}/static/scripts/jquery-alert/images/fold.png' width='80px' height='80px'/>";
							}else{
								str += "<img src='${base}/static/scripts/jquery-alert/images/file.png' width='80px' height='80px'/>";
							}
							str += "</div><div id='" + val.id + "name' style='width:84px; text-align:center; font-size:12px; line-height:20px;' onclick='selName(" + val.id + ")' onkeydown='updName(" + val.id + ")'>" + val.name + "</div></li>";
							$("#fileList").append(str);
						});
						dragdrop();
						
						$("#pathtxt").val($("#pathtxt").val() + "\\" + menuname);
					}	
			});
		}
		
		
		
		function dragdrop() {
    		$( ".draggable" ).draggable({revert:"invalid",cursor:"move",
			create:function(event,ui){
			
			},
			start:function(event,ui){
				//$(this).hide();
				//alert($(this).find("p").html());
				$("body").css({"cursor": "move"});
				$(this).css({"z-index":"56"});
			},
			stop:function(event,ui){
				//$(this).hide();
			},
			drap:function(event,ui){
			
			}
		});
   	 	$( ".droppable" ).droppable({
     	 	activeClass:"ui-state-hover",
	  		hoverClass:"ui-state-active",
	  		activate:function(event,ui){
				$(this).find("p").html("Drop here!");
	  		},
	  		over:function(event,ui){
				$(this).addClass("ui-state-highlight").find("p").html("Oh,Yeah!");
	  		},
	  		out:function(event,ui){
				$(this).removeClass("ui-state-highlight").find("p").html("Don't leave me!");
	 		},
	  	 	drop: function( event, ui ) {
        		$( this )
				.removeClass("ui-state-highlight")
          		.find( "p" )
            	.html( "Dropped!" );
      }
    });
  }
  
  
  		$.fn.setCursorPosition = function(position){
    		if(this.length == 0) return this;
   	 		return $(this).setSelection(position, position);
		};

		$.fn.setSelection = function(selectionStart, selectionEnd) {
    		if(this.length == 0) return this;
    		input = this[0];

    		if (input.createTextRange) {
        		var range = input.createTextRange();
        		range.collapse(true);
       	 		range.moveEnd('character', selectionEnd);
        		range.moveStart('character', selectionStart);
        		range.select();
    		} else if (input.setSelectionRange) {
        		input.focus();
        		input.setSelectionRange(selectionStart, selectionEnd);
    		}

    		return this;
		};

		$.fn.focusEnd = function(){
    		this.setCursorPosition(this.text().length);
            return this;
		};
  	
	</script>
	
	
	
	
	
	<style type="text/css">
		*{margin:0px; padding:0px;}
		.content_wrap{width:810px; margin:10px auto;}
		.zTreeDemoBackground{ width:200px;}
		.left{float:left;}
		.contentDisplay{ width:600px;}
		.right{float:right;}
		
		
		div#rMenu {position:absolute; visibility:hidden; top:0; background-color:gray;text-align: left;padding: 2px;}
    	div#rMenu ul li{
			margin: 1px 0;
			padding: 5 5px;
			cursor: pointer;
			font-size:12px;
			list-style: none outside none;
			background-color: #bee1ff;
		}
		a img{ border:0px;}
		a#back img{ margin-right:10px;}
		.tool{ height:24px; line-height:24px;}
	</style>
		
		
		
  </head>
  
  <body>	
  	<div class="content_wrap">
  		<div class="toolBar">
  			<div class="tool"><a href="javascript:void(0);" id="back" onclick="back()"><img src="${base}/static/scripts/jquery-alert/images/back.png" width="24px" height="24px"/></a><input type = "text" id = "pathtxt" value="${path}" style="height:24px; line-height:24px; font-size:20px; width:300px;"/></div>
  		</div>
  		<div class="zTreeDemoBackground left">	
  			<ul id="tree" class="ztree"></ul>
  		</div>
  		<div class="contentDisplay right">
  			<div id = "fileContent">
  				<ul id='fileList' style='width:600px;'>
  				</ul>
  			</div>
  		</div>
  	</div>
	<div id="rMenu">
	<ul>
		<li id="m_add" onclick="addTreeNode();">增加节点</li>
		<li id="m_del" onclick="removeTreeNode();">删除节点</li>
		<li id="m_check" onclick="checkTreeNode(true);">Check节点</li>
		<li id="m_unCheck" onclick="checkTreeNode(false);">unCheck节点</li>
		<li id="m_reset" onclick="resetTree();">恢复zTree</li>
	</ul>
</div>	
	

<script>
var div = document.getElementById("my_div_id");
if (document.selection) {
    // for IE
    var r = document.body.createTextRange();
    r.moveToElementText(div);
    r.moveEnd("character");
    r.select();
} else {
    // For others
    var s = window.getSelection();
    var r = document.createRange();
    r.selectNode(div);
    s.addRange(r);
}
</script>
	
	
	
	 <!-- 右键菜单的源 -->
   <div class="contextMenu" id = "myMenu1">
   	<ul>
    	<li id="edit"><img src = "edit.png"/>编辑</li>
        <li id="copy"><img src = "copy.png"/>复制</li>
        <li class="separator"><hr /></li>
    	<li id="open"><img src = "folder.png"/>打开</li>
        <li id="email"><img src = "email.png"/>邮件</li>
        <li id="save"><img src = "disk.png"/>保存</li>
        <li id="delete"><img src = "cross.png"/>关闭</li>
    </ul>
   </div>
   
   
   <div class="contextMenu" id = "myMenu3">
   	<ul>
    	<li id = "item_11">csdn</li>
        <li id = "item_12">javaeye</li>
        <li id = "item_13">itpub</li>
        <li id = "paste">paste</li>
        <li id="new_file"><img src = "new_file.png"/>新建文件</li>
        <li id="new_fold"><img src = "new_fold.png"/>新建文件夹</li>
    </ul>
   </div>
   
   
	<script type="text/javascript">
	function func(){
		$('.foldfile').contextMenu('myMenu1',
		{
			bindings:
			{
				'edit':function(t){
					jPrompt('Edit content:', $("#" + t.id + " > .fileName").text(), '重命名', function(r) {
						if( r ) {
							$("#" + t.id + " > .fileName").text(r);
						}
					});
				},
				'copy':function(t){
					window.copyObj = t;
				},
				'cut':function(t){
					
				},
				'open':function(t){
					alert('Trigger was ' + t.id + '\nAction was Open');
				},	
				'email':function(t){
					alert('Trigger was ' + t.id + '\nAction was Email');	
				},
				'save':function(t){
					alert('Trigger was ' + t.id + '\nAction was Save');	
				},
				'delete':function(t){
					alert('Trigger was ' + t.id + '\nAction was Delete');
				}
			}	
		});	
	}
	$(function(){
		document.oncontextmenu=function(){return false;}
		//var copyObj;	//被拷贝的副本
		var cutObj;		//被剪切的对象
		var copyNo = 1;
		var newCreateFile = 1;	 //新建文件统计
		var newCreateFold = 1;   //新建文件夹统计
		var idNo = 5;        //用于统计id编号的变大
		//所有class为demo1的span标签都会绑定此右键菜单
		func();
		
		//所有div标签class为demo3的绑定此右键菜单
		$("div.demo3,body").contextMenu('myMenu3',{
			//重写onContextMenu和onShowMenu事件
			onContextMenu:function(e){
				if($(e.target).attr('id') == 'dontShow') 
					return false;
				else
					return true;
			},
			//显示菜单项
			onShowMenu:function(e,menu){
				if($(e.target).attr('id') == 'showOne'){
					$('#item_22,#item_33',menu).remove();	
				}else if(window.copyObj == null){
					$('#paste',menu).attr("disabled","disabled");
				}
				return menu;
			},
			bindings:
			{
				'paste':function(t){
					$("#fileList").append("<li id = 't_" + idNo + "' class = 'demo1'><div class = 'fileName'>" + $('#' + window.copyObj.id + ' > .fileName').text() + " 复制" + copyNo + "</div></li>");
					idNo++;
					copyNo++;
					//window.copyObj = null;
					func();
				},
				'new_file':function(){
					$("#fileList").append("<li id = 't_" + idNo + "' class = 'demo1'><div class = 'fileName'>" + "新建文件" + newCreateFile + "</div></li>");
					newCreateFile++;
					idNo++;
					func();
				},
				'new_fold':function(){
					$("#fileList").append("<li id = 't_" + idNo + "' class = 'demo1'><div class = 'fileName'>" + "新建文件夹" + newCreateFold + "</div></li>");
					newCreateFold++;
					idNo++;
					func();
				}
			}
		});
		
	});
    </script>
  </body>
</html>
