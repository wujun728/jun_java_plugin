FreeMarker,${strvalue}
FreeMarker,${title}
FreeMarker,${content}

listvalue:

<#list listvalue as v>
	${v} [${v_index}]
</#list>




<#macro greet p>
	Hello,${p}
</#macro>

<@greet p="张三"/>
<@greet p="${name}"/>

 
<@my.greet p="张三"/>
<@my.greet p="${name}"/>