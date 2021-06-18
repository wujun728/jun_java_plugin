package org.typroject.tyboot.api.face.systemctl.orm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

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
@TableName("systemctl_dictionarie_value")
public class DictionarieValue extends BaseEntity {

    private static final long serialVersionUID = 454515151231231L;

    /**
     * 字典编码(系统中固定不变的)
     */
	@TableField("DICT_CODE")
	private String dictCode;
    /**
     * 字典KEY(当前字典中唯一)
     */
	@TableField("DICT_DATA_KEY")
	private String dictDataKey;
	@TableField("DICT_DATA_VALUE")
	private String dictDataValue;
    /**
     * 字典VALUE描述
     */
	@TableField("DICT_DATA_DESC")
	private String dictDataDesc;
    /**
     * 排序字段
     */
	@TableField("ORDER_NUM")
	private Integer orderNum;
    /**
     * 机构编码
     */
	@TableField("AGENCY_CODE")
	private String agencyCode;

	@TableField("LOCK_STATUS")
	private String lockStatus;
	@TableField("LOCK_USER_ID")
	private String lockUserId;
	@TableField("LOCK_DATE")
	private Date lockDate;
}
