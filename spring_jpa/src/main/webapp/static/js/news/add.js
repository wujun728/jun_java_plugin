;
(function($, window, document, undefined) {

  var news = {};

  // 绑定时间选择事件
  news.bindPlugins = function() {
    $("#newsTime").datepicker();
  };

  // 提交前的校验
  news.checkAll = function() {
    var flag = true;

    var $msgObj = $("<span class='label label-danger'></span>");

    if (!(j_util.validationText($("#title").val()))) {
      $("#title").parent().after($msgObj.clone().text("*请填写标题。"));
      flag = false;
    }
    if (!(j_util.validationText($("#description").val()))) {
      $("#description").parent().after($msgObj.clone().text("*请填写内容。"));
      flag = false;
    }
    if (!(j_util.validationText($("#address").val()))) {
      $("#address").parent().after($msgObj.clone().text("*请填写地址。"));
      flag = false;
    }
    if (!(j_util.validationText($("#newsTime").val()))) {
      $("#newsTime").parent().after($msgObj.clone().text("*请选择发生时间。"));
      flag = false;
    }

    return flag;
  };

  // 保存数据
  news.save = function() {

    $("#saveBtn").click(function(event) {
      event.stopPropagation();
      if (news.checkAll()) {
        $(this).prop("disabled", "disabled");
        $("#newsForm").submit();
      }
    });
  };

  $(function() {
    news.bindPlugins();
    news.save();
  });

})(jQuery, window, document);