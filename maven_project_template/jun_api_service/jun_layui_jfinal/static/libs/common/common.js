/**
 * 改变DIV内容
 * 
 * @param url
 * @param targetDivId
 *            目标DIV
 * @param callback
 *            回调方法（可选）
 */
function changeDivContent(url,targetDivId,callback){
	var $contentBody = $('#'+targetDivId);
// $contentBody.showLoading();//显示loading..
	$contentBody.fadeTo('fast',0.6);
	$('#'+targetDivId).load(url,{js_:'1'},function(responseText, textStatus, XMLHttpRequest){
// $contentBody.hideLoading();
		$contentBody.fadeTo('fast',1);
		if($.type(callback)=='function'){
			callback.call(this,responseText, textStatus, XMLHttpRequest);
		}
	});
}

// 阻止事件(包括冒泡和默认行为)
var stopEvent = function(e){
  e = e || window.event;
  if(e.preventDefault) {
    e.preventDefault();
    e.stopPropagation();
  }else{
    e.returnValue = false;
    e.cancelBubble = true;
  }
}
// 阻止向上冒泡事件
var stopPropagation = function(e){
  e = e || window.event;
  e.stopPropagation();
}
function log(obj){
	try{
		if((typeof console)!='undefined'){
			console.log(obj);
		}
	}catch(e){
		
	}
}
// 关闭弹出框
function closeParentWin(){
	if(parent.win){
		parent.closeWin();
	}
}
// 自动加载页面
function autoLoad(){
	$('div[load]').not('[done="true"]').attr('done',true).each(function(){
		var url=$(this).attr('url');
		if(url){
			$(this).load(url,autoLoad);
		}
	});
}
// 表单关闭按钮
$(function(){
	if(top&&top.openTab){//是否web端打开
		$('#closeWinBtn').bind('click',function(){
			closeParentWin();
			if(top&&top.f_addTab)top.closeCurrentTab();
		});
	}else{
		// 在手机APP上显示不用关闭按钮
		$('#closeWinBtn').hide();
	}
	autoLoad();
});
$(function(){
	// 转换为json对象
	$.fn.toJson = function(){
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			var name=this.name;
			var value=this.value;
			var names=[];
			var ename=name;
			var ename2;
			if(/\./.test(name)){
				names=name.split('.');
				log(names);
				ename=names[0];
				ename2=names[1];
				log(ename2);
			}else{
				
			}
				var key;
				var obj;
				log(ename);
				log((/\[\w+\]$/.test(ename)));
				if((/\[\w+\]$/.test(ename))){
					key=ename.replace(ename.substring(0,ename.lastIndexOf('[')),'');
					key=key.replace(']','').replace('[','');
					obj={};
				}
				ename=ename.replace(ename.substring(ename.lastIndexOf('['),ename.lastIndexOf(']')+1),'');
				log(ename);
				var mp=o[ename];
				if (mp) {
					if(key){
						if(o[ename][key]){
							if(ename2&&o[ename][key][ename2]){
								if(!o[ename][key][ename2].push){
									o[ename][key][ename2]=[o[ename][key][ename2]];
								}
								o[ename][key][ename2].push(value||'');
							}else{
								o[ename][key][ename2]=value;
							}
							
						}else{
							if(ename2){
								o[ename][key]={};
								o[ename][key][ename2]=value;
							}else{
								o[ename][key]=value;
							}
						}
					}else{
						if(!mp.push){
							o[ename]=[o[ename]];
						}	
						o[ename].push(value|| '');
					}
				}else{
					if(key&&ename2){
						o[ename]={};
						o[ename][key]={};
						o[ename][key][ename2]=value;
					}else if(key){
						o[ename]={};
						o[ename][key]=value;
					}else{
						o[ename] =value|| '';
					}
				}
		});
		return o;
	};
});
// 格式化日期
Date.prototype.format = function(format){ 
	var o = { 
	"M+" : this.getMonth()+1, // month
	"d+" : this.getDate(), // day
	"D+" : this.getDate(), // day
	"h+" : this.getHours(), // hour
	"H+" : this.getHours(), // hour
	"m+" : this.getMinutes(), // minute
	"s+" : this.getSeconds(), // second
	"q+" : Math.floor((this.getMonth()+3)/3), // quarter
	"S" : this.getMilliseconds() // millisecond
	} 

	if(/(y+)/.test(format)||/(Y+)/.test(format)) { 
	format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 

	for(var k in o) { 
	if(new RegExp("("+ k +")").test(format)) { 
	format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
	} 
	} 
	return format; 
} 
function changeUrl(url){
	window.location=url;
}
// 关闭弹出框
function closeParentWin(){
	if(parent.closeCurrentLayer){
		parent.closeCurrentLayer();
	}
}

$(function(){
	$('#closeWinBtn').bind('click',function(){
		closeParentWin();
		if(top&&top.f_addTab)top.closeCurrentTab();
	});
})
//打开新的选项卡
function openTab(title,url){
	top.openTab(title,url);
}
//删除当前页的选项卡
function deleteTab(){
	top.deleteTab();
}