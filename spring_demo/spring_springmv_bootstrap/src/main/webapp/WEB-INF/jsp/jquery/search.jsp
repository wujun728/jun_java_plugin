<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
<title>ajax file upload</title>
<style type="text/css">
.autocomplete-suggestions {
	border: 1px solid #999;
	background: #FFF;
	overflow: auto;
}

.autocomplete-suggestion {
	padding: 5px 5px;
	white-space: nowrap;
	overflow: hidden;
	font-size: 22px
}

.autocomplete-selected {
	background: #F0F0F0;
}

.autocomplete-suggestions strong {
	font-weight: bold;
	color: #3399FF;
}
</style>
<script type="text/javascript" src="<c:url value='/static/jquery/plugins/js/jquery.autocomplete.min.js'/>"></script>
<script type="text/javascript">
  (function($) {
    $(document).ready(function() {

      $('#w-input-search').autocomplete({
        serviceUrl: '/jq/getTags',
        paramName: "tagName",
        delimiter: ",",
        transformResult: function(response) {
          return {
            suggestions: $.map($.parseJSON(response), function(item) {
              return {
                value: item.tagName,
                data: item.id
              };
            })
          };
        }
      });

    });
  })(jQuery);
</script>
</head>
<body>
	<div class="container">
		<div class="row">
			<input type="text" id="w-input-search" value="">
		</div>
	</div>
</body>
</html>
