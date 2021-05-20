package org.typroject.tyboot.api.face.systemctl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

/**
 * <p>
 * 系统字典表
 * </p>
 *
 * @author Wujun
 * @since 2017-06-27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictionarieModel extends BaseModel {

    private static final long serialVersionUID = 57438927329847L;

    /**
     * 字典编码 系统中固定不变
     */
	private String dictCode;
    /**
     * 字典名字
     */
	private String dictName;
    /**
     * 字典值描述
     */
	private String dictDesc;
    /**
     * 机构编码
     */
	private String agencyCode;
}
