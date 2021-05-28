<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>后台管理系统</title>
<meta name="keyword" content="">
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<meta name="Author" content="zifan">
<meta name="copyright" content="胡桃夹子。All Rights Reserved">
<link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/static/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<link href="${ctx}/static/css/plugins/iCheck/green.css" rel="stylesheet">
<link href="${ctx}/static/css/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/style.css" rel="stylesheet">
</head>

<body class="fixed-sidebar">
	<div id="wrapper">
		<!----左侧导航开始----->
		<nav class="navbar-default navbar-static-side" role="navigation" id="leftnav"></nav>
		<!----左侧导航结束----->

		<!---右侧内容区开始---->
		<div id="page-wrapper" class="gray-bg">
			<!---顶部状态栏 star-->
			<div class="row ">
				<nav class="navbar navbar-fixed-top" role="navigation" style="margin-bottom: 0">
					<div class="navbar-header">
						<a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
					</div>
					<ul class="nav navbar-top-links navbar-right notification-menu">
						<li><span class="m-r-sm text-muted welcome-message">Welcome to Wuling Admin WebSite.</span></li>

						<li class="dropdown"><a class="dropdown-toggle count-info" data-toggle="dropdown" href="#"> <i class="fa fa-envelope"></i> <span class="label label-warning">16</span>
						</a>
							<div class="dropdown-menu dropdown-menu-head pull-right">
								<h5 class="title">You have 5 Mails</h5>
								<ul class="dropdown-list normal-list">
									<li class="new"><a href=""> <span class="thumb"><img src="${ctx}/static/images/photos/user1.png" alt=""></span> <span class="desc"> <span class="name">John Doe <span class="badge badge-success">new</span></span> <span
												class="msg">Lorem ipsum dolor sit amet...</span>
										</span>
									</a></li>
									<li class="new"><a href="">Read All Mails</a></li>
								</ul>
							</div></li>
						<li class="dropdown"><a class="dropdown-toggle count-info" data-toggle="dropdown" href="#"> <i class="fa fa-bell"></i> <span class="label label-primary">8</span>
						</a>
							<div class="dropdown-menu dropdown-menu-head pull-right">
								<h5 class="title">Notifications</h5>
								<ul class="dropdown-list normal-list">
									<li class="new"><a href=""> <span class="label label-danger"><i class="fa fa-bolt"></i></span> <span class="name">Server #1 overloaded. </span> <em class="small">34 mins</em>
									</a></li>
									<li class="new"><a href="">See All Notifications</a></li>
								</ul>
							</div></li>

						<li class="user-dropdown"><a href="#" class="btn  dropdown-toggle" data-toggle="dropdown"> <img src="${ctx}/static/images/photos/user-avatar.png" alt="" width="20"> 羊羊案场经理 <span class="caret"></span>
						</a>
							<ul class="dropdown-menu dropdown-menu-usermenu pull-right">
								<li><a href="#"><i class="fa fa-user"></i> Profile</a></li>
								<li><a href="#"><i class="fa fa-cog"></i> Settings</a></li>
								<li><a href="#"><i class="fa fa-sign-out"></i> Log Out</a></li>
							</ul></li>
					</ul>
				</nav>
			</div>
			<!---顶部状态栏 end-->

			<!--------当前位置----->
			<div class="row  border-bottom white-bg page-heading">
				<div class="col-sm-4">
					<h2>电商配置</h2>
					<ol class="breadcrumb">
						<li><a href="${ctx}/index">管理首页</a></li>
						<li><a href="javascript:void(0)">站内新闻</a></li>
						<li class="active">新闻列表</li>

					</ol>
				</div>
			</div>

			<!-----内容区域---->
			<div class="wrapper wrapper-content animated fadeInRight">
				<div class="ibox-content m-b-sm border-bottom">
					<div class="row">
						<div class="col-md-5">
							<div class="input-group">
								<input type="text" class="input-sm form-control" id="keywords" name="keywords" value="" placeholder="新闻标题"> <span class="input-group-btn"><button type="button" class="btn btn-sm btn-primary" id="queryNewsBtn">查询</button></span>
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
			<div class="footer">
				<div class="pull-right">
					10GB of <strong>250GB</strong> Free.
				</div>
				<div>
					<strong>Copyright</strong> Example Company &copy; 2014-2015
				</div>
			</div>
		</div>
		<!---右侧内容区结束----->

	</div>

	<!----添加用户--->
	<div class="modal inmodal fade" id="edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header bg-primary">
					<button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
					<h4 class="modal-title">编辑重新发布新闻</h4>
				</div>
				<div class="modal-body">
					<form role="form" id="newsForm" name="newsForm" class="form-horizontal"></form>
				</div>
			</div>
		</div>
	</div>
	<!---添加用户结束--->


	<!-- 全局 scripts -->
	<script src="${ctx}/static/js/jquery-2.1.1.js"></script>
	<script src="${ctx}/static/js/bootstrap.js"></script>
	<script src="${ctx}/static/js/wuling.js"></script>
	<script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>

	<script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<!-- 插件 scripts -->
	<script src="${ctx}/static/js/plugins/toastr/toastr.min.js" async></script>
	<!---顶部弹出提示--->
	<script src="${ctx}/static/js/plugins/iCheck/icheck.min.js"></script>
	<script src="${ctx}/static/js/plugins/validate/jquery.validate.min.js"></script>
	<!---表单验证--->
	<script src="${ctx}/static/js/plugins/validate/validate-cn.js"></script>
	<!--date style-->
	<script src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script>
	<!---validate 自定义方法--->
	<script>
    var _ctx = '${ctx}';
    $(document).ready(function() {

      $('.i-checks').iCheck({
        checkboxClass: 'icheckbox_minimal-green',
        radioClass: 'iradio_minimal-green',
      });
      //表单验证
      $("#newsForm").validate({
        //debug: true,
        submitHandler: function(form) {
          editForm(form);
        }
      });

      //查询新闻
      $("#queryNewsBtn").click(function() {
        list_page();
      });

      // 分页查询
      function list_page() {
        var keywords = $("#keywords").val();
        $("#ibox").load(_ctx + '/news/list_page',{"keywords":keywords});
      }

      function editForm(form) {
        $.ajax({
          url: _ctx + "/news/edit",
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
            toastr.error('', '保存发布失败');
          }
        });
      }

      //验证码在模态框出现前加载
      $("#edit").on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget);
        var id = button.data("id");
        $("#newsForm").load(_ctx + '/news/load/' + id);//加载待编辑数据
      });
    });
  </script>
</body>
</html>
