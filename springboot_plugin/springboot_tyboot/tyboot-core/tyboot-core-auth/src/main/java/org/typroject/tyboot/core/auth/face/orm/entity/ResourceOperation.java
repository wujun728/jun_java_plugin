package org.typroject.tyboot.core.auth.face.orm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

/**
 * <p>
 * 资源操作表
 * </p>
 *
 * @author Wujun
 * @since 2017-08-18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("auth_resource_operation")
public class ResourceOperation extends BaseEntity {

    private static final long serialVersionUID = 89784513154165465L;


    /**
     * 模块代码，程序中的注解
     */
	@TableField("MODULE_CODE")
	private String moduleCode;
    /**
     * 资源代码，程序中的注解
     */
	@TableField("RESOURCE_CODE")
	private String resourceCode;
    /**
     * 操作代码，程序中的注解
     */
	@TableField("OPRATE_CODE")
	private String oprateCode;
    /**
     * 按约定规则识别操作的唯一的标识FXXXXXXXXXX(F后面8位数字加2位字符)
     */
	@TableField("API_CODE")
	private String apiCode;
    /**
     * 父主键UID
     */
	@TableField("PARENT_ID")
	private String parentId;
    /**
     * 资源类型(模块、资源、操作)
     */
	@TableField("RES_TYPE")
	private String resType;
    /**
     * 操作级别代码，程序中的注解
     */
	@TableField("LEVEL_CODE")
	private String levelCode;
    /**
     * 模块名称
     */
	@TableField("MODULE_NAME")
	private String moduleName;
    /**
     * 资源名称
     */
	@TableField("RESOURCE_NAME")
	private String resourceName;
    /**
     * 操作名称
     */
	@TableField("OPRATE_NAME")
	private String oprateName;
    /**
     * 操作级别
     */
	@TableField("OPRATE_LEVEL")
	private String oprateLevel;
    /**
     * 机构编码
     */
	@TableField("AGENCY_CODE")
	private String agencyCode;
    /**
     * 请求地址
     */
	@TableField("REQ_URL")
	private String reqUrl;
    /**
     * 请求方式get,post,put,delete
     */
	@TableField("REQ_METHOD")
	private String reqMethod;
}