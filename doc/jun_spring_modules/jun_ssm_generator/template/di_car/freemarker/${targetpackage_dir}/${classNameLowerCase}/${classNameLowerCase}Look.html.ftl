${r'<#ftl output_format="HTML" auto_esc=true>'}
${r"<@h.commonHead"} title="后台管理系统" keywords="开源,永久免费" description="springrain开源系统管理后台"/>

<#assign className = table.className>   
<#assign tableName = table.tableAlias>   
<#assign classNameLower = className?uncap_first>  
<#assign classNameLowerCase = className?lower_case>
<#assign from = basepackage?last_index_of(".")>
<#assign rootPagefloder = basepackage?substring(basepackage?last_index_of(".")+1)>
<#assign targetpackage = targetpackage>

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
														    <#if !column.pk>
																<#assign columnValue = "("+classNameLower+"."+column.columnNameFirstLower+")!''">
																<#if column.isDateTimeColumn>
																	<!--日期型-->
																	<#assign columnDataValue = column.columnNameFirstLower+")?string('yyyy-MM-dd'))!'' ">
																	<div class="layui-form-item col-lg-6">
																		<label class="layui-form-label">${column.columnAlias}*</label>
																		<div class="layui-inline col-lg-5">  
																			${r"${((returnDatas.data."}${columnDataValue}${r"}"}
																		</div>
																		<div class="layui-inline valid-info"></div>
																	</div>
																<#else>
																	<div class="layui-form-item col-lg-6">
																		<label class="layui-form-label">${column.columnAlias}*</label>
																		<div class="layui-inline col-lg-5">  
																			${r"${(returnDatas.data."}${column.columnNameFirstLower}${r")!''}"}
																		</div>
																		<div class="layui-inline valid-info"></div>
																	</div>
																</#if>
															</#if>
													</#list>
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