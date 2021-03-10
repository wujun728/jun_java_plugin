$(function() {

  if (currentPage == 'console') {
    // 控制台页面

    // 暂停
    $("#pause").click(function(event) {
      var name = $(this).attr("data-name");
      var group = $(this).attr("data-group");
      trigger(name, group, 1);
    });

    // 恢复
    $("#resume").click(function(event) {
      var name = $(this).attr("data-name");
      var group = $(this).attr("data-group");
      trigger(name, group, 2);
    });

    // 删除
    $("#remove").click(function(event) {
      var name = $(this).attr("data-name");
      var group = $(this).attr("data-group");
      trigger(name, group, 3);
    });

  } else if (currentPage == 'add') {
    // 新增页面

    $("#timeForm [name='startTime'").datetimepicker({
      timeFormat: "HH:mm",
      dateFormat: "yy-mm-dd"
    });
    $("#timeForm [name='endTime'").datetimepicker({
      timeFormat: "HH:mm",
      dateFormat: "yy-mm-dd"
    });

    $("#cronExpressionBtn").click(function(event) {
      var name = $("#cronExpressionForm [name='triggerName']").val();
      if (name == undefined || name == null || name == '') {
        showMessage("请输入Trigger名称.");
        return;
      }
      var cronExpression = $("#cronExpressionForm [name='cronExpression']").val();
      if (cronExpression == undefined || cronExpression == null || cronExpression == '') {
        showMessage('请输入Cron表达式.');
        return;
      }
      $("#cronExpressionForm").submit();
    });

    $("#frequencyBtn").click(function(event) {
      var name = $("#frequencyForm [name='triggerName']").val();
      if (name == undefined || name == null || name == '') {
        showMessage('请输入Trigger名称.');
        return;
      }
      var intervalTime = $("#frequencyForm [name='intervalTime']").val();
      if (intervalTime == undefined || intervalTime == null || intervalTime == '') {
        showMessage('请输入执行间隔时间.');
        return;
      }
      $("#frequencyForm").submit();
    });

    $("#timeBtn").click(function(event) {
      var name = $("#timeForm [name='triggerName']").val();
      if (name == undefined || name == null || name == '') {
        showMessage('请输入Trigger名称.');
        return;
      }

      var startTime = $("#timeForm [name='startTime']").val();
      if (startTime == undefined || startTime == null || startTime == '') {
        showMessage('请选择开始时间.');
        return;
      }

      var endTime = $("#timeForm [name='endTime']").val();
      if (endTime == undefined || endTime == null || endTime == '') {
        showMessage('请选择结束时间.');
        return;
      }
      $("#timeForm").submit();
    });

  }
});

function trigger(name, group, flag) {

  // flag： 1=暂停，2=恢复，3=删除
  console.log(name, group, flag);
  $.ajax({
    type: "post",
    url: _ctx + "/job/trigger",
    dataType: 'json',
    data: {
      "name": name,
      "group": group,
      "flag": flag
    },
    success: function(data) {
      if (data) {
        showMessage("操作成功!");
      } else {
        showMessage("操作失败!");
      }
      window.location.href = _ctx + "/job/index";
    },
    error: function(data, status, e) { // 服务器响应失败时的处理函数
      showMessage("操作失败!");
      window.location.href = _ctx + "/job/index";
    }
  });
}

function showMessage(message) {
  var div = "";
  div += "<div title='提示'>";
  div += "<p>" + message + "</p>";
  div += "</div>";
  $(div).dialog();
}
