function warn(msg){
	layer.confirm(msg ,{icon: 0, title:'提示'});
};
function info(msg){
	layer.confirm(msg ,{icon: 1, title:'提示'});
};
function success(msg){
	layer.msg(msg);
};
function error(msg){
	layer.confirm(msg ,{icon: 2, title:'错误'});
};
function jsConfirm(msg,callback){
layer.confirm(msg ,{icon: 3, title:'提示'},function(index){
	if(index){
		layer.close(index);
		if(callback){
			callback.apply();
		}
	}
   });   
}
// 关闭弹出框
function closeParentWin(){
	if(parent.win){
		parent.closeWin();
	}
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
	});
})

/**
 *  弹出对话框
 * @param dialogTitle
 * @param dialogUrl
 * @param isMax
 * @param h 高度
 * @param w  宽度,可设置'80%'\'800px'\400;
 * @param options
 * @returns
 */
var currentLayer;
function openDialog(dialogTitle,dialogUrl,isMax,w,h,option,maxmin){
	var windowHeight = $(window).height();
	var windowWidth = $(window).width();
	var height_=Math.floor(windowHeight*0.9)+'px';
	var width_='90%';
	if(h){
		if(typeof h=='string'){
			height_=h;
		}else{
			height_=h+'px';
		}
	}
	if(w){
		if(typeof w=='string'){
			width_=w;
		}else{
			width_=w+'px';
		}
	}
	
	if(windowWidth<500){
		width_="90%";
		//height_=Math.floor(windowHeight*0.9)+'px';
	}
	
	var option_=null;
	if(option){
		option_=option;
	}
	if(maxmin==null)
		maxmin=true;
	var tableOptions = $.extend({
		  type: 2,
		  maxmin : maxmin,
		  title: dialogTitle,
		  area: [width_,height_],
		  content: dialogUrl
		},option_);
	currentLayer=layer.open(tableOptions);
	if(isMax){ 
		layer.full(currentLayer);
	}
	return currentLayer;
}

