$(document).ready(function(){
	jQuery.ajaxSetup({
		beforeSend:function(e){
			var _that=this;
			var _url=_that.url;
			if(!springrain.ajaxFireWallHosts(_url)){
				if(_url.indexOf("springraintoken")!=-1)return;
				if(_url.indexOf("?")!=-1){
					_that.url= _url+"&springraintoken="+springraintoken;
				}else{
					_that.url= _url+"?springraintoken="+springraintoken;
				}
			}
		},
		complete:function(data){
			var _that=this;
			var _url=_that.url;
			if(!springrain.ajaxFireWallHosts(_url)){
				try{
					var _obj=data.responseText;
					_obj=eval("("+_obj+")");
					if(_obj.statusCode=='relogin'){
						this.success=null;
						springrain.info("登录超时，请重新登录.", null);
						setTimeout(function(){
							window.location.href=ctx+"/s/"+getDefaultSiteId()+"/login";
						},1000);
					}
				}catch(e){
					springrain.info(e, null);
				}
			}
			
		}
	});
	//初始化插件
	configLayui("global");
	//加载菜单
	loadMenu();
	init_sort_btn();
	init_button_action();
	//加载站点logo页脚等信息
	loadSiteInfo();
	//赋予 元素特殊事件 ，和表单的样式处理。如TAB和菜单 的滑过事件 ,不添加没有动画效果，且必须加到ready后
	setTimeout(function(){
		if(jQuery("form").length>0){
			layui.use('form', function(){
				  form = layui.form();
			});
		}
		layui.use(['element'], function(){
			  var element = layui.element();   
		});
	},200);
	//修改 修改密码的链接
	var siteId = $.cookie('defaultSiteId');
	jQuery("#modifypwd").attr("href","javascript:springrain.goTo('"+ctx+"/s/password/"+siteId+"/modifiypwd/pre')");
});
var form;
/*添加form的监听回调*/
function selectListener(filterId,callback){
	if(form==null||form==undefined){
		if(jQuery("form").length>0){
			layui.use('form', function(){
				  form = layui.form();
			});
		}else{
			return;
		}
	}
	form.on('select('+filterId+')', function(data){
		callback(data);
	});
}
function loadMenu(){
	
	//加载菜单
    if(!(!!locache.get("menuData"))){//没有数据
    	ajaxmenu();
    }else{
    	var menuData = locache.get("menuData");
    	buildModule(menuData);
    }
}


function exit(){
		springrain.confirm("确定退出？", function(){
			var siteId = $.cookie('defaultSiteId');
			try{
				locache.flush();
			}catch(e){}
			
			if(!!siteId){
				_url=ctx+"/s/"+siteId+"/logout";
				springrain.goTo(_url);
			}
			
		});
	
}


function getDefaultSiteId(){
	return  $.cookie('defaultSiteId');
}




function configLayui(par){
	layui.config({
		  base: ctx+"/layui/lay/modules/"
		}).use(par);
}



/**
 * 获取所有导航资源
 */
function ajaxmenu() {
    jQuery.ajax({
        url : ctx + "/s/"+getDefaultSiteId()+"/menu/leftMenu",
        type : "post",
        data:{"springraintoken":springraintoken},
        cache : false,
        async : false,
        scriptCharset : "utf-8",
        dataType : "json",
        success : function(_json) {
            if(_json.status=="success"){
            	locache.set("menuData",_json.data[0].leaf[0].leaf);
            	var menuData = locache.get("menuData");
                buildModule(menuData);
            }
        }
    });
}

function buildModule(data) {
    if (data != null && typeof (data) != "undefined") {
        var htmlStr = '';
        /*处理/update时丢了菜单 状态*/
        var _url=window.location.pathname;
        if(_url.indexOf('/update/pre')!=-1){
        	_url=_url.substring(0,_url.indexOf("/update/pre"))+"/list";
        } 
        /*处理/update时丢了菜单 状态*/
        var childrenMenuList = null;
        for ( var i = 0; i < data.length; i++) {
            var url = data[i].pageurl;
            var tmpData = data[i]['leaf'][0];
            while(!!tmpData){
                url = tmpData.pageurl;
                tmpData = tmpData['leaf'][0];
            }
            var tmpPid = locache.get("currentPagePid");
            var menuIcon_df="&#xe63c;";
            if(data[i].menuIcon!=null){
            	menuIcon_df=data[i].menuIcon;
            }
            if((tmpPid == data[i].id) || (!(!!tmpPid) && i==0)){//url中有第一个菜单的键值
            	url = ctx + url;
            	htmlStr += '<li id="pmenu'+data[i].id+'" class="layui-nav-item layui-this"><a href="javascript:void(0);" data-pid="'+data[i].id+'" data-action="'+url+'"><i class="layui-icon">'+menuIcon_df+'</i><cite>'+data[i].name+'</cite></a></li>';
                childrenMenuList = data[i]['leaf'];
                $("ul.site-demo-title").prepend('<li class="layui-this">\
	             		<i class="layui-icon">&#xe630;</i>\
	             		<span class="layui-breadcrumb" style="visibility: visible;" id="neckNavi">\
						  <a><cite>'+data[i].name+'</cite></a><span class="layui-box">&gt;</span>\
						</span>\
             		</li>');
            }else{
            	url = ctx + url;
                htmlStr += '<li id="pmenu'+data[i].id+'" class="layui-nav-item"><a href="javascript:void(0);" data-pid="'+data[i].id+'" data-action="'+url+'"><i class="layui-icon">'+menuIcon_df+'</i><cite>'+data[i].name+'</cite></a></li>';
            }
        }
        $("#naviHeaderMenu").html(htmlStr);
        $("#menu").html(getParentModule(childrenMenuList));
    }
}


