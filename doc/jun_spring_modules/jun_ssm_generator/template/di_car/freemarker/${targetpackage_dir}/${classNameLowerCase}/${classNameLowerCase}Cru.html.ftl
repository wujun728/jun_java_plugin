${r'<#ftl output_format="HTML" auto_esc=true>'}
${r"<@h.commonHead"} title="后台管理系统" keywords="开源,永久免费" description="springrain开源系统管理后台"/>
<#assign className = table.className>
<#assign tableName = table.tableAlias>   
<#assign classNameLower = className?uncap_first>  
<#assign classNameLowerCase = className?lower_case>
<#assign from = basepackage?last_index_of(".")>
<#assign rootPagefloder = basepackage?substring(basepackage?last_index_of(".")+1)>

<script src="${r"${ctx}"}/js/validform/validform.min.js"></script>
<link rel="stylesheet" href="${r"${ctx}"}/js/validform/validform.css" media="all">
<script>
	 jQuery(function(){
		 /*
		 init_valid(_before,_after)
		 @_before:提交表单前，调用 的函数   可以为空，一般有validform处理不了，独自进行处理时使用
		 @_after:保存成功后，调用的函数  可以为空，一般为对应的列表页面
		 */
		 springrain.initValid(null,function(){window.location.href="${r"${ctx}"}/${targetpackage}/${classNameLowerCase}/list?springraintoken="+springraintoken});
	 });
</script>
</head>
<body>
	<div class="layui-layout layui-layout-admin">
		${r"<@h.naviHeader />"}${r"<@h.leftMenu />"}
		<!-- 主体内容开始 -->
			<div class="layui-tab layui-tab-brief">
				<ul class="layui-tab-title site-demo-title">
		            <li class="layui-this">
		            		<i class="layui-icon">&#xe630;</i>
		             		<span class="layui-breadcrumb" style="visibility: visible;">
							   <a href="${r"${ctx}"}">首页<span class="layui-box">&gt;</span></a>
							   <a><cite>${tableName}管理</cite></a>
							</span>
		            </li>
		             <li style="float:right;">
				        <button type="button" onclick="history.go(-1)" class="layui-btn layui-btn-small" style="margin-top:8px;"><i class="layui-icon layui-icon-specil">&#xe603;</i>返回</button>
		             </li>
	       		</ul>
				
				<div class="layui-body layui-tab-content site-demo-body">
					<div class="layui-tab-item layui-show">
							<div class="layui-main">
						          <div id="LAY_preview" class="layui-my-form">
						          				<header class="larry-personal-tit">
													<span>添加${tableName}</span>
												</header>
												<div class="larry-personal-body clearfix changepwd">
													<form id="validForm" class="layui-form <!--  -->"  method="post" action="${r"${ctx}"}/${targetpackage}/${classNameLowerCase}/update">
													<#list table.columns as column>
															<#if column.pk>
																<#assign columnValue = "("+classNameLower+"."+column.columnNameFirstLower+")!''">
																<input type="hidden" id="${column.columnNameFirstLower}" name="${column.columnNameFirstLower}" value="${r"${(returnDatas.data."}${column.columnNameFirstLower}${r")!''}"}"/>	
															</#if>
													</#list>
													<#list table.columns as column>
														    <#if !column.pk>
																<#assign columnValue = "("+classNameLower+"."+column.columnNameFirstLower+")!''">
																<#if column.isDateTimeColumn>
																	<!--日期型-->
																	<div class="layui-form-item col-lg-6">
																		<label class="layui-form-label">${column.columnAlias}*</label>
																		<div class="layui-inline col-lg-5">  
																			<input type="datetime" name="${column.columnNameFirstLower}" id="${column.columnNameFirstLower}" datatype="*" nullmsg="不能为空" errormsg="不能为空！" autocomplete="off" class="layui-input" value="${r"${(returnDatas.data."}${column.columnNameFirstLower}${r")!''}"}">
																		</div>
																		<div class="layui-inline valid-info"></div>
																	</div>
																<#else>
																	<div class="layui-form-item col-lg-6">
																		<label class="layui-form-label">${column.columnAlias}*</label>
																		<div class="layui-inline col-lg-5">  
																			<input type="text" name="${column.columnNameFirstLower}" id="${column.columnNameFirstLower}" datatype="*" nullmsg="不能为空" errormsg="不能为空！" autocomplete="off" class="layui-input" value="${r"${(returnDatas.data."}${column.columnNameFirstLower}${r")!''}"}">
																		</div>
																		<div class="layui-inline valid-info"></div>
																	</div>
																</#if>
															</#if>
													</#list>
													<div class="layui-form-item change-submit">
													<label class="layui-form-label"></label>
															<div class="layui-inline">
																<button type="button" class="layui-btn" id="smtbtn">保存</button>
																<button type="button" class="layui-btn layui-btn-primary" id="rstbtn">重置</button>
															</div>
														</div>
													</form>
												</div>
								  </div>
							</div>
					</div>
			</div>
		</div>
		<!-- 主体内容结束 -->
		${r"<@h.footer />"}
	</div>
</body>
</html>
