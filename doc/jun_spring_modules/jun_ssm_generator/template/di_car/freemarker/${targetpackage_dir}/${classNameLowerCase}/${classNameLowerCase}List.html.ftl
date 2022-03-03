${r'<#ftl output_format="HTML" auto_esc=true>'}
${r"<@h.commonHead"} title="后台管理系统" keywords="开源,永久免费" description="springrain开源系统管理后台"/>
<script src="${r"${ctx}"}/js/custom/common/form.js"></script>
<#assign className = table.className>   
<#assign tableName = table.tableAlias>   
<#assign classNameLower = className?uncap_first>  
<#assign classNameLowerCase = className?lower_case>
<#assign from = basepackage?last_index_of(".")>
<#assign rootPagefloder = basepackage?substring(basepackage?last_index_of(".")+1)>
<#assign targetpackage = targetpackage>

<script type="text/javascript">
	jQuery(function(){ 
		var _state="${r"${(returnDatas.queryBean.active)!''}"}";
		jQuery("#active").val(_state);
		/*
		全选、反选
		*/
		jQuery("#checkAll").bind('click',function(){
			var _is_check=jQuery(this).get(0).checked;
			jQuery("input[name='check_li']").each(function(_i,_o){
				jQuery(_o).get(0).checked=_is_check;
			});
		});
	});
	function del(_id){
		springrain.mydelete(_id,"${r"${ctx}"}/${targetpackage}/${classNameLowerCase}/delete");
	}
</script>

