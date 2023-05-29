/**
 * 指定位置显示$.messager.show
 * options $.messager.show的options
 * param = {left,top,right,bottom}
 */
$.extend($.messager, {
  showBySite: function(options, param) {
    var site = $.extend({
      left: "",
      top: "",
      right: 0,
      bottom: -document.body.scrollTop - document.documentElement.scrollTop
    },
    param || {});
    var win = $("body > div .messager-body");
    if (win.length <= 0) $.messager.show(options);
    win = $("body > div .messager-body");
    win.window("window").css({
      left: site.left,
      top: site.top,
      right: site.right,
      zIndex: $.fn.window.defaults.zIndex++,
      bottom: site.bottom
    });
  }
});

var g_startT, g_endT; //debug
$.extend($.fn.tabs.methods, {
  /**
	 * tabs组件每个tab panel对应的小工具条绑定的事件没有传递事件参数 本函数修正这个问题
	 * 
	 * @param {[type]} jq [description]
	 */
  addEventParam: function(jq) {
    return jq.each(function() {
      var that = this;
      var headers = $(this).find('>div.tabs-header>div.tabs-wrap>ul.tabs>li');
      headers.each(function(i) {
        var tools = $(that).tabs('getTab', i).panel('options').tools;
        if (typeof tools != "string") {
          $(this).find('>span.tabs-p-tool a').each(function(j) {
            $(this).unbind('click').bind("click", {
              handler: tools[j].handler
            },
            function(e) {
              if ($(this).parents("li").hasClass("tabs-disabled")) {
                return;
              }
              e.data.handler.call(this, e);
            });
          });
        }
      })
    });
  },
  /**
	 * 加载iframe内容
	 * 
	 * @param {jq Object} jq [description]
	 * @param {Object} params
	 *            params.which:tab的标题或者index;params.iframe:iframe的相关参数
	 * @return {jq Object} [description]
	 */
  loadTabIframe: function(jq, params) {
    return jq.each(function() {
      var $tab = $(this).tabs('getTab', params.which);
      if ($tab == null) return;
      var $tabBody = $tab.panel('body');

      // 销毁已有的iframe
      var $frame = $('iframe', $tabBody);
      if ($frame.length > 0) {
        try { // 跨域会拒绝访问，这里处理掉该异常
          $frame[0].contentWindow.document.write('');
          $frame[0].contentWindow.close();
          $frame.remove();
        } catch(e) {}
        if ($.browser.msie) {
          CollectGarbage();
        }
      }

      $tabBody.css({
        'overflow': 'hidden',
        'position': 'relative'
      });
      var $mask = $('<div style="position:absolute;z-index:2;width:100%;height:100%;background:#ccc;z-index:1000;opacity:0.3;filter:alpha(opacity=30);"><div>').appendTo($tabBody);
      var $maskMessage = $('<div class="mask-message" style="z-index:3;width:auto;height:16px;line-height:16px;position:absolute;top:50%;left:50%;margin-top:-20px;margin-left:-92px;border:2px solid #d4d4d4;padding: 12px 5px 10px 30px;background: #ffffff url(\''+top.sysPath+'/images/loading.gif\') no-repeat  scroll 5px center;">' + (params.iframe.message || '稍等，系统正在努力为您加载中...') + '</div>').appendTo($tabBody);
      var $containterMask = $('<div style="position:absolute;width:100%;height:100%;z-index:1;background:#fff;"></div>').appendTo($tabBody);
      var $containter = $('<div style="position:absolute;width:100%;height:100%;z-index:0;"></div>').appendTo($tabBody);

      var iframe = document.createElement("iframe");
      iframe.src = params.iframe.src;
      iframe.frameBorder = params.iframe.frameBorder || 0;
      iframe.height = params.iframe.height || '100%';
      iframe.width = params.iframe.width || '100%';

      if (iframe.attachEvent) {
        iframe.attachEvent("onload",
        function() {
        g_endT = new Date().getTime();
        page_debug.link=iframe.src;
        page_debug.loadTime=(g_endT -g_startT) / 1000;//debug
        g_page_debug();
          $([$mask[0], $maskMessage[0]]).fadeOut(params.iframe.delay || 'slow',
          function() {
            $(this).remove();
            if ($(this).hasClass('mask-message')) {
              $containterMask.fadeOut(params.iframe.delay || 'slow',
              function() {
                $(this).remove();
              });
            }
          });
        });
      } else {
        iframe.onload = function() {
        	g_endT = new Date().getTime();
        	 page_debug.link=iframe.src;
             page_debug.loadTime=(g_endT -g_startT) / 1000;//debug
             g_page_debug();
          $([$mask[0], $maskMessage[0]]).fadeOut(params.iframe.delay || 'slow',
          function() {
            $(this).remove();
            if ($(this).hasClass('mask-message')) {
              $containterMask.fadeOut(params.iframe.delay || 'slow',
              function() {
                $(this).remove();
              });
            }
          });
        };
      }
      $containter[0].appendChild(iframe);
    });
  },
  /**
 * 增加iframe模式的标签页
 * 
 * @param {[type]} jq [description]
 * @param {[type]} params [description]
 */
  addIframeTab: function(jq, params) {
	g_startT = new Date().getTime();//debug
    return jq.each(function() {
      if (params.tab.href) {
        delete params.tab.href;
      }
      $(this).tabs('add', params.tab);
      $(this).tabs('loadTabIframe', {
        'which': params.tab.title,
        'iframe': params.iframe
      });
    });
  },
  /**
	 * 更新tab的iframe内容
	 * 
	 * @param {jq Object} jq [description]
	 * @param {Object} params [description]
	 * @return {jq Object} [description]
	 */
  updateIframeTab: function(jq, params) {
	 g_startT=new Date().getTime();//debug
    return jq.each(function() {
      params.iframe = params.iframe || {};
      if (!params.iframe.src) {
        var $tab = $(this).tabs('getTab', params.which);
        if ($tab == null) return;
        var $tabBody = $tab.panel('body');
        var $iframe = $tabBody.find('iframe');
        if ($iframe.length === 0) return;
        $.extend(params.iframe, {
          'src': $iframe.attr('src')
        });
      }
      $(this).tabs('loadTabIframe', params);
    });
  }
});

