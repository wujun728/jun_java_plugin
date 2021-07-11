<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" />
<title>Example</title>
<!-- include libraries(jQuery, bootstrap) -->
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
<!-- include summernote css/js-->
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.2/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.2/summernote.js"></script>
</head>
<style>
/* Space out content a bit */
body {
  padding-top: 20px;
  padding-bottom: 20px;
}

/* Everything but the jumbotron gets side spacing for mobile first views */
.header, .marketing, .footer {
  padding-right: 15px;
  padding-left: 15px;
}

/* Custom page header */
.header {
  padding-bottom: 20px;
  border-bottom: 1px solid #e5e5e5;
}
/* Make the masthead heading the same height as the navigation */
.header h3 {
  margin-top: 0;
  margin-bottom: 0;
  line-height: 40px;
}

/* Custom page footer */
.footer {
  padding-top: 19px;
  color: #777;
  border-top: 1px solid #e5e5e5;
}

/* Customize container */
@media ( min-width : 768px) {
  .container {
    max-width: 900px;
  }
}

.container-narrow>hr {
  margin: 30px 0;
}

/* Main marketing message and sign up button */
.jumbotron {
  text-align: center;
  border-bottom: 1px solid #e5e5e5;
}

.jumbotron .btn {
  padding: 14px 24px;
  font-size: 21px;
}

/* Supporting marketing content */
.marketing {
  margin: 40px 0;
}

.marketing p+h4 {
  margin-top: 28px;
}

/* Responsive: Portrait tablets and up */
@media screen and (min-width: 768px) {
  /* Remove the padding we set earlier */
  .header, .marketing, .footer {
    padding-right: 0;
    padding-left: 0;
  }
  /* Space out the masthead */
  .header {
    margin-bottom: 30px;
  }
  /* Remove the bottom border on the jumbotron for visual effect */
  .jumbotron {
    border-bottom: 0;
  }
}
</style>
<body>
  <c:if test="${not empty message}">
    <div>
      <h2>${message}</h2>
    </div>
  </c:if>
  <div class="container">
    <div class="header clearfix">
      <nav>
        <ul class="nav nav-pills pull-right">
        </ul>
      </nav>
      <h3 class="text-muted">Summernote Image Upload Example</h3>
    </div>
    <div class="row marketing">
      <div class="col-lg-12">
        <div class="form-area">
          <form id="articleForm" role="form" action="/article" method="post">
            <br style="clear: both">
            <h3 style="margin-bottom: 25px;">Article Form</h3>
            <div class="form-group">
              <input type="text" class="form-control" id="subject" name="subject" placeholder="subject" required>
            </div>
            <div class="form-group">
              <textarea class="form-control" id="summernote" name="content" placeholder="content" maxlength="140" rows="7"></textarea>
            </div>
            <button type="submit" id="submit" name="submit" class="btn btn-primary pull-right">Submit Form</button>
          </form>
        </div>
      </div>
    </div>
    <div id="imageBoard">
      <ul>
        <c:forEach items="${files}" var="file">
          <li><img src="/image/${file}" width="480" height="auto"/></li>
        </c:forEach>
      </ul>
    </div>
  </div>
  <!-- /container -->
  <script type="text/javascript">
      $(document).ready(function() {
        $('#summernote').summernote({
          height: 300,
          minHeight: null,
          maxHeight: null,
          focus: true,
          callbacks: {
            onImageUpload: function(files, editor, welEditable) {
              for (var i = files.length - 1; i >= 0; i--) {
                sendFile(files[i], this);
              }
            }
          }
        });
      });
      
      function sendFile(file, el) {
        var form_data = new FormData();
        form_data.append('file', file);
        $.ajax({
          data: form_data,
          type: "POST",
          url: '/image',
          cache: false,
          contentType: false,
          enctype: 'multipart/form-data',
          processData: false,
          success: function(url) {
            $(el).summernote('editor.insertImage', url);
            $('#imageBoard > ul').append('<li><img src="'+url+'" width="480" height="auto"/></li>');
          }
        });
      }
  </script>
</body>
</html>