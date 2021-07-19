package ${packageBase}.model;

<#if (tableBean.hasDateColumn)>
import java.util.Date;
</#if>
<#if (tableBean.hasBigDecimal)>
import java.math.BigDecimal;
</#if>

public class ${tableBean.tableNameCapitalized} {
<#list tableBean.columnBeanList as columnBean>
    <#if ('' != columnBean.columnComment)>
    /**
    * ${columnBean.columnComment}
    **/
    </#if>
private ${columnBean.columnType} ${columnBean.columnNameNoDash};

</#list>
public ${tableBean.tableNameCapitalized}() {
}

public ${tableBean.tableNameCapitalized}(<#list tableBean.columnBeanList as columnBean>${columnBean.columnType} ${columnBean.columnNameNoDash}<#if columnBean_has_next>, </#if></#list>) {
<#list tableBean.columnBeanList as columnBean>
this.${columnBean.columnNameNoDash} = ${columnBean.columnNameNoDash};
</#list>
}

<#list tableBean.columnBeanList as columnBean>
public void set${columnBean.columnNameCapitalized}(${columnBean.columnType} ${columnBean.columnNameNoDash}) {
this.${columnBean.columnNameNoDash} = ${columnBean.columnNameNoDash};
}

public ${columnBean.columnType} get${columnBean.columnNameCapitalized}() {
return ${columnBean.columnNameNoDash};
}

</#list>
}
