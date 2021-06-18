package org.typroject.tyboot.api.face.systemctl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.util.Date;

/**
 * <p>
 * 系统字典表数据
 * </p>
 *
 * @author Wujun
 * @since 2017-06-27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class 	DictionarieValueModel extends BaseModel {

    private static final long serialVersionUID = 4894465489456L;


    /**
     * 字典编码(系统中固定不变的)
     */
	private String dictCode;
    /**
     * 字典KEY(当前字典中唯一)
     */
	private String dictDataKey;
	private String dictDataValue;
    /**
     * 字典VALUE描述
     */
	private String dictDataDesc;
    /**
     * 排序字段
     */
	private Integer orderNum;
    /**
     * 机构编码
     */
	private String agencyCode;

	private String lockStatus;
	private String lockUserId;
	private Date lockDate;
}
