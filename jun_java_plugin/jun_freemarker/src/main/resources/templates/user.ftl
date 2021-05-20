<#--Freemarker遍历list-->
简单遍历list:
<#list userList as user>
	用户名：${user.userName}
	密  码：${user.userPassword}
	年  龄: ${user.age}
</#list>


<#--Freemarker遍历list并应用list隐含变量item_index-->
item_index使用：
<#list userList as user>
第${user_index+1}个用户
	用户名：${user.userName}
	密  码：${user.userPassword}
	年  龄: ${user.age}
</#list>
<#--Freemarker遍历list并应用list隐含变量item_has_next-->
item_has_next,size使用：
<#list userList as user>

	用户名：${user.userName}
	密  码：${user.userPassword}
	年  龄: ${user.age}
	<#if !user_has_next>
	共有${userList?size}最后一个用户是:${user.userName}
	</#if>
</#list>
<#--Freemarker遍历list并按用户年龄升序排序-->

按用户年龄升序排序：
<#list userList?sort_by("age") as user>

	用户名：${user.userName}
	密  码：${user.userPassword}
	年  龄: ${user.age}
	
</#list>
<#--Freemarker遍历list并按用户年龄降序排序-->

按用户年龄降序排序：
<#list userList?sort_by("age")?reverse as user>

	用户名：${user.userName}
	密  码：${user.userPassword}
	年  龄: ${user.age}
	
</#list>
<#--Freemarker遍历list当用户年龄大于21岁时，停止输出-->
list中应用break:
<#list userList?sort_by("age")?reverse as user>

	用户名：${user.userName}
	密  码：${user.userPassword}
	年  龄: ${user.age}
	<#if (user.age>21) >
		<#break>
	</#if>
</#list>

