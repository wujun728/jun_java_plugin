layui.define(['element', 'layer', 'util', 'pagesize', 'form'], function (exports) {
    var $ = layui.jquery;
    var element = layui.element();
    var layer = layui.layer;
    var util = layui.util;
    var form = layui.form();
    //快捷菜单开关
    $('span.sys-title').click(function (e) {
        e.stopPropagation();
        $('div.short-menu').slideToggle('fast');
    });
    $('div.short-menu').click(function (e) {
        e.stopPropagation();
    });
    $(document).click(function () {
        $('div.short-menu').slideUp('fast');
        $('.individuation').removeClass('bounceInRight').addClass('flipOutY');
    });
    //个性化设置开关
    $('#individuation').click(function (e) {
        e.stopPropagation();
        $('.individuation').removeClass('layui-hide').toggleClass('bounceInRight').toggleClass('flipOutY');
    });
    $('.individuation').click(function (e) {
        e.stopPropagation();
    })
    $('.layui-body').click(function () {
        $('.individuation').removeClass('bounceInRight').addClass('flipOutY');
    });
    //监听左侧导航点击
    element.on('nav(leftnav)', function (elem) {
        var url = $(elem).children('a').attr('data-url');   //页面url
        var id = $(elem).children('a').attr('data-id');     //tab唯一Id
        var title = $(elem).children('a').text();           //菜单名称
        if (title == "首页") {
            element.tabChange('tab', 0);
            return;
        }
        if (url == undefined) return;

        var tabTitleDiv = $('.layui-tab[lay-filter=\'tab\']').children('.layui-tab-title');
        var exist = tabTitleDiv.find('li[lay-id=' + id + ']');
        if (exist.length > 0) {
            //切换到指定索引的卡片
            element.tabChange('tab', id);
        } else {
            setTimeout(function () {
                //模拟菜单加载
                element.tabAdd('tab', { title: title, content: '<iframe src="' + url + '" style="width:100%;height:100%;border:none;outline:none;"></iframe>', id: id });
                //切换到指定索引的卡片
                element.tabChange('tab', id);
            }, 500);
        }
    });
    //监听侧边导航开关
    form.on('switch(sidenav)', function (data) {
        if (data.elem.checked) {
            showSideNav();
        } else {
            hideSideNav();
        }
    });

    //收起侧边导航点击事件
    $('.layui-side-hide').click(function () {
        hideSideNav();
        $('input[lay-filter=sidenav]').siblings('.layui-form-switch').removeClass('layui-form-onswitch');
        $('input[lay-filter=sidenav]').prop("checked", false);
    });

    //鼠标靠左展开侧边导航
    $(document).mousemove(function (e) {
        if (e.pageX == 0) {
            showSideNav();
            $('input[lay-filter=sidenav]').siblings('.layui-form-switch').addClass('layui-form-onswitch');
            $('input[lay-filter=sidenav]').prop("checked", true);
        }
    });

    //皮肤切换
    $('.skin').click(function () {
        var skin = $(this).attr("data-skin");
        $('body').removeClass();
        $('body').addClass(skin);
    });

    var ishide = false;
    //隐藏侧边导航
    function hideSideNav() {
        if (!ishide) {
            $('.layui-side').animate({ left: '-200px' });
            $('.layui-side-hide').animate({ left: '-200px' });
            $('.layui-body').animate({ left: '0px' });
            $('.layui-footer').animate({ left: '0px' });
            var tishi = layer.msg('鼠标靠左自动显示菜单', { time: 1500 });
            layer.style(tishi, {
                top: 'auto',
                bottom: '50px'
            });
            ishide = true;
        }
    }
    //显示侧边导航
    function showSideNav() {
        if (ishide) {
            $('.layui-side').animate({ left: '0px' });
            $('.layui-side-hide').animate({ left: '0px' });
            $('.layui-body').animate({ left: '200px' });
            $('.layui-footer').animate({ left: '200px' });
            ishide = false;
        }
    }
    exports('main', {});
});
