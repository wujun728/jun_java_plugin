"use strict";
layui.define(["layer"], function (exprots) {
    var $ = layui.jquery;
    var okUtils = {
        /**
         * 是否前后端分离
         */
        isFrontendBackendSeparate: true,
        /**
         * 服务器地址
         */
        baseUrl: "/",
        /**
         * 获取body的总宽度
         */
        getBodyWidth: function () {
            return document.body.scrollWidth;
        },
        /**
         * 主要用于对ECharts视图自动适应宽度
         */
        echartsResize: function (element) {
            var element = element || [];
            window.addEventListener("resize", function () {
                var isResize = localStorage.getItem("isResize");
                // if (isResize == "false") {
                for (let i = 0; i < element.length; i++) {
                    element[i].resize();
                }
                // }
            });
        },
        /**
         * ajax()函数二次封装
         * @param url
         * @param type
         * @param param
         * @param load
         * @param sendJson  是否发送Json数据
         * @returns {*|never|{always, promise, state, then}}
         */
        ajax: function (url, type, param, load, sendJson = false) {
            var deferred = $.Deferred();
            var loadIndex;
            $.ajax({
                url: okUtils.isFrontendBackendSeparate ? okUtils.baseUrl + url : url,
                type: type || "get",
                data: param || {},
                dataType: "json",
                contentType: sendJson ? "application/json" : "application/x-www-form-urlencoded",
                beforeSend: function () {
                    if (load) {
                        loadIndex = layer.load(0, {shade: false});
                    }
                },
                success: function (data) {
                    if (data.errorCode == "0") {
                        // 业务正常
                        deferred.resolve(data.result)
                    } else {
                        // 业务异常
                        layer.msg(data.errorMessage, {icon: 7, time: 2000});
                        deferred.reject("okUtils.ajax warn: " + data.errorMessage);
                    }
                },
                complete: function () {
                    if (load) {
                        layer.close(loadIndex);
                    }
                },
                error: function () {
                    layer.close(loadIndex);
                    layer.msg("服务器错误", {icon: 2, time: 2000});
                    deferred.reject("okUtils.ajax error: 服务器错误");
                }
            });
            return deferred.promise();
        },
        table: {
            /**
             * 主要用于针对表格批量操作操作之前的检查
             * @param table
             * @returns {string}
             */
            batchCheck: function (table) {
                var checkStatus = table.checkStatus("tableId");
                var rows = checkStatus.data.length;
                if (rows > 0) {
                    var idsStr = "";
                    for (var i = 0; i < checkStatus.data.length; i++) {
                        idsStr += checkStatus.data[i].id + ",";
                    }
                    return idsStr;
                } else {
                    layer.msg("未选择有效数据", {offset: "t", anim: 6});
                }
            },
            /**
             * 在表格页面操作成功后弹窗提示
             * @param content
             */
            successMsg: function (content) {
                layer.msg(content, {icon: 1, time: 1000}, function () {
                    // 刷新当前页table数据
                    $(".layui-laypage-btn")[0].click();
                });
            }
        },
        /**
         * 获取父窗体的okTab
         * @returns {string}
         */
        getOkTab: function () {
            return parent.objOkTab;
        },
        /**
         * 格式化当前日期
         * @param date
         * @param fmt
         * @returns {void | string}
         */
        dateFormat: function (date, fmt) {
            var o = {
                "M+": date.getMonth() + 1,
                "d+": date.getDate(),
                "h+": date.getHours(),
                "m+": date.getMinutes(),
                "s+": date.getSeconds(),
                "q+": Math.floor((date.getMonth() + 3) / 3),
                "S": date.getMilliseconds()
            };
            if (/(y+)/.test(fmt))
                fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        }
    };
    exprots("okUtils", okUtils);
});
