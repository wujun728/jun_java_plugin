/** index.js By Beginner Emain:zheng_jinfan@126.com HomePage:http://www.zhengjinfan.cn */

var tab;

layui.config({
    base: 'static/libs/ext/',
    version: new Date().getTime()
}).use(['element', 'layer', 'navbar', 'tab'], function () {
    var element = layui.element,
        $ = layui.jquery,
        layer = layui.layer,
        navbar = layui.navbar(),
        sideTips;
    tab = layui.tab({
        elem: '.admin-nav-card' //设置选项卡容器
        ,
        maxSetting: {
        	max: 15,
        	tipMsg: '只能开15个哇，不能再开了。真的。'
        },
        contextMenu: true,
        onSwitch: function (data) {
            //console.log(data.id); //当前Tab的Id
            //console.log(data.index); //得到当前Tab的所在下标
            //console.log(data.elem); //得到当前的Tab大容器

            //console.log(tab.getCurrentTabId())
        },
        closeBefore: function (obj) { //tab 关闭之前触发的事件
            //console.log(obj);
            //obj.title  -- 标题
            //obj.url    -- 链接地址
            //obj.id     -- id
            //obj.tabId  -- lay-id
            if (obj.title === 'BTable') {
                layer.confirm('确定要关闭' + obj.title + '吗?', { icon: 3, title: '系统提示' }, function (index) {
                    //因为confirm是非阻塞的，所以这里关闭当前tab需要调用一下deleteTab方法
                    tab.deleteTab(obj.tabId);
                    layer.close(index);
                });
                //返回true会直接关闭当前tab
                return false;
            }else if(obj.title==='表单'){
                layer.confirm('未保存的数据可能会丢失哦，确定要关闭吗?', { icon: 3, title: '系统提示' }, function (index) {
                    tab.deleteTab(obj.tabId);
                    layer.close(index);
                });
                return false;
            }
            return true;
        }
    });
    //iframe自适应
    $(window).on('resize', function () {
        var $content = $('.admin-nav-card .layui-tab-content');
        $content.height($(this).height() - topHeight);
        $content.find('iframe').each(function () {
            $(this).height($content.height());
        });
    }).resize();

    //设置navbar
    navbar.set({
    	titleName:'text',
    	hrefName:'url',
        spreadOne: true,
        elem: '#admin-navbar-side',
        cached: true,
        data: navData
		/*cached:true,
		url: 'datas/nav.json'*/
    });
    //渲染navbar
    navbar.render();
    //监听点击事件
    navbar.on('click(side)', function (data) {
        tab.tabAdd(data.field);
    });
    //清除缓存
    $('#clearCached').on('click', function () {
        navbar.cleanCached();
        layer.alert('清除完成!', { icon: 1, title: '系统提示' }, function () {
            location.reload();//刷新
        });
    });
      //.admin-side-toggle
    $('.admin-side-toggle-new').on('click', function () {
        var sideWidth = $('#admin-side').width();
        var itemed=$('ul>li.layui-nav-itemed');
        var lasItemed=$('ul>li.check');
        if (sideWidth > 200) {        	
        	setDivWidth(60);
        	showAndHide('none');
        	if(lasItemed.length>0){
        		$('ul>li.check').removeClass('check');
        	}
        	itemed.addClass('check');
        	itemed.removeClass('layui-nav-itemed');
        	
        	
        	$(this).find("i").css({ transform: 'rotate(90deg)' });
        	
        } else {
        	lasItemed.addClass('layui-nav-itemed');
        	showAndHide('');
        	setDivWidth(220);
        	$(this).find("i").css({ transform: 'rotate(0deg)' });
        }
    });
    
    $('ul.beg-navbar>li').on('click',function () {
    	var sideWidth = $('#admin-side').width();
    	if(sideWidth<220){
    		setDivWidth(220);
    		showAndHide('');   
    		layer.close(sideTips);
    	}
    });
    //鼠标悬浮事件，显示菜单标题
    $('ul.beg-navbar>li').on({
        mouseenter:function(){
        	var aEl = $(this).children('a');
        	var citeEl = aEl.children('cite');
        	if(citeEl.css('display') == 'none'
        		||$('#admin-side').width()<200){
        		sideTips = layer.tips(citeEl.text(), aEl, {
                    tips: [2, '#000'],time:0
                });
        	}
        },
        mouseleave:function(){
        	layer.close(sideTips);
        }
    });
    function showAndHide(display){
      	 $('ul.beg-navbar>li>a>cite').css('display',display);
      	 $('ul.beg-navbar>li>a>span').css('display',display);
      	 $('#menu_tip').css('display',display);
      }
    function setDivWidth(w){
    	$('#admin-body').animate({
    		left: w+'px'
    	});
    	$('#admin-footer').animate({
    		left: w+'px'
    	});
    	$('#admin-side').animate({
    		width: w+'px'
    	});
    	$('#admin-side .layui-nav-tree').animate({
    		width: w+'px'
    	});
    }
    
    $('.admin-side-full-new').on('click', function () {
        var docElm = document.documentElement;
        //W3C  
        if (docElm.requestFullscreen) {
            docElm.requestFullscreen();
        }
        //FireFox  
        else if (docElm.mozRequestFullScreen) {
            docElm.mozRequestFullScreen();
        }
        //Chrome等  
        else if (docElm.webkitRequestFullScreen) {
            docElm.webkitRequestFullScreen();
        }
        //IE11
        else if (elem.msRequestFullscreen) {
            elem.msRequestFullscreen();
        }
        layer.msg('按Esc即可退出全屏');
    });

    $('#setting').on('click', function () {
        tab.tabAdd({
            href: '/Manage/Account/Setting/',
            icon: 'fa-gear',
            title: '设置'
        });
    });

    //锁屏
    $(document).on('keydown', function () {
        var e = window.event;
        if (e.keyCode === 76 && e.altKey) {
            //alert("你按下了alt+l");
            lock($, layer);
        }
    });
    $('#lock').on('click', function () {
        lock($, layer);
    });
    
    
    //手机设备的简单适配
    var treeMobile = $('.site-tree-mobile'),
        shadeMobile = $('.site-mobile-shade');
    treeMobile.on('click', function () {
        $('body').addClass('site-mobile');
    });
    shadeMobile.on('click', function () {
        $('body').removeClass('site-mobile');
    });
});

