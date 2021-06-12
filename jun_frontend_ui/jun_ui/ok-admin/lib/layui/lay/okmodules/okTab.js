var $;
var config;
String.prototype.format = function () {
   //字符串占位符
   //eg: var str1 = "hello {0}".format("world");
   if (arguments.length == 0) return this;
   var param = arguments[0];
   var s = this;
   if (typeof (param) == 'object') {
      for (var key in param) {
         s = s.replace(new RegExp("\\{" + key + "\\}", "g"), param[key]);
      }
      return s;
   } else {
      for (var i = 0; i < arguments.length; i++) {
         s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
      }
      return s;
   }
};

/**打开缓存中的tabMenu**/
let OpenTabMenuFun = function ($, callback) {
   if(config && config.isTabMenu){
      var tabMenu = sessionStorage.getItem("tabMenu");//已经打开的tab页面
      if (tabMenu) {
         tabMenu = JSON.parse(tabMenu);
         $("#tabTitle").html(tabMenu.tabTitle);
         $("#tabContent").html(tabMenu.tabContent);
         if (typeof callback == 'function') {
            callback();
         }
      }
   }

};

/**删除缓存中的tabMenu**/
let removeTabMenu = function (okTab, callback) {
   sessionStorage.removeItem("tabMenu");
   sessionStorage.removeItem("notice");
   sessionStorage.removeItem("lay-id");
   if (typeof callback == "function") {
      callback(okTab);
   }
};

/**存储打开的tabMenu**/
function saveTabMenuFun($) {
   if(config && config.isTabMenu) {
      let tabTitle = $("<div>" + $("#tabTitle").html() + "</div>");
      tabTitle.find("i.layui-tab-close").remove();
      let tabMenu = JSON.stringify({
         tabTitle: tabTitle.html(),
         tabContent: $("#tabContent").html()
      });
      sessionStorage.setItem('tabMenu', tabMenu);
   }
}