function getParentModule(childrenMenuList) {
    var htmlStr = '';
    var _url=window.location.pathname;
    if(_url.indexOf('/update/pre')!=-1){
    	_url=_url.substring(0,_url.indexOf("/update/pre"))+"/list";
    } 
    if(_url.indexOf('/look')!=-1){
    	_url=_url.substring(0,_url.indexOf("/look"))+"/list";
    } 
    for(var i=0;i<childrenMenuList.length;i++){
    	 var showItem = "";
        if((ctx+childrenMenuList[i].pageurl) ==_url){
        	showItem = "layui-this";
        	$("#neckNavi").append('<a><cite>'+childrenMenuList[i].name+'</cite></a>');
        }
        
        var _leaf=childrenMenuList[i]["leaf"];
        var menuIcon_df="&#xe63c;";
        if(childrenMenuList[i].menuIcon!=null){
        	menuIcon_df=childrenMenuList[i].menuIcon;
        }
        if(_leaf&&_leaf.length>0){
        	 htmlStr += '<li class="layui-nav-item layui-nav-itemed'+showItem+'" id="'+childrenMenuList[i].id+'"><a href="';
            htmlStr = htmlStr+ ' javascript:;"> <i class="layui-icon">'+menuIcon_df+'</i><cute>'+childrenMenuList[i].name+'</cute></a>';
            htmlStr = htmlStr+getChindModule(_leaf,childrenMenuList[i]);
        }else{
        	htmlStr += '<li class="layui-nav-item layui-nav-itemed'+showItem+'" id="'+childrenMenuList[i].id+'"><a href="javascript:void(0);" data-pid="'+childrenMenuList[i].pid+'" data-action="';
        	 var url = childrenMenuList[i].pageurl;
             var tmpData = childrenMenuList[i]['leaf'][0];
             while(!!tmpData){
                 url = tmpData.pageurl;
                 tmpData = tmpData['leaf'][0];
             }
             url = ctx+url;
            htmlStr = htmlStr+url+'" ><i class="layui-icon">'+menuIcon_df+'</i><cite>'+childrenMenuList[i].name+'</cite></a>';
        }
        htmlStr = htmlStr+'</li>';
    }
    return htmlStr;
}

function getChindModule(_leaf,parentMenu) {
    var t = '<dl class="layui-nav-child">';
    for ( var menuObj in _leaf) {
        if((ctx+_leaf[menuObj].pageurl)==window.location.pathname){
        	 t = t+'<dd class="layui-this" pageUrl="'+_leaf[menuObj].pageurl+'" id="'+_leaf[menuObj].id+'"><a href="javascript:void(0);" data-action="'+ctx+_leaf[menuObj].pageurl+'"><i class="layui-icon">'+_leaf[menuObj].menuIcon+'</i><cite>'+_leaf[menuObj].name+'</cite></a></dd>';
        	 $("#neckNavi").append('<a><cite>'+parentMenu.name+'</cite></a><span class="layui-box">&gt;</span>');
        	 $("#neckNavi").append('<a><cite>'+_leaf[menuObj].name+'</cite></a>');
        }else{
        	t = t+'<dd pageUrl="'+_leaf[menuObj].pageurl+'" id="'+_leaf[menuObj].id+'"><a href="javascript:void(0);" data-action="'+ctx+_leaf[menuObj].pageurl+'"><i class="layui-icon">'+_leaf[menuObj].menuIcon+'</i><cite>'+_leaf[menuObj].name+'</cite></a></dd>';
        }
    }
    t = t+'</dl>';
    return t;
}


/**
 * 获取url中的hash值，例如http://www.baidu.com#keyword,返回['keyword']
 * @returns 数组
 */
function getLocationHashArr(){
    var urlHash = window.location.hash;
    if(!!urlHash){
        var urlHashArr = urlHash.split("#");
        urlHashArr.splice(0,1);
        return urlHashArr;
    }
    return new Array();
}
function gentimestampstr(){
	return new Date().getTime();
}
function getcurrentMenuId(){
	var currentPageUrl=window.location.href;
	var urlElementArr=currentPageUrl.split("?");
	var menuId='';
	if(urlElementArr.length>1){//非首页
		var paramElementArr=urlElementArr[1].split("#");
		menuId= paramElementArr[0].split("=")[1];
		menuId=menuId.replace("&t","");
	}
	return menuId;
}
 

