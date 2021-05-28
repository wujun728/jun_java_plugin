<!DOCTYPE html>
<html>
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
    <link href="${ctx}/static/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="${ctx}/static/css/animate.css" rel="stylesheet">
    <link href="${ctx}/static/css/style.css" rel="stylesheet">
</head>

<body class="fixed-sidebar">
<div id="wrapper">
    <!----左侧导航开始----->
    <nav class="navbar-default navbar-static-side" role="navigation" id="leftnav">
    </nav>
    <!----左侧导航结束----->
    <!---右侧内容区开始---->
    <div id="page-wrapper" class="gray-bg">
        <!---顶部状态栏 star-->
        <div class="row ">
            <nav class="navbar navbar-fixed-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i>
                    </a>
                </div>
                <ul class="nav navbar-top-links navbar-right notification-menu">
                    <li>
                        <span class="m-r-sm text-muted welcome-message">Welcome to Wuling Admin WebSite.</span>
                    </li>
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-envelope"></i> <span class="label label-warning">16</span>
                        </a>

                        <div class="dropdown-menu dropdown-menu-head pull-right">
                            <h5 class="title">You have 5 Mails </h5>
                            <ul class="dropdown-list normal-list">
                                <li class="new">
                                    <a href="">
                                        <span class="thumb"><img src="${ctx}/static/images/photos/user1.png" alt=""></span>
                                            <span class="desc">
                                              <span class="name">John Doe <span
                                                      class="badge badge-success">new</span></span>
                                              <span class="msg">Lorem ipsum dolor sit amet...</span>
                                            </span>
                                    </a>
                                </li>
                                <li class="new"><a href="">Read All Mails</a></li>
                            </ul>
                        </div>
                    </li>

                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-bell"></i> <span class="label label-primary">8</span>
                        </a>
                        <div class="dropdown-menu dropdown-menu-head pull-right">
                            <h5 class="title">Notifications</h5>
                            <ul class="dropdown-list normal-list">
                                <li class="new">
                                    <a href="">
                                        <span class="label label-danger"><i class="fa fa-bolt"></i></span>
                                        <span class="name">Server #1 overloaded.  </span>
                                        <em class="small">34 mins</em>
                                    </a>
                                </li>
                                <li class="new"><a href="">See All Notifications</a></li>
                            </ul>
                        </div>
                    </li>

                    <li class="user-dropdown">
                        <a href="#" class="btn  dropdown-toggle" data-toggle="dropdown">
                            <img src="${ctx}/static/images/photos/user-avatar.png" alt="" width="20">羊羊案场经理
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
                            <li><a href="#"><i class="fa fa-user"></i> Profile</a></li>
                            <li><a href="#"><i class="fa fa-cog"></i> Settings</a></li>
                            <li><a href="#"><i class="fa fa-sign-out"></i> Log Out</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
        <!---顶部状态栏 end-->
        <!--------当前位置----->
        <div class="row  border-bottom white-bg page-heading">
            <div class="col-sm-4">
                <h2>POS机绑定信息</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="${ctx}/index">管理首页</a>
                    </li>
                    <li>
                        <a class="active" href="javascript:void(0)">POS机绑定信息</a>
                    </li>
                </ol>
            </div>
        </div>
        <!-----内容区域---->
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="row">
                <div class="col-md-12">
                            <div class="ibox-heading">
                                <div class="ibox-title">
                                    <h5>新增POS机</h5>
                                </div>
                            </div>
                            <div class="ibox-content m-b-sm border-bottom">
                                <div class="row">
                                    <div class="col-md-6">
                                        <form class="form-horizontal" id="house_form">

                                            <div class="form-group">
                                                <label class="col-md-4 col-lg-3 control-label" readonly="readonly">pos机终端编号 <span class="text-danger">*</span> </label>
                                                <div class="col-md-8 col-lg-9"><input type="text" class="form-control" value="" placeholder="" ></div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-4 col-lg-3 control-label" readonly="readonly">备注 </label>
                                                <div class="col-md-8 col-lg-9"><textarea class="form-control" placeholder="" rows="5" ></textarea></div>
                                            </div>

                                            <div class="form-group">
                                                <div class="col-sm-8 col-sm-offset-4 col-lg-offset-3">
                                                    <button class="btn btn-primary" type="submit" id="myButton2"><i class="fa fa-check"></i> 填写完成，提交！</button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>


                </div>
                <div class="col-md-12">
                    <div class="ibox-heading">
                        <div class="ibox-title">
                            <h5>POS机列表</h5>
                        </div>
                    </div>
                    <div class="ibox-content m-b-sm border-bottom">
                        <div class="table-responsive ">
                            <table class="table table-centerbody table-striped table-condensed" id="editable-sample">
                                <thead>
                                <tr>
                                    <th>POS机终端号 </th>
                                    <th>服役时间 </th>
                                    <th>备注 </th>
                                    <th>操作 </th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>64564653672</td>
                                    <td>起始时间：2015-07-20 16:01:01<br>结束时间：2015-07-20 16:01:08</td>
                                    <td>经纪人：明明 公司：测试经纪公司 门店：多媒体门店</td>
                                    <td class="text-right"><a class="btn-primary  btn btn-sm delete" data-userid="1"><i class="fa fa-trash"></i>  删除</a></td>
                                </tr>
                                <tr>
                                    <td>64564653672</td>
                                    <td>起始时间：2015-07-20 16:01:01<br>结束时间：2015-07-20 16:01:08</td>
                                    <td>经纪人：明明 公司：测试经纪公司 门店：多媒体门店</td>
                                    <td class="text-right"><a class="btn-primary  btn btn-sm delete" data-userid="1"><i class="fa fa-trash"></i>  删除</a></td>
                                </tr>

                                <tr>
                                    <td>64564653672</td>
                                    <td>起始时间：2015-07-20 16:01:01<br>结束时间：2015-07-20 16:01:08</td>
                                    <td>经纪人：明明 公司：测试经纪公司 门店：多媒体门店</td>
                                    <td class="text-right"><a class="btn-default btn btn-sm " data-userid="1" disabled="disabled"><i class="fa fa-trash"></i>  已删除</a></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
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
<!-- 全局 scripts -->
<script src="${ctx}/static/js/jquery-2.1.1.js"></script>
<script src="${ctx}/static/js/bootstrap.js"></script>
<script src="${ctx}/static/js/wuling.js"></script>
<script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
<script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<!-- 插件 scripts -->
<script src="${ctx}/static/js/plugins/toastr/toastr.min.js" async></script>
<script src="${ctx}/static/js/plugins/sweetalert/sweetalert.min.js" async></script><!---对话框 alert--->
<!---顶部弹出提示--->

<script>
    $(document).on('click','#editable-sample a.delete', function () {
        var userid=$(this).data("userid");
        swal({
            title: "您确定要删除吗?",
            text: "",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#1ab394",
            confirmButtonText: "确定删除！",
            closeOnConfirm: false
        }, function () {
            //请求成功返回
            //swal("删除成功!", "","success");

            //失败
            swal("删除失败!", "","error");
        });
    });
</script>
</body>
</html>
