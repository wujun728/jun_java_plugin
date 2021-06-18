;
(function($, window, document, undefined) {

  function search() {

    $("#newsBody").empty();

    var keywords = $("#keywords").val();

    $.ajax({
      type: "post",
      url: _ctx + "/news/list",
      dataType: 'json',
      data: {
        "keywords": keywords
      },
      success: function(data) {
        var news;
        $.each(data, function(i, item) {
          news += "<tr>";
          news += "<td>" + item.title + "</td>";
          news += "<td>" + item.description + "</td>";
          news += "<td>" + item.newsTime + "</td>";
          news += "<td>" + item.createTime + "</td>";
          news += "<td>" + item.address + "</td>";
          news += "</tr>";
        });
        $("#newsBody").append(news);
      }
    });
  }

  $(function() {

    search();

    $("#searchBtn").click(function() {
      search();
    });

  });

})(jQuery, window, document);