/**
 * 批量删除
 * @param _url
 * @param formId
 * @param listage
 * @returns {Boolean}
 */
function mydeletemore(_url, formId,listage) {
	var arr = new Array();
	var checks = jQuery(":checkbox[name='check_li']");
	checks.each(function(i, _obj) {
		if (_obj.checked) {
			arr.push(_obj.value);
		}

	});
	if (arr.length < 1) {
		myalert("请选择要删除的记录!");
		return false;
	}
	
	myconfirm("确定删除选中数据?", function() {
		myhref2page(_url,listage,"records=" + arr.join(","));
	});
}




function myexport(formId, _url) {
	var _form = document.getElementById(formId);
	var _action = _form.action;
	_form.action = _url;
	_form.submit();
	_form.action = _action;
}
/* 赋值 */
function set_val(name, val) {
	if ($("#" + name + " option").length > 0) {
		//按老的UI不动是这个
		//$("#" + name).val(val);
		//按新的layerui只能，模拟点击
		jQuery("#"+name).siblings("div").filter(".layui-form-select").eq(0).find("dd[lay-value='"+val+"']").trigger("click");
		return;
	}

	if (($("#" + name).attr("type")) === "checkbox") {
		if (val == 1) {
			$("#" + name).attr("checked", true);
			return;
		}
	}
	if ($("." + name).length > 0) {
		if (($("." + name).first().attr("type")) === "checkbox") {
			var arr_val = val.split(",");
			for ( var s in arr_val) {
				$("input." + name + "[value=" + arr_val[s] + "]").attr(
						"checked", true);
			}
		}
	}
	if (($("#" + name).attr("type")) === "text") {
		if(typeof val==="number"&&val.length>11){
			try{
				val=getSmpFormatDateByLong(val);
				$("#" + name).val(val);
				return;
			}catch(e){
				$("#" + name).val(val);
				return;
			}
		}
		$("#" + name).val(val);
		return;
	}
	if (($("#" + name).attr("type")) === "hidden") {
		$("#" + name).val(val);
		return;
	}
	if (($("#" + name).attr("rows")) > 0) {
		$("#" + name).val(val);
		return;
	}
}
function  init_sort_btn(){
	jQuery("th[id^='th_']").each(function(_i,_o){
		jQuery(_o).append('<i class="layui-icon sort-icon-up sort-icon">&#xe619;</i><i class="layui-icon sort-icon-down sort-icon">&#xe61a;</i>');
	});
	
	//加载颜色
	var _sort=jQuery("#page_sort").val();
	var _order=jQuery("#page_order").val();
	if(_order){
		if("asc"==_sort){
			jQuery("#th_"+_order).find(".sort-icon-up").css("color","#333333");
		}else{
			jQuery("#th_"+_order).find(".sort-icon-down").css("color","#333333");
		}
	}
	
	jQuery(".sort-icon").bind("mouseenter",function(){
		jQuery(this).addClass("sort-icon-on");
	}).bind("mouseout",function(){
		jQuery(this).removeClass("sort-icon-on");
	});
	//单击事件
	jQuery(".sort-icon").bind("click",function(){
		if(jQuery(this).hasClass("sort-icon-down")){
			jQuery("#page_sort").val("desc");
			jQuery("#page_order").val(jQuery(this).parent("th").attr("id").split("_")[1]);
		}else{
			jQuery("#page_sort").val("asc");
			jQuery("#page_order").val(jQuery(this).parent("th").attr("id").split("_")[1]);
		}
		//提交表单
		springrain.commonSubmit("searchForm");
		//$("#searchForm").submit();
	});
}
function init_button_action(){
	jQuery("button[data-action]").bind("click",function(){
		if(!!$(this).attr("data-pid"))
			locache.set("currentPagePid",$(this).attr("data-pid"));
		window.location.href=springrain.appendToken(jQuery(this).attr("data-action"));
	});
	jQuery("a[data-action]").bind("click",function(){
		if(!!$(this).attr("data-pid"))
			locache.set("currentPagePid",$(this).attr("data-pid"));
		window.location.href=springrain.appendToken(jQuery(this).attr("data-action"));
	});
}

function loadSiteInfo(){
	var siteLogo = locache.get("defaultSiteLogoUrl");
	var siteFooter = locache.get("defaultSiteFooter");
	$("#siteLogo").attr("src",ctx+siteLogo);
	$("#siteFooter").html(siteFooter);
	$("a.logo").attr('href','javascript:springrain.goTo("'+ctx+'/s/'+getDefaultSiteId()+'/index?springraintoken='+springraintoken+'")');
}
