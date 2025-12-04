/** EasyWeb iframe v3.1.8 date:2020-05-04 License By http://easyweb.vip */

layui.define(['layer', 'element', 'admin'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var element = layui.element;
    var admin = layui.admin;
    var setter = admin.setter;
    var headerDOM = '.layui-layout-admin>.layui-header';
    var sideDOM = '.layui-layout-admin>.layui-side>.layui-side-scroll';
    var bodyDOM = '.layui-layout-admin>.layui-body';
    var tabDOM = bodyDOM + '>.layui-tab';
    var titleDOM = bodyDOM + '>.layui-body-header';
    var tabFilter = 'admin-pagetabs';
    var navFilter = 'admin-side-nav';
    var tabEndCall = {};  // Tab关闭的事件回调
    var mIsAddTab = false;  // 是否是添加Tab，添加Tab的时候切换不自动刷新
    var index = {homeUrl: undefined, mTabPosition: undefined, mTabList: []};

    /** 渲染主体部分 */
    index.loadView = function (param) {
        if (!param.menuPath) return layer.msg('url不能为空', {icon: 2, anim: 6});
        if (setter.pageTabs) {  // 多标签模式
            var flag;  // 选项卡是否已添加
            $(tabDOM + '>.layui-tab-title>li').each(function () {
                if ($(this).attr('lay-id') === param.menuPath) flag = true;
            });
            if (!flag) {  // 添加选项卡
                if (index.mTabList.length + 1 >= setter.maxTabNum) {
                    layer.msg('最多打开' + setter.maxTabNum + '个选项卡', {icon: 2, anim: 6});
                    return admin.activeNav(index.mTabPosition);
                }
                mIsAddTab = true;
                element.tabAdd(tabFilter, {
                    id: param.menuPath, title: '<span class="title">' + (param.menuName || '') + '</span>',
                    content: '<iframe class="admin-iframe" lay-id="' + param.menuPath + '" src="' + param.menuPath +
                        '" onload="layui.index.hideLoading(this);" frameborder="0"></iframe>'
                });
                admin.showLoading({elem: $('iframe[lay-id="' + param.menuPath + '"]').parent(), size: ''});
                if (param.menuPath !== index.homeUrl) index.mTabList.push(param);  // 记录tab
                if (setter.cacheTab) admin.putTempData('indexTabs', index.mTabList);  // 缓存tab
            }
            if (!param.noChange) element.tabChange(tabFilter, param.menuPath);  // 切换到此tab
        } else {  // 单标签模式
            admin.activeNav(param.menuPath);
            var $contentDom = $(bodyDOM + '>div>.admin-iframe');
            if ($contentDom.length === 0) {
                $(bodyDOM).html([
                    '<div class="layui-body-header">',
                    '   <span class="layui-body-header-title"></span>',
                    '   <span class="layui-breadcrumb pull-right" lay-filter="admin-body-breadcrumb" style="visibility: visible;"></span>',
                    '</div>',
                    '<div style="-webkit-overflow-scrolling: touch;">',
                    '   <iframe class="admin-iframe" lay-id="', param.menuPath, '" src="', param.menuPath, '"',
                    '      onload="layui.index.hideLoading(this);" frameborder="0"></iframe>',
                    '</div>'].join(''));
                admin.showLoading({elem: $('iframe[lay-id="' + param.menuPath + '"]').parent(), size: ''});
            } else {
                admin.showLoading({elem: $contentDom.parent(), size: ''});
                $contentDom.attr('lay-id', param.menuPath).attr('src', param.menuPath);
            }
            $('[lay-filter="admin-body-breadcrumb"]').html(index.getBreadcrumbHtml(param.menuPath));
            index.mTabList.splice(0, index.mTabList.length);
            if (param.menuPath === index.homeUrl) {
                index.mTabPosition = undefined;
                index.setTabTitle($(param.menuName).text() || $(sideDOM + ' [lay-href="' + index.homeUrl + '"]').text() || '主页');
            } else {
                index.mTabPosition = param.menuPath;
                index.mTabList.push(param);
                index.setTabTitle(param.menuName);
            }
            if (!setter.cacheTab) return;
            admin.putTempData('indexTabs', index.mTabList);
            admin.putTempData('tabPosition', index.mTabPosition);
        }
        if (admin.getPageWidth() <= 768) admin.flexible(true); // 移动端自动收起侧导航
    };

    /** 加载主页 */
    index.loadHome = function (param) {
        var cacheTabs = admin.getTempData('indexTabs');  // 获取缓存tab
        var cachePosition = admin.getTempData('tabPosition');
        var recover = (param.loadSetting === undefined || param.loadSetting) && (setter.cacheTab && cacheTabs && cacheTabs.length > 0);
        index.homeUrl = param.menuPath;
        param.noChange = cachePosition ? recover : false;
        if (setter.pageTabs || !recover) index.loadView(param);
        if (recover) {  // 恢复缓存tab
            for (var i = 0; i < cacheTabs.length; i++) {
                cacheTabs[i].noChange = cacheTabs[i].menuPath !== cachePosition;
                if (!cacheTabs[i].noChange || (setter.pageTabs && !param.onlyLast)) index.loadView(cacheTabs[i]);
            }
        }
        admin.removeLoading(undefined, false);
    };

    /** 打开tab */
    index.openTab = function (param) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.index) return top.layui.index.openTab(param);
        if (param.end) tabEndCall[param.url] = param.end;
        index.loadView({menuPath: param.url, menuName: param.title});
    };

    /** 关闭tab */
    index.closeTab = function (url) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.index) return top.layui.index.closeTab(url);
        element.tabDelete(tabFilter, url);
    };

    /** 设置是否记忆Tab */
    index.setTabCache = function (isCache) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.index) return top.layui.index.setTabCache(isCache);
        admin.putSetting('cacheTab', isCache);
        if (!isCache) return index.clearTabCache();
        admin.putTempData('indexTabs', index.mTabList);
        admin.putTempData('tabPosition', index.mTabPosition);
    };

    /** 清除tab记忆 */
    index.clearTabCache = function () {
        admin.putTempData('indexTabs', null);
        admin.putTempData('tabPosition', null);
    };

    /** 设置tab标题 */
    index.setTabTitle = function (title, tabId) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.index) return top.layui.index.setTabTitle(title, tabId);
        if (setter.pageTabs) {
            if (!tabId) tabId = $(tabDOM + '>.layui-tab-title>li.layui-this').attr('lay-id');
            if (tabId) $(tabDOM + '>.layui-tab-title>li[lay-id="' + tabId + '"] .title').html(title || '');
        } else if (title) {
            $(titleDOM + '>.layui-body-header-title').html(title);
            $(titleDOM).addClass('show');
            $(headerDOM).css('box-shadow', '0 1px 0 0 rgba(0, 0, 0, .03)');
        } else {
            $(titleDOM).removeClass('show');
            $(headerDOM).css('box-shadow', '');
        }
    };

    /** 自定义tab标题 */
    index.setTabTitleHtml = function (html) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.index) return top.layui.index.setTabTitleHtml(html);
        if (setter.pageTabs) return;
        if (!html) return $(titleDOM).removeClass('show');
        $(titleDOM).html(html);
        $(titleDOM).addClass('show');
    };

    /** 获取面包屑 */
    index.getBreadcrumb = function (tabId) {
        if (!tabId) tabId = $(bodyDOM + '>div>.admin-iframe').attr('lay-id');
        var breadcrumb = [];
        var $href = $(sideDOM).find('[lay-href="' + tabId + '"]');
        if ($href.length > 0) breadcrumb.push($href.text().replace(/(^\s*)|(\s*$)/g, ''));
        while (true) {
            $href = $href.parent('dd').parent('dl').prev('a');
            if ($href.length === 0) break;
            breadcrumb.unshift($href.text().replace(/(^\s*)|(\s*$)/g, ''));
        }
        return breadcrumb;
    };

    /** 获取面包屑结构 */
    index.getBreadcrumbHtml = function (tabId) {
        var breadcrumb = index.getBreadcrumb(tabId);
        var htmlStr = tabId === index.homeUrl ? '' : ('<a ew-href="' + index.homeUrl + '">首页</a>');
        for (var i = 0; i < breadcrumb.length - 1; i++) {
            if (htmlStr) htmlStr += '<span lay-separator="">/</span>';
            htmlStr += ('<a><cite>' + breadcrumb[i] + '</cite></a>');
        }
        return htmlStr;
    };

    /** 关闭loading */
    index.hideLoading = function (url) {
        if (typeof url !== 'string') url = $(url).attr('lay-id');
        admin.removeLoading($('iframe[lay-id="' + url + '"],' + bodyDOM + ' iframe[lay-id]').parent(), false);
    };

    /** 移动设备遮罩层 */
    var siteShadeDom = '.layui-layout-admin .site-mobile-shade';
    if ($(siteShadeDom).length === 0) $('.layui-layout-admin').append('<div class="site-mobile-shade"></div>');
    $(siteShadeDom).click(function () {
        admin.flexible(true);
    });

    /** 补充tab的dom */
    if (setter.pageTabs && $(tabDOM).length === 0) {
        $(bodyDOM).html([
            '<div class="layui-tab" lay-allowClose="true" lay-filter="', tabFilter, '" lay-autoRefresh="', setter.tabAutoRefresh == 'true', '">',
            '   <ul class="layui-tab-title"></ul><div class="layui-tab-content"></div>',
            '</div>',
            '<div class="layui-icon admin-tabs-control layui-icon-prev" ew-event="leftPage"></div>',
            '<div class="layui-icon admin-tabs-control layui-icon-next" ew-event="rightPage"></div>',
            '<div class="layui-icon admin-tabs-control layui-icon-down">',
            '   <ul class="layui-nav" lay-filter="admin-pagetabs-nav">',
            '      <li class="layui-nav-item" lay-unselect>',
            '         <dl class="layui-nav-child layui-anim-fadein">',
            '            <dd ew-event="closeThisTabs" lay-unselect><a>关闭当前标签页</a></dd>',
            '            <dd ew-event="closeOtherTabs" lay-unselect><a>关闭其它标签页</a></dd>',
            '            <dd ew-event="closeAllTabs" lay-unselect><a>关闭全部标签页</a></dd>',
            '         </dl>',
            '      </li>',
            '   </ul>',
            '</div>'
        ].join(''));
        element.render('nav', 'admin-pagetabs-nav');
    }

    /** 侧导航点击监听 */
    element.on('nav(' + navFilter + ')', function (elem) {
        var $that = $(elem);
        var href = $that.attr('lay-href');
        if (!href || href === '#') return;
        if (href.indexOf('javascript:') === 0) return new Function(href.substring(11))();
        var name = $that.attr('ew-title') || $that.text().replace(/(^\s*)|(\s*$)/g, '');
        var end = $that.attr('ew-end');
        try {
            if (end) end = new Function(end);
            else end = undefined;
        } catch (e) {
            console.error(e);
        }
        index.openTab({url: href, title: name, end: end});
        layui.event.call(this, 'admin', 'side({*})', {href: href});
    });

    /** tab切换监听 */
    element.on('tab(' + tabFilter + ')', function () {
        var layId = $(this).attr('lay-id');
        index.mTabPosition = (layId !== index.homeUrl ? layId : undefined);  // 记录当前Tab位置
        if (setter.cacheTab) admin.putTempData('tabPosition', index.mTabPosition);
        admin.activeNav(layId);
        admin.rollPage('auto');
        if ($(tabDOM).attr('lay-autoRefresh') == 'true' && !mIsAddTab) admin.refresh(layId, true);  // 切换tab刷新
        mIsAddTab = false;
        layui.event.call(this, 'admin', 'tab({*})', {layId: layId});
    });

    /** tab删除监听 */
    element.on('tabDelete(' + tabFilter + ')', function (data) {
        var mTab = index.mTabList[data.index - 1];
        if (mTab) {
            index.mTabList.splice(data.index - 1, 1);
            if (setter.cacheTab) admin.putTempData('indexTabs', index.mTabList);
            tabEndCall[mTab.menuPath] && tabEndCall[mTab.menuPath].call();
            layui.event.call(this, 'admin', 'tabDelete({*})', {layId: mTab.menuPath});
        }
        if ($(tabDOM + '>.layui-tab-title>li.layui-this').length === 0)
            $(tabDOM + '>.layui-tab-title>li:last').trigger('click');  // 解决删除后可能无选中bug
    });

    /** 多系统切换事件 */
    $(document).off('click.navMore').on('click.navMore', '[nav-bind]', function () {
        var navId = $(this).attr('nav-bind');
        $('ul[lay-filter="' + navFilter + '"]').addClass('layui-hide');
        $('ul[nav-id="' + navId + '"]').removeClass('layui-hide');
        $(headerDOM + '>.layui-nav .layui-nav-item').removeClass('layui-this');
        $(this).parent('.layui-nav-item').addClass('layui-this');
        if (admin.getPageWidth() <= 768) admin.flexible(false);  // 展开侧边栏
        layui.event.call(this, 'admin', 'nav({*})', {navId: navId});
    });

    /** 开启Tab右键菜单 */
    if (setter.openTabCtxMenu && setter.pageTabs) {
        layui.use('contextMenu', function () {
            if (!layui.contextMenu) return;
            $(tabDOM + '>.layui-tab-title').off('contextmenu.tab').on('contextmenu.tab', 'li', function (e) {
                var layId = $(this).attr('lay-id');
                layui.contextMenu.show([{
                    icon: 'layui-icon layui-icon-refresh',
                    name: '刷新当前',
                    click: function () {
                        element.tabChange(tabFilter, layId);
                        if ('true' != $(tabDOM).attr('lay-autoRefresh')) admin.refresh(layId);
                    }
                }, {
                    icon: 'layui-icon layui-icon-close-fill ctx-ic-lg',
                    name: '关闭当前',
                    click: function () {
                        admin.closeThisTabs(layId);
                    }
                }, {
                    icon: 'layui-icon layui-icon-unlink',
                    name: '关闭其他',
                    click: function () {
                        admin.closeOtherTabs(layId);
                    }
                }, {
                    icon: 'layui-icon layui-icon-close ctx-ic-lg',
                    name: '关闭全部',
                    click: function () {
                        admin.closeAllTabs();
                    }
                }], e.clientX, e.clientY);
                return false;
            });
        });
    }

    exports('index', index);
});
