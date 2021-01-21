<#-- bean template -->
package ${conf.entityPackage}<#if table.prefix!="">.${table.prefix}</#if>;

import com.alibaba.fastjson.JSON;
import com.github.wujun728.common.dto.pager.PagerInfo;
<#list table.propTypePackages as package>
${package}
</#list>
/**
* Copyright (C), 2017-2020, cn.zlinks
* FileName: ${table.beanName}
* Author:   Wujun
* Date:     ${.now}
* Description: 表名：${table.tableName},描述：${table.tableDesc}
*/
public class ${table.beanName} extends PagerInfo  {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
<#--
<#assign properties = table.properties/>
<#assign propInfoMap = table.propInfoMap/>
<#assign keys = propInfoMap?keys/>
-->
<#assign allPropInfo = table.allPropInfo/>
<#list allPropInfo as prop>
	/**
	 * ${prop.propertyDesc}
	 */
	private ${prop.propertyType} ${prop.propertyName};
</#list>

<#list allPropInfo as prop>
	/**
	 * 获取${prop.propertyDesc}
	 */
	public ${prop.propertyType} get${prop.propertyName?cap_first}() {
		return this.${prop.propertyName};
	}

	/**
	 * 设置${prop.propertyDesc}
	 */
	public void set${prop.propertyName?cap_first}(${prop.propertyType} ${prop.propertyName}) {
		this.${prop.propertyName} = ${prop.propertyName};
	}

</#list>
<#--
<#list keys as key>
	/**
	 * 获取${propInfoMap["${key}"].propertyDesc}
	 */
	public ${properties["${key}"]} get${key?cap_first}() {
		return this.${key};
	}

	/**
	 * 设置${propInfoMap["${key}"].propertyDesc}
	 */
	public void set${key?cap_first}(${properties["${key}"]} ${key}) {
		this.${key} = ${key};
	}

</#list>
-->
    @Override
    public String toString() {
    	return JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss");
    }
}
