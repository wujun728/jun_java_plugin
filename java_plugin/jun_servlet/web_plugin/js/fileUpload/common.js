/**
 * 公共js, 依赖jquery
 * Author: zhoujingjie
 * Date: 2014-5-5
 */

$.async = $.ajax;

$.get = function (url, params, fn) {
    $.async({
        url: url,
        type: "get",
        dataType: "json",
        data: params,
        success: function () {
            if(typeof arguments[0] == "string"){
                arguments[0]= $.parseJSON(arguments[0]);
            }
            if(!arguments[0].message && arguments[0].data=="un_logon"){
                top.location.href="index.action";
                return;
            }
            if (fn){
                fn.apply(null, arguments);
            }
        }
    });
}
$.post = function (url, params, fn) {
    $.async({
        url: url,
        type: "post",
        dataType: "json",
        data: params,
        success: function () {
            if (fn)
                fn.apply(null, arguments);
        }
    });
}
$.ajax = function (option) {
    var option = $.extend({
        url: "",
        data: {},
        success: function () {
        }
    }, option);
    $.get(option.url, option.data, option.success);
};

$.fn.every = function(fn){
    var e=true;
    $(this).each(function(){
        if(!fn.call(this)){
            e= false;
        }
    });
    return e;
}




if (!Array.prototype.map) {
    Array.prototype.map = function (fun /*, thisArg */) {
        "use strict";
        if (this === void 0 || this === null)
            throw new TypeError();
        var t = Object(this);
        var len = t.length >>> 0;
        if (typeof fun !== "function")
            throw new TypeError();
        var res = new Array(len);
        var thisArg = arguments.length >= 2 ? arguments[1] : void 0;
        for (var i = 0; i < len; i++) {
            if (i in t)
                res[i] = fun.call(thisArg, t[i], i, t);
        }

        return res;
    };
}
if (!Array.prototype.forEach) {
    Array.prototype.forEach = function (fun /*, thisArg */) {
        "use strict";
        if (this === void 0 || this === null)
            throw new TypeError();
        var t = Object(this);
        var len = t.length >>> 0;
        if (typeof fun !== "function")
            throw new TypeError();
        var thisArg = arguments.length >= 2 ? arguments[1] : void 0;
        for (var i = 0; i < len; i++) {
            if (i in t)
                fun.call(thisArg, t[i], i, t);
        }
    };
}
if(!window.console){
    window.console={
        log:function(){},
        error: function(){},
        debug: function(){},
        info: function(){},
        memory: {}
    }
}


