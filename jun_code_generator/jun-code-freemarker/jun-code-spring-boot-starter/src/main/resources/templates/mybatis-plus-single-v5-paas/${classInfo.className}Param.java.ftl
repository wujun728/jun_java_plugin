<#if isWithPackage?exists && isWithPackage==true>package ${packageName}.vo;</#if>

<#if isAutoImport?exists && isAutoImport==true>
<#if isLombok?exists && isLombok==true>import lombok.Data;</#if>
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import net.trueland.scrm.common.enums.YesOrNo;
import net.trueland.scrm.common.model.vo.page.PageParam;
<#if isSwagger?exists && isSwagger==true>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;</#if>
</#if>
/**
 * @description ${classInfo.classComment}
 * @author ${authorName}
 * @date ${.now?string('yyyy-MM-dd')}
 */
<#if isLombok?exists && isLombok==true>@Data</#if><#if isSwagger?exists && isSwagger==true>
@Schema(title = "${classInfo.classComment}")</#if>
@Accessors(chain = true)
public class ${classInfo.className}Param  extends PageParam   implements Serializable {

    public interface Retrieve{}
    public interface Delete {}
    public interface Update {}
    public interface Create {}


<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >
    <#if isComment?exists && isComment==true>/**
    * ${fieldItem.fieldComment}
    */</#if><#if isSwagger?exists && isSwagger==true>
    @Schema(description = "${fieldItem.fieldComment}")</#if>
<#if fieldItem.nullable==false>
    @NotNull(message = "${fieldItem.fieldComment}不能为空", groups = {Create.class,Update.class,Delete.class})
</#if>
<#if fieldItem.nullable==false>
    @Size( max = ${fieldItem.columnSize?c},message = "${fieldItem.fieldComment}长度限制${fieldItem.columnSize?c}位")
</#if>
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};

<#if isLombok?exists && isLombok==false>
    public ${fieldItem.fieldClass} get${fieldItem.fieldName?cap_first}() {
        return ${fieldItem.fieldName};
    }

    public void set${fieldItem.fieldName?cap_first}(${fieldItem.fieldClass} ${fieldItem.fieldName}) {
        this.${fieldItem.fieldName} = ${fieldItem.fieldName};
    }
</#if>
</#list>
</#if>
    public ${classInfo.className}Vo() {}
}
