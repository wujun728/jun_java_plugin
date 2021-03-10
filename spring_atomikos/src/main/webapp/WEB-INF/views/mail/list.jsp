<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>列表查询</title>
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
<link href="${ctx}/static/css/plugins/iCheck/green.css?${version_css}" rel="stylesheet">
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
					<br />
					<ol class="breadcrumb">
						<li><a href="javascript:void(0)">邮件收件配置</a></li>
						<li class="active">收件配置列表</li>

					</ol>
				</div>
			</div>

			<!-----内容区域---->
			<div class="wrapper wrapper-content animated fadeInRight">
				<div class="ibox-content m-b-sm border-bottom">
					<div class="row">

						<div class="ibox ">
							<div class="ibox-content">
								<div class="row">
									<div class="col-sm-4 col-lg-2 col-md-3 m-t-xs">
										
										<input class="input-sm form-control input-s-sm inline" id="mailType" name="mailType">
									</div>
									<div class="form-group col-sm-4 col-lg-3 m-t-xs m-b-none" id="data_5">
										<div class="input-daterange input-group" id="datepicker">
											<span class="input-group-btn"><button type="button" class="btn btn-sm btn-primary no-margins" id="queryBtn">查询</button> </span>
										</div>
									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<div class="ibox" id="ibox">
							<jsp:include page="./list_page.jsp" />
						</div>
					</div>
				</div>
			</div>
			<!-----内容结束----->

			<!----版权信息----->
			<jsp:include page="/WEB-INF/views/copyright.jsp" />
		</div>
		<!---右侧内容区结束----->

	</div>

	<!----添加用户--->
	<div class="modal inmodal fade" id="edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header bg-primary">
					<button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
					<h4 class="modal-title">编辑收件配置信息</h4>
				</div>
				<div class="modal-body">
					<form role="form" id="mailAddressForm" name="mailAddressForm" class="form-horizontal"></form>
				</div>
			</div>
		</div>
	</div>
	<!---添加用户结束--->


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
	<script src="${ctx}/static/js/plugins/toastr/toastr.min.js?${version_js}" async></script>
	<!---顶部弹出提示--->
	<script src="${ctx}/static/js/plugins/iCheck/icheck.min.js?${version_js}"></script>
	<script src="${ctx}/static/js/plugins/validate/jquery.validate.min.js?${version_js}"></script>
	<!---表单验证--->
	<script src="${ctx}/static/js/plugins/validate/validate-cn.js?${version_js}"></script>
	<!--date style-->
	<script>
    $(document).ready(function() {

      $('.i-checks').iCheck({
        checkboxClass: 'icheckbox_minimal-green',
        radioClass: 'iradio_minimal-green',
      });
      //表单验证
      $("#mailAddressForm").validate({
        //debug: true,
        submitHandler: function(form) {
          editForm(form);
        }
      });

      //查询新闻
      $("#queryBtn").click(function() {
        list_page();
      });

      // 分页查询
      function list_page() {
        var mailType = $("#mailType").val();
        $("#ibox").load(_ctx + '/mail/list_page', {
          "mailType": mailType
        });
      }

      function editForm(form) {
        $.ajax({
          url: _ctx + "/mail/edit",
          type: "post",
          data: $(form).serialize(),
          success: function(data) {
            if (data.status == '1') {
              list_page();//保存成功，刷新数据
              toastr.success('', data.msg);
              $('#edit').modal('hide');//关闭编辑窗口
            } else
              toastr.error('', data.msg);
          },
          error: function(data) {
            toastr.error('', '保存失败');
          }
        });
      }

      //验证码在模态框出现前加载
      $("#edit").on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget);
        var id = button.data("id");
        $("#mailAddressForm").load(_ctx + '/mail/load/' + id);//加载待编辑数据
      });
    });
  </script>
</body>
</html>