(function($) {
  function S4() {
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
  }
  function CreateIndentityWindowId() {
    return "UUID-" + (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
  }
  function destroy(target) {
    $(target).dialog("destroy");
  }
  function getWindow(target) {
    if (typeof target == "string") {
      return document.getElementById(target);
    } else {
      return $(target).closest(".window-body");
    }
  }

  function getFrameWindow(frame) {
    return frame && typeof(frame) == 'object' && frame.tagName == 'IFRAME' && frame.contentWindow;
  }

  function getTpl(id, ctxjq) {
    try {
      var tpl = ctxjq("#" + id);
      if (tpl.length > 0) {
        var TEMPLATE = $.trim(tpl.html()).replace(/[\r\n]/g, "").replace(/\s+/g, " ");
        var reg = new RegExp("^(<!--)(.*)(-->)$");
        var match = TEMPLATE.match(reg);
        return match[2];
      }
    } catch(e) {
      throw new Error("获取模板错误！");
    }
  }

  function ajaxSuccess(dialog, options, rsp) {
    var dgContent = dialog.find("div.dialog-content");
    if (options.isIframe && !rsp) {
      dgContent.css("overflow", "hidden");
      var iframe = document.createElement("iframe");
      iframe.src = options.url;
      iframe.style.width = "100%";
      iframe.style.height = "100%";
      iframe.style.border = "none";
      if (iframe.attachEvent) {
        iframe.attachEvent("onload",
        function() {
          options.onComplete.call(dialog, window.top.jQuery, iframe);
        });
      } else {
        iframe.onload = function() {
          options.onComplete.call(dialog, window.top.jQuery, iframe);
        };
      }
      dgContent[0].appendChild(iframe);
    } else {
      dgContent.html(rsp);
      $.parser.parse(dialog);
      options.onComplete.call(dialog, window.top.jQuery);
    }

    if (!options.height) {
      dialog.dialog("center");
    }
  }

  $.window = function(options, jq) {

    if (!options.url && !options.contents && !options.tplRef) {
      top.$.messager.alert("提示", "缺少必要参数!(url or contents or tplRef)");
      return false;
    }
    // 获取模板内容
    if (options.tplRef) {
      options.contents = getTpl(options.tplRef, jq || window.jQuery);
    }

    var windowId = CreateIndentityWindowId();
    if (options.winId) {
      windowId = options.winId;
    } else {
      options.winId = windowId;
    }
    options = $.extend({},
    $.window.defaults, options || {});
    if (options.isMax) {
      options.draggable = false;
      options.closed = true;
    }
    var dialog = $('<div/>');
    if (options.target != 'body') {
      options.inline = true;
    }
    if (options.target == "top") {
      options.target = top.document.body;
    } else if (options.target == "parent") {
      options.target = parent.document.body;
    } else if (options.target == "self") {
      options.target = self.document.body;
    } else {
      options.target = top.document.body;
    }
    dialog.appendTo($(options.target));
    dialog.dialog($.extend({},
    options, {
      onClose: function() {
        if (typeof options.onClose == "function") {
          options.onClose.call(dialog, $);
        }
        destroy(this);
      }
    }));
    if (options.align) {
      var w = dialog.closest(".window");
      switch (options.align) {
      case "right":
        dialog.dialog("move", {
          left: w.parent().width() - w.width() - 10
        });
        break;
      case "tright":
        dialog.dialog("move", {
          left: w.parent().width() - w.width() - 10,
          top: 0
        });
        break;
      case "bright":
        dialog.dialog("move", {
          left: w.parent().width() - w.width() - 10,
          top: w.parent().height() - w.height() - 10
        });
        break;
      case "left":
        dialog.dialog("move", {
          left: 0
        });
        break;
      case "tleft":
        dialog.dialog("move", {
          left: 0,
          top: 0
        });
        break;
      case "bleft":
        dialog.dialog("move", {
          left: 0,
          top: w.parent().height() - w.height() - 10
        });
        break;
      case "top":
        dialog.dialog("move", {
          top: 0
        });
        break;
      case "bottom":
        dialog.dialog("move", {
          top: w.parent().height() - w.height() - 10
        });
        break;
      }
    }

    if (options.isMax) {
      dialog.dialog("maximize");
      dialog.dialog("open");
    }

    if (options.contents) {
      ajaxSuccess(dialog, options, options.contents);
    } else {
      if (!options.isIframe) {
        $.ajax({
          url: options.url,
          type: options.ajaxType || "POST",
          data: options.data == null ? "": options.data,
          success: function(rsp) {
            ajaxSuccess(dialog, options, rsp);
          }
        });
      } else {
        ajaxSuccess(dialog, options);
      }
    }

    dialog.attr("id", windowId);

    dialog.destroy = function() {
      destroy(this);
    };

    return dialog;
  };

  // 一些工具方法
  $.window.util = {
    getTpl: getTpl,
    // 获取模板内容
    getFrameWindow: getFrameWindow
    // 根据iframe的dom对象获取iframe的window对象
  };

  $.window.defaults = $.extend({},
  $.fn.dialog.defaults, {
    url: '',
    data: '',
    contents: '',
    isIframe: false,
    tplRef: '',
    ajaxType: "POST",
    target: 'body',
    width: 400,
    collapsible: false,
    minimizable: false,
    maximizable: false,
    closable: true,
    modal: true,
    shadow: false,
    onComplete: function(topjQuery) {}
  });
})(jQuery);

/**
 * 扩展树表格级联勾选方法：
 * 
 * @param {Object} container
 * @param {Object} options
 * @return {TypeName}
 */
$.extend($.fn.treegrid.methods, {
  /**
			 * 级联选择
			 * 
			 * @param {Object} target
			 * @param {Object} param param包括两个参数: id:勾选的节点ID deepCascade:是否深度级联
			 * @return {TypeName}
			 */
  cascadeCheck: function(target, param) {
    var opts = $.data(target[0], "treegrid").options;
    if (opts.singleSelect) return;
    var idField = opts.idField; // 这里的idField其实就是API里方法的id参数
    var status = false; // 用来标记当前节点的状态，true:勾选，false:未勾选
    var selectNodes = $(target).treegrid('getSelections'); // 获取当前选中项
    for (var i = 0; i < selectNodes.length; i++) {
      if (selectNodes[i][idField] == param.id) status = true;
    }
    // 级联选择父节点
    selectParent(target[0], param.id, idField, status);
    selectChildren(target[0], param.id, idField, param.deepCascade, status);
    /**
				 * 级联选择父节点
				 * 
				 * @param {Object} target
				 * @param {Object} id 节点ID
				 * @param {Object} status 节点状态，true:勾选，false:未勾选
				 * @return {TypeName}
				 */
    function selectParent(target, id, idField, status) {
      var parent = $(target).treegrid('getParent', id);
      if (parent) {
        var parentId = parent[idField];
        if (status) $(target).treegrid('select', parentId);
        else $(target).treegrid('unselect', parentId);
        selectParent(target, parentId, idField, status);
      }
    }
    /**
				 * 级联选择子节点
				 * 
				 * @param {Object} target
				 * @param {Object} id 节点ID
				 * @param {Object} deepCascade 是否深度级联
				 * @param {Object} status 节点状态，true:勾选，false:未勾选
				 * @return {TypeName}
				 */
    function selectChildren(target, id, idField, deepCascade, status) {
      deepCascade = true;
      // 深度级联时先展开节点
      if (!status && deepCascade) $(target).treegrid('expand', id);
      // 根据ID获取下层孩子节点
      var children = $(target).treegrid('getChildren', id);
      for (var i = 0; i < children.length; i++) {
        var childId = children[i][idField];
        if (status) $(target).treegrid('select', childId);
        else $(target).treegrid('unselect', childId);
        selectChildren(target, childId, idField, deepCascade, status); // 递归选择子节点
      }
    }
  }
});

function log(info) {
  top.$.messager.showBySite({
    msg: info,
    timeout: 1200
  },
  {
    top: 60,
    left: $(top).width() / 2 - 150,
    bottom: ""
  });
}

function logt(info, time) {
  top.$.messager.showBySite({
    msg: info,
    timeout: time * 1000
  },
  {
    top: 60,
    left: $(top).width() / 2 - 150,
    bottom: ""
  });
}

(function($) {
  HashMap = function() {
    var index = 0;
    var content = '';
    var keyV = new Array();
    var valueV = new Array();
    // 向map中添加key，value键值对
    this.put = function(key, value) {
      if (key == undefined || key.Trim == '') {
        return;
      }
      if (value == undefined || value.Trim == '') {
        return;
      }
      if (content.indexOf(key) == -1) {
        keyV[index] = key;
        valueV[index++] = value;
        content += key + ';';
      } else {
        var contents = content.split(';');
        for (var i = 0; i < contents.length - 1; i++) {
          if (key == contents[i]) {
            valueV[i] = value;
            break;
          }
        }
      }
    };
    // 根据key获取value值
    this.get = function(key) {
      if (key == undefined || key.Trim == '') {
        return;
      }
      var contents = content.split(';');
      for (var i = 0; i < contents.length - 1; i++) {
        if (key == contents[i]) {
          return valueV[i];
        }
      }
      return '';
    };
    // 判断是否包含制定的key值
    this.containsKey = function() {
      if (key == undefined || key.Trim == '') {
        return false;
      }
      var contents = content.split(';');
      for (var i = 0; i < contents.length - 1; i++) {
        if (key == contents[i]) {
          return true;
        }
      }
      return false;
    };
    // 判断map是否为空
    this.isEmpty = function() {
      if (keyV.length == 0) {
        return true;
      } else {
        return false;
      }
    };
    // 获取该map的大小
    this.size = function() {
      return keyV.length;
    };
  };
})(jQuery);
// 释放内存
$.fn.panel.defaults = $.extend({},
$.fn.panel.defaults, {
  onBeforeDestroy: function() {
    var frame = $('iframe', this);
    if (frame.length > 0) {
      frame[0].contentWindow.document.write('');
      frame[0].contentWindow.close();
      frame.remove();
      if ($.browser.msie) {
        CollectGarbage();
      }
    }
  }
});

$.extend($.fn.combo, {
  editable: false
});

$.fn.combo.defaults.width = $.fn.combotree.defaults.width = 200;

/**
 * 时间对象的格式化
 */
Date.prototype.format = function(format) {
  /*
	 * format="yyyy-MM-dd hh:mm:ss";
	 */
  var o = {
    "M+": this.getMonth() + 1,
    "d+": this.getDate(),
    "h+": this.getHours(),
    "m+": this.getMinutes(),
    "s+": this.getSeconds(),
    "q+": Math.floor((this.getMonth() + 3) / 3),
    "S": this.getMilliseconds()
  }

  if (/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  }

  for (var k in o) {
    if (new RegExp("(" + k + ")").test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    }
  }
  return format;
}

/**
 * 获取链接参数
 */
$.getUrlParam = function(name) {
  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if (r != null) return unescape(r[2]);
  return null;
}

/**
 * 获取链接参数
 */
$.getParam = function(name,url) {
  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
  var r = url.split('?')[1].match(reg);
  if (r != null) return unescape(r[2]);
  return null;
}

//为了兼容IE8
if (!Array.prototype.forEach)
{
    Array.prototype.forEach = function(fun /*, thisp*/)
    {
        var len = this.length;
        if (typeof fun != "function")
            throw new TypeError();

        var thisp = arguments[1];
        for (var i = 0; i < len; i++)
        {
            if (i in this)
                fun.call(thisp, this[i], i, this);
        }
    };
}


Number.prototype.toFixed = function(d)
{
    var s=this+"";if(!d)d=0;
    if(s.indexOf(".")==-1)s+=".";s+=new Array(d+1).join("0");
    if (new RegExp("^(-|\\+)?(\\d+(\\.\\d{0,"+ (d+1) +"})?)\\d*$").test(s))
    {
        var s="0"+ RegExp.$2, pm=RegExp.$1, a=RegExp.$3.length, b=true;
        if (a==d+2){a=s.match(/\d/g); if (parseInt(a[a.length-1])>4)
        {
            for(var i=a.length-2; i>=0; i--) {a[i] = parseInt(a[i])+1;
            if(a[i]==10){a[i]=0; b=i!=1;} else break;}
        }
        s=a.join("").replace(new RegExp("(\\d+)(\\d{"+d+"})\\d$"),"$1.$2");
    }if(b)s=s.substr(1);return (pm+s).replace(/\.$/, "");} return this+"";
};   
