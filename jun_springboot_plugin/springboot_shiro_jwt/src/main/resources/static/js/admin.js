layui.use(['jquery', 'nprogress'], function () {
    var $ = layui.jquery,
        nprogress = layui.nprogress;

    var _ajax = $.ajax;
    $.ajax = function (opt) {

        var fn = {
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            },
            success: function (data, textStatus) {
            },
            beforeSend: function (XHR) {
            },
            complete: function (XHR, TS) {
            }
        };
        if (opt.error) {
            fn.error = opt.error;
        }
        if (opt.success) {
            fn.success = opt.success;
        }
        if (opt.beforeSend) {
            fn.beforeSend = opt.beforeSend;
        }
        if (opt.complete) {
            fn.complete = opt.complete;
        }
        //扩展增强处理
        var _opt = $.extend(opt, {
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                //错误方法增强处理
                fn.error(XMLHttpRequest, textStatus, errorThrown);
            },
            success: function (data, textStatus) {
                if (data.code === 0) {
                    //成功回调方法增强处理
                    fn.success(data, textStatus);
                } else if (data.code === 1001) {
                    layer.msg('登录超时', {icon: 2,time: 1000}, function () {
                        window.location = '/page/login.html';
                    });
                } else {
                    layer.closeAll('loading'); //关闭加载层
                    layer.msg(data.msg, {icon: 2});
                }
            },
            beforeSend: function (XHR) {
                nprogress.start();
                //提交前回调方法
                fn.beforeSend(XHR);
            },
            complete: function (XHR, TS) {
                nprogress.done();
                fn.complete(XHR, TS);
            }
        });
        return _ajax(_opt);
    };

    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1, //month
            "d+": this.getDate(),    //day
            "h+": this.getHours(),   //hour
            "m+": this.getMinutes(), //minute
            "s+": this.getSeconds(), //second
            "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
            "S": this.getMilliseconds() //millisecond
        };
        if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
            (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o) if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1,
                RegExp.$1.length === 1 ? o[k] :
                    ("00" + o[k]).substr(("" + o[k]).length));
        return format;
    };


    window.priceFormat = function (value) {
        return '<span>￥' + Number(value).toFixed(2) + '</span>';
    }

});