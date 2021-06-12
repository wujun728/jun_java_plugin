"use strict";
var date_format = function (timestamp, format) {
   format = format || 'yyyy年MM月dd';
   timestamp = timestamp + "";
   if (timestamp * 1 > 0 && timestamp.length == 10) {
      timestamp = timestamp * 1000;
   }

   // 通过getDate()方法获取date类型的时间
   var regYear = new RegExp("(y+)", "i");
   var realDate = new Date(timestamp);

   function timeFormat(num) {
      return num < 10 ? '0' + num : num;
   }

   var date = [
      ["M+", timeFormat(realDate.getMonth() + 1)],
      ["d+", timeFormat(realDate.getDate())],
      ["h+", timeFormat(realDate.getHours())],
      ["m+", timeFormat(realDate.getMinutes())],
      ["s+", timeFormat(realDate.getSeconds())],
      ["q+", Math.floor((realDate.getMonth() + 3) / 3)],
      ["S+", realDate.getMilliseconds()],
   ];
   var reg1 = regYear.exec(format);

   if (reg1) {
      format = format.replace(reg1[1], (realDate.getFullYear() + '').substring(4 - reg1[1].length));
   }
   for (var i = 0; i < date.length; i++) {
      var k = date[i][0];
      var v = date[i][1];
      // getRegExp初始化一个正则表达式对象
      var reg2 = new RegExp("(" + k + ")").exec(format);
      if (reg2) {
         format = format.replace(reg2[1], reg2[1].length == 1
            ? v : ("00" + v).substring(("" + v).length));
      }
   }
   return format;
};
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
      baseUrl: "http://rap2api.taobao.org/app/mock/233041",
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
       * @param params
       * @param load
       * @returns {*|never|{always, promise, state, then}}
       */
      ajax: function (url, type, params, load) {
         var deferred = $.Deferred();
         var loadIndex;
         $.ajax({
            url: okUtils.isFrontendBackendSeparate ? okUtils.baseUrl + url : url,
            type: type || "get",
            data: params || {},
            dataType: "json",
            beforeSend: function () {
               if (load) {
                  loadIndex = layer.load(0, {shade: 0.3});
               }
            },
            success: function (data) {
               if (data.code == 0) {
                  // 业务正常
                  deferred.resolve(data)
               } else {
                  // 业务异常
                  layer.msg(data.msg, {icon: 7, time: 2000});
                  deferred.reject("okUtils.ajax warn: " + data.msg);
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
      /**
       * 主要用于针对表格批量操作操作之前的检查
       * @param table
       * @returns {string}
       */
      tableBatchCheck: function (table) {
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
      tableSuccessMsg: function (content) {
         layer.msg(content, {icon: 1, time: 1000}, function () {
            // 刷新当前页table数据
            $(".layui-laypage-btn")[0].click();
         });
      },
      /**
       * sessionStorage 二次封装
       */
      session: function (name, value) {
         if (value) { /**设置*/
            if (typeof value == "object") {
               sessionStorage.setItem(name, JSON.stringify(value));
            } else {
               sessionStorage.setItem(name, value);
            }
         } else if (null !== value) {
            /**获取*/
            let val = sessionStorage.getItem(name);
            try {
               val = JSON.parse(val);
               return val;
            } catch (err) {
               return val;
            }
         } else { /**清除*/
            return sessionStorage.removeItem(name);
         }
      },
      /**
       * localStorage 二次封装
       */
      local: function (name, value) {
         if (value) { /**设置*/
            if (typeof value == "object") {
               localStorage.setItem(name, JSON.stringify(value));
            } else {
               localStorage.setItem(name, value);
            }
         } else if (null !== value) {
            /**获取*/
            let val = localStorage.getItem(name);
            try {
               val = JSON.parse(val);
               return val;
            } catch (err) {
               return val;
            }
         } else { /**清除*/
            return localStorage.removeItem(name);
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
         date = date || new Date();
         fmt = fmt || "yyyy年M月s日";
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
      },
      number: {
         /**
          * 判断是否为一个正常的数字
          * @param num
          */
         isNumber: function (num) {
            if (num && !isNaN(num)) {
               return true;
            }
            return false;
         },
         /**
          * 判断一个数字是否包括在某个范围
          * @param num
          * @param begin
          * @param end
          */
         isNumberWith: function (num, begin, end) {
            if (this.isNumber(num)) {
               if (num >= begin && num <= end) {
                  return true;
               }
               return false;
            }
         },
      },

   };
   exprots("okUtils", okUtils);
});
