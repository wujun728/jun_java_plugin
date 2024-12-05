<#if isWithPackage?exists && isWithPackage==true>package ${packageName}.service.impl;</#if>
<#if isAutoImport?exists && isAutoImport==true>
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${packageName}.entity.${classInfo.className}Entity;
import ${packageName}.mapper.${classInfo.className}Mapper;
import ${packageName}.service.${classInfo.className}Service;

</#if>
/**
 * @description ${classInfo.classComment}服务层实现
 * @author ${authorName}
 * @date ${.now?string('yyyy-MM-dd')}
 */
@Service
public class ${classInfo.className}ServiceImpl extends ServiceImpl<${classInfo.className}Mapper, ${classInfo.className}Entity> implements ${classInfo.className}Service {

	@Autowired
    private ${classInfo.className}Mapper ${classInfo.className?uncap_first}Mapper;

}



