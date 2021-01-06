<!DOCTYPE html>
<html>
<head>
  	<#import "./common/common.macro.ftl" as netCommon>
    <title>应用性能管理平台</title>
	<@netCommon.commonStyle />
    <#-- datetimepicker -->
    <link rel="stylesheet" href="${request.contextPath}/static/plugins/datetimepicker/jquery.datetimepicker.min.css">
</head>
<body class="hold-transition skin-blue sidebar-mini <#if cookieMap?exists && cookieMap["xxlapm_adminlte_settings"]?exists && "off" == cookieMap["xxlapm_adminlte_settings"].value >sidebar-collapse</#if> ">
<div class="wrapper">
	<!-- header -->
	<@netCommon.commonHeader />
	<!-- left -->
	<@netCommon.commonLeft "index" />
	
	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- 报表导航 -->
            <div class="row">

                <#-- 应用数量 -->
                <div class="col-md-4 col-sm-6 col-xs-12">
                    <div class="info-box bg-aqua">
                        <span class="info-box-icon"><i class="fa fa-flag-o"></i></span>

                        <div class="info-box-content">
                            <span class="info-box-text">应用数量</span>
                            <span class="info-box-number">${appNameCount}</span>

                            <div class="progress">
                                <div class="progress-bar" style="width: 100%"></div>
                            </div>
                            <span class="progress-description">接入的应用数量</span>
                        </div>
                    </div>
                </div>

                <#-- 实例数量 -->
                <div class="col-md-4 col-sm-6 col-xs-12">
                    <div class="info-box bg-green">
                        <span class="info-box-icon"><i class="fa ion-ios-settings-strong"></i></span>

                        <div class="info-box-content">
                            <span class="info-box-text">实例数量</span>
                            <span class="info-box-number">${appNameAddressCount}</span>

                            <div class="progress">
                                <div class="progress-bar" style="width: 100%"></div>
                            </div>
                            <span class="progress-description">接入应用部署的实例数量</span>
                        </div>
                    </div>
                </div>

                <#-- 消息数量 -->
                <div class="col-md-4 col-sm-6 col-xs-12" >
                    <div class="info-box bg-yellow">
                        <span class="info-box-icon"><i class="fa fa-calendar"></i></span>

                        <div class="info-box-content">
                            <span class="info-box-text">消息数量</span>
                            <span class="info-box-number">${totalMsgCount?string("###,###")}</span>

                            <div class="progress">
                                <div class="progress-bar" style="width: 100%" ></div>
                            </div>
                            <span class="progress-description">接入应用上报的消息数量</span>
                        </div>
                    </div>
                </div>

            </div>

            <div class="row" >

                <div class="col-xs-3">
                    <div class="input-group">
                        <span class="input-group-addon">时间</span>
                        <input type="text" class="form-control" id="querytime" value="${querytime?string('yyyy-MM-dd HH:mm')}" readonly >
                    </div>
                </div>
                <div class="col-xs-1">
                    <button class="btn btn-block btn-info" id="searchBtn">GO</button>
                </div>

                <br>
                <div class="col-md-12 col-xs-12" >
                    <div class="btn-group">
                        <#list 0..59 as minItem>
                            <button type="button" class="btn <#if min?exists && minItem = min>btn-success<#else>btn-default</#if> min" data-min="${minItem}" >${minItem?string["00"]}</button>
                        </#list>
                    </div>
                </div>
            </div>

            <div class="row">
                <br>
                <div class="col-md-12 col-xs-12">
                    <div class="box ">
                        <div class="box-body no-padding">
                            <table class="table table-striped2" id="event-table" style="width: 100%;">
                                <thead>
                                    <tr>
                                        <th>故障分类</th>
                                        <th>AppName</th>
                                        <th>Type</th>
                                        <th>故障次数(Min)</th>
                                        <th>Link</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <#if faultList?exists && faultList?size gt 0>
                                        <#list faultList as item>
                                            <tr class="bg-red color-palette" >
                                                <td>${item.Category}</td>
                                                <td>${item.AppName}</td>
                                                <td>${item.Type}</td>
                                                <td>${item.Count}</td>
                                                <td><a href="${request.contextPath}${item.Link}" class="btn btn-default btn-xs" >Link</a></td>
                                            </tr>
                                        </#list>
                                    <#else>
                                        <tr class="bg-gray color-palette" >
                                            <td>--</td>
                                            <td>--</td>
                                            <td>--</td>
                                            <td>--</td>
                                            <td>--</td>
                                        </tr>
                                    </#if>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                </div>

            </div>

        </section>
        <!-- /.content -->


	</div>
	<!-- /.content-wrapper -->
	
	<!-- footer -->
	<@netCommon.commonFooter />
</div>

<@netCommon.commonScript />
<#-- datetimepicker -->
<script src="${request.contextPath}/static/plugins/datetimepicker/jquery.datetimepicker.full.min.js"></script>
<script>
    var min = '${min}';
</script>
<script src="${request.contextPath}/static/js/index.js"></script>

</body>
</html>
