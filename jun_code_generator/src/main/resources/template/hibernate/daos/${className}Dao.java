<#include "/java_copyright.include"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.${persistence}.daos;

import ${basepackage}.beans.${className};
import dwz.dal.BaseDao;

public interface ${className}Dao extends BaseDao<${className},${table.idColumn.javaType}>{

}
