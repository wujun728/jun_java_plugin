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
    <link href="${ctx}/static/css/plugins/chosen/chosen.css" rel="stylesheet">
    <!--date style-->
    <link href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="${ctx}/static/css/animate.css" rel="stylesheet">
    <link href="${ctx}/static/css/style.css" rel="stylesheet">
</head>
<body class="fixed-sidebar">
<!--主体开始-->
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
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                </div>
                <ul class="nav navbar-top-links navbar-right notification-menu">
                    <li>
                        <span class="m-r-sm text-muted welcome-message">Welcome to Wuling Admin WebSite.</span>
                    </li>

                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-envelope"></i>  <span class="label label-warning">16</span>
                        </a>
                        <div class="dropdown-menu dropdown-menu-head pull-right">
                            <h5 class="title">You have 5 Mails </h5>
                            <ul class="dropdown-list normal-list">
                                <li class="new">
                                    <a href="">
                                        <span class="thumb"><img src="${ctx}/static/images/photos/user1.png" alt=""></span>
                                            <span class="desc">
                                              <span class="name">John Doe <span class="badge badge-success">new</span></span>
                                              <span class="msg">Lorem ipsum dolor sit amet...</span>
                                            </span>
                                    </a>
                                </li>
                                <li>
                                    <a href="">
                                        <span class="thumb"><img src="${ctx}/static/images/photos/user2.png" alt=""></span>
                                            <span class="desc">
                                              <span class="name">Jonathan Smith</span>
                                              <span class="msg">Lorem ipsum dolor sit amet...</span>
                                            </span>
                                    </a>
                                </li>
                                <li>
                                    <a href="">
                                        <span class="thumb"><img src="${ctx}/static/images/photos/user3.png" alt=""></span>
                                            <span class="desc">
                                              <span class="name">Jane Doe</span>
                                              <span class="msg">Lorem ipsum dolor sit amet...</span>
                                            </span>
                                    </a>
                                </li>
                                <li>
                                    <a href="">
                                        <span class="thumb"><img src="${ctx}/static/images/photos/user4.png" alt=""></span>
                                            <span class="desc">
                                              <span class="name">Mark Henry</span>
                                              <span class="msg">Lorem ipsum dolor sit amet...</span>
                                            </span>
                                    </a>
                                </li>
                                <li>
                                    <a href="">
                                        <span class="thumb"><img src="${ctx}/static/images/photos/user5.png" alt=""></span>
                                            <span class="desc">
                                              <span class="name">Jim Doe</span>
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
                            <i class="fa fa-bell"></i>  <span class="label label-primary">8</span>
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
                                <li class="new">
                                    <a href="">
                                        <span class="label label-danger"><i class="fa fa-bolt"></i></span>
                                        <span class="name">Server #3 overloaded.  </span>
                                        <em class="small">1 hrs</em>
                                    </a>
                                </li>
                                <li class="new">
                                    <a href="">
                                        <span class="label label-danger"><i class="fa fa-bolt"></i></span>
                                        <span class="name">Server #5 overloaded.  </span>
                                        <em class="small">4 hrs</em>
                                    </a>
                                </li>
                                <li class="new">
                                    <a href="">
                                        <span class="label label-danger"><i class="fa fa-bolt"></i></span>
                                        <span class="name">Server #31 overloaded.  </span>
                                        <em class="small">4 hrs</em>
                                    </a>
                                </li>
                                <li class="new"><a href="">See All Notifications</a></li>
                            </ul>
                        </div>

                    </li>

                    <li class="user-dropdown">
                        <a href="#" class="btn  dropdown-toggle" data-toggle="dropdown">
                            <img src="${ctx}/static/images/photos/user-avatar.png" alt="" width="20">
                            羊羊案场经理
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
                            <li><a href="#"><i class="fa fa-user"></i>  Profile</a></li>
                            <li><a href="#"><i class="fa fa-cog"></i>  Settings</a></li>
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
                <h2>经纪公司维护</h2>
                <ol class="breadcrumb">
                    <li><a href="${ctx}/index">管理首页</a></li>
                    <li class="active">经纪公司列表</li>
                </ol>
            </div>
        </div>

        <!-----内容区域---->
        <div class="wrapper wrapper-content animated fadeInRight">

            <div class="ibox-content m-b-sm ibox-content-t border-bottom" id="ibox-content">
                <div class="row">
                    <div class="form-group col-md-3">
                        <a href="#add_rslide" class="btn btn-primary"  data-title="添加广告">新增经济公司 <i class="fa fa-plus"></i></a>
                    </div>
                    <div class="col-md-3 form-group  m-t-xs">
                        <input type="text" class="input-sm form-control" name="end" value="" placeholder="公司名称、公司简称、联系人、公司地址">
                    </div>

                    <div class="col-md-2  m-t-xs form-group">
                        <select name="" class="form-control input-s-sm inline input-sm">
                            <option value="">－－城市－－</option>
                        </select>
                    </div>

                    <div class="form-group col-md-4 m-t-xs " id="data_5">
                        <div class="input-daterange input-group" id="datepicker">
                            <input type="text" class="input-sm form-control" name="start" value="09/07/2015" placeholder="">
                            <span class="input-group-addon">至</span>
                            <input type="text" class="input-sm form-control" name="end" value="10/07/2015" placeholder="">
                            <span class="input-group-btn"><i class="fa fa-search"></i> <button class="btn btn-primary btn-sm m-b-none"><i class="fa fa-search"></i> 搜索</button></span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox">
                        <div class="ibox-content">
                            <div class="table-responsive ">
                                <table class="table table-centerbody table-striped table-condensed text-nowrap" id="editable-sample">
                                    <thead>
                                    <tr>
                                        <th>公司全称</th>
                                        <th>城市</th>
                                        <th>创建时间</th>
                                        <th>审核状态</th>
                                        <th>备注</th>
                                        <th class="text-right">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>test-领客道</td>
                                        <td>上海</td>
                                        <td>2015-10-19</td>
                                        <td><span class="label label-danger">审核拒绝</span></td>
                                        <td>通过</td>
                                        <td class="text-right text-nowrap">
                                            <div class="btn-group ">
                                                <a href="edit.html" class="btn btn-white btn-sm edit" ><i class="fa fa-pencil"></i>  修改</a>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>test-领客道</td>
                                        <td>上海</td>
                                        <td>2015-10-19</td>
                                        <td><span class="label label-success">审核通过</span></td>
                                        <td>通过</td>
                                        <td class="text-right text-nowrap">
                                            <div class="btn-group ">
                                                <a href="edit.html" class="btn btn-white btn-sm edit" ><i class="fa fa-pencil"></i>  修改</a>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>test-领客道</td>
                                        <td>上海</td>
                                        <td>2015-10-19</td>
                                        <td><span class="label label-white">等待审核</span></td>
                                        <td>通过</td>
                                        <td class="text-right text-nowrap">
                                            <div class="btn-group ">
                                                <a href="edit.html" class="btn btn-white btn-sm edit" ><i class="fa fa-pencil"></i>  修改</a>
                                            </div>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="pages border-top">
                                <div class="row">
                                    <div class="col-md-4"><div class="m-t-md">当前显示 1 到 20 条，共 57 条</div></div>
                                    <div class="col-md-8 footable-visible">
                                        <ul class="pagination pull-right">
                                            <li class="footable-page-arrow disabled"><a data-page="first" href="#first">«</a></li>
                                            <li class="footable-page-arrow disabled"><a data-page="prev" href="#prev">‹</a></li>
                                            <li class="footable-page active"><a data-page="0" href="#">1</a></li>
                                            <li class="footable-page"><a data-page="1" href="#">2</a></li>
                                            <li class="footable-page"><a data-page="1" href="#">3</a></li>
                                            <li class="footable-page"><a data-page="1" href="#">4</a></li>
                                            <li class="footable-page"><a data-page="1" href="#">5</a></li>
                                            <li class="footable-page"><a data-page="1" href="#">6</a></li>
                                            <li class="footable-page-arrow"><a data-page="next" href="#next">›</a></li>
                                            <li class="footable-page-arrow"><a data-page="last" href="#last">»</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
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
<!--主体结束-->

<!-- 全局 scripts -->
<script src="${ctx}/static/js/jquery-2.1.1.js"></script>
<script src="${ctx}/static/js/bootstrap.js"></script>
<script src="${ctx}/static/js/wuling.js"></script>
<script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>

<script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>

<!-- 插件 scripts -->
<script src="${ctx}/static/js/plugins/chosen/chosen.jquery.js"></script>
<script src="${ctx}/static/js/plugins/toastr/toastr.min.js" async></script><!---顶部弹出提示--->
<script src="${ctx}/static/js/plugins/sweetalert/sweetalert.min.js" async></script><!---对话框 alert--->
<script src="${ctx}/static/js/plugins/validate/jquery.validate.min.js"></script>  <!---表单验证--->
<script src="${ctx}/static/js/plugins/validate/validate-cn.js" ></script> <!---validate 自定义方法--->

<script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<script>
    $('#data_5 .input-daterange').datepicker({
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true
    });
</script>
</body>
</html>