layui.define(["element", "jquery", "okUtils"], function (exports) {
   var layui = parent.layui || layui;
   var okUtils = layui.okUtils;
   $ = layui.jquery;
   var element = layui.element,
      layer = layui.layer;
   config = okUtils.local("okConfig") || window.okConfig || {};

   if(!config.isTabMenu){
      okUtils.session("tabMenu", null);
      okUtils.session("lay-id", null);
   }

   okTab = function () {
      this.tabConfig = {
         openTabNum: 30, //最大可打开窗口数量默认30
         tabFilter: "ok-tab", //添加窗口的filter
         url: "", //获取菜单的接口地址
         data: [],//菜单数据列表(如果传入了url则data无效)
         parseData: ''//这是一个方法处理url请求地址的返回值(该方法必须提供一个返回值)
      }
   };
   /**
    * 导航初始化的操作(只执行一次)
    * @param option 配置tabConfig参数
    * @returns {okTab}
    */
   okTab.prototype.init = function (option) {
      var _this = this; //这个this就是代表okTab（因为prototype的原因）
      $.extend(true, _this.tabConfig, option); //函数用于将一个或多个对象的内容合并到目标对象。http://www.runoob.com/jquery/misc-extend.html
      this.tabDelete(); //关闭导航页的操作
      this.tab(); //tab导航切换时的操作
      return _this;
   };

   //生成左侧菜单
   var temp = "";
   okTab.prototype.navBar = function (strData) {
      var data;
      if (typeof (strData) == "string") {
         var data = JSON.parse(strData); //有可能是字符串，转换一下
      } else {
         data = strData || [];
      }
      var ulItem = '';
      for (var i = 0; i < data.length; i++) {
         temp = "";
         if (data[i].isCheck) {
            ulItem += '<li class="layui-nav-item layui-this">';
         } else if (data[i].spread) {
            ulItem += '<li class="layui-nav-item layui-nav-itemed">';
         } else {
            ulItem += '<li class="layui-nav-item">';
         }
         createMenu(data[i], (i + 1).toString());
         ulItem += temp;
         ulItem += '</li>';
      }
      return ulItem;
   };

   /**
    * 递归生成菜单
    * @param data 数据
    * @param tabID 设置lay-id使用
    */
   function createMenu(data, tabID) {
      if (data.children != undefined && data.children.length > 0) {
         temp += '<a>';
         if (data.icon != undefined && data.icon != '') {
            if (!data.fontFamily || data.fontFamily.indexOf("layui-icon") >= 0) {
               if (data.icon.indexOf("&#") >= 0) {
                  temp += ('<i class="layui-icon">{0}</i>').format(data.icon);
               } else {
                  temp += ('<i class="layui-icon {0}"></i>').format(data.icon);
               }
            } else if (data.fontFamily) {
               if (data.icon.indexOf("&#") >= 0) {
                  temp += ('<i class="{0}">{1}</i>').format(data.fontFamily, data.icon);
               } else {
                  temp += ('<i class="{0} {1}"></i>').format(data.fontFamily, data.icon);
               }
            }
         }
         temp += '<cite>' + data.title + '</cite>';
         temp += '<span class="layui-nav-more"></span>';
         temp += '</a>';
         temp += '<dl class="layui-nav-child">';
         for (var i = 0; i < data.children.length; i++) {
            temp += '<dd>';
            var lay_id = tabID + "-" + (i + 1);
            createMenu(data.children[i], lay_id);
            temp += '</dd>';
         }
         temp += "</dl>";
      } else {
         var isClose = data.isClose === false ? "false" : "true";
         if (data.target) {
            temp += ('<a lay-id="{0}" data-url="{1}" target="{2}" is-close="{3}">').format(tabID, data.href, data.target, isClose);
         } else {
            temp += ('<a lay-id="{0}" data-url="{1}" is-close="{2}">').format(tabID, data.href, isClose);
         }
         if (data.icon != undefined && data.icon != '') {
            if (!data.fontFamily || data.fontFamily.indexOf("layui-icon") >= 0) {
               if (data.icon.indexOf("&#") >= 0) {
                  temp += ('<i class="layui-icon">{0}</i>').format(data.icon);
               } else {
                  temp += ('<i class="layui-icon {0}"></i>').format(data.icon);
               }
            } else if (data.fontFamily) {
               if (data.icon.indexOf("&#") >= 0) {
                  temp += ('<i class="{0}">{1}</i>').format(data.fontFamily, data.icon);
               } else {
                  temp += ('<i class="{0} {1}"></i>').format(data.fontFamily, data.icon);
               }
            }
         }
         temp += ('<cite>{0}</cite></a>').format(data.title);

      }
   }

   /**
    * 定位tab位置
    * @param superEle 父级元素
    * @param ele 当前tab
    */
   okTab.prototype.positionTab = function (contEle, ele) {

      var superEle = $(".ok-tab");//父级元素
      contEle = contEle ? $(contEle) : $("#tabTitle");//(contEle);//tab内容存放的父元素
      ele = ele ? $(ele) : $("#tabTitle li.layui-this");//当前的tab

      var menuSet = $("#navBar a[lay-id]");//获取所有菜单集合
      var thatLayId = ele.attr('lay-id');

      var contWidth = contEle.width(),//父级元素宽度
         superWidth = parseInt(superEle.width()) - 40 * 3,//可显示的宽度
         subWidth = ele.outerWidth(),//当前元素宽度
         elePrevAll = ele.prevAll(),//左边的所有同级元素
         leftWidth = 0,//左边距离
         postLeft = Math.abs(contEle.position().left);//当前移动的位置
      var maxMoveWidth = contWidth - superWidth;//最大可移动的宽度

      /*console.log("maxMoveWidth：" + maxMoveWidth);
      console.log("superWidth：" + superWidth);
      console.log("contWidth：" + contWidth);*/
      // console.log(contEle);
      for (let i = 0; i < elePrevAll.length; i++) {
         leftWidth += $(elePrevAll[i]).outerWidth() * 1;
      }
      if (contWidth > superWidth) {
         /**
          * 移动tab栏的位置
          */
         var showPost = leftWidth - postLeft;//当前点击的元素位置（显示区域的位置）
         var halfPlace = parseInt(superWidth / 2);//可显示的宽度的一半
         var tempMove = 0;
         if (halfPlace < showPost) {//从右往左移动
            tempMove = leftWidth - subWidth;//预留一部分距离
            if (tempMove > maxMoveWidth) {//当前移动的距离超过最大可移动的宽度
               tempMove = maxMoveWidth;
            }
            contEle.animate({
               left: -tempMove
            }, 50);
         } else {//从左往右移动
            tempMove = leftWidth - halfPlace;//预留一部分距离
            if (tempMove < 0) {
               tempMove = 0;
            }
            contEle.animate({
               left: -tempMove
            }, 50);
         }
      } else {
         contEle.animate({
            left: 0
         }, 50);
      }

      /**
       * 左侧菜单的样式和多级菜单的展开
       */
      $("#navBar").find("li,dd").removeClass("layui-this").removeClass("layui-nav-itemed");//关闭所有展开的菜单
      $("#navBar > li dl.layui-nav-child").removeAttr('style');
      for (let i = 0; i < menuSet.length; i++) {
         if ($(menuSet[i]).attr('lay-id') == thatLayId) {
            $(menuSet[i]).parents("dd").addClass("layui-nav-itemed");
            $(menuSet[i]).parents("li").addClass("layui-nav-itemed");
            $(menuSet[i]).parent().removeClass("layui-nav-itemed").addClass("layui-this");
            break;
         }
      }
   };

   // 点击导航页的操作
   okTab.prototype.tab = function (e) {
      var that = this;
      var filter = this.tabConfig.tabFilter;
      // "hello {0}".format("world");
      //`tab(${filter})`
      element.on("tab({0})".format(filter), function (data) {
         if(config.isTabRefresh){ /**切换刷新*/
            $(".ok-tab-content .layui-show").find("iframe")[0].contentWindow.location.reload(true);
         }
         var index = data.index;//点击的某个tab索引
         config = okUtils.local("okConfig") || okConfig || {};
         sessionStorage.setItem('lay-id', this.getAttribute('lay-id'));
         var elSuper = $(".ok-tab"),//视窗元素
            elMove = $(".ok-tab-title"),//移动的元素
            elTabs = $(".ok-tab-title li"),//所有已存在的tab集合
            thatElem = $(elTabs[index]);//当前元素
         saveTabMenuFun($);
         that.positionTab(elMove, thatElem);
      });
   };

   //删除tab页的操作（此处为点击关闭按钮的操作）
   okTab.prototype.tabDelete = function () {
      var that = this;
      var filter = this.tabConfig.tabFilter;
      element.on("tabDelete({0})".format(filter), function (data) {
         /**保存展开的tab**/
         saveTabMenuFun($);
      });
   };

   //删除缓存中的tab
   okTab.prototype.removeTabStorage = function (callback) {
      removeTabMenu(this, callback);
   };

   //添加tab页
   okTab.prototype.tabAdd = function (_thisa) {
      var that = this;
      var _this = $(_thisa).clone(true);//拷贝dom（js： _this.cloneNode(true) ）

      var openTabNum = that.tabConfig.openTabNum;
      var tabFilter = that.tabConfig.tabFilter;
      var url = _this.attr("data-url");//选项卡的页面路径
      var tabId = _this.attr("lay-id");//选项卡ID
      var thatTabNum = $('.ok-tab-title > li').length;//当前已经打开的选项卡的数量
      var iframes = $(".ok-tab-content iframe");
      var isClose = _this.attr('is-close') || "true";

      _this.prepend("<strong style='display: none;' is-close=" + isClose + " lay-id=" + tabId + "></strong>");

      if (_this.children("i").length < 1) {
         _this.prepend("<i class='layui-icon'></i>");
      }
      if (_this.attr("target") == "_blank") {
         window.location.href = _this.attr("data-url");
      } else if (url != undefined) {
         var html = _this.html();
         // 去重复选项卡
         /**
          * 这里还有一种情况就是同一选项卡页面路径不同（这个是多余的没必要了，页面不同，跳转的选项卡也应该新增）
          */
         for (var i = 0; i < thatTabNum; i++) {
            if ($('.ok-tab-title > li').eq(i).attr('lay-id') == tabId) {
               var iframeParam = (iframes[i].src).split("?")[1];
               var urlParam = url.split("?")[1];
               if (urlParam == iframeParam) {
                  element.tabChange(tabFilter, tabId);
               } else {
                  //参数不一样的时候刷新并且更换参数
                  iframes[i].contentWindow.location.reload(true);
                  iframes[i].src = url;
                  element.tabChange(tabFilter, tabId);
               }
               if (event) {
                  event.stopPropagation();
               }
               return;
            }
         }

         if (thatTabNum >= openTabNum) {
            layer.msg('最多只能同时打开' + openTabNum + '个选项卡哦。不然系统会卡的！');
            return;
         }
         var contentIframe = ("<iframe src='{0}' lay-id='{1}'" +
            "frameborder='0' scrolling='yes' width='100%' height='100%'>" +
            "</iframe>").format(url, tabId);
         element.tabAdd(tabFilter, {
            title: html,
            content: contentIframe,
            id: tabId
         });
         // 切换选项卡
         element.tabChange(tabFilter, tabId);
         this.navMove("rightmax");
      }
   };

   //重新对导航进行渲染(此处有个回调函数，主要用作渲染完成之后的操作)
   okTab.prototype.render = function (callback) {
      var _this = this;//data
      var _data = _this.tabConfig.data;
      if (_this.tabConfig.url) {
         $.get(_this.tabConfig.url, function (res) {
            _data = res;
            if (typeof _this.tabConfig.parseData == "function") {
               _data = _this.tabConfig.parseData(res);
            }
            $("#navBar").html(_this.navBar(_data));
            element.render('nav');
            $(window).resize(function () {
               $("#navBar").height($(window).height() - 245);
            });
            if (typeof callback == "function") {
               callback();
            }
            var lay_id = sessionStorage.getItem('lay-id');
            element.tabChange(_this.tabConfig.tabFilter, lay_id);
         });
         /**打开缓存中的tab**/
         OpenTabMenuFun($, function () {
            // var filter = _this.tabConfig.tabFilter;
            element.render("tab");
         });
      } else if ($.type(_data) == 'array') {
         if (_data.length < 1) {
            alert("菜单集合中无任何数据");
         }
         var _data = _this.tabConfig.data;
         $("#navBar").html(_this.navBar(_data));
         element.render('nav');
         $(window).resize(function () {
            $("#navBar").height($(window).height() - 245);
         });
         if (typeof callback == "function") {
            callback();
         }
      } else {
         alert("你的菜单配置有误请查看菜单配置说明");
      }
   };

   //刷新当前tab页
   okTab.prototype.refresh = function (_this, callback) {
      if (!($(_this).hasClass("refreshThis"))) {
         $(_this).addClass("refreshThis");
         $(".ok-tab-content .layui-show").find("iframe")[0].contentWindow.location.reload(true);
         if (typeof callback == "function") {
            callback(okTab);
         }
         setTimeout(function () {
            $(_this).removeClass("refreshThis");
         }, 2000)
      } else {
         layer.msg("客官请不要频繁点击哦！我会反应不过来的");
      }
   };

   /**
    * 关闭tab标签页操作
    * @param num 默认为1
    * 1：代表关闭当前标签页
    * 2：代表关闭其他标签页
    * 3：代表关闭所有标签页
    */
   okTab.prototype.tabClose = function (num) {
      num = num || 1;
      num = num * 1;//强制转换成数字
      var that = this;
      let openTabs = $('.ok-tab-title > li strong[lay-id]').not('strong[is-close=false]'),//获取已打开的tab元素(除开不会被关闭的导航)
         thatLayID = $('.ok-tab-title > li.layui-this strong[lay-id]').not('strong[is-close=false]').attr("lay-id") || '';//获取当前打开的tab元素ID(除开不会被关闭的导航)

      var filter = that.tabConfig.tabFilter;
      if (thatLayID.length < 1 && num == 1) {
         layer.msg("您不能关闭当前页哦 (๑╹◡╹)ﾉ");
         return;
      } else if (openTabs.length < 1) {
         layer.msg("您好！当前没有可关闭的窗口了 (๑╹◡╹)ﾉ");
         return;
      }
      switch (num) {
         case 1:
            element.tabDelete(filter, thatLayID);
            break;
         case 2:
            if (openTabs.length > 0) {
               openTabs.each(function (i, j) {
                  if ($(j).attr("lay-id") != thatLayID) {
                     element.tabDelete(filter, $(j).attr("lay-id"));
                  }
               });
               this.navMove('leftmax');
            } else {
               layer.msg("您好！当前没有可关闭的窗口了 (๑╹◡╹)ﾉ");
               return;
            }
            break;
         case 3:
            openTabs.each(function (i, j) {
               element.tabDelete(filter, $(j).attr("lay-id"));
            });
            this.navMove('leftmax');
            break;
      }
      element.render("tab", filter);
      /**保存展开的tab**/
      saveTabMenuFun($);
   };

   /**
    * 移动标签导航
    * @param moveId
    *      为left:往左边移动
    *      为right:往右边移动
    *      为rightmax:跑到最右边
    *      为leftmax:跑到最左边
    */
   okTab.prototype.navMove = function (moveId, that) {
      var superEle = $(".ok-tab"),//父级元素
         contEle = $(".ok-tab-title"),//tab内容存放的父元素
         superWidth = parseInt(superEle.width()) - 40 * 3,//可显示的宽度
         contWidth = parseInt(contEle.width()),//移动元素的总宽度
         elTabs = $(".ok-tab-title li"),//所有已存在的tab集合
         postLeft = contEle.position().left;//当前移动的位置

      /*elTabs.each(function (i, j) {
         moveWidth += $(j).outerWidth() * 1;
      });*/

      var movePost = moveId.toLowerCase().indexOf("left") < 0 ? -1 : 1;//移动方向
      var step = parseInt((superWidth * 0.25 < 20 ? 20 : superWidth * 0.25));//移动步长
      var moveLeft = postLeft + step * movePost;
      var moveWidth = contWidth - superWidth;
      if (contWidth > superWidth) {
         switch (moveId) {
            case 'left':
               if (moveLeft >= step) {
                  layer.tips('已到最左边啦', that, {
                     tips: [1, '#000'],
                     time: 2000
                  });
                  moveLeft = 0;
               }
               break;
            case 'leftmax':
               if (moveLeft >= step) {
                  layer.tips('已到最左边啦', that, {
                     tips: [1, '#000'],
                     time: 2000
                  });
                  moveLeft = 0;
               }
               break;
            case 'right':
               if (superWidth + Math.abs(moveLeft) >= contWidth + step) {
                  layer.tips('已到最右边啦', that, {
                     tips: [1, '#000'],
                     time: 2000
                  });
                  moveLeft = -moveWidth;
               }
               if (superWidth + Math.abs(moveLeft) >= contWidth) {
                  moveLeft = -moveWidth;
               }
               break;
            case 'rightmax':
               if (superWidth + Math.abs(postLeft) >= contWidth + step) {
                  layer.tips('已到最右边啦', that, {
                     tips: [1, '#000'],
                     time: 2000
                  });
               }
               moveLeft = -moveWidth;
         }
         if (moveLeft > 0) {
            moveLeft = 0;
         }
         contEle.animate({
            left: moveLeft
         }, 50);
      } else {
         contEle.animate({
            left: 0
         }, 50);
      }
   };

   exports("okTab", function (option) {
      if (parent.objOkTab) {
         return parent.objOkTab;
      } else {
         return new okTab().init(option);
      }
   });


});

