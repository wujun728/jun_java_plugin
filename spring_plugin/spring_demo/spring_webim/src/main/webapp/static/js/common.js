;
(function($, window, document, undefined) {

  var j_util = window.j_util = {};

  j_util.queryPaging = function() {

    $(".paging").click(function(event) {
      // get Parameter
      var totlePage = j_util.parseInt($("#totlePage").val());
      var index = j_util.parseInt($("#currentPageIndex").val());

      var pageIndex = 1;

      // 当前页==最大页，不能跳转
      // 第一页时，不能向上一页

      if ($(this).hasClass("pagingIndex")) {
        // 指定页面跳转
        pageIndex = j_util.parseInt($("#pagingIndex").val());
        if (pageIndex >= 1 && pageIndex <= totlePage) {
          $("#pIndex").val(pageIndex);
          $("#pagingFrom").submit();
        } else {
          alert("请输入合法有效的页码。");
          return false;
        }
      } else {

        pageIndex = j_util.parseInt($(this).attr("data-pageIndex"));

        if (index == totlePage && pageIndex == totlePage) {
          // 若当前页为最后一页，则不能“下一页”
          return false;
        }
        if (index == pageIndex && index == 1) {
          // 当前为第一页，不能再“上一页”
          return false;
        }

        // 不合法
        if (pageIndex > totlePage) { return false; }

        if (pageIndex <= totlePage && pageIndex >= 1) {
          $("#pIndex").val(pageIndex);
          $("#pagingFrom").submit();
        }

      }

    });

  };

  // 校验文本框内容是否为空,处理空格情况,检验通过=true,检验没通过=false
  j_util.validationText = function(text) {
    if ($.trim(text) == null || $.trim(text) == "") {
      return false;
    } else {
      return true;
    }
  };

  // 计算中英文客串长度
  j_util.lengthOf = function(str) {
    var totalCount = 0;
    for (var i = 0; i < str.length; i++) {
      var c = str.charCodeAt(i);
      if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
        totalCount++;
      } else {
        totalCount += 2;
      }
    }
    return totalCount;
  };

  // 字符转Integer
  j_util.parseInt = function(str) {
    if (str != undefined && $.trim(str) != "") {
      return window.parseInt(str);
    } else {
      return 0;
    }
  };

  // 生成随机数
  j_util.getRandomNum = function() {
    var a = {};
    var b;
    while (true) {
      b = Math.random().toString().substr(2, 7) / 100;
      if (b.toString().length === 8 && a[b] === undefined) { return a[b] = b; }
    }
  };

  // jQuery 定位让body的scrollTop等于pos的top，就实现了滚动
  j_util.scrollOffset = function(scroll_offset) {
    $("body,html").animate({
      scrollTop: scroll_offset.top - 70
    }, 0);
  };

})(jQuery, window, document);
