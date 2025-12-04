package ${package.Entity?replace("model","dto.input")};

<#if cfg.swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Getter;
import lombok.Setter;
</#if>
import java.io.Serializable;

/**
 * <p>
 * ${table.comment!}
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if entityLombokModel>
@Getter
@Setter
</#if>
<#if cfg.swagger2>
@ApiModel(value="${entity}InputDTO对象", description="${table.comment!}")
</#if>
<#if superEntityClass??>
public class ${entity}InputDTO implements Serializable {
</#if>

    private static final long serialVersionUID = 1L;
    <#-- 定义一个序列 -->
    <#assign commonFields = ["createBy", "createDate", "updateBy", "updateDate","delFlag","remark"]>
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if !commonFields?seq_contains(field.propertyName)>
        <#if field.comment!?length gt 0>
            <#if cfg.swagger2>

    @ApiModelProperty(value = "${field.comment}")
            </#if>
        </#if>
    private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>
<#------------  END 字段循环遍历  ---------->

}