/**
* jQuery JavaScript Library v1.9.x && v2.x
* http://jquery.com/
*
* Copyright 2005, 2012 jQuery Foundation, Inc. and other contributors
* Released under the MIT license
* http://jquery.org/license
*
* jQuery Extensions Basic Library 基础函数工具包 v1.0 beta
* jquery.jdirk.js
* 二次开发 流云
* 最近更新：2014-06-26
*
* 依赖项：jquery 1.9.x/2.x late
*
* Copyright 2013-2014 ChenJianwei personal All rights reserved.
* http://www.chenjianwei.org
*/
(function (window, $, undefined) {


    var version = "2014-04-24",

        //  定义 字符串对象(String) 扩展对象基元
        coreString = function () { return String.apply(this, arguments); },

        //  定义 日期对象(Date) 扩展对象基元
        coreDate = function () { return Date.apply(this, arguments); },

        //  定义 数值对象(Number) 扩展对象基元
        coreNumber = function () { return Number.apply(this, arguments); },

        //  定义 数组对象(Array) 扩展对象基元
        coreArray = function () { return Array.apply(this, arguments); },

        //  定义 布尔值对象(Boolean) 扩展对象基元
        coreBoolean = function () { return Boolean.apply(this, arguments); },

        //  定义 通用工具方法 扩展对象基元
        coreUtil = function () { return Object.apply(this, arguments); },

        //  定义 空值 集合基元
        coreNullable = {},

        //  定义 jQuery 扩展对象基元
        coreJquery = function () { return $.apply(this, arguments); },

        //  定义 HTML5 工具组件对象基元
        coreHtml5 = {};

    coreString.fn = coreString.prototype = {};
    coreDate.fn = coreDate.prototype = {};
    coreNumber.fn = coreNumber.prototype = {};
    coreArray.fn = coreArray.prototype = {};
    coreBoolean.fn = coreBoolean.prototype = {};
    coreUtil.fn = coreUtil.prototype = {};
    coreJquery.fn = coreJquery.prototype = {};

    coreNullable.String = new String();
    coreNullable.Date = new Date();
    coreNullable.Number = new Number();
    coreNullable.Array = [];
    coreNullable.Boolean = new Boolean();
    coreNullable.Function = new Function();
    coreNullable.Object = new Object();

    coreJquery.string = coreString;
    coreJquery.date = coreDate;
    coreJquery.number = coreNumber;
    coreJquery.array = coreArray;
    coreJquery.boolean = coreBoolean;
    coreJquery.util = coreUtil;
    coreJquery.nullable = coreNullable;
    coreJquery.html5 = coreHtml5;



    var document = coreUtil.document = window.document,
        location = coreUtil.location = window.location,
        docElem = coreUtil.docElem = document.documentElement,
        history = coreUtil.history = window.history,
        parent = coreUtil.parent = window.parent,
        top = coreUtil.top = window.top,
        $$ = coreJquery.emptyJquery = coreJquery.empty$ = coreJquery.$$ = coreUtil.emptyJquery = coreUtil.empty$ = coreUtil.$$ = $(),
        core_string = version,
        //core_date = coreNullable.Date,
        //core_number = coreNullable.Number,
        core_array = coreNullable.Array,
        //core_boolean = coreNullable.Boolean,
        //core_trim = core_string.trim,
        core_push = core_array.push,
        core_slice = core_array.slice,
        core_splice = core_array.splice,
        //core_sort = core_array.sort,
        //core_join = core_array.join,
        core_isArray = Array.isArray;


    //  定义版本
    coreUtil.version = version;

    /////////////////////////////////////////////////////////////////////////////////////////////// 
    //  javascript 工具，提供常用的 js 工具方法。
    //  包括解析和判断对象的类型、url 解析等功能。
    ///////////////////////////////////////////////////////////////////////////////////////////////


    //  获取指定对象的类型。
    coreUtil.type = $.type;

    //  测试对象是否是窗口（有可能是Frame）。
    coreUtil.isWindow = $.isWindow;

    //  测试对象是否是布尔（Boolean）类型值。
    coreUtil.isBoolean = function (obj) { return coreUtil.type(obj) == "boolean"; };

    //  测试对象是否是字符串（String）类型值。
    coreUtil.isString = function (obj) { return coreUtil.type(obj) == "string"; };

    //  测试对象是否是日期（Date）类型值。
    coreUtil.isDate = function (obj) { return coreUtil.type(obj) == "date"; };

    //  测试对象是否是正则表达式（RegExp）。
    coreUtil.isRegExp = function (obj) { return coreUtil.type(obj) == "regexp"; };

    //  测试传入的参数是否是一个 javscript 对象；
    coreUtil.isObject = function (obj) { return coreUtil.type(obj) == "object"; };

    //  测试对象是否是数组（Array）。
    coreUtil.isArray = $.isArray;

    //  测试对象是否是函数。
    //  注意：在IE浏览器里，浏览器提供的函数比如'alert'还有 DOM 元素的方法比如 'getAttribute' 将不认为是函数。
    coreUtil.isFunction = $.isFunction;

    //  测试对象是否是数值或数值格式的字符串。
    //  方法检查它的参数是否代表一个数值。如果是这样，它返回 true。否则，它返回false。该参数可以是任何类型的。
    coreUtil.isNumeric = coreUtil.likeNumber = coreUtil.likeNumeric = $.isNumeric;

    //  判断对象是否是数值类型；
    coreUtil.isNumber = function (obj) { return coreUtil.type(obj) == "number"; };

    //  测试对象是否是空对象（不包含任何属性）。
    //  这个方法既检测对象本身的属性，也检测从原型继承的属性（因此没有使用hasOwnProperty）。
    coreUtil.isEmptyObject = $.isEmptyObject;

    //  测试对象是否为空（不包含任何属性的空对象、null、undefined、空字符串、全空格）。
    //  这个方法既检测对象本身的属性，也检测从原型继承的属性（因此没有使用hasOwnProperty）。
    coreUtil.isEmptyObjectOrNull = function (obj) {
        switch (coreUtil.type(obj)) {
            case "string":
                return coreString.isNullOrWhiteSpace(obj);
            case "array":
                return obj.length == 0;
            case "date":
                return Date.parse(obj) == 0;
            case "object":
                return coreUtil.isEmptyObject(obj);
        }
        return obj == null || obj == undefined;
    };

    //  测试对象是否是纯粹的对象（通过 "{}" 或者 "new Object" 创建的）。
    coreUtil.isPlainObject = $.isPlainObject;

    //  判断对象是否为 "未定义" 值(即 undefined)。
    coreUtil.isUndefined = function (obj) { return obj === undefined || typeof obj === "undefined"; };

    //  判断对象是否为空(Null)值。
    coreUtil.isNull = function (obj) { return obj === null; };

    //  判断对象是否为 "未定义" 值(即 undefined)或空(Null)值。
    coreUtil.isNullOrUndefined = function (obj) { return coreUtil.isUndefined(obj) || coreUtil.isNull(obj); };

    //  测试对象不为 "未定义" 值(即 undefined)、空(Null)值、Boolean-False值、空字符串值或数字0中的任何一种。
    coreUtil.isPositive = function (obj) { return obj ? true : false; };

    //  判断对象是否为 "未定义" 值(即 undefined)、空(Null)值、Boolean-False值、空字符串值或数字0中的一种。
    coreUtil.isNegative = function (obj) { return obj ? false : true; };

    //  测试对象是否是 jQuery 对象。
    coreUtil.isJqueryObject = function (obj) { return obj != null && obj != undefined && ((obj.jquery ? true : false) || obj.constructor === $$.constructor); };

    //  判断对象是否是一个空的 jQuery 对象(不包含任何 DOM 元素，即 length = 0)。
    coreUtil.isEmptyJquery = coreUtil.isEmptyJqueryObject = function (obj) { return coreUtil.isJqueryObject(obj) && !obj.length; };

    //  定义一个空函数
    coreUtil.noop = coreUtil.isFunction($.noop) ? $.noop : function () { };

    //  判断传入的字符串是否为Null或者为空字符串或者全是空格。
    coreUtil.trim = $.trim;

    //  将一个 DOM 对象或者表达式解析为 jQuery 对象；
    //  如果该对象本身就已经是一个 jQuery 对象，则直接将其返回。
    coreUtil.parseJqueryObject = coreUtil.parseJquery = function (obj) { return coreUtil.isJqueryObject(obj) ? obj : $(obj); };

    //  检测一个对象是否为一个数组对象或者类似于数组对（具有数组的访问方式：具有 length 属性、且具有属性名为数字的索引访问器）
    //  注意：此方法传入 字符串 时执行，也会返回 true，因为 字符串 是一个字符数组。
    coreUtil.likeArray = function (obj) {
        if (obj == null || obj == undefined || coreUtil.isWindow(obj)) {
            return false;
        }
        if (obj.nodeType === 1 && obj.length) {
            return true;
        }
        var type = coreUtil.type(obj);
        return type === "array" || type !== "function" && coreUtil.isNumeric(obj.length) && obj.length >= 0;
    };

    //  检测一个对象是否为一个数组对象或者类似于数组对（具有数组的访问方式：具有 length 属性、且具有属性名为数字的索引访问器）且不是字符串
    coreUtil.likeArrayNotString = function (obj) {
        return coreUtil.likeArray(obj) && !coreUtil.isString(obj);
    };

    //  获取当前页面 url 参数。
    //  返回值：该方法返回一个数组，数组中的每个元素都是一个 JSON 对象，该 JSON 对象包含如下属性：
    //      name:   表示 url 参数的名称；
    //      value:  表示 url 参数的值；
    //  也可以通过数组访问器快速访问某个特定名称的参数值，方法如：coreUtil.getRequest()["id"]。
    coreUtil.getRequest = function () {
        var search = location.search;
        if (search.substr(0, 1) == "?") { search = search.substr(1, search.length - 1); }
        var result = [];
        if (search.length > 0) {
            var params = search.split("&");
            for (var i = 0; i < params.length; i++) {
                var param = params[i];
                var pos = param.indexOf("=");
                var name = param.substring(0, pos);
                var value = param.substr(pos + 1);
                result.push({ name: name, value: value });
                result[name] = value;
            }
        }
        return result;
    };
    coreUtil.request = coreUtil.getRequest();

    //  生成一个 Guid(全球唯一标识符) 值；该函数定义如下参数：
    //      format: String 类型值，一个单格式说明符，它指示如何格式化此 Guid 的值。‎format 参数可以是：
    //          "N":    返回值的格式 32 位(xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
    //          "D":    返回值的格式 由连字符分隔的 32 位数字(xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx)
    //          "B":    返回值的格式 括在大括号中、由连字符分隔的 32 位数字({xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx})
    //          "P":    返回值的格式 括在圆括号中、由连字符分隔的 32 位数字((xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx))
    //          如果 format 为 null 或空字符串 ("")，则使用 "D"。
    //      length: Number 类型值，表示返回字符串的长度；如果不定义该参数，则全部返回。
    coreUtil.guid = function (format, length) {
        format = coreUtil.isString(format) ? format.toLowerCase() : "d";
        length = (length == null || length == undefined || !coreUtil.isNumeric(length)) ? 32 : length;
        if (length > 32 || length == 0) { length = 32; }
        if (length < -32) { length = -32; }
        var ret = "";
        for (var i = 1; i <= 32; i++) {
            ret += Math.floor(Math.random() * 16.0).toString(16);
            if ((i == 8) || (i == 12) || (i == 16) || (i == 20)) { ret += "-"; }
        }
        switch (format) {
            case "n": ret = coreString.replaceAll(ret, "-", ""); break;
            case "b": ret = "{" + ret + "}"; break;
            case "p": ret = "(" + ret + ")"; break;
            case "d": default: break;
        }
        return length >= 0 ? coreString.left(ret, length) : coreString.right(ret, Math.abs(length));
    };

    //  获取当前应用程序的完整主机地址部分，返回的结果格式如( http://127.0.0.1 )
    coreUtil.getHostPath = function () {
        var href = location.href;
        var pathname = location.pathname;
        return href.substr(0, href.lastIndexOf(pathname));
    };
    coreUtil.hostPath = coreUtil.getHostPath();

    //  返回当前页面不带参数的完整路径。
    coreUtil.currentUri = coreUtil.hostPath + location.pathname;

    //  返回当前页面所在目录的完整路径。
    coreUtil.currentPath = coreUtil.currentUri.substr(0, coreUtil.currentUri.lastIndexOf("/"));

    //  表示当前应用程序所嵌套的虚拟目录的层数。默认为 0，表示未嵌套虚拟目录。
    coreUtil.rootDegree = 0;

    //  返回当前应用程序（含站点名或者虚拟目录路径）的完整根路径。
    //  该方法依赖于全局参数 coreUtil.rootDegree 的值。
    //  coreUtil.rootDegree 该全局参数表示 虚拟目录 的层数。
    //  coreUtil.rootDegree 参数设置正确，该方法方能返回正确的结果。
    //  coreUtil.rootDegree 默认值为 0，即应用程序没有设置虚拟目录。
    coreUtil.getRootPath = function () {
        var result = coreUtil.hostPath;
        var pathname = location.pathname;
        if (pathname.indexOf("/") > -1) {
            var paths = pathname.split("/");
            var temps = new Array();
            for (var i = 0; i < paths.length; i++) { if (paths[i].length > 0) { temps.push(paths[i]); } }
            for (var i = 0; i < coreUtil.rootDegree && i < temps.length; i++) { result += "/" + temps[i]; }
        }
        return result;
    };
    coreUtil.rootPath = coreUtil.getRootPath();

    //  根据传入的 uri 地址返回该 uri 相对于应用程序的完整客户端访问url地址。
    //  传入的 uri 地址应为相对于应用程序根路径的完整地址。
    //  该方法依赖于当前文件的 coreUtil.rootPath 属性。
    coreUtil.resolveClientUrl = coreUtil.resolveUrl = function (url) {
        url = String(url);
        if (coreString.isNullOrEmpty(url)) { return url; }
        if (coreString.isUrl(url)) { return url; }
        url = coreString.replaceAll(url, "\\", "/");
        while (url.substring(0, 2) == "./" || url.substring(0, 1) == "/") { url = url.substring(1, url.length); }
        var tmps1 = coreUtil.rootPath.split("/");
        var tmps2 = url.split("/");
        while (tmps1.length > 3 && tmps2.length > 1 && tmps2[0] == "..") { tmps1.pop(); tmps2.shift(); }
        while (tmps2.length > 1 && tmps2[0] == "..") { tmps2.shift(); }
        return tmps1.join("/") + "/" + tmps2.join("/");
    };

    //  在不弹出关闭提示确认的情况下直接关闭当前浏览器窗口。
    coreUtil.closeWindowNoConfirm = function () {
        coreUtil.top.opener = null;
        coreUtil.top.open('', '_self', '');
        coreUtil.top.close();
    };

    //  关闭当前浏览器窗口，同 window.close。
    coreUtil.closeWindow = window.close;

    //  屏蔽当前页面的 HTML 源代码。
    //  有 bug，不建议使用。
    coreUtil.shieldSourceCode = function () {
        var source = document.body.innerHTML;  //获取文档的原有内容
        document.open();                 //打开文档
        document.close();                //关闭文档
        document.body.innerHTML = source;  //重新写入旧内容
    };

    //  屏蔽当前页面的鼠标右键默认事件，而调用指定的回调函数。
    //  如果回调函数为空，则点击鼠标右键没有任何反应。
    coreUtil.shieldContxtMenuEvent = function (callback) {
        document.oncontextmenu = function (e) {
            e.preventDefault();
            if (callback && coreUtil.type(callback) == "function") { callback.apply(this, arguments); }
        };
    };


    function _loadJs(url, callback) {
        var heads = document.getElementsByTagName("head");
        var scripts = heads[0].getElementsByTagName("script");
        var isFunc = coreUtil.isFunction(callback);
        for (var i = 0; i < scripts.length; i++) {
            var s = scripts[i];
            if (s.src == url) { if (isFunc) { callback.call(s); } return; }
        }
        var done = false;
        var script = document.createElement('script');
        script.type = "text/javascript";
        script.language = "javascript";
        script.src = url;
        script.onload = script.onreadystatechange = function () {
            if (!done && (!script.readyState || script.readyState == "loaded" || script.readyState == "complete")) {
                done = true;
                script.onload = script.onreadystatechange = null;
                if (isFunc) { callback.call(script); }
            }
        };
        heads[0].appendChild(script);
    }
    function _loadCss(url, callback) {
        var link = document.createElement('link');
        link.rel = "stylesheet";
        link.type = "text/css";
        link.media = "screen";
        link.href = url;
        document.getElementsByTagName("head")[0].appendChild(link);
        var isFunc = coreUtil.isFunction(callback);
        if (isFunc) { callback.call(link); }
    }

    //  动态引入一个或多个 javascript 脚本文件至当前页面文档，并在引入成功后调用指定的回调函数。
    //  参数 url 表示需要载入的 javascript 脚本路径；如果需要一次性载入多个脚本，则 url 可以是一个数组，数组中每个元素表示 javascript 脚本路径。
    coreUtil.loadJs = function (url, callback) {
        if (coreUtil.likeArray(url) && !coreUtil.isString(url)) {
            for (var i = 0; i < url.length; i++) {
                var fn = (i == url.length - 1) ? callback : null;
                _loadJs(url[i], fn);
            }
        } else { _loadJs(url, callback); }
    };

    //  一次性执行某个 javascript 脚本文件，并在执行成功后调用指定的回调函数。
    coreUtil.runJs = function (url, callback) {
        var isFunc = coreUtil.isFunction(callback);
        _loadJs(url, function () {
            document.getElementsByTagName("head")[0].removeChild(this);
            if (isFunc) { callback(); }
        });
    };

    //  动态引入一个或多个 css 样式表文件至当前页面文档，并在引入成功后调用指定的回调函数。
    coreUtil.loadCss = function (url, callback) {
        if (coreUtil.likeArray(url) && !coreUtil.isString(url)) {
            for (var i = 0; i < url.length; i++) {
                var fn = (i == url.length - 1) ? callback : null;
                _loadCss(url, fn);
            }
        } else {
            _loadCss(url, callback);
        }
    };

    //  对某个对象及其所有可见属性进行多次嵌套递归循环调用某个方法；该函数定义如下参数：
    //      obj:    目标对象
    //      call:   要被针对目标对象循环调用的方法
    //      times:  嵌套的层数
    coreUtil.eachCall = function (obj, call, times) {
        if (!coreUtil.isFunction(call)) { return; }
        if (obj == undefined) { obj = coreNullable.Object; }
        if (times == undefined || times < 0) { times = 1; }
        if (times == 0) { return obj; }
        call.call(this, obj);
        for (var i = 1; i <= times; i++) { for (var key in obj) { coreUtil.eachCall.call(this, obj[key], call, times - 1); } }
    };

    //  阻止向对象添加新属性。
    //  模拟 Object.preventExtensions 方法；
    coreUtil.preventExtensions = function (obj, times) {
        coreUtil.eachCall.call(this, obj, Object.preventExtensions, times);
    };
    //  阻止修改现有属性的特性，并阻止添加新属性。
    //  模拟 Object.seal 方法；
    coreUtil.seal = function (obj, times) {
        coreUtil.eachCall.call(this, obj, Object.seal, times);
    };
    //  阻止修改现有属性的特性和值，并阻止添加新属性。
    //  模拟 Object.freeze 方法；
    coreUtil.freeze = function (obj, times) {
        coreUtil.eachCall.call(this, obj, Object.freeze, times);
    };

    //  在指定的毫秒数后调用函数或计算表达式；该函数定义如下参数：
    //      code:       必需。要调用的函数后要执行的 JavaScript 代码串。
    //      millisec:   可选。在执行代码前需等待的毫秒数。
    //  模拟 setTimeout/setImmediate 方法。
    //  备注：如果不传入参数 millisec 或该参数值为 0，则自动调用 setImmediate(该方法相对于 setTimeout 可以有效降低浏览器资源开销) 方法；
    coreUtil.exec = function (code, millisec) {
        if (!code) { return; }
        return !millisec && window.setImmediate ? window.setImmediate(code) : window.setTimeout(code, millisec);
    };


    //  以 try...catch... 方式执行指定的函数代码块；该函数提供如下重载方式：
    //      function (options):该重载中，参数 options 表示一个 JSON-Object.
    //          格式如 { code: function|string , error: function|string , final: function|string , tryError: boolean , tryFinal: boolean }
    //          其中 code 表示将被 try 块执行的函数代码块.
    //               error 表示在 code 执行出错时将执行的代码块.
    //               final 表示在 code 和 error 执行完成后将执行的代码块.
    //               tryError 指定 error 是否同样以 try...catch... 方式执行.
    //               tryFinal 指定 final 是否同样以 try...catch... 方式执行.
    //      function (code, error, final): 该重载将会被自动转换成 function ({ code: code, error: error, final: finall, tryError: false, tryFinal: false }) 方式执行；
    //  返回值：如果 code 代码块执行成功，则返回该代码块的执行返回值；
    //      否则判断 error 和 final 代码块是否具有返回值；
    //          如果这两个代码块都有返回值，则取 final 的执行结果返回；
    //          如果 error 和 final 两个代码块只有其中一个具有返回值，则返回那个具有返回值的代码块的执行结果。
    coreUtil.tryExec = function (code, error, final) {
        var defaults = {
            code: null, error: null, final: null, tryError: false, tryFinal: false
        };
        var opts = $.extend(defaults, typeof code == "object" ? code : { code: code, error: error, final: final }), ret;
        if (typeof opts.code == "string") { opts.code = coreString.toFunction(opts.code); }
        if (typeof opts.error == "string") { opts.error = coreString.toFunction(opts.error); }
        if (typeof opts.final == "string") { opts.final = coreString.toFunction(opts.final); }
        try {
            if (coreUtil.isFunction(opts.code)) {
                ret = opts.code();
            }
        } catch (e) {
            if (coreUtil.isFunction(opts.error)) {
                var a = opts.tryError ? coreUtil.tryExec(opts.error) : opts.error(e);
                if (a != undefined) { ret = a; }
            }
        } finally {
            if (coreUtil.isFunction(opts.final)) {
                var b = opts.tryFinal ? coreUtil.tryExec(opts.final) : opts.final();
                if (b != undefined) { ret = b; }
            }
        }
        return ret;
    };





    var _matched, _browser;
    var _userAgentMatch = function (userAgent) {
        userAgent = userAgent.toLowerCase();
        var match = /(chrome)[ \/]([\w.]+)/.exec(userAgent) ||
		    /(webkit)[ \/]([\w.]+)/.exec(userAgent) ||
		    /(opera)(?:.*version|)[ \/]([\w.]+)/.exec(userAgent) ||
		    /(msie) ([\w.]+)/.exec(userAgent) ||
		    userAgent.indexOf("compatible") < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec(userAgent) || [];
        return { browser: match[1] || "", version: match[2] || "0" };
    };
    _matched = _userAgentMatch(window.navigator.userAgent);
    _browser = {};
    if (_matched.browser) { _browser[_matched.browser] = true; _browser.version = _matched.version; }
    if (_browser.chrome) { _browser.webkit = true; } else if (_browser.webkit) { _browser.safari = true; }

    //  获取浏览器的名称以及版本号。
    //  判断浏览器版本示例：判断浏览器是否为IE：  coreUtil.browser.msie == true，判断浏览器是否为 Chrome：window.browser.chrome == true
    //  判断浏览器版本号：coreUtil.browser.version，IE下可能的值为 6.0/7.0/8.0/9.0/10.0 等等。
    coreUtil.browser = _browser;




    //  定义默认的对象比较函数，该函数返回一个 bool 值表示传入的两个对象是否等值。
    coreUtil.equalsCompare = function (item1, item2) { return item1 == item2; };

    //  定义默认的数值比较函数，该函数返回一个 int 值，该返回值的意义如下：
    //      如果大于 0，则表示 a > b；
    //      如果小于 0，则表示 a < b；
    //      如果等于 0，则表示 a == b。
    coreUtil.numericCompare = function (a, b) {
        if (!coreUtil.isNumeric(a) && coreString.isNumeric(a)) { a = parseFloat(a, 10); }
        if (!coreUtil.isNumeric(b) && coreString.isNumeric(b)) { b = parseFloat(b, 10); }
        if (a > b) { return 1; } else if (a < b) { return -1; } else { return 0; }
    };

    //  确认两个 javascript 对象是否等值，该函数定义如下参数:
    //      item1: 待比较的对象1；
    //      item2: 待比较的对象2，用于和对象1进行比较；
    //      compare: 用于比较运算的函数，该函数用于比较 item1 是否与 item2 等值；该函数返回一个 bool 值；这是一个可选参数。
    //          该函数的签名应该是 function (item1, item2) { }，参数 item1 表示源数组中的项、item2 表示要进行比较的项；
    //          如果不定义参数 compare，则使用默认的比较运算符 "==" 进行值的匹配；
    //  返回值：如果 item1 与 item2 等值，则返回 true，否则返回 false。
    coreUtil.equals = function (item1, item2, compare) {
        var isFunc = coreUtil.isFunction(compare);
        if (!isFunc) { compare = coreUtil.equalsCompare; }
        return compare.call(this, item1, item2) == true;
    };

    //  比较两个 javascript 对象的大小，该函数定义如下参数：
    //      item1: 待比较的对象1；
    //      item2: 待比较的对象2，用于和对象1进行比较；
    //      compare: 比较函数，该函数被循环调用，用于比较 array 中没两项的大小；这是一个可选参数；
    //          该函数的签名为 function (a, b) { }，参数 a、b 分别表示源数组中的待比较大小的项；该函数必须返回一个数值表示比较后的结果；
    //              如果 a > b ，则返回一个 大于 0 的值；
    //              如果 a < b ，则返回一个 小于 0 的值；
    //              如果 a == b，则返回 0；
    //          如果不定义该参数，则默认将 array 中的每一项当作数字来进行比较。
    //  返回值：如果 item1 > item2 ，则返回一个 大于 0 的值；
    //          如果 item1 < item2 ，则返回一个 小于 0 的值；
    //          如果 item1 == item2，则返回 0；
    coreUtil.compare = function (item1, item2, compare) {
        var isFunc = coreUtil.isFunction(compare);
        if (!isFunc) { compare = coreUtil.numericCompare; }
        return compare.call(this, item1, item2);
    };



    //  过滤 JSON 对象指定名称列表的属性，并返回该 JSON 对象的一个新副本；该函数定义如下参数：
    //      obj: 待操作的 JSON 对象；
    //      propertieNames:需要从 obj 中排除或提取的属性名称，为一个数组对象，数组中的每一项都是一个 String 类型值；
    //      excluding: Boolean 类型值；默认为 false；
    //          如果为 true 则表示从 obj 中提取列表所示的属性值；
    //          如果为 false 则表示从 obj 中排除列表所示的属性值；
    //  返回值：返回一个 JSON 对象，该对象为传入的 obj 提取或排除指定列表所示属性值后的一个新 JSON-Object 副本。
    coreUtil.filterProperties = function (obj, propertieNames, excluding) {
        propertieNames = coreUtil.likeArrayNotString(propertieNames) ? propertieNames : [];
        var ret = {};
        for (var k in obj) {
            if (excluding ? coreArray.contains(propertieNames, k) : !coreArray.contains(propertieNames, k)) { ret[k] = obj[k]; }
        }
        return ret;
    };

    //  排除 JSON 对象指定名称列表的属性，并返回该 JSON 对象的一个新副本；该函数定义如下参数：
    //      obj: 待操作的 JSON 对象；
    //      propertieNames:需要从 obj 中排除的属性名称，为一个数组对象，数组中的每一项都是一个 String 类型值；
    //  返回值：返回一个 JSON 对象，该对象包含 obj 中除 propertieNames 列表外的所有属性。
    coreUtil.excludeProperties = function (obj, propertieNames) {
        return coreUtil.filterProperties(obj, propertieNames, false);
    };

    //  提取 JSON 对象指定名称列表的属性，并返回该 JSON 对象的一个新副本；该函数定义如下参数：
    //      obj: 待操作的 JSON 对象；
    //      propertieNames:需要从 obj 中提取的属性名称，为一个数组对象，数组中的每一项都是一个 String 类型值；
    //  返回值：返回一个 JSON 对象，该对象包含 obj 中 propertieNames 列表制定的所有属性。
    coreUtil.extractProperties = function (obj, propertieNames) {
        return coreUtil.filterProperties(obj, propertieNames, true);
    };



    //  扩展 $.string、$.date、$.number、$.array、$.boolean，使这些对象分别具有 String、Date、Number、Array、Boolean 对象的成员属性作为其静态属性；
    $.each([
        {
            unit: String.prototype, prototype: coreString, methods: [
                "anchor", "big", "blink", "bold", "charAt", "charCodeAt", "concat", "fixed", "fontcolor", "fontsize", "indexOf", "italics",
                "lastIndexOf", "link", "localeCompare", "match", "replace", "search", "slice", "small", "split", "strike", "sub", "substr",
                "substring", "sup", "toLocaleLowerCase", "toLocaleString", "toLocaleUpperCase", "toLowerCase", "toUpperCase", "toString", "trim"
            ]
        },
        {
            unit: Date.prototype, prototype: coreDate, methods: [
                "getDate", "getDay", "getFullYear", "getHours", "getMilliseconds", "getMinutes", "getMonth", "getSeconds", "getTime",
                "getTimezoneOffset", "getUTCDate", "getUTCDay", "getUTCFullYear", "getUTCHours", "getUTCMilliseconds", "getUTCMinutes",
                "getUTCMonth", "getUTCSeconds", "getVarDate", "getYear", "setDate", "setFullYear", "setHours", "setMilliseconds", "setMinutes",
                "setMonth", "setSeconds", "setTime", "setUTCDate", "setUTCFullYear", "setUTCHours", "setUTCMilliseconds", "setUTCMinutes",
                "setUTCMonth", "setUTCSeconds", "setYear", "toDateString", "toGMTString", "toISOString", "toJSON", "toLocaleDateString",
                "toLocaleString", "toLocaleTimeString", "toString", "toTimeString", "toUTCString"
            ]
        },
        { unit: Number.prototype, prototype: coreNumber, methods: ["toExponential", "toFixed", "toLocaleString", "toPrecision", "toString"] },
        {
            unit: Array.prototype, prototype: coreArray, methods: [
                "concat", "every", "filter", "forEach", "indexOf", "join", "lastIndexOf", "map", "pop", "push", "reduce", "reduceRight",
                "reverse", "shift", "slice", "some", "sort", "splice", "toLocaleString", "toString", "unshift"
            ]
        },
        { unit: Boolean.prototype, prototype: coreBoolean, methods: ["toString"] }
    ], function (i, n) {
        $.each(n.methods, function (l, name) {
            var method = n.unit[name], hasMethod = method && coreUtil.isFunction(method) ? true : false;
            if (hasMethod && (n.prototype[name] == null || n.prototype[name] == undefined)) {
                n.prototype[name] = function () {
                    var thisArg = arguments[0], args = $.array.removeAt(arguments, 0);
                    return method.apply(thisArg, args);
                };
            }
        });
    });





    /////////////////////////////////////////////////////////////////////////////////////////////// 
    //  javascript 字符(串)函数功能扩充
    /////////////////////////////////////////////////////////////////////////////////////////////// 

    //  判断传入的对象是否是一个字符串。
    coreString.isString = coreUtil.isString;

    //  判断传入的字符串是否为Null或者为空字符串。
    coreString.isNullOrEmpty = function (str) { return str === undefined || str === null || str === ""; };
    coreString.prototype.isNullOrEmpty = function () { return coreString.isNullOrEmpty(this); };

    //  判断传入的字符串是否为Null或者为空字符串或者全是空格。
    coreString.isNullOrWhiteSpace = function (str) { return coreString.isNullOrEmpty(str) || coreString.trim(String(str)) === ""; };
    coreString.prototype.isNullOrWhiteSpace = function () { return coreString.isNullOrWhiteSpace(this); };

    //  判断传入的字符串是否为 HTML 代码段。
    coreString.isHtmlText = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return str.length >= 3 && str.charAt(0) === "<" && str.charAt(str.length - 1) === ">";
    };
    coreString.prototype.isHtmlText = function () { return coreString.isHtmlText(this); };

    //  用新字符串替换与给定字符串匹配的所有子串；该方法将返回源字符串处理后的一个副本，而不会改变源字符串的值。
    coreString.replaceAll = function (str, substr, replacement, ignoreCase) {
        if (!substr || substr == replacement) { return str; }
        //var regexp = coreUtil.isRegExp(substr) ? substr : new RegExp(String(substr), ignoreCase ? "gm" : "igm");
        //return str.replace(regexp, replacement);
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var length = str.length, i = 0;
        while (str.indexOf(substr) > -1 && i++ < length) { str = str.replace(substr, replacement); }
        return str;
    };
    coreString.prototype.replaceAll = function (substr, replacement) { return coreString.replaceAll(this, substr, replacement); };

    //  格式化字符串，类似于 .NET 中的 string.format 函数功能
    //  使用方法：coreString.format('字符串{0}字符串{1}字符串','第一个变量','第二个变量','第三个变量', ...'第 N 个变量');
    //  该方法将返回源字符串处理后的一个副本，而不会改变源字符串的值。
    coreString.format = function (str, arg1, arg2, arg3, argn) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var isArray = coreUtil.likeArray(arg1),
            data = (isArray && !coreUtil.isString(arg1)) || coreUtil.isObject(arg1) ? arg1 : coreArray.range(arguments, 1);
        if (isArray) {
            for (var i = 0; i < data.length; i++) {
                value = data[i] ? data[i] : "";
                str = str.replace(new RegExp("\\{" + i + "}", "gm"), value);
            }
        } else {
            for (var key in data) {
                var value = proxy.call(data, key);
                value = (value == null || value == undefined) ? "" : value;
                str = str.replace(new RegExp("\\{" + key + "}", "gm"), value);
            }
        }
        function proxy(key) { try { return eval("this[\"" + key + "\"]"); } catch (ex) { return ""; } }
        return str;
    };
    coreString.prototype.format = function (arg1, arg2, arg3, argn) {
        arguments = coreArray.insert(arguments, 0, this);
        return coreString.format.apply(this, arguments);
    };

    //  获取字符串包含非 ASCII 码字符(例如中文、日文、俄文等)的 byte 字节长度。
    coreString.getByteLen = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var bytelen = 0, i = 0, length = str.length, cc = document.charset;
        if (!cc) { cc = "utf-8"; }
        cc = cc.toLowerCase();
        var s = cc == "iso-8859-1" ? 5 : 2;
        for (; i < length; i++) {
            bytelen += str.charCodeAt(i) > 255 ? s : 1;
        }
        return bytelen;
    };
    coreString.prototype.getByteLen = function () { return coreString.getByteLen(this); };

    //  判断当前字符串对象是否包含指定的字符串内容。
    coreString.contains = function (str, val) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return String(str).indexOf(val) > -1;
    };
    coreString.prototype.contains = function (val) { return coreString.contains(this, val); };

    //  字符串反转；该方法将返回源字符串处理后的一个副本，而不会改变源字符串的值。
    coreString.reverse = function (str) {
        var charArray = [];
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        for (var i = str.length - 1; i > -1; i--) { charArray.push(str[i]); }
        return charArray.join("");
    };
    coreString.prototype.reverse = function () { return coreString.reverse(this); };

    //  去除字符串左边的空格；该方法将返回源字符串处理后的一个副本，而不会改变源字符串的值。
    coreString.ltrim = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return str.replace(/(^\s*)/g, "");
    };
    coreString.prototype.ltrim = function () { return coreString.ltrim(this); };

    //  去除字符串右边的空格；该方法将返回源字符串处理后的一个副本，而不会改变源字符串的值。
    coreString.rtrim = function () {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return str.replace(/(\s*$)/g, "");
    };
    coreString.prototype.rtrim = function () { return coreString.rtrim(this); };

    //  去除字符串左右两边的空格；该方法将返回源字符串处理后的一个副本，而不会改变源字符串的值。
    coreString.trim = coreString.trim ? coreString.trim : coreUtil.trim;
    coreString.prototype.trim = function () { return coreString.trim(this); };

    //  返回一个新字符串，该字符串通过在此实例中的字符左侧填充空格或指定字符来来达到指定的总长度，从而使这些字符右对齐。
    coreString.padLeft = function (str, len, paddingChar) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        paddingChar = coreString.isNullOrEmpty(paddingChar) || !paddingChar.length ? " " : paddingChar;
        len = coreUtil.isNumeric(len) ? len : str.length;
        if (str.length < len) { for (; str.length < len; str = paddingChar + str) { } }
        return str;
    };
    coreString.prototype.padLeft = function (len, paddingChar) { return coreString.padLeft(this, len, paddingChar); };

    //  返回一个新字符串，该字符串通过在此字符串中的字符右侧填充空格或指定字符来达到指定的总长度，从而使这些字符左对齐
    coreString.padRight = function (str, len, paddingChar) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        paddingChar = coreString.isNullOrEmpty(paddingChar) || !paddingChar.length ? " " : paddingChar;
        len = coreUtil.isNumeric(len) ? len : str.length;
        if (str.length < len) { for (; str.length < len; str += paddingChar) { } }
        return str;
    };
    coreString.prototype.padRight = function (len, paddingChar) { return coreString.padRight(this, len, paddingChar); };

    //  返回字符串中的的字符，注意从 0 开始。
    coreString.mid = function (str, start, len) {
        if (!start) { start = 0; }
        if (!len) { len = 0; }
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return str.substr(start, len);
    };
    coreString.prototype.mid = function (start, len) { return coreString.mid(this, start, len); };

    //  计算字符串的打印长度。
    coreString.lengthOfPrint = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return str.replace(/[^\x00-\xff]/g, "**").length;
    };
    coreString.prototype.lengthOfPrint = function () { return coreString.lengthOfPrint(this); };

    //  判断当前 String 对象是否以指定的字符串开头。
    coreString.startsWith = function (str, val) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return str.substr(0, val.length) == val;
    };
    coreString.prototype.startsWith = function (val) { return coreString.startsWith(this, val); };

    //  判断当前 String 对象是否以指定的字符串结尾。
    coreString.endsWith = function (str, val) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return str.substr(str.length - val.length) == val;
    };
    coreString.prototype.endsWith = function (val) { return coreString.endsWith(this, val); };

    //  截取当前字符串左边的指定长度内容。
    coreString.left = function (str, len) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        if (!coreUtil.isNumeric(len)) { len = parseInt(len, 10); }
        if (len < 0 || len > str.length) { len = str.length; }
        return str.substr(0, len);
    };
    coreString.prototype.left = function (len) { return coreString.left(this, len); };

    //  截取当前字符串右边的指定长度内容。
    coreString.right = function (str, len) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        if (!coreUtil.isNumeric(len)) { len = parseInt(len, 10); }
        if (len < 0 || len > str.length) { len = str.length; }
        return str.substring(str.length - len, str.length);
    };
    coreString.prototype.right = function (len) { return coreString.right(this, len); };

    //  截取当前字符串左边的指定字节长度内容。
    coreString.leftBytes = function (str, len) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        if (!coreUtil.isNumeric(len)) { len = parseInt(len, 10); }
        var length = coreString.getByteLen(str), i = 0, bytelen = 0, cc = document.charset;
        if (!cc) { cc = "utf-8"; }
        cc = cc.toLowerCase();
        var s = cc == "iso-8859-1" ? 5 : 2;
        if (len < 0 || len > length) { len = length; }
        for (; i < str.length; i++) {
            bytelen += str.charCodeAt(i) > 255 ? s : 1;
            if (bytelen == len) { break; }
            if (bytelen > len) { i--; break; }
        }
        return coreString.left(str, i + 1);
    };
    coreString.prototype.leftBytes = function (len) { return coreString.leftBytes(this, len); };

    //  截取当前字符串右边的指定字节长度内容。
    coreString.rightBytes = function (str, len) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        if (!coreUtil.isNumeric(len)) { len = parseInt(len, 10); }
        var length = coreString.getByteLen(str), i = 0, bytelen = 0, cc = document.charset;
        if (!cc) { cc = "utf-8"; }
        cc = cc.toLowerCase();
        var s = cc == "iso-8859-1" ? 5 : 2;
        if (len < 0 || len > length) { len = length; }
        for (; i < str.length; i++) {
            bytelen += str.charCodeAt(str.length - 1 - i) > 255 ? s : 1;
            if (bytelen == len) { break; }
            if (bytelen > len) { i--; break; }
        }
        return coreString.right(str, i + 1);
    };
    coreString.prototype.rightBytes = function (len) { return coreString.rightBytes(this, len); };

    //  判断当前 String 对象是否是正确的长日期格式。
    coreString.isLongDate = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var r = str.replace(/(^\s*)|(\s*$)/g, "").match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/);
        if (r == null) { return false; }
        var d = new Date(r[1], r[3] - 1, r[4], r[5], r[6], r[7]);
        return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4] && d.getHours() == r[5] && d.getMinutes() == r[6] && d.getSeconds() == r[7]);
    };
    coreString.prototype.isLongDate = function () { return coreString.isLongDate(this); };

    //  判断当前 String 对象是否是正确的段日期格式。
    coreString.isShortDate = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var r = str.replace(/(^\s*)|(\s*$)/g, "").match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
        if (r == null) { return false; }
        var d = new Date(r[1], r[3] - 1, r[4]);
        return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4]);
    };
    coreString.prototype.isShortDate = function () { return coreString.isShortDate(this); };

    //  判断当前 String 对象是否是正确的日期格式。
    coreString.isDate = function (str) {
        return coreString.isLongDate(str) || coreString.isShortDate(str);
    };
    coreString.prototype.isDate = function () { return coreString.isDate(this); };

    //  判断当前 String 对象是否是正确的电话号码格式(中国)。
    coreString.isTel = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(str);
    };
    coreString.prototype.isTel = function () { return coreString.isTel(this); };

    //  判断当前 String 对象是否是正确的手机号码格式(中国)。
    coreString.isMobile = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^(13|14|15|17|18)\d{9}$/i.test(str);
    };
    coreString.prototype.isMobile = function () { return coreString.isMobile(this); };

    //  判断当前 String 对象是否是正确的电话号码或者手机号码格式(中国)
    coreString.isTelOrMobile = function (str) {
        return coreString.isTel(str) || coreString.isMobile(str);
    };
    coreString.prototype.isTelOrMobile = function () { return coreString.isTelOrMobile(this); };

    //  判断当前 String 对象是否是正确的传真号码格式
    coreString.isFax = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(str);
    };
    coreString.prototype.isFax = function () { return coreString.isFax(this); };

    //  判断当前 String 对象是否是正确的 电子邮箱地址(Email) 格式。
    coreString.isEmail = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(str);
    };
    coreString.prototype.isEmail = function () { return coreString.isEmail(this); };

    //  判断当前 String 对象是否是正确的 邮政编码(中国) 格式。
    coreString.isZipCode = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^[\d]{6}$/.test(str);
    };
    coreString.prototype.isZipCode = function () { return coreString.isZipCode(this); };

    //  判断当前 String 对象是否是否存在汉字字符。
    coreString.existChinese = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        //[\u4E00-\u9FA5]為漢字﹐[\uFE30-\uFFA0]為全角符號
        return !/^[\x00-\xff]*$/.test(str);
    };
    coreString.prototype.existChinese = function () { return coreString.existChinese(this); };

    //  验证中文
    coreString.isChinese = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^[\u0391-\uFFE5]+$/i.test(str);
    };
    coreString.prototype.isChinese = function () { return coreString.isChinese(this); };

    //  验证英文
    coreString.isEnglish = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^[A-Za-z]+$/i.test(str);
    };
    coreString.prototype.isEnglish = function () { return coreString.isEnglish(this); };

    //  判断当前 String 对象是否是正确的 文件名称(路径) 格式。
    coreString.isFileName = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        //return !/[\\\/\*\?\|:"<>]/g.test(str);
        return !/[\\\/\*\?\|:<>]/g.test(str);
    };
    coreString.prototype.isFileName = function () { return coreString.isFileName(this); };

    //  判断当前 String 对象是否是正确的 IPv4 地址格式。
    coreString.isIPv4 = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var reSpaceCheck = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;
        if (reSpaceCheck.test(str)) {
            str.match(reSpaceCheck);
            if (RegExp.$1 <= 255 && RegExp.$1 >= 0
                    && RegExp.$2 <= 255 && RegExp.$2 >= 0
                    && RegExp.$3 <= 255 && RegExp.$3 >= 0
                    && RegExp.$4 <= 255 && RegExp.$4 >= 0) {
                return true;
            } else { return false; }
        } else { return false; }
    };
    coreString.prototype.isIPv4 = function () { return coreString.isIPv4(this); };

    //  判断当前 String 对象是否是正确的 url 格式。
    coreString.isUrl = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        //        var strRegex = "^((https|http|ftp|rtsp|mms)?:               //)"
        //    + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?"    //ftp的user@
        //          + "(([0-9]{1,3}\.){3}[0-9]{1,3}"                          // IP形式的URL- 199.194.52.184
        //          + "|"                                                     // 允许IP和DOMAIN（域名）
        //          + "([0-9a-z_!~*'()-]+\.)*"                                // 域名- www.
        //          + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\."                  // 二级域名
        //          + "[a-z]{2,6})"                                           // first level domain- .com or .museum
        //          + "|"                                                     // 允许为本机
        //          + "localhost|127.0.0.1"                                   // 允许为本机地址
        //          + "(:[0-9]{1,4})?"                                        // 端口- :80
        //          + "((/?)|"                                                // a slash isn't required if there is no file name
        //          + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        var strRegex = /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i;
        var re = new RegExp(strRegex);
        return re.test(str);
    };
    coreString.prototype.isUrl = function () { return coreString.isUrl(this); };

    //  判断是否为合法的 ipv4 或者 url 格式
    coreString.isUrlOrIPv4 = function (str) {
        return coreString.isUrl(str) || coreString.isIP(str);
    };
    coreString.prototype.isUrlOrIPv4 = function () { return coreString.isUrlOrIPv4(this); };

    //  判断是否为合法的货币格式
    coreString.isCurrency = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^\d{0,}(\.\d+)?$/i.test(str);
    };
    coreString.prototype.isCurrency = function () { return coreString.isCurrency(this); };

    //  判断是否为合法的 QQ 号码格式
    coreString.isQQ = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^[1-9]\d{4,11}$/i.test(str);
    };
    coreString.prototype.isQQ = function () { return coreString.isQQ(this); };

    //  判断是否为合法的 MSN 帐号格式
    coreString.isMSN = function (str) {
        return coreString.isEmail(str);
    };
    coreString.prototype.isMSN = function () { return coreString.isMSN(this); };

    //  验证是否包含空格和非法字符Z
    coreString.isUnNormal = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /.+/i.test(str);
    };
    coreString.prototype.isUnNormal = function () { return coreString.isUnNormal(this); };

    //  验证是否为合法的车牌号码
    coreString.isCarNo = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(str);
    };
    coreString.prototype.isCarNo = function () { return coreString.isCarNo(this); };

    //  验证是否为合法的汽车发动机序列号
    coreString.isCarEngineNo = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^[a-zA-Z0-9]{16}$/.test(str);
    };
    coreString.prototype.isCarEngineNo = function () { return coreString.isCarEngineNo(this); };

    //  验证是否可以作为合法的用户名字符(字母开头，允许6-16字节，允许字母数字下划线)
    coreString.isUserName = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(str);
    };
    coreString.prototype.isUserName = function () { return coreString.isUserName(this); };

    //  判断当前 String 对象是否是正确的 身份证号码(中国) 格式。
    coreString.isIDCard = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var iSum = 0,
            sId = str,
            aCity = {
                11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古",
                21: "辽宁", 22: "吉林", 23: "黑龙江", 31: "上海", 32: "江苏",
                33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东",
                41: "河南", 42: "湖北", 43: "湖南", 44: "广东", 45: "广西",
                46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南",
                54: "西藏", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏",
                65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外"
            };
        if (!/^\d{17}(\d|x)$/i.test(sId)) {
            return false;
        }
        sId = sId.replace(/x$/i, "a");
        //非法地区
        if (aCity[parseInt(sId.substr(0, 2), 10)] == null) {
            return false;
        }
        var sBirthday = sId.substr(6, 4) + "-" + Number(sId.substr(10, 2)) + "-" + Number(sId.substr(12, 2)),
            d = new Date(sBirthday.replace(/-/g, "/"));
        //非法生日
        if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())) {
            return false;
        }
        for (var i = 17; i >= 0; i--) {
            iSum += (Math.pow(2, i) % 11) * parseInt(sId.charAt(17 - i), 11);
        }
        if (iSum % 11 != 1) {
            return false;
        }
        return true;
    };
    coreString.prototype.isIDCard = function () { return coreString.isIDCard(this); };

    //  验证是否为整数格式
    coreString.isInteger = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^[+]?[1-9]+\d*$/i.test(str);
    };
    coreString.prototype.isInteger = function () { return coreString.isInteger(this); };

    //  判断当前 String 对象是否是正确的 数字 格式。
    coreString.isNumeric = function (str, flag) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        //验证是否是数字
        if (isNaN(str)) { return false; }
        if (arguments.length == 0) { return false; }
        switch (flag) {
            case "":
                return true;
            case "+":        //正数
                return /(^\+?|^\d?)\d*\.?\d+$/.test(str);
            case "-":        //负数
                return /^-\d*\.?\d+$/.test(str);
            case "i":        //整数
                return /(^-?|^\+?|\d)\d+$/.test(str);
            case "+i":        //正整数
                return /(^\d+$)|(^\+?\d+$)/.test(str);
            case "-i":        //负整数
                return /^[-]\d+$/.test(str);
            case "f":        //浮点数
                return /(^-?|^\+?|^\d?)\d*\.\d+$/.test(str);
            case "+f":        //正浮点数
                return /(^\+?|^\d?)\d*\.\d+$/.test(str);
            case "-f":        //负浮点数
                return /^[-]\d*\.\d$/.test(str);
            default:        //缺省
                return true;
        }
    };
    coreString.prototype.isNumeric = function (flag) { return coreString.isNumeric(this, flag); };

    //  判断当前 String 对象是否是正确的 颜色(#FFFFFF形式) 格式。
    coreString.isColor = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        if (str == "") { return true; };
        if (str.length != 7) { return false; };
        return (str.search(/\#[a-fA-F0-9]{6}/) != -1);
    };
    coreString.prototype.isColor = function () { return coreString.isColor(this); };

    //  判断当前 String 对象是否可以作为安全密码字符(由字符和数字组成，至少 6 位).
    coreString.isSafePassword = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(str));
    };
    coreString.prototype.isSafePassword = function () { return coreString.isSafePassword(this); };

    //  转换成全角
    coreString.toCase = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var tmp = "";
        for (var i = 0; i < str.length; i++) {
            if (str.charCodeAt(i) > 0 && str.charCodeAt(i) < 255) { tmp += String.fromCharCode(str.charCodeAt(i) + 65248); }
            else { tmp += String.fromCharCode(str.charCodeAt(i)); }
        }
        return tmp;
    };
    coreString.prototype.toCase = function () { return coreString.toCase(this); };

    //  对字符串进行Html编码。
    coreString.toHtmlEncode = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var temp = str;
        temp = temp.replace(/&/g, "&amp;");
        temp = temp.replace(/</g, "&lt;");
        temp = temp.replace(/>/g, "&gt;");
        temp = temp.replace(/\'/g, "&apos;");
        temp = temp.replace(/\"/g, "&quot;");
        temp = temp.replace(/\n/g, "<br />");
        temp = temp.replace(/\ /g, "&nbsp;");
        temp = temp.replace(/\t/g, "&nbsp;&nbsp;&nbsp;&nbsp;");
        return temp;
    };
    coreString.prototype.toHtmlEncode = function () { return coreString.toHtmlEncode(this); };

    //  转换成日期。
    coreString.toDate = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        try { return new Date(str.replace(/-/g, "\/")); }
        catch (e) { return null; }
    };
    coreString.prototype.toDate = function () { return coreString.toDate(this); };

    //  将字符串对象转换成 布尔(boolean) 值
    coreString.toBoolean = function (str) {
        if (typeof str == "boolean") { return str; }
        str = coreString.isNullOrEmpty(str) ? "" : String(str).toLowerCase();
        str = coreString.trim(str);
        return str == "true" || str == "yes" || str == "y" || str == "t" || str == "1";
    };
    coreString.prototype.toBoolean = function () { return coreString.toBoolean(this); };

    //  将字符串对象转换成 整数(int) 值
    coreString.toInteger = function (str) { return parseInt(str); };
    coreString.prototype.toInteger = function () { return coreString.toInteger(this); };

    //  将字符串对象转换成 数值(Number)。
    coreString.toNumber = function (str) { return coreString.toFloat(str); };
    coreString.prototype.toNumber = function () { return coreString.toNumber(this); };

    //  将字符串对象转换成 浮点数(float) 值
    coreString.toFloat = function (str) { return parseFloat(str); };
    coreString.prototype.toFloat = function () { return coreString.toFloat(this); };

    //  将字符串对象转换成 数值
    coreString.toNumeric = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        str = coreString.trim(str);
        return str.indexOf(".") > -1 ? coreString.toFloat(str) : coreString.toInteger(str);
    };
    coreString.prototype.toNumeric = function () { return coreString.toNumeric(this); };

    //  将字符串对象转换成 对象(Object) 值
    coreString.toObject = function (data) {
        return eval("(" + $.trim(data + "") + ")");
    };
    coreString.prototype.toObject = function () { return coreString.toObject(this); };

    coreString.toJSONString = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        str = coreString.trim(str);
        return str.charAt(0) === "<" && str.charAt(str.length - 1) === ">" && str.length >= 3 ? $(str).text() : str;
    };
    coreString.prototype.toJSONString = function () { return coreString.toJSONString(this); };

    //  将字符串对象转换成 函数(function) 值
    coreString.toFunction = function (str) {
        if (coreUtil.isFunction(str)) { return str; }
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        str = coreString.trim(str);
        if (!str.startsWith("function")) { str = "function(){" + str + "}"; }
        str = "{ \"func\": " + str + " }";
        return coreString.toObject(str).func;
    };
    coreString.prototype.toFunction = function () { return coreString.toFunction(this); };




    /////////////////////////////////////////////////////////////////////////////////////////////// 
    //  javascript 的数值(Number)函数功能扩充。
    /////////////////////////////////////////////////////////////////////////////////////////////// 

    //  判断对象是否是一个数值或数值格式字符串
    coreNumber.isNumeric = coreNumber.likeNumber = coreNumber.likeNumeric = coreUtil.isNumeric;

    //  判断对象是否是一个数值
    coreNumber.isNumber = coreUtil.isNumber;

    //  把一个数字/浮点数舍入为指定精度的数值；该函数定义如下参数：
    //      num:    需要进行舍入计算的数值;
    //      precision:  舍入操作保留的精度(意即保留多少为小数)，默认为 0;
    coreNumber.round = function (num, precision) {
        if (!coreUtil.isNumeric(num)) { throw "传入的参数 num 必须是一个数值"; }
        precision = coreUtil.isNumeric(precision) ? precision : 0;
        var str = new Number(num).toFixed(precision);
        return precision ? parseFloat(str) : parseInt(str);
    };
    coreNumber.prototype.round = function (precision) { return coreNumber.round(this, precision); };

    //  获取或设置数值对象的精度；该函数定义如下重载：
    //      重载一：function(num)，该重载用于获取数值的精度，该重载定义如下参数：
    //              num:    需要获取精度的数值。
    //          返回值：返回数值 num 的精度(小数位数)。
    //      重载二：function(num, precision)，该重载用于设置数值的精度(即进行数值舍入操作)，该重载定义如下参数：
    //              num:    需要设置精度的数值。
    //              precision: 需要设置的精度。
    //          返回值：返回数值 num 按照指定的精度进行舍入操作后的值；
    //          备注：该重载会调用函数 coreNumber.round 进行数值舍入操作。
    coreNumber.precision = function (num, precision) {
        if (!coreUtil.isNumeric(num)) { throw "传入的参数 num 必须是一个数值"; }
        if (coreUtil.isNumeric(precision)) { return coreNumber.round(num, precision); } else {
            var str = String(num), i = str.indexOf(".");
            return i == -1 ? 0 : str.length - str.indexOf(".") - 1;
        }
    };

    //  判断传入的数值是否是一个奇数；该函数定义如下参数：
    //      num:    需要判断的数值；
    //  返回值：如果传入的参数 num 是一个奇数，则返回 true，否则返回 false。
    coreNumber.isOdd = function (num) {
        return (num % 2) == 1;
    };
    coreNumber.prototype.isOdd = function () { return coreNumber.isOdd(this); };

    //  判断传入的数值是否是一个偶数；该函数定义如下参数：
    //      num:    需要判断的数值；
    //  返回值：如果传入的参数 num 是一个偶数，则返回 true，否则返回 false。
    coreNumber.isEven = function (num) {
        return (num % 2) == 0;
    };
    coreNumber.prototype.isEven = function () { return coreNumber.isEven(this); };


    //  将传入的数值转换成一个描述文件大小的字符串；该函数定义如下参数：
    //      num:    待转换格式的数值，表示文件大小字节数(B)；
    //      str:  待转换的格式，String 类型值，可选的值包括 "auto"、"b"、"kb"、"mb"、"gb"
    coreNumber.toFileSize = function (num, str) {
        num = coreUtil.isNumeric(num) ? window.parseFloat(num) : 0;
        str = coreString.trim(String(str)).toLowerCase();
        if (str == "b") { return String(num); }
        if (!coreArray.contains(["b", "kb", "mb", "gb", "auto"], str)) { str = "auto"; }
        var ret = null, kb = 1024, mb = 1048576, gb = 1073741824;
        if (str == "auto") {
            str = (num >= gb) ? "gb" : (num >= mb ? "mb" : (num >= kb ? "kb" : "b"));
        }
        switch (str) {
            case "b": ret = toB(num); break;
            case "kb": ret = toKB(num); break;
            case "mb": ret = toMB(num); break;
            case "gb": ret = toGB(num); break;
            default: ret = toMB(num); break;
        }
        function toB(size) { return String(coreNumber.round(size, 2)) + "B"; };
        function toKB(size) { return String(coreNumber.round(size / kb, 2)) + "KB"; };
        function toMB(size) { return String(coreNumber.round(size / mb, 2)) + "MB"; };
        function toGB(size) { return String(coreNumber.round(size / gb, 2)) + "GB"; };
        return ret;
    };




    /////////////////////////////////////////////////////////////////////////////////////////////// 
    //  javascript 的数组函数功能扩充。
    /////////////////////////////////////////////////////////////////////////////////////////////// 

    //  判断对象是否是一个数组
    coreArray.isArray = core_isArray ? core_isArray : coreUtil.isArray;
    coreUtil.isArray = coreArray.isArray;

    //  检测一个对象是否为一个数组对象或者类似于数组对象，同 coreUtil.likeArray
    coreArray.likeArray = coreUtil.likeArray;

    //  同 coreUtil.likeArrayNotString
    coreArray.likeArrayNotString = coreUtil.likeArrayNotString;

    //  判断传入的 数组 是否为 Null 或者为空数组。
    coreArray.isNullOrEmpty = function (array) { return !coreArray.likeArray(array) || !array.length; };
    coreArray.prototype.isNullOrEmpty = function () { return coreArray.isNullOrEmpty(this); };


    //  将另一数组中的所有项复制到当前指定数组中，该函数定义如下参数：
    //      target: 目标数组，源数组 source 中的所有项将被赋值到该数组中；
    //      source: 源数据数组，该数组内的所有项将被赋值到目标数组 target 中；
    //  注意：该方法会改变目标数组 target 中的元素数量。
    //  返回值：源数组数据复制过来后的目标数组 target。
    coreArray.copy = coreArray.copyFrom = function (target, source) {
        target = coreArray.likeArray(target) ? target : [];
        source = coreArray.likeArray(source) ? source : [];
        var l = source.length, i = 0;
        if (coreUtil.isNumeric(l)) {
            while (i < l) { core_push.call(target, source[i++]); };
        } else {
            while (source[i] !== undefined) { core_push.call(target, source[i++]); }
        }
        return target;
    };
    coreArray.prototype.copy = coreArray.prototype.copyFrom = function (source) { return coreArray.copy(this, source); };

    //  将当前指定数组中的所有项复制到另一数组中；该函数定义如下参数：
    //      source: 源数据数组，该数组内的所有项将被赋值到目标数组 target 中；
    //      target: 目标数组，源数组 source 中的所有项将被赋值到该数组中；
    //  注意：该方法会改变目标数组 target 中的元素数量。
    //  返回值：源数组数据复制过来后的目标数组 target。
    coreArray.copyTo = function (source, target) {
        return coreArray.copy(target, source);
    };
    coreArray.prototype.copyTo = function (target) { return coreArray.copy(target, this); };

    //  创建一个和当前数组对象相同的数组并返回
    coreArray.clone = function (source) { return coreArray.copy([], source); };
    coreArray.prototype.clone = function () { return coreArray.clone(this); };

    //  确认数组中是否包含指定的元素。该函数定义如下参数：
    //      array: 被检测的数组；
    //      item: 被检测的元素，判断该元素是否存在于数组 array 中；
    //      compare: 用于比较运算的函数，该函数被循环调用，用于比较 array 中的每一项是否与 item 等值；该函数返回一个 bool 值；这是一个可选参数。
    //          该函数的签名应该是 function (item1, item2) { }，参数 item1 表示源数组中的项、item2 表示要进行比较的项；
    //          如果不定义参数 compare，则使用默认的比较运算符 "==" 进行值的匹配；
    coreArray.contains = function (array, item, compare) {
        return coreArray.some(array, function (val) { return coreUtil.equals(val, item, compare); });
    };
    coreArray.prototype.contains = function (item, compare) { return coreArray.contains(this, item, compare); };

    //  颠倒数组中元素的顺序。
    //  返回值：返回传入的参数 array 本身；如果传入的参数 array 不是一个数组，则返回一个新创建的空数组对象。
    //  注意：该方法会改变原来的数组，而不会创建新的数组。
    coreArray.reverse = function (array) {
        array = coreArray.likeArray(array) ? array : [];
        if (coreArray.isArray(array)) { array.reverse(); return array; }
        var len = array.length, l = len / 2, j;
        for (var i = 0; i < l; i++) {
            j = len - i - 1;
            var a = array[i];
            var b = array[j];
            array[i] = b;
            array[j] = a;
        }
        return array;
    };

    //  在数组中搜索指定的项，并返回整个数组中第一个匹配项的从零开始的索引，该函数定义如下参数：
    //      array: 源数据数组；
    //      item:  要搜索的项；
    //      startIndex: 从零开始的搜索的起始索引，空列表中 0（零）为有效值；该参数可选；如果该参数未定义则从 0 开始；
    //      count: 要搜索的部分中的元素数；该参数可选，如果该参数未定义则搜索至数组的末尾；
    //      compare: 用于比较运算的函数，该函数被循环调用，用于比较 array 中的每一项是否与 item 等值；该函数返回一个 bool 值；这是一个可选参数。
    //          该函数的签名应该是 function (item1, item2) { }，参数 item1 表示源数组中的项、item2 表示要进行比较的项；
    //          如果不定义参数 compare，则使用默认的比较运算符 "==" 进行值的匹配；
    //  返回值：如果在数组中从 startIndex 开始并包含 count 个元素的元素范围内找到 item 的第一个匹配项，则为该项的从零开始的索引；否则为 -1。
    //  参考：http://msdn.microsoft.com/ZH-CN/library/ie/ff679977(v=vs.94).aspx
    coreArray.indexOf =
        //coreArray.indexOf ? coreArray.indexOf :
        function (array, item, startIndex, count, compare) {
            array = coreArray.likeArray(array) ? array : [];
            var result = -1;
            if (!array.length) { return result; }
            if (arguments.length > 2) {
                var c = arguments[arguments.length - 1];
                compare = coreUtil.isFunction(c) ? c : null;
                var s = arguments[2];
                startIndex = coreUtil.isNumeric(s) ? s : 0;
                if (startIndex < 0 || array.length < startIndex) { return result; }
                if (arguments.length > 3) {
                    c = arguments[3];
                    count = coreUtil.isNumeric(c) ? c : array.length - startIndex;
                } else {
                    count = array.length - startIndex;
                }
                if (count < 0 || startIndex + count > array.length) { return result; }
            } else {
                startIndex = 0;
                count = array.length - startIndex;
                compare = null;
            }
            var stopIndex = startIndex + count;
            for (var i = startIndex; i < stopIndex; i++) {
                if (coreUtil.equals(array[i], item, compare)) { result = i; break; }
            }
            return result;
        };
    coreArray.prototype.indexOf = function (item, startIndex, count, compare) { return coreArray.indexOf(this, item, startIndex, count, compare); };

    //  在数组中搜索指定的项，并返回整个数组中最后一个匹配项的从零开始的索引。
    //      array: 源数据数组；
    //      item:  要搜索的项；
    //      startIndex: 向后搜索的从零开始的起始索引；该参数可选；如果该参数未定义则从数组末尾开始；
    //      count: 要搜索的部分中的元素数；该参数可选，如果该参数未定义则搜索至数组的起始位置；
    //      compare: 用于比较运算的函数，该函数被循环调用，用于比较 array 中的每一项是否与 item 等值；该函数返回一个 bool 值；这是一个可选参数。
    //          该函数的签名应该是 function (item1, item2) { }，参数 item1 表示源数组中的项、item2 表示要进行比较的项；
    //          如果不定义参数 compare，则使用默认的比较运算符 "==" 进行值的匹配；
    //  返回值：如果在数组中包含 count 个元素、在 startIndex 处结尾的元素范围内找到 item 的最后一个匹配项，则为该项的从零开始的索引；否则为 -1。
    //  参考：http://msdn.microsoft.com/ZH-CN/library/ie/ff679972(v=vs.94).aspx
    coreArray.lastIndexOf =
        //coreArray.lastIndexOf ? coreArray.lastIndexOf :
        function (array, item, startIndex, count, compare) {
            array = coreArray.likeArray(array) ? array : [];
            var result = -1;
            if (!array.length) { return result; }
            if (arguments.length > 2) {
                var c = arguments[arguments.length - 1];
                compare = coreUtil.isFunction(c) ? c : null;
                var s = arguments[2];
                startIndex = coreUtil.isNumeric(s) ? s : 0;
                if (startIndex < 0 || array.length < startIndex) {
                    return result;
                }
                if (arguments.length > 3) {
                    c = arguments[3];
                    count = coreUtil.isNumeric(c) ? c : array.length - startIndex;
                } else {
                    count = array.length - startIndex;
                }
                if (count < 0 || startIndex + count > array.length) {
                    return result;
                }
            } else {
                startIndex = 0;
                count = array.length - startIndex;
                compare = null;
            }
            var begin = array.length - startIndex - 1,
                end = begin - count;
            for (var i = begin; i > end; i--) {
                if (coreUtil.equals(array[i], item, compare)) {
                    result = i; break;
                }
            }
            return result;
        };
    coreArray.prototype.lastIndexOf = function (item, startIndex, count, compare) { return coreArray.lastIndexOf(this, item, startIndex, count, compare); };

    //  提取指定数组中介于两个指定索引号之间的元素构成的一个新的数组；该函数定义如下参数：
    //      array: 源数据数组；
    //      startIndex: 必需。一个大于或等于 0 的整数，规定从何处开始选取，从 0 开始计数。
    //      stopIndex: 可选。规定从何处结束选取。该参数是数组片断结束处的数组下标。如果没有指定该参数，那么切分的数组包含从 startIndex 到数组结束的所有元素。
    //  返回值：返回一个新的数组，包含从 startIndex 到 stopIndex （不包括该元素）的 arrayObject 中的元素。
    coreArray.range = function (array, startIndex, stopIndex) {
        array = coreArray.likeArray(array) ? array : [];
        startIndex = coreUtil.isNumeric(startIndex) ? startIndex : 0;
        stopIndex = coreUtil.isNumeric(stopIndex) ? stopIndex : array.length;
        return core_slice.call(array, startIndex, stopIndex);
    };
    coreArray.prototype.range = function (startIndex, stopIndex) { return coreArray.range(this, startIndex, stopIndex); };

    //  提取指定数组中从 startIndex 位置开始后指定数量的元素构成的一个新的数组；该函数定义如下参数：
    //      array: 源数据数组；
    //      startIndex: 一个非负的整数，规定要提取的起始位置的索引号；
    //      length: 一个非负的整数，规定要提取的元素的数量；该参数可选，如果不定义该参数，则一直提取到数组的末尾；
    //  返回值：返回一个新的数组，包含从 startIndex 处开始后长度为 length 的所有元素。
    coreArray.rangeLen = function (array, startIndex, length) {
        startIndex = coreUtil.isNumeric(startIndex) ? startIndex : 0;
        length = coreUtil.isNumeric(length) ? length : array.length;
        var stopIndex = startIndex + length;
        return coreArray.range(array, startIndex, stopIndex);
    };
    coreArray.prototype.rangeLen = function (startIndex, length) { return coreArray.rangeLen(this, startIndex, length); };

    //  对指定的数组进行分页处理，并返回分页后的结果；该函数定义如下参数：
    //      array: 源数据数组；
    //      pageIndex: 一个非负整数，表示要返回的数据所在页面的索引号，从 0 开始计算；该参数可选，如果未定义该参数或不合法，则默认为 0；
    //      pageSize: 一个非负整数，表示每一个分页页面的尺寸，即每页有多少行记录；该参数可选，如果未定义该参数或不合法，则默认为 10；
    //          sortby: 用于排序的比较函数，该函数被循环调用，用于比较 array 中没两项的大小；这是一个可选参数；
    //              该函数的签名为 function (a, b) { }，参数 a、b 分别表示源数组中的待比较大小的项；该函数必须返回一个数值表示比较后的结果；
    //              如果 a > b ，则返回一个 大于 0 的值；
    //              如果 a < b ，则返回一个 小于 0 的值；
    //              如果 a == b，则返回 0；
    //          如果不定义该参数，则默认将 array 中的每一项当作数字来进行比较。
    //  该函数返回一个具有如下属性的 JSON 对象：
    //      pageSize:   一个非负整数，表示每一个分页页面的尺寸，即每页有多少行记录；
    //      pageIndex:  一个非负整数，表示返回的数据所在页面的索引号，从 0 开始计算；
    //      rowCount:   一个非负整数，表示返回的数据的未分页前的总行数；
    //      data:       一个数组，为传入的参数 array 的子集，表示分页后的页面数据；
    //      pageCount:  一个非负整数，表示源数据数组分页后的总页面数量；
    //      pageNumber: 一个非负整数，表示返回的数据所在的页面的序号，从 1 开始计算；同 pageIndex + 1；
    //      total:      一个非负整数，同 rowCount。
    coreArray.splitPage = function (array, pageIndex, pageSize, sortby) {
        array = coreArray.likeArray(array) ? array : [];
        if (!pageIndex || !coreUtil.isNumeric(pageIndex) || pageIndex < 0) { pageIndex = 0; }
        if (!pageSize || !coreUtil.isNumeric(pageSize) || pageSize < 1) { pageSize = 10; }
        var isFunc = coreUtil.isFunction(sortby);
        array = isFunc ? coreArray.clone(array).sort(sortby) : array;
        var startIndex = pageIndex * pageSize;
        var stopIndex = (pageIndex + 1) * pageSize;
        var data = coreArray.range(array, startIndex, stopIndex);
        var rowCount = array.length;
        var pageCount = Math.ceil(parseFloat(rowCount) / pageSize);
        var pageNumber = pageIndex + 1;
        var total = rowCount;
        return { pageSize: pageSize, pageIndex: pageIndex, rowCount: rowCount, data: data, pageCount: pageCount, pageNumber: pageNumber, total: total };
    };
    coreArray.prototype.splitPage = function (pageIndex, pageSize, sortby) { return coreArray.splitPage(this, pageIndex, pageSize, sortby); };

    //  从数组中移除一定范围的元素，该函数定义如下参数：
    //      array: 源数据数组；
    //      index: 要移除的元素的范围从零开始的起始索引；该参数可选，如果不定义该参数则默认为 0；
    //      count: 要移除的元素数；该参数可选，如果不定义该参数则默认为从 index 起始位置一直到数组的末尾，可以为 0。
    //  注意：该方法会改变现有的数组。
    coreArray.removeRange = function (array, index, count) {
        if (!coreArray.likeArray(array)) { throw "传入的参数 array 必须是一个数组"; }
        index = coreUtil.isNumeric(index) ? index : 0;
        count = coreUtil.isNumeric(count) && count >= 0 ? count : array.length;
        core_splice.call(array, index, count);
        return array;
    };
    coreArray.prototype.removeRange = function (index, count) { return coreArray.removeRange(this, index, count); };

    //  移除数组中的指定索引位置的项；该函数定义如下参数：
    //      array: 源数据数组，被移除的项包含在该数组中；
    //      index: 指定的索引位置，当检测到源数据数组中存在该索引项时，则移除源数据中的该索引项。
    //  注意：该方法会改变现有的数组。
    coreArray.removeAt = function (array, index) { return coreArray.removeRange(array, index, 1); };
    coreArray.prototype.removeAt = function (index) { return coreArray.removeAt(this, index); };

    //  移除数组中的指定项；该函数定义如下参数：
    //      array: 源数据数组，被移除的项包含在该数组中；
    //      item: 被移除的项，当检测到源数据数组中存在该项时，则移除源数据中的该项；
    //      compare: 用于比较运算的函数，该函数被循环调用，用于比较 array 中的每一项是否与 item 等值；该函数返回一个 bool 值；这是一个可选参数。
    //          该函数的签名应该是 function (item1, item2) { }，参数 item1 表示源数组中的项、item2 表示要进行比较的项；
    //          如果不定义参数 compare，则使用默认的比较运算符 "==" 进行值的匹配；
    //  注意：该方法会改变现有的数组。
    coreArray.remove = function (array, item, compare) {
        var index = coreArray.indexOf(array, item, compare);
        if (index < 0) { return array; }
        return coreArray.removeAt(array, index);
    };
    coreArray.prototype.remove = function (item, compare) { return coreArray.remove(this, item, compare); };

    //  将另一个数组插入到当前数组的指定索引处；该方法定义如下参数：
    //      array: 源数据数组；
    //      index: 应插入 item 的位置的零始索引；
    //      collect:  包含要插入的元素的数组；该值可以为 null。
    //  返回值：返回插入元素后的数组对象本身；如果传入的参数 array 不是一个数组，则返回一个新创建的空数组对象。
    //  注意：该方法会改变现有的数组。
    coreArray.insertRange = function (array, index, collect) {
        if (!coreArray.likeArray(array)) { throw "传入的参数 array 必须是一个数组"; }
        collect = coreArray.likeArray(collect) ? collect : [collect];
        if (!coreUtil.isNumeric(index) || index < 0 || index > array.length) {
            throw "ArgumentOutOfRangeException: 传入的索引号 index 超出数组 array 的范围。";
        }
        var part = coreArray.range(array, index);
        coreArray.removeRange(array, index);
        coreArray.copy(array, collect);
        coreArray.copy(array, part);
        return array;
    };
    coreArray.prototype.insertRange = function (index, collect) { return coreArray.insertRange(this, index, collect); };

    //  将元素插入数组的指定索引处；该方法定义如下参数：
    //      array: 源数据数组；
    //      index: 应插入 item 的位置的零始索引；
    //      item:  要插入的元素；该值可以为 null。
    //  返回值：返回插入元素后的数组对象本身；如果传入的参数 array 不是一个数组，则返回一个新创建的空数组对象。
    //  注意：该方法会改变现有的数组。
    coreArray.insert = function (array, index, item) {
        var collect = [item];
        return coreArray.insertRange(array, index, collect);
    };
    coreArray.prototype.insert = function (index, item) { return coreArray.insert(this, index, item); };

    //  将另一数组中的元素复制到当前数组中一定范围的元素上；该函数定义如下参数：
    //      array: 源数据数组；
    //      index: 从 0 开始的数组索引，从该位置开始赋值 collect 元素；该参数可选，如果不定义该参数，则默认为数组的末尾；
    //      collect: 要将其元素赋值到 array 中，该数组本身不能为 null，但它可以包含为null 的元素。
    //  返回值：返回设置元素后的数组对象本身；如果传入的参数 array 不是一个数组，则返回一个新创建的空数组对象。
    //  注意：该方法会改变现有数组中的项。
    coreArray.setRange = function (array, index, collect) {
        if (!coreArray.likeArray(array)) { throw "传入的参数 array 必须是一个数组"; }
        index = coreUtil.isNumeric(index) ? index : 0;
        if (index < 0 || array.length < index) { throw "ArgumentOutOfRangeException: 传入的索引号 index 超出数组 array 的范围。"; }
        collect = coreArray.likeArray(collect) ? collect : [];
        coreArray.removeRange(array, collect.length);
        return coreArray.insertRange(array, index, collect);
    };
    coreArray.prototype.setRange = function (index, collect) { return coreArray.setRange(this, index, collect); };

    //  如果源数组中不存在指定的项，则将该项添加到源数组中；该方法提供如下参数：
    //      array: 源数据数组；
    //      item: 将要被合并到源数组中的项，如果源数组中不存在该项，则将其添加至源数组；
    //      compare: 用于比较运算的函数，该函数被循环调用，用于比较 array 中的每一项是否与 item 等值；该函数返回一个 bool 值；这是一个可选参数。
    //          该函数的签名应该是 function (item1, item2) { }，参数 item1 表示源数组中的项、item2 表示要进行比较的项；
    //          如果不定义参数 compare，则使用默认的比较运算符 "==" 进行值的匹配；
    //  返回值：返回添加元素后的数组对象本身。
    //  注意：该方法会改变现有的数组。
    coreArray.attach = function (array, item, compare) {
        if (!coreArray.contains(array, item, compare)) {
            array.push(item);
        }
        return array;
    };
    coreArray.prototype.attach = function (item, compare) { return coreArray.attach(this, item, compare); };

    //  去除数组中重复项；该方法提供如下参数:
    //      array: 源数据数组；
    //      compare: 用于比较运算的函数，该函数被循环调用，用于比较 array 中的每一项是否与 item 等值；该函数返回一个 bool 值；这是一个可选参数。
    //          该函数的签名应该是 function (item1, item2) { }，参数 item1 表示源数组中的项、item2 表示要进行比较的项；
    //          如果不定义参数 compare，则使用默认的比较运算符 "==" 进行值的匹配；
    //  返回值：返回去除重复元素后的数组对象本身。
    //  注意：该方法会改变现有的数组。
    coreArray.distinct = function (array, compare) {
        if (!coreArray.likeArray(array)) {
            throw "传入的参数 array 必须是一个数组对象。";
        }
        var temps = [];
        for (var i = 0; i < array.length; i++) {
            var item = array[i];
            if (i == 0) {
                temps.push(item);
            } else {
                coreArray.attach(temps, item, compare);
            }
        }
        coreArray.removeRange(array, 0);
        coreArray.copy(array, temps);
        return array;
    };
    coreArray.prototype.distinct = function (compare) { return coreArray.distinct(this, compare); };

    //  合并两个或多个数组；该方法提供如下参数:
    //      array: 初始源数组，之后所有的项都将被合并入该数组；
    //      item1: 第 1 个待合并项；
    //      item2: 第 2 个待合并项；
    //      item3: 第 3 个待合并项；
    //      itemn... 第 n 个待合并项；
    //  如果要进行 merge 操作的参数是数组，那么添加的是数组中的元素，而不是数组。
    //  返回值：返回合并多个数组(元素)后的数组对象本身。
    //  注意：该方法会改变现有的数组，item1、item2、item3、itemn...等所有的参数项将被合并入 array 数组。
    coreUtil.merge = coreArray.merge = function (array, item1, item2, itemn) {
        if (!coreArray.likeArray(array)) {
            throw "传入的参数 array 必须是一个数组对象。";
        }
        if (arguments.length > 1) {
            for (var i = 1; i < arguments.length; i++) {
                var arg = arguments[i];
                arg = coreArray.likeArrayNotString(arg) ? arg : [arg];
                coreArray.copy(array, arg);
            }
        }
        return array;
    };
    coreArray.prototype.merge = function () { return coreArray.merge(this, arguments); };

    //  合并两个或多个数组，重复项将不会被合并；该方法提供如下参数:
    //      array: 初始源数组；
    //      compare: 用于比较运算的函数，该函数被循环调用，用于比较 array 中的每一项是否与 item 等值；该函数返回一个 bool 值；这是一个可选参数。
    //          该函数的签名应该是 function (item1, item2) { }，参数 item1 表示源数组中的项、item2 表示要进行比较的项；
    //          如果不定义参数 compare，则使用默认的比较运算符 "==" 进行值的匹配；
    //      item1: 第 1 个待合并项；
    //      item2: 第 2 个待合并项；
    //      item3: 第 3 个待合并项；
    //      itemn... 第 n 个待合并项；
    //  如果要进行 unique 操作的参数是数组，那么添加的是数组中的元素，而不是数组。
    //  返回值：返回合并多个数组(元素)后的数组对象本身。
    //  注意：该方法会改变现有的数组，item1、item2、item3、itemn...等所有的参数项将被合并入 array 数组。
    //  该方法与 coreArray.merge 方法的区别在于：
    //      merge 方法会将源数组与所有的 item 进行合并；
    //      unique 方法会判断源数组中是否存在相应的 item，如果存在则不合并；并且如果源数组中本身的元素如果存在重复，也会进行 distinct 处理。
    coreUtil.unique = coreArray.unique = function (array, compare, item1, item2, itemn) {
        var args = coreArray.clone(arguments);
        args.callee = arguments.callee;
        if (coreUtil.isFunction(compare)) { coreArray.removeAt(args, 1); }
        coreArray.merge.apply(this, args);
        coreArray.distinct(array, compare);
        return array;
    };
    coreArray.prototype.unique = function (compare, item1, item2, itemn) {
        var args = coreArray.clone(arguments);
        args.callee = arguments.callee;
        coreArray.insert(args, 0, this);
        return coreArray.unique.apply(this, args);
    };

    //  过滤查找当前数组中的元素，并返回查找的结果；返回的查找结果是一个新的数组；该函数定义如下参数：
    //      array: 必需。 一个数组对象。
    //      compare: 必需。 一个接受最多三个参数的函数。 对于数组中的每个元素， filter 方法都会调用 callbackfn 函数一次。
    //          该回调函数的语法如：function callbackfn(value, index, array)；
    //          如果该回调函数返回 true，则该元素将被包含在返回的集合当中。
    //  返回值：一个包含回调函数为其返回 true 的所有值的新数组。 如果回调函数为 array 的所有元素返回 false，则新数组的长度为 0。
    //  参考：http://msdn.microsoft.com/ZH-CN/library/ie/ff679973(v=vs.94).aspx
    coreArray.filter = coreArray.filter ? coreArray.filter : function (array, compare, thisArg) {
        array = coreArray.likeArray(array) ? array : [];
        if (!coreUtil.isFunction(compare)) { return array; }
        var temps = [];
        for (var i = 0; i < array.length; i++) {
            if (compare.call(thisArg, array[i], i, array) == true) { temps.push(array[i]); }
        }
        return temps;
    };
    coreArray.prototype.filter = function (compare) { return coreArray.filter(this, compare); };

    //  对数组的每个元素调用定义的回调函数并返回包含结果的数组；该函数定义如下参数：
    //      array:      必需。 一个数组对象。
    //      callback:   必需。 一个接受最多三个参数的函数。 对于数组中的每个元素， map 方法都会调用 callbackfn 函数一次。
    //          该回调函数语法如：function callbackfn(value, index, array1)；
    //      thisArg:    可选。 可在 callbackfn 函数中为其引用 this 关键字的对象。 如果省略 thisArg，则 undefined 将用作 this 值。
    //  返回值：其中的每个元素均为关联的原始数组元素的回调函数返回值的新数组。
    //  备注：对于数组中的每个元素， map 方法都会调用 callbackfn 函数一次（采用升序索引顺序）。 不为数组中缺少的元素调用该回调函数。
    //      除了数组对象之外， map 方法可由具有 length 属性且具有已按数字编制索引的属性名的任何对象使用。
    //  参考：http://msdn.microsoft.com/ZH-CN/library/ie/ff679976(v=vs.94).aspx
    coreArray.map = coreArray.map ? coreArray.map : function (array, callback, thisArg) {
        array = coreArray.likeArray(array) ? array : [];
        if (!coreUtil.isFunction(callback)) {
            throw "传入的参数 callback 不是一个函数。";
        }
        var temps = [];
        for (var i = 0; i < array.length; i++) {
            var item = callback.call(thisArg, array[i], i, array);
            temps.push(item);
        }
        return temps;
    };
    coreArray.prototype.map = function (callback, thisArg) { return coreArray.map(this, callback, thisArg); };

    //  对数组进行格式转换，将数组中的每一项转换成新的格式，然后合并成一个新的数组并返回；该函数定义如下参数：
    //  该方法同 coreArray.map
    coreArray.cast = coreArray.map;
    coreArray.prototype.cast = function (convert) { return coreArray.cast(this, convert); };

    //  获取数组中最大值的项；该函数定义如下参数:
    //      array: 待查找的源数组；
    //      compare: 比较函数，该函数被循环调用，用于比较 array 中没两项的大小；这是一个可选参数；
    //          该函数的签名为 function (a, b) { }，参数 a、b 分别表示源数组中的待比较大小的项；该函数必须返回一个数值表示比较后的结果；
    //              如果 a > b ，则返回一个 大于 0 的值；
    //              如果 a < b ，则返回一个 小于 0 的值；
    //              如果 a == b，则返回 0；
    //      如果不定义该参数，则默认将 array 中的每一项当作数字来进行比较。
    coreArray.max = function (array, compare) {
        array = coreArray.likeArray(array) ? array : [];
        if (array.length == 0) { return undefined; }
        if (array.length == 1) { return array[0]; }
        return coreArray.reduce(coreArray.range(array, 1), function (prev, current, index, array) {
            return coreUtil.compare(prev, current, compare) >= 0 ? prev : current;
        }, array[0]);
    };
    coreArray.prototype.max = function (compare) { return coreArray.max(this, compare); };

    //  获取数组中值等于最大值的集合数组；该函数的参数定义和 coreArray.max 相同；
    //  该函数返回的是一个新的数组，即使查找到的结果只有一项；
    coreArray.maxs = function (array, compare) {
        array = coreArray.likeArray(array) ? array : [];
        var max = coreArray.max(array, compare);
        return coreArray.filter(array, function (item) {
            return coreUtil.compare(item, max, compare) == 0;
        });
    };
    coreArray.prototype.maxs = function (compare) { return coreArray.maxs(this, compare); };

    //  获取数组中最小值的项；该函数的参数定义和 coreArray.max 相同；
    coreArray.min = function (array, compare) {
        array = coreArray.likeArray(array) ? array : [];
        if (array.length == 0) { return undefined; }
        if (array.length == 1) { return array[0]; }
        return coreArray.reduce(coreArray.range(array, 1), function (prev, current, index, array) {
            return coreUtil.compare(current, prev, compare) >= 0 ? prev : current;
        }, array[0]);
    };
    coreArray.prototype.min = function (compare) { return coreArray.min(this, compare); };

    //  获取数组中值等于最小值的集合；该函数的参数定义和 coreArray.max 相同；
    //  该函数返回的是一个新的数组，即使查找到的结果只有一项；
    coreArray.mins = function (array, compare) {
        array = coreArray.likeArray(array) ? array : [];
        var min = coreArray.min(array, compare);
        return coreArray.filter(array, function (item) {
            return coreUtil.compare(item, min, compare) == 0;
        });
    };
    coreArray.prototype.mins = function (compare) { return coreArray.mins(this, compare); };

    //  计算数组中各项累加后的合计值；该函数定义如下参数:
    //      array:  源数据数组
    //      callback: 转换函数，该函数被循环调用，用于将 array 中的每一项转换成一个新的数值并输出；如果定义该函数，则其必须返回一个数值；该参数可选；
    //          该函数的签名应该是 function (item) { }，参数 item 表示源数组中的项；
    //          如果不定义该参数，则默认将 array 中的每一项直接相加。
    //      thisArg:    可选。 可在 callback 函数中为其引用 this 关键字的对象。 如果省略 thisArg，则 undefined 将用作 this 值。
    coreArray.sum = function (array, callback, thisArg) {
        var isFunc = coreUtil.isFunction(callback),
            fn = function (previous, current) {
                return previous + (isFunc ? callback.call(thisArg, current) : current);
            };
        return coreArray.reduce(array, fn, 0);
    };
    coreArray.prototype.sum = function (convert) { return coreArray.sum(this, convert); };

    //  计算数组中各项累积后的平均值；该函数参数的定义和 coreArray.sum 一样；
    coreArray.avg = function (array, callback, thisArg) {
        array = coreArray.likeArray(array) ? array : [];
        var sum = coreArray.sum(array, callback, thisArg),
            avg = parseFloat(sum) / array.length;
        return avg;
    };
    coreArray.prototype.avg = function (convert) { return coreArray.avg(this, convert); };

    //  从数组的开头返回指定数量的连续元素构成的新数组；该函数定义如下参数:
    //      array: 源数据数组；
    //      count: 要提取的元素项的数量，必须是一个正整数；该参数可选；如果不传入该参数或传入的值超出范围，则默认为 1。
    coreArray.take = function (array, count) {
        array = coreArray.likeArray(array) ? array : [];
        if (!coreUtil.isNumeric(count) || count < 1) { count = 1; }
        var temps = [];
        for (var i = 0; i < array.length; i++) { if (i < count) { temps.push(array[i]); } }
        return temps;
    };
    coreArray.prototype.take = function (count) { return coreArray.take(this, count); };

    //  跳过数组中指定数量的元素，然后返回剩余元素构成的新数组；该函数定义如下参数：
    //      array: 源数据数组；
    //      count: 返回剩余元素前要跳过的元素数量，必须是一个非负整数；该参数可选；如果不传入该参数或传入的值为负数，则默认为 0。
    coreArray.skip = function (array, count) {
        array = coreArray.likeArray(array) ? array : [];
        if (!coreUtil.isNumeric(count) || count < 0) { count = 0; }
        var temps = [];
        for (var i = count; i < array.length; i++) { temps.push(array[i]); }
        return temps;
    };
    coreArray.prototype.skip = function (count) { return coreArray.skip(this, count); };

    // 为数组中的每个元素执行指定操作；该函数定义如下参数：
    //      array:      必需。 一个数组对象。
    //      callback:   必需。 一个接受最多三个参数的函数。 对于数组中的每个元素， forEach 都会调用 callbackfn 函数一次。
    //          该函数语法如：function callbackfn(value, index, array)；
    //      thisArg:    可选。 可在 callbackfn 函数中为其引用 this 关键字的对象。 如果省略 thisArg，则 undefined 将用作 this 值。
    //  返回值：返回传入的参数 array 本身。
    //  备注：对于数组中的每个元素， forEach 方法都会调用 callbackfn 函数一次（采用升序索引顺序）。
    //      如果需要退出 each 循环可使回调函数返回 false，其它返回值将被忽略。
    //      除了数组对象之外， forEach 方法可由具有 length 属性且具有已按数字编制索引的属性名的任何对象使用。
    //  参考：http://msdn.microsoft.com/ZH-CN/library/ie/ff679980(v=vs.94).aspx
    coreArray.forEach = coreArray.forEach ? coreArray.forEach : function (array, callback, thisArg) {
        var isArray = coreArray.likeArray(array), temps = isArray ? array : [], i = 0, length = temps.length;
        if (temps.length == 0) { return; }
        if (!coreUtil.isFunction(callback)) { throw "传入的参数 callback 不是一个函数。"; }
        if (isArray) {
            for (; i < length; i++) { if (callback.call(thisArg, temps[i], i, temps) == false) { break; } }
        } else {
            for (i in temps) { if (callback.call(thisArg, temps[i], i, temps) == false) { break; } }
        }
        return array;
    };
    coreArray.prototype.forEach = function (callback, thisArg) { return coreArray.forEach(this, callback, thisArg); };

    //  对数组中的所有元素调用指定的回调函数。 该回调函数的返回值为累积结果，并且此返回值在下一次调用该回调函数时作为参数提供；该函数定义如下参数：
    //      array:      必需。 一个数组对象。
    //      callback:   必需。 一个接受最多四个参数的函数。 对于数组中的每个元素， reduce 方法都会调用 callbackfn 函数一次。
    //          该回调函数语法如：function callbackfn(previousValue, currentValue, currentIndex, array)
    //      initialValue:可选。 如果指定 initialValue，则它将用作初始值来启动累积。 第一次调用 callbackfn 函数会将此值作为参数而非数组值提供。
    //  返回值：通过最后一次调用回调函数获得的累积结果。
    //  异常：当满足下列任一条件时，将引发 TypeError 异常：
    //      1、callbackfn 参数不是函数对象。
    //      2、数组不包含元素，且未提供 initialValue。
    //  备注：如果提供了 initialValue，则 reduce 方法会对数组中的每个元素调用一次 callbackfn 函数（按升序索引顺序）。
    //      如果未提供 initialValue，则 reduce 方法会对从第二个元素开始的每个元素调用 callbackfn 函数。
    //      回调函数的返回值在下一次调用回调函数时作为 previousValue 参数提供。 最后一次调用回调函数获得的返回值为 reduce 方法的返回值。
    //  参考：http://msdn.microsoft.com/ZH-CN/library/ie/ff679975(v=vs.94).aspx
    coreArray.reduce = coreArray.reduce ? coreArray.reduce : function (array, callback, initialValue) {
        if (!coreUtil.isFunction(callback)) { throw "传入的参数 callback 不是一个函数。"; }
        array = coreArray.likeArray(array) ? array : [];
        if (array.length == 0 && (initialValue === undefined)) { throw "数组不包含元素，且未提供 initialValue。"; }
        var index = 0;
        if (initialValue === undefined) { initialValue = array[0]; index = 1; }
        for (var i = index; i < array.length; i++) {
            initialValue = callback.call(this, initialValue, array[i], i, array);
        }
        return initialValue;
    };
    coreArray.prototype.reduce = function (callback, initialValue) { return coreArray.reduce(this, callback, initialValue); };

    //  按降序顺序对数组中的所有元素调用指定的回调函数。 该回调函数的返回值为累积结果，并且此返回值在下一次调用该回调函数时作为参数提供；该函数定义如下参数：
    //      array:      必需。 一个数组对象。
    //      callback:   必需。 一个接受最多四个参数的函数。 对于数组中的每个元素， reduce 方法都会调用 callbackfn 函数一次。
    //          该回调函数语法如：function callbackfn(previousValue, currentValue, currentIndex, array)
    //      initialValue:可选。 如果指定 initialValue，则它将用作初始值来启动累积。 第一次调用 callbackfn 函数会将此值作为参数而非数组值提供。
    //  返回值：通过最后一次调用回调函数获得的累积结果。
    //  异常：当满足下列任一条件时，将引发 TypeError 异常：
    //      1、callbackfn 参数不是函数对象。
    //      2、数组不包含元素，且未提供 initialValue。
    //  备注：如果提供了 initialValue，则 reduceRight 方法会按降序索引顺序对数组中的每个元素调用一次 callbackfn 函数。
    //      如果未提供 initialValue，则 reduceRight 方法会按降序索引顺序对每个元素（从倒数第二个元素开始）调用 callbackfn 函数。
    //      回调函数的返回值在下一次调用回调函数时作为 previousValue 参数提供。 最后一次调用回调函数获得的返回值为 reduceRight 方法的返回值。
    //  参考：http://msdn.microsoft.com/ZH-CN/library/ie/ff679979(v=vs.94).aspx
    coreArray.reduceRight = coreArray.reduceRight ? coreArray.reduceRight : function (array, callback, initialValue) {
        if (!coreUtil.isFunction(callback)) { throw "传入的参数 callback 不是一个函数。"; }
        array = coreArray.likeArray(array) ? array : [];
        if (array.length == 0 && (initialValue === undefined)) { throw "数组不包含元素，且未提供 initialValue。"; }
        var index = array.length - 1;
        if (initialValue === undefined) { initialValue = array[array.length - 1]; index = array.length - 2; }
        for (var i = index; i > -1; i--) {
            initialValue = callback.call(this, initialValue, array[i], i, array);
        }
        return initialValue;
    };
    coreArray.prototype.reduceRight = function (callback, initialValue) { return coreArray.reduceRight(this, callback, initialValue); };

    //  确定数组的所有成员是否满足指定的测试；该函数定义如下参数：
    //      array:      必需。 一个数组对象。
    //      callback:   必需。 一个接受最多三个参数的函数。 every 方法会为 array1 中的每个元素调用 callbackfn 函数，直到 callbackfn 返回 false，或直到到达数组的结尾。
    //      thisArg:    可选。 可在 callbackfn 函数中为其引用 this 关键字的对象。 如果省略 thisArg，则 undefined 将用作 this 值。
    //  返回值：如果 callbackfn 函数为所有数组元素返回 true，则为 true；否则为 false。 如果数组没有元素，则 every 方法将返回 true。
    //  备注：除了数组对象之外， every 方法可由具有 length 属性且具有已按数字编制索引的属性名的任何对象使用。
    //  参考：http://msdn.microsoft.com/ZH-CN/library/ie/ff679981(v=vs.94).aspx
    coreArray.every = coreArray.every ? coreArray.every : function (array, callback, thisArg) {
        array = coreArray.likeArray(array) ? array : [];
        if (array.length == 0) { return true; }
        if (!coreUtil.isFunction(callback)) { throw "传入的参数 callback 不是一个函数。"; }
        for (var i = 0; i < array.length; i++) {
            if (callback.call(thisArg, array[i], i, array) == false) { return false; }
        }
        return true;
    };
    coreArray.prototype.every = function (callback, thisArg) { return coreArray.every(this, callback, thisArg); };

    //  确定指定的回调函数是否为数组中的任何元素均返回 true；该函数定义如下参数：
    //      array:      必需。 一个数组对象。
    //      callback:   必需。 一个接受最多三个参数的函数。 some 方法会为 array1 中的每个元素调用 callbackfn 函数，直到 callbackfn 返回 true，或直到到达数组的结尾。
    //          该函数语法如：function callbackfn(value, index, array1)
    //      thisArg:    可选。 可在 callbackfn 函数中为其引用 this 关键字的对象。 如果省略 thisArg，则 undefined 将用作 this 值。
    //  返回值：如果 callbackfn 函数为任何数组元素均返回 true，则为 true；否则为 false。
    //  异常：如果 callbackfn 参数不是函数对象，则将引发 TypeError 异常。
    //  备注：some 方法会按升序索引顺序对每个数组元素调用 callbackfn 函数，直到 callbackfn 函数返回 true。 如果找到导致 callbackfn 返回 true 的元素，则 some 方法会立即返回 true。 如果回调不对任何元素返回 true，则 some 方法会返回 false。
    //      除了数组对象之外， some 方法可由具有 length 属性且具有已按数字编制索引的属性名的任何对象使用。
    //  参考：http://msdn.microsoft.com/ZH-CN/library/ie/ff679978(v=vs.94).aspx
    coreArray.some = coreArray.some ? coreArray.some : function (array, callback, thisArg) {
        array = coreArray.likeArray(array) ? array : [];
        if (!coreUtil.isFunction(callback)) { throw "传入的参数 callback 不是一个函数。"; }
        for (var i = 0; i < array.length; i++) {
            if (callback.call(thisArg, array[i], i, array) == true) { return true; }
        }
        return false;
    };
    coreArray.prototype.some = function (callback, thisArg) { return coreArray.some(this, callback, thisArg); };

    //  查找指定数组中第一个符合条件判定的项会将其返回；该函数定义如下参数：
    //      array:      必需。 一个数组对象。
    //      callback:   可选。 一个接受最多三个参数的函数。 first 方法会为 array 中的每个元素调用 callbackfn 函数，直到 callbackfn 返回 true，或直到到达数组的结尾。
    //          该函数语法如：function callbackfn(value, index, array1)
    //      thisArg:    可选。 可在 callbackfn 函数中为其引用 this 关键字的对象。 如果省略 thisArg，则 undefined 将用作 this 值。
    //  返回值：如果定义了参数 callbackfn ，返回 array 中第一个导致回调函数 callback 返回 true 的项目；
    //      如果未定义参数 callback，则返回 array 中的第一个元素；
    //      如果数组 array 不包含任何元素，或者 callback 回调函数遍历完 array 中所有元素后始终未返回 true 值，则 first 方法返回 null。
    //  备注：first 方法会按升序索引顺序对每个数组元素调用 callbackfn 函数，直到 callbackfn 函数返回 true。 如果找到导致 callbackfn 返回 true 的元素，则 first 方法会立即返回该元素。 如果回调不对任何元素返回 true，则 first 方法会返回 null。
    //      除了数组对象之外， first 方法可由具有 length 属性且具有已按数字编制索引的属性名的任何对象使用。
    coreArray.first = function (array, callback, thisArg) {
        array = coreArray.likeArray(array) ? array : [];
        if (!coreUtil.isFunction(callback)) { return array.length ? array[0] : null; }
        for (var i = 0; i < array.length; i++) {
            if (callback.call(thisArg, array[i], i, array) == true) { return array[i]; }
        }
        return null;
    };
    coreArray.prototype.first = function (callback, thisArg) { return coreArray.first(this, callback, thisArg); };

    //  查找指定数组中最后一个符合条件判定的项会将其返回；该函数定义如下参数：
    //      array:      必需。 一个数组对象。
    //      callback:   可选。 一个接受最多三个参数的函数。 last 方法会从 array 的结束位置其为它的每个元素调用 callbackfn 函数，直到 callbackfn 返回 true，或直到到达数组的起始位置。
    //          该函数语法如：function callbackfn(value, index, array1)
    //      thisArg:    可选。 可在 callbackfn 函数中为其引用 this 关键字的对象。 如果省略 thisArg，则 undefined 将用作 this 值。
    //  返回值：如果定义了参数 callbackfn ，返回 array 中最后一个导致回调函数 callback 返回 true 的项目；
    //      如果未定义参数 callback，则返回 array 中的最后一个元素；
    //      如果数组 array 不包含任何元素，或者 callback 回调函数遍历完 array 中所有元素后始终未返回 true 值，则 last 方法返回 null。
    //  备注：last 方法会按降序索引顺序对每个数组元素调用 callbackfn 函数，直到 callbackfn 函数返回 true。 如果找到导致 callbackfn 返回 true 的元素，则 last 方法会立即返回该元素。 如果回调不对任何元素返回 true，则 last 方法会返回 null。
    //      除了数组对象之外， last 方法可由具有 length 属性且具有已按数字编制索引的属性名的任何对象使用。
    coreArray.last = function (array, callback, thisArg) {
        array = coreArray.likeArray(array) ? array : [];
        if (!coreUtil.isFunction(callback)) { return array.length ? array[array.length - 1] : null; }
        for (var i = array.length - 1; i >= 0; i--) {
            if (callback.call(thisArg, array[i], i, array) == true) { return array[i]; }
        }
        return null;
    };
    coreArray.prototype.last = function (callback, thisArg) { return coreArray.last(this, callback, thisArg); };

    //  获取指定数组的前 N 项元素所构成的一个新数组；该函数定义如下参数：
    //      array:  必需。 一个数组对象。
    //      length: 必须。 一个 Number 类型值，表示要获取的项的数量；
    //  返回值：返回指定的数组对象 array 的前面长度为 length 的元素所构成的一个新的数组。
    //      如果 length 的值为 0，则返回一个空数组；
    //      如果 length 的值大于 array.length，则返回 array 的一个副本；
    coreArray.left = function (array, length) {
        array = coreArray.likeArray(array) ? array : [];
        if (!length || !coreUtil.isNumeric(length) || length < 0) { return []; }
        if (length > array.length) { return coreArray.clone(array); }
        return coreArray.range(array, 0, length);
    };
    coreArray.prototype.left = function (length) { return coreArray.left(this, length); };

    //  获取指定数组的后 N 项元素所构成的一个新数组；该函数定义如下参数：
    //      array:  必需。 一个数组对象。
    //      length: 必须。 一个 Number 类型值，表示要获取的项的数量；
    //  返回值：返回指定的数组对象 array 的后面长度为 length 的元素所构成的一个新的数组。
    //      如果 length 的值为 0，则返回一个空数组；
    //      如果 length 的值大于 array.length，则返回 array 的一个副本；
    coreArray.right = function (array, length) {
        array = coreArray.likeArray(array) ? array : [];
        if (!length || !coreUtil.isNumeric(length) || length < 0) { return []; }
        if (length > array.length) { return coreArray.clone(array); }
        return coreArray.range(array, array.length + 1 - length);
    };
    coreArray.prototype.right = function (length) { return coreArray.right(this, length); };








    /////////////////////////////////////////////////////////////////////////////////////////////// 
    //  javascript 的日期函数功能扩充。
    /////////////////////////////////////////////////////////////////////////////////////////////// 

    //  判断指定的对象是否为合法的日期(Date)格式对象；
    coreDate.isDate = function (date) { return coreUtil.isDate(date); };

    //  判断指定的对象是否为一个日期(Date)对象或者是一个日期格式的字符串。
    coreDate.likeDate = function (date) { return coreDate.isDate(date) || coreString.isDate(date); };

    //  将 String 或 Number 类型值转换成 Date 类型值；
    //  返回值：该方法返回一个新创建的 Date 类型值；
    coreDate.toDate = function (obj) {
        return coreUtil.isDate(obj) ? obj : (coreUtil.isString(obj) ? coreString.toDate(obj) : new Date(obj));
    };

    //  判断指定的日期字符串是否是合法的长日期格式；
    //  该函数依赖于 coreString.isLongDate 函数。
    coreDate.isLongDate = function (date) { return coreString.isLongDate(date); };

    //  判断指定的日期字符串是否是合法的短日期格式；
    //  该函数依赖于 coreString.isShortDate 函数。
    coreDate.isShortDate = function (date) { return coreString.isShortDate(date); };

    //  判断指定的日期是否为闰年；该函数定义如下参数：
    //      date: 可以是一个 日期(Date) 对象，也可以是表示日期的字符串，或者是一个表示年份的数字。
    //  返回值：如果指定的日期是闰年，则返回 true，否则返回 false。
    coreDate.isLeapYear = function (date) {
        var y = 0;
        if (coreDate.isDate(date)) {
            y = new Date(date).getYear();
        } else if (coreUtil.isNumeric(date)) {
            y = date;
        } else {
            throw "传入的参数 date 的数据类型必须为 Date、String 或者 Number。";
        }
        var b = false;
        if (y >= 0) {
            b = (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
        } else {
            b = (y % 4 == 1 && y % 100 != 0) || (y % 400 == 1);
        }
        return b;
    };
    coreDate.prototype.isLeapYear = function () { return coreDate.isLeapYear(this); };

    //  创建一个新的 日期(Date) 对象，返回的值与当前 日期对象 的值相同；
    coreDate.clone = function (date) {
        var d = coreDate.toDate(date).getTime();
        return new Date(d);
    };
    coreDate.prototype.clone = function () { return coreDate.clone(this); };

    //  比较两个日期对象的大小；该函数定义如下参数：
    //      date1: 第 1 个待比较的日期对象；
    //      date2: 第 2 个待比较的日期对象；
    //  返回值：如果 date1 > date2，则返回一个大于 0 的值；
    //      如果 date1 < date2，则返回一个小于 0 的值；
    //      如果 date1 == date2，则返回 0。
    coreDate.compare = function (date1, date2) {
        date1 = coreDate.toDate(date1);
        date2 = coreDate.toDate(date2);
        var d1 = date1.getTime(), d2 = date2.getTime();
        return coreUtil.compare(d1, d2);
    };
    coreDate.prototype.compareTo = function (date) { return coreDate.compare(this, date); };
    coreDate.prototype.equals = function (date) { return coreDate.compare(this, date) == 0; };

    //  获取指定日期对象当前表示的季度（0 - 3）
    coreDate.getQuarter = function (date) {
        date = coreDate.toDate(date);
        var m = date.getMonth();
        var q = 0;
        if (m >= 0 && m < 3) {
            q = 0;
        } else if (m >= 3 && m < 6) {
            q = 1;
        } else if (m >= 6 && m < 9) {
            q = 2;
        } else if (m >= 9 && m < 12) {
            q = 3;
        }
        return q;
    };
    coreDate.prototype.getQuarter = function () { return coreDate.getQuarter(this); };

    //  获取当前日期对象表示所在周的星期几（0 - 6）
    coreDate.getDayOfWeek = function (date) {
        date = coreDate.toDate(date);
        return date.getDay();
    };
    coreDate.prototype.getDayOfWeek = function () { return coreDate.getDayOfWeek(this); };

    //  获取当前日期对象所在年的第几周计数。
    coreDate.getWeek = function (date, weekStart) {
        date = coreDate.toDate(date);
        weekStart = (weekStart || 0) - 0;
        if (!coreUtil.isNumeric(weekStart) || weekStart > 6) { weekStart = 0; }
        var year = date.getFullYear(),
            firstDay = new Date(year, 0, 1),
            firstWeekDays = 7 - firstDay.getDay() + weekStart,
            dayOfYear = (((new Date(year, date.getMonth(), date.getDate())) - firstDay) / (24 * 3600 * 1000)) + 1;
        return Math.ceil((dayOfYear - firstWeekDays) / 7) + 1;
    };
    coreDate.prototype.getWeek = function (weekStart) { return coreDate.getWeek(this, weekStart); };

    //  获取当前日期对象所在月的第几周计数。
    coreDate.getWeekOfMonth = function (date, weekStart) {
        date = coreDate.toDate(date);
        weekStart = (weekStart || 0) - 0;
        if (!coreUtil.isNumeric(weekStart) || weekStart > 6) { weekStart = 0; }
        var dayOfWeek = date.getDay(),
            day = date.getDate();
        return Math.ceil((day - dayOfWeek - 1) / 7) + ((dayOfWeek >= weekStart) ? 1 : 0);
    };
    coreDate.prototype.getWeekOfMonth = function (weekStart) { return coreDate.getWeekOfMonth(this, weekStart); };

    //  给指定日期对象添加指定的毫秒数，并返回一个新的日期对象；该函数定义如下参数：
    //      date: 源日期对象；
    //      millisec: 要添加的毫秒数，可以是一个负数。
    //  返回值：date 添加指定毫秒数后的新值；注意，该方法不会修改源日期对象 date，而是返回一个新创建的日期对象。
    coreDate.addTime = function (date, millisec) {
        date = coreDate.toDate(date);
        var d = Date.parse(date);
        if (!coreUtil.isNumeric(millisec)) { millisec = Date.parse(millisec); }
        return new Date(d + millisec);
    };
    coreDate.prototype.addTime = function (millisec) { return coreDate.addTime(this, millisec); };

    //  给指定日期对象添加指定的天数，并返回一个新的日期对象；该函数定义如下参数：
    //      date: 源日期对象；
    //      days: 要添加的天数，可以是一个负数。
    //  返回值：date 添加指定天数后的新值；注意，该方法不会修改源日期对象 date，而是返回一个新创建的日期对象。
    coreDate.addDays = function (date, days) {
        date = coreDate.toDate(date);
        var d = Date.parse(date);
        if (!coreUtil.isNumeric(days)) { return new Date(d); }
        var millisec = 86400000 * days;
        return new Date(d + millisec);
    };
    coreDate.prototype.addDays = function (days) { return coreDate.addDays(this, days); };

    //  给指定日期对象添加指定的小时数，并返回一个新的日期对象；该函数定义如下参数：
    //      date: 源日期对象；
    //      hours: 要添加的小时数，可以是一个负数。
    //  返回值：date 添加指定小时数后的新值；注意，该方法不会修改源日期对象 date，而是返回一个新创建的日期对象。
    coreDate.addHours = function (date, hours) {
        date = coreDate.toDate(date);
        var d = Date.parse(date);
        if (!coreUtil.isNumeric(hours)) { return new Date(d); }
        var millisec = 3600000 * hours;
        return new Date(d + millisec);
    };
    coreDate.prototype.addHours = function (hours) { return coreDate.addHours(this, hours); };

    //  给指定日期对象添加指定的毫秒数，并返回一个新的日期对象；该函数定义如下参数：
    //      date: 源日期对象；
    //      millisec: 要添加的毫秒数，可以是一个负数。
    //  返回值：date 添加指定毫秒数后的新值；注意，该方法不会修改源日期对象 date，而是返回一个新创建的日期对象。
    coreDate.addMilliseconds = function (date, millisec) {
        date = coreDate.toDate(date);
        var d = Date.parse(date);
        if (!coreUtil.isNumeric(millisec)) { return new Date(d); }
        return new Date(d + millisec);
    };
    coreDate.prototype.addMilliseconds = function (millisec) { return coreDate.addMilliseconds(this, millisec); };

    //  给指定日期对象添加指定的分钟数，并返回一个新的日期对象；该函数定义如下参数：
    //      date: 源日期对象；
    //      minutes: 要添加的分钟数，可以是一个负数。
    //  返回值：date 添加指定分钟数后的新值；注意，该方法不会修改源日期对象 date，而是返回一个新创建的日期对象。
    coreDate.addMinutes = function (date, minutes) {
        date = coreDate.toDate(date);
        var d = Date.parse(date);
        if (!coreUtil.isNumeric(minutes)) { return new Date(d); }
        var millisec = 60000 * minutes;
        return new Date(d + millisec);
    };
    coreDate.prototype.addMinutes = function (minutes) { return coreDate.addMinutes(this, minutes); };

    //  给指定日期对象添加指定的星期周数，并返回一个新的日期对象；该函数定义如下参数：
    //      date: 源日期对象；
    //      weeks: 要添加的星期周数，可以是一个负数。
    //  返回值：date 添加指定星期周数后的新值；注意，该方法不会修改源日期对象 date，而是返回一个新创建的日期对象。
    coreDate.addWeeks = function (date, weeks) {
        date = coreDate.toDate(date);
        var d = Date.parse(date);
        if (!coreUtil.isNumeric(weeks)) { return new Date(d); }
        var millisec = 86400000 * 7 * weeks;
        return new Date(d + millisec);
    };
    coreDate.prototype.addWeeks = function (weeks) { return coreDate.addWeeks(this, weeks); };

    //  给指定日期对象添加指定的月数，并返回一个新的日期对象；该函数定义如下参数：
    //      date: 源日期对象；
    //      months: 要添加的月数，可以是一个负数。
    //  返回值：date 添加指定月数后的新值；注意，该方法不会修改源日期对象 date，而是返回一个新创建的日期对象。
    coreDate.addMonths = function (date, months) {
        date = coreDate.toDate(date);
        if (!coreUtil.isNumeric(months)) { months = 0; }
        return new Date(date.getFullYear(), date.getMonth() + months, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds(), date.getMilliseconds());
    };
    coreDate.prototype.addMonths = function (months) { return coreDate.addMonths(this, months); };

    //  给指定日期对象添加指定的秒数，并返回一个新的日期对象；该函数定义如下参数：
    //      date: 源日期对象；
    //      seconds: 要添加的秒数，可以是一个负数。
    //  返回值：date 添加指定秒数后的新值；注意，该方法不会修改源日期对象 date，而是返回一个新创建的日期对象。
    coreDate.addSeconds = function (date, seconds) {
        date = coreDate.toDate(date);
        var d = Date.parse(date);
        if (!coreUtil.isNumeric(seconds)) { return new Date(d); }
        var millisec = 1000 * seconds;
        return new Date(d + millisec);
    };
    coreDate.prototype.addSeconds = function (seconds) { return coreDate.addSeconds(this, seconds); };

    //  给指定日期对象添加指定的百纳秒数，并返回一个新的日期对象；该函数定义如下参数：
    //      date: 源日期对象；
    //      ticks: 要添加的百纳秒数，可以是一个负数。
    //  返回值：date 添加指定百纳秒数后的新值；注意，该方法不会修改源日期对象 date，而是返回一个新创建的日期对象。
    coreDate.addTicks = function (date, ticks) {
        date = coreDate.toDate(date);
        var d = Date.parse(date);
        if (!coreUtil.isNumeric(ticks)) { return new Date(d); }
        var millisec = 0.000001 * ticks;
        return new Date(d + millisec);
    };
    coreDate.prototype.addTicks = function (ticks) { return coreDate.addTicks(this, ticks); };

    //  给指定日期对象添加指定的年数，并返回一个新的日期对象；该函数定义如下参数：
    //      date: 源日期对象；
    //      years: 要添加的年数，可以是一个负数。
    //  返回值：date 添加指定年数后的新值；注意，该方法不会修改源日期对象 date，而是返回一个新创建的日期对象。
    coreDate.addYears = function (date, years) {
        date = coreDate.toDate(date);
        if (!coreUtil.isNumeric(years)) { years = 0; }
        return new Date(date.getFullYear() + years, date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds(), date.getMilliseconds());
    };
    coreDate.prototype.addYears = function (years) { return coreDate.addYears(this, years); };

    //  给指定日期对象添加指定的季度数，并返回一个新的日期对象；该函数定义如下参数：
    //      date: 源日期对象；
    //      quarters: 要添加的季度数，可以是一个负数。
    //  返回值：date 添加指定季度数后的新值；注意，该方法不会修改源日期对象 date，而是返回一个新创建的日期对象。
    coreDate.addQuarters = function (date, quarters) {
        date = coreDate.toDate(date);
        if (!coreUtil.isNumeric(quarters)) { quarters = 0; }
        return new Date(date.getFullYear(), date.getMonth() + quarters * 3, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds(), date.getMilliseconds());
    };
    coreDate.prototype.addQuarters = function (quarters) { return coreDate.addQuarters(this, quarters); };

    //  给指定日期对象添加指定日期部分的指定数，并返回一个新的日期对象；该函数定义如下参数：
    //      date: 源日期对象；
    //      datepart: 指定的日期部分，字符串格式，可选的值限定为：
    //          yy 或 yyyy:  表示年；
    //          qq 或 q:     表示季度；
    //          mm 或 m:     表示月；
    //          dd 或 d:     表示日(天)；
    //          wk 或 ww:    表示周；
    //          hh:          表示小时；
    //          mi 或 n:     表示分钟；
    //          ss 或 s:     表示秒；
    //          ms:          表示毫秒；
    //      number: 要添加的指定日期部分的指定数量，可以是一个负数；
    //  返回值：date 添加指定日期部分的指定数后的一个新值；注意，该方法不会修改源日期对象 date，而是返回一个新创建的日期对象。
    coreDate.add = function (date, datepart, number) {
        if (!coreUtil.isString(datepart)) { return date; }
        if (!coreUtil.isNumeric(number)) { return date; }
        datepart = datepart.toLowerCase();
        var d = null;
        switch (datepart) {
            case "yy":
            case "yyyy":
                d = coreDate.addYears(date, number);
                break;
            case "qq":
            case "q":
                d = coreDate.addQuarters(date, number);
                break;
            case "mm":
            case "m":
                d = coreDate.addMonths(date, number);
                break;
            case "dd":
            case "d":
                d = coreDate.addDays(date, number);
                break;
            case "wk":
            case "ww":
                d = coreDate.addWeeks(date, number);
                break;
            case "hh":
                d = coreDate.addHours(date, number);
                break;
            case "mi":
            case "n":
                d = coreDate.addMinutes(date, number);
                break;
            case "ss":
            case "s":
                d = coreDate.addSeconds(date, number);
                break;
            case "ms":
                d = coreDate.addMilliseconds(date, number);
                break;
            default:
                throw "传入的参数 datepart 为不可识别的值。";
                break;
        }
        return d;
    };
    coreDate.prototype.add = function (datepart, number) { return coreDate.add(this, datepart, number); };

    //  比较两个日期对象指定部分的差，并返回比较结果；该函数定义如下参数：
    //      date: 源日期对象；
    //      datepart: 指定的日期部分，字符串格式，可选的值限定为：
    //          yy 或 yyyy:  表示年；
    //          qq 或 q:     表示季度；
    //          mm 或 m:     表示月；
    //          dd 或 d:     表示日(天)；
    //          wk 或 ww:    表示周；
    //          hh:          表示小时；
    //          mi 或 n:     表示分钟；
    //          ss 或 s:     表示秒；
    //          ms:          表示毫秒；
    //      value: 要比较的日期对象；
    //  返回值：返回 date 日期对象 和 value 日期对象 指定部分的数值的差。
    coreDate.diff = function (date, datepart, value) {
        if (!coreUtil.isString(datepart)) { return null; }
        date = coreDate.toDate(date);
        value = coreDate.toDate(value);
        datepart = datepart.toLowerCase();
        var d = null;
        switch (datepart) {
            case "yy":
            case "yyyy":
                d = value.getFullYear() - date.getFullYear();
                break;
            case "qq":
            case "q":
                var quarter = coreDate.getQuarter(value);
                d = quarter + ((value.getFullYear() - date.getFullYear()) * 3) - quarter;
                break;
            case "mm":
            case "m":
                d = (value.getMonth() + 1) + ((value.getFullYear() - date.getFullYear()) * 12) - (date.getMonth() + 1);
                break;
            case "dd":
            case "d":
                d = parseInt((value.getTime() - date.getTime()) / 86400000);
                break;
            case "wk":
            case "ww":
                d = parseInt((value.getTime() - date.getTime()) / (86400000 * 7));
                break;
            case "hh":
                d = parseInt((value.getTime() - date.getTime()) / 3600000);
                break;
            case "mi":
            case "n":
                d = parseInt((value.getTime() - date.getTime()) / 60000);
                break;
            case "ss":
            case "s":
                d = parseInt((value.getTime() - date.getTime()) / 1000);
                break;
            case "ms":
                d = value.getTime() - date.getTime();
                break;
            default:
                throw "传入的参数 datepart 为不可识别的值。";
                break;
        }
        return d;
    };
    coreDate.prototype.diff = function (datepart, value) { return coreDate.diff(this, datepart, value); };

    //  返回指定日期对象的指定部分的值；该函数定义如下参数：
    //      date: 源日期对象；
    //      datepart: 指定的日期部分，字符串格式，可选的值限定为：
    //          yy 或 yyyy:  表示年；
    //          qq 或 q:     表示季度；
    //          mm 或 m:     表示月；
    //          dd 或 d:     表示日(天)；
    //          wk 或 ww:    表示周；
    //          hh:          表示小时；
    //          mi 或 n:     表示分钟；
    //          ss 或 s:     表示秒；
    //          ms:          表示毫秒；
    //  返回值：返回指定日期对象的指定部分的值；
    coreDate.part = function (date, datepart) {
        if (!coreUtil.isString(datepart)) { return null; }
        date = coreDate.toDate(date);
        datepart = datepart.toLowerCase();
        var d = null;
        switch (datepart) {
            case "yy":
            case "yyyy":
                d = date.getFullYear();
                break;
            case "qq":
            case "q":
                d = coreDate.getQuarter(date);
                break;
            case "mm":
            case "m":
                d = date.getMonth();
                break;
            case "dd":
            case "d":
                d = date.getDate();
                break;
            case "wk":
            case "ww":
                d = date.getWeek();
                break;
            case "hh":
                d = date.getHours();
                break;
            case "mi":
            case "n":
                d = date.getMinutes();
                break;
            case "ss":
            case "s":
                d = date.getSeconds();
                break;
            case "ms":
                d = date.getMilliseconds();
                break;
            default:
                throw "传入的参数 datepart 为不可识别的值。";
                break;
        }
        return d;
    };
    coreDate.prototype.part = function (datepart) { return coreDate.part(this, datepart); };

    //  返回当前日期对象的格式化字符值；该函数定义如下参数：
    //      date:   要进行格式化的日期对象
    //      format: 返回字符串格式定义；如果该参数不传入，则默认值为 "yyyy-MM-dd"
    coreDate.format = function (date, format) {
        date = coreDate.toDate(date);
        format = coreUtil.isEmptyObjectOrNull(format) ? "yyyy-MM-dd" : format;
        switch (typeof date) {
            case "string":
                date = new Date(date.replace(/-/, "/"));
                break;
            case "number":
                date = new Date(date);
                break;
        }
        var dict = {
            "yyyy": date.getFullYear(),
            "M": date.getMonth() + 1,
            "d": date.getDate(),
            "H": date.getHours(),
            "m": date.getMinutes(),
            "s": date.getSeconds(),
            "MM": ("" + (date.getMonth() + 101)).substr(1),
            "dd": ("" + (date.getDate() + 100)).substr(1),
            "HH": ("" + (date.getHours() + 100)).substr(1),
            "mm": ("" + (date.getMinutes() + 100)).substr(1),
            "ss": ("" + (date.getSeconds() + 100)).substr(1)
        };
        return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function () {
            return dict[arguments[0]];
        });
    };
    coreDate.prototype.format = function (format) { return coreDate.format(this, format); };

    //  获取当前日期时间的长字符串格式，返回的日期时间字符串格式如 “2013年05月16日 星期四 夏季, 下午 15:38:11”
    coreDate.toLongDateTimeString = function (date) {
        date = coreDate.toDate(date);
        var year = date.getFullYear(),
            month = date.getMonth(),
            day = date.getDate(),
            hours = date.getHours(),
            minutes = date.getMinutes(),
            seconds = date.getSeconds(),
            week = date.getDay(),
            quarter = coreDate.getQuarter(date),
            hoursArray = ["午夜", "凌晨", "早上", "上午", "中午", "下午", "傍晚", "晚上"],
            weekArray = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
            //monthArray = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
            quarterArray = ["春", "夏", "秋", "冬"],
            hourSpan = hoursArray[Math.floor(parseFloat(hours) / 3)],
            weekSpan = weekArray[week],
            //monthSpan = monthArray[month],
            quarterSpan = quarterArray[quarter];
        return coreString.format(
            "{0}年{1}月{2}日 {3} {4}季, {5} {6}:{7}:{8}",
            year,
            ("" + (month + 101)).substr(1),
            ("" + (day + 100)).substr(1),
            weekSpan,
            quarterSpan,
            hourSpan,
            ("" + (hours + 100)).substr(1),
            ("" + (minutes + 100)).substr(1),
            ("" + (seconds + 100)).substr(1));
    };
    coreDate.prototype.toLongDateTimeString = function () { return coreDate.toLongDateTimeString(this); };





    var _html5ValidateCache = {};
    //  测试指定的 html 标签是否具备相应的属性；该函数定义如下参数：
    //      propName:   待测试的属性名；
    //      tagName:    被测试的 html 标签名。
    //  返回值：如果指定的标签名(tagName)所表示的 html 标签具备相应的属性名 propName，则返回 true，否则返回 false。
    coreHtml5.testProp = function (propName, tagName) {
        propName = coreString.trim(propName);
        tagName = coreString.trim(tagName);
        if (propName) { propName = propName.toLowerCase(); }
        if (tagName) { tagName = tagName.toLowerCase(); }
        var tag = _html5ValidateCache[tagName];
        if (!tag) { _html5ValidateCache[tagName] = tag = document.createElement(tagName); }
        return propName in tag ? true : false;
    };



    //  判断指定的对象是否为一个 HTML-DOM 对象；该函数定义如下参数：
    //      obj：    要判断的对象；
    //      doc：    该参数可选；表示 obj 所在页面的 document；如果不定义该参数，则默认使用当前页面的 document；
    //  返回值：如果 obj 是一个 HTML-DOM 对象且存在于指定的 document 中，则返回 true；否则返回 false。
    coreUtil.isDOM = function (obj, doc) {
        if (!obj) { return false; }
        doc = doc || document;
        return obj.nodeName && obj.nodeType == 1 && obj.ownerDocument == doc;
    };



    //  该属性表示是否启用浏览器自动给所有 DOM 元素增加唯一标识符的功能。
    var _enableUniqueID = false;
    //  该属性表示浏览器自动给所有 DOM 元素增加的唯一标识符的名称。
    coreUtil.uniqueIdName = "uniqueID";

    //  获取 HTML DOM 对象的 GUID 值；该函数定义如下参数：
    //      element:    必须，表示需要获取 uniqueID 属性的 DOM 对象；
    //  返回值：返回 element 元素的 uniqueID 值；如果该元素未定义该值，则返回 undefined。
    coreUtil.getElementUniqueID = function (element) {
        return element != undefined && element != null && element.getAttribute ? element.getAttribute(coreUtil.uniqueIdName) : null;
    };

    //  判断 HTML DOM 对象是否具有 uniqueID 属性；该函数定义如下参数：
    //      element:    必须，表示需要判断是否具有 uniqueID 属性的 DOM 对象。
    //  返回值：如果 element 元素具有 uniqueID 属性，则返回 true，否则返回 false。
    coreUtil.hasUniqueID = function (element) {
        return !coreString.isNullOrWhiteSpace(coreUtil.getElementUniqueID(element));
    };

    //  给 HTML DOM 对象设置一个新的 uniqueID 值；该函数定义如下参数：
    //      element:    必须，表示需要设置 uniqueID 属性的 DOM 对象；
    //      uniqueID:   可选，表示要给 element 元素设置的 uniqueID 值；如果不定义该值，则会调用 coreUtil.guid 方法自动生成一个 uniqueID 值。
    coreUtil.setElementUniqueID = function (element, uniqueID) {
        if (element == undefined || element == null || !element.setAttribute) { return; }
        var nodeName = (element.nodeName || coreUtil.uniqueIdName) + "_";
        uniqueID = coreString.isNullOrWhiteSpace(uniqueID) ? nodeName + coreUtil.guid("N") : uniqueID;
        element.setAttribute(coreUtil.uniqueIdName, uniqueID, 0);
    };

    //  初始化 HTML DOM 对象的 uniqueID 值；该函数定义如下参数：
    //      element:    必须，表示需要初始化 uniqueID 属性的 DOM 对象；
    //  备注：该方法判断 element 元素是否具有 uniqueID 属性值，如果有则不进行任何更改；如果没有则为期设置一个新的 uniqueID 值。
    coreUtil.initElementUniqueID = function (element) {
        if (!coreUtil.hasUniqueID(element)) {
            coreUtil.setElementUniqueID(element);
        }
    };

    coreUtil._createElement = document.createElement;
    coreUtil._createDocumentFragment = document.createDocumentFragment;

    //  重写 document.createElement 方法，使之在创建 dom 对象的同时还给对象添加一个唯一标识符 uniqueID。
    coreUtil.createElement = function () {
        var element = coreUtil._createElement.apply(this, arguments);
        if (!_enableUniqueID) { return element; }
        coreUtil.initElementUniqueID(element);
        return element;
    };

    //  重写 document.createDocumentFragment 方法，使之在创建文档碎片并向文档碎片添加子节点时向子节点添加一个唯一标识符 uniqueID。
    coreUtil.createDocumentFragment = function () {
        var documentFragment = coreUtil._createDocumentFragment.apply(this, arguments);
        if (!_enableUniqueID) { return documentFragment; }
        var appendChild = documentFragment.appendChild;
        documentFragment.appendChild = function (child) {
            $("*", child).add(child).each(function () { coreUtil.initElementUniqueID(this); });
            return appendChild.apply(this, arguments);
        };
        return documentFragment;
    };

    //  启用或者禁用浏览器自动给 DOM 元素设置全局唯一标识符功能；该函数定义如下参数：
    //      enableUniqueID；必须，布尔类型值，表示启用或禁用该功能；
    coreUtil.setEnableUniqueID = function (enableUniqueID) {
        enableUniqueID = coreUtil.isBoolean(enableUniqueID) ? enableUniqueID : false;
        _enableUniqueID = enableUniqueID;
        if (enableUniqueID) {
            document.createElement = coreUtil.createElement;
            document.createDocumentFragment = coreUtil.createDocumentFragment;
            $("*").each(function () { coreUtil.initElementUniqueID(this); });
        } else {
            document.createElement = coreUtil._createElement;
            document.createDocumentFragment = coreUtil._createDocumentFragment;
        }
    };

    //  启用浏览器自动给 DOM 元素设置全局唯一标识符功能；
    coreUtil.enableUniqueID = function () { coreUtil.setEnableUniqueID(true); };

    //  禁用浏览器自动给 DOM 元素设置全局唯一标识符功能；
    coreUtil.disableUniqueID = function () { coreUtil.setEnableUniqueID(false); };

    //  获取浏览器是否启用了自动给 DOM 元素设置全局唯一标识符功能；
    coreUtil.getEnableUniqueID = function () { return _enableUniqueID; };



    //  判断指定的 window 对象是否具有可访问的父级页面；
    //  返回值：返回一个 Boolean 值；
    //      当前页面在一个 FRAME/IFRAME 中且父级页面和当前页面同域，则返回 true；
    //      当前页面不是在一个 FRAME/IFRAME 中或父级页面和当前页面不同域，则返回 false。
    coreUtil.hasParentWindow = function (win) {
        var ret = false;
        try {
            var p = win.parent;
            ret = p && coreUtil.isWindow(p) && coreUtil.isObject(p.document) ? true : false;
        } catch (ex) { }
        return ret;
    };
    //  获取当前页面的可访问(同域)的顶级页面；
    //  返回值：返回一个 window 对象；
    coreUtil.getTop = function () {
        var w = window;
        while (coreUtil.hasParentWindow(w) && w != w.parent) { w = w.parent; }
        return w;
    };
    //  获取当前页面的可访问(同域)的父级页面；
    //  返回值：返回一个 window 对象；
    coreUtil.getParent = function () {
        var w = window;
        if (coreUtil.hasParentWindow(w) && w != w.parent) { w = w.parent; }
        return w;
    };

    //  获取当前页面所在父级页面的 window 对象；如果父级页面不可访问，则返回当前页面的 window 对象；
    //  如果当前页面为顶级页面或当前页面的父级页面和当前页面不在同一个域下，则返回当前页面的 window 对象。
    parent = coreUtil.parent = coreUtil.getParent();

    //  获取当前页面所在顶级页面的 window 对象；如果顶级页面不可访问，则返回次级页面的 window 对象；以此类推。
    //  如果当前页面为顶级页面或当前页面的父级页面和当前页面不在同一个域下，则返回当前页面的 window 对象。
    top = coreUtil.top = coreUtil.getTop();

    //  判断当前页面是否为顶级窗口页面(含跨域 IFRAME 判定)。
    coreUtil.checkTopMost = function () {
        var ret = false;
        try {
            ret = window == window.top ? true : false;
        } catch (ex) { }
        return ret;
    };
    coreUtil.isTopMost = coreUtil.isTop = coreUtil.checkTopMost();

    //  判断当前页面是否为同域下的顶级窗口页面
    coreUtil.isUtilTop = coreUtil.isUtilTopMost = window == coreUtil.top;


    coreUtil.hasParentJquery = function (win) {
        var ret = false;
        try {
            var p = win.parent;
            ret = p && coreUtil.isWindow(p) && coreUtil.isObject(p.document) && coreUtil.isFunction(p.jQuery) ? true : false;
        } catch (ex) { }
        return ret;
    };
    coreUtil.getTopJquery = function () {
        if (coreUtil.isUtilTop) { return $; }
        var w = window;
        while (coreUtil.hasParentJquery(w) && w != w.parent) { w = w.parent; }
        return w.jQuery;
    };
    var topJquery = coreUtil.getTopJquery();

    //  获取当前页面所在顶级窗口的 jQuery 对象；如果顶级窗口不存在 jQuery 对象或者 jQuery 对象无法访问(例如跨域情况下) 则返回次级 jQuery 对象；以此类推；
    coreUtil.$ = coreUtil.jQuery = coreUtil.topJquery = topJquery;

    //  获取包含当前页面的 iframe 对象。
    //  如果当前页面为顶级页面或当前页面的父级页面和当前页面不在同一个域下，则返回 null。
    coreUtil.currentFrame = null;

    //  获取包含当前页面的 iframe 对象的 id。
    //  如果当前页面为顶级页面或当前页面的父级页面和当前页面不在同一个域下，则返回 null。
    coreUtil.currentFrameId = null;

    //  获取包含当前页面的 iframe 对象的 uniqueID。
    //  如果当前页面为顶级页面或当前页面的父级页面和当前页面不在同一个域下，则返回 null。
    coreUtil.currentFrameUniqueID = null;
    coreUtil.getCurrentFrame = function () {
        if (coreUtil.isUtilTop) { return null; }
        var result = null;
        var frames = coreArray.merge([], top.document.getElementsByTagName("iframe"), top.document.getElementsByTagName("frame"));
        var find = function (frame) {
            var win = frame.contentWindow;
            if (win === window) { return frame; }
            try {
                if (!win || !coreUtil.isObject(win.document)) { return null; }
                var fs = coreArray.merge([], win.document.getElementsByTagName("iframe"), win.document.getElementsByTagName("frame"));
                $.each(fs, function () { result = find(this); return result == null; });
            } catch (ex) { }
            return result;
        };
        $.each(frames, function () { result = find(this); return result == null; });
        return result;
    };
    coreUtil.currentFrame = coreUtil.getCurrentFrame();
    if (coreUtil.currentFrame) { coreUtil.currentFrameId = coreUtil.currentFrame.id; }
    if (coreUtil.currentFrame) { coreUtil.currentFrameUniqueID = coreUtil.getElementUniqueID(coreUtil.currentFrame); }

    //  获取当前焦点对象；
    coreUtil.getActiveElement = function () { return $(document.activeElement); };

    //  获取或设置当前 window 窗体的大小；该函数定义如下重载：
    //  1、function ()；获取当前 window 的窗体大小；该方法返回一个格式如 { width, height } 的 JSON-Object；
    //  2、function (valType)；获取当前 window 的窗体大小的指定属性值；该函数定义如下参数：
    //      valType: String 类型值；该参数的值必须为 "width" 或 "height" 其中之一；表示返回当前 window 窗体大小的哪个属性值；
    //  3、function (size)；设置当前 window 的窗体大小；该函数定义如下参数：
    //      size: JSON-Object 类型值；该参数格式必须为 { width, height }，表示要将当前窗体设置的新的尺寸；
    //  4、function (width, height)；设置当前 window 的窗体大小；该函数定义如下参数：
    //      width : Number 类型值；表示窗体的新宽度；
    //      height: Number 类型值；表示窗体的新高度；
    //  5、function (valType, val)；根据指定的属性值设置窗体的尺寸大小；该函数定义如下参数：
    //      valType: String 类型值；该参数的值必须为 "width" 或 "height" 其中之一；表示要设置哪个属性；
    //      val: 表示窗体的新宽度或者新宽度，对应 valType 指示的属性；
    coreUtil.windowSize = function () {
        var length = arguments.length, arg1, arg2, arg1Type, arg2Type,
            getSize = function () {
                var win = $(window);
                return { width: window.innerWidth ? window.innerWidth : win.width(), height: window.innerHeight ? window.innerHeight : win.height() };
            },
            size = getSize();
        if (length == 0) { return size; }
        arg1 = arguments[0];
        arg1Type = coreUtil.type(arg1);
        if (length == 1) {
            arg1 = arguments[0];
            if (arg1Type == "string") { return size[arg1]; }
            if (coreUtil.isPlainObject(arg1) || arg1Type == "function") { coreUtil.windowSize(arg1.width || size.width, arg1.height || size.height); }
        }
        if (length >= 2) {
            arg2 = arguments[1];
            arg2Type = coreUtil.type(arg2);
            if (arg1Type == "string" && arg2Type == "number") {
                var newSize = $.extend({}, size);
                newSize[arg1] = arg2;
                if (size.width != newSize.width || size.height != newSize.height) { window.resizeTo(newSize.width, newSize.height); }
            }
            if (arg1Type == "number" && arg2Type == "number") { window.resizeTo(arg1, arg2); }
        }
    };

    //  获取或设置当前 window 窗体的位置；该方法定义如下重载：
    //  1、function ()；获取当前 window 的窗体位置；该方法返回一个格式如 { left, top } 的 JSON-Object；
    //  2、function (valType)；获取当前 window 的窗体位置的指定属性值；该函数定义如下参数：
    //      valType: String 类型值；该参数的值必须为 "left" 或 "top" 其中之一；表示返回当前 window 窗体位置的哪个属性值；
    //  3、function (left, top)；设置当前 window 的窗体位置；该函数定义如下参数：
    //      left: Number 类型值；表示窗体新位置的 left 值；
    //      top : Number 类型值；表示窗体新位置的 top 值；
    //  4、function (valType, val)；设置当前 window 的窗体位置；该函数定义如下参数：
    //      valType: String 类型值；表示要设置的值的类型，是 left 还是 top；该参数的值必须为 "left" 或 "top" 其中之一；
    //      val:     Number 类型值；表示 valType 对应的值；
    coreUtil.windowOffset = function () {
        var length = arguments.length, arg1, arg2, arg1Type, arg2Type,
            getOffset = function () { return { left: window.screenLeft || window.screenX, top: window.screenTop || window.screenY }; },
            offset = getOffset();
        if (length == 0) { return offset; }
        arg1 = arguments[0];
        arg1Type = coreUtil.type(arg1);
        if (length == 1) {
            arg1 = arguments[0];
            if (arg1Type == "string") { return offset[arg1]; }
            if (coreUtil.isPlainObject(arg1) || arg1Type == "function") { coreUtil.windowOffset(arg1.left || offset.left, arg1.top || offset.top); }
        }
        if (length >= 2) {
            arg2 = arguments[1];
            arg2Type = coreUtil.type(arg2);
            if (arg1Type == "string" && arg2Type == "number") {
                var newOffset = $.extend({}, offset);
                newOffset[arg1] = arg2;
                if (offset.left != newOffset.left || offset.top != newOffset.top) { window.moveTo(newSize.left, newSize.top); }
            }
            if (arg1Type == "number" && arg2Type == "number") { window.moveTo(arg1, arg2); }
        }
    };

    //  获取或设置当前 window 窗体的大小和位置；该函数定义如下重载：
    //  1、function ()；获取当前 window 的窗体大小和位置；该方法返回一个格式如 { left, top, width, height } 的 JSON-Object；
    //  2、function (valType)；获取当前 window 的窗体大小和位置的指定属性值；该函数定义如下参数：
    //      valType: String 类型值；该参数的值必须为 "left"、"top"、"width" 或 "height" 其中之一；表示返回当前 window 窗体大小和位置的哪个属性值；
    //  3、function (pos)；
    //  4、function (valType, val)；
    //  5、function (width, height)；
    //  6、function (width, height, left, top)；
    coreUtil.windowPosition = function () {
        var length = arguments.length, arg1, arg2, arg3, arg4, arg1Type, arg2Type,
            getPosition = function () { return $.extend(coreUtil.windowSize(), coreUtil.windowOffset()); },
            position = getPosition();
        if (length == 0) { return position; }
        arg1 = arguments[0];
        arg1Type = coreUtil.type(arg1);
        if (length == 1) {
            arg1 = arguments[0];
            if (arg1Type == "string") { return position[arg1]; }
            if (coreUtil.isPlainObject(arg1) || arg1Type == "function") { coreUtil.position(arg1.width || position.width, arg1.height || position.height, arg1.left || position.left, arg1.top || position.top); }
        }
        if (length == 2) {
            arg2 = arguments[1];
            arg2Type = coreUtil.type(arg2);
            if (arg1Type == "string" && arg2Type == "number") {
                var newPosition = $.extend({}, position);
                newPosition[arg1] = arg2;
                if (position.width != newPosition.width || position.height != newPosition.height || position.left != newPosition.left || position.top != newPosition.top) {
                    window.moveTo(newSize.left, newSize.top);
                    coreUtil.windowPosition(newPosition.width, newPosition.height, newPosition.left, newPosition.top);
                }
            }
            if (arg1Type == "number" && arg2Type == "number") { coreUtil.windowSize(arg1, arg2); }
        }
        if (length >= 3) {
            arg2 = arguments[1];
            arg3 = arguments[2];
            arg4 = arguments.length > 3 ? arguments[3] : null;
            coreUtil.windowSize(arg1, arg2);
            coreUtil.windowOffset(arg3, arg4);
        }
    };

    //  解析函数的运行值并返回；该函数定义如下参数：
    //      callback:   需要解析的函数，可以是一个值，也可以是函数；如果是函数，则该方法返回该函数的运行值；
    //      args:       表示需要传入函数 callback 的参数，是一个数组对象，该参数可选；
    //      thisArg:    表示传入函数 callback 包内的 this 引用对象，该参数可选。
    //  返回值：如果参数 callback 是一个函数，则进行 callback.apply(thisArg, args) 运算后并将其返回；否则直接将其返回。
    coreUtil.parseFunction = function (callback, args, thisArg) {
        var val = callback, obj = { length: 0 };
        if (coreUtil.isFunction(callback)) {
            if (arguments.length == 1) {
                args = [];
                thisArg = this;
            } else if (arguments.length == 2) {
                if (coreArray.likeArrayNotString(args)) {
                    thisArg = this;
                } else {
                    thisArg = args;
                    args = [];
                }
            }
            coreArray.copy(obj, args);
            obj.callee = callback;
            val = callback.apply(thisArg, obj);
        }
        return val;
    };

    //  解析键值对格式对象中键值格式为 key: function 的 JSON 格式对象的函数运算值并返回解析后的数据；该函数定义如下参数：
    //  返回值：返回对象中所有的 key: function 中的 function 运算后的结果与 key 序列组合成的新的对象副本；
    //  示例： var obj = { arg: 20, sex: function () { return "男"; } };
    //         coreUtil.parseMapFunction(obj); 
    //      此时，obj 的值为：{ arg: 20, sex: "男" }。
    coreUtil.parseMapFunction = function (obj) {
        var val = {};
        var type = coreUtil.type(obj);
        if (type == "object" || type == "function") {
            for (var key in obj) { val[key] = coreUtil.parseFunction(obj[key]); }
        }
        return val;
    };

    //  将通过 SOA(例如 ASP.NET 一般处理程序 或者 WebService) 方式返回的数据转换成 JSON 格式数据。
    coreUtil.parseJSON = function (data) {
        var val = null;
        var isString = coreUtil.isString(data);
        if (coreUtil.isPlainObject(data) || (coreUtil.likeArrayNotString(data))) {
            val = coreUtil.isPlainObject(data.d) ? coreUtil.parseJSON(data.d) : data;
        } else {
            val = $.parseJSON(isString ? coreString.toJSONString(data) : $(data).text());
        }
        return val;
    };

    //  采用同步发送 ajax 请求的方式，以指定的参数请求远程数据并返回；该函数定义如下参数：
    //      url:    请求的远程服务地址；
    //      args:   发送至远程服务的数据，在发送数据之前该参数将会被序列化；
    //  返回值：返回远程请求的数据；
    //  备注：该方法为 $.ajax 方法的快捷调用，采用 post 方式提交，并且 async 属性设置为 false；
    //      如果需要更加丰富的 ajax 调用，请使用 $.ajax 方法。
    coreUtil.requestAjaxData = function (url, args) {
        var val = null;
        args = coreUtil.parseMapFunction(args);
        $.ajax({
            url: url, type: "POST", data: args, async: false,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                throw XMLHttpRequest.responseText;
            }, success: function (data, textStatus, jqXHR) { val = data; }
        });
        return val;
    };

    //  采用同步发送 ajax 请求的方式，以指定的参数请求远程布尔类型数据并返回；该函数定义如下参数：
    //      url:    请求的远程服务地址；
    //      args:   发送至远程服务的数据，在发送数据之前该参数将会被序列化；
    //  返回值：返回远程请求的布尔数据；
    //  备注：该方法为 $.ajax 方法的快捷调用，采用 post 方式提交，并且 async 属性设置为 false；
    //      如果需要更加丰富的 ajax 调用，请使用 $.ajax 方法。
    coreUtil.requestAjaxBoolean = function (url, args) {
        var data = coreUtil.requestAjaxData(url, args), type = typeof data;
        if (type == "object" || (type == "string" && data.charAt(0) === "<" && data.charAt(data.length - 1) === ">" && data.length >= 3)) { data = $(data).text(); }
        return coreString.toBoolean(data);
    };

    //  采用同步发送 ajax 请求的方式，以指定的参数请求远程数据并将其转换成 JSON 格式后返回；该函数定义如下参数：
    //      url:    请求的远程服务地址；
    //      args:   发送至远程服务的数据，在发送数据之前该参数将会被序列化；
    //  返回值：返回转换成 JSON 格式后的远程请求的数据；
    //  备注：该方法为 $.ajax 方法的快捷调用，采用 post 方式提交，并且 async 属性设置为 false；
    //      如果需要更加丰富的 ajax 调用，请使用 $.ajax 方法。
    coreUtil.requestAjaxJson = function (url, args) {
        var data = coreUtil.requestAjaxData(url, args);
        return coreUtil.parseJSON(data);
    };

    //  以指定的 CSS 内容创建一个 CSS 对象；该函数定义如下参数：
    //      context: 表示 css 内容；
    //      target:  该参数可选；表示包含该 css 的 style 标签被添加到的目标位置，可以是一个 DOM 对象或者 jQuery 对象。
    //  返回值：返回创建的 CSS 标签的 jQuery DOM 对象；
    //  备注：该方法会在 target 表示的 DOM 对象或当前页面的 head 中添加一个 <style type="text/css"></style> 标签，标签中的内容即为传入的 context 值。
    coreUtil.addCss = function (context, target) {
        return $("<style>" + context + "</style>").attr("type", "text/css").appendTo(target ? target : "head");
    };

    //  通过 javascript 前端本地方式导出 Excel 数据；该函数定义如下参数：
    //      options: JSON Object 类型；定义导出数据的参数信息，该对象定义如下属性：
    //          file:   String 类型，表示要导出的文件名；
    //          columns: Array 类型，表示要导出的 Excel 数据的列信息，该数组中每一个元素都是一个 JSON Object，该 Object 定义如下属性：
    //              field:  表示数据参数 rows 中对应的列字段名；
    //              title:  表示 field 对应的列的标题(导出后显示的名称)，默认为 field 的值；
    //              width:  表示 field 对应的列的列宽(单位：像素)，默认为 140；
    //              type:   表示 field 对应的列的数据类型，可选的值有 "boolean", "number", "string", "date"，默认为 "string"。
    //              formatter:  表示 field 对应的列数据导出时的格式化函数；导出的结果显示内容为该函数运算后返回的结果，Function 类型，定义参数列表如下：
    //                  field:
    //                  rowIndex:
    //                  rowData:
    //                  array:
    //                  默认值为 function (field, rowIndex, rowData, array) { return rowData[field]; }
    //          data: 实际要导出的数据；是一个数组对象，数组中的每一个元素都是一个 JSON Object 对象，表示一行数据；该 JSON Object 中的每一个属性都表示一个列字段值；
    //              关于数据的列字段属性信息由 columns 参数定义；
    //  返回值：
    //  注意：该函数不支持 ie6。
    coreUtil.exportExcel = function (options) { $.error("未实现"); };






    //  获取或设置当前表达式匹配到的元素的边框值(css-border 属性)；该方法提供如下重载：
    //      function()              : 获取当前表达式匹配到的第一个元素的边框值，该函数返回一个格式如 { top: number, left: number, right: number, bottom: number } 的 JSON 对象；
    //      function("region")      : 获取当前表达式匹配到的第一个元素的 region 所示位置的边框值，该函数返回一个 Number 数值表示该位置的边框值；
    //      function(val)           : 设置当前表达式匹配到的所有元素的所有位置边框值为 val；该函数返回当前 jquery 链式对象；
    //      function("region", val) : 设置当前表达式匹配到的所有元素的 region 位置边框值为 val；该函数返回当前 jquery 链式对象；
    //  以上重载中，region 参数表示边框的位置，String 类型值，可选的值限定为 "top"、"left"、"right" 或 "bottom"
    //              val 参数表示要设定的边框的值，Number 类型值，不能为负数。
    coreJquery.prototype.border = function (region, val) {
        if (!arguments.length) {
            return { top: getRegionBorder("top"), left: getRegionBorder("left"), right: getRegionBorder("right"), bottom: getRegionBorder("bottom") };
        }
        var thisArg = this;
        if (arguments.length == 1) {
            if (coreUtil.isNumeric(region) || coreString.endsWith(region, "px")) {
                return thisArg.css("border", region);
            } else {
                var locale = coreString.toLowerCase(region);
                return coreArray.contains(["top", "left", "right", "bottom"], locale) ? getRegionBorder(locale) : null;
            }
        } else {
            var locale = coreString.toLowerCase(region);
            if (coreArray.contains(["top", "left", "right", "bottom"], locale)) {
                return thisArg.css("border-" + locale + "-width", val);
            } else {
                return thisArg;
            }
        }
        function getRegionBorder(locale) {
            var str = thisArg.css("border-" + locale + "-width");
            return toNumber(str);
        };
        function toNumber(str) {
            str = coreString.isNullOrEmpty(str) ? "" : String(str).toLowerCase();
            str = str.replace("px", "");
            return str ? coreString.toInteger(str) : 0;
        };
    };



    //  获取当前表达式匹配到的所有元素中的第一个元素是否具有 uniqueID 属性值；
    //  返回值：如果当当前表达式匹配到的所有元素中的第一个元素具有 uniqueID 属性值，则返回 true，否则返回 false。
    coreJquery.prototype.hasUniqueID = function () { return this.length ? coreUtil.hasUniqueID(this[0]) : false; };

    //  获取当前表达式匹配到的所有元素中的第一个元素的 uniqueID 属性值；
    //  返回值：如果当前表达式没有匹配的元素，则返回 null；否则返回 所有元素中的第一个元素的 uniqueID 属性值；
    coreJquery.prototype.getUniqueID = function () { return this.length ? coreUtil.getElementUniqueID(this[0]) : null; };

    //  设置当前表达式匹配到的所有元素的 uniqueID 属性值；该函数定义如下参数：
    //      uniqueID: 可选，表示要设置为的 uniqueID 属性值；如果不定义该值，则默认用 coreUtil.guid() 为其创建一个随机值。
    //  返回值：返回当前 jquery 对象的引用。
    coreJquery.prototype.setUniqueID = function (uniqueID) { return this.each(function () { coreUtil.setElementUniqueID(this, uniqueID); }); };

    //  初始化当前表达式匹配到的所有元素的 uniqueID 属性值；
    //  返回值：返回当前 jquery 对象的引用。
    //  备注：该方法循环判断每一个元素是否具有 uniqueID 属性值，如果有则不进行任何更改；如果没有则其期设置一个新的 uniqueID 值。
    coreJquery.prototype.initUniqueID = function () { return this.each(function () { coreUtil.initElementUniqueID(this); }); };

    //  获取或设置当前表达式元素的 uniqueID 属性值；该函数定义如下重载：
    //      1、function()；该重载表示：获取当前表达式匹配到的所有元素中第一个元素的 uniqueID 属性值；等效于 coreJquery.prototype.getUniqueID 函数；
    //      2、function(uniqueID)；该重载表示：设置当前表达式匹配到的所有元素的 uniqueID 属性值；等效于 coreJquery.prototype.setUniqueID 函数；
    coreJquery.prototype.uniqueID = function (uniqueID) {
        return arguments.length == 0 ? this.getUniqueID() : this.setUniqueID(uniqueID);
    };

    //  判断当前匹配到的元素是否具有焦点；
    coreJquery.prototype.isFocus = function () {
        var elements = $("*", this).add(this);
        for (var i = 0; i < elements.length; i++) { if (document.activeElement == elements[i]) { return true; } }
        return false;
    };

    //  测试当前 jQuery 对象是否包含另一个 DOM 对象；该函数定义如下参数：
    //      this: 方法体内的 this 对象引用，表示当前 jQuery 对象；
    //      item: DOM节点，可能被其他元素所包含
    //  返回值：如果 item DOM节点包含在 this 指向的当前 jQuery 对象中，则返回 true，否则返回 false。
    coreJquery.prototype.contains = function (item) {
        var b = false;
        this.each(function () { if ($.contains(this, item)) { b = true; return false; } });
        return b;
    };

    //  如果当前 jQuery 对象不包含指定表达式匹配的元素，则把与表达式匹配的元素添加到jQuery对象中。这个函数可以用于连接分别与两个表达式匹配的元素结果集；该函数定义如下参数：
    //      this: 方法体内的 this 对象引用，表示当前 jQuery 对象；
    //      其他参数同 jQuery 的官方 API 方法 jQuery.fn.add 相同；
    //  返回值：返回处理后的 this 的引用。
    coreJquery.prototype.attach = function () {
        var t = this;
        $.apply(this, arguments).each(function () {
            if (!t.contains(this)) { core_push.call(t, this); }
        });
        return t;
    };

    //  获取匹配元素相对滚动条顶部的偏移百分比
    coreJquery.prototype.scrollTopP = function () {
        var height = this.height();
        height = height <= 0 ? parseFloat(height) : parseFloat(1);
        return this.scrollTop() / height;
    };

    //  获取匹配元素相对滚动条左侧的偏移百分比
    coreJquery.prototype.scrollLeftP = function () {
        var width = this.width();
        width = width <= 0 ? parseFloat(width) : parseFloat(1);
        return this.scrollLeft() / width;
    };

    //  将当前表达式匹配到的所有元素及其子元素序列化成 JSON 对象并返回；该函数定义如下类型的重载方式：
    //      1、Function(Object)：其中参数 Object 对象定义如下属性：
    //          onlyEnabled:    表示返回的结果数据中是否仅包含启用(disabled == false)的 HTML 表单控件；Boolean 类型值，默认为 false。
    //          transcript :    表示当范围内存在重名(name 相同时)的 DOM 元素时，对重复元素的取值规则；
    ///                 这是一个 String 类型值，可选的值限定在以下范围：
    //              cover  :    覆盖方式，只取后面元素 的值，丢弃前面元素的值；默认值；
    //              discard:    丢弃后面元素的值，只取前面元素的值；
    //              overlay:    将所有元素的值进行叠加；
    //          overtype   :    元素叠加方式，当 transcript 的值定义为 "overlay" 时，此属性方有效；
    //                  这是一个 String 类型值，可选的值限定在以下范围：
    //              array  :    将所有重复的元素叠加为一个数组；
    //              append :    将所有的重复元素叠加为一个字符串；默认值；
    //          separator  :    元素叠加的分隔符，定义将所有重名元素叠加为一个字符串时用于拼接字符串的分隔符；
    //                  这是一个 String 类型值，默认为 ","；当 transcript 的值定义为 "overlay" 且 overtype 的值定义为 "append" 时，此属性方有效。
    //      2、Function(String)：其中参数 String 表示当范围内存在重名(name 相同时)的 DOM 元素时，对重复元素的取值规则；
    //          其取值范围和当参数格式为 JSON-Object 时的属性 transcript 一样。
    //  返回值：该方法返回一个 JSON Object，返回对象中的每个数据都表示一个表单控件值。
    coreJquery.prototype.serializeObject = function (options) {
        var rCRLF = /\r?\n/g,
	        rsubmitterTypes = /^(?:submit|button|image|reset)$/i,
	        rsubmittable = /^(?:input|select|textarea|keygen)/i,
            //rsubmittable_radio = /^(?:radio)$/i,
            //rsubmittable_checkbox = /^(?:checkbox)$/i,
            rsubmittable_radiocheckbox = /^(?:checkbox|radio)$/i,
            list, names, ret = {};
        options = options || {};

        var defaults = { onlyEnabled: false, transcript: "cover", overtype: "append", separator: "," },
            opts = $.extend({}, defaults, (typeof options == "string") ? { transcript: options } : options || {});
        if (!coreArray.contains(["cover", "discard", "overlay"], opts.transcript)) {
            opts.transcript = defaults.transcript;
        }
        if (!coreArray.contains(["array", "append"], opts.overtype)) {
            opts.overtype = defaults.overtype;
        }

        list = this.map(function () {
            var elements = jQuery.prop(this, "elements");
            return $.merge([], elements ? $.makeArray(elements) : $(this).find("*"));
        }).filter(function () {
            return this.name && (!opts.onlyEnabled || !$(this).is(":disabled")) &&
				rsubmittable.test(this.nodeName) && !rsubmitterTypes.test(this.type);
        }).map(function (i, elem) {
            var name = elem.name, id = elem.id, type = this.type, val = $(this).val(),
                checked = (this.checked == undefined || this.checked == null) ? null : this.checked;
            return {
                name: name || id, type: type, checked: checked,
                val: $.isArray(val) ? $.map(val, function (val) { return val ? val.replace(rCRLF, "\r\n") : val; })
                    : (val ? val.replace(rCRLF, "\r\n") : val)
            };
        });
        names = coreArray.distinct(list.map(function (i, elem) { return elem.name; }));
        $.each(names, function (i, name) {
            var elems = list.filter(function (i, elem) { return elem.name == name; }),
                val = elems.length == 1 ? getElemVal(elems[0]) : getElemsVal(elems);
            ret[name] = (val == undefined || val == null) ? null : val;
        });
        function getElemVal(elem) {
            return rsubmittable_radiocheckbox.test(elem.type) ? elem.checked : elem.val;
        };
        function getElemsVal(elems) {
            var items = coreArray.filter(elems, function (elem) {
                return (rsubmittable_radiocheckbox.test(elem.type) && elem.checked == true) || !rsubmittable_radiocheckbox.test(elem.type);
            });
            var values = coreArray.map(items, function (val) { return val.val; });
            switch (opts.transcript) {
                case "cover": return values[values.length - 1];
                case "discard": return values[0];
                case "overlay":
                    return opts.overtype == "array"
                        ? (values.length > 1 ? values : values[0])
                        : values.join(opts.separator);
                default: return values[0];
            }
        };
        return ret;
    };


    //  创建或定义命名空间；该函数定义如下参数：
    //      namespace:  要创建的命名空间，不同级别的命名请用符号 "." 隔开，请不要包含任何空格；
    //      callback:   可选，创建完命名空间后执行的回调函数；
    //      thisArg:    可选，同参数 callback 一起定义；表示 callback 回调函数执行中的 this 对象
    coreUtil.namespace = function (obj, namespace, callback, thisArg) {
        var index = 0, ret;
        if (typeof obj != "string") {
            ret = obj || window;
            index++;
        } else {
            ret = window;
        }
        namespace = arguments[index++];
        callback = arguments[index++];
        thisArg = arguments[index++];

        if (!namespace) { return ret; }
        var names = String(namespace).split("."), array = [], n;
        for (var i = 0; i < names.length; i++) {
            n = coreString.trim(names[i]);
            if (n != "") { array.push(n); }
        }
        $.each(array, function (i, name) {
            ret = (ret[name] == null || ret[name] == undefined) ? (ret[name] = {}) : ret[name];
        });
        if (coreUtil.isFunction(callback)) { callback.call(thisArg, ret); }
        return ret;
    };

    //  获取指定全名称的 JavaScript 类型函数对象；该函数定义如下参数：
    //      namespace   : 要获取的类的类名称，对应命名空间限定名用符号 "." 隔开，请不要包含任何空格；
    //  返回值：
    //      如果 namespace 指定的类型函数存在，则返回该类型函数对象；
    //      如果 namespace 指定的类型函数不存在，namespace 值为空字符串或者 null/undefined，否则返回 null。
    coreUtil.getNamespace = coreUtil.getDefined = function (namespace) {
        var index = 0, ret;
        if (typeof obj != "string") {
            ret = obj || window;
            index++;
        } else {
            ret = window;
        }
        namespace = arguments[index++];
        if (!namespace) { return ret; }
        var names = String(namespace).split("."), array = [], n;
        for (var i = 0; i < names.length; i++) {
            n = coreString.trim(names[i]);
            if (n != "") { array.push(n); }
        }
        $.each(array, function (i, name) {
            ret = (ret == null || ret == undefined || ret[name] == null || ret[name] == undefined) ? null : ret[name];
        });
        return ret;
    };

    //  创建或定义一个 JavaScript 类；该函数定义如下参数：
    //      namespace   : 要创建的类的类名，对应命名空间限定名用符号 "." 隔开，请不要包含任何空格；
    //      data        : 可选；被创建的类型默认定义的成员属性或方法(即 prototype)；
    //      createFn    : 可选；被创建的类型的默认构造函数；
    //  返回值：返回被创建的类型的 Function 对象；
    //  注意：
    //      如果传入的参数 namespace 的值为 null，则创建的这个 JavaScript 类为匿名类；
    //      如果指定此定义函数时，namespace 所指定的对象已经存在，则该对象将会被覆盖；
    //      可以用 coreUtil.getDefined(namespace) 来判断 namespace 所指定的对象是否已经存在；
    coreUtil.define = function (namespace, data, createFn) {
        if (coreUtil.isFunction(data)) {
            createFn = data;
            data = {};
        }
        var p, name, constructor, func;
        if (namespace) {
            var names = String(namespace).split("."), array = [], n;
            for (var i = 0; i < names.length; i++) {
                n = coreString.trim(names[i]);
                if (n != "") { array.push(n); }
            }
            if (array[0] != "window") { array.splice(0, 0, "window"); }
            if (array.length > 1) {
                p = coreUtil.namespace(array.slice(0, array.length - 1).join("."));
                name = array[array.length - 1];
            }
        }
        createFn = coreUtil.isFunction(createFn) ? createFn : function () { };
        constructor = function (options) {
            return createFn.call(this, options);
        };
        func = function (options) {
            return new constructor(options);
        };
        func.defaults = func.fn = func.prototype = constructor.defaults = constructor.fn = constructor.prototype;
        $.extend(func, { extend: $.extend, union: coreJquery.union, init: constructor, inst: createFn });
        $.extend(func.defaults, data, { extend: $.extend, union: coreJquery.union });
        if (p && name) {
            var old = p[name];
            p[name] = func;
            if (old) {
                coreJquery.union(func, old);
            }
        }
        return func;
    };

    //  以指定的参数创建一个指定类型的对象；该函数定义如下参数：
    //      namespace   : 必须，String 类型值，指定的类型函数名称；
    //      options     : 可选，JSON-Object 类型值；构造 namespace 类型对象所用的参数，默认为 null；
    //  返回值：
    //      如果 namespace 指定的类型函数存在，则返回该函数通过 options 参数和 thisArgs 参数所构造的对象；
    //      如果 namespace 指定的类型函数不存在，则返回 null。
    coreUtil.createDefinedObject = function (namespace, options) {
        var type = coreUtil.getDefined(namespace);
        return coreUtil.isFunction(type) ? type(options) : null;
    };



    //  禁用页面的 window.console 脚本功能；
    //  返回值：无返回值。
    coreUtil.disableConsole = function () {
        try {
            var _console = window.console;
            if (Object.defineProperties) {
                Object.defineProperties(window, "console", {
                    get: function () {
                        if (_console._commandLineAPI) {
                            throw "抱歉, 为了用户安全, 本站已禁用 console 脚本功能";
                        }
                        return _console;
                    },
                    set: function (val) {
                        return _console = val;
                    }
                });
            }
        } catch (e) { }
    };



    //  下段代码提供 javascript 控制浏览器 进入/退出 全屏模式的 API。
    var fullScreen = {
        supports: false, eventName: "", prefix: "", prefixes: "webkit moz o ms khtml".split(" "),
        isFullScreen: function () { }, requestFullScreen: function () { }, cancelFullScreen: function () { }
    };
    if (typeof document.cancelFullScreen != "undefined") {
        fullScreen.supports = true;
    } else {
        for (var i = 0; i < fullScreen.prefixes.length; i++) {
            fullScreen.prefix = fullScreen.prefixes[i];
            if (typeof document[fullScreen.prefix + "CancelFullScreen"] != "undefined") {
                fullScreen.supports = true;
                break;
            }
        }
    }
    if (fullScreen.supports) {
        fullScreen.eventName = fullScreen.prefix + "fullscreenchange";
        fullScreen.isFullScreen = function () {
            switch (this.prefix) {
                case "": return document.fullScreen;
                case "webkit": return document.webkitIsFullScreen;
                default: return document[this.prefix + "FullScreen"];
            }
        };
        fullScreen.requestFullScreen = function (elem) {
            return (this.prefix === "") ? elem.requestFullScreen() : elem[this.prefix + "RequestFullScreen"]();
        };
        fullScreen.cancelFullScreen = function (elem) {
            return (this.prefix === "") ? document.cancelFullScreen() : document[this.prefix + "CancelFullScreen"]();
        };
    }
    coreUtil.isFullScreen = coreJquery.isFullScreen = function () {
        return fullScreen.isFullScreen();
    };
    coreUtil.requestFullScreen = coreJquery.requestFullScreen = function (selector) {
        if (selector == null || selector == undefined) { selector = document.documentElement; }
        selector = coreUtil.parseJquery(selector);
        return selector.each(function () {
            if (fullScreen.supports) { fullScreen.requestFullScreen(this); }
        });
    };
    coreJquery.prototype.requestFullScreen = function () { return coreJquery.requestFullScreen(this); };
    coreUtil.cancelFullScreen = coreJquery.cancelFullScreen = function (selector) {
        if (selector == null || selector == undefined) { selector = document.documentElement; }
        selector = coreUtil.parseJquery(selector);
        return selector.each(function () {
            if (fullScreen.supports) { fullScreen.cancelFullScreen(this); }
        });
    };
    coreJquery.prototype.cancelFullScreen = function () { return coreJquery.cancelFullScreen(this); };
    coreUtil.toggleFullScreen = coreJquery.toggleFullScreen = function (selector) {
        if (selector == null || selector == undefined) { selector = document.documentElement; }
        selector = coreUtil.parseJquery(selector);
        return selector.each(function () {
            if (fullScreen.supports) {
                if (coreUtil.isFullScreen()) {
                    fullScreen.cancelFullScreen(this);
                } else {
                    fullScreen.requestFullScreen(this);
                }
            }
        });
    };
    coreJquery.prototype.toggleFullScreen = function () { return coreJquery.toggleFullScreen(this); };
    coreUtil.supportsFullScreen = fullScreen.supports;
    coreUtil.fullScreenEventName = fullScreen.eventName;
    coreUtil.fullScreen = fullScreen;





    //  元素闪动的默认时间间隔（毫秒）；该属性仅限于被方法 coreUtil.shine 调用；
    coreUtil.shineInterval = 100;
    //  元素闪动的默认次数；该属性仅限于被方法 coreUtil.shine 调用；
    coreUtil.shineTimes = 8;
    //  使元素闪动
    coreUtil.shine = coreJquery.shine = function (selector, interval, times) {
        if (selector == null || selector == undefined) { return selector; }
        selector = coreUtil.parseJquery(selector);
        if (!coreUtil.isNumeric(interval) || interval <= 40) { interval = coreUtil.shineInterval; }
        if (!coreUtil.isNumeric(times) || times < 4) { times = coreUtil.shineTimes; }
        var a = function () { selector.addClass("jdirk-shine"); };
        var b = function () { selector.removeClass("jdirk-shine"); };
        var run = function () {
            coreUtil.exec(a, interval / 2);
            coreUtil.exec(b, interval);
            times--;
            if (times > 0) { coreUtil.exec(run, interval); }
        };
        coreUtil.exec(run);
        return selector;
    };
    coreJquery.prototype.shine = function (interval, times) { return coreJquery.shine(this, interval, times); };



    coreUtil.addFavorites = function (url, title) {
        var favo = { url: window.location.href, title: document.title };
        if (arguments.length == 1) { $.extend(favo, url); }
        if (arguments.length > 1) { $.extend(favo, { url: url, title: title }); }
        if (window.external && coreUtil.isFunction(window.external.AddFavorite)) {
            window.external.AddFavorite(favo.url, favo.title);
        } else {
            window.alert("请按 Ctrl + D 为您的浏览器添加 收藏/书签!");
        }
    };





    //  用一个或多个其他对象来扩展一个对象，返回被扩展的对象；该函数定义如下参数：
    //      deep:   可选；如果设为 true，则递归合并；
    //      target: 可选；一个对象，如果附加的对象被传递给这个方法将那么它将接收新的属性，如果它是唯一的参数将扩展jQuery的命名空间；
    //      object1:待合并到 target 的对象；
    //      object2:待合并到 target 的对象；
    //      objectN:待合并到 target 的对象；
    //      ...
    //  参考 jquery-2.0.0.js 中关于 jQuery.extend 以及 jQuery.fn.extend 方法的定义；
    //  注意：该方法与 jQuery.extend 以及 jQuery.fn.extend 的不同之处在于：
    //      jQuery.extend 以及 jQuery.fn.extend：无论 target 对象中是否存在 object1、object2、objectN 等待合并对象中相应的属性，待合并对象中的相应属性都将会合并到 target 中；
    //      union: 如果 target 对象中存在 object1、object2、objectN 等待合并对象中相应的属性，则该属性将不会被合并到 target 中。
    var union = coreJquery.union = coreJquery.prototype.union = function () {
        var src, copyIsArray, copy, name, options, clone, target = arguments[0] || {}, i = 1, length = arguments.length, deep = false;
        if (typeof target === "boolean") { deep = target; target = arguments[1] || {}; i = 2; }
        if (typeof target !== "object" && !coreUtil.isFunction(target)) { target = {}; }
        if (length === i) { target = this; --i; }
        for (; i < length; i++) {
            if ((options = arguments[i]) != null) {
                for (name in options) {
                    src = target[name];
                    copy = options[name];
                    if (target === copy) { continue; }
                    if (deep && copy && (coreUtil.isPlainObject(copy) || (copyIsArray = coreUtil.isArray(copy)))) {
                        if (copyIsArray) {
                            copyIsArray = false;
                            clone = src && coreUtil.isArray(src) ? src : [];
                        } else {
                            clone = src && coreUtil.isPlainObject(src) ? src : {};
                        }
                        target[name] = union(deep, clone, copy);
                    } else if (copy !== undefined && copy !== null && (src === undefined || src === null)) {
                        target[name] = copy;
                    }
                }
            }
        }
        return target;
    };


    coreUtil.addCss(".jdirk-shine { filter: alpha(opacity=40); opacity: 0.4; }");

    //  _enableUniqueID = true;
    //  初始化浏览器的自动给 DOM 元素创建全局唯一标识符 uniqueID 功能；
    if (_enableUniqueID) {
        $(function () {
            if (coreUtil.currentFrame) {
                coreUtil.initElementUniqueID(coreUtil.currentFrame);
            }
            coreUtil.setEnableUniqueID(_enableUniqueID);
        });
    }




    ///////////////////////////////////////////////////////////////////////////////////////////////
    //  初始化 JSON 对象（兼容 IE 6、7、8 使之支持 JSON 对象）
    //  json2.js 2013-05-26
    //  Public Domain. NO WARRANTY EXPRESSED OR IMPLIED. USE AT YOUR OWN RISK.
    //  See http://www.JSON.org/js.html
    ///////////////////////////////////////////////////////////////////////////////////////////////
    if (typeof JSON !== 'object') { JSON = {}; }
    function f(n) { return n < 10 ? '0' + n : n; }
    if (typeof Date.prototype.toJSON !== 'function') {
        Date.prototype.toJSON = function () {
            return isFinite(this.valueOf())
                ? this.getUTCFullYear() + '-' +
                    f(this.getUTCMonth() + 1) + '-' +
                    f(this.getUTCDate()) + 'T' +
                    f(this.getUTCHours()) + ':' +
                    f(this.getUTCMinutes()) + ':' +
                    f(this.getUTCSeconds()) + 'Z'
                : null;
        };
        String.prototype.toJSON = Number.prototype.toJSON = Boolean.prototype.toJSON = function () { return this.valueOf(); };
    }
    var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        gap,
        indent,
        meta = { '\b': '\\b', '\t': '\\t', '\n': '\\n', '\f': '\\f', '\r': '\\r', '"': '\\"', '\\': '\\\\' },
        rep;
    function quote(string) {
        escapable.lastIndex = 0;
        return escapable.test(string) ? '"' + string.replace(escapable, function (a) {
            var c = meta[a];
            return typeof c === 'string' ? c : '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
        }) + '"' : '"' + string + '"';
    }
    function str(key, holder) {
        var i, k, v, length, mind = gap, partial, value = holder[key];
        if (value && typeof value === 'object' && typeof value.toJSON === 'function') { value = value.toJSON(key); }
        if (typeof rep === 'function') { value = rep.call(holder, key, value); }
        switch (typeof value) {
            case 'string':
                return quote(value);
            case 'number':
                return isFinite(value) ? String(value) : 'null';
            case 'boolean':
            case 'null':
                return String(value);
            case 'object':
                if (!value) { return 'null'; }
                gap += indent;
                partial = [];
                if (Object.prototype.toString.apply(value) === '[object Array]') {
                    length = value.length;
                    for (i = 0; i < length; i += 1) { partial[i] = str(i, value) || 'null'; }
                    v = partial.length === 0 ? '[]' : gap ? '[\n' + gap + partial.join(',\n' + gap) + '\n' + mind + ']' : '[' + partial.join(',') + ']';
                    gap = mind;
                    return v;
                }
                if (rep && typeof rep === 'object') {
                    length = rep.length;
                    for (i = 0; i < length; i += 1) {
                        if (typeof rep[i] === 'string') {
                            k = rep[i];
                            v = str(k, value);
                            if (v) { partial.push(quote(k) + (gap ? ': ' : ':') + v); }
                        }
                    }
                } else {
                    for (k in value) {
                        if (Object.prototype.hasOwnProperty.call(value, k)) {
                            v = str(k, value);
                            if (v) { partial.push(quote(k) + (gap ? ': ' : ':') + v); }
                        }
                    }
                }
                v = partial.length === 0 ? '{}' : gap ? '{\n' + gap + partial.join(',\n' + gap) + '\n' + mind + '}' : '{' + partial.join(',') + '}';
                gap = mind;
                return v;
        }
    }
    if (typeof JSON.stringify !== 'function') {
        JSON.stringify = function (value, replacer, space) {
            var i;
            gap = '';
            indent = '';
            if (typeof space === 'number') {
                for (i = 0; i < space; i += 1) { indent += ' '; }
            } else if (typeof space === 'string') {
                indent = space;
            }
            rep = replacer;
            if (replacer && typeof replacer !== 'function' && (typeof replacer !== 'object' || typeof replacer.length !== 'number')) {
                throw new Error('JSON.stringify');
            }
            return str('', { '': value });
        };
    }
    if (typeof JSON.parse !== 'function') {
        JSON.parse = function (text, reviver) {
            var j;
            function walk(holder, key) {
                var k, v, value = holder[key];
                if (value && typeof value === 'object') {
                    for (k in value) {
                        if (Object.prototype.hasOwnProperty.call(value, k)) {
                            v = walk(value, k); if (v !== undefined) { value[k] = v; } else { delete value[k]; }
                        }
                    }
                }
                return reviver.call(holder, key, value);
            }
            text = String(text);
            cx.lastIndex = 0;
            if (cx.test(text)) {
                text = text.replace(cx, function (a) { return '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4); });
            }
            if (/^[\],:{}\s]*$/
                    .test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@')
                        .replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']')
                        .replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {
                j = eval('(' + text + ')');
                return typeof reviver === 'function' ? walk({ '': j }, '') : j;
            }
            throw new SyntaxError('JSON.parse');
        };
    }




    ///////////////////////////////////////////////////////////////////////////////////////////////
    //  jQuery Cookie Plugin v1.4.0
    //  https://github.com/carhartl/jquery-cookie
    //
    //  Copyright 2013 Klaus Hartl
    //  Released under the MIT license
    ///////////////////////////////////////////////////////////////////////////////////////////////
    var cookie_inst = function () {
        var pluses = /\+/g;
        function encode(s) { return config.raw ? s : encodeURIComponent(s); }
        function decode(s) { return config.raw ? s : decodeURIComponent(s); }
        function stringifyCookieValue(value) { return encode(config.json ? JSON.stringify(value) : String(value)); }
        function parseCookieValue(s) {
            if (s.indexOf('"') === 0) { s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\'); }
            try { s = decodeURIComponent(s.replace(pluses, ' ')); } catch (e) { return; }
            try { return config.json ? JSON.parse(s) : s; } catch (e) { }
        }
        function read(s, converter) {
            var value = config.raw ? s : parseCookieValue(s);
            return coreUtil.isFunction(converter) ? converter(value) : value;
        }
        var config = coreJquery.cookie = coreUtil.cookie = function (key, value, options) {
            if (value !== undefined && !coreUtil.isFunction(value)) {
                options = $.extend({}, config.defaults, options);
                if (typeof options.expires === 'string') {
                    options.expires = coreString.toNumber(options.expires);
                }
                if (typeof options.expires === 'number') {
                    var days = options.expires, t = options.expires = new Date();
                    t.setDate(t.getDate() + days);
                }
                return (document.cookie = [
                    encode(key), '=', stringifyCookieValue(value),
                    options.expires ? '; expires=' + options.expires.toUTCString() : '',
                    options.path ? '; path=' + options.path : '',
                    options.domain ? '; domain=' + options.domain : '',
                    options.secure ? '; secure' : ''
                ].join(''));
            }
            var result = key ? undefined : {}, cookies = document.cookie ? document.cookie.split('; ') : [];
            for (var i = 0, l = cookies.length; i < l; i++) {
                var parts = cookies[i].split('=');
                var name = decode(parts.shift());
                var cookie = parts.join('=');
                if (key && key === name) { result = read(cookie, value); break; }
                if (!key && (cookie = read(cookie)) !== undefined) { result[name] = cookie; }
            }
            return result;
        };
        config.defaults = {};
        coreJquery.removeCookie = coreUtil.removeCookie = function (key, options) {
            if (config(key) !== undefined) { config(key, '', $.extend({}, options, { expires: -1 })); return true; }
            return false;
        };
    };
    cookie_inst();




    union($, coreJquery);
    union($.fn, coreJquery.prototype);

    union(String, coreString);
    union(String.prototype, coreString.fn);
    union(Date, coreDate);
    union(Date.prototype, coreDate.fn);
    union(Number, coreNumber);
    union(Number.prototype, coreNumber.fn);
    union(Array, coreArray);
    union(Array.prototype, coreArray.fn);
    union(Boolean, coreBoolean);
    union(Boolean.prototype, coreBoolean.fn);

    union($.fn, Array.prototype);


})(window, jQuery);