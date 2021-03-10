<!-- 应用上下文 -->
<#assign base=request.contextPath />

<#macro pagerForm>
	<input type="hidden" name="pageNum" value="${(page.currentPage)!'1'}" />
	<input type="hidden" name="numPerPage" value="${(page.numPerPage)!'20'}" />
	<input type="hidden" name="orderField" value="${(request.orderField)!}" />
	<input type="hidden" name="orderDirection" value="${(request.orderDirection)!}" />
</#macro>

<#macro pages>
	<div class="panelBar" >
		<div class="pages">
			<span>显示</span>
			<select name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" <#if page.numPerPage == 20>selected="selected"</#if>>20</option>
				<option value="50" <#if page.numPerPage == 50>selected="selected"</#if>>50</option>
				<option value="100" <#if page.numPerPage == 100>selected="selected"</#if>>100</option>
				<option value="200" <#if page.numPerPage == 200>selected="selected"</#if>>200</option>
			</select>
			<span>条，共${(page.totalCount)!}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${(page.totalCount)!}" numPerPage="${(page.numPerPage)!}" pageNumShown="10" currentPage="${(page.currentPage)!}"></div>
	</div>
</#macro>

<#macro pagesDialog>
    <div class="panelBar" >
        <div class="pages">
            <span>显示</span>
            <select name="numPerPage" onchange="dialogPageBreak({numPerPage:this.value})">
                <option value="20" <#if page.numPerPage == 20>selected="selected"</#if>>20</option>
                <option value="50" <#if page.numPerPage == 50>selected="selected"</#if>>50</option>
                <option value="100" <#if page.numPerPage == 100>selected="selected"</#if>>100</option>
                <option value="200" <#if page.numPerPage == 200>selected="selected"</#if>>200</option>
            </select>
            <span>条，共${(page.totalCount)!}条</span>
        </div>
        <div class="pagination" targetType="dialog" totalCount="${(page.totalCount)!}" numPerPage="${(page.numPerPage)!}" pageNumShown="10" currentPage="${(page.currentPage)!}"></div>
    </div>
</#macro>