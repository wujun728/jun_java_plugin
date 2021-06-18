package org.typroject.tyboot.api.face.systemctl.orm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

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
@TableName("systemctl_dictionarie")
public class Dictionarie extends BaseEntity{

    private static final long serialVersionUID = 5648645132156L;

    /**
     * 字典编码 系统中固定不变
     */
    @TableField("DICT_CODE")
	private String dictCode;
    /**
     * 字典名字
     */
	@TableField("DICT_NAME")
	private String dictName;

    /**
     * 字典值描述
     */
	@TableField("DICT_DESC")
	private String dictDesc;

    /**
     * 机构编码
     */

	@TableField("AGENCY_CODE")
	private String agencyCode;

}
