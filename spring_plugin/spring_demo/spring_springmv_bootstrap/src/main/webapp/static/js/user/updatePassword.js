;
(function($, window, document, undefined) {

  var updateUtil = {};
  updateUtil.checkPassword = function() {
    var flag = true;

    var password = $.trim($("#password").val());
    var newPassword = $.trim($("#newPassword").val());
    var passwordAgain = $("#passwordAgain").val();
    $(".label-danger").remove();
    var $msgObj = $("<span class='label label-danger'></span>");
    if (!(j_util.validationText(password))) {
      $("#password").parent().after($msgObj.clone().text("*请输入密码。"));
      flag = false;
    }
    if (!(j_util.validationText(newPassword))) {
      $("#newPassword").parent().after($msgObj.clone().text("*请输入新密码。"));
      flag = false;
    }
    if (!(j_util.validationText(passwordAgain))) {
      $("#passwordAgain").parent().after($msgObj.clone().text("*请输入确认密码。"));
      flag = false;
    }
    if (newPassword != passwordAgain) {
      $("#passwordAgain").parent().after($msgObj.clone().text("*两次密码不一致。"));
      flag = false;
    }
    return flag;
  };

  updateUtil.update = function() {
    $("#updatePasswordBtn").click(function(event) {

      event.stopPropagation();
      if (updateUtil.checkPassword()) {
        if (window.confirm("  您确定要修改密码吗？ ")) {
          $("#updatePasswordForm").submit();
        }
      }
    });
  };

  $(function() {
    updateUtil.update();
  });

})(jQuery, window, document);