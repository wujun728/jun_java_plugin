/**
 * @Description: 主页面
 * @Copyright: 2017 www.fallsea.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.6.1
 * @License：MIT
 */

layui.use(['layer','fsTab',"element","form"], function(){
	var element = layui.element;
	var fsTab = layui.fsTab;
	var form = layui.form;
	intiCompanySelect();

	//初始化显示菜单
	showMenu($("#fsTopMenu li.layui-this").attr("dataPid"));


	window.addEventListener("hashchange", hashChanged, false);

	hashChanged();
	
	function intiCompanySelect(){
		$("#changeCompanyButton").click(function(){
			layer.open({
				  type: 1
				  ,title: false //不显示标题栏
				  ,closeBtn: false
				  ,area: ['500px', '300px']
				  ,shade: 0.8
				  ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
				  ,resize: false
				  ,btn: ['确定', '取消']
				  ,btnAlign: 'c'
				  ,moveType: 1 //拖拽模式，0或者1
				  ,content: $("#selectCompany")
				  ,yes: function(index,layero){
					  var orgId = $("#companyList").val();
					  window.location.href=Common.ctxPath+"/changeOrg.do?orgId="+orgId;
				  }
				});
		});
	}

	function hashChanged(){
		//获取路由信息
		var hash = window.location.hash;
		if(!$.isEmpty(hash) && hash.length>1){
			var menuId = hash.substring(1);
			//获取layId
			var dom = $('#fsLeftMenu a[menuId="'+ menuId +'"]').parent();
			if(dom.length>0){
				var layId = dom.attr("lay-id");
				if($.isEmpty(layId)){
					layId = $.uuid();
					dom.attr("lay-id",layId);
					fsTab.add(dom.find("a").html(),dom.find("a").attr("dataUrl"),layId);
				}
				fsTab.tabChange(layId);

				fsTab.menuSelectCss(layId);
			}
		}
	}


	$("#fsTopMenu li").bind("click",function(){
		var dataPid = $(this).attr("dataPid");
		showMenu(dataPid);
	});


	//显示菜单
	function showMenu(dataPid){
		if(!$.isEmpty(dataPid)){
			$('#fsLeftMenu>li').hide();
			$('#fsLeftMenu>li[dataPid="'+ dataPid +'"]').show();
		}
	}

	//渲染tab
	fsTab.render();

	//新增tab
	function addTab(title,dataUrl,layId){
		fsTab.add(title,dataUrl,layId);
	}

	//手机设备的简单适配
	var treeMobile = $('.site-tree-mobile'),
	shadeMobile = $('.site-mobile-shade')

	treeMobile.on('click', function(){
		$('body').addClass('site-mobile');
	});

	shadeMobile.on('click', function(){
		$('body').removeClass('site-mobile');
	});


	//菜单绑定

	$(".fsSwitchMenu").on("click",function(){
		
		if($(this).find("i.icon-category").length>0){
			$(this).find("i").removeClass("icon-category").addClass("icon-viewgallery");
		}else{
			$(this).find("i").removeClass("icon-viewgallery").addClass("icon-category");
		}
		$(".layui-layout-admin").toggleClass("showMenu");
	});

});