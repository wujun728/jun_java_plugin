package com.chentongwei.core.system.entity.io.report;

import com.chentongwei.common.entity.io.TokenIO;
import com.chentongwei.core.system.annotation.Type;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author TongWei.Chen 2017-12-16 17:00:00
 * @Project tucaole
 * @Description: 吐槽了举报
 */
@Data
public class ReportIO extends TokenIO {

    /** 主键id */
    private Long id;
    /** 举报资源id */
    @NotNull
    private Long resourceId;
    /** 举报原因 */
    @NotNull
    @NotBlank
    private String remark;
    /** 举报类型表id */
    @NotNull
    private Integer reportTypeId;
    /** 类型：1：吐槽的文章；2：吐槽文章的评论；*/
    @Type
    private Integer type;
}