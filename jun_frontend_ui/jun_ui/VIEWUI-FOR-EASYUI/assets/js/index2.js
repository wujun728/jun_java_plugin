/***********

* 作者：poya QQ：931989338
* 讨论群：149663025
* 更新日期：2017/1/8

* 广告：本人承接各类大中小型管理系统的软件的设计与主题开发，有需要的朋友联系我啦~~~~
**********************************************************/

$(function(){
	InitTopMenu();
	InitLeftMenu("0");//参数选择0为左侧选择第一个
	addFristPage();

	//绑定 div 的鼠标事件
	$('.navmenu-item a').click(function(){
	  $('.navmenu-item a').removeClass("active");//清空已经选择的元素
	  $(this).addClass("active");
	});

	$("#mainPanle").addClass('no_border');//数据展示区域
	$(".navigation-path").parent().parent().addClass('b_path');//路径
	$("#layout_center_plan").addClass('no_border');
	$(".LeftMenu").parent().addClass('b_left');
	$(".LeftMenu").parent().find(".panel-header").addClass('leftMenuTitle');
})
function InitTopMenu(){
    $.each(_menus_oneLeve, function(i, n) {
		var str='<li class="navmenu-item"> ';
		if(i==0){
		 str+='<a class="item active" target="LeftList" id="tommenu_'+n.menuid+'" onClick="InitLeftMenu(\''+n.menuid+'\')"  onfocus="this.blur();">';
		}else{
		 str+='<a class="item" target="LeftList" id="tommenu_'+n.menuid+'" onClick="InitLeftMenu(\''+n.menuid+'\')"  onfocus="this.blur();">';
		}
		str+='<i class="fa '+n.icon+'"></i>';
		str+='<span class="title">'+n.menuname+'</span>';
		str+='</a>';
		str+='</li>';
		
		var s=i;
		$(".viewui-navmenu").append(str);
	});

}

//初始化左侧
function InitLeftMenu(parentMenu) {
	$("#nav").accordion({animate:false});
	var s= $('#nav').accordion('panels');
	var l=s.length;
	for(var i=0;i<l;i++){
		$('#nav').accordion('remove',0);//删除一个后，下一个补充原来的位置所以下标始终为0
	}
    $.each(_menus, function(i, n) {
	 if(n.parentMenu==parentMenu){
		 var menulist ='';
		 menulist +='<ul>';
		 $.each(n.menus, function(j, o) {
			menulist += '<li><a ref="'+o.menuid+'" href="javascript:;" rel="' + o.url + '" ><i class="fa '+o.icon+'" ></i><span class="nav">' + o.menuname + '</span></a></li> ';
		  });
		 menulist += '</ul>';
		 $('#nav').accordion('add', {
            title: n.menuname,
            content: menulist,
            iconCls: 'fa ' + n.icon
        });
	 }
    });

	$('.easyui-accordion li a').click(function(){
		var tabTitle = $(this).children('.nav').text();
		var menuid = $(this).attr("ref");
		addTab(menuid);
		$('.easyui-accordion li').removeClass("selected");
		$(this).parent().addClass("selected");
	}).hover(function(){
		$(this).parent().addClass("hover");
	},function(){
		$(this).parent().removeClass("hover");
	});

	//选中第一个
	var panels = $('#nav').accordion('panels');
	var t = panels[0].panel('options').title;
    $('#nav').accordion('select', t);
}

//获取菜单信息
function getMenu(menuid){
	var m = null;
	$.each(_menus, function(i, n) {
		 $.each(n.menus, function(j, o) {
		 	if(o.menuid==menuid){
				//icon += o.icon;
				m = o;
				return true;
			}
		})
	})
	return m;
}

 //添加 页面容器tab
function addTab(menuid){
	var menuInfo=getMenu(menuid);
	var opts={id:menuid,title:menuInfo.menuname,closeable:true,content:'',href:menuInfo.url};
	if($("#center_iframe")){
	  $('#center_iframe').attr('name', opts.title);
	  $('#center_iframe').attr('src',opts.href);
	  $('#layout_center_plan').panel( 'setTitle','<ul class="navigation-path"><li><a href="javascript:;" onclick="addFristPage()" >首页</a></li><li>'+opts.title+'</li></ul>');
	}else{
	  createFrame(opts);
	  opts['closable']=false;
	  $('#layout_center_plan').panel(opts);  
	}
}

 //设置 tab 中的 iframe
function createFrame(opts){
	var url=opts.href;
	var $iframe=$("<iframe id='center_iframe' name='"+opts.title+"'  class='main_iframe' src='"+url+"' width='100%' height='100%' allowfullscreen></iframe>");
	opts.content=$iframe;
	opts.href="";
}
	 
//打开首页
function addFristPage(){
	var $iframe=$("<iframe id='center_iframe' class='main_iframe' border='1'  scrolling='auto' src='kanban.html' width='100%' height='100%'></iframe>");
	$('#layout_center_plan').panel( {
		title:'<ul class="navigation-path"><li><a href="javascript:;" onclick="addFristPage()" >首页</a></li></ul>',
		closable:false,
		content:$iframe
	});
}

function firstrefresh (){
	 $('#center_iframe').attr('src', $('#center_iframe').attr('src'));
}
	 
//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}
