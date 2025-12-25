<#if isWithPackage?exists && isWithPackage==true>package ${packageName}.service;</#if>
<#if isAutoImport?exists && isAutoImport==true>
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;

import ${packageName}.entity.${classInfo.className}Entity;
</#if>
/**
 * @description ${classInfo.classComment}服务层
 * @author ${authorName}
 * @date ${.now?string('yyyy-MM-dd')}
 */
public interface ${classInfo.className}Service extends IService<${classInfo.className}Entity> {


}
