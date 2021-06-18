<div id="leftside">
	<div id="sidebar_s">
		<div class="collapse">
			<div class="toggleCollapse">
				<div></div>
			</div>
		</div>
	</div>
	<div id="sidebar">
		<div class="toggleCollapse">
			<h2>主菜单</h2>
			<div>收缩</div>
		</div>
		<div class="accordion" fillSpace="sidebar">
    		<#list menuVOList as menu>
            <div class="accordionHeader">
                <h2>
                    <span>Folder</span> ${menu.menuName}
                </h2>
            </div>
            <div class="accordionContent">
                <ul class="tree treeFolder">
                   <#list menu.list as me>
                    <li>
                        <a>${me.menuName}</a>
                        <ul>
                            <#list me.list as m>
                                <li><a href="${base}/${m.menuUrl}" target="navTab" rel="${m.targetName}">${m.menuName }</a></li>
                            </#list>
                        </ul>
                    </li>
                   </#list>
                </ul>
            </div>
            </#list>
		</div>
	</div>
</div>