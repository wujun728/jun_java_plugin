/** navbar.js By Beginner Emain:zheng_jinfan@126.com HomePage:http://www.zhengjinfan.cn */
layui.define(['element', 'common'], function (exports) {
    "use strict";
    var $ = layui.jquery,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        element = layui.element,
        common = layui.common,
        cacheName = 'tb_navbar';

    var Navbar = function () {
		/**
		 *  默认配置 
		 */
        this.config = {
            elem: undefined, //容器
            data: undefined, //数据源
            url: undefined, //数据源地址
            type: 'GET', //读取方式
            cached: false, //是否使用缓存
            spreadOne: false //设置是否只展开一个二级菜单
        };
        this.v = '1.0.0';
    };
    //渲染
    Navbar.prototype.render = function () {
        var _that = this;
        var _config = _that.config;
        if (typeof (_config.elem) !== 'string' && typeof (_config.elem) !== 'object') {
            common.throwError('Navbar error: elem参数未定义或设置出错，具体设置格式请参考文档API.');
        }
        var $container;
        if (typeof (_config.elem) === 'string') {
            $container = $('' + _config.elem + '');
        }
        if (typeof (_config.elem) === 'object') {
            $container = _config.elem;
        }
        if ($container.length === 0) {
            common.throwError('Navbar error:找不到elem参数配置的容器，请检查.');
        }
        if (_config.data === undefined && _config.url === undefined) {
            common.throwError('Navbar error:请为Navbar配置数据源.')
        }
        if (_config.data !== undefined && typeof (_config.data) === 'object') {
            var html = getHtml(_config.data);
            $container.html(html);
            element.init();
            _that.config.elem = $container;
        } else {
            if (_config.cached) {
                var cacheNavbar = layui.data(cacheName);
                if (cacheNavbar.navbar === undefined) {
                    $.ajax({
                        type: _config.type,
                        url: _config.url,
                        async: false, //_config.async,
                        dataType: 'json',
                        success: function (result, status, xhr) {
                            //添加缓存
                            layui.data(cacheName, {
                                key: 'navbar',
                                value: result
                            });
                            var html = getHtml(result);
                            $container.html(html);
                            element.init();
                        },
                        error: function (xhr, status, error) {
                            common.msgError('Navbar error:' + error);
                        },
                        complete: function (xhr, status) {
                            _that.config.elem = $container;
                        }
                    });
                } else {
                    var html = getHtml(cacheNavbar.navbar);
                    $container.html(html);
                    element.init();
                    _that.config.elem = $container;
                }
            } else {
                //清空缓存
                layui.data(cacheName, null);
                $.ajax({
                    type: _config.type,
                    url: _config.url,
                    async: false, //_config.async,
                    dataType: 'json',
                    success: function (result, status, xhr) {
                        var html = getHtml(result);
                        $container.html(html);
                        element.init();
                    },
                    error: function (xhr, status, error) {
                        common.msgError('Navbar error:' + error);
                    },
                    complete: function (xhr, status) {
                        _that.config.elem = $container;
                    }
                });
            }
        }

        //只展开一个二级菜单
        if (_config.spreadOne) {
            var $ul = $container.children('ul');
            $ul.find('li.layui-nav-item').each(function () {
                $(this).on('click', function () {
                    $(this).siblings().removeClass('layui-nav-itemed');
                });
            });
        }
        return _that;
    };
	/**
	 * 配置Navbar
	 * @param {Object} options
	 */
    Navbar.prototype.set = function (options) {
        var that = this;
        that.config.data = undefined;
        $.extend(true, that.config, options);
        return that;
    };
	/**
	 * 绑定事件
	 * @param {String} events
	 * @param {Function} callback
	 */
    Navbar.prototype.on = function (events, callback) {
        var that = this;
        var _con = that.config.elem;
        if (typeof (events) !== 'string') {
            common.throwError('Navbar error:事件名配置出错，请参考API文档.');
        }
        var lIndex = events.indexOf('(');
        var eventName = events.substr(0, lIndex);
        var filter = events.substring(lIndex + 1, events.indexOf(')'));
        if (eventName === 'click') {
            if (_con.attr('lay-filter') !== undefined) {
                _con.children('ul').find('li').each(function () {
                    var $this = $(this);
                    if ($this.find('dl').length > 0) {
                        var $dd = $this.find('dd').each(function () {
                            $(this).on('click', function () {
                                var $a = $(this).children('a');
                                var href = $a.data('url');
                                var icon = $a.children('i:first').data('icon');
                                icon="";
                                var title = $a.children('cite').text();
                                var data = {
                                    elem: $a,
                                    field: {
                                        href: href,
                                        icon: icon,
                                        title: title
                                    }
                                }
                                callback(data);
                            });
                        });
                    } else {
                        $this.on('click', function () {
                            var $a = $this.children('a');
                            var href = $a.data('url');
                            var icon = $a.children('i:first').data('icon');
                            icon="";
                            var title = $a.children('cite').text();
                            var data = {
                                elem: $a,
                                field: {
                                    href: href,
                                    icon: icon,
                                    title: title
                                }
                            }
                            callback(data);
                        });
                    }
                });
            }
        }
    };
	/**
	 * 清除缓存
	 */
    Navbar.prototype.cleanCached = function () {
        layui.data(cacheName, null);
    };
	/**
	 * 获取html字符串
	 * @param {Object} data
	 */
    function getHtml(data) {
    	//debugger;
        var ulHtml = '<ul class="layui-nav layui-nav-tree beg-navbar">';
        $.each(data,function(i,obj){
            ulHtml += '<li class="layui-nav-item">';
            if (obj.spread) {
            	ulHtml += '<li class="layui-nav-item layui-nav-itemed">';
            }
            if (obj.children !== undefined && obj.children !== null && obj.children.length > 0) {
            	ulHtml += '<a href="javascript:;">';
            	if (obj.icon !== null && obj.icon !== undefined && obj.icon !== '') {
                    if (obj.icon.indexOf('fa-') !== -1) {
                    	ulHtml += '<i class="fa ' + obj.icon + '" aria-hidden="true" data-icon="' + obj.icon + '"></i>';
                    } else {
                    	ulHtml += '<i class="layui-icon ' + obj.icon + '"></i>';
                    }
                }
            	ulHtml += '<cite>' + obj.title + '</cite>'
            	ulHtml += '</a>';
            }else{
            	var dataUrl = (obj.href !== undefined && obj.href !== '') ? 'data-url="' + obj.href + '"' : '';
                if(obj.spread){
                	ulHtml = ulHtml.replace("class=\"layui-this\"","");
                	dataUrl +=' class="layui-this"';
                }
                if(obj.isWindowOpen==1){
                	ulHtml += '<a target="_blank" href="' + obj.href + '">';
                }else{
                	ulHtml += '<a href="javascript:;" ' + dataUrl + '>';              	
                }
                if (obj.icon !== null && obj.icon !== undefined && obj.icon !== '') {
                    if (obj.icon.indexOf('fa-') !== -1) {
                    	ulHtml += '<i class="fa ' + obj.icon + '" aria-hidden="true" data-icon="' + obj.icon + '"></i>';
                    } else {
                    	ulHtml += '<i class="layui-icon ' + obj.icon + '"></i>';
                    }
                }
                ulHtml += '<cite>' + obj.title + '</cite>'
                ulHtml += '</a>';
            }
            //这里是添加所有的子菜单
            ulHtml += loadchild(obj,0);
            ulHtml += '</li>';
        });
        ulHtml += '</ul>';
        return ulHtml;
    }

    //组装子菜单的方法
    var dep=0;
    function loadchild(obj,num){
        if(obj==null){
            return;
        }
             
        var content='';
        if(obj.children!=null && obj.children.length>0){
        	dep++;
        	num=num*dep;
        	content += '<dl class="layui-nav-child">';
            $.each(obj.children,function(i,note){
            	if (note.children !== undefined && note.children !== null && note.children.length > 0) {
            		if(note.spread){
                		content += '<dd title="' + note.title + '" style="margin-left:'+num+'px;" class="layui-nav-itemed">';    
                	}else{
                		content += '<dd title="' + note.title + '" style="margin-left:'+num+'px;">';                  		
                	}
                	content += '<a href="javascript:;">';
                	if (note.icon !== null && note.icon !== undefined && note.icon !== '') {
                        if (note.icon.indexOf('fa-') !== -1) {
                        	content += '<i class="fa ' + note.icon + '" aria-hidden="true" data-icon="' + note.icon + '"></i>';
                        } else {
                        	content += '<i class="layui-icon ' + note.icon + '"></i>';
                        }
                    }
                	content += '<cite>' + note.title + '</cite>'
                	content += '</a>';
                	content+=loadchild(note,3);
                }else{
                	content += '<dd title="' + note.title + '" style="margin-left:'+num+'px;">';
                	var dataUrl = (note.href !== undefined && note.href !== '') ? 'data-url="' + note.href + '"' : '';
                    if(note.spread){
                    	content=content.replace("class=\"layui-this\"","");
                    	dataUrl+=' class="layui-this"';
                    }
                    if(note.isWindowOpen==1){
                    	content += '<a target="_blank" href="' + note.href + '">';
                    }else{                 	
                    	content += '<a href="javascript:;" ' + dataUrl + '>';
                    }
                    if (note.icon !== null && note.icon !== undefined && note.icon !== '') {
                        if (note.icon.indexOf('fa-') !== -1) {
                        	content += '<i class="fa ' + note.icon + '" aria-hidden="true" data-icon="' + note.icon + '"></i>';
                        } else {
                        	content += '<i class="layui-icon ' + note.icon + '"></i>';
                        }
                    }
                    content += '<cite>' + note.title + '</cite>'
                    content += '</a>';
                }
                content += '</dd>';
            });

            content += '</dl>';
        }
        return content;
    }
    
    var navbar = new Navbar();

    exports('navbar', function (options) {
        return navbar.set(options);
    });
});