</head>
<body>
	<div class="layui-layout layui-layout-admin">
		${r"<@h.naviHeader />"}${r"<@h.leftMenu />"}
			<!-- 主体内容开始 -->
			<div class="layui-tab layui-tab-brief">
				<ul class="layui-tab-title site-demo-title">
		             <li class="layui-this">
		             		
		             </li>
					 <li style="float:right;">
		             	${r"<@shiro.hasPermission"} name="/${targetpackage}/${classNameLowerCase}/update" >
		             		<button type="button"   class="layui-btn layui-btn-small" data-action="${r"${ctx}"}/${targetpackage}/${classNameLowerCase}/update/pre"><i class="layui-icon layui-icon-specil">&#xe61f;</i>新增</button>
		             	${r"</@shiro.hasPermission>"}
		             	${r"<@shiro.hasPermission"} name="/${targetpackage}/${classNameLowerCase}/list/export" >
				        	<button type="button"   class="layui-btn layui-btn-small"><i class="layui-icon layui-icon-specil">&#xe609;</i>导出</button>
				        ${r"</@shiro.hasPermission>"}
		                <button type="button"  class="layui-btn layui-btn-warm layui-btn-small"><i class="layui-icon layui-icon-specil">&#xe601;</i>导入</button>
		                ${r"<@shiro.hasPermission"} name="/${targetpackage}/${classNameLowerCase}/delete" >
		               		 <button type="button"  class="layui-btn layui-btn-danger layui-btn-small"><i class="layui-icon">&#xe640;</i>批量删除</button>
		                ${r"</@shiro.hasPermission>"}
		             </li>
	       		</ul>
				
				<div class="layui-body layui-tab-content site-demo-body">
					<div class="layui-tab-item layui-show">
							<div class="layui-main">
						          <div id="LAY_preview">
						          <!-- 查询  开始 -->
							          <form class="layui-form layui-form-pane" id="searchForm" action="${r"${ctx}"}/${targetpackage}/${classNameLowerCase}/list" method="post">
							          <input type="hidden" name="pageIndex" id="pageIndex" value="${r"${(returnDatas.page.pageIndex)!'1'}"}" /> 
							          <input type="hidden" name="sort" id="page_sort" value="${r"${(returnDatas.page.sort)!'desc'}"}" />
							          <input type="hidden" name="order" id="page_order" value="${r"${(returnDatas.page.order)!'id'}"}" />
									  <table class="layui-table search-wrap">
									  	<colgroup>
										    <col width="">
										    <col width="150">
										</colgroup>
							          	<tbody>
							          		<tr>
							          			<th colspan="2">${tableName}搜索</th>
							          		</tr>
							          		<tr>
							          			<td>
							          				<div class="layui-inline">
									                    <label class="layui-form-label">名称</label>
									                    <div class="layui-input-inline">
									                           <input type="text" name="name" value="${r"${(returnDatas.queryBean.name)!''}"}" placeholder="请输入名称 " class="layui-input">
									                    </div>
							                		</div>
									                 <div class="layui-inline">
									                    <label class="layui-form-label">是否可用</label>
									                    <div class="layui-input-inline">
									                        <select name="active" id="active" class="layui-input">
									                          <option value="">==请选择==</option>
															  <option value="1">是</option>
															  <option value="0">否</option>
															</select>   
									                    </div>
									                </div>
							          			</td>
							          			<td>
							          			   <div class="layui-inline">
									                    <button class="layui-btn" type="button" onclick="springrain.commonSubmit('searchForm');"><i class="layui-icon" style="top:4px;right:5px;">&#xe615;</i>搜索</button>
									                </div>
							          			</td>
							          		</tr>
							          	</tbody>
							          </table>
									  <!-- 查询  结束 -->
									</form>
									<!--start_export-->
									<table class="layui-table" lay-even>
										  <colgroup>
										    <col width="40">
										    <col width="120">
										    <col>
										  </colgroup>
										  <!--end_no_export-->
										  <!--first_start_export-->
											<thead>
												<tr>
													<th colspan="${(table.columns?size+1)!9}">${tableName}列表<font id='recordsView' class='recorsView'>共<span></span>页,共 <span></span>条记录</font></th>
												</tr>
												<tr>
												  <!--first_start_no_export-->
												  <th class="center">
															<input id="checkAll"  type="checkbox">
												  </th>
												  <th>操作</th>
												  <!--first_end_no_export-->
												  	<#list table.columns as column>
														<#if !column.pk>
														<th id="th_${column.columnNameFirstLower}" >${column.columnAlias}</th>
														</#if>
													</#list>	
												</tr> 
											</thead>
										  <!--first_end_export-->
										  <!--start_export-->
										   <tbody>
										    ${r"<#if"} (returnDatas.data??)&&(returnDatas.data?size>0)> 
										    	${r"<#list"}	returnDatas.data as _data>
										    		<!--start_no_export-->
													<tr class="">
														<td class="center">
																<input name="check_li" value="${r"${_data.id}"}"  type="checkbox">
														</td>
														<td>
															${r"<@shiro.hasPermission"} name="/${targetpackage}/${classNameLowerCase}/update" >
								                           		 <a href="#" data-action="${r"${ctx}"}/${targetpackage}/${classNameLowerCase}/update/pre?id=${r"${(_data.id)!''}"}" class="layui-btn layui-btn-normal layui-btn-mini">编辑</a>
								                            ${r"</@shiro.hasPermission>"}
								                            ${r"<@shiro.hasPermission"} name="/${targetpackage}/${classNameLowerCase}/delete" >
								                            	<a href="javascript:del('${r"${(_data.id)!''}"}')" class="layui-btn layui-btn-danger layui-btn-mini ajax-delete">删除</a>
								                            ${r"</@shiro.hasPermission>"}
														</td>
														<!--end_no_export-->
														<#list table.columns as column>
														<#if !column.pk>
														<td >
															<#if column.isDateTimeColumn>
															<!--日期型-->
																<#assign columnDataValue = "((_data."+column.columnNameFirstLower+")?string('yyyy-MM-dd'))!''"> 
														${r"${"}${columnDataValue}${r"}"}
															<#elseif column.javaType == 'java.lang.Boolean'>
																<!--布尔型-->
																<#assign columnBooleanValue = "(_data."+column.columnNameFirstLower+")">
																${r'<#if'} ${columnBooleanValue}?? && ${columnBooleanValue} >
														真
																${r'<#else>'}
														假
																${r'</#if>'}
															<#elseif column.isNumberColumn>
															${r"${(_data."}${column.columnNameFirstLower}${r")!0}"}
															<#else>
															${r"${(_data."}${column.columnNameFirstLower}${r")!''}"}
															</#if>
														</td>
														</#if>
													</#list>
													</tr>
												${r"</#list>"}
											 ${r"</#if>"}
											</tbody>
										</table>
									${r"<#if"} returnDatas.page??> 
										<div id='laypageDiv'></div>
										${r"<@h.layPage"} page=returnDatas.page /> 
									${r"</#if>"}
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

