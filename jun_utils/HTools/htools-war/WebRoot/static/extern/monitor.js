var lock = 1;
var job;
var infoIndex=0;
var infoLength = 0;
function init(){
  try{
  
	mywebdb.create({
		db:"nodeListData",version:1,
		table:{
			name:"nodeList",
			columns:{
				keyPath:{keyPath:"ip"},
				isExists:{name:'isExists',index:{unique:false}},
				hostname:{name:'hostname',index:{unique:false}},
				rootPassword:{name:'rootPassword',index:{unique:false}},
				hadooppath:{name:'hadooppath',index:{unique:false}},
				isHadoop:{name:'isHadoop',index:{unique:false}},
				hadoop:{name:'hadoop',index:{unique:false}},
				javahome:{name:'javahome',index:{unique:false}},
				pingip:{name:'ping_ip',index:{unique:false}}
			}
		}
	});
	mywebdb.each({
		   name:"nodeList",
		   success:function(msg){
	         var ip = msg.value.ip;
		     mywebdb.delete({
		        name:"nodeList",
		        key:ip
		    });
		   }
		});
	
   }catch(ex){
// 	  alert(ex);
   }
   
}
function delDB(){
	try{
	mywebdb.deleteDB({name:'nodeListData'});
	}catch(ex){
// 		alert(ex);
	}
}
function ipNumber(ips){
	if(ips.indexOf("/")!=-1){
		var ip = ips.substring(ips.lastIndexOf(".")+1,ips.length);
		var ips = ip.split("/");
		var ip1 = parseInt(ips[0]);
		var ip2 = parseInt(ips[1]);
		return ip2-ip1+1;
	}
	return 1;
}
function success(){
	return "<i class='icon-ok'></i>";
}
function error(){
	return "<i class='icon-remove'></i>";
}
function circle(){
	return "<i class=‘icon-ban-circle’></i>";
}
function showInfoUL(msg){
//     <ul id="tablehead" class="breadcrumb"> 
//     <li id="ipchbox" style="width:20px;"><input type="checkbox" class="checkbox" id="allnodeips" name="allnodeips" /></li> 
//     <li id="ipaddr" style="width:120px;">主机ip</li> 
//     <li id="hostname" style="width:90px;">主机名</li> 
//     <li id="hostexists" style="width:80px;">主机</br>是否存在</li> 
//     <li id="rootpwd" style="width:50px;">ROOT</br>密码</li> 
//     <li id="isHadoop">hadoop</br>用户是否存在</li> 
//     <li id="ish">是否使用</br>hadoop用户登录</li> 
//     <li id="hadoophome">hadoop</br>程序</li> 
//     <li id="javahome">java环境</li> 
//   </ul> 
	 try{
		 var trid=msg.ip.replace(/\./g,"_");
//		 $("#hostInfo").append("<ul class='breadcrumb' id='"+trid+"'><li>aa</li></ul>");
 		 $("#hostInfo").append("<ul  class='breadcrumb'  id='"+trid+"' style='height:1px'></ul>");
 		 if(msg.isExists != undefined && msg.hadooppath == undefined  && msg.rootPassword != undefined){
		 	$("#"+trid).append("<li id='ipchbox'  style='width:20px;'><input checked type='checkbox' class='checkbox' id='nodeips' name='nodeips' value='"+msg.ip+","+msg.hostname+","+msg.isHadoop+","+msg.hadooppath+"'/></li>");
 		 }else{
		 	$("#"+trid).append("<li id='ipchbox'  style='width:20px;'><input type='checkbox' class='checkbox' id='nodeips' name='nodeips' value='"+msg.ip+","+msg.hostname+","+msg.isHadoop+","+msg.hadooppath+"'/></li>");
 		 }
		 $("#"+trid).append("<li id='ipaddr' title='"+msg.ping_ip+"' style='width:130px'>"+msg.ip+"</li>");
		 $("#"+trid).append(msg.hostname==undefined?"<li id='hostname' style='width:115px;'>"+error()+"</li>":"<li id='hostname' style='width:115px;'>"+msg.hostname+"</li>");
	     $("#"+trid).append(msg.isExists==undefined?"<li id='hostexists'  style='width:100px;'>"+error()+"</li>":"<li id='hostexists'  style='width:100px;'>"+success()+"</li>");
	     $("#"+trid).append(msg.rootPassword==undefined?"<li id='rootpwd'  style='width:90px;'>"+error()+"</li>":"<li id='rootpwd'  style='width:90px;'>"+success()+"</li>");
	 	 $("#"+trid).append(msg.isHadoop==undefined?"<li id='isHadoop' style='width:150px;' >"+error()+"</li>":"<li id='isHadoop' style='width:150px;' >"+success()+"</li>");
	 	 if(msg.usernameAndPassword != undefined){
	 	 	//登录的用户是否是hadoop
	 	 	$("#"+trid).append("<li id='ish' style='width:180px;' >"+success()+"</li>");
	 	 }else{
	 	 	$("#"+trid).append("<li id='ish' style='width:180px;'>"+error()+"</li>");
	 	 }
	 	 $("#"+trid).append(msg.hadooppath==undefined?"<li id='hadoophome' style='width:100px;'>"+error()+"</li>":"<li id='hadoophome' style='width:100px;' title='"+msg.hadooppath+"'>"+success()+"</li>");
		 $("#"+trid).append(msg.javahome==undefined?"<li id='javahome' style='width:80px'>"+error()+"</li>":"<li id='javahome' title='"+msg.javahome+"' style='width:80px'>"+success()+"</li>");	
		 
	 }catch(ex){
		 
	 }
}
function allck(){
 		var cks = document.getElementsByName("nodeips");
 		for(var i=0;i<cks.length;i++){
 			cks[i].checked=document.getElementById("allnodeips").checked;
 		}
}
function showInfo(msg){
	 showInfoUL(msg);
	 return;
}
function show(obj){
	$("#hostInfo").text("");
	mywebdb.each({
		name:"nodeList",
		success:function(msg){
			showInfo(msg.value);
		}
	});

}
function notExistsHost(){
	$("#hostInfo").text("");
	mywebdb.each({
		name:"nodeList",
		success:function(msg){
			if(msg != null && msg.value.isExists == undefined)
				showInfo(msg.value);
		}
	});
	return false;
}
function existsHost(){
	$("#hostInfo").text("");
	mywebdb.each({
		name:"nodeList",
		success:function(msg){
			if(msg != null && msg.value.isExists != undefined && msg.value.hadooppath != undefined  && msg.value.rootPassword != undefined)
				showInfo(msg.value);
		}
	});
   
	return false;
}
function showConfigHost(){
	$("#hostInfo").text("");
	try{
	 mywebdb.getAll({
	    	name:"nodeList",
	    	success:function(msg){
	    		for(var i=0;i<msg.length;i++){
	    			if(msg != null){
	    				val = msg[i];
	    				if(val.isExists != undefined && val.hadooppath == undefined  && val.rootPassword != undefined){
	    					showInfo(msg[i]);
	    				}
	    			}//end if
	    		}//end for
	    	},
	    });
	}catch(ex){
	}
	return false;
}

function progresshidden(){
	 $("#progress").css({display:"none"});
}