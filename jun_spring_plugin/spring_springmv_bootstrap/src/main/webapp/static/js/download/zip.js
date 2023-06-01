;
(function($, undefined) {

  $(function() {

    $(".downloadzipfile").bind("click", function(event) {
      event.stopPropagation();
      var filepath = $(this).attr("data-file-path");
      $("#zippath").val(filepath);
      $("#downloadzip").submit();
    })
    
    $(".downloadfile").bind("click", function(event) {
      event.stopPropagation();
      var filepath = $(this).attr("data-file-path");
      $("#filepath").val(filepath);
      $("#downloadfile").submit();
    })

  });

})(jQuery);