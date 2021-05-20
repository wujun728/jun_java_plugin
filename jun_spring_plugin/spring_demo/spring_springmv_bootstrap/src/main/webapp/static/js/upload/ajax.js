;
(function($, window, document, undefined) {

  $(function() {
    $("#ajaxupload-btn").click(function() {
      // loading();

      var file = document.getElementById("ajaxuploadfile").files[0];
      // 开始上传文件时显示一个图片,文件上传完成将图片隐藏
      // $("#loading").ajaxStart(function(){$(this).show();}).ajaxComplete(function(){$(this).hide();});
      // 执行上传文件操作的函数
      $.ajaxFileUpload({
        url: 'ajax',
        secureuri: false, // 是否启用安全提交,默认为false
        fileElementId: 'ajaxuploadfile', // 文件选择框的id属性s
        dataType: 'text', // 服务器返回的格式,可以是json或xml等
        success: function(data, status) { // 服务器响应成功时的处理函数
          console.log(data);
          $("#msg").text(data);
        },
        error: function(data, status, e) { // 服务器响应失败时的处理函数
          $('#msg').html(data);
        }
      });

    });

  });

  // function loading() {
  // $("#loading").ajaxStart(function() {
  // $(this).show();
  // }).ajaxComplete(function() {
  // $(this).hide();
  // });
  // }

})(jQuery, window, document);