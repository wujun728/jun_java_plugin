<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>新增</title>
<link rel="icon" href="${ctx}/static/images/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico" type="image/x-icon" />
<link rel="icon" href="${ctx}/static/images/favicon.ico" type="pngimage/png" />
<meta name="keyword" content="">
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<meta name="Author" content="zifan">
<meta name="copyright" content="胡桃夹子。All Rights Reserved">
<link href="${ctx}/static/css/bootstrap.min.css?${version_css}" rel="stylesheet">
<link href="${ctx}/static/font-awesome/css/font-awesome.css?${version_css}" rel="stylesheet">
<link href="${ctx}/static/css/plugins/toastr/toastr.min.css?${version_css}" rel="stylesheet">
<link href="${ctx}/static/css/plugins/chosen/chosen.css?${version_css}" rel="stylesheet">
<link href="${ctx}/static/css/plugins/fileinput/fileinput.min.css?${version_css}" rel="stylesheet">
<link href="${ctx}/static/css/animate.css?${version_css}" rel="stylesheet">
<link href="${ctx}/static/css/style.css?${version_css}" rel="stylesheet">

</head>

<body class="fixed-sidebar">
	<div id="wrapper">
		<!----左侧导航开始----->
        <nav class="navbar-default navbar-static-side animated fadeInLeft" role="navigation" id="leftnav"></nav>
        <!----左侧导航结束----->


		<!---右侧内容区开始---->
		<div id="page-wrapper" class="gray-bg">
			<!---顶部状态栏 star-->
            <div class="row ">
              <nav class="navbar navbar-fixed-top" role="navigation" id="topnav"></nav>
            </div>
            <!---顶部状态栏 end-->

			<!--------当前位置----->
			<div class="row  border-bottom white-bg page-heading">
				<div class="col-sm-4">
					<ol class="breadcrumb">
						<br/>
						<li>邮件收件配置</li>
						<li class="active">新增收件信息</li>
					</ol>
				</div>
			</div>

			<!-----内容区域---->
			<div class="wrapper wrapper-content animated fadeInRight">
				<div class="ibox float-e-margins ">
					<div class="ibox-content p-t-slg">
						<form name="entity" id="input_form" class="form-horizontal">

							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-2 control-label" for="title"><span class="text-danger">* </span>收件类型</label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" id="mailType" name="mailType" placeholder="请输入收件类型" class="form-control" required>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-2 control-label" for="description"><span class="text-danger">*</span>收件人地址</label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" id="toAddress" name="toAddress" placeholder="请输入收件人地址" class="form-control" required>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-2 control-label" for="address"><span class="text-danger">*</span>抄送地址</label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" id="toCc" name="toCc" value="" placeholder="请输入抄送地址" class="form-control" required>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-12 col-md-4 col-lg-2 control-label" for="newsTime"><span class="text-danger">*</span>秘送地址</label>
								<div class="col-sm-12 col-md-7 col-lg-9">
									<input type="text" id="toBcc" name="toBcc" value="" placeholder="请输入秘送地址" class="form-control" required>
								</div>
							</div>

							<div class="hr-line-dashed"></div>


							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" type="submit">
										<i class="fa fa-check"></i> 填写完成，提交！
									</button>
									<button class="btn btn-white" type="reset">取消</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<!-----内容结束----->

			<!----版权信息----->
			<jsp:include page="/WEB-INF/views/copyright.jsp" />
		</div>
		<!---右侧内容区结束----->

	</div>

	<!-- 全局 scripts -->
	<script>
    var _ctx = '${ctx}';
    </script>
	<script src="${ctx}/static/js/jquery-2.1.1.js?${version_js}"></script>
	<script src="${ctx}/static/js/bootstrap.js?${version_js}"></script>
	<script src="${ctx}/static/js/wuling.js?${version_js}"></script>
	<script src="${ctx}/static/js/plugins/pace/pace.min.js?${version_js}"></script>
	<script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js?${version_js}"></script>
	<script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js?${version_js}"></script>
	<!-- 插件 scripts -->
	<script src="${ctx}/static/js/plugins/chosen/chosen.jquery.js?${version_js}"></script>
	<!---下拉菜单--->
	<script src="${ctx}/static/js/plugins/toastr/toastr.min.js?${version_js}" async></script>
	<!---顶部弹出提示--->
	<script src="${ctx}/static/js/plugins/validate/jquery.validate.min.js?${version_js}"></script>
	<!---表单验证--->
	<script src="${ctx}/static/js/plugins/validate/validate-cn.js?${version_js}"></script>
	<!---validate 自定义方法--->
	<script src="${ctx}/static/js/plugins/fileinput/fileinput.js?${version_js}"></script>
	<!---文件上传--->
	<script src="${ctx}/static/js/plugins/fileinput/fileinput_locale_zh.js?${version_js}"></script>
	<!---文件上传中文配置--->

	<script>
    $(".chosen-select").chosen({
      no_results_text: '未找到此项',
      width: "100%",
      allow_single_deselect: true,
      disable_search_threshold: 10
    });
    $(document).ready(function() {
      $("#input_form").validate({
        debug: true,
        submitHandler: function(form) {
          addform(form);
        }
      });
      function addform(form) {
        $.ajax({
          url: _ctx + "/mail/add",
          type: "post",
          data: $(form).serialize(),
          success: function(data) {
            if (data.status == '1') {
              toastr.success('', data.msg);
              window.location.href=_ctx+"/mail/list"; 
            } else
              toastr.error('', data.msg);
          },
          error: function(data) {
            toastr.error('', '发布失败');
          }
        });
      }

    });
  </script>
</body>
</html>
