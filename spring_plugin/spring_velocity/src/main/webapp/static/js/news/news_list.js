;
(function($, window, document, undefined) {

  function search() {

    $("#newsBody").empty();

    var keywords = $("#keywords").val();

    $("#newsBody").load(_ctx + "/news/list", {
      "keywords": keywords
    });
  }

  $(function() {

    search();

    $("#searchBtn").click(function() {
      search();
    });

  });

})(jQuery, window, document);