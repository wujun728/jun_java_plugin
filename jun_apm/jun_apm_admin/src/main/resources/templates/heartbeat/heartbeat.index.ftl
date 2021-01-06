<!DOCTYPE html>
<html>
<head>
  	<#import "../common/common.macro.ftl" as netCommon>
    <title>应用性能管理平台</title>

    <#-- select2 -->
    <link rel="stylesheet" href="${request.contextPath}/static/adminlte/bower_components/select2/css/select2.min.css">
    <@netCommon.commonStyle />
    <#-- datetimepicker -->
    <link rel="stylesheet" href="${request.contextPath}/static/plugins/datetimepicker/jquery.datetimepicker.min.css">
    <#-- jquery-ui -->
    <link rel="stylesheet" href="${request.contextPath}/static/plugins/jquery-ui/jquery-ui.min.css">

</head>
<body class="hold-transition skin-blue sidebar-mini <#if cookieMap?exists && cookieMap["xxlapm_adminlte_settings"]?exists && "off" == cookieMap["xxlapm_adminlte_settings"].value >sidebar-collapse</#if>">
<div class="wrapper">
	<!-- header -->
	<@netCommon.commonHeader />
	<!-- left -->
	<@netCommon.commonLeft "heartbeat" />
	
	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">

		<!-- Main content -->
	    <section class="content">
	    
	    	<div class="row">

                <div class="col-xs-3">
                    <div class="input-group">
                        <span class="input-group-addon">时间</span>
                        <input type="text" class="form-control" id="querytime" value="${querytime?string('yyyy-MM-dd HH:mm')}" readonly >
                    </div>
                </div>

                <div class="col-xs-4">
                    <div class="input-group">
                        <span class="input-group-addon">AppName</span>
                        <input type="text" class="form-control" id="appname" autocomplete="on" placeholder="请输入应用 AppName" value="${appname!''}" maxlength="100" >
                    </div>
                </div>

                <div class="col-xs-4">
                    <div class="input-group">
                        <span class="input-group-addon">机器</span>
                        <select class="form-control select2" style="width: 100%;" id="address" >
                            <#if addressInfo?exists >
                                <#list addressInfo?keys as key>
                                    <option value="${key}" <#if address?exists && address==key>selected="selected"</#if> >${addressInfo[key]}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                </div>

	            <div class="col-xs-1">
	            	<button class="btn btn-block btn-info" id="searchBtn">GO</button>
	            </div>
          	</div>

			<div class="row">

                <div id="bar-sample" style="display: none;" >
                    <div class="col-md-6 col-xs-12-" >
                        <div class="box box-default">
                            <div class="box-body chart-responsive">
                                <div class="chart bar-item" id="{id}" style="height: 300px;" ></div>
                            </div>
                        </div>
                    </div>
                </div>

				<div id="bar-parent" >
				</div>

			</div>
			
	    </section>
	</div>


	<!-- footer -->
	<@netCommon.commonFooter />
</div>

<@netCommon.commonScript />
<#-- echarts -->
<script src="${request.contextPath}/static/plugins/echarts/echarts.common.min.js"></script>
<#-- datetimepicker -->
<script src="${request.contextPath}/static/plugins/datetimepicker/jquery.datetimepicker.full.min.js"></script>
<#-- select2 -->
<script src="${request.contextPath}/static/adminlte/bower_components/select2/js/select2.full.min.js"></script>
<script src="${request.contextPath}/static/adminlte/bower_components/select2/js/i18n/zh-CN.js"></script>
<#-- jquery-ui -->
<script src="${request.contextPath}/static/plugins/jquery-ui/jquery-ui.min.js"></script>

<script>
    var heartbeatList;
    <#if heartbeatList?exists>
        heartbeatList = JSON.parse('${heartbeatList}');
    </#if>

</script>
<script src="${request.contextPath}/static/js/heartbeat.index.js"></script>

</body>
</html>
