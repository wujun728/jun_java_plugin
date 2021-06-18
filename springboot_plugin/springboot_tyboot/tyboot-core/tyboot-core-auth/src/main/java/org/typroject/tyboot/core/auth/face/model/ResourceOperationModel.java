package org.typroject.tyboot.core.auth.face.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

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
public class ResourceOperationModel extends BaseModel {

    private static final long serialVersionUID = 89784513154165465L;


    /**
     * 模块代码，程序中的注解
     */
	private String moduleCode;
    /**
     * 资源代码，程序中的注解
     */
	private String resourceCode;
    /**
     * 操作代码，程序中的注解
     */
	private String oprateCode;
    /**
     * 按约定规则识别操作的唯一的标识FXXXXXXXXXX(F后面8位数字加2位字符)
     */
	private String apiCode;
    /**
     * 父主键UID
     */
	private String parentId;
    /**
     * 资源类型(模块、资源、操作)
     */
	private String resType;
    /**
     * 操作级别代码，程序中的注解
     */
	private String levelCode;
    /**
     * 模块名称
     */
	private String moduleName;
    /**
     * 资源名称
     */
	private String resourceName;
    /**
     * 操作名称
     */
	private String oprateName;
    /**
     * 操作级别
     */
	private String oprateLevel;
    /**
     * 机构编码
     */
	private String agencyCode;
    /**
     * 请求地址
     */
	private String reqUrl;
    /**
     * 请求方式get,post,put,delete
     */
	private String reqMethod;
}
