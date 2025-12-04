/** EasyWeb iframe v3.1.8 date:2020-05-04 License By http://easyweb.vip */

layui.define(['layer'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var setter = layui.cache;
    var bodyDOM = '.layui-layout-admin>.layui-body';
    var tabDOM = bodyDOM + '>.layui-tab';
    var sideDOM = '.layui-layout-admin>.layui-side>.layui-side-scroll';
    var headerDOM = '.layui-layout-admin>.layui-header';
    var navFilter = 'admin-side-nav';
    var admin = {version: '3.1.8', layerData: {}};


    /** 设置侧栏折叠 */
    admin.flexible = function (expand) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.admin) return top.layui.admin.flexible(expand);
        var $layout = $('.layui-layout-admin');
        var isExapnd = $layout.hasClass('admin-nav-mini');
        if (expand === undefined) expand = isExapnd;
        if (isExapnd === expand) {
            if (window.sideFlexTimer) clearTimeout(window.sideFlexTimer);
            $layout.addClass('admin-side-flexible');
            window.sideFlexTimer = setTimeout(function () {
                $layout.removeClass('admin-side-flexible');
            }, 600);
            if (expand) {
                admin.hideTableScrollBar();
                $layout.removeClass('admin-nav-mini');
            } else {
                $layout.addClass('admin-nav-mini');
            }
            layui.event.call(this, 'admin', 'flexible({*})', {expand: expand});
        }
    };

    /** 设置导航栏选中 */
    admin.activeNav = function (url) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.admin) return top.layui.admin.activeNav(url);
        if (!url) return console.warn('active url is null');
        $(sideDOM + '>.layui-nav .layui-nav-item .layui-nav-child dd.layui-this').removeClass('layui-this');
        $(sideDOM + '>.layui-nav .layui-nav-item.layui-this').removeClass('layui-this');
        var $a = $(sideDOM + '>.layui-nav a[lay-href="' + url + '"]');
        if ($a.length === 0) return console.warn(url + ' not found');
        var isMini = $('.layui-layout-admin').hasClass('admin-nav-mini');
        if ($(sideDOM + '>.layui-nav').attr('lay-shrink') === '_all') {  // 手风琴效果
            var $pChilds = $a.parent('dd').parents('.layui-nav-child');
            if (!isMini) {
                $(sideDOM + '>.layui-nav .layui-nav-itemed>.layui-nav-child').not($pChilds)
                    .css('display', 'block').slideUp('fast', function () {
                    $(this).css('display', '');
                });
            }
            $(sideDOM + '>.layui-nav .layui-nav-itemed').not($pChilds.parent()).removeClass('layui-nav-itemed');
        }
        $a.parent().addClass('layui-this');  // 选中当前
        // 展开所有父级
        var $asParents = $a.parent('dd').parents('.layui-nav-child').parent();
        if (!isMini) {
            var $childs = $asParents.not('.layui-nav-itemed').children('.layui-nav-child');
            $childs.slideDown('fast', function () {
                if ($(this).is($childs.last())) {
                    $childs.css('display', '');
                    // 菜单超出屏幕自动滚动
                    var topBeyond = $a.offset().top + $a.outerHeight() + 30 - admin.getPageHeight();
                    var topDisparity = 50 + 65 - $a.offset().top;
                    if (topBeyond > 0) {
                        $(sideDOM).animate({'scrollTop': $(sideDOM).scrollTop() + topBeyond}, 300);
                    } else if (topDisparity > 0) {
                        $(sideDOM).animate({'scrollTop': $(sideDOM).scrollTop() - topDisparity}, 300);
                    }
                }
            });
        }
        $asParents.addClass('layui-nav-itemed');
        // 适配多系统模式
        $('ul[lay-filter="' + navFilter + '"]').addClass('layui-hide');
        var $aUl = $a.parents('.layui-nav');
        $aUl.removeClass('layui-hide');
        $(headerDOM + '>.layui-nav>.layui-nav-item').removeClass('layui-this');
        $(headerDOM + '>.layui-nav>.layui-nav-item>a[nav-bind="' + $aUl.attr('nav-id') + '"]').parent().addClass('layui-this');
    };

    /** 右侧弹出 */
    admin.popupRight = function (param) {
        param.anim = -1;
        param.offset = 'r';
        param.move = false;
        param.fixed = true;
        if (param.area === undefined) param.area = '336px';
        if (param.title === undefined) param.title = false;
        if (param.closeBtn === undefined) param.closeBtn = false;
        if (param.shadeClose === undefined) param.shadeClose = true;
        if (param.skin === undefined) param.skin = 'layui-anim layui-anim-rl layui-layer-adminRight';
        return admin.open(param);
    };

    /** 封装layer.open */
    admin.open = function (param) {
        if (param.content && param.type === 2) param.url = undefined;  // 参数纠正
        if (param.url && (param.type === 2 || param.type === undefined)) param.type = 1;  // 参数纠正
        if (param.area === undefined) param.area = param.type === 2 ? ['360px', '300px'] : '360px';
        if (param.offset === undefined) param.offset = '70px';
        if (param.shade === undefined) param.shade = .1;
        if (param.fixed === undefined) param.fixed = false;
        if (param.resize === undefined) param.resize = false;
        if (param.skin === undefined) param.skin = 'layui-layer-admin';
        var eCallBack = param.end;
        param.end = function () {
            layer.closeAll('tips');  // 关闭表单验证的tips
            eCallBack && eCallBack();
        };
        if (param.url) {
            var sCallBack = param.success;
            param.success = function (layero, index) {
                $(layero).data('tpl', param.tpl || '');
                admin.reloadLayer(index, param.url, sCallBack);
            };
        } else if (param.tpl && param.content) {
            param.content = admin.util.tpl(param.content, param.data, setter.tplOpen, setter.tplClose);
        }
        var layIndex = layer.open(param);
        if (param.data) admin.layerData['d' + layIndex] = param.data;
        return layIndex;
    };

    /** 获取弹窗数据 */
    admin.getLayerData = function (index, key) {
        if (index === undefined) {
            index = parent.layer.getFrameIndex(window.name);
            if (index === undefined) return null;
            else return parent.layui.admin.getLayerData(parseInt(index), key);
        } else if (isNaN(index)) {
            index = admin.getLayerIndex(index);
        }
        if (index === undefined) return;
        var layerData = admin.layerData['d' + index];
        if (key && layerData) return layerData[key];
        return layerData;
    };

    /** 放入弹窗数据 */
    admin.putLayerData = function (key, value, index) {
        if (index === undefined) {
            index = parent.layer.getFrameIndex(window.name);
            if (index === undefined) return;
            else return parent.layui.admin.putLayerData(key, value, parseInt(index));
        } else if (isNaN(index)) {
            index = admin.getLayerIndex(index);
        }
        if (index === undefined) return;
        var layerData = admin.getLayerData(index);
        if (!layerData) layerData = {};
        layerData[key] = value;
        admin.layerData['d' + index] = layerData;
    };

    /** 刷新url方式的layer */
    admin.reloadLayer = function (index, url, success) {
        if (typeof url === 'function') {
            success = url;
            url = undefined;
        }
        if (isNaN(index)) index = admin.getLayerIndex(index);
        if (index === undefined) return;
        var $layero = $('#layui-layer' + index);
        if (url === undefined) url = $layero.data('url');
        if (!url) return;
        $layero.data('url', url);
        admin.showLoading($layero);
        admin.ajax({
            url: url,
            dataType: 'html',
            success: function (res) {
                admin.removeLoading($layero, false);
                if (typeof res !== 'string') res = JSON.stringify(res);
                var tpl = $layero.data('tpl');
                // 模板解析
                if (tpl === true || tpl === 'true') {
                    var data = admin.getLayerData(index) || {};
                    data.layerIndex = index;
                    // 模板里面有动态模板处理
                    var $html = $('<div>' + res + '</div>'), tplAll = {};
                    $html.find('script,[tpl-ignore]').each(function (i) {
                        var $this = $(this);
                        tplAll['temp_' + i] = $this[0].outerHTML;
                        $this.after('${temp_' + i + '}').remove();
                    });
                    res = admin.util.tpl($html.html(), data, setter.tplOpen, setter.tplClose);
                    for (var f in tplAll) res = res.replace('${' + f + '}', tplAll[f]);
                }
                $layero.children('.layui-layer-content').html(res);
                admin.renderTpl('#layui-layer' + index + ' [ew-tpl]');
                success && success($layero[0], index);
            }
        });
    };

    /** 封装layer.alert */
    admin.alert = function (content, options, yes) {
        if (typeof options === 'function') {
            yes = options;
            options = {};
        }
        if (options.skin === undefined) options.skin = 'layui-layer-admin';
        if (options.shade === undefined) options.shade = .1;
        return layer.alert(content, options, yes);
    };

    /** 封装layer.confirm */
    admin.confirm = function (content, options, yes, cancel) {
        if (typeof options === 'function') {
            cancel = yes;
            yes = options;
            options = {};
        }
        if (options.skin === undefined) options.skin = 'layui-layer-admin';
        if (options.shade === undefined) options.shade = .1;
        return layer.confirm(content, options, yes, cancel);
    };

    /** 封装layer.prompt */
    admin.prompt = function (options, yes) {
        if (typeof options === 'function') {
            yes = options;
            options = {};
        }
        if (options.skin === undefined) options.skin = 'layui-layer-admin layui-layer-prompt';
        if (options.shade === undefined) options.shade = .1;
        return layer.prompt(options, yes);
    };

    /** 封装ajax请求，返回数据类型为json */
    admin.req = function (url, data, success, method, option) {
        if (typeof data === 'function') {
            option = method;
            method = success;
            success = data;
            data = {};
        }
        if (method !== undefined && typeof method !== 'string') {
            option = method;
            method = undefined;
        }
        if (!method) method = 'GET';
        if (typeof data === 'string') {
            if (!option) option = {};
            if (!option.contentType) option.contentType = 'application/json;charset=UTF-8';
        } else if (setter.reqPutToPost) {
            if ('put' === method.toLowerCase()) {
                method = 'POST';
                data._method = 'PUT';
            } else if ('delete' === method.toLowerCase()) {
                method = 'GET';
                data._method = 'DELETE';
            }
        }
        return admin.ajax($.extend({
            url: (setter.baseServer || '') + url, data: data, type: method, dataType: 'json', success: success
        }, option));
    };

    /** 封装ajax请求 */
    admin.ajax = function (param) {
        var oldParam = admin.util.deepClone(param);
        if (!param.dataType) param.dataType = 'json';
        if (!param.headers) param.headers = {};
        // 统一设置header
        var headers = setter.getAjaxHeaders(param.url);
        //var token = layui.data('LocalData')["Authorization"];
        var token = localStorage.getItem("Authorization");
        headers.push({"authorization":token});
        if (headers) {
            /* for (var i = 0; i < headers.length; i++) {
                if (param.headers[headers[i].name] === undefined){
                    param.headers[headers[i].name] = headers[i].value;
                } 
            } */
            for (var i = 0; i < headers.length; i++) {
                var item = headers[i];
                $.each(item,function(key,value){
                    //console.log(key + ": " + value);
                    param.headers[key] = value;
                });
            }
        }
        if (param.headers["authorization"] === undefined){
            param.headers["authorization"] = token;
        }
        // success预处理
        var success = param.success;
        param.success = function (result, status, xhr) {
            var before = setter.ajaxSuccessBefore(admin.parseJSON(result), param.url, {
                param: oldParam, reload: function (p) {
                    admin.ajax($.extend(true, oldParam, p));
                }, update: function (r) {
                    result = r;
                }, xhr: xhr
            });
            if (before !== false) success && success(result, status, xhr);
            else param.cancel && param.cancel();
        };
        param.error = function (xhr, status) {
            param.success({code: xhr.status, msg: xhr.statusText}, status, xhr);
        };
        // 解决缓存问题
        if (layui.cache.version && (!setter.apiNoCache || param.dataType.toLowerCase() !== 'json')) {
            if (param.url.indexOf('?') === -1) param.url += '?v=';
            else param.url += '&v=';
            if (layui.cache.version === true) param.url += new Date().getTime();
            else param.url += layui.cache.version;
        }
        return $.ajax(param);
    };

    /** 解析json */
    admin.parseJSON = function (str) {
        if (typeof str === 'string') {
            try {
                return JSON.parse(str);
            } catch (e) {
            }
        }
        return str;
    };

    /** 显示加载动画 */
    admin.showLoading = function (elem, type, opacity, size) {
        if (elem !== undefined && (typeof elem !== 'string') && !(elem instanceof $)) {
            type = elem.type;
            opacity = elem.opacity;
            size = elem.size;
            elem = elem.elem;
        }
        if (type === undefined) type = setter.defaultLoading || 1;
        if (size === undefined) size = 'sm';
        if (elem === undefined) elem = 'body';
        var loader = [
            '<div class="ball-loader ' + size + '"><span></span><span></span><span></span><span></span></div>',
            '<div class="rubik-loader ' + size + '"></div>',
            '<div class="signal-loader ' + size + '"><span></span><span></span><span></span><span></span></div>',
            '<div class="layui-loader ' + size + '"><i class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop"></i></div>'
        ];
        $(elem).addClass('page-no-scroll');  // 禁用滚动条
        $(elem).scrollTop(0);
        var $loading = $(elem).children('.page-loading');
        if ($loading.length <= 0) {
            $(elem).append('<div class="page-loading">' + loader[type - 1] + '</div>');
            $loading = $(elem).children('.page-loading');
        }
        if (opacity !== undefined) $loading.css('background-color', 'rgba(255,255,255,' + opacity + ')');
        $loading.show();
    };

    /** 移除加载动画 */
    admin.removeLoading = function (elem, fade, del) {
        if (elem === undefined) elem = 'body';
        if (fade === undefined) fade = true;
        var $loading = $(elem).children('.page-loading');
        if (del) $loading.remove();
        else if (fade) $loading.fadeOut('fast');
        else $loading.hide();
        $(elem).removeClass('page-no-scroll');
    };

    /** 缓存临时数据 */
    admin.putTempData = function (key, value, local) {
        var tableName = local ? setter.tableName : setter.tableName + '_tempData';
        if (value === undefined || value === null) {
            if (local) layui.data(tableName, {key: key, remove: true});
            else layui.sessionData(tableName, {key: key, remove: true});
        } else {
            if (local) layui.data(tableName, {key: key, value: value});
            else layui.sessionData(tableName, {key: key, value: value});
        }
    };

    /** 获取缓存临时数据 */
    admin.getTempData = function (key, local) {
        if (typeof key === 'boolean') {
            local = key;
            key = undefined;
        }
        var tableName = local ? setter.tableName : setter.tableName + '_tempData';
        var tempData = local ? layui.data(tableName) : layui.sessionData(tableName);
        if (!key) return tempData;
        return tempData ? tempData[key] : undefined;
    };

    /** 滑动选项卡 */
    admin.rollPage = function (d) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.admin) return top.layui.admin.rollPage(d);
        var $tabTitle = $(tabDOM + '>.layui-tab-title');
        var left = $tabTitle.scrollLeft();
        if ('left' === d) {
            $tabTitle.animate({'scrollLeft': left - 120}, 100);
        } else if ('auto' === d) {
            var autoLeft = 0;
            $tabTitle.children("li").each(function () {
                if ($(this).hasClass('layui-this')) return false;
                else autoLeft += $(this).outerWidth();
            });
            $tabTitle.animate({'scrollLeft': autoLeft - 120}, 100);
        } else {
            $tabTitle.animate({'scrollLeft': left + 120}, 100);
        }
    };

    /** 刷新当前选项卡 */
    admin.refresh = function (url, isIndex) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.admin) return top.layui.admin.refresh(url);
        var $iframe;
        if (!url) {
            $iframe = $(tabDOM + '>.layui-tab-content>.layui-tab-item.layui-show>.admin-iframe');
            if (!$iframe || $iframe.length <= 0) $iframe = $(bodyDOM + '>div>.admin-iframe');
        } else {
            $iframe = $(tabDOM + '>.layui-tab-content>.layui-tab-item>.admin-iframe[lay-id="' + url + '"]');
            if (!$iframe || $iframe.length <= 0) $iframe = $(bodyDOM + '>.admin-iframe');
        }
        if (!$iframe || !$iframe[0]) return console.warn(url + ' is not found');
        try {
            if (isIndex && $iframe[0].contentWindow.refreshTab) {
                $iframe[0].contentWindow.refreshTab();
            } else {
                admin.showLoading({elem: $iframe.parent(), size: ''});
                $iframe[0].contentWindow.location.reload();
            }
        } catch (e) {
            console.warn(e);
            $iframe.attr('src', $iframe.attr('src'));
        }
    };

    /** 关闭当前选项卡 */
    admin.closeThisTabs = function (url) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.admin) return top.layui.admin.closeThisTabs(url);
        admin.closeTabOperNav();
        var $title = $(tabDOM + '>.layui-tab-title');
        if (!url) {
            if ($title.find('li').first().hasClass('layui-this')) return layer.msg('主页不能关闭', {icon: 2});
            $title.find('li.layui-this').find('.layui-tab-close').trigger('click');
        } else {
            if (url === $title.find('li').first().attr('lay-id')) return layer.msg('主页不能关闭', {icon: 2});
            $title.find('li[lay-id="' + url + '"]').find('.layui-tab-close').trigger('click');
        }
    };

    /** 关闭其他选项卡 */
    admin.closeOtherTabs = function (url) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.admin) return top.layui.admin.closeOtherTabs(url);
        if (!url) {
            $(tabDOM + '>.layui-tab-title li:gt(0):not(.layui-this)').find('.layui-tab-close').trigger('click');
        } else {
            $(tabDOM + '>.layui-tab-title li:gt(0)').each(function () {
                if (url !== $(this).attr('lay-id')) $(this).find('.layui-tab-close').trigger('click');
            });
        }
        admin.closeTabOperNav();
    };

    /** 关闭所有选项卡 */
    admin.closeAllTabs = function () {
        if (window !== top && !admin.isTop() && top.layui && top.layui.admin) return top.layui.admin.closeAllTabs();
        $(tabDOM + '>.layui-tab-title li:gt(0)').find('.layui-tab-close').trigger('click');
        $(tabDOM + '>.layui-tab-title li:eq(0)').trigger('click');
        admin.closeTabOperNav();
    };

    /** 关闭选项卡操作菜单 */
    admin.closeTabOperNav = function () {
        if (window !== top && !admin.isTop() && top.layui && top.layui.admin) return top.layui.admin.closeTabOperNav();
        $('.layui-icon-down .layui-nav .layui-nav-child').removeClass('layui-show');
    };

    /** 设置主题 */
    admin.changeTheme = function (theme, win, noCache, noChild) {
        if (!noCache) admin.putSetting('defaultTheme', theme);
        if (!win) win = top;
        admin.removeTheme(win);
        if (theme) {
            try {
                var $body = win.layui.jquery('body');
                $body.addClass(theme);
                $body.data('theme', theme);
            } catch (e) {
            }
        }
        if (noChild) return;
        var ifs = win.frames;
        for (var i = 0; i < ifs.length; i++) admin.changeTheme(theme, ifs[i], true, false);
    };

    /** 移除主题 */
    admin.removeTheme = function (w) {
        if (!w) w = window;
        try {
            var $body = w.layui.jquery('body');
            var theme = $body.data('theme');
            if (theme) $body.removeClass(theme);
            $body.removeData('theme');
        } catch (e) {
        }
    };

    /** 关闭当前iframe层弹窗 */
    admin.closeThisDialog = function () {
        return admin.closeDialog();
    };

    /** 关闭elem所在的页面层弹窗 */
    admin.closeDialog = function (elem) {
        if (elem) layer.close(admin.getLayerIndex(elem));
        else parent.layer.close(parent.layer.getFrameIndex(window.name));
    };

    /** 获取页面层弹窗的index */
    admin.getLayerIndex = function (elem) {
        if (!elem) return parent.layer.getFrameIndex(window.name);
        var id = $(elem).parents('.layui-layer').first().attr('id');
        if (id && id.length >= 11) return id.substring(11);
    };

    /** 让当前的iframe弹层自适应高度 */
    admin.iframeAuto = function () {
        return parent.layer.iframeAuto(parent.layer.getFrameIndex(window.name));
    };

    /** 获取浏览器高度 */
    admin.getPageHeight = function () {
        return document.documentElement.clientHeight || document.body.clientHeight;
    };

    /** 获取浏览器宽度 */
    admin.getPageWidth = function () {
        return document.documentElement.clientWidth || document.body.clientWidth;
    };

    /** 绑定表单弹窗 */
    admin.modelForm = function (layero, btnFilter, formFilter) {
        var $layero = $(layero);
        $layero.addClass('layui-form');
        if (formFilter) $layero.attr('lay-filter', formFilter);
        // 确定按钮绑定submit
        var $btnSubmit = $layero.find('.layui-layer-btn .layui-layer-btn0');
        $btnSubmit.attr('lay-submit', '');
        $btnSubmit.attr('lay-filter', btnFilter);
    };

    /** loading按钮 */
    admin.btnLoading = function (elem, text, loading) {
        if (text !== undefined && (typeof text === 'boolean')) {
            loading = text;
            text = undefined;
        }
        if (text === undefined) text = '&nbsp;加载中';
        if (loading === undefined) loading = true;
        var $elem = $(elem);
        if (loading) {
            $elem.addClass('ew-btn-loading');
            $elem.prepend('<span class="ew-btn-loading-text"><i class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop"></i>' + text + '</span>');
            $elem.attr('disabled', 'disabled').prop('disabled', true);
        } else {
            $elem.removeClass('ew-btn-loading');
            $elem.children('.ew-btn-loading-text').remove();
            $elem.removeProp('disabled').removeAttr('disabled');
        }
    };

    /** 鼠标移入侧边栏自动展开 */
    admin.openSideAutoExpand = function () {
        var $side = $('.layui-layout-admin>.layui-side');
        $side.off('mouseenter.openSideAutoExpand').on("mouseenter.openSideAutoExpand", function () {
            if (!$(this).parent().hasClass('admin-nav-mini')) return;
            admin.flexible(true);
            $(this).addClass('side-mini-hover');
        });
        $side.off('mouseleave.openSideAutoExpand').on("mouseleave.openSideAutoExpand", function () {
            if (!$(this).hasClass('side-mini-hover')) return;
            admin.flexible(false);
            $(this).removeClass('side-mini-hover');
        });
    };

    /** 表格单元格超出内容自动展开 */
    admin.openCellAutoExpand = function () {
        var $body = $('body');
        $body.off('mouseenter.openCellAutoExpand').on('mouseenter.openCellAutoExpand', '.layui-table-view td', function () {
            $(this).find('.layui-table-grid-down').trigger('click');
        });
        $body.off('mouseleave.openCellAutoExpand').on('mouseleave.openCellAutoExpand', '.layui-table-tips>.layui-layer-content', function () {
            $('.layui-table-tips-c').trigger('click');
        });
    };

    /** open事件解析layer参数 */
    admin.parseLayerOption = function (option) {
        // 数组类型进行转换
        for (var f in option) {
            if (!option.hasOwnProperty(f)) continue;
            if (option[f] && option[f].toString().indexOf(',') !== -1) option[f] = option[f].toString().split(',');
        }
        // function类型参数转换
        var fs = {'success': 'layero,index', 'cancel': 'index,layero', 'end': '', 'full': '', 'min': '', 'restore': ''};
        for (var k in fs) {
            if (!fs.hasOwnProperty(k) || !option[k]) continue;
            try {
                if (/^[a-zA-Z_]+[a-zA-Z0-9_]+$/.test(option[k])) option[k] += '()';
                option[k] = new Function(fs[k], option[k]);
            } catch (e) {
                option[k] = undefined;
            }
        }
        // content取内容
        if (option.content && (typeof option.content === 'string') && option.content.indexOf('#') === 0) {
            if ($(option.content).is('script')) option.content = $(option.content).html();
            else option.content = $(option.content);
        }
        if (option.type === undefined && option.url === undefined) option.type = 2;  // 默认为iframe类型
        return option;
    };

    /** 字符串形式的parent.parent转window对象 */
    admin.strToWin = function (str) {
        var win = window;
        if (!str) return win;
        var ws = str.split('.');
        for (var i = 0; i < ws.length; i++) win = win[ws[i]];
        return win;
    };

    /** 解决折叠侧边栏表格滚动条闪现 */
    admin.hideTableScrollBar = function (win) {
        if (admin.getPageWidth() <= 768) return;
        if (!win) {
            var $iframe = $(tabDOM + '>.layui-tab-content>.layui-tab-item.layui-show>.admin-iframe');
            if ($iframe.length <= 0) $iframe = $(bodyDOM + '>div>.admin-iframe');
            if ($iframe.length > 0) win = $iframe[0].contentWindow;
        }
        try {  // 可能会跨域
            if (window.hsbTimer) clearTimeout(window.hsbTimer);
            win.layui.jquery('.layui-table-body.layui-table-main').addClass('no-scrollbar');
            window.hsbTimer = setTimeout(function () {
                win.layui.jquery('.layui-table-body.layui-table-main').removeClass('no-scrollbar');
            }, 800);
        } catch (e) {
        }
    };

    /** 判断是否是主框架 */
    admin.isTop = function () {
        return $(bodyDOM).length > 0;
    };

    /** admin提供的事件 */
    admin.events = {
        /* 折叠侧导航 */
        flexible: function () {
            admin.strToWin($(this).data('window')).layui.admin.flexible();
        },
        /* 刷新主体部分 */
        refresh: function () {
            admin.strToWin($(this).data('window')).layui.admin.refresh();
        },
        /* 后退 */
        back: function () {
            admin.strToWin($(this).data('window')).history.back();
        },
        /* 设置主题 */
        theme: function () {
            var option = admin.util.deepClone($(this).data());
            admin.strToWin(option.window).layui.admin.popupRight($.extend({
                id: 'layer-theme', url: option.url || 'page/tpl/tpl-theme.html'
            }, admin.parseLayerOption(option)));
        },
        /* 打开便签 */
        note: function () {
            var option = admin.util.deepClone($(this).data());
            admin.strToWin(option.window).layui.admin.popupRight($.extend({
                id: 'layer-note', url: option.url || 'page/tpl/tpl-note.html'
            }, admin.parseLayerOption(option)));
        },
        /* 打开消息 */
        message: function () {
            var option = admin.util.deepClone($(this).data());
            admin.strToWin(option.window).layui.admin.popupRight($.extend({
                id: 'layer-notice', url: option.url || 'page/tpl/tpl-message.html'
            }, admin.parseLayerOption(option)));
        },
        /* 打开修改密码弹窗 */
        psw: function () {
            var option = admin.util.deepClone($(this).data());
            admin.strToWin(option.window).layui.admin.open($.extend({
                id: 'layer-psw', title: '修改密码', shade: 0, url: option.url || 'page/tpl/tpl-password.html'
            }, admin.parseLayerOption(option)));
        },
        /* 退出登录 */
        logout: function () {
            var option = admin.util.deepClone($(this).data());
            admin.unlockScreen();

            function doLogout() {
                if (option.ajax) {
                    var loadIndex = layer.load(2);
                    admin.req(option.ajax, function (res) {
                        layer.close(loadIndex);
                        if (option.parseData) {
                            try {
                                var parseData = new Function('res', option.parseData);
                                res = parseData(res);
                            } catch (e) {
                                console.error(e);
                            }
                        }
                        if (res.code == (option.code || 0)) {
                            setter.removeToken && setter.removeToken();
                            location.replace(option.url || '/');
                        } else {
                            layer.msg(res.msg, {icon: 2});
                        }
                    }, option.method || 'delete');
                } else {
                    setter.removeToken && setter.removeToken();
                    location.replace(option.url || '/');
                }
            }

            if (false === option.confirm || 'false' === option.confirm) return doLogout();
            admin.strToWin(option.window).layui.layer.confirm(option.content || '确定要退出登录吗？', $.extend({
                title: '温馨提示', skin: 'layui-layer-admin', shade: .1
            }, admin.parseLayerOption(option)), function () {
                doLogout();
            });
        },
        /* 打开弹窗 */
        open: function () {
            var option = admin.util.deepClone($(this).data());
            admin.strToWin(option.window).layui.admin.open(admin.parseLayerOption(option));
        },
        /* 打开右侧弹窗 */
        popupRight: function () {
            var option = admin.util.deepClone($(this).data());
            admin.strToWin(option.window).layui.admin.popupRight(admin.parseLayerOption(option));
        },
        /* 全屏 */
        fullScreen: function () {
            var ac = 'layui-icon-screen-full', ic = 'layui-icon-screen-restore';
            var $ti = $(this).find('i');
            var isFullscreen = document.fullscreenElement || document.msFullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement || false;
            if (isFullscreen) {
                var efs = document.exitFullscreen || document.webkitExitFullscreen || document.mozCancelFullScreen || document.msExitFullscreen;
                if (efs) {
                    efs.call(document);
                } else if (window.ActiveXObject) {
                    var ws = new ActiveXObject('WScript.Shell');
                    ws && ws.SendKeys('{F11}');
                }
                $ti.addClass(ac).removeClass(ic);
            } else {
                var el = document.documentElement;
                var rfs = el.requestFullscreen || el.webkitRequestFullscreen || el.mozRequestFullScreen || el.msRequestFullscreen;
                if (rfs) {
                    rfs.call(el);
                } else if (window.ActiveXObject) {
                    var wss = new ActiveXObject('WScript.Shell');
                    wss && wss.SendKeys('{F11}');
                }
                $ti.addClass(ic).removeClass(ac);
            }
        },
        /* 左滑动tab */
        leftPage: function () {
            admin.strToWin($(this).data('window')).layui.admin.rollPage('left');
        },
        /* 右滑动tab */
        rightPage: function () {
            admin.strToWin($(this).data('window')).layui.admin.rollPage();
        },
        /* 关闭当前选项卡 */
        closeThisTabs: function () {
            var url = $(this).data('url');
            admin.strToWin($(this).data('window')).layui.admin.closeThisTabs(url);
        },
        /* 关闭其他选项卡 */
        closeOtherTabs: function () {
            admin.strToWin($(this).data('window')).layui.admin.closeOtherTabs();
        },
        /* 关闭所有选项卡 */
        closeAllTabs: function () {
            admin.strToWin($(this).data('window')).layui.admin.closeAllTabs();
        },
        /* 关闭当前弹窗(智能) */
        closeDialog: function () {
            if ($(this).parents('.layui-layer').length > 0) admin.closeDialog(this);
            else admin.closeDialog();
        },
        /* 关闭当前iframe弹窗 */
        closeIframeDialog: function () {
            admin.closeDialog();
        },
        /* 关闭当前页面层弹窗 */
        closePageDialog: function () {
            admin.closeDialog(this);
        },
        /* 锁屏 */
        lockScreen: function () {
            admin.strToWin($(this).data('window')).layui.admin.lockScreen($(this).data('url'));
        }
    };

    /** 选择位置 */
    admin.chooseLocation = function (param) {
        var dialogTitle = param.title;  // 弹窗标题
        var onSelect = param.onSelect;  // 选择回调
        var needCity = param.needCity;  // 是否返回行政区
        var mapCenter = param.center;  // 地图中心
        var defaultZoom = param.defaultZoom;  // 地图默认缩放级别
        var pointZoom = param.pointZoom;  // 选中时地图缩放级别
        var searchKeywords = param.keywords;  // poi检索关键字
        var searchPageSize = param.pageSize;  // poi检索最大数量
        var mapJsUrl = param.mapJsUrl;  // 高德地图js的url
        if (dialogTitle === undefined) dialogTitle = '选择位置';
        if (defaultZoom === undefined) defaultZoom = 11;
        if (pointZoom === undefined) pointZoom = 17;
        if (searchKeywords === undefined) searchKeywords = '';
        if (searchPageSize === undefined) searchPageSize = 30;
        if (mapJsUrl === undefined) mapJsUrl = 'https://webapi.amap.com/maps?v=1.4.14&key=006d995d433058322319fa797f2876f5';
        var isSelMove = false, selLocation;
        // 搜索附近
        var searchNearBy = function (lat, lng) {
            AMap.service(['AMap.PlaceSearch'], function () {
                var placeSearch = new AMap.PlaceSearch({
                    type: '', pageSize: searchPageSize, pageIndex: 1
                });
                var cpoint = [lng, lat];
                placeSearch.searchNearBy(searchKeywords, cpoint, 1000, function (status, result) {
                    if (status === 'complete') {
                        var pois = result.poiList.pois;
                        var htmlList = '';
                        for (var i = 0; i < pois.length; i++) {
                            var poiItem = pois[i];
                            if (poiItem.location !== undefined) {
                                htmlList += '<div data-lng="' + poiItem.location.lng + '" data-lat="' + poiItem.location.lat + '" class="ew-map-select-search-list-item">';
                                htmlList += '     <div class="ew-map-select-search-list-item-title">' + poiItem.name + '</div>';
                                htmlList += '     <div class="ew-map-select-search-list-item-address">' + poiItem.address + '</div>';
                                htmlList += '     <div class="ew-map-select-search-list-item-icon-ok layui-hide"><i class="layui-icon layui-icon-ok-circle"></i></div>';
                                htmlList += '</div>';
                            }
                        }
                        $('#ew-map-select-pois').html(htmlList);
                    }
                });
            });
        };
        // 渲染地图
        var renderMap = function () {
            var mapOption = {
                resizeEnable: true, // 监控地图容器尺寸变化
                zoom: defaultZoom  // 初缩放级别
            };
            mapCenter && (mapOption.center = mapCenter);
            var map = new AMap.Map('ew-map-select-map', mapOption);
            // 地图加载完成
            map.on('complete', function () {
                var center = map.getCenter();
                searchNearBy(center.lat, center.lng);
            });
            // 地图移动结束事件
            map.on('moveend', function () {
                if (isSelMove) {
                    isSelMove = false;
                } else {
                    $('#ew-map-select-tips').addClass('layui-hide');
                    $('#ew-map-select-center-img').removeClass('bounceInDown');
                    setTimeout(function () {
                        $('#ew-map-select-center-img').addClass('bounceInDown');
                    });
                    var center = map.getCenter();
                    searchNearBy(center.lat, center.lng);
                }
            });
            // poi列表点击事件
            $('#ew-map-select-pois').off('click').on('click', '.ew-map-select-search-list-item', function () {
                $('#ew-map-select-tips').addClass('layui-hide');
                $('#ew-map-select-pois .ew-map-select-search-list-item-icon-ok').addClass('layui-hide');
                $(this).find('.ew-map-select-search-list-item-icon-ok').removeClass('layui-hide');
                $('#ew-map-select-center-img').removeClass('bounceInDown');
                setTimeout(function () {
                    $('#ew-map-select-center-img').addClass('bounceInDown');
                });
                var lng = $(this).data('lng');
                var lat = $(this).data('lat');
                var name = $(this).find('.ew-map-select-search-list-item-title').text();
                var address = $(this).find('.ew-map-select-search-list-item-address').text();
                selLocation = {name: name, address: address, lat: lat, lng: lng};
                isSelMove = true;
                map.setZoomAndCenter(pointZoom, [lng, lat]);
            });
            // 确定按钮点击事件
            $('#ew-map-select-btn-ok').click(function () {
                if (selLocation === undefined) {
                    layer.msg('请点击位置列表选择', {icon: 2, anim: 6});
                } else if (onSelect) {
                    if (needCity) {
                        var loadIndex = layer.load(2);
                        map.setCenter([selLocation.lng, selLocation.lat]);
                        map.getCity(function (result) {
                            layer.close(loadIndex);
                            selLocation.city = result;
                            admin.closeDialog('#ew-map-select-btn-ok');
                            onSelect(selLocation);
                        });
                    } else {
                        admin.closeDialog('#ew-map-select-btn-ok');
                        onSelect(selLocation);
                    }
                } else {
                    admin.closeDialog('#ew-map-select-btn-ok');
                }
            });
            // 搜索提示
            var $inputSearch = $('#ew-map-select-input-search');
            $inputSearch.off('input').on('input', function () {
                var keywords = $(this).val();
                var $selectTips = $('#ew-map-select-tips');
                if (!keywords) {
                    $selectTips.html('');
                    $selectTips.addClass('layui-hide');
                }
                AMap.plugin('AMap.Autocomplete', function () {
                    var autoComplete = new AMap.Autocomplete({city: '全国'});
                    autoComplete.search(keywords, function (status, result) {
                        if (result.tips) {
                            var tips = result.tips;
                            var htmlList = '';
                            for (var i = 0; i < tips.length; i++) {
                                var tipItem = tips[i];
                                if (tipItem.location !== undefined) {
                                    htmlList += '<div data-lng="' + tipItem.location.lng + '" data-lat="' + tipItem.location.lat + '" class="ew-map-select-search-list-item">';
                                    htmlList += '     <div class="ew-map-select-search-list-item-icon-search"><i class="layui-icon layui-icon-search"></i></div>';
                                    htmlList += '     <div class="ew-map-select-search-list-item-title">' + tipItem.name + '</div>';
                                    htmlList += '     <div class="ew-map-select-search-list-item-address">' + tipItem.address + '</div>';
                                    htmlList += '</div>';
                                }
                            }
                            $selectTips.html(htmlList);
                            if (tips.length === 0) $('#ew-map-select-tips').addClass('layui-hide');
                            else $('#ew-map-select-tips').removeClass('layui-hide');
                        } else {
                            $selectTips.html('');
                            $selectTips.addClass('layui-hide');
                        }
                    });
                });
            });
            $inputSearch.off('blur').on('blur', function () {
                var keywords = $(this).val();
                var $selectTips = $('#ew-map-select-tips');
                if (!keywords) {
                    $selectTips.html('');
                    $selectTips.addClass('layui-hide');
                }
            });
            $inputSearch.off('focus').on('focus', function () {
                var keywords = $(this).val();
                if (keywords) $('#ew-map-select-tips').removeClass('layui-hide');
            });
            // tips列表点击事件
            $('#ew-map-select-tips').off('click').on('click', '.ew-map-select-search-list-item', function () {
                $('#ew-map-select-tips').addClass('layui-hide');
                var lng = $(this).data('lng');
                var lat = $(this).data('lat');
                selLocation = undefined;
                map.setZoomAndCenter(pointZoom, [lng, lat]);
            });
        };
        // 显示弹窗
        var htmlStr = [
            '<div class="ew-map-select-tool" style="position: relative;">',
            '     搜索：<input id="ew-map-select-input-search" class="layui-input icon-search inline-block" style="width: 190px;" placeholder="输入关键字搜索" autocomplete="off" />',
            '     <button id="ew-map-select-btn-ok" class="layui-btn icon-btn pull-right" type="button"><i class="layui-icon">&#xe605;</i>确定</button>',
            '     <div id="ew-map-select-tips" class="ew-map-select-search-list layui-hide">',
            '     </div>',
            '</div>',
            '<div class="layui-row ew-map-select">',
            '     <div class="layui-col-sm7 ew-map-select-map-group" style="position: relative;">',
            '          <div id="ew-map-select-map"></div>',
            '          <i id="ew-map-select-center-img2" class="layui-icon layui-icon-add-1"></i>',
            '          <img id="ew-map-select-center-img" src="https://3gimg.qq.com/lightmap/components/locationPicker2/image/marker.png" alt=""/>',
            '     </div>',
            '     <div id="ew-map-select-pois" class="layui-col-sm5 ew-map-select-search-list">',
            '     </div>',
            '</div>'].join('');
        admin.open({
            id: 'ew-map-select', type: 1, title: dialogTitle, area: '750px', content: htmlStr,
            success: function (layero, dIndex) {
                var $content = $(layero).children('.layui-layer-content');
                $content.css('overflow', 'visible');
                admin.showLoading($content);
                if (undefined === window.AMap) {
                    $.getScript(mapJsUrl, function () {
                        renderMap();
                        admin.removeLoading($content);
                    });
                } else {
                    renderMap();
                    admin.removeLoading($content);
                }
            }
        });
    };

    /** 裁剪图片 */
    admin.cropImg = function (param) {
        var uploadedImageType = 'image/jpeg';  // 当前图片的类型
        var aspectRatio = param.aspectRatio;  // 裁剪比例
        var imgSrc = param.imgSrc;  // 裁剪图片
        var imgType = param.imgType;  // 图片类型
        var onCrop = param.onCrop;  // 裁剪完成回调
        var limitSize = param.limitSize;  // 限制选择的图片大小
        var acceptMime = param.acceptMime;  // 限制选择的图片类型
        var imgExts = param.exts;  // 限制选择的图片类型
        var dialogTitle = param.title;  // 弹窗的标题
        if (aspectRatio === undefined) aspectRatio = 1;
        if (dialogTitle === undefined) dialogTitle = '裁剪图片';
        if (imgType) uploadedImageType = imgType;
        layui.use(['Cropper', 'upload'], function () {
            var Cropper = layui.Cropper, upload = layui.upload;

            // 渲染组件
            function renderElem() {
                var imgCropper, $cropImg = $('#ew-crop-img');
                // 上传文件按钮绑定事件
                var uploadOptions = {
                    elem: '#ew-crop-img-upload', auto: false, drag: false,
                    choose: function (obj) {
                        obj.preview(function (index, file, result) {
                            uploadedImageType = file.type;
                            $cropImg.attr('src', result);
                            if (!imgSrc || !imgCropper) {
                                imgSrc = result;
                                renderElem();
                            } else {
                                imgCropper.destroy();
                                imgCropper = new Cropper($cropImg[0], options);
                            }
                        });
                    }
                };
                if (limitSize !== undefined) uploadOptions.size = limitSize;
                if (acceptMime !== undefined) uploadOptions.acceptMime = acceptMime;
                if (imgExts !== undefined) uploadOptions.exts = imgExts;
                upload.render(uploadOptions);
                // 没有传图片触发上传图片
                if (!imgSrc) return $('#ew-crop-img-upload').trigger('click');
                // 渲染裁剪组件
                var options = {aspectRatio: aspectRatio, preview: '#ew-crop-img-preview'};
                imgCropper = new Cropper($cropImg[0], options);
                // 操作按钮绑定事件
                $('.ew-crop-tool').on('click', '[data-method]', function () {
                    var data = $(this).data(), cropped, result;
                    if (!imgCropper || !data.method) return;
                    data = $.extend({}, data);
                    cropped = imgCropper.cropped;
                    switch (data.method) {
                        case 'rotate':
                            if (cropped && options.viewMode > 0) imgCropper.clear();
                            break;
                        case 'getCroppedCanvas':
                            if (uploadedImageType === 'image/jpeg') {
                                if (!data.option) data.option = {};
                                data.option.fillColor = '#fff';
                            }
                            break;
                    }
                    result = imgCropper[data.method](data.option, data.secondOption);
                    switch (data.method) {
                        case 'rotate':
                            if (cropped && options.viewMode > 0) imgCropper.crop();
                            break;
                        case 'scaleX':
                        case 'scaleY':
                            $(this).data('option', -data.option);
                            break;
                        case 'getCroppedCanvas':
                            if (result) {
                                onCrop && onCrop(result.toDataURL(uploadedImageType));
                                admin.closeDialog('#ew-crop-img');
                            } else {
                                layer.msg('裁剪失败', {icon: 2, anim: 6});
                            }
                            break;
                    }
                });
            }

            // 显示弹窗
            var htmlStr = [
                '<div class="layui-row">',
                '     <div class="layui-col-sm8" style="min-height: 9rem;">',
                '          <img id="ew-crop-img" src="', imgSrc || '', '" style="max-width:100%;" alt=""/>',
                '     </div>',
                '     <div class="layui-col-sm4 layui-hide-xs" style="padding: 15px;text-align: center;">',
                '          <div id="ew-crop-img-preview" style="width: 100%;height: 9rem;overflow: hidden;display: inline-block;border: 1px solid #dddddd;"></div>',
                '     </div>',
                '</div>',
                '<div class="text-center ew-crop-tool" style="padding: 15px 10px 5px 0;">',
                '     <div class="layui-btn-group" style="margin-bottom: 10px;margin-left: 10px;">',
                '          <button title="放大" data-method="zoom" data-option="0.1" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-add-1"></i></button>',
                '          <button title="缩小" data-method="zoom" data-option="-0.1" class="layui-btn icon-btn" type="button"><span style="display: inline-block;width: 12px;height: 2.5px;background: rgba(255, 255, 255, 0.9);vertical-align: middle;margin: 0 4px;"></span></button>',
                '     </div>',
                '     <div class="layui-btn-group layui-hide-xs" style="margin-bottom: 10px;">',
                '          <button title="向左旋转" data-method="rotate" data-option="-45" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-refresh-1" style="transform: rotateY(180deg) rotate(40deg);display: inline-block;"></i></button>',
                '          <button title="向右旋转" data-method="rotate" data-option="45" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-refresh-1" style="transform: rotate(30deg);display: inline-block;"></i></button>',
                '     </div>',
                '     <div class="layui-btn-group" style="margin-bottom: 10px;">',
                '          <button title="左移" data-method="move" data-option="-10" data-second-option="0" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-left"></i></button>',
                '          <button title="右移" data-method="move" data-option="10" data-second-option="0" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-right"></i></button>',
                '          <button title="上移" data-method="move" data-option="0" data-second-option="-10" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-up"></i></button>',
                '          <button title="下移" data-method="move" data-option="0" data-second-option="10" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-down"></i></button>',
                '     </div>',
                '     <div class="layui-btn-group" style="margin-bottom: 10px;">',
                '          <button title="左右翻转" data-method="scaleX" data-option="-1" class="layui-btn icon-btn" type="button" style="position: relative;width: 41px;"><i class="layui-icon layui-icon-triangle-r" style="position: absolute;left: 9px;top: 0;transform: rotateY(180deg);font-size: 16px;"></i><i class="layui-icon layui-icon-triangle-r" style="position: absolute; right: 3px; top: 0;font-size: 16px;"></i></button>',
                '          <button title="上下翻转" data-method="scaleY" data-option="-1" class="layui-btn icon-btn" type="button" style="position: relative;width: 41px;"><i class="layui-icon layui-icon-triangle-d" style="position: absolute;left: 11px;top: 6px;transform: rotateX(180deg);line-height: normal;font-size: 16px;"></i><i class="layui-icon layui-icon-triangle-d" style="position: absolute; left: 11px; top: 14px;line-height: normal;font-size: 16px;"></i></button>',
                '     </div>',
                '     <div class="layui-btn-group" style="margin-bottom: 10px;">',
                '          <button title="重新开始" data-method="reset" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-refresh"></i></button>',
                '          <button title="选择图片" id="ew-crop-img-upload" class="layui-btn icon-btn" type="button" style="border-radius: 0 2px 2px 0;"><i class="layui-icon layui-icon-upload-drag"></i></button>',
                '     </div>',
                '     <button data-method="getCroppedCanvas" data-option="{ &quot;maxWidth&quot;: 4096, &quot;maxHeight&quot;: 4096 }" class="layui-btn icon-btn" type="button" style="margin-left: 10px;margin-bottom: 10px;"><i class="layui-icon">&#xe605;</i>完成</button>',
                '</div>'].join('');
            admin.open({
                title: dialogTitle, area: '665px', type: 1, content: htmlStr,
                success: function (layero, dIndex) {
                    $(layero).children('.layui-layer-content').css('overflow', 'visible');
                    renderElem();
                }
            });
        });
    };

    /** 工具类 */
    admin.util = {
        /* 百度地图坐标转高德地图坐标 */
        Convert_BD09_To_GCJ02: function (point) {
            var x_pi = (3.14159265358979324 * 3000.0) / 180.0;
            var x = point.lng - 0.0065, y = point.lat - 0.006;
            var z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
            var theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
            return {lng: z * Math.cos(theta), lat: z * Math.sin(theta)};
        },
        /* 高德地图坐标转百度地图坐标 */
        Convert_GCJ02_To_BD09: function (point) {
            var x_pi = (3.14159265358979324 * 3000.0) / 180.0;
            var x = point.lng, y = point.lat;
            var z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
            var theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
            return {lng: z * Math.cos(theta) + 0.0065, lat: z * Math.sin(theta) + 0.006};
        },
        /* 动态数字 */
        animateNum: function (elem, isThd, delay, grain) {
            isThd = isThd === null || isThd === undefined || isThd === true || isThd === 'true';  // 是否是千分位
            delay = isNaN(delay) ? 500 : delay;   // 动画延迟
            grain = isNaN(grain) ? 100 : grain;   // 动画粒度
            var getPref = function (str) {
                var pref = '';
                for (var i = 0; i < str.length; i++) if (!isNaN(str.charAt(i))) return pref; else pref += str.charAt(i);
            }, getSuf = function (str) {
                var suf = '';
                for (var i = str.length - 1; i >= 0; i--) if (!isNaN(str.charAt(i))) return suf; else suf = str.charAt(i) + suf;
            }, toThd = function (num, isThd) {
                if (!isThd) return num;
                if (!/^[0-9]+.?[0-9]*$/.test(num)) return num;
                num = num.toString();
                return num.replace(num.indexOf('.') > 0 ? /(\d)(?=(\d{3})+(?:\.))/g : /(\d)(?=(\d{3})+(?:$))/g, '$1,');
            };
            $(elem).each(function () {
                var $this = $(this);
                var num = $this.data('num');
                if (!num) {
                    num = $this.text().replace(/,/g, '');  // 内容
                    $this.data('num', num);
                }
                var flag = 'INPUT,TEXTAREA'.indexOf($this.get(0).tagName) >= 0;  // 是否是输入框
                var pref = getPref(num.toString()), suf = getSuf(num.toString());
                var strNum = num.toString().replace(pref, '').replace(suf, '');
                if (isNaN(strNum * 1) || strNum === '0') {
                    flag ? $this.val(num) : $this.html(num);
                    return console.error('not a number');
                }
                var int_dec = strNum.split('.');
                var deciLen = int_dec[1] ? int_dec[1].length : 0;
                var startNum = 0.0, endNum = strNum;
                if (Math.abs(endNum * 1) > 10) startNum = parseFloat(int_dec[0].substring(0, int_dec[0].length - 1) + (int_dec[1] ? '.0' + int_dec[1] : ''));
                var oft = (endNum - startNum) / grain, temp = 0;
                var mTime = setInterval(function () {
                    var str = pref + toThd(startNum.toFixed(deciLen), isThd) + suf;
                    flag ? $this.val(str) : $this.html(str);
                    startNum += oft;
                    temp++;
                    if (Math.abs(startNum) >= Math.abs(endNum * 1) || temp > 5000) {
                        str = pref + toThd(endNum, isThd) + suf;
                        flag ? $this.val(str) : $this.html(str);
                        clearInterval(mTime);
                    }
                }, delay / grain);
            });
        },
        /* 深度克隆对象 */
        deepClone: function (obj) {
            var result;
            var oClass = admin.util.isClass(obj);
            if (oClass === 'Object') result = {};
            else if (oClass === 'Array') result = [];
            else return obj;
            for (var key in obj) {
                if (!obj.hasOwnProperty(key)) continue;
                var copy = obj[key], cClass = admin.util.isClass(copy);
                if (cClass === 'Object') result[key] = arguments.callee(copy); // 递归调用
                else if (cClass === 'Array') result[key] = arguments.callee(copy);
                else result[key] = obj[key];
            }
            return result;
        },
        /* 获取变量类型 */
        isClass: function (o) {
            if (o === null) return 'Null';
            if (o === undefined) return 'Undefined';
            return Object.prototype.toString.call(o).slice(8, -1);
        },
        /* 判断富文本是否为空 */
        fullTextIsEmpty: function (text) {
            if (!text) return true;
            var noTexts = ['img', 'audio', 'video', 'iframe', 'object'];
            for (var i = 0; i < noTexts.length; i++) {
                if (text.indexOf('<' + noTexts[i]) > -1) return false;
            }
            var str = text.replace(/\s*/g, '');  // 去掉所有空格
            if (!str) return true;
            str = str.replace(/&nbsp;/ig, '');  // 去掉所有&nbsp;
            if (!str) return true;
            str = str.replace(/<[^>]+>/g, '');   // 去掉所有html标签
            return !str;
        },
        /* 移除元素的style */
        removeStyle: function (elem, names) {
            if (typeof names === 'string') names = [names];
            for (var i = 0; i < names.length; i++) $(elem).css(names[i], '');
        },
        /* 滚动到顶部 */
        scrollTop: function (elem) {
            $(elem || 'html,body').animate({scrollTop: 0}, 300);
        },
        /* 模板解析 */
        tpl: function (html, data, openCode, closeCode) {
            if (html === undefined || html === null || typeof html !== 'string') return html;
            if (!data) data = {};
            if (!openCode) openCode = '{{';
            if (!closeCode) closeCode = '}}';
            var tool = {
                exp: function (str) {
                    return new RegExp(str, 'g');
                },
                // 匹配满足规则内容
                query: function (type, _, __) {
                    var types = ['#([\\s\\S])+?', '([^{#}])*?'][type || 0];
                    return tool.exp((_ || '') + openCode + types + closeCode + (__ || ''));
                },
                escape: function (str) {
                    return String(str || '').replace(/&(?!#?[a-zA-Z0-9]+;)/g, '&amp;')
                        .replace(/</g, '&lt;').replace(/>/g, '&gt;')
                        .replace(/'/g, '&#39;').replace(/"/g, '&quot;');
                },
                error: function (e, tplog) {
                    console.error('Laytpl Error：' + e + '\n' + (tplog || ''));
                },
                parse: function (tpl, data) {
                    var tplog = tpl;
                    try {
                        var jss = tool.exp('^' + openCode + '#'), jsse = tool.exp(closeCode + '$');
                        tpl = tpl.replace(tool.exp(openCode + '#'), openCode + '# ')
                            .replace(tool.exp(closeCode + '}'), '} ' + closeCode).replace(/\\/g, '\\\\')
                            // 不匹配指定区域的内容
                            .replace(tool.exp(openCode + '!(.+?)!' + closeCode), function (str) {
                                str = str.replace(tool.exp('^' + openCode + '!'), '')
                                    .replace(tool.exp('!' + closeCode), '')
                                    .replace(tool.exp(openCode + '|' + closeCode), function (tag) {
                                        return tag.replace(/(.)/g, '\\$1')
                                    });
                                return str
                            })
                            // 匹配JS规则内容
                            .replace(/(?="|')/g, '\\').replace(tool.query(), function (str) {
                                str = str.replace(jss, '').replace(jsse, '');
                                return '";' + str.replace(/\\/g, '') + ';view+="';
                            })
                            // 匹配普通字段
                            .replace(tool.query(1), function (str) {
                                var start = '"+(';
                                if (str.replace(/\s/g, '') === openCode + closeCode) return '';
                                str = str.replace(tool.exp(openCode + '|' + closeCode), '');
                                if (/^=/.test(str)) {
                                    str = str.replace(/^=/, '');
                                    start = '"+_escape_(';
                                }
                                return start + str.replace(/\\/g, '') + ')+"';
                            })
                            // 换行符处理
                            .replace(/\r\n/g, '\\r\\n" + "').replace(/\n/g, '\\n" + "').replace(/\r/g, '\\r" + "');
                        tpl = '"use strict";var view = "' + tpl + '";return view;';
                        tpl = new Function('d, _escape_', tpl);
                        return tpl(data, tool.escape);
                    } catch (e) {
                        tool.error(e, tplog);
                        return tplog;
                    }
                }
            };
            return tool.parse(html, data);
        },
        /* 渲染动态模板 */
        render: function (option) {
            if (typeof option.url === 'string') {
                option.success = function (res) {
                    admin.util.render($.extend({}, option, {url: res}));
                };
                if (option.ajax === 'ajax') admin.ajax(option);
                else admin.req(option.url, option.where, option.success, option.method, option);
                return;
            }
            var html = admin.util.tpl(option.tpl, option.url,
                option.open || setter.tplOpen, option.close || setter.tplClose);
            $(option.elem).next('[ew-tpl-rs]').remove();
            $(option.elem).after(html);
            $(option.elem).next().attr('ew-tpl-rs', '');
            option.done && option.done(option.url);
        }
    };

    /** 锁屏功能 */
    admin.lockScreen = function (url) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.admin) return top.layui.admin.lockScreen(url);
        if (!url) url = 'page/tpl/tpl-lock-screen.html';
        var $lock = $('#ew-lock-screen-group');
        if ($lock.length > 0) {
            $lock.fadeIn('fast');
            admin.isLockScreen = true;
            admin.putTempData('isLockScreen', admin.isLockScreen, true);
        } else {
            var loadIndex = layer.load(2);
            admin.ajax({
                url: url, dataType: 'html',
                success: function (res) {
                    layer.close(loadIndex);
                    if (typeof res === 'string') {
                        $('body').append('<div id="ew-lock-screen-group">' + res + '</div>');
                        admin.isLockScreen = true;
                        admin.putTempData('isLockScreen', admin.isLockScreen, true);
                        admin.putTempData('lockScreenUrl', url, true);
                    } else {
                        console.error(res);
                        layer.msg(JSON.stringify(res), {icon: 2, anim: 6});
                    }
                }
            });
        }
    };

    /** 解除锁屏 */
    admin.unlockScreen = function (isRemove) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.admin) return top.layui.admin.unlockScreen(isRemove);
        var $lock = $('#ew-lock-screen-group');
        isRemove ? $lock.remove() : $lock.fadeOut('fast');
        admin.isLockScreen = false;
        admin.putTempData('isLockScreen', null, true);
    };

    /** tips方法封装 */
    admin.tips = function (option) {
        return layer.tips(option.text, option.elem, {
            tips: [option.direction || 1, option.bg || '#191a23'],
            tipsMore: option.tipsMore, time: option.time || -1,
            success: function (layero) {
                var $content = $(layero).children('.layui-layer-content');
                if (option.padding || option.padding === 0) $content.css('padding', option.padding);
                if (option.color) $content.css('color', option.color);
                if (option.bgImg) $content.css('background-image', option.bgImg).children('.layui-layer-TipsG').css('z-index', '-1');
                if (option.fontSize) $content.css('font-size', option.fontSize);
                if (!option.offset) return;
                var offset = option.offset.split(',');
                var top = offset[0], left = offset.length > 1 ? offset[1] : undefined;
                if (top) $(layero).css('margin-top', top);
                if (left) $(layero).css('margin-left', left);
            }
        });
    };

    /** 渲染动态模板 */
    admin.renderTpl = function (elem) {
        if (!layui.admin) layui.admin = admin;

        // 解析数据
        function parseData(data) {
            if (!data) return;
            try {
                return new Function('return ' + data + ';')();
            } catch (e) {
                console.error(e + '\nlay-data: ' + data);
            }
        }

        $(elem || '[ew-tpl]').each(function () {
            var $this = $(this);
            var option = admin.util.deepClone($(this).data());
            option.elem = $this;
            option.tpl = $this.html();
            option.url = parseData($this.attr('ew-tpl'));
            option.headers = parseData(option.headers);
            option.where = parseData(option.where);
            if (option.done) {
                try {
                    option.done = new Function('res', option.done);
                } catch (e) {
                    console.error(e + '\nlay-data:' + option.done);
                    option.done = undefined;
                }
            }
            admin.util.render(option);
        });
    };

    /** 事件监听 */
    admin.on = function (events, callback) {
        return layui.onevent.call(this, 'admin', events, callback);
    };

    /** 修改配置信息 */
    admin.putSetting = function (key, value) {
        setter[key] = value;
        admin.putTempData(key, value, true);
    };

    /** 恢复配置信息 */
    admin.recoverState = function () {
        // 恢复锁屏状态
        if (admin.getTempData('isLockScreen', true)) admin.lockScreen(admin.getTempData('lockScreenUrl', true));
        // 恢复配置的主题
        if (setter.defaultTheme) admin.changeTheme(setter.defaultTheme, window, true, true);
        // 恢复页脚状态、导航箭头
        if (setter.closeFooter) $('body').addClass('close-footer');
        if (setter.navArrow !== undefined) {
            var $nav = $(sideDOM + '>.layui-nav-tree');
            $nav.removeClass('arrow2 arrow3');
            if (setter.navArrow) $nav.addClass(setter.navArrow);
        }
        // 恢复tab自动刷新
        if (setter.pageTabs && setter.tabAutoRefresh == 'true') $(tabDOM).attr('lay-autoRefresh', 'true');
    };

    /* 事件监听 */
    admin.on = function (events, callback) {
        return layui.onevent.call(this, 'admin', events, callback);
    };

    /** 侧导航折叠状态下鼠标经过无限悬浮效果 */
    var navItemDOM = '.layui-layout-admin.admin-nav-mini>.layui-side .layui-nav .layui-nav-item';
    $(document).on('mouseenter', navItemDOM + ',' + navItemDOM + ' .layui-nav-child>dd', function () {
        if (admin.getPageWidth() > 768) {
            var $that = $(this), $navChild = $that.find('>.layui-nav-child');
            if ($navChild.length > 0) {
                $that.addClass('admin-nav-hover');
                $navChild.css('left', $that.offset().left + $that.outerWidth());
                var top = $that.offset().top;
                if (top + $navChild.outerHeight() > admin.getPageHeight()) {
                    top = top - $navChild.outerHeight() + $that.outerHeight();
                    if (top < 60) top = 60;
                    $navChild.addClass('show-top');
                }
                $navChild.css('top', top);
                $navChild.addClass('ew-anim-drop-in');
            } else if ($that.hasClass('layui-nav-item')) {
                admin.tips({elem: $that, text: $that.find('cite').text(), direction: 2, offset: '12px'});
            }
        }
    }).on('mouseleave', navItemDOM + ',' + navItemDOM + ' .layui-nav-child>dd', function () {
        layer.closeAll('tips');
        var $this = $(this);
        $this.removeClass('admin-nav-hover');
        var $child = $this.find('>.layui-nav-child');
        $child.removeClass('show-top ew-anim-drop-in');
        $child.css({'left': 'auto', 'top': 'auto'});
    });

    /** 所有ew-event */
    $(document).on('click', '*[ew-event]', function () {
        var te = admin.events[$(this).attr('ew-event')];
        te && te.call(this, $(this));
    });

    /** 所有lay-tips处理 */
    $(document).on('mouseenter', '*[lay-tips]', function () {
        var $this = $(this);
        admin.tips({
            elem: $this, text: $this.attr('lay-tips'), direction: $this.attr('lay-direction'),
            bg: $this.attr('lay-bg'), offset: $this.attr('lay-offset'),
            padding: $this.attr('lay-padding'), color: $this.attr('lay-color'),
            bgImg: $this.attr('lay-bgImg'), fontSize: $this.attr('lay-fontSize')
        });
    }).on('mouseleave', '*[lay-tips]', function () {
        layer.closeAll('tips');
    });

    /** 表单搜索展开更多 */
    $(document).on('click', '.form-search-expand,[search-expand]', function () {
        var $this = $(this);
        var $form = $this.parents('.layui-form').first();
        var expand = $this.data('expand');
        var change = $this.attr('search-expand');
        if (expand === undefined || expand === true) {
            expand = true;
            $this.data('expand', false);
            $this.html('收起 <i class="layui-icon layui-icon-up"></i>');
            var $elem = $form.find('.form-search-show-expand');
            $elem.attr('expand-show', '');
            $elem.removeClass('form-search-show-expand');
        } else {
            expand = false;
            $this.data('expand', true);
            $this.html('展开 <i class="layui-icon layui-icon-down"></i>');
            $form.find('[expand-show]').addClass('form-search-show-expand');
        }
        if (!change) return;
        new Function('d', change)({expand: expand, elem: $this});
    });

    /** select使用fixed定位显示 */
    $(document).on('click.ew-sel-fixed', '.ew-select-fixed .layui-form-select .layui-select-title', function () {
        var $this = $(this), $dl = $this.parent().children('dl'), tTop = $this.offset().top;
        var tWidth = $this.outerWidth(), tHeight = $this.outerHeight(), scrollT = $(document).scrollTop();
        var dWidth = $dl.outerWidth(), dHeight = $dl.outerHeight();
        var top = tTop + tHeight + 5 - scrollT, left = $this.offset().left;
        if (top + dHeight > admin.getPageHeight()) top = top - dHeight - tHeight - 10;
        if (left + dWidth > admin.getPageWidth()) left = left - dWidth + tWidth;
        $dl.css({'left': left, 'top': top, 'min-width': tWidth});
    });

    /** 用于滚动时关闭一些fixed的组件 */
    admin.hideFixedEl = function () {
        $('.ew-select-fixed .layui-form-select').removeClass('layui-form-selected layui-form-selectup');  // select
        $('body>.layui-laydate').remove();  // laydate
    };

    /** 垂直导航栏展开折叠增加过渡效果 */
    $(document).on('click', '.layui-nav-tree>.layui-nav-item a', function () {
        var $this = $(this), $child = $this.siblings('.layui-nav-child'), $parent = $this.parent();
        if ($child.length === 0) return;
        if ($parent.hasClass('admin-nav-hover')) return;
        if ($parent.hasClass('layui-nav-itemed')) {  // 因为layui会处理一遍所以这里状态是相反的
            $child.css('display', 'none').slideDown('fast', function () {
                $(this).css('display', '');
            });
        } else {
            $child.css('display', 'block').slideUp('fast', function () {
                $(this).css('display', '');
            });
        }
        if ($this.parents('.layui-nav').attr('lay-shrink') === '_all') {  // 手风琴效果
            var $siblings = $this.parent().siblings('.layui-nav-itemed');
            $siblings.children('.layui-nav-child').css('display', 'block').slideUp('fast', function () {
                $(this).css('display', '');
            });
            $siblings.removeClass('layui-nav-itemed');
        }
    });
    $('.layui-nav-tree[lay-shrink="all"]').attr('lay-shrink', '_all');  // 让layui不处理手风琴效果

    /** 折叠面板展开折叠增加过渡效果 */
    $(document).on('click', '.layui-collapse>.layui-colla-item>.layui-colla-title', function () {
        var $this = $(this), $content = $this.siblings('.layui-colla-content')
            , $collapse = $this.parent().parent(), isNone = $content.hasClass('layui-show');
        if (isNone) {  // 因为layui会处理一遍所以这里状态是相反的
            $content.removeClass('layui-show').slideDown('fast').addClass('layui-show');
        } else {
            $content.css('display', 'block').slideUp('fast', function () {
                $(this).css('display', '');
            });
        }
        $this.children('.layui-colla-icon').html('&#xe602;')
            .css({'transition': 'all .3s', 'transform': 'rotate(' + (isNone ? '90deg' : '0deg') + ')'});
        if ($collapse.attr('lay-shrink') === '_all') {  // 手风琴效果
            var $show = $collapse.children('.layui-colla-item').children('.layui-colla-content.layui-show').not($content);
            $show.css('display', 'block').slideUp('fast', function () {
                $(this).css('display', '');
            });
            $show.removeClass('layui-show');
            $show.siblings('.layui-colla-title').children('.layui-colla-icon').html('&#xe602;')
                .css({'transition': 'all .3s', 'transform': 'rotate(0deg)'});
        }
    });
    $('.layui-collapse[lay-accordion]').attr('lay-shrink', '_all').removeAttr('lay-accordion');  // 让layui不处理手风琴效果

    /** 表单验证tips提示样式修改 */
    layer.oldTips = layer.tips;
    layer.tips = function (content, follow, options) {
        var $fFip;  // 判断是否是表单验证调用的tips
        if ($(follow).length > 0 && $(follow).parents('.layui-form').length > 0) {
            if ($(follow).is('input') || $(follow).is('textarea')) {
                $fFip = $(follow);
            } else if ($(follow).hasClass('layui-form-select') || $(follow).hasClass('layui-form-radio')
                || $(follow).hasClass('layui-form-checkbox') || $(follow).hasClass('layui-form-switch')) {
                $fFip = $(follow).prev();
            }
        }
        if (!$fFip) return layer.oldTips(content, follow, options);
        options.tips = [$fFip.attr('lay-direction') || 3, $fFip.attr('lay-bg') || '#ff4c4c'];
        setTimeout(function () {
            options.success = function (layero) {
                $(layero).children('.layui-layer-content').css('padding', '6px 12px');
            };
            layer.oldTips(content, follow, options);
        }, 100);
    };

    /** 所有ew-href处理 */
    $(document).on('click', '*[ew-href]', function () {
        var $this = $(this);
        var href = $this.attr('ew-href');
        if (!href || href === '#') return;
        if (href.indexOf('javascript:') === 0) return new Function(href.substring(11))();
        var title = $this.attr('ew-title') || $this.text();
        var win = $this.data('window');
        win ? (win = admin.strToWin(win)) : (win = top);
        var end = $this.attr('ew-end');
        try {
            if (end) end = new Function(end);
            else end = undefined;
        } catch (e) {
            console.error(e);
        }
        if (win.layui && win.layui.index) win.layui.index.openTab({title: title || '', url: href, end: end});
        else location.href = href;
    });

    /** 帮助鼠标右键菜单完成点击空白关闭的功能 */
    if (!layui.contextMenu) {
        $(document).off('click.ctxMenu').on('click.ctxMenu', function () {
            try {
                var ifs = top.window.frames;
                for (var i = 0; i < ifs.length; i++) {
                    var tif = ifs[i];
                    try {  // 可能会跨域
                        if (tif.layui && tif.layui.jquery) tif.layui.jquery('body>.ctxMenu').remove();
                    } catch (e) {
                    }
                }
                try {  // 可能会跨域
                    if (top.layui && top.layui.jquery) top.layui.jquery('body>.ctxMenu').remove();
                } catch (e) {
                }
            } catch (e) {
            }
        });
    }

    /** 读取缓存的配置信息 */
    setter = $.extend({
        pageTabs: true, cacheTab: true, openTabCtxMenu: true, maxTabNum: 30, tableName: 'qixing-web',
        // pageTabs: true, cacheTab: true, openTabCtxMenu: true, maxTabNum: 30, tableName: 'easyweb-iframe30',
        apiNoCache: true, ajaxSuccessBefore: function (res, url, obj) {
            return admin.ajaxSuccessBefore ? admin.ajaxSuccessBefore(res, url, obj) : true;
        }, getAjaxHeaders: function (res, url, obj) {
            return admin.getAjaxHeaders ? admin.getAjaxHeaders(res, url, obj) : [];
        }
    }, setter);
    var cache = admin.getTempData(true);
    if (cache) {
        var keys = ['pageTabs', 'cacheTab', 'defaultTheme', 'navArrow', 'closeFooter', 'tabAutoRefresh'];
        for (var i = 0; i < keys.length; i++) if (cache[keys[i]] !== undefined) setter[keys[i]] = cache[keys[i]];
    }
    admin.recoverState();  // 恢复本地配置
    admin.renderTpl();  // 渲染动态模板
    admin.setter = setter;
    if (layui.device().ios) $('body').addClass('ios-iframe-body');  // ios浏览器iframe兼容
    exports('admin', admin);
});