var isShowLock = false;
function lock($, layer) {
    if (isShowLock)
        return;
    //自定页
    layer.open({
        title: false,
        type: 1,
        closeBtn: 0,
        anim: 6,
        content: $('#lock-temp').html(),
        shade: [0.9, '#393D49'],
        success: function (layero, lockIndex) {
            isShowLock = true;
            //给显示用户名赋值
            //layero.find('div#lockUserName').text('admin');
            //layero.find('input[name=username]').val('admin');
            layero.find('input[name=password]').on('focus', function () {
                var $this = $(this);
                if ($this.val() === '输入密码解锁..') {
                    $this.val('').attr('type', 'password');
                }
            })
                .on('blur', function () {
                    var $this = $(this);
                    if ($this.val() === '' || $this.length === 0) {
                        $this.attr('type', 'text').val('输入密码解锁..');
                    }
                });
            //在此处可以写一个请求到服务端删除相关身份认证，因为考虑到如果浏览器被强制刷新的时候，身份验证还存在的情况			
            //do something...
            //e.g.             
            var $lock = $('div#lock-box');
            var name = $lock.find('div#lockUserName').html();
            $.getJSON('lock', { userName: name}, function (res) {
                if (!res.code=="success") {
                    layer.msg(res.msg);
                }
            }, 'json');

            //绑定解锁按钮的点击事件
            layero.find('button#unlock').on('click', function () {
                var $lockBox = $('div#lock-box');

                var userName = $lockBox.find('div#lockUserName').html();
                var pwd = $lockBox.find('input[name=password]').val();
                if (pwd === '输入密码解锁..' || pwd.length === 0) {
                    layer.msg('请输入密码..', {
                        icon: 2,
                        time: 1000
                    });
                    return;
                }
                unlock(userName, pwd);
            });
			/**
			 * 解锁操作方法
			 * @param {String} 用户名
			 * @param {String} 密码
			 */
            var unlock = function (un, pwd) {
                //console.log(un, pwd);
                //这里可以使用ajax方法解锁
                $.post('unLock', { userName: un, password: pwd }, function (res) {
                    //验证成功
                    if (res.code=="success") {
                        //关闭锁屏层
                        layer.close(lockIndex);
                        isShowLock = false;
                    } else {
                        layer.msg(res.msg, { icon: 2, time: 1000 });
                    }
                }, 'json');
                //isShowLock = false;
                //演示：默认输入密码都算成功
                //关闭锁屏层
                //layer.close(lockIndex);
            };
        }
    });
};
//切换主题
function themeswicth(theme){
	 var loading = layer.load(2, {shade: false, time: 2000 });
	 $.post('switchTheme', { theme: theme}, function (res) {
		 layer.close(loading);
		 if(res.code=="success"){
			// layer.confirm("切换成功，立即刷新？", { icon: 2, time: 1000 });
		 }
         window.location.reload();
     }, 'json');
}
