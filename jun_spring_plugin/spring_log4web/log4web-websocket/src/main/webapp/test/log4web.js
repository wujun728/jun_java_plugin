/**
 * Created by houyhea on 14-12-19.
 * copyright © houyhea{at}126.com https://github.com/houyhea/log4web
 *
 */
(function () {
    'use strict';
    var VERSION = "1.0.0",
        globalScope = typeof global !== 'undefined' ? global : this,
        oldGlobalMoment,
        __config = {
            debug: 0,                     //是否开启调试模式。如果开启调试模式，则可以在console中输入window.log4web进行调试；
            level: "debug",             //日志级别，error(4)、warn(3)、info(2)、log(1)、debug(0),级别越高，输出的日志越少。比如：当前级别如果是warn，则只输出error、warn的日志
            tagFilter: "",               //日志tag筛选,正则表达式字符串
            post: 0,                    //当发生异常是是否post到服务器
            postContextInfo: 1,                //是否提交环境数据
            postUrl: "/api/log"     //异常信息提交的服务器地址
        };

    /**
     * 日志级别,级别越高，输出的日志越少。比如：当前级别如果是WARN，则只输出ERROR,WARN的日志
     *
     * @type {{error: number, warn: number, info: number, log: number, debug: number}}
     */
    var Level = {
        "error": 4,
        "warn": 3,
        "info": 2,
        "log": 1,
        "debug": 0
    }

    function extend(a, b) {
        if (!b || !a)
            return a;

        for (var i in b) {
            if (b.hasOwnProperty(i)) {
                a[i] = b[i];
            }
        }

        if (b.hasOwnProperty("toString")) {
            a.toString = b.toString;
        }

        if (b.hasOwnProperty("valueOf")) {
            a.valueOf = b.valueOf;
        }

        return a;
    }

    /**
     * 软硬件环境信息获取对象
     */
    var webContextInfo = (function () {
        //缓存
        var cacheInfo = {url: null, referrer: null, browser: null, os: null, flash: null, resolution: null};
        /**
         * 获取当前url
         * @returns {string|window.location.href|*}
         */
        var url = function () {
            if (cacheInfo.url != null)
                return cacheInfo.url;

            cacheInfo.url = document.location.href;
            return cacheInfo.url;
        }
        /**
         * 获取上一个访问页面
         * @returns {*}
         */
        var referrer = function () {

            return  document.referrer;


        };

        /**
         * 获取浏览器信息
         * @returns {string}
         */
        var browser = function () {
            if (cacheInfo.browser != null)
                return cacheInfo.browser;

            var br = {type: "Unknown", version: "0"};
            var explorer = window.navigator.userAgent.toLowerCase();
            //ie
            if (explorer.indexOf("msie") >= 0) {
                var ver = explorer.match(/msie ([\d.]+)/)[1];
                br = {type: "IE", version: ver};
            }
            //firefox
            else if (explorer.indexOf("firefox") >= 0) {
                var ver = explorer.match(/firefox\/([\d.]+)/)[1];
                br = {type: "Firefox", version: ver};
            }
            //Chrome
            else if (explorer.indexOf("chrome") >= 0) {
                var ver = explorer.match(/chrome\/([\d.]+)/)[1];
                br = {type: "Chrome", version: ver};
            }
            //Opera
            else if (explorer.indexOf("opera") >= 0) {
                var ver = explorer.match(/opera.([\d.]+)/)[1];
                br = {type: "Opera", version: ver};
            }
            //Safari
            else if (explorer.indexOf("Safari") >= 0) {
                var ver = explorer.match(/version\/([\d.]+)/)[1];
                br = {type: "Safari", version: ver};
            }
            cacheInfo.browser = br.type + "," + br.version;
            return cacheInfo.browser;
        };
        /**
         * 获取操作系统信息
         * @returns {string}
         */
        var os = function () {
            if (cacheInfo.os != null)
                return cacheInfo.os;
            var ret = "Unknown";
            var sUA = navigator.userAgent.toLowerCase();
            if (sUA.indexOf('win') != -1) {
                if (sUA.indexOf("nt 5.2") != -1) {
                    ret = "Windows 2003";
                }
                else if ((sUA.indexOf("nt 5.1") != -1) || (sUA.indexOf("XP") != -1)) {
                    ret = "Windows XP";
                }
                else if ((sUA.indexOf('nt 5.0') != -1) || (sUA.indexOf('2000') != -1)) {
                    ret = 'Windows 2000';
                }
                else if ((sUA.indexOf("winnt") != -1) || (sUA.indexOf("windows nt") != -1)) {
                    ret = "Windows NT";
                }
                else if ((sUA.indexOf("win98") != -1) || (sUA.indexOf("windows 98") != -1)) {
                    ret = "Windows 98";
                }
                ret = "Windows";
            }
            else if (sUA.indexOf('linux') != -1) {
                ret = 'Linux';
            }
            else if (sUA.indexOf("freebsd") != -1) {
                ret = 'FreeBSD';
            }
            else if (sUA.indexOf('x11') != -1) {
                ret = 'Unix';
            }
            else if (sUA.indexOf('mac') != -1) {
                ret = "Mac";
            }
            else if (sUA.indexOf("sunos") != -1) {
                ret = 'Sun OS';
            }
            else if ((sUA.indexOf("os/2") != -1) || (navigator.appVersion.indexOf("OS/2") != -1) || (sUA.indexOf("ibm-webexplorer") != -1)) {
                ret = "OS 2"
            }
            else if (navigator.platform == 'PalmOS') {
                ret = 'Palm OS';
            }
            else if ((navigator.platform == 'WinCE' ) || ( navigator.platform == 'Windows CE' ) || ( navigator.platform == 'Pocket PC' )) {
                ret = 'Windows CE';
            }
            else if (sUA.indexOf('webtv') != -1) {
                ret = 'WebTV Platform';
            }
            else if (sUA.indexOf('netgem') != -1) {
                ret = 'Netgem';
            }
            else if (sUA.indexOf('opentv') != -1) {
                ret = 'OpenTV Platform';
            }
            else if (sUA.indexOf('symbian') != -1) {
                ret = 'Symbian';
            }
            cacheInfo.os = ret;
            return cacheInfo.os;
        };
        /**
         * 获取flash插件信息
         * @returns {string}
         */
        var flash = function () {
            if (cacheInfo.flash != null)
                return cacheInfo.flash;

            //是否安装了flash
            var hasFlash = 0;
            //flash版本
            var flashVersion = 0;
            //是否IE浏览器
            var isIE = /*@cc_on!@*/0;

            if (isIE) {
                var swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
                if (swf) {
                    hasFlash = 1;
                    var vSwf = swf.GetVariable("$version");
                    flashVersion = parseInt(vSwf.split(" ")[1].split(",")[0]);
                }
            } else {
                if (navigator.plugins && navigator.plugins.length > 0) {
                    var swf = navigator.plugins["Shockwave Flash"];
                    if (swf) {
                        hasFlash = 1;
                        var words = swf.description.split(" ");
                        for (var i = 0; i < words.length; ++i) {
                            if (isNaN(parseInt(words[i])))
                                continue;
                            flashVersion = parseInt(words[i]);
                        }
                    }
                }
            }
            cacheInfo.flash = hasFlash + "," + flashVersion;
            return cacheInfo.flash;
        };
        /**
         * 获取屏幕分辨率信息
         * @returns {string}
         */
        var resolution = function () {
            if (cacheInfo.resolution != null)
                return cacheInfo.resolution;

            cacheInfo.resolution = window.screen.width + "*" + window.screen.height;
            return cacheInfo.resolution;
        };
        /**
         * 返回服务公共方法
         */
        return {referrer: referrer, browser: browser, os: os, flash: flash, resolution: resolution, url: url};
    })();


    var getPostData = function (msg) {

        var body = {};
        if (__config.postContextInfo) {
            body = {
                browser: webContextInfo.browser(),
                os: webContextInfo.os(),
                flash: webContextInfo.flash(),
                url: webContextInfo.url(),
                resolution: webContextInfo.resolution(),
                referrer: webContextInfo.referrer()
            }
        }
        if (msg instanceof Error) {
            body = extend(body, {name: msg.name, message: msg.message, stack: msg.stack});
        }
        else {
            body = extend(body, {name: "", message: msg+ "", stack: ""});
        }

        return body;
    }
    var post = function (msg) {
        var body = getPostData(msg);
        //依赖于jquery
        try {
            console.log(__config.postUrl);
            console.log(body)
            $.post(__config.postUrl, body);
            /*$.ajax( {
              url:__config.postUrl,// 跳转到 action  
              data:body,  
             type:'get', 
             async:false,
             cache:false,  
             dataType:'jsonp', 
             jsonp:"jsonpCallback",
             jsonpCallback: "success_jsonpCallback",
             success:function() {  
              }
                
            })*/
        }
        catch (e) {
            console ? console.error(e) : !0;
        }


    }

    var exec = function (level, msg, tag) {
        msg = JSON.stringify(msg);
        var logLevel = Level[__config.level] || Level["debug"];

        if (logLevel > Level[level])
            return;
        var needLog = false;
        if (tag != undefined) {
            var reg = new RegExp(__config.tagFilter);
            needLog = reg.test(tag);
        }
        else {
            needLog = true;
        }
        if (needLog) {

            console ? console[level](msg) : !0;
            console.log(msg)
            // if (msg instanceof Error && __config.post) {
            //     post(msg);
            // }

                post(msg);

        }
    }
    var config = function () {
        if (arguments.length == 0)
            return __config;
        var args = arguments[0];
        __config = extend(__config, args);

    }

    var error = function (msg, tag) {
        exec("error", msg, tag);
    }

    var warn = function (msg, tag) {
        exec("warn", msg, tag);
    }
    var info = function (msg, tag) {
        exec("info", msg, tag);
    }
    var log = function (msg, tag) {
        exec("log", msg, tag);
    }
    var debug = function (msg, tag) {
        exec("debug", msg, tag);
    }
    var log4web = {
        version: VERSION,
        config: config,
        log: log,
        warn: warn,
        info: info,
        error: error,
        debug: debug
    };


    log4web.noConflict = function () {
        globalScope.log4web = oldGlobalMoment;
        return log4web;
    }
    //输出模块
    if (typeof window.define === 'function' && window.define.amd) {
        window.define('log4web', [], function () {
            return log4web;
        });

    }
    else {
        oldGlobalMoment = globalScope.log4web;
        globalScope.log4web = log4web;
    }
    //用于调试模式
    if (__config.debug) {
        oldGlobalMoment = globalScope.log4web;
        globalScope.log4web = log4web;
    }

    return log4web;

}).call(this);


function include(path){ 
        var a=document.createElement("script");
        a.type = "text/javascript"; 
        a.src=path; 
        var head=document.getElementsByTagName("head")[0];
        head.appendChild(a);
    }
    
    var success_jsonpCallback = function(data){
        console.log(data)
    }