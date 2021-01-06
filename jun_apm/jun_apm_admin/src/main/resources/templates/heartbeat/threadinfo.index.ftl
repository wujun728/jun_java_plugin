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
	<@netCommon.commonLeft "threadinfo" />
	
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

            <#-- min -->
            <div class="row" >
                <br>
                <div class="col-md-12 col-xs-12" >
                    <div class="btn-group">
                        <#list 0..59 as minItem>
                            <button type="button" class="btn <#if minItem = min>btn-success<#else>btn-default</#if> min" data-min="${minItem}" >${minItem?string["00"]}</button>
                        </#list>
                    </div>
                </div>
            </div>

            <#-- filter -->
            <#if threadInfoList?exists>
                <br>
                <div class="row" >
                    <div class="col-xs-4">
                        <div class="input-group">
                            <span class="input-group-addon">ThreadName</span>
                            <input type="text" class="form-control" id="ThreadName" autocomplete="on" placeholder="请输入ThreadName" maxlength="100" >
                        </div>
                    </div>
                    <div class="col-xs-6">
                        <div class="input-group">
                            <span class="input-group-addon">ThreadStatus</span>
                            <select class="form-control select2" multiple="multiple" style="width: 100%;" id="ThreadStatus" data-searchplaceholder="请选择 ThreadStatus" >
                                <option value="NEW" >NEW</option>
                                <option value="RUNNABLE" >RUNNABLE</option>
                                <option value="BLOCKED" >BLOCKED</option>
                                <option value="WAITING" >WAITING</option>
                                <option value="TIMED_WAITING" >TIMED_WAITING</option>
                                <option value="TERMINATED" >TERMINATED</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-xs-2">
                        <strong id="threadInfo-table-tips" >当前线程数量：${threadInfoList?size}</strong>
                    </div>
                </div>
            </#if>


            <#-- thread -->
			<div class="row">

                <br>
                <#if threadInfoList?exists>
                    <div class="col-md-12 col-xs-12">
                        <div class="box ">
                            <div class="box-body no-padding">
                                <table class="table table-stripe" id="threadInfo-table" style="width: 100%;">
                                    <thead>
                                    <tr>
                                        <th>ThreadId</th>
                                        <th>ThreadName</th>
                                        <th>ThreadStatus</th>
                                        <th>stack_info</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <#list threadInfoList as threadInfo>
                                            <tr class="threadInfo_item <#if threadInfo.status=="NEW" >bg-teal disabled color-palette
                                                    <#elseif threadInfo.status=="RUNNABLE">bg-green disabled color-palette
                                                    <#elseif threadInfo.status=="BLOCKED" || threadInfo.status=="WAITING" || threadInfo.status=="TIMED_WAITING" >bg-yellow disabled color-palette
                                                    <#elseif threadInfo.status=="TERMINATED" >bg-gray disabled color-palette
                                                    </#if>"
                                                data-name="${threadInfo.name}" data-status="${threadInfo.status}"   >

                                                <td>${threadInfo.id}</td>
                                                <td style="word-break: break-all">${threadInfo.name}</td>
                                                <td>${threadInfo.status}</td>
                                                <td>
                                                    <a class="btn btn-default btn-xs" onclick="javascript:$('#stack_info_${threadInfo.id}').toggle();" >Detail</a>
                                                </td>
                                            </tr>
                                            <tr class="threadInfo_item_stack_info" id="stack_info_${threadInfo.id}" style="display: none;" >
                                                <td colspan="4" >
                                                    <pre style="width:100%; white-space: pre-wrap; white-space: -moz-pre-wrap; white-space: -pre-wrap;white-space: -o-pre-wrap; word-wrap: break-word;"
                                                        >${threadInfo.stack_info!''}</pre>
                                                </td>
                                            </tr>
                                        </#list>
                                    </tbody>

                                </table>
                            </div>
                            <!-- /.box-body -->
                        </div>
                    </div>
                <#else>
                    <section class="content-header col-md-12">
                        <h1>暂无指标数据<small></small></h1>
                    </section>
                </#if>

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
    var min = '${min}';
</script>
<script src="${request.contextPath}/static/js/threadinfo.index.js"></script>

</body>
</html>
